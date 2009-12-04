package cbit.vcell.mapping;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

import org.vcell.util.BeanUtils;
import org.vcell.util.Compare;
import org.vcell.util.Matchable;

import cbit.vcell.mapping.SpeciesContextSpec.SpeciesContextSpecParameter;
import cbit.vcell.math.VCML;
import cbit.vcell.model.Feature;
import cbit.vcell.model.Model;
import cbit.vcell.model.Parameter;
import cbit.vcell.model.ReactionStep;
import cbit.vcell.model.SpeciesContext;
import cbit.vcell.model.Structure;
import cbit.vcell.model.VCMODL;
import cbit.vcell.model.Model.ModelParameter;
import cbit.vcell.parser.Expression;
import cbit.vcell.parser.ExpressionBindingException;
import cbit.vcell.parser.ExpressionException;
import cbit.vcell.units.VCUnitDefinition;
/**
 * This class was generated by a SmartGuide.
 *  
 */
public  class ReactionContext implements Serializable, Matchable, PropertyChangeListener, VetoableChangeListener {

	protected transient java.beans.VetoableChangeSupport vetoPropertyChange;
	protected transient java.beans.PropertyChangeSupport propertyChange;
	private ReactionSpec[] fieldReactionSpecs = new ReactionSpec[0];
	private SpeciesContextSpec[] fieldSpeciesContextSpecs = new SpeciesContextSpec[0];
	private Model fieldModel = null;
	private SimulationContext simContext = null;

/**
 * This method was created by a SmartGuide.
 * @param model cbit.vcell.model.Model
 * @param geoContext cbit.vcell.mapping.GeometryContext
 */
ReactionContext(ReactionContext reactionContext, SimulationContext argSimulationContext) {
	this.fieldModel = reactionContext.fieldModel;
	this.simContext = argSimulationContext;
	fieldReactionSpecs = new ReactionSpec[reactionContext.fieldReactionSpecs.length];
	for (int i = 0; i < reactionContext.fieldReactionSpecs.length; i++){
		fieldReactionSpecs[i] = new ReactionSpec(reactionContext.getReactionSpecs(i));
		fieldReactionSpecs[i].setSimulationContext(simContext);
	}
	fieldSpeciesContextSpecs = new SpeciesContextSpec[reactionContext.fieldSpeciesContextSpecs.length];
	for (int i = 0; i < reactionContext.fieldSpeciesContextSpecs.length; i++){
		fieldSpeciesContextSpecs[i] = new SpeciesContextSpec(reactionContext.fieldSpeciesContextSpecs[i],simContext);
	}	
	refreshDependencies();
}


/**
 * This method was created by a SmartGuide.
 * @param model cbit.vcell.model.Model
 * @param geoContext cbit.vcell.mapping.GeometryContext
 */
ReactionContext (Model argModel, SimulationContext argSimulationContext) {
	this.fieldModel = argModel;
	if (argModel != null){
		argModel.addPropertyChangeListener(this);
	}
	this.simContext = argSimulationContext;
	refreshDependencies();
	refreshAll();
}


/**
 * The addPropertyChangeListener method was generated to support the propertyChange field.
 */
public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
	getPropertyChange().addPropertyChangeListener(listener);
}


/**
 * The addPropertyChangeListener method was generated to support the propertyChange field.
 */
public synchronized void addPropertyChangeListener(java.lang.String propertyName, java.beans.PropertyChangeListener listener) {
	getPropertyChange().addPropertyChangeListener(propertyName, listener);
}


/**
 * The addVetoableChangeListener method was generated to support the vetoPropertyChange field.
 */
public synchronized void addVetoableChangeListener(java.beans.VetoableChangeListener listener) {
	getVetoPropertyChange().addVetoableChangeListener(listener);
}


/**
 * The addVetoableChangeListener method was generated to support the vetoPropertyChange field.
 */
public synchronized void addVetoableChangeListener(java.lang.String propertyName, java.beans.VetoableChangeListener listener) {
	getVetoPropertyChange().addVetoableChangeListener(propertyName, listener);
}


/**
 * This method was created in VisualAge.
 * @return boolean
 * @param object java.lang.Object
 */
