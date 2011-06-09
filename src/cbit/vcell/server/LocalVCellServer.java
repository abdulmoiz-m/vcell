package cbit.vcell.server;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;

import org.vcell.util.BeanUtils;
import org.vcell.util.CacheStatus;
import org.vcell.util.DataAccessException;
import org.vcell.util.PropertyLoader;
import org.vcell.util.SessionLog;
import org.vcell.util.StdoutSessionLog;
import org.vcell.util.document.User;

import cbit.vcell.client.desktop.biomodel.VCellErrorMessages;
import cbit.vcell.export.server.ExportServiceImpl;
import cbit.vcell.messaging.JmsConnectionFactory;
import cbit.vcell.messaging.server.LocalVCellConnectionMessaging;
import cbit.vcell.simdata.Cachetable;
import cbit.vcell.simdata.DataSetControllerImpl;
/**
 * This class was generated by a SmartGuide.
 * 
 */
@SuppressWarnings("serial")
public class LocalVCellServer extends UnicastRemoteObject implements VCellServer {
	private java.util.Vector<VCellConnection> vcellConnectionList = new Vector<VCellConnection>();
	private String hostName = null;
	private AdminDatabaseServer adminDbServer = null;
	private SessionLog sessionLog = null;
	private Cachetable dataCachetable = null;
	private DataSetControllerImpl dscImpl = null;
	private SimulationControllerImpl simControllerImpl = null;
	private JmsConnectionFactory fieldJmsConnFactory = null;
	private ExportServiceImpl exportServiceImpl = null;
	private java.util.Date bootTime = new java.util.Date();

