/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.message.server.bootstrap;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.vcell.util.DataAccessException;
import org.vcell.util.SessionLog;
import org.vcell.util.document.UserLoginInfo;
import org.vcell.util.document.VCDataIdentifier;
import org.vcell.vis.io.VtuFileContainer;
import org.vcell.vis.io.VtuVarInfo;

import cbit.plot.PlotData;
import cbit.rmi.event.ExportEvent;
import cbit.vcell.export.server.ExportSpecs;
import cbit.vcell.field.io.FieldDataFileOperationResults;
import cbit.vcell.field.io.FieldDataFileOperationSpec;
import cbit.vcell.message.VCMessageSession;
import cbit.vcell.server.DataSetController;
import cbit.vcell.simdata.DataIdentifier;
import cbit.vcell.simdata.DataOperation;
import cbit.vcell.simdata.DataOperationResults;
import cbit.vcell.simdata.DataSetMetadata;
import cbit.vcell.simdata.DataSetTimeSeries;
import cbit.vcell.simdata.OutputContext;
import cbit.vcell.simdata.ParticleDataBlock;
import cbit.vcell.simdata.SimDataBlock;
import cbit.vcell.simdata.SpatialSelection;
import cbit.vcell.solvers.CartesianMesh;

/**
 * This interface was generated by a SmartGuide.
 * 
 */