public boolean compareEqual(Matchable object) {
	ReactionContext reactContext = null;
	if (!(object instanceof ReactionContext)){
		return false;
	}else{
		reactContext = (ReactionContext)object;
	}

	if (!Compare.isEqual(fieldModel,reactContext.fieldModel)){
		return false;
	}
	
	if (!Compare.isEqual(fieldSpeciesContextSpecs, reactContext.fieldSpeciesContextSpecs)){
		return false;
	}
	
	if (!Compare.isEqual(fieldReactionSpecs, reactContext.fieldReactionSpecs)){
		return false;
	}
	
	return true;
}


/**
 * The firePropertyChange method was generated to support the propertyChange field.
 */
public void firePropertyChange(java.beans.PropertyChangeEvent evt) {
	getPropertyChange().firePropertyChange(evt);
}


/**
 * The firePropertyChange method was generated to support the propertyChange field.
 */
public void firePropertyChange(java.lang.String propertyName, int oldValue, int newValue) {
	getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
}


/**
 * The firePropertyChange method was generated to support the propertyChange field.
 */
public void firePropertyChange(java.lang.String propertyName, java.lang.Object oldValue, java.lang.Object newValue) {
	getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
}


/**
 * The firePropertyChange method was generated to support the propertyChange field.
 */
public void firePropertyChange(java.lang.String propertyName, boolean oldValue, boolean newValue) {
	getPropertyChange().firePropertyChange(propertyName, oldValue, newValue);
}


/**
 * The fireVetoableChange method was generated to support the vetoPropertyChange field.
 */
public void fireVetoableChange(java.beans.PropertyChangeEvent evt) throws java.beans.PropertyVetoException {
	getVetoPropertyChange().fireVetoableChange(evt);
}


/**
 * The fireVetoableChange method was generated to support the vetoPropertyChange field.
 */
public void fireVetoableChange(java.lang.String propertyName, int oldValue, int newValue) throws java.beans.PropertyVetoException {
	getVetoPropertyChange().fireVetoableChange(propertyName, oldValue, newValue);
}


/**
 * The fireVetoableChange method was generated to support the vetoPropertyChange field.
 */
public void fireVetoableChange(java.lang.String propertyName, java.lang.Object oldValue, java.lang.Object newValue) throws java.beans.PropertyVetoException {
	getVetoPropertyChange().fireVetoableChange(propertyName, oldValue, newValue);
}


/**
 * The fireVetoableChange method was generated to support the vetoPropertyChange field.
 */
public void fireVetoableChange(java.lang.String propertyName, boolean oldValue, boolean newValue) throws java.beans.PropertyVetoException {
	getVetoPropertyChange().fireVetoableChange(propertyName, oldValue, newValue);
}


/**
 * Insert the method's description here.
 * Creation date: (11/1/2005 9:44:36 AM)
 * @param issueVector java.util.Vector
 */
public void gatherIssues(Vector issueVector) {
	for (int i = 0; fieldSpeciesContextSpecs!=null && i < fieldSpeciesContextSpecs.length; i++){
		fieldSpeciesContextSpecs[i].gatherIssues(issueVector);
	}
	for (int i = 0; fieldReactionSpecs!=null && i < fieldReactionSpecs.length; i++){
		fieldReactionSpecs[i].gatherIssues(issueVector);
	}
}


/**
 * Gets the model property (cbit.vcell.model.Model) value.
 * @return The model property value.
 * @see #setModel
 */
public Model getModel() {
	return fieldModel;
}


/**
 * Insert the method's description here.
 * Creation date: (2/23/01 10:58:34 PM)
 * @return int
 */
public int getNumReactionSpecs() {
	if (fieldReactionSpecs==null){
		return 0;
	}else{
		return fieldReactionSpecs.length;
	}
}


/**
 * Accessor for the propertyChange field.
 */
protected java.beans.PropertyChangeSupport getPropertyChange() {
	if (propertyChange == null) {
		propertyChange = new java.beans.PropertyChangeSupport(this);
	};
	return propertyChange;
}


/**
 * Gets the reactionSpecs index property (cbit.vcell.mapping.ReactionSpec) value.
 * @return The reactionSpecs property value.
 * @param index The index value into the property array.
 * @see #setReactionSpecs
 */
