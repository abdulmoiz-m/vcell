// Code generated by jtd-codegen for Java + Jackson v0.2.1

package org.vcell.optimization.jtd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;

@JsonSerialize
public class OptResultSet {
    @JsonProperty("numFunctionEvaluations")
    private Integer numFunctionEvaluations;

    @JsonProperty("objectiveFunction")
    private Double objectiveFunction;

    @JsonProperty("optParameterValues")
    private Map<String, Double> optParameterValues;

    public OptResultSet() {
    }

    /**
     * Getter for numFunctionEvaluations.<p>
     */
    public Integer getNumFunctionEvaluations() {
        return numFunctionEvaluations;
    }

    /**
     * Setter for numFunctionEvaluations.<p>
     */
    public void setNumFunctionEvaluations(Integer numFunctionEvaluations) {
        this.numFunctionEvaluations = numFunctionEvaluations;
    }

    /**
     * Getter for objectiveFunction.<p>
     */
    public Double getObjectiveFunction() {
        return objectiveFunction;
    }

    /**
     * Setter for objectiveFunction.<p>
     */
    public void setObjectiveFunction(Double objectiveFunction) {
        this.objectiveFunction = objectiveFunction;
    }

    /**
     * Getter for optParameterValues.<p>
     */
    public Map<String, Double> getOptParameterValues() {
        return optParameterValues;
    }

    /**
     * Setter for optParameterValues.<p>
     */
    public void setOptParameterValues(Map<String, Double> optParameterValues) {
        this.optParameterValues = optParameterValues;
    }
}