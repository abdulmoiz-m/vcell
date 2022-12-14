/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.math;
import java.io.Serializable;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vcell.util.CommentStringTokenizer;
import org.vcell.util.Compare;
import org.vcell.util.Issue.IssueOrigin;
import org.vcell.util.Matchable;
import org.vcell.util.Token;

import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionException;
/**
 * This class was generated by a SmartGuide.
 * 
 */
@SuppressWarnings("serial")
public abstract class SubDomain extends CommentedBlockObject implements Serializable, Matchable, IssueOrigin {
	protected static final Logger LG = LogManager.getLogger(SubDomain.class);


	public abstract void getAllExpressions(List<Expression> expressionList, MathDescription mathDescription);

	public void getAllExpressions0(List<Expression> expressionList, MathDescription mathDescription){
		if (getFastSystem()!=null) expressionList.addAll(Arrays.asList(getFastSystem().getExpressions()));
		for (Equation equation : getEquationCollection()){
			expressionList.addAll(equation.getExpressions(mathDescription));
		}
		for (JumpProcess jumpProcess : getJumpProcesses()){
			expressionList.addAll(Arrays.asList(jumpProcess.getExpressions()));
		}
		for (ParticleJumpProcess particleJumpProcess : getParticleJumpProcesses()){
			expressionList.addAll(Arrays.asList(particleJumpProcess.getExpressions()));
		}
		for (VarIniCondition varIniCondition : getVarIniConditions()){
			expressionList.add(varIniCondition.getIniVal());
		}
	}