public ReactionSpec getReactionSpec(ReactionStep reactionStep) {
	for (int i=0;fieldReactionSpecs!=null && i<fieldReactionSpecs.length;i++){
		if (fieldReactionSpecs[i].getReactionStep() == reactionStep){
			return fieldReactionSpecs[i];
		}
	}
	return null;
}


/**
 * Gets the reactionSpecs property (cbit.vcell.mapping.ReactionSpec[]) value.
 * @return The reactionSpecs property value.
 * @see #setReactionSpecs
 */
public ReactionSpec[] getReactionSpecs() {
	return fieldReactionSpecs;
}


/**
 * Gets the reactionSpecs index property (cbit.vcell.mapping.ReactionSpec) value.
 * @return The reactionSpecs property value.
 * @param index The index value into the property array.
 * @see #setReactionSpecs
 */
public ReactionSpec getReactionSpecs(int index) {
	return getReactionSpecs()[index];
}


/**
 * Insert the method's description here.
 * Creation date: (4/4/2004 10:41:23 AM)
 * @return cbit.vcell.mapping.SimulationContext
 */
public SimulationContext getSimulationContext() {
	return simContext;
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.mapping.SpeciesContextSpec
 * @param speciesContext cbit.vcell.model.SpeciesContext
 */
public SpeciesContextSpec getSpeciesContextSpec(SpeciesContext speciesContext) {
	for (int i=0;i<fieldSpeciesContextSpecs.length;i++){
		SpeciesContextSpec scs = fieldSpeciesContextSpecs[i];
		if (scs.getSpeciesContext() == speciesContext){
			return scs;
		}
	}
	return null;
}			

/**
 * Gets the speciesContextSpecs property (cbit.vcell.mapping.SpeciesContextSpec[]) value.
 * @return The speciesContextSpecs property value.
 * @see #setSpeciesContextSpecs
 */
public SpeciesContextSpec[] getSpeciesContextSpecs() {
	return fieldSpeciesContextSpecs;
}


/**
 * Gets the speciesContextSpecs index property (cbit.vcell.mapping.SpeciesContextSpec) value.
 * @return The speciesContextSpecs property value.
 * @param index The index value into the property array.
 * @see #setSpeciesContextSpecs
 */
public SpeciesContextSpec getSpeciesContextSpecs(int index) {
	return getSpeciesContextSpecs()[index];
}


/**
 * This method was created by a SmartGuide.
 * @return java.lang.String
 */
public String getVCML() throws Exception {
	StringBuffer buffer = new StringBuffer();
	buffer.append(VCMODL.ReactionContext+" {\n");

	//
	// write SpeciesContextSpecs
	//
	for (int i=0;i<fieldSpeciesContextSpecs.length;i++){
		SpeciesContextSpec scs = fieldSpeciesContextSpecs[i];
		buffer.append(scs.getVCML());
	}
	//
	// write ReactionSpecs
	//
	for (int i=0;i<fieldReactionSpecs.length;i++){
		buffer.append(fieldReactionSpecs[i].getVCML());
	}
	buffer.append("}\n");
	return buffer.toString();		
}


/**
 * Accessor for the vetoPropertyChange field.
 */
protected java.beans.VetoableChangeSupport getVetoPropertyChange() {
	if (vetoPropertyChange == null) {
		vetoPropertyChange = new java.beans.VetoableChangeSupport(this);
	};
	return vetoPropertyChange;
}


/**
 * The hasListeners method was generated to support the propertyChange field.
 */
public synchronized boolean hasListeners(java.lang.String propertyName) {
	return getPropertyChange().hasListeners(propertyName);
}


	/**
	 * This method gets called when a bound property is changed.
	 * @param evt A PropertyChangeEvent object describing the event source 
	 *   	and the property that has changed.
	 */
public void propertyChange(java.beans.PropertyChangeEvent evt) {
	if (evt.getSource().equals(getModel()) && evt.getPropertyName().equals("reactionSteps")){
		refreshAll();
	}
	if (evt.getSource().equals(getModel()) && evt.getPropertyName().equals("speciesContexts")){
		refreshAll();
	}
}


/**
 * This method was created by a SmartGuide.
 * @param tokens java.util.StringTokenizer
 * @exception java.lang.Exception The exception description.
 */
public void read(org.vcell.util.CommentStringTokenizer tokens) throws Exception {
	refreshSpeciesContextSpecs();
	
	String token = null;
	token = tokens.nextToken();
	if (token.equalsIgnoreCase(VCMODL.ReactionContext)){
		token = tokens.nextToken();
	}			
	if (!token.equalsIgnoreCase(VCML.BeginBlock)){
		throw new Exception("unexpected token "+token+" expecting "+VCML.BeginBlock);
	}			
	while (tokens.hasMoreTokens()){
		token = tokens.nextToken();
		if (token.equalsIgnoreCase(VCML.EndBlock)){
			break;
		}		
		//
		// read speciesMapping
		//	
		if (token.equalsIgnoreCase(VCMODL.SpeciesContextSpec)){
			SpeciesContextSpec scs = (SpeciesContextSpec)getSpeciesContextSpec(getModel().getSpeciesContext(tokens.nextToken()));
			scs.read(tokens);
			continue;
		}
		//
		// read reactionSpec
		//	
		if (token.equalsIgnoreCase(VCML.ReactionSpec)){
			ReactionSpec rs = getReactionSpec(getModel().getReactionStep(tokens.nextToken()));
			rs.read(tokens);
			continue;
		}
		throw new Exception("unexpected identifier "+token);
	}
	refreshSpeciesContextSpecs();
}


/**
 * This method was created by a SmartGuide.
 * @param o java.util.Observable
 * @param obj java.lang.Object
 */
private void refreshAll()
{
	try {
		refreshSpeciesContextSpecs();
		refreshReactionSpecs();
		if (simContext!=null){
			refreshSpeciesContextSpecBoundaryUnits(simContext.getGeometryContext().getStructureMappings());
		}
	}catch (Exception e){
		e.printStackTrace(System.out);
	}		
	return;
}


/**
 * This method was created in VisualAge.
 */
public void refreshDependencies() {
	removePropertyChangeListener(this);
	addPropertyChangeListener(this);
	
	fieldModel.removePropertyChangeListener(this);
	fieldModel.addPropertyChangeListener(this);
	fieldModel.removeVetoableChangeListener(this);
	fieldModel.addVetoableChangeListener(this);

	//fieldModel.refreshDependencies();

	for (int i=0;i<fieldSpeciesContextSpecs.length;i++){
		fieldSpeciesContextSpecs[i].setSimulationContext(simContext);
		fieldSpeciesContextSpecs[i].refreshDependencies();
	}
	refreshSpeciesContextSpecBoundaryUnits(simContext.getGeometryContext().getStructureMappings());
	for (int i=0;i<fieldReactionSpecs.length;i++){
		fieldReactionSpecs[i].refreshDependencies();
		fieldReactionSpecs[i].setSimulationContext(simContext);
	}
}


/**
 * This method was created by a SmartGuide.
 */
private void refreshReactionSpecs() throws java.beans.PropertyVetoException {

	//
	// if any reactionStep deleted, remove reactionSpec (reaction mapping)
	//
	boolean bChanged = false;
	Vector reactionSpecList = null;
	if (fieldReactionSpecs!=null){
		reactionSpecList = new Vector(Arrays.asList(fieldReactionSpecs));
	}else{
		reactionSpecList = new Vector();
	}
	for (int i=0;fieldReactionSpecs!=null && i<fieldReactionSpecs.length;i++){
		ReactionSpec reactionSpec = fieldReactionSpecs[i];
		ReactionStep reactionStep = getModel().getReactionStep(reactionSpec.getReactionStep().getName());
		//
		// No longer in database or name changed. Discard reaction mapping
		//
		if (reactionStep == null) {
			reactionSpecList.removeElement(reactionSpec);
			bChanged = true;
			continue;
		}

		//
		// Reaction was different instance (edited or from database) 
		// Keep same mapping, point to new reaction instance
		//
		if (reactionStep != reactionSpec.getReactionStep()){
			reactionSpec.setReactionStep(reactionStep);
			continue;
		}
	}

	//
	// update reactionSpec list if any reactionStep was added
	//
	ReactionStep reactionSteps[] = fieldModel.getReactionSteps();
	for (int i=0;i<reactionSteps.length;i++){
		ReactionStep reactionStep = reactionSteps[i];
		if (getReactionSpec(reactionStep) == null) {
			ReactionSpec rSpec = new ReactionSpec(reactionStep);
			reactionSpecList.addElement(rSpec);
			bChanged = true;
		}
	}

	if (bChanged){
		ReactionSpec[] newReactionSpecs = new ReactionSpec[reactionSpecList.size()];
		reactionSpecList.copyInto(newReactionSpecs);
		setReactionSpecs(newReactionSpecs);
	}
}


/**
 * Insert the method's description here.
 * Creation date: (9/15/2004 4:10:23 PM)
 */
void refreshSpeciesContextSpecBoundaryUnits(StructureMapping structureMappings[]) {
	for (int i = 0; i < fieldSpeciesContextSpecs.length; i++){
		SpeciesContextSpec scs = fieldSpeciesContextSpecs[i];
		VCUnitDefinition dirichletUnit = scs.getSpeciesContext().getUnitDefinition();
		VCUnitDefinition neumannUnit = scs.computeFluxUnit();
		for (int j = 0; j < structureMappings.length; j++){
			if (structureMappings[j].getStructure().equals(scs.getSpeciesContext().getStructure())){
				try {
					FeatureMapping fm = null;
					if (structureMappings[j] instanceof FeatureMapping){
						fm = (FeatureMapping)structureMappings[j];
					}else if (structureMappings[j] instanceof MembraneMapping){
						//
						// if a membrane, use boundary condition type from the enclosed volume compartment
						// (e.g. Plasma membrane uses cytosol boundary condition types).
						//
						Feature insideFeature = ((MembraneMapping)structureMappings[j]).getMembrane().getInsideFeature();
						for (int k = 0; k < structureMappings.length; k++){
							if (structureMappings[k].getStructure() == insideFeature){
								fm = (FeatureMapping)structureMappings[k];
							}
						}
					}
					//
					// SpeciesContextSpec is a volume species mapped to this resolved Feature
					//
					//
					// for the XM direction
					//
					{
					SpeciesContextSpec.SpeciesContextSpecParameter xmParm = scs.getBoundaryXmParameter();
					if (fm.getBoundaryConditionTypeXm().isDIRICHLET() && !xmParm.getUnitDefinition().compareEqual(dirichletUnit)){
						xmParm.setUnitDefinition(dirichletUnit);
					}else if (fm.getBoundaryConditionTypeXm().isNEUMANN() && !xmParm.getUnitDefinition().compareEqual(neumannUnit)){
						xmParm.setUnitDefinition(neumannUnit);
					}
					}
					//
					// for the XP direction
					//
					{
					SpeciesContextSpec.SpeciesContextSpecParameter xpParm = scs.getBoundaryXpParameter();
					if (fm.getBoundaryConditionTypeXp().isDIRICHLET() && !xpParm.getUnitDefinition().compareEqual(dirichletUnit)){
						xpParm.setUnitDefinition(dirichletUnit);
					}else if (fm.getBoundaryConditionTypeXp().isNEUMANN() && !xpParm.getUnitDefinition().compareEqual(neumannUnit)){
						xpParm.setUnitDefinition(neumannUnit);
					}
					}
					//
					// for the YM direction
					//
					{
					SpeciesContextSpec.SpeciesContextSpecParameter ymParm = scs.getBoundaryYmParameter();
					if (fm.getBoundaryConditionTypeYm().isDIRICHLET() && !ymParm.getUnitDefinition().compareEqual(dirichletUnit)){
						ymParm.setUnitDefinition(dirichletUnit);
					}else if (fm.getBoundaryConditionTypeYm().isNEUMANN() && !ymParm.getUnitDefinition().compareEqual(neumannUnit)){
						ymParm.setUnitDefinition(neumannUnit);
					}
					}
					//
					// for the YP direction
					//
					{
					SpeciesContextSpec.SpeciesContextSpecParameter ypParm = scs.getBoundaryYpParameter();
					if (fm.getBoundaryConditionTypeYp().isDIRICHLET() && !ypParm.getUnitDefinition().compareEqual(dirichletUnit)){
						ypParm.setUnitDefinition(dirichletUnit);
					}else if (fm.getBoundaryConditionTypeYp().isNEUMANN() && !ypParm.getUnitDefinition().compareEqual(neumannUnit)){
						ypParm.setUnitDefinition(neumannUnit);
					}
					}
					//
					// for the ZM direction
					//
					{
					SpeciesContextSpec.SpeciesContextSpecParameter zmParm = scs.getBoundaryZmParameter();
					if (fm.getBoundaryConditionTypeZm().isDIRICHLET() && !zmParm.getUnitDefinition().compareEqual(dirichletUnit)){
						zmParm.setUnitDefinition(dirichletUnit);
					}else if (fm.getBoundaryConditionTypeZm().isNEUMANN() && !zmParm.getUnitDefinition().compareEqual(neumannUnit)){
						zmParm.setUnitDefinition(neumannUnit);
					}
					}
					//
					// for the ZP direction
					//
					{
					SpeciesContextSpec.SpeciesContextSpecParameter zpParm = scs.getBoundaryZpParameter();
					if (fm.getBoundaryConditionTypeZp().isDIRICHLET() && !zpParm.getUnitDefinition().compareEqual(dirichletUnit)){
						zpParm.setUnitDefinition(dirichletUnit);
					}else if (fm.getBoundaryConditionTypeZp().isNEUMANN() && !zpParm.getUnitDefinition().compareEqual(neumannUnit)){
						zpParm.setUnitDefinition(neumannUnit);
					}
					}
				}catch (java.beans.PropertyVetoException e){
					e.printStackTrace(System.out);
				}
			}
		}
	}
}


/**
 * This method was created by a SmartGuide.
 */
private void refreshSpeciesContextSpecs() throws MappingException {

	//
	// if any speciesContext or structures deleted, remove speciesContext specification
	//
	SpeciesContextSpec newSpeciesContextSpecs[] = fieldSpeciesContextSpecs;
	for (int i=0;i<newSpeciesContextSpecs.length;i++){
		SpeciesContextSpec scs = newSpeciesContextSpecs[i];
		SpeciesContext speciesContext = getModel().getSpeciesContext(scs.getSpeciesContext().getName());
		if (speciesContext == null || !speciesContext.compareEqual(scs.getSpeciesContext())) {
			newSpeciesContextSpecs = (SpeciesContextSpec[])BeanUtils.removeElement(newSpeciesContextSpecs,scs);
			i--;
			continue;
		}else if (speciesContext != scs.getSpeciesContext()){
			scs.setSpeciesContextReference(speciesContext);
		}
		Structure structure = getModel().getStructure(scs.getSpeciesContext().getStructure().getName());
		if (structure == null || structure != scs.getSpeciesContext().getStructure()) {
			newSpeciesContextSpecs = (SpeciesContextSpec[])BeanUtils.removeElement(newSpeciesContextSpecs,scs);
			i--;
			continue;
		}
		if (fieldModel.getSpeciesContext(speciesContext.getName()) != scs.getSpeciesContext()) {
			throw new MappingException("GeometryContext.refreshSpeciesContextSpecs(), speciesContext doesn't match");
		}
	}

	//
	// update speciesContext mapping list if any speciesContext or structures were added
	//
	Structure structures[] = fieldModel.getStructures();
	for (int i=0;i<structures.length;i++){
		Structure structure = structures[i];
		SpeciesContext speciesContexts[] = fieldModel.getSpeciesContexts(structure);
		for (int j=0;j<speciesContexts.length;j++){
			SpeciesContext sc = speciesContexts[j];
			if (getSpeciesContextSpec(sc) == null) {
				newSpeciesContextSpecs = (SpeciesContextSpec[])BeanUtils.addElement(newSpeciesContextSpecs,new SpeciesContextSpec(sc,getSimulationContext()));
			}
		}
	}
	if (newSpeciesContextSpecs != fieldSpeciesContextSpecs){
		try {
			setSpeciesContextSpecs(newSpeciesContextSpecs);
		}catch (java.beans.PropertyVetoException e){
			e.printStackTrace(System.out);
			throw new MappingException(e.getMessage());
		}
	}
}


/**
 * The removePropertyChangeListener method was generated to support the propertyChange field.
 */
public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
	getPropertyChange().removePropertyChangeListener(listener);
}


