// Code generated by jtd-codegen for Java + Jackson v0.2.1

package org.vcell.optimization.jtd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class CopasiOptimizationParameter {
    @JsonProperty("dataType")
    private CopasiOptimizationParameterDataType dataType;

    @JsonProperty("paramType")
    private CopasiOptimizationParameterParamType paramType;

    @JsonProperty("value")
    private Double value;

    public CopasiOptimizationParameter() {
    }

    /**
     * Getter for dataType.<p>
     */
    public CopasiOptimizationParameterDataType getDataType() {
        return dataType;
    }

    /**
     * Setter for dataType.<p>
     */
    public void setDataType(CopasiOptimizationParameterDataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Getter for paramType.<p>
     */
    public CopasiOptimizationParameterParamType getParamType() {
        return paramType;
    }

    /**
     * Setter for paramType.<p>
     */
    public void setParamType(CopasiOptimizationParameterParamType paramType) {
        this.paramType = paramType;
    }

    /**
     * Getter for value.<p>
     */
    public Double getValue() {
        return value;
    }

    /**
     * Setter for value.<p>
     */
    public void setValue(Double value) {
        this.value = value;
    }
}
