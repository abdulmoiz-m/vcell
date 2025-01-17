/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.model;

import java.beans.PropertyVetoException;
import java.util.List;

import org.vcell.util.*;
import org.vcell.util.Issue.IssueCategory;

import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionException;

/**
 * This class was generated by a SmartGuide.
 * 
 */
public class HMM_IRRKinetics extends DistributedKinetics {

public HMM_IRRKinetics(SimpleReaction simpleReaction) throws ExpressionException {
	super(KineticsDescription.HMM_irreversible.getName(),simpleReaction);
	try {
		KineticsParameter rateParm = new KineticsParameter(getDefaultParameterName(ROLE_ReactionRate),new Expression(0.0),ROLE_ReactionRate,null);
		KineticsParameter currentParm = new KineticsParameter(getDefaultParameterName(ROLE_CurrentDensity),new Expression(0.0),ROLE_CurrentDensity,null);
		KineticsParameter kmParm = new KineticsParameter(getDefaultParameterName(ROLE_Km),new Expression(0.0),ROLE_Km,null);
		KineticsParameter vMaxParm = new KineticsParameter(getDefaultParameterName(ROLE_Vmax),new Expression(0.0),ROLE_Vmax,null);
		KineticsParameter netChargeValence = new KineticsParameter(getDefaultParameterName(ROLE_NetChargeValence),new Expression(1.0),ROLE_NetChargeValence,null);

		if (simpleReaction.getStructure() instanceof Membrane){
			setKineticsParameters(new KineticsParameter[] { rateParm, currentParm, netChargeValence, kmParm, vMaxParm });
		}else{
			setKineticsParameters(new KineticsParameter[] { rateParm, kmParm, vMaxParm });
		}
		
		updateGeneratedExpressions();
		refreshUnits();
	}catch (PropertyVetoException e){
		e.printStackTrace(System.out);
		throw new RuntimeException("unexpected exception: "+e.getMessage());
	}

}

	@Override
	public boolean compareEqual(Matchable obj) {
		if (obj == this){
			return true;
		}
		if (!(obj instanceof HMM_IRRKinetics)){
			return false;
		}

		HMM_IRRKinetics hmm = (HMM_IRRKinetics)obj;

		if (!compareEqual0(hmm)){
			return false;
		}

		return true;
	}