/**
 * The removePropertyChangeListener method was generated to support the propertyChange field.
 */
public synchronized void removePropertyChangeListener(java.lang.String propertyName, java.beans.PropertyChangeListener listener) {
	getPropertyChange().removePropertyChangeListener(propertyName, listener);
}


/**
 * The removeVetoableChangeListener method was generated to support the vetoPropertyChange field.
 */
public synchronized void removeVetoableChangeListener(java.beans.VetoableChangeListener listener) {
	getVetoPropertyChange().removeVetoableChangeListener(listener);
}


/**
 * The removeVetoableChangeListener method was generated to support the vetoPropertyChange field.
 */
public synchronized void removeVetoableChangeListener(java.lang.String propertyName, java.beans.VetoableChangeListener listener) {
	getVetoPropertyChange().removeVetoableChangeListener(propertyName, listener);
}


/**
 * Sets the model property (cbit.vcell.model.Model) value.
 * @param model The new value for the property.
 * @see #getModel
 */
public void setModel(Model model) throws MappingException, java.beans.PropertyVetoException {
	Model oldValue = fieldModel;
	fieldModel = model;

	if (oldValue != null){
		oldValue.removePropertyChangeListener(this);
		oldValue.removeVetoableChangeListener(this);

	}
	if (fieldModel != null){
		fieldModel.addPropertyChangeListener(this);
		fieldModel.addVetoableChangeListener(this);
	}
	refreshSpeciesContextSpecs();
	refreshReactionSpecs();
	firePropertyChange("model", oldValue, model);
}