	private long CLEANUP_INTERVAL = 600*1000;	
	
/**
 * This method was created by a SmartGuide.
 * @exception java.rmi.RemoteException The exception description.
 */
public LocalVCellServer(String argHostName, JmsConnectionFactory jmsConnFactory, AdminDatabaseServer dbServer) throws RemoteException, FileNotFoundException {
	super(PropertyLoader.getIntProperty(PropertyLoader.rmiPortVCellServer,0));
	this.hostName = argHostName;
	this.fieldJmsConnFactory = jmsConnFactory;
	adminDbServer = dbServer;
	this.sessionLog = new StdoutSessionLog(PropertyLoader.ADMINISTRATOR_ACCOUNT);
	this.dataCachetable = new Cachetable(10*Cachetable.minute);
	this.dscImpl = new DataSetControllerImpl(sessionLog,dataCachetable, 
			new File(PropertyLoader.getRequiredProperty(PropertyLoader.primarySimDataDirProperty)), 
			new File(PropertyLoader.getRequiredProperty(PropertyLoader.secondarySimDataDirProperty)));
	this.simControllerImpl = new SimulationControllerImpl(sessionLog,adminDbServer, this);
	this.exportServiceImpl = new ExportServiceImpl(sessionLog);
	
	if (fieldJmsConnFactory != null) {
		Thread cleanupThread = new Thread() { 
			public void run() {
				setName("CleanupThread");
				cleanupConnections();
			}
		};
		cleanupThread.start();
	}
}

/**
 * This method was created in VisualAge.
 * @param userid java.lang.String
 * @param password java.lang.String
 */
private synchronized void addVCellConnection(UserLoginInfo userLoginInfo) throws RemoteException, java.sql.SQLException, FileNotFoundException, javax.jms.JMSException {
	if (getVCellConnection0(userLoginInfo.getUser()) == null) {
		VCellConnection localConn = null;
		if (fieldJmsConnFactory == null){
			localConn = new LocalVCellConnection(userLoginInfo, hostName, new StdoutSessionLog(userLoginInfo.getUser().getName()), this);
		} else {
			localConn = new LocalVCellConnectionMessaging(userLoginInfo, hostName, new StdoutSessionLog(userLoginInfo.getUser().getName()), fieldJmsConnFactory, this);
		}
		vcellConnectionList.addElement(localConn);
	}
}


/**
 * Insert the method's description here.
 * Creation date: (4/16/2004 10:19:42 AM)
 */
public void cleanupConnections() {	
	if (fieldJmsConnFactory == null) {
		return;
	}
	
	while (true) {
		try {
			Thread.sleep(CLEANUP_INTERVAL);
		} catch (InterruptedException ex) {
		}

		sessionLog.print("Starting to clean up stale connections...");
		VCellConnection[] connections = new VCellConnection[vcellConnectionList.size()];
		vcellConnectionList.copyInto(connections);

		for (int i = 0; i < connections.length; i++){
			try {
				if (connections[i] instanceof LocalVCellConnectionMessaging) {
					LocalVCellConnectionMessaging messagingConnection = (LocalVCellConnectionMessaging)connections[i];

					if (messagingConnection != null && messagingConnection.isTimeout()) {
						synchronized (this) {
							vcellConnectionList.remove(messagingConnection);
							messagingConnection.close();							
						}
						sessionLog.print("Removed connection from " + messagingConnection.getUserLoginInfo().getUser());						
					}

				}
			} catch (Throwable ex) {
				sessionLog.exception(ex);
			}
		}
	}
}

/**
 * This method was created in VisualAge.
 * @return cbit.vcell.server.AdminDatabaseServer
 */
public AdminDatabaseServer getAdminDatabaseServer() {
	try {
		return adminDbServer;
	}catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created in VisualAge.
 * @return cbit.vcell.simdata.CacheStatus
 */
public CacheStatus getCacheStatus() {
	try {
		return dataCachetable.getCacheStatus();
	}catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created in VisualAge.
 * @return cbit.vcell.server.ConnectionPoolStatus
 * @exception java.rmi.RemoteException The exception description.
 */
public User[] getConnectedUsers() {
	try {
		Vector<User> userList = new Vector<User>();
		for (VCellConnection vcConn : vcellConnectionList) {
			if (!userList.contains(vcConn.getUserLoginInfo().getUser())){
				userList.addElement(vcConn.getUserLoginInfo().getUser());
			}
		}
		return (User[])BeanUtils.getArray(userList,User.class);
	}catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}

/**
 * This method was created in VisualAge.
 * @return cbit.vcell.simdata.DataSetControllerImpl
 */
public DataSetControllerImpl getDataSetControllerImpl() {
	return dscImpl;
}

/**
 * Insert the method's description here.
 * Creation date: (3/29/2001 4:04:58 PM)
 * @return cbit.vcell.export.server.ExportServiceImpl
 */
public ExportServiceImpl getExportServiceImpl() {
	return exportServiceImpl;
}

/**
 * Insert the method's description here.
 * Creation date: (12/9/2002 12:58:00 AM)
 * @return cbit.vcell.server.ServerInfo
 */
public ServerInfo getServerInfo() {
	return new ServerInfo(hostName,getCacheStatus(),getConnectedUsers());
}

/**
 * This method was created in VisualAge.
 * @return cbit.vcell.simdata.DataSetControllerImpl
 */
SimulationControllerImpl getSimulationControllerImpl() {
	return simControllerImpl;
}

/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.server.DataSetController
 * @exception java.lang.Exception The exception description.
 */
public VCellConnection getVCellConnection(User user) {
	try {
		return getVCellConnection0(user);
	}catch (Throwable e){
		sessionLog.exception(e);
		throw new RuntimeException(e.getMessage());
	}
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.server.DataSetController
 * @exception java.lang.Exception The exception description.
 */
VCellConnection getVCellConnection(UserLoginInfo userLoginInfo) throws RemoteException, java.sql.SQLException, DataAccessException, FileNotFoundException, AuthenticationException, javax.jms.JMSException {
	VCellConnection localConnection = null;
	//Authenticate User
	User user = null;
	try{
		synchronized (adminDbServer) {
			user = adminDbServer.getUser(userLoginInfo.getUserName(),userLoginInfo.getPassword());
			if (user == null){
				throw new AuthenticationException(VCellErrorMessages.getErrorMessage(VCellErrorMessages.AUTHEN_FAIL_MESSAGE, userLoginInfo.getUserName()));
			}
			userLoginInfo.setUser(user);
		}
	}catch(Exception e){
		throw new DataAccessException(VCellErrorMessages.NETWORK_FAIL_MESSAGE, e);
	}
	//
	// get existing VCellConnection
	//
	localConnection = getVCellConnection0(user);

	//
	// if doesn't exist, create new one
	//
	if (localConnection == null) {
		addVCellConnection(userLoginInfo);
		localConnection = getVCellConnection0(user);
		if (localConnection==null){
			sessionLog.print("LocalVCellServer.getVCellConnecytion("+user.getName()+") unable to create VCellConnection");
			throw new DataAccessException("unable to create VCellConnection");
		}
	}

	//
	//Update UserStat.  Do not fail login if UserStat fails.
	//
	try{
		getAdminDatabaseServer().updateUserStat(userLoginInfo);
	}catch(Exception e){
		e.printStackTrace();
		//Ignore
	}
	return localConnection;
}


/**
 * This method was created by a SmartGuide.
 * @return cbit.vcell.server.DataSetController
 * @exception java.lang.Exception The exception description.
 */
private synchronized VCellConnection getVCellConnection0(User user) {
	//
	// Lookup existing VCellConnections
	//
	for (VCellConnection vcc : vcellConnectionList) {
		if (vcc instanceof LocalVCellConnection){
			LocalVCellConnection lvcc = (LocalVCellConnection)vcc;
			if (lvcc.getUserLoginInfo().getUser().compareEqual(user)) {
				return lvcc;
			}
		}else if (vcc instanceof LocalVCellConnectionMessaging){
			LocalVCellConnectionMessaging lvccm = (LocalVCellConnectionMessaging)vcc;
			if (lvccm.getUserLoginInfo().getUser().compareEqual(user)) {
				return lvccm;
			}
		}
	}

	return null;
}

public Date getBootTime() throws RemoteException {
	return bootTime;
}
}