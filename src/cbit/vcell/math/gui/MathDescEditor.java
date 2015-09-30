/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.math.gui;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import cbit.gui.MultiPurposeTextPanel;
import cbit.vcell.math.BoundaryConditionType;
import cbit.vcell.math.MathDescription;
import cbit.vcell.math.MathFormatException;
import cbit.vcell.math.VCML;

/**
 * This class was generated by a SmartGuide.
 * 
 */
@SuppressWarnings("serial")
public class MathDescEditor extends JPanel implements ActionListener, KeyListener {
	private JButton ivjCancelButton = null;
	private MathDescription ivjMathDescription = null;
	private JButton ivjApplyButton = null;
	protected transient java.beans.PropertyChangeSupport propertyChange;
	private MultiPurposeTextPanel vcmlPane = null;
	
	private final static Set<String> autoCompletionWords = new HashSet<String>();
	private final static Set<String> keywords = new HashSet<String>();

/**
 * Constructor
 */
public MathDescEditor() {
	super();
	initialize();
}


/**
 * Method to handle events for the ActionListener interface.
 * @param e java.awt.event.ActionEvent
 */
public void actionPerformed(java.awt.event.ActionEvent e) {
	if (e.getSource() == getApplyButton()) 
		applyChanges(e);
	if (e.getSource() == getCancelButton()) 
		cancelChanges(e);

}


/**
 * Comment
 */
private void apply_ExceptionOccurred(java.lang.Throwable e) throws javax.swing.text.BadLocationException {
	JOptionPane.showMessageDialog(this, e.getMessage(), "Error While Saving", JOptionPane.ERROR_MESSAGE);
	if (e instanceof MathFormatException){
		int lineNumber = ((MathFormatException)e).getLineNumber();
		if (lineNumber>=0){
			int lineStartOffset = getVCMLPane().getLineStartOffset(Math.max(0,lineNumber-1));
			//int lineEndOffset = getVCMLPane().getLineEndOffset(lineNumber);
			getVCMLPane().setCaretPosition(lineStartOffset);
			//getVCMLPane().select(lineStartOffset,lineEndOffset);
		}
	}
}

/**
 * connEtoM1:  (ApplyButton.action.actionPerformed(java.awt.event.ActionEvent) --> tempMathDescription.this)
 * @param arg1 java.awt.event.ActionEvent
 */
private void applyChanges(java.awt.event.ActionEvent arg1) {
	try {
		if ((getMathDescription() != null)) {
			JTextPane tp = getVCMLPane().getTextPane();
			int cp = tp.getCaretPosition();
			setMathDescription(MathDescription.fromEditor(getMathDescription(), getVCMLPane().getText()));
			cp = Math.min(cp, tp.getText().length());
			tp.setCaretPosition(cp);
		}
		getCancelButton().setEnabled(false);
		getApplyButton().setEnabled(false);
	} catch (java.lang.Throwable ivjExc) {
		try {
			apply_ExceptionOccurred(ivjExc);
		} catch (BadLocationException e) {			
			handleException(e);
		}
	}
}

/**
 * connEtoM24:  (CancelButton.action.actionPerformed(java.awt.event.ActionEvent) --> lineNumberedTextPanel1.text)
 * @param arg1 java.awt.event.ActionEvent
 */
private void cancelChanges(java.awt.event.ActionEvent arg1) {
	try {
		if ((getMathDescription() != null)) {
			getVCMLPane().setText(getMathDescription().getVCML_database());
		}
		getVCMLPane().setCaretPosition(0);
		getApplyButton().setEnabled(false);
		getCancelButton().setEnabled(false);

	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
}

/**
 * Return the ApplyButton property value.
 * @return javax.swing.JButton
 */
private javax.swing.JButton getApplyButton() {
	if (ivjApplyButton == null) {
		try {
			ivjApplyButton = new javax.swing.JButton();
			ivjApplyButton.setName("ApplyButton");
			ivjApplyButton.setText("Apply Changes");
			ivjApplyButton.setEnabled(false);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}
	return ivjApplyButton;
}

/**
 * Return the CancelButton property value.
 * @return javax.swing.JButton
 */
private javax.swing.JButton getCancelButton() {
	if (ivjCancelButton == null) {
		try {
			ivjCancelButton = new javax.swing.JButton();
			ivjCancelButton.setName("CancelButton");
			ivjCancelButton.setText("Cancel");
			ivjCancelButton.setEnabled(false);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}
	return ivjCancelButton;
}

/**
 * Return the lineNumberedTextPanel1 property value.
 * @return cbit.gui.MultiPurposeTextPanel
 */
private MultiPurposeTextPanel getVCMLPane() {
	if (vcmlPane == null) {
		try {
			vcmlPane = new MultiPurposeTextPanel();
			vcmlPane.setAutoCompletionWords(getAutoCompletionWords());
			vcmlPane.setKeywords(getkeywords());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}
	return vcmlPane;
}


/**
 * Return the MathDescription property value.
 * @return cbit.vcell.math.MathDescription
 */
public MathDescription getMathDescription() {
	return ivjMathDescription;
}

/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	exception.printStackTrace(System.out);
}

/**
 * Initializes connections
 */
private void initConnections() throws java.lang.Exception {
	getApplyButton().addActionListener(this);
	getCancelButton().addActionListener(this);
	getVCMLPane().getTextPane().addKeyListener(this);
}

/**
 * Initialize class
 */
private void initialize() {
	try {
		setName("MathDescEditor");
		setLayout(new java.awt.GridBagLayout());
		setSize(981, 242);

		java.awt.GridBagConstraints constraintsApplyButton = new java.awt.GridBagConstraints();
		constraintsApplyButton.gridx = 0; constraintsApplyButton.gridy = 1;
		constraintsApplyButton.insets = new java.awt.Insets(0, 9, 9, 0);
		add(getApplyButton(), constraintsApplyButton);

		java.awt.GridBagConstraints constraintsCancelButton = new java.awt.GridBagConstraints();
		constraintsCancelButton.gridx = 1; constraintsCancelButton.gridy = 1;
		constraintsCancelButton.anchor = java.awt.GridBagConstraints.WEST;
		constraintsCancelButton.insets = new java.awt.Insets(0, 9, 9, 0);
		constraintsCancelButton.fill = java.awt.GridBagConstraints.BOTH;
		constraintsCancelButton.gridwidth = 1;		
		add(getCancelButton(), constraintsCancelButton);
		
		java.awt.GridBagConstraints constraintslineNumberedTextArea1 = new java.awt.GridBagConstraints();
		constraintslineNumberedTextArea1.gridx = 0; constraintslineNumberedTextArea1.gridy = 0;
		constraintslineNumberedTextArea1.gridwidth = 6;
		constraintslineNumberedTextArea1.fill = java.awt.GridBagConstraints.BOTH;
		constraintslineNumberedTextArea1.weightx = 1.0;
		constraintslineNumberedTextArea1.weighty = 1.0;
		constraintslineNumberedTextArea1.insets = new java.awt.Insets(4, 4, 4, 4);
		add(getVCMLPane(), constraintslineNumberedTextArea1);
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
}

/**
 * Method to handle events for the KeyListener interface.
 * @param e java.awt.event.KeyEvent
 */
public void keyPressed(java.awt.event.KeyEvent e) {
	if (getApplyButton().isEnabled()) {
		return;
	}
	if (e.getSource() == getVCMLPane().getTextPane()) {
		int keyCode = e.getKeyCode();
		if (!e.isActionKey() && keyCode != KeyEvent.VK_CONTROL && keyCode != KeyEvent.VK_SHIFT) {
			getApplyButton().setEnabled(true);	
			getCancelButton().setEnabled(true);
		}
	}
}

/**
 * Method to handle events for the KeyListener interface.
 * @param e java.awt.event.KeyEvent
 */
public void keyReleased(java.awt.event.KeyEvent e) {
}


/**
 * Method to handle events for the KeyListener interface.
 * @param e java.awt.event.KeyEvent
 */
public void keyTyped(java.awt.event.KeyEvent e) {
}


/**
 * Set the MathDescription to a new value.
 * @param newValue cbit.vcell.math.MathDescription
 */
public void setMathDescription(MathDescription newValue) {
	if (ivjMathDescription != newValue) {
		try {
			MathDescription oldValue = getMathDescription();
			ivjMathDescription = newValue;			
			getVCMLPane().setText(getMathDescription().getVCML_database());
			getVCMLPane().setCaretPosition(0);
			firePropertyChange("mathDescription", oldValue, newValue);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}
}

public boolean hasUnappliedChanges() {
	return getApplyButton().isEnabled();
}

private static Set<String> getAutoCompletionWords() {
	if (autoCompletionWords.size() == 0) {
		autoCompletionWords.add(VCML.BoundaryXm);
		autoCompletionWords.add(VCML.BoundaryConditionSpec);
		autoCompletionWords.add(VCML.BoundaryConditionValue);
		autoCompletionWords.add(VCML.CompartmentSubDomain);
		autoCompletionWords.add(VCML.Constant);
		autoCompletionWords.add(VCML.Diffusion);
		autoCompletionWords.add(VCML.Action);
		autoCompletionWords.add(VCML.FastInvariant);
		autoCompletionWords.add(VCML.FastRate);
		autoCompletionWords.add(getTemplate_FastSystem());
		autoCompletionWords.add(BoundaryConditionType.NEUMANN_STRING);
		autoCompletionWords.add(VCML.Function);
		autoCompletionWords.add(VCML.VolFunction);
		autoCompletionWords.add(VCML.MemFunction);
		autoCompletionWords.add(VCML.InFlux);
		autoCompletionWords.add(VCML.Initial);
		autoCompletionWords.add(getTemplate_JumpCondition());
		autoCompletionWords.add(getTemplate_JumpProcess());
		autoCompletionWords.add(VCML.MathDescription);
		autoCompletionWords.add(VCML.MembraneRate);
		autoCompletionWords.add(VCML.MembraneRegionVariable);
		autoCompletionWords.add(VCML.MembraneSubDomain);
		autoCompletionWords.add(VCML.Name);		// for 'name' of MembraneSubdomain
		autoCompletionWords.add(VCML.MembraneVariable);
		autoCompletionWords.add(getTemplate_OdeEquation());
		autoCompletionWords.add(VCML.OutFlux);
		autoCompletionWords.add(getTemplate_PdeEquation());
		autoCompletionWords.add(BoundaryConditionType.PERIODIC_STRING);
		autoCompletionWords.add(VCML.Priority);
		autoCompletionWords.add(VCML.ProbabilityRate);
		autoCompletionWords.add(VCML.Rate);
		autoCompletionWords.add(VCML.StochVolVariable);
		autoCompletionWords.add(VCML.UniformRate);
		autoCompletionWords.add(VCML.Value);
		autoCompletionWords.add(VCML.VarIniCount_Old);
		autoCompletionWords.add(VCML.VarIniCount);
		autoCompletionWords.add(VCML.VarIniPoissonExpectedCount);
		autoCompletionWords.add(VCML.VelocityX);
		autoCompletionWords.add(VCML.VelocityY);
		autoCompletionWords.add(VCML.VelocityZ);
		autoCompletionWords.add(VCML.GradientX);
		autoCompletionWords.add(VCML.GradientY);
		autoCompletionWords.add(VCML.GradientZ);
		autoCompletionWords.add(VCML.VolumeRate);
		autoCompletionWords.add(VCML.VolumeRegionVariable);
		autoCompletionWords.add(VCML.VolumeVariable);
		autoCompletionWords.add(VCML.Steady);
		autoCompletionWords.add(getTemplate_Event());
		autoCompletionWords.add(VCML.Delay);
		autoCompletionWords.add(VCML.Trigger);
		autoCompletionWords.add(VCML.Duration);
		autoCompletionWords.add(VCML.EventAssignment);
		autoCompletionWords.add(VCML.UseValuesFromTriggerTime);
		autoCompletionWords.add(getTemplate_VolumeRandomVariable());
		autoCompletionWords.add(getTemplate_MembraneRandomVariable());
		autoCompletionWords.add(getTemplate_UniformDistribution());
		autoCompletionWords.add(VCML.RandomVariable_Seed);
		autoCompletionWords.add(VCML.UniformDistribution_Minimum);
		autoCompletionWords.add(VCML.UniformDistribution_Maximum);
		autoCompletionWords.add(getTemplate_GaussianDistribution());
		autoCompletionWords.add(VCML.GaussianDistribution_Mean);
		autoCompletionWords.add(VCML.GaussianDistribution_StandardDeviation);
		
		autoCompletionWords.add(VCML.VolumeParticleObservable);
		autoCompletionWords.add(VCML.VolumeParticleSpeciesPattern);
		autoCompletionWords.add(VCML.ParticleDistribution);
		autoCompletionWords.add(VCML.VolumeParticleSpeciesPatterns);
		autoCompletionWords.add(VCML.ParticleMolecularType);
		autoCompletionWords.add(VCML.ParticleMolecularComponent);
		autoCompletionWords.add(VCML.ParticleMolecularTypePattern);
		autoCompletionWords.add(VCML.ParticleComponentStatePattern);
		autoCompletionWords.add(VCML.ParticleComponentBondPattern);
		autoCompletionWords.add(VCML.ParticleComponentAllowableState);
	
		autoCompletionWords.add(VCML.VolumeParticleVariable);	
		autoCompletionWords.add(VCML.MembraneParticleVariable);
		
		autoCompletionWords.add(VCML.ParticleJumpProcess);	
		autoCompletionWords.add(VCML.InteractionRadius);
		autoCompletionWords.add(getTemplate_ParticleJumpProcess());
		autoCompletionWords.add(VCML.DestroyParticle);
		autoCompletionWords.add(VCML.CreateParticle);
		autoCompletionWords.add(VCML.SelectedParticle);		
		autoCompletionWords.add(VCML.ParticleProperties);
		autoCompletionWords.add(getTemplate_ParticleProperties());		
		autoCompletionWords.add(VCML.ParticleInitialCount);
		autoCompletionWords.add(VCML.ParticleInitialConcentration);
		autoCompletionWords.add(VCML.ParticleCount);
		autoCompletionWords.add(VCML.ParticleLocationX);
		autoCompletionWords.add(VCML.ParticleLocationY);
		autoCompletionWords.add(VCML.ParticleLocationZ);
		
		autoCompletionWords.add(VCML.PostProcessingBlock);
		autoCompletionWords.add(VCML.ExplicitDataGenerator);
		autoCompletionWords.add(VCML.ProjectionDataGenerator);
		autoCompletionWords.add(VCML.ProjectionAxis);
		autoCompletionWords.add(VCML.ProjectionOperation);
		
		autoCompletionWords.add(VCML.ConvolutionDataGenerator);
		autoCompletionWords.add(VCML.Kernel);
		autoCompletionWords.add(VCML.KernelGaussian);
		autoCompletionWords.add(VCML.KernelGaussianSigmaXY);
		autoCompletionWords.add(VCML.KernelGaussianSigmaZ);

	}
	
	return autoCompletionWords;
}

private static String getTemplate_OdeEquation() {	
	return VCML.OdeEquation + " varName " + VCML.BeginBlock + "\n" 
		+ "\t\t" + VCML.Rate + " 0.0;\n" 
		+ "\t\t" + VCML.Initial + " 0.0;\n"
		+ "\t" + VCML.EndBlock + "\n";
}

private static String getTemplate_PdeEquation() {	
	return VCML.PdeEquation + " varName " + VCML.BeginBlock + "\n"
		+ "\t\t" + VCML.BoundaryXm + " 0.0;\n"
		+ "\t\t" + VCML.BoundaryXp + " 0.0;\n"
		+ "\n\t\t" + VCML.BoundaryConditionValue + "\tMembraneSubdomainName \t0.0;" + "\n\n" 
		+ "\t\t" + VCML.Rate + " 0.0;\n" 
		+ "\t\t" + VCML.Diffusion + " 0.0;\n" 
		+ "\t\t" + VCML.Initial + " 0.0;\n"
		+ "\t" + VCML.EndBlock + "\n";
}

private static String getTemplate_JumpCondition() {	
	return VCML.JumpCondition + " varName " + VCML.BeginBlock + "\n" 
		+ "\t\t" + VCML.InFlux + " 0.0;\n" 
		+ "\t\t" + VCML.OutFlux + " 0.0;\n" 
		+ "\t" + VCML.EndBlock + "\n";
}

private static String getTemplate_FastSystem() {	
	return VCML.FastSystem + " " + VCML.BeginBlock + "\n" 
		+ "\t\t" + VCML.FastInvariant + " 0.0;\n" 
		+ "\t\t" + VCML.FastRate + " 0.0;\n" 
		+ "\t" + VCML.EndBlock + "\n";
}

private static String getTemplate_JumpProcess() {	
	return VCML.JumpProcess + " processName " + VCML.BeginBlock + "\n" 
		+ "\t\t" + VCML.ProbabilityRate + " 0.0;\n" 
		+ "\t\t Effect 0.0;\n" 
		+ "\t" + VCML.EndBlock + "\n";
}

private static String getTemplate_ParticleJumpProcess() {	
	return VCML.ParticleJumpProcess + " processName " + VCML.BeginBlock + "\n" 
		+ "\t\t" + VCML.MacroscopicRateConstant + " 0.0;\n" 
		+ "\t\t" + VCML.Action + " Var " + VCML.DestroyParticle + "\n" 
		+ "\t" + VCML.EndBlock + "\n";
}

private static String getTemplate_ParticleProperties() {	
	return VCML.ParticleProperties + " varName " + VCML.BeginBlock + "\n" 
		+ "\t\t" + VCML.ParticleInitialCount + " " + VCML.BeginBlock + "\n" 
		+ "\t\t\t" + VCML.ParticleCount + " 1.0; \n"
		+ "\t\t\t" + VCML.ParticleLocationX + " u; \n"
		+ "\t\t\t" + VCML.ParticleLocationY + " u; \n"
		+ "\t\t\t" + VCML.EndBlock + "\n"
		+ "\t\t" + VCML.ParticleDiffusion + " 1.0; \n" 
		+ "\t" + VCML.EndBlock + "\n";
}

private static String getTemplate_Event() {	
	return VCML.Event + " event0 " + VCML.BeginBlock + "\n"
		+ "\t" + VCML.Trigger + " 0.0;\n"
		+ "\t" + VCML.Delay + " " +  VCML.BeginBlock + "\n"
		+ "\t\t" + VCML.UseValuesFromTriggerTime + " true\n"
		+ "\t\t" + VCML.Duration + " 0.0;\n"
		+ "\t" + VCML.EndBlock + "\n"
		+ "\t" + VCML.EventAssignment + " varname 0.0;\n" 
		+ VCML.EndBlock + "\n";
}

private static String getTemplate_VolumeRandomVariable() {	
	return VCML.VolumeRandomVariable + " rand0 " + VCML.BeginBlock + "\n"
		+ "\t" + VCML.RandomVariable_Seed + " 5;\n"
		+ "\t" + getTemplate_UniformDistribution()
		+ VCML.EndBlock + "\n";
}

private static String getTemplate_MembraneRandomVariable() {	
	return VCML.MembraneRandomVariable + " rand0 " + VCML.BeginBlock + "\n"
		+ "\t" + VCML.RandomVariable_Seed + " 5;\n"
		+ "\t" + getTemplate_UniformDistribution()
		+ VCML.EndBlock + "\n";
}

private static String getTemplate_UniformDistribution() {	
		return VCML.UniformDistribution + " " +  VCML.BeginBlock + "\n"
		+ "\t\t" + VCML.UniformDistribution_Minimum + " 0.0;\n"
		+ "\t\t" + VCML.UniformDistribution_Maximum + " 1.0;\n"
		+ "\t" + VCML.EndBlock + "\n";
}

private static String getTemplate_GaussianDistribution() {	
	return VCML.GaussianDistribution + " " +  VCML.BeginBlock + "\n"
	+ "\t\t" + VCML.GaussianDistribution_Mean + " 0.0;\n"
	+ "\t\t" + VCML.GaussianDistribution_StandardDeviation + " 1.0;\n"
	+ "\t" + VCML.EndBlock + "\n";
}

public JMenu getEditMenu() {
	return getVCMLPane().createEditMenu();
}


public static Set<String> getkeywords() {
	if (keywords.size() == 0) {
		keywords.add(VCML.BoundaryXm);
		keywords.add(VCML.BoundaryXp);
		keywords.add(VCML.BoundaryYm);
		keywords.add(VCML.BoundaryYp);
		keywords.add(VCML.BoundaryZm);
		keywords.add(VCML.BoundaryZp);
		keywords.add(VCML.BoundaryConditionSpec);
		keywords.add(VCML.BoundaryConditionValue);
		keywords.add(VCML.CompartmentSubDomain);
		keywords.add(VCML.Constant);
		keywords.add(VCML.Diffusion);
		keywords.add(VCML.Action);
		keywords.add(VCML.Exact);
		keywords.add(VCML.FastInvariant);
		keywords.add(VCML.FastRate);
		keywords.add(VCML.FastSystem);
		keywords.add(BoundaryConditionType.NEUMANN_STRING);
		keywords.add(VCML.Function);
		keywords.add(VCML.VolFunction);
		keywords.add(VCML.MemFunction);
		keywords.add(VCML.InFlux);
		keywords.add(VCML.Initial);
		keywords.add(VCML.JumpCondition);
		keywords.add(VCML.JumpProcess);
		keywords.add(VCML.MathDescription);
		keywords.add(VCML.MembraneRate);
		keywords.add(VCML.MembraneRegionEquation);
		keywords.add(VCML.MembraneRegionVariable);
		keywords.add(VCML.MembraneSubDomain);
		keywords.add(VCML.Name);
		keywords.add(VCML.MembraneVariable);
		keywords.add(VCML.OdeEquation);
		keywords.add(VCML.OutFlux);
		keywords.add(VCML.PdeEquation);
		keywords.add(BoundaryConditionType.PERIODIC_STRING);
		keywords.add(VCML.Priority);
		keywords.add(VCML.ProbabilityRate);
		keywords.add(VCML.Rate);
		keywords.add(VCML.StochVolVariable);
		keywords.add(VCML.UniformRate);
		keywords.add(VCML.Value);
		keywords.add(VCML.VarIniCount_Old);
		keywords.add(VCML.VarIniCount);
		keywords.add(VCML.VarIniPoissonExpectedCount);
		keywords.add(VCML.VelocityX);
		keywords.add(VCML.VelocityY);
		keywords.add(VCML.VelocityZ);
		keywords.add(VCML.GradientX);
		keywords.add(VCML.GradientY);
		keywords.add(VCML.GradientZ);
		keywords.add(VCML.VolumeRate);
		keywords.add(VCML.VolumeRegionEquation);
		keywords.add(VCML.VolumeRegionVariable);
		keywords.add(VCML.VolumeVariable);
		keywords.add(VCML.Steady);

		keywords.add(VCML.Event);
		keywords.add(VCML.Delay);
		keywords.add(VCML.Trigger);
		keywords.add(VCML.Duration);
		keywords.add(VCML.EventAssignment);
		keywords.add(VCML.UseValuesFromTriggerTime);

		keywords.add(VCML.VolumeRandomVariable);
		keywords.add(VCML.MembraneRandomVariable);
		keywords.add(VCML.RandomVariable_Seed);
		keywords.add(VCML.UniformDistribution);
		keywords.add(VCML.UniformDistribution_Minimum);
		keywords.add(VCML.UniformDistribution_Maximum);
		keywords.add(VCML.GaussianDistribution);
		keywords.add(VCML.GaussianDistribution_Mean);
		keywords.add(VCML.GaussianDistribution_StandardDeviation);
		
		keywords.add(VCML.VolumeParticleObservable);
		keywords.add(VCML.VolumeParticleSpeciesPattern);
		keywords.add(VCML.ParticleDistribution);
		keywords.add(VCML.VolumeParticleSpeciesPatterns);
		keywords.add(VCML.ParticleMolecularType);
		keywords.add(VCML.ParticleMolecularComponent);
		keywords.add(VCML.ParticleMolecularTypePattern);
		keywords.add(VCML.ParticleComponentStatePattern);
		keywords.add(VCML.ParticleComponentBondPattern);
		keywords.add(VCML.ParticleComponentAllowableState);

		keywords.add(VCML.VolumeParticleVariable);
		keywords.add(VCML.MembraneParticleVariable);
		keywords.add(VCML.ParticleJumpProcess);
		keywords.add(VCML.MacroscopicRateConstant);
		keywords.add(VCML.InteractionRadius);
		keywords.add(VCML.DestroyParticle);
		keywords.add(VCML.CreateParticle);
		keywords.add(VCML.SelectedParticle);
		keywords.add(VCML.ParticleProperties);
		keywords.add(VCML.ParticleInitialCount);
		keywords.add(VCML.ParticleInitialConcentration);
		keywords.add(VCML.ParticleCount);
		keywords.add(VCML.ParticleLocationX);
		keywords.add(VCML.ParticleLocationY);
		keywords.add(VCML.ParticleLocationZ);
		keywords.add(VCML.ParticleDiffusion);
		
		keywords.add(VCML.PostProcessingBlock);
		keywords.add(VCML.ExplicitDataGenerator);
		keywords.add(VCML.ProjectionDataGenerator);
		keywords.add(VCML.ProjectionAxis);
		keywords.add(VCML.ProjectionOperation);
		keywords.add(VCML.ConvolutionDataGenerator);
		keywords.add(VCML.Kernel);
		keywords.add(VCML.KernelGaussian);
		keywords.add(VCML.KernelGaussianSigmaXY);
		keywords.add(VCML.KernelGaussianSigmaZ);
	}
	 return keywords;
}
}