/**
 * Sets the reactionSpecs property (cbit.vcell.mapping.ReactionSpec[]) value.
 * @param reactionSpecs The new value for the property.
 * @exception java.beans.PropertyVetoException The exception description.
 * @see #getReactionSpecs
 */
public void setReactionSpecs(ReactionSpec[] reactionSpecs) throws java.beans.PropertyVetoException {
	ReactionSpec[] oldValue = fieldReactionSpecs;
	fireVetoableChange("reactionSpecs", oldValue, reactionSpecs);
	fieldReactionSpecs = reactionSpecs;
	firePropertyChange("reactionSpecs", oldValue, reactionSpecs);
}


/**
 * Sets the reactionSpecs index property (cbit.vcell.mapping.ReactionSpec[]) value.
 * @param index The index value into the property array.
 * @param reactionSpecs The new value for the property.
 * @see #getReactionSpecs
 */
public void setReactionSpecs(int index, ReactionSpec reactionSpecs) {
	ReactionSpec oldValue = fieldReactionSpecs[index];
	fieldReactionSpecs[index] = reactionSpecs;
	if (oldValue != null && !oldValue.equals(reactionSpecs)) {
		firePropertyChange("reactionSpecs", null, fieldReactionSpecs);
	};
}


/**
 * Sets the speciesContextSpecs property (cbit.vcell.mapping.SpeciesContextSpec[]) value.
 * @param speciesContextSpecs The new value for the property.
 * @exception java.beans.PropertyVetoException The exception description.
 * @see #getSpeciesContextSpecs
 */
