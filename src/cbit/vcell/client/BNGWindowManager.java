/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.client;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.EventObject;
import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.vcell.sbml.vcell.SBMLImporter;
import org.vcell.sbml.vcell.SBMLUnitTranslator;
import org.vcell.util.ProgressDialogListener;
import org.vcell.util.UserCancelException;
import org.vcell.util.gui.DialogUtils;
import org.vcell.util.gui.VCFileChooser;

import cbit.util.xml.VCLogger;
import cbit.util.xml.XmlUtil;
import cbit.vcell.biomodel.BioModel;
import cbit.vcell.biomodel.ModelUnitConverter;
import cbit.vcell.client.bionetgen.BNGOutputPanel;
import cbit.vcell.client.desktop.biomodel.UnitSystemSelectionPanel;
import cbit.vcell.client.server.UserPreferences;
import cbit.vcell.client.task.AsynchClientTask;
import cbit.vcell.client.task.ClientTaskDispatcher;
import cbit.vcell.client.task.DisplayBNGOutput;
import cbit.vcell.client.task.RunBioNetGen;
import cbit.vcell.model.DistributedKinetics;
import cbit.vcell.model.Kinetics;
import cbit.vcell.model.LumpedKinetics;
import cbit.vcell.model.ModelUnitSystem;
import cbit.vcell.model.ReactionStep;
import cbit.vcell.server.bionetgen.BNGInput;
import cbit.vcell.server.bionetgen.BNGUtils;
import cbit.vcell.xml.XMLInfo;
import cbit.vcell.xml.XmlHelper;

/**
 * Insert the type's description here.
 * Creation date: (7/18/2006 2:18:31 PM)
 * @author: Anuradha Lakshminarayana
 */
public class BNGWindowManager extends TopLevelWindowManager {
	/**
	 * key for async hash
	 */
	public static final String BNG_OUTPUT_PANEL = "bngOutputPanel";
	
