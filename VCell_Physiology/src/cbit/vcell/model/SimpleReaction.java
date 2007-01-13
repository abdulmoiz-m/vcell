package cbit.vcell.model;

/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/

import org.vcell.expression.ExpressionFactory;

import cbit.util.*;


public class SimpleReaction extends ReactionStep
{
public SimpleReaction(Structure structure,cbit.util.document.KeyValue key,String name) throws java.beans.PropertyVetoException {
	super(structure,key,name);
}   
public SimpleReaction(Structure structure,String name) throws java.beans.PropertyVetoException {
	super(structure,null,name);
}  

public void addProduct(SpeciesContext speciesContext,int stoichiometry) throws Exception {

	ReactionParticipant[] rps = getReactionParticipants(speciesContext);

	// NOTE : right now, we are not taking into account the possiblity of allowing 
	// a speciesContext to be a Catalyst as well as pdt or reactant.
	if (rps == null || rps.length == 0) {
		// No matching reactionParticipant was found for the speciesContext, hence add it as a Pdt
		addReactionParticipant(new Product(null,this, speciesContext, stoichiometry));
	} else if (rps.length == 1) {
		// One matching reactionParticipant was found for the speciesContext, 
		// if rp[0] is a reactant, add speciesContext as pdt, else throw exception since it is already a pdt (refer NOTE above)
		if (rps[0] instanceof Reactant){
			addReactionParticipant(new Product(null,this, speciesContext, stoichiometry));
		} else if (rps[0] instanceof Product || rps[0] instanceof Catalyst) {
			throw new Exception("reactionParticipant " + speciesContext.getName() + " already defined as a Product or Catalyst of the reaction.");
		}
	} else if (rps.length > 1) {
		// if rps.length is > 1, speciesContext occurs both as reactant and pdt, so throw exception
		throw new Exception("reactionParticipant " + speciesContext.getName() + " already defined as a Reactant and Product of the reaction.");
	}
}   

public void addReactant(SpeciesContext speciesContext,int stoichiometry) throws Exception {

	ReactionParticipant[] rps = getReactionParticipants(speciesContext);

	// NOTE : right now, we are not taking into account the possiblity of allowing 
	// a speciesContext to be a Catalyst as well as pdt or reactant.
	if (rps == null || rps.length == 0) {
		// No matching reactionParticipant was found for the speciesContext, hence add it as a Pdt
		addReactionParticipant(new Reactant(null,this, speciesContext, stoichiometry));
	} else if (rps.length == 1) {
		// One matching reactionParticipant was found for the speciesContext, 
		// if rp[0] is a product, add speciesContext as reactant, else throw exception since it is already a reactant (refer NOTE above)
		if (rps[0] instanceof Product){
			addReactionParticipant(new Reactant(null,this, speciesContext, stoichiometry));
		} else if (rps[0] instanceof Reactant || rps[0] instanceof Catalyst) {
			throw new Exception("reactionParticipant " + speciesContext.getName() + " already defined as a Reactant or a Catalyst of the reaction.");
		}
	} else if (rps.length > 1) {
		// if rps.length is > 1, speciesContext occurs both as reactant and pdt, so throw exception
		throw new Exception("reactionParticipant " + speciesContext.getName() + " already defined as a Reactant AND Product of the reaction.");
	}
		
}   

/**
 * This method was created in VisualAge.
 * @return boolean
 * @param obj java.lang.Object
 */
public boolean compareEqual(Matchable obj) {
	if (obj instanceof SimpleReaction){
		SimpleReaction sr = (SimpleReaction)obj;
		if (!super.compareEqual0(sr)){
			return false;
		}
		return true;
	}else{
		return false;
	}
}
/**
 * This method was created by a SmartGuide.
 * @param tokens java.util.StringTokenizer
 * @exception java.lang.Exception The exception description.
 */
public void fromTokens(cbit.util.CommentStringTokenizer tokens, Model model) throws Exception {
//	String token = tokens.nextToken(); // read "{"
//	if (!VCMODL.BeginBlock.equals(token)){
//		throw new Exception("read '"+token+"', expecting '"+VCMODL.BeginBlock+"'");
//	}
	while (tokens.hasMoreTokens()){
		String token = tokens.nextToken();
		if (token==null){
			throw new Exception("unexpected EOF in SimpleReaction");
		}
		if (token.equalsIgnoreCase(VCMODL.EndBlock)){
			break;
		}			
		if (token.equalsIgnoreCase(VCMODL.Reactant)){
			Reactant reactant = new Reactant(null,this);
			reactant.fromTokens(tokens,model);
			addReactionParticipant(reactant);
			continue;
		}
		if (token.equalsIgnoreCase(VCMODL.Product)){
			Product product = new Product(null,this);
			product.fromTokens(tokens,model);
			addReactionParticipant(product);
			continue;
		}
		if (token.equalsIgnoreCase(VCMODL.Catalyst)){
			Catalyst catalyst = new Catalyst(null,this);
			catalyst.fromTokens(tokens,model);
			addReactionParticipant(catalyst);
			continue;
		}
		if (token.equalsIgnoreCase(VCMODL.Valence)){
			getChargeCarrierValence().setExpression(ExpressionFactory.createExpression(Integer.parseInt(tokens.nextToken())));
			continue;
		}
		if (token.equalsIgnoreCase(VCMODL.PhysicsOptions)){
			setPhysicsOptions(Integer.parseInt(tokens.nextToken()));
			continue;
		}
		if (token.equalsIgnoreCase(VCMODL.Kinetics)){
			String kineticsType = tokens.nextToken();	// read MassActionKinetics or GeneralKinetics
			if (kineticsType==null){
				throw new Exception("unexpected EOF in SimpleReaction");
			}
			KineticsDescription kineticsDescription = KineticsDescription.fromVCMLKineticsName(kineticsType);
			if (kineticsDescription==null){
				Kinetics kinetics = kineticsDescription.createKinetics(this);
				setKinetics(kinetics);
				getKinetics().fromTokens(tokens);
			}else{
				throw new Exception("unknown kinetic type '"+kineticsType+"' in SimpleReaction");
			}						
			continue;
		}
		throw new Exception("unexpected identifier "+token);
	}
}
public int getNumProducts() {

	int count = 0;

	ReactionParticipant rp_Array[] = getReactionParticipants();
	
	for (int i = 0; i < rp_Array.length; i++) {
		if (rp_Array[i] instanceof Product){
			count++;
		}
	}
	
	return count;
}   
public int getNumReactants() {

	int count = 0;

	ReactionParticipant rp_Array[] = getReactionParticipants();

	for (int i = 0; i < rp_Array.length; i++) {
		if (rp_Array[i] instanceof Reactant){
			count++;
		}
	}
	
	return count;
}   
/**
 * This method was created in VisualAge.
 * @return double
 * @param speciesContext cbit.vcell.model.SpeciesContext
 */
public int getStoichiometry(Species species, Structure structure) {
	ReactionParticipant[] rps = getReactionParticipants(species, structure);
	if (rps == null || rps.length == 0){
		return 0;
	}

	int totalStoich = 0;
	for (int i = 0; i < rps.length; i++){
		if (rps[i] instanceof Product){
			totalStoich += rps[i].getStoichiometry();
		}else if (rps[i] instanceof Reactant){
			totalStoich += (-1)*rps[i].getStoichiometry();
		}
	}
	return totalStoich;
}

/**
 * Insert the method's description here.
 * Creation date: (5/22/00 10:22:19 PM)
 * @return java.lang.String
 */
public String getTerm() {
	return "Reaction";
}
/**
 * This method was created by a SmartGuide.
 * @return boolean
 */
public boolean isMembrane() {

	ReactionParticipant rp_Array[] = getReactionParticipants();

	for (int i = 0; i < rp_Array.length; i++) {
		if (rp_Array[i].getStructure() instanceof Membrane){
			return true;
		}
	}			
	
	return false;
}
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 */
public String toString() {
	// return "SimpleReaction("+getKey()+", "+getName()+", "+getNumReactionParticipants()+" reactParticipants)";
	return "SimpleReaction@"+Integer.toHexString(hashCode())+"("+getKey()+", "+getName()+", "+getReactionParticipants().length+" reactParticipants, valence="+getChargeCarrierValence()+", physicsOption="+getPhysicsOptions()+")";
}
/**
 * This method was created by a SmartGuide.
 * @param ps java.io.PrintStream
 * @exception java.lang.Exception The exception description.
 */
public void writeTokens(java.io.PrintWriter pw) {

	String nameStr = (getName()!=null)?(getName()):"unnamed_SimpleReaction";
	pw.println("\t"+VCMODL.SimpleReaction+" "+getStructure().getName()+" "+nameStr+" "+VCMODL.BeginBlock+" ");
	pw.println("\t\t"+VCMODL.Valence+" "+getChargeCarrierValence()+"; ");
	pw.println("\t\t"+VCMODL.PhysicsOptions+" "+getPhysicsOptions()+"; ");
	
	//
	// write Reactants/Products/Catalysts
	//
	ReactionParticipant rp_Array[] = getReactionParticipants();

	for (int i = 0; i < rp_Array.length; i++) {
		rp_Array[i].writeTokens(pw);
	}
			
	getKinetics().writeTokens(pw);
				
	pw.println("\t"+VCMODL.EndBlock+" ");
}
}