public void setSpeciesContextSpecs(SpeciesContextSpec[] speciesContextSpecs) throws java.beans.PropertyVetoException {
	SpeciesContextSpec[] oldValue = fieldSpeciesContextSpecs;
	fireVetoableChange("speciesContextSpecs", oldValue, speciesContextSpecs);
	for (int i = 0; i < oldValue.length; i++){
		oldValue[i].simulationContext = null;
	}
	fieldSpeciesContextSpecs = speciesContextSpecs;
	for (int i = 0; i < fieldSpeciesContextSpecs.length; i++){
		fieldSpeciesContextSpecs[i].simulationContext = simContext;
	}
	firePropertyChange("speciesContextSpecs", oldValue, speciesContextSpecs);
}

public void convertSpeciesIniCondition(boolean bUseConcentration) throws ExpressionException, PropertyVetoException
{
	for(int i = 0; i < getSpeciesContextSpecs().length; i++)
	{
		SpeciesContextSpecParameter iniConParam = getSpeciesContextSpecs()[i].getInitialConcentrationParameter();
		SpeciesContextSpecParameter iniPartParam = getSpeciesContextSpecs()[i].getInitialCountParameter();
		if(bUseConcentration && iniPartParam.getExpression() != null)
		{
			Expression covertedConcentration = getSpeciesContextSpecs()[i].convertParticlesToConcentration(iniPartParam.getExpression());
			iniConParam.setExpression(covertedConcentration);
			iniPartParam.setExpression(null);
		}
		else if(iniConParam.getExpression() != null)
		{
			Expression covertedAmount = getSpeciesContextSpecs()[i].convertConcentrationToParticles(iniConParam.getExpression());
			iniPartParam.setExpression(covertedAmount);
			iniConParam.setExpression(null);
		}
	}
}