	private BNGOutputPanel fieldBngOutputPanel = null;

/**
 * BNGWindowManager constructor comment.
 * @param requestManager cbit.vcell.client.RequestManager
 */
public BNGWindowManager(BNGOutputPanel argBngOutputPanel, RequestManager requestManager) {
	super(requestManager);
	setBngOutputPanel(argBngOutputPanel);
}


/**
 * Gets the bngOutputPanel property (cbit.vcell.client.bionetgen.BNGOutputPanel) value.
 * @return The bngOutputPanel property value.
 */
public BNGOutputPanel getBngOutputPanel() {
	return fieldBngOutputPanel;
}


/**
 * Insert the method's description here.
 * Creation date: (7/18/2006 2:18:31 PM)
 * @return java.lang.String
 */
public java.awt.Component getComponent() {
	return getBngOutputPanel();
}


/**
 * Insert the method's description here.
 * Creation date: (7/18/2006 2:18:31 PM)
 * @return java.lang.String
 */
public String getManagerID() {
	return ClientMDIManager.BIONETGEN_WINDOW_ID;
}


/**
 * Comment
 */
public void importSbml(String bngSbmlStr) {
	if (bngSbmlStr == null || bngSbmlStr.length() == 0) {
		throw new RuntimeException("SBMl string is empty, cannot import into VCell.");
	}
	
	//
	// 1. Convert SBML string from BNG to SBML model, add unitDefintions to SBML model using VCell sbml compatible unit system 
	// 2. Import unit modified SBML model into VCell as biomodel
	// 3. Enforce "cleaner" (looking) units on this imported biomodel (can use the units added to the sbml model above)
	// 4. Convert all LumpedKinetics reactions into DistributedKinetics.
	// 4. Convert this biomodel into vcml string and pass it into XMLInfo and then to RequestManager to open document.
	//
	
	ModelUnitSystem mus = ModelUnitSystem.createDefaultVCModelUnitSystem();
	ModelUnitSystem sbmlCompatibleVCModelUnitSystem = ModelUnitSystem.createSBMLUnitSystem(mus.getVolumeSubstanceUnit(), mus.getVolumeUnit(), mus.getAreaUnit(), mus.getLengthUnit(), mus.getTimeUnit());
	
	// display to user to change units if desired.
	UnitSystemSelectionPanel unitSystemSelectionPanel = new UnitSystemSelectionPanel(true);
	unitSystemSelectionPanel.initialize(sbmlCompatibleVCModelUnitSystem);
	int retcode = DialogUtils.showComponentOKCancelDialog(getBngOutputPanel(), unitSystemSelectionPanel, "Select new unit system to import into VCell");
	ModelUnitSystem forcedModelUnitSystem = null;
	while (retcode == JOptionPane.OK_OPTION){
		try {
			forcedModelUnitSystem = unitSystemSelectionPanel.createModelUnitSystem();
			break;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			DialogUtils.showErrorDialog(getBngOutputPanel(), e.getMessage(), e);
			retcode = DialogUtils.showComponentOKCancelDialog(getBngOutputPanel(), unitSystemSelectionPanel, "Select new unit system to import into VCell");
		}
	}
	if (forcedModelUnitSystem == null) {
		DialogUtils.showErrorDialog(getBngOutputPanel(), "Units are required for import into Virtual Cell.");
	}

	String modifiedSbmlStr = SBMLUnitTranslator.addUnitDefinitionsToSbmlModel(bngSbmlStr, forcedModelUnitSystem);
	
	// Create a default VCLogger - SBMLImporter needs it
    cbit.util.xml.VCLogger logger = new cbit.util.xml.VCLogger() {
        //private StringBuffer buffer = new StringBuffer();
        public void sendMessage(int messageLevel, int messageType) {
            String message = cbit.util.xml.VCLogger.getDefaultMessage(messageType);
            sendMessage(messageLevel, messageType, message);	
        }
        public void sendMessage(int messageLevel, int messageType, String message) {
            System.err.println("LOGGER: msgLevel="+messageLevel+", msgType="+messageType+", "+message);
            if (messageLevel == VCLogger.HIGH_PRIORITY) {
            	throw new RuntimeException("Import failed : " + message);
            }
        }
        public void sendAllMessages() {
        }
        public boolean hasMessages() {
            return false;
        }
    };
    
    // import sbml String into VCell biomodel
	try {
		File sbmlFile = File.createTempFile("temp", ".xml");
		sbmlFile.deleteOnExit();
		XmlUtil.writeXMLStringToFile(modifiedSbmlStr, sbmlFile.getAbsolutePath(), true);

		org.vcell.sbml.vcell.SBMLImporter sbmlImporter = new SBMLImporter(sbmlFile.getAbsolutePath(), logger, false);
		BioModel bioModel = sbmlImporter.getBioModel();
		
		// enforce 'cleaner looking' units on vc biomodel (the process of adding unit defintion to sbml model messes up the units, though they are correct units (eg., 1e-6m for um).
		BioModel modifiedBiomodel = ModelUnitConverter.createBioModelWithNewUnitSystem(bioModel, forcedModelUnitSystem);
		
		// convert any reaction that has GeneralLumpedKinetics to GeneralKinetics
		for (ReactionStep rs : modifiedBiomodel.getModel().getReactionSteps()) {
			Kinetics kinetics = rs.getKinetics();
			if (kinetics instanceof LumpedKinetics) {
				rs.setKinetics(DistributedKinetics.toDistributedKinetics((LumpedKinetics)kinetics));
			}
		}

		// convert biomodel to vcml string
		String vcmlString = XmlHelper.bioModelToXML(modifiedBiomodel);

		XMLInfo xmlInfo = new XMLInfo(vcmlString);

		if (xmlInfo != null) {
			getRequestManager().openDocument(xmlInfo, this, true);
		}
	} catch (Exception e) {
		e.printStackTrace(System.out);
		throw new RuntimeException("Cound not convert BNG sbml string to VCell biomodel : ", e);
	}		
}


/**
 * Insert the method's description here.
 * Creation date: (7/18/2006 2:18:31 PM)
 * @return boolean
 */
public boolean isRecyclable() {
	return true;
}


/**
 * Gets the bngOutputPanel property (cbit.vcell.client.bionetgen.BNGOutputPanel) value.
 * @return The bngOutputPanel property value.
 */
public void runBioNetGen(BNGInput bngInput) {

	// Create a hash and put in the details required to run the ClientTaskDispatcher
	Hashtable<String, Object> hash = new Hashtable<String, Object>();
	hash.put("bngInput", bngInput);
	hash.put(BNG_OUTPUT_PANEL, getBngOutputPanel());

	// Create the AsynchClientTasks : in this case, running the BioNetGen (non-swing) and then displaying the output (swing) tasks.
	AsynchClientTask[] tasksArray = new AsynchClientTask[2];
	tasksArray[0] = new RunBioNetGen();
	tasksArray[1] = new DisplayBNGOutput();

	// Dispatch the tasks using the ClientTaskDispatcher.
	ClientTaskDispatcher.dispatch(getBngOutputPanel(), hash, tasksArray, false, true, new ProgressDialogListener() {
		
		public void cancelButton_actionPerformed(EventObject newEvent) {
			try {
				BNGUtils.stopBNG();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}


/**
 * Comment
 */
public void saveOutput(String bngOutputStr) {

	// Ask user for save location
	String defaultPath = getUserPreferences().getGenPref(UserPreferences.GENERAL_LAST_PATH_USED);
	VCFileChooser fileChooser = new VCFileChooser(defaultPath);
	fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
	fileChooser.setMultiSelectionEnabled(false);
	// remove all selector
	fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
    fileChooser.setSelectedFile(new java.io.File(getBngOutputPanel().getSelectedOutputFileName()));
	
	fileChooser.setDialogTitle("Save Selected BNG Output Format ...");
	if (fileChooser.showSaveDialog(getBngOutputPanel()) != JFileChooser.APPROVE_OPTION) {
		// user didn't choose save
		throw UserCancelException.CANCEL_FILE_SELECTION;
	} else {
		File selectedFile = fileChooser.getSelectedFile();
		if (selectedFile == null) {
			// no file selected (no name given)
			throw UserCancelException.CANCEL_FILE_SELECTION;
		} else {
			// we have a file selection, check for overwrites
			if (selectedFile.exists()) {
				String answer = PopupGenerator.showWarningDialog(this, getUserPreferences(), UserMessage.warn_OverwriteFile, selectedFile.getAbsolutePath());
				if (answer.equals(UserMessage.OPTION_CANCEL)){
					throw UserCancelException.CANCEL_FILE_SELECTION;
				}
			}

			// write out text into file using FileOutputStream and PrintWriter
			java.io.FileOutputStream outputStream = null;
			String selectedFileName = selectedFile.getPath();
			try {
				outputStream = new java.io.FileOutputStream(selectedFileName);
			}catch (java.io.IOException e){
				e.printStackTrace(System.out);
				throw new RuntimeException("Error opening file '"+ selectedFileName + " to write BioNetGen output" +": " + e.getMessage());
			}	
				
			PrintWriter bngOutputFile = new PrintWriter(outputStream);
			bngOutputFile.print(bngOutputStr);
			bngOutputFile.close();
		}
	}	
}


/**
 * Insert the method's description here.
 * Creation date: (7/18/2006 2:18:31 PM)
 * @return boolean
 */
private void setBngOutputPanel(BNGOutputPanel newBngOutputPanel) {
	fieldBngOutputPanel = newBngOutputPanel;
}


/**
 * Gets the bngOutputPanel property (cbit.vcell.client.bionetgen.BNGOutputPanel) value.
 * @return The bngOutputPanel property value.
 */
public void stopBioNetGen() {
	try {
		cbit.vcell.server.bionetgen.BNGUtils.stopBNG();
	} catch (Exception ex) {
		ex.printStackTrace(System.out);
	}
}


/**
 * Comment
 */
public String uploadBNGLFile() throws java.io.FileNotFoundException, java.io.IOException {
	// BNG input file (.bngl) contents
	String bngInputStr = null;
	// Ask user for upload location
	String defaultPath = getUserPreferences().getGenPref(UserPreferences.GENERAL_LAST_PATH_USED);
	VCFileChooser fileChooser = new VCFileChooser(defaultPath);
	fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
	fileChooser.setMultiSelectionEnabled(false);
	// remove all selector
	fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());

	// set default file filter
	fileChooser.addChoosableFileFilter(org.vcell.util.gui.FileFilters.FILE_FILTER_BNGL);
	fileChooser.setFileFilter(org.vcell.util.gui.FileFilters.FILE_FILTER_BNGL);

	// Set file chooser dialog title
	fileChooser.setDialogTitle("Upload Selected BNG file ...");
	if (fileChooser.showOpenDialog(getBngOutputPanel()) != JFileChooser.APPROVE_OPTION) {
		// user didn't choose Open
		throw UserCancelException.CANCEL_FILE_SELECTION;
	} else {
		File selectedFile = fileChooser.getSelectedFile();
		if (selectedFile == null) {
			// no file selected (no name given)
			throw UserCancelException.CANCEL_FILE_SELECTION;
		} else {
			long selectedFileLength = selectedFile.length();
			// Check if file exists
			if (!selectedFile.exists()){
				throw new RuntimeException("File "+selectedFile.getPath()+" not found");
			}
			// Check if file has .bngl extension
			if (!selectedFile.getPath().endsWith(".bngl")) {
				DialogUtils.showErrorDialog(getComponent(), "File " + selectedFile.getPath() + " is not a .bngl file");
				throw new RuntimeException("File " + selectedFile.getPath() + " is not a .bngl file");
			}
			// Read characters from file into character array and transfer into string buffer.
			StringBuffer stringBuffer = new StringBuffer();
			FileInputStream fis = null;
			fis = new FileInputStream(selectedFile);
			InputStreamReader reader = new InputStreamReader(fis);
			try (BufferedReader br = new BufferedReader(reader)) {
				char charArray[] = new char[10000];
				while (true) {
					int numRead = br.read(charArray, 0, charArray.length);
					if (numRead > 0) {
						stringBuffer.append(charArray,0,numRead);
					} else if (numRead == -1) {
						break;
					}
				}
			} 

			if (stringBuffer.length() != selectedFileLength){
				System.out.println("<<<SYSOUT ALERT>>> Reading from bng file: read "+stringBuffer.length()+" of "+selectedFileLength+" bytes of input file");
			}
			bngInputStr = stringBuffer.toString();
		}
		return bngInputStr;
	}	
}
}
