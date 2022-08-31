/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.geometry;
import cbit.vcell.parser.*;
import org.vcell.util.Compare;
import org.vcell.util.Matchable;
import org.vcell.util.document.KeyValue;

/**
 * This class was generated by a SmartGuide.
 * 
 */
public class AnalyticSubVolume extends SubVolume {
	private Expression exp = null;

	//
	// the following fields are used for evaluating the inside/outside function "isInside()"
	//
	private double valueArray[] = new double[3];

/**
 * MathDescription constructor comment.
 */
public AnalyticSubVolume(KeyValue key, String subDomainName, Expression exp, int handle) throws ExpressionException {
	super(key,subDomainName, handle);
	setExpression(exp);
}

public AnalyticSubVolume(AnalyticSubVolume oldAnalyticSubVolume){
	super(oldAnalyticSubVolume.getKey(),oldAnalyticSubVolume.getName(), oldAnalyticSubVolume.getHandle());
	try {
		setExpression(new Expression(oldAnalyticSubVolume.getExpression()));
	} catch (ExpressionBindingException e) {
		e.printStackTrace();
		throw new RuntimeException(e.getMessage(), e);
	}
}
/**
 * MathDescription constructor comment.
 */
public AnalyticSubVolume(String subDomainName, Expression exp) throws ExpressionException {
	super(null,subDomainName, -1);
	setExpression(exp);
}


/**
 * This method was created in VisualAge.
 */
private void bind() throws ExpressionBindingException {
	if (exp != null){
		exp.bindExpression(new SimpleSymbolTable(new String[] { "x", "y", "z" } ));
	}
}


/**
 * This method was created in VisualAge.
 * @return boolean
 * @param obj java.lang.Object
 */
public boolean compareEqual(Matchable obj) {
	if (!compareEqual0(obj)){
		return false;
	}
	if (!(obj instanceof AnalyticSubVolume)){
		return false;
	}
	AnalyticSubVolume sv = (AnalyticSubVolume)obj;

	if ((exp==null && sv.exp!=null) || (exp!=null && sv.exp==null)){
		return false;
	}
	if (!ExpressionUtils.functionallyEquivalent(exp,sv.exp)){
		return false;
	}

	return true;
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.parser.Expression
 */
public Expression getExpression() {
	return exp;
}


/**
 * This method was created in VisualAge.
 * @return boolean
 * @param x double
 * @param y double
 * @param z double
 */
public boolean isInside(double x,double y, double z, GeometrySpec geometrySpec) throws GeometryException, ExpressionException {
	if (exp==null){
		throw new GeometryException("expression for analytic geometry is not defined");
	}
	valueArray[0] = x;		// x
	valueArray[1] = y;		// y
	valueArray[2] = z;		// z
	double value = exp.evaluateVector(valueArray);
	return (value!=0);
}


/**
 * Insert the method's description here.
 * Creation date: (4/2/01 5:39:04 PM)
 */
public void rebind() throws ExpressionException {
	//
	// make sure x,y,z are pointing to the correct instances (each are singletons per-JVM)
	// such that they have the right variableIndex (it's a transient property and serialization
	// sets it to zero
	//
	bind();
}


/**
 * This method was created in VisualAge.
 * @param exp cbit.vcell.parser.Expression
 */
public void setExpression(Expression exp) throws ExpressionBindingException {
	Expression oldExp = this.exp;
	this.exp = exp;
	bind();
	firePropertyChange("expression",oldExp,exp);
//	if (geometry!=null){
//		geometry.forceNotifyObservers(this);
//	}
}


}
