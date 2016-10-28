/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.vcell.vis.vismesh.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-10-27")
public class FiniteVolumeIndexData implements org.apache.thrift.TBase<FiniteVolumeIndexData, FiniteVolumeIndexData._Fields>, java.io.Serializable, Cloneable, Comparable<FiniteVolumeIndexData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FiniteVolumeIndexData");

  private static final org.apache.thrift.protocol.TField DOMAIN_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("domainName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField FINITE_VOLUME_INDICES_FIELD_DESC = new org.apache.thrift.protocol.TField("finiteVolumeIndices", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FiniteVolumeIndexDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FiniteVolumeIndexDataTupleSchemeFactory());
  }

  public String domainName; // required
  public List<FiniteVolumeIndex> finiteVolumeIndices; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DOMAIN_NAME((short)1, "domainName"),
    FINITE_VOLUME_INDICES((short)2, "finiteVolumeIndices");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // DOMAIN_NAME
          return DOMAIN_NAME;
        case 2: // FINITE_VOLUME_INDICES
          return FINITE_VOLUME_INDICES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DOMAIN_NAME, new org.apache.thrift.meta_data.FieldMetaData("domainName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FINITE_VOLUME_INDICES, new org.apache.thrift.meta_data.FieldMetaData("finiteVolumeIndices", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, FiniteVolumeIndex.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FiniteVolumeIndexData.class, metaDataMap);
  }

  public FiniteVolumeIndexData() {
  }

  public FiniteVolumeIndexData(
    String domainName,
    List<FiniteVolumeIndex> finiteVolumeIndices)
  {
    this();
    this.domainName = domainName;
    this.finiteVolumeIndices = finiteVolumeIndices;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FiniteVolumeIndexData(FiniteVolumeIndexData other) {
    if (other.isSetDomainName()) {
      this.domainName = other.domainName;
    }
    if (other.isSetFiniteVolumeIndices()) {
      List<FiniteVolumeIndex> __this__finiteVolumeIndices = new ArrayList<FiniteVolumeIndex>(other.finiteVolumeIndices.size());
      for (FiniteVolumeIndex other_element : other.finiteVolumeIndices) {
        __this__finiteVolumeIndices.add(new FiniteVolumeIndex(other_element));
      }
      this.finiteVolumeIndices = __this__finiteVolumeIndices;
    }
  }

  public FiniteVolumeIndexData deepCopy() {
    return new FiniteVolumeIndexData(this);
  }

  @Override
  public void clear() {
    this.domainName = null;
    this.finiteVolumeIndices = null;
  }

  public String getDomainName() {
    return this.domainName;
  }

  public FiniteVolumeIndexData setDomainName(String domainName) {
    this.domainName = domainName;
    return this;
  }

  public void unsetDomainName() {
    this.domainName = null;
  }

  /** Returns true if field domainName is set (has been assigned a value) and false otherwise */
  public boolean isSetDomainName() {
    return this.domainName != null;
  }

  public void setDomainNameIsSet(boolean value) {
    if (!value) {
      this.domainName = null;
    }
  }

  public int getFiniteVolumeIndicesSize() {
    return (this.finiteVolumeIndices == null) ? 0 : this.finiteVolumeIndices.size();
  }

  public java.util.Iterator<FiniteVolumeIndex> getFiniteVolumeIndicesIterator() {
    return (this.finiteVolumeIndices == null) ? null : this.finiteVolumeIndices.iterator();
  }

  public void addToFiniteVolumeIndices(FiniteVolumeIndex elem) {
    if (this.finiteVolumeIndices == null) {
      this.finiteVolumeIndices = new ArrayList<FiniteVolumeIndex>();
    }
    this.finiteVolumeIndices.add(elem);
  }

  public List<FiniteVolumeIndex> getFiniteVolumeIndices() {
    return this.finiteVolumeIndices;
  }

  public FiniteVolumeIndexData setFiniteVolumeIndices(List<FiniteVolumeIndex> finiteVolumeIndices) {
    this.finiteVolumeIndices = finiteVolumeIndices;
    return this;
  }

  public void unsetFiniteVolumeIndices() {
    this.finiteVolumeIndices = null;
  }

  /** Returns true if field finiteVolumeIndices is set (has been assigned a value) and false otherwise */
  public boolean isSetFiniteVolumeIndices() {
    return this.finiteVolumeIndices != null;
  }

  public void setFiniteVolumeIndicesIsSet(boolean value) {
    if (!value) {
      this.finiteVolumeIndices = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DOMAIN_NAME:
      if (value == null) {
        unsetDomainName();
      } else {
        setDomainName((String)value);
      }
      break;

    case FINITE_VOLUME_INDICES:
      if (value == null) {
        unsetFiniteVolumeIndices();
      } else {
        setFiniteVolumeIndices((List<FiniteVolumeIndex>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DOMAIN_NAME:
      return getDomainName();

    case FINITE_VOLUME_INDICES:
      return getFiniteVolumeIndices();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DOMAIN_NAME:
      return isSetDomainName();
    case FINITE_VOLUME_INDICES:
      return isSetFiniteVolumeIndices();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FiniteVolumeIndexData)
      return this.equals((FiniteVolumeIndexData)that);
    return false;
  }

  public boolean equals(FiniteVolumeIndexData that) {
    if (that == null)
      return false;

    boolean this_present_domainName = true && this.isSetDomainName();
    boolean that_present_domainName = true && that.isSetDomainName();
    if (this_present_domainName || that_present_domainName) {
      if (!(this_present_domainName && that_present_domainName))
        return false;
      if (!this.domainName.equals(that.domainName))
        return false;
    }

    boolean this_present_finiteVolumeIndices = true && this.isSetFiniteVolumeIndices();
    boolean that_present_finiteVolumeIndices = true && that.isSetFiniteVolumeIndices();
    if (this_present_finiteVolumeIndices || that_present_finiteVolumeIndices) {
      if (!(this_present_finiteVolumeIndices && that_present_finiteVolumeIndices))
        return false;
      if (!this.finiteVolumeIndices.equals(that.finiteVolumeIndices))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_domainName = true && (isSetDomainName());
    list.add(present_domainName);
    if (present_domainName)
      list.add(domainName);

    boolean present_finiteVolumeIndices = true && (isSetFiniteVolumeIndices());
    list.add(present_finiteVolumeIndices);
    if (present_finiteVolumeIndices)
      list.add(finiteVolumeIndices);

    return list.hashCode();
  }

  @Override
  public int compareTo(FiniteVolumeIndexData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetDomainName()).compareTo(other.isSetDomainName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDomainName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.domainName, other.domainName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFiniteVolumeIndices()).compareTo(other.isSetFiniteVolumeIndices());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFiniteVolumeIndices()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.finiteVolumeIndices, other.finiteVolumeIndices);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FiniteVolumeIndexData(");
    boolean first = true;

    sb.append("domainName:");
    if (this.domainName == null) {
      sb.append("null");
    } else {
      sb.append(this.domainName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("finiteVolumeIndices:");
    if (this.finiteVolumeIndices == null) {
      sb.append("null");
    } else {
      sb.append(this.finiteVolumeIndices);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (domainName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'domainName' was not present! Struct: " + toString());
    }
    if (finiteVolumeIndices == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'finiteVolumeIndices' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FiniteVolumeIndexDataStandardSchemeFactory implements SchemeFactory {
    public FiniteVolumeIndexDataStandardScheme getScheme() {
      return new FiniteVolumeIndexDataStandardScheme();
    }
  }

  private static class FiniteVolumeIndexDataStandardScheme extends StandardScheme<FiniteVolumeIndexData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FiniteVolumeIndexData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DOMAIN_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.domainName = iprot.readString();
              struct.setDomainNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FINITE_VOLUME_INDICES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list48 = iprot.readListBegin();
                struct.finiteVolumeIndices = new ArrayList<FiniteVolumeIndex>(_list48.size);
                FiniteVolumeIndex _elem49;
                for (int _i50 = 0; _i50 < _list48.size; ++_i50)
                {
                  _elem49 = new FiniteVolumeIndex();
                  _elem49.read(iprot);
                  struct.finiteVolumeIndices.add(_elem49);
                }
                iprot.readListEnd();
              }
              struct.setFiniteVolumeIndicesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, FiniteVolumeIndexData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.domainName != null) {
        oprot.writeFieldBegin(DOMAIN_NAME_FIELD_DESC);
        oprot.writeString(struct.domainName);
        oprot.writeFieldEnd();
      }
      if (struct.finiteVolumeIndices != null) {
        oprot.writeFieldBegin(FINITE_VOLUME_INDICES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.finiteVolumeIndices.size()));
          for (FiniteVolumeIndex _iter51 : struct.finiteVolumeIndices)
          {
            _iter51.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FiniteVolumeIndexDataTupleSchemeFactory implements SchemeFactory {
    public FiniteVolumeIndexDataTupleScheme getScheme() {
      return new FiniteVolumeIndexDataTupleScheme();
    }
  }

  private static class FiniteVolumeIndexDataTupleScheme extends TupleScheme<FiniteVolumeIndexData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FiniteVolumeIndexData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.domainName);
      {
        oprot.writeI32(struct.finiteVolumeIndices.size());
        for (FiniteVolumeIndex _iter52 : struct.finiteVolumeIndices)
        {
          _iter52.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FiniteVolumeIndexData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.domainName = iprot.readString();
      struct.setDomainNameIsSet(true);
      {
        org.apache.thrift.protocol.TList _list53 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.finiteVolumeIndices = new ArrayList<FiniteVolumeIndex>(_list53.size);
        FiniteVolumeIndex _elem54;
        for (int _i55 = 0; _i55 < _list53.size; ++_i55)
        {
          _elem54 = new FiniteVolumeIndex();
          _elem54.read(iprot);
          struct.finiteVolumeIndices.add(_elem54);
        }
      }
      struct.setFiniteVolumeIndicesIsSet(true);
    }
  }

}

