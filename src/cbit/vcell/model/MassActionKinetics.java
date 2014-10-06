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

import org.vcell.util.Matchable;

import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionException;
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class MassActionKinetics extends DistributedKinetics {
/**
 * MassActionKinetics constructor comment.
 * @param name java.lang.String
 * @param exp cbit.vcell.parser.Expression
 */
public MassActionKinetics(ReactionStep reactionStep) throws ExpressionException {
	super(KineticsDescription.MassAction.getName(),reactionStep);
	try {
		KineticsParameter rateParm = new KineticsParameter(getDefaultParameterName(ROLE_ReactionRate),new Expression(0.0),ROLE_ReactionRate,null);
		KineticsParameter currentParm = new KineticsParameter(getDefaultParameterName(ROLE_CurrentDensity),new Expression(0.0),ROLE_CurrentDensity,null);
		KineticsParameter kf = new KineticsParameter(getDefaultParameterName(ROLE_KForward),new Expression(0.0),ROLE_KForward,null);
		KineticsParameter kr = new KineticsParameter(getDefaultParameterName(ROLE_KReverse),new Expression(0.0),ROLE_KReverse,null);
		KineticsParameter netChargeValence = new KineticsParameter(getDefaultParameterName(ROLE_NetChargeValence),new Expression(1.0),ROLE_NetChargeValence,null);

		if (reactionStep.getStructure() instanceof Membrane){
			setKineticsParameters(new KineticsParameter[] { rateParm, currentParm, netChargeValence, kf, kr });
		}else{
			setKineticsParameters(new KineticsParameter[] { rateParm, kf, kr });
		}
		updateGeneratedExpressions();
		refreshUnits();
	}catch (PropertyVetoException e){
		e.printStackTrace(System.out);
		throw new RuntimeException("unexpected exception: "+e.getMessage());
	}
}
/**
 * Checks for internal representation of objects, not keys from database
 * @return boolean
 * @param obj java.lang.Object
 */
public boolean compareEqual(Matchable obj) {
	if (obj == this){
		return true;
	}
	if (!(obj instanceof MassActionKinetics)){
		return false;
	}
	
	MassActionKinetics mak = (MassActionKinetics)obj;

	if (!compareEqual0(mak)){
		return false;
	}
	
	return true;
}
/**
 * Insert the method's description here.
 * Creation date: (8/6/2002 3:37:07 PM)
 * @return cbit.vcell.model.KineticsParameter
 */
public KineticsParameter getForwardRateParameter() {
	return getKineticsParameterFromRole(ROLE_KForward);
}
/**
 * Insert the method's description here.
 * Creation date: (8/6/2002 9:52:55 AM)
 * @return cbit.vcell.model.KineticsDescription
 */
public KineticsDescription getKineticsDescription() {
	return KineticsDescription.MassAction;
}
/**
 * Insert the method's description here.
 * Creation date: (8/6/2002 3:37:07 PM)
 * @return cbit.vcell.model.KineticsParameter
 */
public KineticsParameter getReverseRateParameter() {
	return getKineticsParameterFromRole(ROLE_KReverse);
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
		
		Kinetics.KineticsParameter rateParm = getReactionRateParameter();
		Kinetics.KineticsParameter currentDensityParm = getCurrentDensityParameter();
		Kinetics.KineticsParameter forwardRateParm = getForwardRateParameter();
		Kinetics.KineticsParameter reverseRateParm = getReverseRateParameter();
		// since units for kinetic parameters are set from model's unit system, if model is null (possible when model is not yet set on reactionStep when reading from XML)
		// don't worry about setting units on kinetic parameters. Call this method when model is set on reactionStep (rebindToModel()). 
		Model model = getReactionStep().getModel();
		if (model != null) {
			ModelUnitSystem modelUnitSystem = model.getUnitSystem();
			if (getReactionStep().getStructure() instanceof Membrane){
				rateParm.setUnitDefinition(modelUnitSystem.getMembraneReactionRateUnit());
				if (currentDensityParm!=null){
					currentDensityParm.setUnitDefinition(modelUnitSystem.getCurrentDensityUnit());
				}
				KineticsParameter chargeValenceParm = getChargeValenceParameter();
				if (chargeValenceParm!=null){
					chargeValenceParm.setUnitDefinition(modelUnitSystem.getInstance_DIMENSIONLESS());
				}
			}else if (getReactionStep().getStructure() instanceof Feature){
				rateParm.setUnitDefinition(modelUnitSystem.getVolumeReactionRateUnit());
			}else{
				throw new RuntimeException("unexpected structure type "+getReactionStep().getStructure()+" in MassActionKinetics.refreshUnits()");
			}
			
			cbit.vcell.units.VCUnitDefinition kfUnits = rateParm.getUnitDefinition();
			cbit.vcell.units.VCUnitDefinition krUnits = rateParm.getUnitDefinition();
			ReactionParticipant reactionParticipants[] = getReactionStep().getReactionParticipants();
			for (int i = 0; i < reactionParticipants.length; i++){
				if (reactionParticipants[i] instanceof Reactant){
					cbit.vcell.units.VCUnitDefinition reactantUnit = reactionParticipants[i].getSpeciesContext().getUnitDefinition();
					if (reactionParticipants[i].getStoichiometry()!=1){
						reactantUnit = reactantUnit.raiseTo(new ucar.units.RationalNumber(reactionParticipants[i].getStoichiometry()));
					}
					kfUnits = kfUnits.divideBy(reactantUnit);
				}else if (reactionParticipants[i] instanceof Product){
					cbit.vcell.units.VCUnitDefinition productUnit = reactionParticipants[i].getSpeciesContext().getUnitDefinition();
					if (reactionParticipants[i].getStoichiometry()!=1){
						productUnit = productUnit.raiseTo(new ucar.units.RationalNumber(reactionParticipants[i].getStoichiometry()));
					}
					krUnits = krUnits.divideBy(productUnit);
				}
			}
			if (forwardRateParm!=null && !kfUnits.compareEqual(forwardRateParm.getUnitDefinition())){
				forwardRateParm.setUnitDefinition(kfUnits);
			}
			if (reverseRateParm!=null && !krUnits.compareEqual(reverseRateParm.getUnitDefinition())){
				reverseRateParm.setUnitDefinition(krUnits);
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
protected void updateGeneratedExpressions() throws cbit.vcell.parser.ExpressionException, PropertyVetoException {
	KineticsParameter rateParm = getKineticsParameterFromRole(ROLE_ReactionRate);
	KineticsParameter currentParm = getKineticsParameterFromRole(ROLE_CurrentDensity);
	KineticsParameter kf = getKineticsParameterFromRole(ROLE_KForward);
	KineticsParameter kr = getKineticsParameterFromRole(ROLE_KReverse);
	if (currentParm==null && rateParm==null){
		return;
	}
	
	ReactionParticipant rp_Array[] = getReactionStep().getReactionParticipants();
	Expression kf_exp = getSymbolExpression(kf);
	Expression kr_exp = getSymbolExpression(kr);
	int reactantCount = 0;
	int productCount = 0;
	for (int i = 0; i < rp_Array.length; i++) {
		Expression term = null;
		Expression speciesContext = getSymbolExpression(rp_Array[i].getSpeciesContext());
		int stoichiometry = rp_Array[i].getStoichiometry();
		if (rp_Array[i] instanceof Reactant){
			reactantCount++;
			if (stoichiometry < 1){
				throw new ExpressionException("reactant must have stoichiometry of at least 1");
			}else if (stoichiometry == 1){
				term = speciesContext;
			}else{
				term = Expression.power(speciesContext,new Expression(stoichiometry));
			}	
			kf_exp = Expression.mult(kf_exp,term);	
		}else if (rp_Array[i] instanceof Product){
			productCount++;
			if (stoichiometry < 1){
				throw new RuntimeException("product must have stoichiometry of at least 1");
			}else if (stoichiometry == 1){
				term = speciesContext;
			}else{
				term = Expression.power(speciesContext,new Expression(stoichiometry));
			}	
			kr_exp = Expression.mult(kr_exp,term);	
		}	
	}

	Expression newRateExp = null;
	if (reactantCount > 0 && productCount > 0){
		newRateExp = Expression.add(kf_exp,Expression.negate(kr_exp));
	}else if (reactantCount > 0){
		newRateExp = kf_exp;
	}else if (productCount > 0){
		newRateExp = Expression.negate(kr_exp);
	}else{
		newRateExp = new Expression(0.0);
	}
	rateParm.setExpression(newRateExp);
	
	
	// SECONDARY CURRENT DENSITY
	// update from reaction rate
	updateInwardCurrentDensityFromReactionRate();
}
}