public void setIniConditionParam(boolean bUseConcentration, Expression[] iniExp) throws MappingException, PropertyVetoException
{
	for(int i = 0; i < getSpeciesContextSpecs().length; i++)
	{
		SpeciesContextSpecParameter iniConParam = getSpeciesContextSpecs()[i].getInitialConcentrationParameter();
		SpeciesContextSpecParameter iniPartParam = getSpeciesContextSpecs()[i].getInitialCountParameter();
		try{
			if(bUseConcentration)
			{
				iniConParam.setExpression(iniExp[i]);
				iniPartParam.setExpression(null);
			}
			else
			{
				iniConParam.setExpression(null);
				iniPartParam.setExpression(iniExp[i]);
			}
		}catch(ExpressionBindingException e)
		{
			throw new MappingException(e.getMessage());
		}
	}
}

public void vetoableChange(java.beans.PropertyChangeEvent evt) throws java.beans.PropertyVetoException {
	
	if (evt.getSource() == getModel() && evt.getPropertyName().equals(Model.MODEL_PARAMETERS_PROPERTY_NAME)) {
		// check for speciesContextSpec proxy parameters, if any exists, veto 
		
		ModelParameter[] newModelParams = (ModelParameter[])evt.getNewValue();
		ModelParameter[] oldModelParams = (ModelParameter[])evt.getOldValue();
		ModelParameter modelParam = null;
		for (int i = 0; i < oldModelParams.length; i++) {
			if (!BeanUtils.arrayContains(newModelParams, oldModelParams[i])) {
				modelParam = oldModelParams[i];
			}
		}
		// use this missing model parameter (to be deleted) to determine if it is used in any speciesContextSpec parameters. 
		Vector<String> referencedSCSVector = new Vector<String>();
		if (modelParam != null) {
			for (int i=0;i<getSpeciesContextSpecs().length;i++){
				Parameter[] scsParams = getSpeciesContextSpecs(i).getParameters();
				for (int k = 0; k < scsParams.length; k++) {
					if (scsParams[k].getExpression() != null && 
						scsParams[k].getExpression().hasSymbol(modelParam.getName()) && 
						(getSpeciesContextSpecs(i).getProxyParameter(modelParam.getName()) != null)) {
						referencedSCSVector.add(getSpeciesContextSpecs(i).getSpeciesContext().getName());
						break;
					}
				}
			}
			// if there are any speciesContextSpecs referencing the global, list them all in error msg.
			if (referencedSCSVector.size() > 0) {
				String msg = "Model Parameter '" + modelParam.getName() + "' is used in the expression of the following speciesContext(s): ";
				for (int i = 0; i < referencedSCSVector.size(); i++) {
					msg = msg + "'" + referencedSCSVector.elementAt(i) + "'";
					if (i < referencedSCSVector.size()-1) {
						msg = msg + ", "; 
					} else {
						msg = msg + " ";
					}
				}
				msg = msg + " in application '" + getSimulationContext().getName() + "'. \n\nCannot delete '" + modelParam.getName() + "'.";
				throw new PropertyVetoException(msg,evt);
			}
		}
	}
}

}