	/**
	 * parse tokens into expression, bind to math description 
	 * @param mathDesc non null
	 * @param tokens non null
	 * @return new Expression
	 * @throws MathException general exception
	 * @throws ExpressionException can't be parsed
	 */
	protected static Expression parseAndBind(MathDescription mathDesc, CommentStringTokenizer tokens)
			throws MathException, ExpressionException {
				Expression rval = MathFunctionDefinitions.fixFunctionSyntax(tokens);
				try{
					rval.bindExpression(mathDesc);
				}catch(Exception ex){
					LG.warn("Can't bind " + rval.infix(), ex);
					throw new MathException(ex.getMessage());
				}
				return rval;
			}
			/**
			 * This method was created by a SmartGuide.
			 * @param tokens java.util.StringTokenizer
			 * @exception java.lang.Exception The exception description.
			 */
			/*
			private void read(MathDescription mathDesc, CommentStringTokenizer tokens) throws MathException, cbit.vcell.parser.ExpressionException {
				String token = null;
				token = tokens.nextToken();
				if (!token.equalsIgnoreCase(VCML.BeginBlock)){
					throw new MathFormatException("unexpected token "+token+" expecting "+VCML.BeginBlock);
				}			
				while (tokens.hasMoreTokens()){
					token = tokens.nextToken();
					if (token.equalsIgnoreCase(VCML.EndBlock)){
						break;
					}			
					if (token.equalsIgnoreCase(VCML.Handle)){
						//
						// throw away "handle information" deprecated
						//
						token = tokens.nextToken();
						//handle = Integer.valueOf(token).intValue();
						continue;
					}
					if (token.equalsIgnoreCase(VCML.Priority)){
						token = tokens.nextToken();
						priority = Integer.valueOf(token).intValue();
						continue;
					}
					if (token.equalsIgnoreCase(VCML.BoundaryXm)){
						String type = tokens.nextToken();
						boundaryConditionTypeXm = new BoundaryConditionType(type);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.BoundaryXp)){
						String type = tokens.nextToken();
						boundaryConditionTypeXp = new BoundaryConditionType(type);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.BoundaryYm)){
						String type = tokens.nextToken();
						boundaryConditionTypeYm = new BoundaryConditionType(type);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.BoundaryYp)){
						String type = tokens.nextToken();
						boundaryConditionTypeYp = new BoundaryConditionType(type);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.BoundaryZm)){
						String type = tokens.nextToken();
						boundaryConditionTypeZm = new BoundaryConditionType(type);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.BoundaryZp)){
						String type = tokens.nextToken();
						boundaryConditionTypeZp = new BoundaryConditionType(type);
						continue;
					}
					if (token.equalsIgnoreCase(VCML.BoundaryConditionSpec)) {
						String name = tokens.nextToken();
						String type = tokens.nextToken();
						BoundaryConditionSpec  bcs = new BoundaryConditionSpec(name, new BoundaryConditionType(type));
						addBoundaryConditionSpec(bcs);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.PdeEquation)){
						token = tokens.nextToken();
						boolean bSteady = false;
						if (token.equals(VCML.Steady)) {
							bSteady = true;
							token = tokens.nextToken();
						}
						Variable var = mathDesc.getVariable(token);
						if (var == null){
							throw new MathFormatException("variable "+token+" not defined");
						}	
						if (!(var instanceof VolVariable)){
							throw new MathFormatException("variable "+token+" not a VolumeVariable");
						}	
						PdeEquation pde = new PdeEquation((VolVariable)var, bSteady);
						pde.read(tokens);
						addEquation(pde);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.OdeEquation)){
						token = tokens.nextToken();
						Variable var = mathDesc.getVariable(token);
						if (var == null){
							throw new MathFormatException("variable "+token+" not defined");
						}	
						if (!(var instanceof VolVariable)){
							throw new MathFormatException("variable "+token+" not a VolumeVariable");
						}	
						OdeEquation ode = new OdeEquation((VolVariable)var,null,null);
						ode.read(tokens);
						addEquation(ode);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.VolumeRegionEquation)){
						token = tokens.nextToken();
						Variable var = mathDesc.getVariable(token);
						if (var == null){
							throw new MathFormatException("variable "+token+" not defined");
						}	
						if (!(var instanceof VolumeRegionVariable)){
							throw new MathFormatException("variable "+token+" not a VolumeRegionVariable");
						}	
						VolumeRegionEquation vre = new VolumeRegionEquation((VolumeRegionVariable)var,null);
						vre.read(tokens);
						addEquation(vre);
						continue;
					}			
					/**
					 * ParticleJumpProcess name A B {
					 *    MacroscopicRateConstant dkdkdk;
					 *    Action destroy A
					 *    Action destroy B
					 *    Action create C
					 * }
					 */
			/*
					if (token.equalsIgnoreCase(VCML.ParticleJumpProcess)){
						ParticleJumpProcess particleJumpProcess = ParticleJumpProcess.fromVCML(mathDesc, tokens);
						addParticleJumpProcess(particleJumpProcess);
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.ParticleProperties)){
						ParticleProperties pp = new ParticleProperties(mathDesc, tokens);
						if((pp.getVariable() == null) || (pp.getVariable().getDomain() == null)) {
							throw new RuntimeException("either variable not found or no domain");
			//				continue;
						}
						if(pp.getVariable().getDomain().getName().equals(this.getName())){
							addParticleProperties(pp);
						}else{
							throw new MathException("Variable (" + pp.getVariable().getName() + ") is defined in domain " + pp.getVariable().getDomain().getName() +
									                 ". \nHowever the variable particle properties of " + pp.getVariable().getName() + " is defined in domain " + this.getName() + ". \nPlease check your model.");
						}
						continue;
					}			
					if (token.equalsIgnoreCase(VCML.FastSystem)){
						FastSystem fs = new FastSystem(mathDesc);
						fs.read(tokens);
						setFastSystem(fs);
						continue;
					}	
					//Variable initial conditions as count		
					if (token.equalsIgnoreCase(VCML.VarIniCount_Old) || token.equalsIgnoreCase(VCML.VarIniCount))
					{
						token = tokens.nextToken();
						Variable var = mathDesc.getVariable(token);
						if (var == null){
							throw new MathFormatException("variable "+token+" not defined");
						}	
						if (!(var instanceof StochVolVariable)){
							throw new MathFormatException("variable "+token+" not a Stochastic Volume Variable");
						}
						
						Expression varIniExp = MathFunctionDefinitions.fixFunctionSyntax(tokens);
						try{
							varIniExp.bindExpression(mathDesc);
						}catch(Exception ex){
							ex.printStackTrace(System.out);
							throw new MathException(ex.getMessage());
						}
						VarIniCount vic= new VarIniCount(var,varIniExp);
						addVarIniCondition(vic);
						
						continue;
					}
					//Variable inital conditions as concentration
					if (token.equalsIgnoreCase(VCML.VarIniPoissonExpectedCount))
					{
						token = tokens.nextToken();
						Variable var = mathDesc.getVariable(token);
						if (var == null){
							throw new MathFormatException("variable "+token+" not defined");
						}	
						if (!(var instanceof StochVolVariable)){
							throw new MathFormatException("variable "+token+" not a Stochastic Volume Variable");
						}
						
						Expression varIniExp = MathFunctionDefinitions.fixFunctionSyntax(tokens);
						try{
							varIniExp.bindExpression(mathDesc);
						}catch(Exception ex){
							ex.printStackTrace(System.out);
							throw new MathException(ex.getMessage());
						}
						VarIniPoissonExpectedCount vic= new VarIniPoissonExpectedCount(var,varIniExp);
						addVarIniCondition(vic);
						
						continue;
					}
					//Jump processes 
					if (token.equalsIgnoreCase(VCML.JumpProcess))
					{
						JumpProcess jump = null;
						token = tokens.nextToken();
						String name=token;
						token = tokens.nextToken();
						if(!token.equalsIgnoreCase(VCML.BeginBlock))
							throw new MathFormatException("unexpected token "+token+" expecting "+VCML.BeginBlock);
						token = tokens.nextToken();	
						if(token.equalsIgnoreCase(VCML.ProbabilityRate))
						{
							Expression probExp = MathFunctionDefinitions.fixFunctionSyntax(tokens);
							//check if probability functions contain "t", which is not allowed.
							Expression extProb = MathUtilities.substituteFunctions(probExp,mathDesc).flatten();
							String[] symbols = extProb.getSymbols();
							if(symbols != null)
							{
								for(int i=0; i<symbols.length; i++)
								{
									if(symbols[i].equals("t"))
									{
										throw new MathFormatException("Unexpected symbol \'t\'  in probability rate of jump process "+name+". Probability rate should not be a function of t.");
									}	
								}
							}
							probExp.bindExpression(mathDesc);
							jump = new JumpProcess(name,probExp);
							addJumpProcess(jump);
						}
						else {
							throw new MathFormatException("unexpected identifier "+token);
						}
			
						if(jump != null)
						{
							while (tokens.hasMoreTokens())
							{
								token = tokens.nextToken();
								if (token.equalsIgnoreCase(VCML.EndBlock)){
									break;
								}
								if (token.equalsIgnoreCase(VCML.Action))
								{
									token = tokens.nextToken();
									Variable var = mathDesc.getVariable(token);
									if (var == null){
										throw new MathFormatException("variable "+token+" not defined");
									}	
									if (!(var instanceof StochVolVariable)){
										throw new MathFormatException("variable "+token+" not a Stochastic Volume Variable");
									}
									String opera = tokens.nextToken();
									if (!opera.equals(Action.ACTION_INC)){
										throw new MathFormatException("expected 'INC' for action, found "+opera);
									}
									Expression exp = MathFunctionDefinitions.fixFunctionSyntax(tokens);
									try{
										exp.bindExpression(mathDesc);
									}
									catch(Exception ex){
										ex.printStackTrace(System.out);
										throw new MathException(ex.getMessage());
									}
									Action action = Action.createIncrementAction(var,exp);
									jump.addAction(action);
								}
								else throw new MathFormatException("unexpected identifier "+token);
							}
						}
						
						continue;
					}	
					throw new MathFormatException("unexpected identifier "+token);
				}	
					
			}
			*/

