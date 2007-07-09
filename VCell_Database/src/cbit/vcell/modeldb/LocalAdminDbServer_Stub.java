// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package cbit.vcell.modeldb;

public final class LocalAdminDbServer_Stub
    extends java.rmi.server.RemoteStub
    implements AdminDatabaseServer, java.rmi.Remote
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_getSimulationJobStatus_0;
    private static java.lang.reflect.Method $method_getSimulationJobStatus_1;
    private static java.lang.reflect.Method $method_getSimulationJobStatus_2;
    private static java.lang.reflect.Method $method_getUser_3;
    private static java.lang.reflect.Method $method_getUser_4;
    private static java.lang.reflect.Method $method_getUserFromSimulationKey_5;
    private static java.lang.reflect.Method $method_getUserInfo_6;
    private static java.lang.reflect.Method $method_getUserInfos_7;
    private static java.lang.reflect.Method $method_insertSimulationJobStatus_8;
    private static java.lang.reflect.Method $method_insertUserInfo_9;
    private static java.lang.reflect.Method $method_updateSimulationJobStatus_10;
    private static java.lang.reflect.Method $method_updateUserInfo_11;
    
    static {
	try {
	    $method_getSimulationJobStatus_0 = AdminDatabaseServer.class.getMethod("getSimulationJobStatus", new java.lang.Class[] {org.vcell.util.document.KeyValue.class, int.class});
	    $method_getSimulationJobStatus_1 = AdminDatabaseServer.class.getMethod("getSimulationJobStatus", new java.lang.Class[] {java.lang.String.class});
	    $method_getSimulationJobStatus_2 = AdminDatabaseServer.class.getMethod("getSimulationJobStatus", new java.lang.Class[] {boolean.class, org.vcell.util.document.User.class});
	    $method_getUser_3 = AdminDatabaseServer.class.getMethod("getUser", new java.lang.Class[] {java.lang.String.class});
	    $method_getUser_4 = AdminDatabaseServer.class.getMethod("getUser", new java.lang.Class[] {java.lang.String.class, java.lang.String.class});
	    $method_getUserFromSimulationKey_5 = AdminDatabaseServer.class.getMethod("getUserFromSimulationKey", new java.lang.Class[] {org.vcell.util.document.KeyValue.class});
	    $method_getUserInfo_6 = AdminDatabaseServer.class.getMethod("getUserInfo", new java.lang.Class[] {org.vcell.util.document.KeyValue.class});
	    $method_getUserInfos_7 = AdminDatabaseServer.class.getMethod("getUserInfos", new java.lang.Class[] {});
	    $method_insertSimulationJobStatus_8 = AdminDatabaseServer.class.getMethod("insertSimulationJobStatus", new java.lang.Class[] {cbit.rmi.event.SimulationJobStatus.class});
	    $method_insertUserInfo_9 = AdminDatabaseServer.class.getMethod("insertUserInfo", new java.lang.Class[] {org.vcell.util.document.UserInfo.class});
	    $method_updateSimulationJobStatus_10 = AdminDatabaseServer.class.getMethod("updateSimulationJobStatus", new java.lang.Class[] {cbit.rmi.event.SimulationJobStatus.class, cbit.rmi.event.SimulationJobStatus.class});
	    $method_updateUserInfo_11 = AdminDatabaseServer.class.getMethod("updateUserInfo", new java.lang.Class[] {org.vcell.util.document.UserInfo.class});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public LocalAdminDbServer_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of getSimulationJobStatus(KeyValue, int)
    public cbit.rmi.event.SimulationJobStatus getSimulationJobStatus(org.vcell.util.document.KeyValue $param_KeyValue_1, int $param_int_2)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getSimulationJobStatus_0, new java.lang.Object[] {$param_KeyValue_1, new java.lang.Integer($param_int_2)}, 3258198114365414872L);
	    return ((cbit.rmi.event.SimulationJobStatus) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getSimulationJobStatus(String)
    public java.util.List getSimulationJobStatus(java.lang.String $param_String_1)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getSimulationJobStatus_1, new java.lang.Object[] {$param_String_1}, -3992308848836386568L);
	    return ((java.util.List) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getSimulationJobStatus(boolean, User)
    public cbit.rmi.event.SimulationJobStatus[] getSimulationJobStatus(boolean $param_boolean_1, org.vcell.util.document.User $param_User_2)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getSimulationJobStatus_2, new java.lang.Object[] {new java.lang.Boolean($param_boolean_1), $param_User_2}, 1316770442574823816L);
	    return ((cbit.rmi.event.SimulationJobStatus[]) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUser(String)
    public org.vcell.util.document.User getUser(java.lang.String $param_String_1)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUser_3, new java.lang.Object[] {$param_String_1}, -5876487373133529709L);
	    return ((org.vcell.util.document.User) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUser(String, String)
    public org.vcell.util.document.User getUser(java.lang.String $param_String_1, java.lang.String $param_String_2)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUser_4, new java.lang.Object[] {$param_String_1, $param_String_2}, -6257811118775427044L);
	    return ((org.vcell.util.document.User) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUserFromSimulationKey(KeyValue)
    public org.vcell.util.document.User getUserFromSimulationKey(org.vcell.util.document.KeyValue $param_KeyValue_1)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUserFromSimulationKey_5, new java.lang.Object[] {$param_KeyValue_1}, -9117084100856111954L);
	    return ((org.vcell.util.document.User) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUserInfo(KeyValue)
    public org.vcell.util.document.UserInfo getUserInfo(org.vcell.util.document.KeyValue $param_KeyValue_1)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUserInfo_6, new java.lang.Object[] {$param_KeyValue_1}, 5888400704317291773L);
	    return ((org.vcell.util.document.UserInfo) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getUserInfos()
    public org.vcell.util.document.UserInfo[] getUserInfos()
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getUserInfos_7, null, 4275675527704489443L);
	    return ((org.vcell.util.document.UserInfo[]) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of insertSimulationJobStatus(SimulationJobStatus)
    public cbit.rmi.event.SimulationJobStatus insertSimulationJobStatus(cbit.rmi.event.SimulationJobStatus $param_SimulationJobStatus_1)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_insertSimulationJobStatus_8, new java.lang.Object[] {$param_SimulationJobStatus_1}, 6612250165645515978L);
	    return ((cbit.rmi.event.SimulationJobStatus) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of insertUserInfo(UserInfo)
    public org.vcell.util.document.UserInfo insertUserInfo(org.vcell.util.document.UserInfo $param_UserInfo_1)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_insertUserInfo_9, new java.lang.Object[] {$param_UserInfo_1}, 9077131507998373534L);
	    return ((org.vcell.util.document.UserInfo) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of updateSimulationJobStatus(SimulationJobStatus, SimulationJobStatus)
    public cbit.rmi.event.SimulationJobStatus updateSimulationJobStatus(cbit.rmi.event.SimulationJobStatus $param_SimulationJobStatus_1, cbit.rmi.event.SimulationJobStatus $param_SimulationJobStatus_2)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_updateSimulationJobStatus_10, new java.lang.Object[] {$param_SimulationJobStatus_1, $param_SimulationJobStatus_2}, 5376187929673614080L);
	    return ((cbit.rmi.event.SimulationJobStatus) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of updateUserInfo(UserInfo)
    public org.vcell.util.document.UserInfo updateUserInfo(org.vcell.util.document.UserInfo $param_UserInfo_1)
	throws org.vcell.util.DataAccessException, java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_updateUserInfo_11, new java.lang.Object[] {$param_UserInfo_1}, 1504983660871353769L);
	    return ((org.vcell.util.document.UserInfo) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (org.vcell.util.DataAccessException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
