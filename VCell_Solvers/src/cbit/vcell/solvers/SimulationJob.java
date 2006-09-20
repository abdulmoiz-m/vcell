package cbit.vcell.solvers;

import cbit.vcell.solver.Simulation;

public class SimulationJob implements java.io.Serializable {
	private Simulation workingSim = null;
	private int jobIndex = -1;				// expect non-negative value.

/**
 * Insert the method's description here.
 * Creation date: (10/7/2005 4:50:05 PM)
 * @param masterSim cbit.vcell.solver.Simulation
 * @param jobIndex int
 */
public SimulationJob(Simulation masterSim, int jobIndex) {
	if (jobIndex<0){
		throw new RuntimeException("unexpected simulation jobIndex < 0");
	}
	workingSim = Simulation.createWorkingSim(masterSim, jobIndex);
	this.jobIndex = jobIndex;
}


/**
 * Insert the method's description here.
 * Creation date: (10/14/2005 12:09:29 PM)
 * @return java.lang.String
 */
public static String createSimulationJobID(String simulationID, int jobIndex) {
	return simulationID+"_"+jobIndex+"_";
}


/**
 * Insert the method's description here.
 * Creation date: (10/7/2005 4:52:17 PM)
 * @return int
 */
public int getJobIndex() {
	return jobIndex;
}


/**
 * Insert the method's description here.
 * Creation date: (10/14/2005 12:09:29 PM)
 * @return java.lang.String
 */
public String getSimulationJobID() {
	return createSimulationJobID(getWorkingSim().getSimulationID(),jobIndex);
}


/**
 * Insert the method's description here.
 * Creation date: (10/14/2005 11:45:42 AM)
 * @return cbit.vcell.server.VCDataIdentifier
 */
public VCSimulationDataIdentifier getVCDataIdentifier() {
	return new VCSimulationDataIdentifier(workingSim.getSimulationInfo().getAuthoritativeVCSimulationIdentifier(), jobIndex);
}


/**
 * Insert the method's description here.
 * Creation date: (10/7/2005 4:48:57 PM)
 * @return cbit.vcell.solver.Simulation
 */
public Simulation getWorkingSim() {
	return workingSim;
}
}