	private Vector<Equation> equationList = new Vector<Equation>();
	private FastSystem fastSystem = null;
	private ArrayList<JumpProcess> listOfJumpProcesses = new ArrayList<JumpProcess>( ); 
	private ArrayList<VarIniCondition> listOfVarIniConditions = new ArrayList<VarIniCondition>(); 
	private ArrayList<ParticleJumpProcess> listOfParticleJumpProcesses = new ArrayList<ParticleJumpProcess>( );
	private ArrayList<ParticleProperties> listOfParticleProperties = new ArrayList<ParticleProperties>();
	
	private ArrayList<BoundaryConditionSpec> listOfBoundaryConditionSpecs = new ArrayList<SubDomain.BoundaryConditionSpec>();

	public interface DomainWithBoundaryConditions {
		BoundaryConditionType getBoundaryConditionXm();
		BoundaryConditionType getBoundaryConditionXp();
		BoundaryConditionType getBoundaryConditionYm();
		BoundaryConditionType getBoundaryConditionYp();
		BoundaryConditionType getBoundaryConditionZm();
		BoundaryConditionType getBoundaryConditionZp();
		Collection<Equation> getEquationCollection();
	}

	// To specify the <MembraneName, BoundaryConditionType> for each compartmentsubDomain
	public static class BoundaryConditionSpec implements Matchable, Serializable {
		
