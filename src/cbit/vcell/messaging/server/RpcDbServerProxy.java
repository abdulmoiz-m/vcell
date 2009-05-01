package cbit.vcell.messaging.server;
import org.vcell.util.BigString;
import org.vcell.util.DataAccessException;
import org.vcell.util.ObjectNotFoundException;
import org.vcell.util.MessageConstants.ServiceType;
import org.vcell.util.document.User;
import org.vcell.util.document.VersionableFamily;

import cbit.vcell.solver.ode.gui.SimulationStatus;
import cbit.vcell.solver.SolverResultSetInfo;
import cbit.vcell.mathmodel.MathModelMetaData;
import cbit.vcell.biomodel.BioModelMetaData;
import cbit.vcell.field.FieldDataDBOperationResults;
import cbit.vcell.field.FieldDataDBOperationSpec;
import cbit.vcell.server.UserRegistrationOP;
import cbit.vcell.server.UserRegistrationResults;
import cbit.vcell.messaging.JmsClientMessaging;
import cbit.vcell.modeldb.*;
import cbit.vcell.messaging.JmsUtils;

/**
 * Insert the type's description here.
 * Creation date: (12/5/2001 12:00:10 PM)
 * @author: Jim Schaff
 *
 * stateless database service for any user (should be thread safe ... reentrant)
 *
 */
public class RpcDbServerProxy extends AbstractRpcServerProxy implements cbit.vcell.server.UserMetaDbServer {
/**
 * DataServerProxy constructor comment.
 */
public RpcDbServerProxy(User argUser, JmsClientMessaging clientMessaging, org.vcell.util.SessionLog log) throws javax.jms.JMSException {
	super(argUser, clientMessaging, JmsUtils.getQueueDbReq(), log);
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.VCDocumentInfo curate(org.vcell.util.document.CurateSpec curateSpec) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException, java.rmi.RemoteException {
	return (org.vcell.util.document.VCDocumentInfo)rpc("curate",new Object[]{user,curateSpec});
}


public UserRegistrationResults userRegistrationOP(UserRegistrationOP userRegistrationOP) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException, java.rmi.RemoteException {
	return (UserRegistrationResults)rpc("userRegistrationOP",new Object[]{user,userRegistrationOP});
}

/**
 * Insert the method's description here.
 * Creation date: (4/29/2004 4:31:48 PM)
 * @param bioModelKey cbit.sql.KeyValue
 * @exception java.rmi.RemoteException The exception description.
 */
public void deleteBioModel(org.vcell.util.document.KeyValue bioModelKey) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	rpc("deleteBioModel",new Object[]{user, bioModelKey});
}


/**
 * Insert the method's description here.
 * Creation date: (4/29/2004 4:31:48 PM)
 * @param bioModelKey cbit.sql.KeyValue
 * @exception java.rmi.RemoteException The exception description.
 */
public FieldDataDBOperationResults fieldDataDBOperation(FieldDataDBOperationSpec fieldDataDBOperationSpec) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (FieldDataDBOperationResults)rpc("fieldDataDBOperation",new Object[]{user, fieldDataDBOperationSpec});
}


/**
 * Insert the method's description here.
 * Creation date: (4/29/2004 4:31:48 PM)
 * @param bioModelKey cbit.sql.KeyValue
 * @exception java.rmi.RemoteException The exception description.
 */
public void deleteGeometry(org.vcell.util.document.KeyValue geometryKey) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	rpc("deleteGeometry",new Object[]{user, geometryKey});
}


/**
 * Insert the method's description here.
 * Creation date: (4/29/2004 4:31:48 PM)
 * @param bioModelKey cbit.sql.KeyValue
 * @exception java.rmi.RemoteException The exception description.
 */
public void deleteMathModel(org.vcell.util.document.KeyValue mathModelKey) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	rpc("deleteMathModel",new Object[]{user, mathModelKey});
}