public class LocalDataSetControllerMessaging extends UnicastRemoteObject implements DataSetController {
    private RpcDataServerProxy dataServerProxy = null;
    private SessionLog sessionLog = null;
    private boolean bClosed = false;

/**
 * This method was created by a SmartGuide.
 */
public LocalDataSetControllerMessaging (UserLoginInfo userLoginInfo, VCMessageSession vcMessageSession, SessionLog sLog, int rmiPort) throws RemoteException {
	super(rmiPort);
	this.sessionLog = sLog;
	this.dataServerProxy = new RpcDataServerProxy(userLoginInfo, vcMessageSession, sessionLog);
}



public FieldDataFileOperationResults fieldDataFileOperation(FieldDataFileOperationSpec fieldDataFileOperationSpec) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.fieldDataFileOperationSpec(...)");
	checkClosed();
	try {
		return dataServerProxy.fieldDataFileOperation(fieldDataFileOperationSpec);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created by a SmartGuide.
 * @return java.lang.String[]
 * @throws RemoteException 
 */
public DataIdentifier[] getDataIdentifiers(OutputContext outputContext,VCDataIdentifier vcdID) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getDataIdentifiers(vcdID=" + vcdID + ")");
	checkClosed();
	try {
		return dataServerProxy.getDataIdentifiers(outputContext,vcdID);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}

/**
 * This method was created by a SmartGuide.
 * @return double[]
 * @throws RemoteException 
 */
public double[] getDataSetTimes(VCDataIdentifier vcdID) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getDataSetTimes(vcdID=" + vcdID + ")");
	checkClosed();
	try {
		return dataServerProxy.getDataSetTimes(vcdID);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * Insert the method's description here.
 * Creation date: (2/26/2004 1:05:01 PM)
 * @param function cbit.vcell.math.Function
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */

public cbit.vcell.solver.AnnotatedFunction[] getFunctions(OutputContext outputContext,org.vcell.util.document.VCDataIdentifier vcdataID) throws org.vcell.util.DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getFunctions(vcdataID=" + vcdataID + ")");
	checkClosed();
	try {
		return dataServerProxy.getFunctions(outputContext,vcdataID);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.plot.PlotData
 * @param varName java.lang.String
 * @param spatialSelection cbit.vcell.simdata.gui.SpatialSelection
 * @throws RemoteException 
 */
public PlotData getLineScan(OutputContext outputContext,VCDataIdentifier vcdID, String varName, double time, SpatialSelection spatialSelection) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getLineScan(vcdID=" + vcdID + ", " + varName + ", " + time + ", at " + spatialSelection+")");
	checkClosed();
	try {
		return dataServerProxy.getLineScan(outputContext,vcdID, varName, time, spatialSelection);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created by a SmartGuide.
 * @return int[]
 * @throws RemoteException 
 */
public CartesianMesh getMesh(VCDataIdentifier vcdID) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getMesh(vcdID=" + vcdID + ")");
	checkClosed();
	try {
		return dataServerProxy.getMesh(vcdID);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * Insert the method's description here.
 * Creation date: (1/14/00 11:20:51 AM)
 * @return cbit.vcell.export.data.ODESimData
 * @exception org.vcell.util.DataAccessException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public cbit.vcell.solver.ode.ODESimData getODEData(VCDataIdentifier vcdID) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getODEData(vcdID=" + vcdID + ")");
	checkClosed();
	try {
		return dataServerProxy.getODEData(vcdID);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created by a SmartGuide.
 * @return double[]
 * @param varName java.lang.String
 * @param time double
 * @throws RemoteException 
 */
public ParticleDataBlock getParticleDataBlock(VCDataIdentifier vcdID, double time) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getParticleDataBlock(vcdID=" + vcdID + ",time=" + time + ")");
	checkClosed();
	try {
		return dataServerProxy.getParticleDataBlock(vcdID,time);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created by a SmartGuide.
 * @return boolean
 * @throws RemoteException 
 */
public boolean getParticleDataExists(VCDataIdentifier vcdID) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getParticleDataExists(vcdID=" + vcdID + ")");
	checkClosed();
	try {
		return dataServerProxy.getParticleDataExists(vcdID);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created by a SmartGuide.
 * @return double[]
 * @param varName java.lang.String
 * @param time double
 * @throws RemoteException 
 */
public SimDataBlock getSimDataBlock(OutputContext outputContext,VCDataIdentifier vcdID, String varName, double time) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getSimDataBlock(vcdID=" + vcdID + ", varName=" + varName + ", time=" + time + ")");
	checkClosed();
	try {
		return dataServerProxy.getSimDataBlock(outputContext,vcdID,varName,time);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created by a SmartGuide.
 * @return double[]
 * @param varName java.lang.String
 * @param index int
 * @throws RemoteException 
 */
public org.vcell.util.document.TimeSeriesJobResults getTimeSeriesValues(OutputContext outputContext,VCDataIdentifier vcdID,org.vcell.util.document.TimeSeriesJobSpec timeSeriesJobSpec) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getTimeSeriesValues(vcdID=" + vcdID + ", " + timeSeriesJobSpec + ")");
	checkClosed();
	try {
		return dataServerProxy.getTimeSeriesValues(outputContext,vcdID,timeSeriesJobSpec);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created in VisualAge.
 * @param simInfo cbit.vcell.solver.SimulationInfo
 * @exception org.vcell.util.DataAccessException The exception description.
 * @throws RemoteException 
 */
public ExportEvent makeRemoteFile(OutputContext outputContext,ExportSpecs exportSpecs) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.makeRemoteFile(vcdID=" + exportSpecs.getVCDataIdentifier() + ")");
	checkClosed();
	try {
		return dataServerProxy.makeRemoteFile(outputContext,exportSpecs);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}



public DataOperationResults doDataOperation(DataOperation dataOperation) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.doDataOperation(...)");
	checkClosed();
	try {
		return dataServerProxy.doDataOperation(dataOperation);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}



@Override
public DataSetMetadata getDataSetMetadata(VCDataIdentifier vcdataID) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getDataSetMetadata(vcdataID=" + vcdataID + ")");
	checkClosed();
	try {
		return dataServerProxy.getDataSetMetadata(vcdataID);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}



@Override
public DataSetTimeSeries getDataSetTimeSeries(VCDataIdentifier vcdataID, String[] variableNames) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getDataSetMetadata(vcdataID=" + vcdataID + ")");
	checkClosed();
	try {
		return dataServerProxy.getDataSetTimeSeries(vcdataID, variableNames);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}

private void checkClosed() throws RemoteException {
	if (bClosed){
		sessionLog.print("LocalDataSetControllerMessaging closed");
		throw new ConnectException("LocalDataSetControllerMessaging closed, please reconnect");
	}
}


public void close() {
	//try {
		bClosed = true;
	//	UnicastRemoteObject.unexportObject(this, true);
	//} catch (NoSuchObjectException e) {
	//	e.printStackTrace();
	//}
}



@Override
public VtuFileContainer getEmptyVtuMeshFiles(VCDataIdentifier vcdataID, int timeIndex)	throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getEmptyVtuMeshFiles(vcdataID=" + vcdataID + ")");
	checkClosed();
	try {
		return dataServerProxy.getEmptyVtuMeshFiles(vcdataID, timeIndex);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


@Override
public double[] getVtuMeshData(OutputContext outputContext, VCDataIdentifier vcdataID, VtuVarInfo var, double time)	throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getVtuMeshData(vcdataID=" + vcdataID + ", var=" + var.name + ", time=" + time + ")");
	checkClosed();
	try {
		return dataServerProxy.getVtuMeshData(outputContext, vcdataID, var, time);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}



@Override
public VtuVarInfo[] getVtuVarInfos(OutputContext outputContext, VCDataIdentifier vcdataID) throws DataAccessException, RemoteException {
	sessionLog.print("LocalDataSetControllerMessaging.getVtuMeshFiles(vcdataID=" + vcdataID + ")");
	checkClosed();
	try {
		return dataServerProxy.getVtuVarInfos(outputContext, vcdataID);
	} catch (DataAccessException e){
		sessionLog.exception(e);
		throw e;
	} catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}

}