		private String boundarySubdomainName = null;
		private BoundaryConditionType boundaryConditionType = null;

		public BoundaryConditionSpec(String boundarySubdomainName, BoundaryConditionType bcType) {
			super();
			this.boundarySubdomainName = boundarySubdomainName;
			this.boundaryConditionType = bcType;
		}
		
		public String getBoundarySubdomainName() {
			return boundarySubdomainName;
		}
		
		public BoundaryConditionType getBoundaryConditionType() {
			return boundaryConditionType;
		}
		
		public boolean compareEqual(Matchable obj) {
			if (obj instanceof BoundaryConditionSpec){
				BoundaryConditionSpec bcv = (BoundaryConditionSpec)obj;
				if(!Compare.isEqual(boundarySubdomainName,bcv.boundarySubdomainName))  {
					return false;
				}
				if (!Compare.isEqual(boundaryConditionType, bcv.boundaryConditionType)){
					return false;
				}
				return true;
			}
			return false;
		}
		
		public String getVCML() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\t"+VCML.BoundaryConditionSpec+"\t"+getBoundarySubdomainName()+"\t"+getBoundaryConditionType().boundaryTypeStringValue()+"\n");
			return buffer.toString();
		}

	}
	
/**
 * This method was created by a SmartGuide.
 * @param name java.lang.String
 */
protected SubDomain (String name) {
	super(name);
}


protected SubDomain(Token leadToken, CommentStringTokenizer tokens) {
	super(leadToken, tokens);
}

@Override //IssueOrigin
public String getDescription() {
	return super.getName(); 
}


/**
 * This method was created by a SmartGuide.
 * @param equation cbit.vcell.math.Equation
 */
public void addEquation(Equation equation) throws MathException {
	if (getEquation(equation.getVariable()) != null){
		throw new MathException("equation for variable "+equation.getVariable().getName()+" already exists");
	}
	equationList.addElement(equation);	
}


/**
 * Append a new process to the jump process list if it is still not in the list.
 * Creation date: (6/26/2006 5:28:25 PM)
 */
public void addJumpProcess(JumpProcess newJumpProcess) throws MathException
{
	if(getJumpProcess(newJumpProcess.getName())!=null)
		throw new MathException("JumpProcess "+newJumpProcess.getName()+" already exists");
	listOfJumpProcesses.add(newJumpProcess);
}

public void addParticleJumpProcess(ParticleJumpProcess newParticleJumpProcess) throws MathException
{
	if(getParticleJumpProcess(newParticleJumpProcess.getName())!=null)
		throw new MathException("JumpProcess "+newParticleJumpProcess.getName()+" already exists");
	listOfParticleJumpProcesses.add(newParticleJumpProcess);
}

public void addParticleProperties(ParticleProperties newParticleProperties) throws MathException
{
	if(getParticleProperties(newParticleProperties.getVariable())!=null)
		throw new MathException("ParticleProperties "+newParticleProperties.getVariable().getName()+" already exists");
	listOfParticleProperties.add(newParticleProperties);
}


public ParticleProperties getParticleProperties(Variable variable) {
	for (ParticleProperties pp : listOfParticleProperties) {
		if (pp.getVariable() == variable) {
			return pp;
		}
	}
	return null;
}


/**
 * Append a new variable initial condition to the list if the variable is not in the list.
 * Creation date: (6/27/2006 10:02:29 AM)
 * @param newVarIniCondition cbit.vcell.math.VarIniCondition
 */