	@Override
	public boolean relate(Relatable obj, RelationVisitor rv) {
		if (obj == this){
			return true;
		}
		if (!(obj instanceof HMM_IRRKinetics)){
			return false;
		}

		HMM_IRRKinetics hmm = (HMM_IRRKinetics)obj;

		if (!relate0(hmm, rv)){
			return false;
		}

		return true;
	}
/**
 * Insert the method's description here.
 * Creation date: (5/12/2004 3:23:27 PM)
 * @return cbit.util.Issue[]
 */
@Override
public void gatherIssues(IssueContext issueContext, List<Issue> issueList) {

	super.gatherIssues(issueContext, issueList);

	//
	// check for correct number of reactants
	//
	ReactionParticipant reactionParticipants[] = getReactionStep().getReactionParticipants();
	int reactantCount = 0;
	for (int i = 0; i < reactionParticipants.length; i++){
		if (reactionParticipants[i] instanceof Reactant){
			reactantCount++;
		}
	}
	if (reactantCount!=1){
		issueList.add(new Issue(getReactionStep(),issueContext,IssueCategory.KineticsApplicability,"HMM_IRRKinetics must have exactly one reactant",Issue.SEVERITY_ERROR));
	}
}

/**
 * Insert the method's description here.
 * Creation date: (8/6/2002 9:52:55 AM)
 * @return cbit.vcell.model.KineticsDescription
 */
public KineticsDescription getKineticsDescription() {
	return KineticsDescription.HMM_irreversible;
}
/**
 * Gets the kineticsParameters index property (cbit.vcell.model.KineticsParameter) value.
 * @return The kineticsParameters property value.
 * @param index The index value into the property array.
 * @see #setKineticsParameters
 */
public KineticsParameter getKineticsParameterFromRole(int role) {
	if (role == ROLE_KmFwd) {
		role = ROLE_Km;
	} else if (role == ROLE_VmaxFwd) {
		role = ROLE_Vmax;
	}

	return super.getKineticsParameterFromRole(role);	
}
/**
 * Insert the method's description here.
 * Creation date: (8/6/2002 3:48:16 PM)
 * @return cbit.vcell.model.KineticsParameter
 */
public KineticsParameter getKmParameter() {
	return getKineticsParameterFromRole(ROLE_Km);
}
/**
 * Insert the method's description here.
 * Creation date: (8/6/2002 3:48:16 PM)
 * @return cbit.vcell.model.KineticsParameter
 */
public KineticsParameter getVmaxParameter() {
	return getKineticsParameterFromRole(ROLE_Vmax);
}
/**
 * Insert the method's description here.
 * Creation date: (3/31/2004 3:56:05 PM)
 */
protected void refreshUnits() {
	if (bRefreshingUnits){
		return;
	}
	try {
		bRefreshingUnits=true;
		
		Reactant R0 = null;
		int reactantCount = 0;
		ReactionParticipant reactionParticipants[] = getReactionStep().getReactionParticipants();
		for (int i = 0; i < reactionParticipants.length; i++){
			if (reactionParticipants[i] instanceof Reactant){
				reactantCount++;
				R0 = (Reactant)reactionParticipants[i];
			}
		}

		Kinetics.KineticsParameter rateParm = getReactionRateParameter();
		Kinetics.KineticsParameter currentDensityParm = getCurrentDensityParameter();
		Kinetics.KineticsParameter kmParm = getKmParameter();
		Kinetics.KineticsParameter vmaxParm = getVmaxParameter();
		Model model = getReactionStep().getModel();
		if (model != null) {
			ModelUnitSystem modelUnitSystem = model.getUnitSystem();
			if (getReactionStep().getStructure() instanceof Membrane){
				if (rateParm!=null){
					rateParm.setUnitDefinition(modelUnitSystem.getMembraneReactionRateUnit());
				}
				if (currentDensityParm!=null){
					currentDensityParm.setUnitDefinition(modelUnitSystem.getCurrentDensityUnit());
				}
				KineticsParameter chargeValenceParm = getChargeValenceParameter();
				if (chargeValenceParm!=null){
					chargeValenceParm.setUnitDefinition(modelUnitSystem.getInstance_DIMENSIONLESS());
				}
				if (vmaxParm!=null){
					vmaxParm.setUnitDefinition(modelUnitSystem.getMembraneReactionRateUnit());
				}
				if (kmParm!=null){
					if (R0!=null){
						kmParm.setUnitDefinition(R0.getSpeciesContext().getUnitDefinition());
					}else{
						kmParm.setUnitDefinition(modelUnitSystem.getMembraneConcentrationUnit());
					}
				}
			}else{
				if (rateParm!=null){
					rateParm.setUnitDefinition(modelUnitSystem.getVolumeReactionRateUnit());
				}
				if (vmaxParm!=null){
					vmaxParm.setUnitDefinition(modelUnitSystem.getVolumeReactionRateUnit());
				}
				if (kmParm!=null){
					if (R0!=null){
						kmParm.setUnitDefinition(R0.getSpeciesContext().getUnitDefinition());
					}else{
						kmParm.setUnitDefinition(modelUnitSystem.getVolumeConcentrationUnit());
					}
				}
			}
		}
	}finally{
		bRefreshingUnits=false;
	}
}
/**
 * Insert the method's description here.
 * Creation date: (10/19/2003 12:05:14 AM)
 * @exception cbit.vcell.parser.ExpressionException The exception description.
 */
protected void updateGeneratedExpressions() throws ExpressionException, PropertyVetoException {
	KineticsParameter rateParm = getKineticsParameterFromRole(ROLE_ReactionRate);
	KineticsParameter currentParm = getKineticsParameterFromRole(ROLE_CurrentDensity);
	if (currentParm==null && rateParm==null){
		return;
	}

	KineticsParameter km = getKineticsParameterFromRole(ROLE_Km);
	KineticsParameter vMax = getKineticsParameterFromRole(ROLE_Vmax);
	
	ReactionParticipant reactionParticipants[] = getReactionStep().getReactionParticipants();
	Reactant R0 = null;
	for (int i = 0; i < reactionParticipants.length; i++){
		if (reactionParticipants[i] instanceof Reactant){
			R0 = (Reactant)reactionParticipants[i];
			break;
		}
	}
	if (R0==null){
		System.out.println("HMM_IRRKinetics.updateGeneratedExpressions(): HMM_IRRKinetics must have exactly one reactant");
		return;
	}
	
	// PRIMARY REACTION RATE
	//		new Expression("A0*R0/(A1+R0)"),
	Expression vMax_exp = getSymbolExpression(vMax);
	Expression km_exp = getSymbolExpression(km);
	Expression R0_exp = getSymbolExpression(R0.getSpeciesContext());
	
	Expression newRateExp = Expression.div(Expression.mult(vMax_exp, R0_exp), Expression.add(km_exp, R0_exp));
	rateParm.setExpression(newRateExp);

	
	// SECONDARY CURRENT DENSITY
	// update from reaction rate
	updateInwardCurrentDensityFromReactionRate();
}
}
