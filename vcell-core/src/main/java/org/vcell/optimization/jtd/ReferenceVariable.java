// Code generated by jtd-codegen for Java + Jackson v0.2.1

package org.vcell.optimization.jtd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ReferenceVariable {
    @JsonProperty("referenceVariableType")
    private ReferenceVariableReferenceVariableType referenceVariableType;

    @JsonProperty("varName")
    private String varName;

    public ReferenceVariable() {
    }

    /**
     * Getter for referenceVariableType.<p>
     */
    public ReferenceVariableReferenceVariableType getReferenceVariableType() {
        return referenceVariableType;
    }

    /**
     * Setter for referenceVariableType.<p>
     */
    public void setReferenceVariableType(ReferenceVariableReferenceVariableType referenceVariableType) {
        this.referenceVariableType = referenceVariableType;
    }

    /**
     * Getter for varName.<p>
     */
    public String getVarName() {
        return varName;
    }

    /**
     * Setter for varName.<p>
     */
    public void setVarName(String varName) {
        this.varName = varName;
    }
}