public void addVarIniCondition(VarIniCondition newVarIniCondition) throws MathException
{
	if(getVarIniCondition(newVarIniCondition.getVar())!=null)
		throw new MathException("Initial condition regarding variable: "+newVarIniCondition.getVar().getName()+" already exists");
	listOfVarIniConditions.add(newVarIniCondition);
}

public void addBoundaryConditionSpec(BoundaryConditionSpec newBoundaryConditionSpec) throws MathException {
	if(getBoundaryConditionSpec(newBoundaryConditionSpec.getBoundarySubdomainName())!=null)
		throw new MathException("BoundaryCondition spec for : '"+ newBoundaryConditionSpec.getBoundarySubdomainName()+"' already exists");
	listOfBoundaryConditionSpecs.add(newBoundaryConditionSpec);
}

/**
 * This method was created in VisualAge.
 * @return boolean
 * @param object java.lang.Object
 */
protected boolean compareEqual0(Matchable object) {
	if (!super.compareEqual(object)) {
		return false;
	}
	SubDomain subDomain = (SubDomain)object;

	//
	// compare name
	//
	if (!Compare.isEqualOrNull(getName(),subDomain.getName())){
		return false;
	}
	//
	// compare equations
	//
	if (!Compare.isEqual(equationList,subDomain.equationList)){
		return false;
	}
	//
	// compare fastSystem
	//
	if (!Compare.isEqualOrNull(fastSystem,subDomain.fastSystem)){
		return false;
	}
	//
	// compare jumpProcesses
	//
	if (!Compare.isEqualOrNull(listOfJumpProcesses, subDomain.listOfJumpProcesses)){ 
		return false;
	}

	//
	// compare varIniConditions
	//
	if (!Compare.isEqualOrNull(listOfVarIniConditions,subDomain.listOfVarIniConditions)) {
		return false;
	}

	
	if (!Compare.isEqualOrNull(listOfParticleProperties, subDomain.listOfParticleProperties)){ 
		return false;
	}

	if (!Compare.isEqualOrNull(listOfParticleJumpProcesses, subDomain.listOfParticleJumpProcesses)){ 
		return false;
	}
	
	// boundaryConditionSpecs
	if (!Compare.isEqualOrNull(listOfBoundaryConditionSpecs, subDomain.listOfBoundaryConditionSpecs)){ 
		return false;
	}

	return true;
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.math.Equation
 * @param variable cbit.vcell.math.Variable
 */
public Equation getEquation(Variable variable) {
	for (Equation equ : equationList){
		if (equ.getVariable().getName().equals(variable.getName())){
			return equ;
		}
	}
	return null;
}


/**
 * This method was created by a SmartGuide.
 * @return java.util.Enumeration
 */
public Enumeration<Equation> getEquations() {
	return equationList.elements();
}

/**
 * @return equations as unmodifiable collection
 */
public Collection<Equation> getEquationCollection( ) {
	return Collections.unmodifiableCollection(equationList);
}


/**
 * This method was created in VisualAge.
 * @return cbit.vcell.math.FastSystem
 */
public FastSystem getFastSystem() {
	return fastSystem;
}


/**
 * Get a jump process from the list by it's index.
 * Creation date: (6/27/2006 10:23:01 AM)
 * @return cbit.vcell.math.JumpProcess
 * @param index int
 */
public JumpProcess getJumpProcess(int index)
{
	if(index<listOfJumpProcesses.size())
		return (JumpProcess)listOfJumpProcesses.get(index);
	return null;
}


/**
 * Get a jump process from process list by it's name.
 * Creation date: (6/27/2006 10:36:11 AM)
 * @return cbit.vcell.math.JumpProcess
 * @param processName java.lang.String
 */
public JumpProcess getJumpProcess(String processName) {
	for(JumpProcess jp : listOfJumpProcesses){
		if(jp.getName().equals(processName)){
			return jp;
		}
	}
	return null;
}

public ParticleJumpProcess getParticleJumpProcess(String processName) {
	for (ParticleJumpProcess pjp : listOfParticleJumpProcesses){
		if(pjp.getName().compareTo(processName)==0)
			return pjp;
	}
	return null;
}

public List<ParticleJumpProcess> getParticleJumpProcesses() {
	return Collections.unmodifiableList(listOfParticleJumpProcesses);
}

public List<ParticleProperties> getParticleProperties() {
	return Collections.unmodifiableList(listOfParticleProperties);
}


/**
 * Return the reference of the jump process list.
 * Creation date: (6/27/2006 3:05:52 PM)
 * @return java.util.Vector
 */
public List<JumpProcess> getJumpProcesses() {
	return Collections.unmodifiableList(listOfJumpProcesses);
}

/**
 * Get a variable initial condition from the list by it's index.
 * Creation date: (6/27/2006 10:40:07 AM)
 * @return cbit.vcell.math.VarIniCondition
 * @param index int
 */
public VarIniCondition getVarIniCondition(int index) 
{
	if(index<listOfVarIniConditions.size())
		return (VarIniCondition)listOfVarIniConditions.get(index);
	return null;
}


/**
 * Get a variable initial condition from list by varaible's name.
 * Creation date: (6/27/2006 10:42:24 AM)
 * @return cbit.vcell.math.VarIniCondition
 * @param varName java.lang.String
 */
public VarIniCondition getVarIniCondition(Variable var) 
{
	for (VarIniCondition vic : listOfVarIniConditions){
		if (vic.getVar() == var){
			return vic;
		}
	}
	return null;
}


/**
 * Return the reference of the variable initial condition list.
 * Creation date: (6/27/2006 3:07:26 PM)
 * @return java.util.Vector
 */
public List<VarIniCondition> getVarIniConditions() {
	return Collections.unmodifiableList(listOfVarIniConditions);
}


public BoundaryConditionSpec getBoundaryConditionSpec(int index) {
	if(index<listOfBoundaryConditionSpecs.size())
		return (BoundaryConditionSpec)listOfBoundaryConditionSpecs.get(index);
	return null;
}


/**
 * Get a boundaryConditionSpec from list by boundaryConditionSpec's name.
 * @return boundaryConditionSpec
 * @param varName java.lang.String
 */
public BoundaryConditionSpec getBoundaryConditionSpec(String boundaryConditionSpecName)
{
	for (BoundaryConditionSpec bcs : listOfBoundaryConditionSpecs){
		if (bcs.getBoundarySubdomainName().equals(boundaryConditionSpecName)){
			return bcs;
		}
	}
	return null;
}


/**
 * Return the reference of the boundaryConditionSpec list.
 * @return java.util.Vector
 */
public List<BoundaryConditionSpec> getBoundaryconditionSpecs() {
	return Collections.unmodifiableList(listOfBoundaryConditionSpecs);
}


/**
 * Remove a BoundaryConditionSpec from list by it's index.
 * @param index int
 */
public void removeBoundaryConditionSpec(int index) {
	listOfBoundaryConditionSpecs.remove(index);
}


/**
 * Remove a BoundaryConditionSpec from list by it's name in the list.
 * @param varName java.lang.String
 */
public void removeBoundaryConditionSpec(String bcSubdomainName) {
	for (BoundaryConditionSpec bcs : listOfBoundaryConditionSpecs){
		if(bcs.getBoundarySubdomainName().equals(bcSubdomainName)){
			listOfBoundaryConditionSpecs.remove(bcs);
		}
	}	
}


/**
 * empty theBoundaryConditionSpec list
 */
public void removeBoundaryConditionSpecs() {
	listOfBoundaryConditionSpecs.clear();
}


/**
 * This method was created by a SmartGuide.
 * @return java.lang.String
 */
public abstract String getVCML(int spatialDimension);


/**
 * Remove a jump process from jump process list by it's index in the list.
 * Creation date: (6/26/2006 5:39:50 PM)
 */
public void removeJumpProcess(int index)
{
	if(index<listOfJumpProcesses.size())
		listOfJumpProcesses.remove(index);
}


/**
 * Remove a jump process from jump process list by it's index in the list.
 * Creation date: (6/26/2006 5:39:50 PM)
 */
public void removeJumpProcess(String procName)
{
	for(JumpProcess jp : listOfJumpProcesses){
		if (jp.getName().equals(procName)){
			listOfJumpProcesses.remove(jp);
		}
	}
}


/**
 * empty the jump process list
 * Creation date: (6/26/2006 5:39:50 PM)
 */
public void removeJumpProcesses()
{
	listOfJumpProcesses.clear();
}


/**
 * Remove a variable initial condition from list by it's index.
 * Creation date: (6/27/2006 10:04:45 AM)
 * @param index int
 */
public void removeVarIniCondition(int index)
{
	if(index<listOfVarIniConditions.size())
		listOfVarIniConditions.remove(index);
}


/**
 * Remove a variable initial condition from list by it's name in the list.
 * Creation date: (6/27/2006 10:06:44 AM)
 * @param varName java.lang.String
 */
public void removeVarIniCondition(String varName)
{
	for (VarIniCondition vic : listOfVarIniConditions){
		if(vic.getVar().getName().equals(varName)){
			listOfVarIniConditions.remove(vic);
		}
	}	
}


/**
 * Remove a variable initial condition from list by it's name in the list.
 * Creation date: (6/27/2006 10:06:44 AM)
 * @param varName java.lang.String
 */
public void removeEquation(Variable var)
{
	Equation equationToDelete = null;
	for (Equation equ : equationList){
		if(equ.getVariable() == var){
			equationToDelete = equ;
		}
	}
	if (equationToDelete!=null){
		equationList.remove(equationToDelete);
	}
}


/**
 * empty the variable initial condition list
 * Creation date: (11/14/2006 6:40:22 PM)
 */
public void removeVarIniConditions() 
{
	listOfVarIniConditions.clear();
}


/**
 * This method was created in VisualAge.
 * @param equation cbit.vcell.math.Equation
 */
public void replaceEquation(Equation equation) throws MathException {
	Equation currentEquation = getEquation(equation.getVariable());
	if (currentEquation!=null){
		equationList.removeElement(currentEquation);
	}
	addEquation(equation);
}


/**
 * This method was created in VisualAge.
 * @param fastSystem cbit.vcell.math.FastSystem
 * @exception java.lang.Exception The exception description.
 */
public void setFastSystem(FastSystem fastSystem) {
	this.fastSystem = fastSystem;
}


/**
 * This method was created in VisualAge.
 * @return java.lang.String
 */
public String toString() {
	return getClass().getName()+"@"+Integer.toHexString(hashCode())+": "+getName();
}


/**
 * Insert the method's description here.
 * Creation date: (10/12/2002 3:30:10 PM)
 */
public void trimTrivialEquations(MathDescription mathDesc) {
	for (int i = 0; i < equationList.size(); i++){
		Equation equ = (Equation)equationList.elementAt(i);
		Vector<Expression> expressionList = equ.getExpressions(mathDesc);
		boolean bNonZeroExists = false;
		for (Expression exp : expressionList){
			if (!exp.isZero()){
				bNonZeroExists = true;
				break;
			}
		}
		if (!bNonZeroExists){
			equationList.remove(i);
			i--;
		}
	}
}

/**
 * get provider for specified dimension
 * @param spatialDimension
 * @return VCMLProvider
 */
public VCMLProvider getVCMLProvider(int spatialDimension) {
	return new ProviderAdapter(this,spatialDimension);
}

/**
 * adapt SubDomain.getVCML(int) API to VCMLProvider.getVCML( )
 */
private class ProviderAdapter implements VCMLProvider {
	private final SubDomain subDomain;
	private final int spatialDimension;

	ProviderAdapter(SubDomain subDomain, int spatialDimension) {
		super();
		this.subDomain = subDomain;
		this.spatialDimension = spatialDimension;
	}

	@Override
	public String getBeforeComment() {
		return subDomain.getBeforeComment();
	}

	@Override
	public void setBeforeComment(String comment) {
		subDomain.setBeforeComment(comment);

	}

	@Override
	public String getAfterComment() {
		return subDomain.getAfterComment();
	}

	@Override
	public void setAfterComment(String comment) {
		subDomain.setAfterComment(comment);
	}

	@Override
	public String getVCML() throws MathException {
		return subDomain.getVCML(spatialDimension);
	}

}

public void refreshDependencies(MathDescription mathDesc) {
	for (Equation eq : this.equationList){
		eq.refreshDependencies(mathDesc);
	}
}


}
