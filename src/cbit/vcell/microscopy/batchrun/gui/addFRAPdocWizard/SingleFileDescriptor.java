package cbit.vcell.microscopy.batchrun.gui.addFRAPdocWizard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ListSelectionModel;

import org.vcell.util.UserCancelException;
import org.vcell.util.gui.DialogUtils;
import org.vcell.wizard.WizardPanelDescriptor;

import cbit.vcell.VirtualMicroscopy.ImageDataset;
import cbit.vcell.VirtualMicroscopy.ImageDatasetReader;
import cbit.vcell.client.task.AsynchClientTask;
import cbit.vcell.microscopy.FRAPData;
import cbit.vcell.microscopy.FRAPStudy;
import cbit.vcell.microscopy.FRAPSingleWorkspace;
import cbit.vcell.microscopy.batchrun.FRAPBatchRunWorkspace;
import cbit.vcell.microscopy.batchrun.gui.VirtualFrapBatchRunFrame;
import cbit.vcell.microscopy.gui.FRAPStudyPanel;
import cbit.vcell.microscopy.gui.VirtualFrapLoader;
import cbit.vcell.microscopy.gui.VirtualFrapMainFrame;
import cbit.vcell.microscopy.gui.loaddatawizard.LoadFRAPData_SingleFilePanel;
import cbit.vcell.simdata.DataIdentifier;
import cbit.vcell.simdata.SimDataConstants;
import cbit.vcell.simdata.VariableType;

public class SingleFileDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "BATCHRUN_SingleFile";
    //    private FRAPStudy localFrapStudy = null;
    private LoadFRAPData_SingleFilePanel singleFilePanel = new LoadFRAPData_SingleFilePanel();
    private boolean isFileLoaded = false;
    private FRAPBatchRunWorkspace batchRunWorkspace = null;
    
	public SingleFileDescriptor() {
    	super();
        setPanelComponent(singleFilePanel);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setProgressPopupShown(true); 
        setTaskProgressKnown(true);
    }
    //This method is override to make fure that it goes to SummaryPanel
    public String getNextPanelDescriptorID() {
        return FileSummaryDescriptor.IDENTIFIER;
    }
    //This method is override to make sure that it backs to FileTypePanel
    public String getBackPanelDescriptorID() {
        return FileTypeDescriptor.IDENTIFIER;
    }
    
    //load the data before the panel disappears
    public ArrayList<AsynchClientTask> preNextProcess()
    {
    	//create AsynchClientTask arraylist
		ArrayList<AsynchClientTask> taskArrayList = new ArrayList<AsynchClientTask>();
		
    	if(singleFilePanel.getFileName().length() > 0)
    	{
    		final String fileStr = singleFilePanel.getFileName();
    		final String LOADING_MESSAGE = "Loading "+fileStr+"...";
    		
			AsynchClientTask updateUIBeforeLoadTask = new AsynchClientTask("", AsynchClientTask.TASKTYPE_SWING_BLOCKING) 
    		{
    			public void run(Hashtable<String, Object> hashTable) throws Exception
    			{
    				VirtualFrapBatchRunFrame.updateStatus(LOADING_MESSAGE);
    			}
    		};
			
			
    		AsynchClientTask loadTask = new AsynchClientTask(LOADING_MESSAGE, AsynchClientTask.TASKTYPE_NONSWING_BLOCKING) 
    		{
    			public void run(Hashtable<String, Object> hashTable) throws Exception
    			{
    					FRAPStudy newFRAPStudy = null;
    					
    					File inFile = new File(fileStr);
    					if(inFile.getName().endsWith(SimDataConstants.LOGFILE_EXTENSION)) //.log (vcell log file) 
    					{
							DataIdentifier[] dataIdentifiers = FRAPData.getDataIdentiferListFromVCellSimulationData(inFile, 0);
							String[][] rowData = new String[dataIdentifiers.length][1];
							for (int i = 0; i < dataIdentifiers.length; i++) {
								if(dataIdentifiers[i].getVariableType().equals(VariableType.VOLUME)){
									rowData[i][0] = dataIdentifiers[i].getName();
								}
							}
							int[] selectedIndexArr = DialogUtils.showComponentOKCancelTableList(
									SingleFileDescriptor.this.getPanelComponent(),
									"Select Volume Variable",
									new String[] {"Volume Variable Name"},
									rowData, ListSelectionModel.SINGLE_SELECTION);
							if(selectedIndexArr != null && selectedIndexArr.length > 0)
							{
//								newFRAPStudy = getFrapWorkspace().loadFRAPDataFromVcellLogFile(inFile, dataIdentifiers[selectedIndexArr[0]].getName(), this.getClientTaskStatusSupport());
								isFileLoaded = true;
							}else{
								throw UserCancelException.CANCEL_GENERIC;
							}
    					}else //.lsm or other image formatss
    					{
    							newFRAPStudy = getBatchRunWorkspace().loadFRAPDataFromImageFile(inFile, this.getClientTaskStatusSupport());
    							isFileLoaded = true;
    					}
    					
    					//for all loaded file
    					hashTable.put(FRAPStudyPanel.NEW_FRAPSTUDY_KEY, newFRAPStudy);
    					
    			}
    		};
    		
    		AsynchClientTask afterLoadingSwingTask = new AsynchClientTask(LOADING_MESSAGE, AsynchClientTask.TASKTYPE_SWING_BLOCKING) 
    		{
    			public void run(Hashtable<String, Object> hashTable) throws Exception
    			{
    				FRAPStudy newFRAPStudy = (FRAPStudy)hashTable.get(FRAPStudyPanel.NEW_FRAPSTUDY_KEY);
    				//setFrapStudy fires property change, so we have to put it in Swing thread.
    				getBatchRunWorkspace().getWorkingSingleWorkspace().setFrapStudy(newFRAPStudy, true);
    				
//    				VirtualFrapLoader.mf.setMainFrameTitle("");
    				VirtualFrapBatchRunFrame.updateProgress(0);
    				if(isFileLoaded)
    				{
        				VirtualFrapBatchRunFrame.updateStatus("Loaded " + fileStr);
    				}
    				else
    				{
    					VirtualFrapBatchRunFrame.updateStatus("Failed loading " + fileStr+".");
    				}
    			}
    		};
    		taskArrayList.add(updateUIBeforeLoadTask);
    		taskArrayList.add(loadTask);
    		taskArrayList.add(afterLoadingSwingTask);
    	}
		
		return taskArrayList;
    } 
    
    public FRAPBatchRunWorkspace getBatchRunWorkspace() {
		return batchRunWorkspace;
	}
    
	public void setBatchRunWorkspace(FRAPBatchRunWorkspace batchRunWorkspace) {
		this.batchRunWorkspace = batchRunWorkspace;
	}
}