/**
* Insert the method's description here.
* Creation date: (10/22/2003 10:28:00 AM)
*/
public void deleteResultSetExport(org.vcell.util.document.KeyValue eleKey) throws org.vcell.util.DataAccessException {
	rpc("deleteResultSetExport",new Object[]{user, eleKey});
}


/**
 * Insert the method's description here.
 * Creation date: (4/29/2004 4:31:48 PM)
 * @param bioModelKey cbit.sql.KeyValue
 * @exception java.rmi.RemoteException The exception description.
 */
public void deleteVCImage(org.vcell.util.document.KeyValue imageKey) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	rpc("deleteVCImage",new Object[]{user, imageKey});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.numericstest.TestSuiteOPResults doTestSuiteOP(cbit.vcell.numericstest.TestSuiteOP tsop) throws org.vcell.util.DataAccessException, java.rmi.RemoteException {

	return (cbit.vcell.numericstest.TestSuiteOPResults ) rpc("doTestSuiteOP",new Object[] {user,tsop});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.ReferenceQueryResult findReferences(org.vcell.util.ReferenceQuerySpec rqs) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException, java.rmi.RemoteException {
	return (org.vcell.util.ReferenceQueryResult)rpc("findReferences",new Object[]{user,rqs});
}


/**
 * This method was created in VisualAge.
 * @return cbit.vcell.modeldb.VersionableFamily
 * @param vType cbit.sql.VersionableType
 * @param key cbit.sql.KeyValue
 */
public org.vcell.util.document.VersionableFamily getAllReferences(org.vcell.util.document.VersionableType vType, org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (VersionableFamily)rpc("getAllReferences",new Object[]{user, vType,key});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.BioModelInfo getBioModelInfo(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (org.vcell.util.document.BioModelInfo)rpc("getBioModelInfo",new Object[]{user,key});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.BioModelInfo[] getBioModelInfos(boolean bAll) throws org.vcell.util.DataAccessException {
	return (org.vcell.util.document.BioModelInfo[])rpc("getBioModelInfos",new Object[]{user, new Boolean(bAll)});
}


/**
 * This method was created in VisualAge.
 * @return Geometry
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.biomodel.BioModelMetaData getBioModelMetaData(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BioModelMetaData)rpc("getBioModelMetaData",new Object[]{user, key});
}


/**
 * This method was created in VisualAge.
 * @return Geometry
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.biomodel.BioModelMetaData[] getBioModelMetaDatas(boolean bAll) throws org.vcell.util.DataAccessException {
	return (BioModelMetaData[])rpc("getBioModelMetaDatas",new Object[]{user, new Boolean(bAll)});
}


/**
 * This method was created in VisualAge.
 * @return Geometry
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString getBioModelXML(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException {
	return (BigString)rpc("getBioModelXML",new Object[]{user, key});
}


/**
 * getBoundSpecies method comment.
 */
public cbit.vcell.dictionary.DBSpecies getBoundSpecies(cbit.vcell.dictionary.DBFormalSpecies dbfs) throws org.vcell.util.DataAccessException {
	return (cbit.vcell.dictionary.DBSpecies)rpc("getBoundSpecies",new Object[]{user, dbfs});
}


/**
 * getDatabaseSpecies method comment.
 */
public cbit.vcell.dictionary.DBFormalSpecies[] getDatabaseSpecies(java.lang.String likeString, boolean isBound, cbit.vcell.dictionary.FormalSpeciesType speciesType, int restrictSearch, int rowLimit, boolean bOnlyUser) throws org.vcell.util.DataAccessException {
	return (cbit.vcell.dictionary.DBFormalSpecies[])rpc("getDatabaseSpecies",new Object[]{user, likeString,new Boolean(isBound),speciesType,new Integer(restrictSearch),new Integer(rowLimit), new Boolean(bOnlyUser)});
}


/**
 * getDictionaryReactions method comment.
 */
public cbit.vcell.dictionary.ReactionDescription[] getDictionaryReactions(ReactionQuerySpec reactionQuerySpec) throws org.vcell.util.DataAccessException {
	return (cbit.vcell.dictionary.ReactionDescription[])rpc("getDictionaryReactions",new Object[]{user, reactionQuerySpec});
}


/**
 * Insert the method's description here.
 * Creation date: (12/5/2001 12:00:10 PM)
 * @return cbit.vcell.export.server.ExportLog
 * @param simKey cbit.sql.KeyValue
 */
public cbit.vcell.export.server.ExportLog getExportLog(org.vcell.util.document.KeyValue simulationKey) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (cbit.vcell.export.server.ExportLog)rpc("getExportLog",new Object[]{user, simulationKey});
}


/**
 * Insert the method's description here.
 * Creation date: (12/5/2001 12:00:10 PM)
 * @return cbit.vcell.export.server.ExportLog[]
 * @param simKey cbit.sql.KeyValue
 */
public cbit.vcell.export.server.ExportLog[] getExportLogs(boolean bAll) throws org.vcell.util.DataAccessException {
	return (cbit.vcell.export.server.ExportLog[])rpc("getExportLogs",new Object[]{user, new Boolean(bAll)});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.geometry.GeometryInfo getGeometryInfo(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (cbit.vcell.geometry.GeometryInfo)rpc("getGeometryInfo",new Object[]{user,key});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.geometry.GeometryInfo[] getGeometryInfos(boolean bAll) throws org.vcell.util.DataAccessException {
	return (cbit.vcell.geometry.GeometryInfo[])rpc("getGeometryInfos",new Object[]{user, new Boolean(bAll)});
}


/**
 * This method was created in VisualAge.
 * @return Geometry
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString getGeometryXML(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException {
	return (BigString)rpc("getGeometryXML",new Object[]{user, key});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.MathModelInfo getMathModelInfo(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (org.vcell.util.document.MathModelInfo)rpc("getMathModelInfo",new Object[]{user,key});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.MathModelInfo[] getMathModelInfos(boolean bAll) throws org.vcell.util.DataAccessException {
	return (org.vcell.util.document.MathModelInfo[])rpc("getMathModelInfos",new Object[]{user, new Boolean(bAll)});
}


/**
 * This method was created in VisualAge.
 * @return Geometry
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.mathmodel.MathModelMetaData getMathModelMetaData(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (MathModelMetaData)rpc("getMathModelMetaData",new Object[]{user, key});
}


/**
 * This method was created in VisualAge.
 * @return MathModelMetaData[]
 * @param bAll boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.mathmodel.MathModelMetaData[] getMathModelMetaDatas(boolean bAll) throws org.vcell.util.DataAccessException {
	return (MathModelMetaData[])rpc("getMathModelMetaDatas",new Object[]{user, new Boolean(bAll)});
}


/**
 * This method was created in VisualAge.
 * @return Geometry
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString getMathModelXML(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException {
	return (BigString)rpc("getMathModelXML",new Object[]{user, key});
}


/**
 * Insert the method's description here.
 * Creation date: (6/10/2004 7:54:49 PM)
 * @return cbit.util.Preference
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.Preference[] getPreferences() throws org.vcell.util.DataAccessException {
	return (org.vcell.util.Preference[])rpc("getPreferences",new Object[]{user});
}


/**
 * getReactionStep method comment.
 */
public cbit.vcell.model.ReactionStep getReactionStep(org.vcell.util.document.KeyValue rxID) throws org.vcell.util.DataAccessException {
	return (cbit.vcell.model.ReactionStep)rpc("getReactionStep",new Object[]{user, rxID});
}


/**
 * getReactionStepInfos method comment.
 */
public cbit.vcell.model.ReactionStepInfo[] getReactionStepInfos(org.vcell.util.document.KeyValue[] reactionStepKeys) throws org.vcell.util.DataAccessException {
	return (cbit.vcell.model.ReactionStepInfo[])rpc("getReactionStepInfos",new Object[]{user, reactionStepKeys});
}


/**
 * Insert the method's description here.
 * Creation date: (12/5/2001 12:00:10 PM)
 * @return cbit.vcell.solver.SolverResultSetInfo
 * @param simKey cbit.sql.KeyValue
 */
public cbit.vcell.solver.SolverResultSetInfo[] getResultSetInfos(boolean bAll) throws org.vcell.util.DataAccessException {
	return (SolverResultSetInfo[])rpc("getResultSetInfos",new Object[]{user, new Boolean(bAll)});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public SimulationStatus[] getSimulationStatus(org.vcell.util.document.KeyValue simulationKeys[]) throws DataAccessException, ObjectNotFoundException {
	return (SimulationStatus[])rpc("getSimulationStatus",new Object[]{simulationKeys});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public SimulationStatus getSimulationStatus(org.vcell.util.document.KeyValue simulationKey) throws DataAccessException, ObjectNotFoundException {
	return (SimulationStatus)rpc("getSimulationStatus",new Object[]{simulationKey});
}


/**
 * This method was created in VisualAge.
 * @return Geometry
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString getSimulationXML(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException {
	return (BigString)rpc("getSimulationXML",new Object[]{user, key});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.numericstest.TestSuiteNew getTestSuite(java.math.BigDecimal getThisTS) throws org.vcell.util.DataAccessException, java.rmi.RemoteException {

	return (cbit.vcell.numericstest.TestSuiteNew ) rpc("getTestSuite",new Object[] {user,getThisTS});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.numericstest.TestSuiteInfoNew[] getTestSuiteInfos() throws org.vcell.util.DataAccessException, java.rmi.RemoteException {

	return (cbit.vcell.numericstest.TestSuiteInfoNew[] ) rpc("getTestSuiteInfos",new Object[] {user});
}


/**
 * getUserReactionDescriptions method comment.
 */
public cbit.vcell.dictionary.ReactionDescription[] getUserReactionDescriptions(ReactionQuerySpec reactionQuerySpec) throws org.vcell.util.DataAccessException {
	return (cbit.vcell.dictionary.ReactionDescription[])rpc("getUserReactionDescriptions",new Object[]{user, reactionQuerySpec});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.image.VCImageInfo getVCImageInfo(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (cbit.image.VCImageInfo)rpc("getVCImageInfo",new Object[]{user,key});
}


/**
 * This method was created in VisualAge.
 * @return GeometryInfo
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.image.VCImageInfo[] getVCImageInfos(boolean bAll)
    throws org.vcell.util.DataAccessException {
    return (cbit.image.VCImageInfo[]) rpc(
        "getVCImageInfos",
        new Object[] { user, new Boolean(bAll)});
}


/**
 * This method was created in VisualAge.
 * @return Geometry
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString getVCImageXML(org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException {
	return (BigString)rpc("getVCImageXML",new Object[]{user, key});
}


/**
 * getVCInfoContainer method comment.
 */
public VCInfoContainer getVCInfoContainer() throws org.vcell.util.DataAccessException {
	return (VCInfoContainer)rpc("getVCInfoContainer",new Object[]{user});
}


/**
 * This method was created in VisualAge.
 * @return void
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.VersionInfo groupAddUser(org.vcell.util.document.VersionableType vType, org.vcell.util.document.KeyValue key, java.lang.String addUserToGroup, boolean isHidden) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (org.vcell.util.document.VersionInfo)rpc("groupAddUser",new Object[]{user, vType,key,addUserToGroup,new Boolean(isHidden)});
}


/**
 * This method was created in VisualAge.
 * @return void
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.VersionInfo groupRemoveUser(org.vcell.util.document.VersionableType vType, org.vcell.util.document.KeyValue key, java.lang.String userRemoveFromGroup, boolean isHiddenFromOwner) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (org.vcell.util.document.VersionInfo)rpc("groupRemoveUser",new Object[]{user, vType,key,userRemoveFromGroup,new Boolean(isHiddenFromOwner)});
}


/**
 * This method was created in VisualAge.
 * @return void
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.VersionInfo groupSetPrivate(org.vcell.util.document.VersionableType vType, org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (org.vcell.util.document.VersionInfo)rpc("groupSetPrivate",new Object[]{user, vType,key});
}


/**
 * This method was created in VisualAge.
 * @return void
 * @param key KeyValue
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.document.VersionInfo groupSetPublic(org.vcell.util.document.VersionableType vType, org.vcell.util.document.KeyValue key) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (org.vcell.util.document.VersionInfo)rpc("groupSetPublic",new Object[]{user, vType,key});
}


/**
 * Insert the method's description here.
 * Creation date: (6/10/2004 7:54:49 PM)
 * @param preferences cbit.util.Preference[]
 * @exception java.rmi.RemoteException The exception description.
 */
public void replacePreferences(org.vcell.util.Preference[] preferences) throws org.vcell.util.DataAccessException {
	rpc("replacePreferences",new Object[]{user, preferences});
}


/**
 * Insert the method's description here.
 * Creation date: (12/5/2001 9:39:03 PM)
 * @return java.lang.Object
 * @param methodName java.lang.String
 * @param args java.lang.Object[]
 * @exception java.lang.Exception The exception description.
 */
private Object rpc(String methodName, Object[] args) throws org.vcell.util.ObjectNotFoundException, DataAccessException {
	try {
		return rpc(ServiceType.DB, methodName, args, true);
	} catch (org.vcell.util.ObjectNotFoundException ex) {
		log.exception(ex);
		throw ex;
	} catch (DataAccessException ex) {
		log.exception(ex);
		throw ex;
	} catch (RuntimeException e){
		log.exception(e);
		throw e;
	} catch (Exception e){
		log.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString saveBioModel(BigString bioModelXML, String independentSims[]) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BigString)rpc("saveBioModel",new Object[]{user, bioModelXML, independentSims});
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString saveBioModelAs(BigString bioModelXML, java.lang.String newName, String independentSims[]) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BigString)rpc("saveBioModelAs",new Object[]{user, bioModelXML, newName, independentSims});
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString saveGeometry(BigString geometryXML) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BigString)rpc("saveGeometry",new Object[]{user, geometryXML});
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString saveGeometryAs(BigString geometryXML, java.lang.String newName) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BigString)rpc("saveGeometryAs",new Object[]{user, geometryXML, newName});
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString saveMathModel(BigString mathModelXML, String independentSims[]) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BigString)rpc("saveMathModel",new Object[]{user, mathModelXML, independentSims});
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString saveMathModelAs(BigString mathModelXML, java.lang.String newName, String independentSims[]) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BigString)rpc("saveMathModelAs",new Object[]{user, mathModelXML, newName, independentSims});
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public org.vcell.util.BigString saveSimulation(org.vcell.util.BigString simulationXML, boolean bForceIndependent) throws DataAccessException {
	return (BigString)rpc("saveSimulation",new Object[]{user, simulationXML, new Boolean(bForceIndependent)});
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString saveVCImage(BigString vcImageXML) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BigString)rpc("saveVCImage",new Object[]{user, vcImageXML});
}


/**
 * This method was created in VisualAge.
 * @return Versionable
 * @param versionable Versionable
 * @param bVersion boolean
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public BigString saveVCImageAs(BigString vcImageXML, java.lang.String newName) throws org.vcell.util.DataAccessException, org.vcell.util.ObjectNotFoundException {
	return (BigString)rpc("saveVCImageAs",new Object[]{user, vcImageXML, newName});
}
}