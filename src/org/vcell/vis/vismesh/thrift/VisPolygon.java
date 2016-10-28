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
public class VisPolygon implements org.apache.thrift.TBase<VisPolygon, VisPolygon._Fields>, java.io.Serializable, Cloneable, Comparable<VisPolygon> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("VisPolygon");

  private static final org.apache.thrift.protocol.TField POINT_INDICES_FIELD_DESC = new org.apache.thrift.protocol.TField("pointIndices", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField CHOMBO_VOLUME_INDEX_FIELD_DESC = new org.apache.thrift.protocol.TField("chomboVolumeIndex", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField FINITE_VOLUME_INDEX_FIELD_DESC = new org.apache.thrift.protocol.TField("finiteVolumeIndex", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField MOVING_BOUNDARY_VOLUME_INDEX_FIELD_DESC = new org.apache.thrift.protocol.TField("movingBoundaryVolumeIndex", org.apache.thrift.protocol.TType.STRUCT, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new VisPolygonStandardSchemeFactory());
    schemes.put(TupleScheme.class, new VisPolygonTupleSchemeFactory());
  }

  public List<Integer> pointIndices; // required
  public ChomboVolumeIndex chomboVolumeIndex; // optional
  public FiniteVolumeIndex finiteVolumeIndex; // optional
  public MovingBoundaryVolumeIndex movingBoundaryVolumeIndex; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    POINT_INDICES((short)1, "pointIndices"),
    CHOMBO_VOLUME_INDEX((short)2, "chomboVolumeIndex"),
    FINITE_VOLUME_INDEX((short)3, "finiteVolumeIndex"),
    MOVING_BOUNDARY_VOLUME_INDEX((short)4, "movingBoundaryVolumeIndex");

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
        case 1: // POINT_INDICES
          return POINT_INDICES;
        case 2: // CHOMBO_VOLUME_INDEX
          return CHOMBO_VOLUME_INDEX;
        case 3: // FINITE_VOLUME_INDEX
          return FINITE_VOLUME_INDEX;
        case 4: // MOVING_BOUNDARY_VOLUME_INDEX
          return MOVING_BOUNDARY_VOLUME_INDEX;
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
  private static final _Fields optionals[] = {_Fields.CHOMBO_VOLUME_INDEX,_Fields.FINITE_VOLUME_INDEX,_Fields.MOVING_BOUNDARY_VOLUME_INDEX};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.POINT_INDICES, new org.apache.thrift.meta_data.FieldMetaData("pointIndices", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.LIST        , "IntList")));
    tmpMap.put(_Fields.CHOMBO_VOLUME_INDEX, new org.apache.thrift.meta_data.FieldMetaData("chomboVolumeIndex", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ChomboVolumeIndex.class)));
    tmpMap.put(_Fields.FINITE_VOLUME_INDEX, new org.apache.thrift.meta_data.FieldMetaData("finiteVolumeIndex", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, FiniteVolumeIndex.class)));
    tmpMap.put(_Fields.MOVING_BOUNDARY_VOLUME_INDEX, new org.apache.thrift.meta_data.FieldMetaData("movingBoundaryVolumeIndex", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, MovingBoundaryVolumeIndex.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(VisPolygon.class, metaDataMap);
  }

  public VisPolygon() {
  }

  public VisPolygon(
    List<Integer> pointIndices)
  {
    this();
    this.pointIndices = pointIndices;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public VisPolygon(VisPolygon other) {
    if (other.isSetPointIndices()) {
      this.pointIndices = other.pointIndices;
    }
    if (other.isSetChomboVolumeIndex()) {
      this.chomboVolumeIndex = new ChomboVolumeIndex(other.chomboVolumeIndex);
    }
    if (other.isSetFiniteVolumeIndex()) {
      this.finiteVolumeIndex = new FiniteVolumeIndex(other.finiteVolumeIndex);
    }
    if (other.isSetMovingBoundaryVolumeIndex()) {
      this.movingBoundaryVolumeIndex = new MovingBoundaryVolumeIndex(other.movingBoundaryVolumeIndex);
    }
  }

  public VisPolygon deepCopy() {
    return new VisPolygon(this);
  }

  @Override
  public void clear() {
    this.pointIndices = null;
    this.chomboVolumeIndex = null;
    this.finiteVolumeIndex = null;
    this.movingBoundaryVolumeIndex = null;
  }

  public int getPointIndicesSize() {
    return (this.pointIndices == null) ? 0 : this.pointIndices.size();
  }

  public java.util.Iterator<Integer> getPointIndicesIterator() {
    return (this.pointIndices == null) ? null : this.pointIndices.iterator();
  }

  public void addToPointIndices(int elem) {
    if (this.pointIndices == null) {
      this.pointIndices = new ArrayList<Integer>();
    }
    this.pointIndices.add(elem);
  }

  public List<Integer> getPointIndices() {
    return this.pointIndices;
  }

  public VisPolygon setPointIndices(List<Integer> pointIndices) {
    this.pointIndices = pointIndices;
    return this;
  }

  public void unsetPointIndices() {
    this.pointIndices = null;
  }

  /** Returns true if field pointIndices is set (has been assigned a value) and false otherwise */
  public boolean isSetPointIndices() {
    return this.pointIndices != null;
  }

  public void setPointIndicesIsSet(boolean value) {
    if (!value) {
      this.pointIndices = null;
    }
  }

  public ChomboVolumeIndex getChomboVolumeIndex() {
    return this.chomboVolumeIndex;
  }

  public VisPolygon setChomboVolumeIndex(ChomboVolumeIndex chomboVolumeIndex) {
    this.chomboVolumeIndex = chomboVolumeIndex;
    return this;
  }

  public void unsetChomboVolumeIndex() {
    this.chomboVolumeIndex = null;
  }

  /** Returns true if field chomboVolumeIndex is set (has been assigned a value) and false otherwise */
  public boolean isSetChomboVolumeIndex() {
    return this.chomboVolumeIndex != null;
  }

  public void setChomboVolumeIndexIsSet(boolean value) {
    if (!value) {
      this.chomboVolumeIndex = null;
    }
  }

  public FiniteVolumeIndex getFiniteVolumeIndex() {
    return this.finiteVolumeIndex;
  }

  public VisPolygon setFiniteVolumeIndex(FiniteVolumeIndex finiteVolumeIndex) {
    this.finiteVolumeIndex = finiteVolumeIndex;
    return this;
  }

  public void unsetFiniteVolumeIndex() {
    this.finiteVolumeIndex = null;
  }

  /** Returns true if field finiteVolumeIndex is set (has been assigned a value) and false otherwise */
  public boolean isSetFiniteVolumeIndex() {
    return this.finiteVolumeIndex != null;
  }

  public void setFiniteVolumeIndexIsSet(boolean value) {
    if (!value) {
      this.finiteVolumeIndex = null;
    }
  }

  public MovingBoundaryVolumeIndex getMovingBoundaryVolumeIndex() {
    return this.movingBoundaryVolumeIndex;
  }

  public VisPolygon setMovingBoundaryVolumeIndex(MovingBoundaryVolumeIndex movingBoundaryVolumeIndex) {
    this.movingBoundaryVolumeIndex = movingBoundaryVolumeIndex;
    return this;
  }

  public void unsetMovingBoundaryVolumeIndex() {
    this.movingBoundaryVolumeIndex = null;
  }

  /** Returns true if field movingBoundaryVolumeIndex is set (has been assigned a value) and false otherwise */
  public boolean isSetMovingBoundaryVolumeIndex() {
    return this.movingBoundaryVolumeIndex != null;
  }

  public void setMovingBoundaryVolumeIndexIsSet(boolean value) {
    if (!value) {
      this.movingBoundaryVolumeIndex = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case POINT_INDICES:
      if (value == null) {
        unsetPointIndices();
      } else {
        setPointIndices((List<Integer>)value);
      }
      break;

    case CHOMBO_VOLUME_INDEX:
      if (value == null) {
        unsetChomboVolumeIndex();
      } else {
        setChomboVolumeIndex((ChomboVolumeIndex)value);
      }
      break;

    case FINITE_VOLUME_INDEX:
      if (value == null) {
        unsetFiniteVolumeIndex();
      } else {
        setFiniteVolumeIndex((FiniteVolumeIndex)value);
      }
      break;

    case MOVING_BOUNDARY_VOLUME_INDEX:
      if (value == null) {
        unsetMovingBoundaryVolumeIndex();
      } else {
        setMovingBoundaryVolumeIndex((MovingBoundaryVolumeIndex)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case POINT_INDICES:
      return getPointIndices();

    case CHOMBO_VOLUME_INDEX:
      return getChomboVolumeIndex();

    case FINITE_VOLUME_INDEX:
      return getFiniteVolumeIndex();

    case MOVING_BOUNDARY_VOLUME_INDEX:
      return getMovingBoundaryVolumeIndex();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case POINT_INDICES:
      return isSetPointIndices();
    case CHOMBO_VOLUME_INDEX:
      return isSetChomboVolumeIndex();
    case FINITE_VOLUME_INDEX:
      return isSetFiniteVolumeIndex();
    case MOVING_BOUNDARY_VOLUME_INDEX:
      return isSetMovingBoundaryVolumeIndex();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof VisPolygon)
      return this.equals((VisPolygon)that);
    return false;
  }

  public boolean equals(VisPolygon that) {
    if (that == null)
      return false;

    boolean this_present_pointIndices = true && this.isSetPointIndices();
    boolean that_present_pointIndices = true && that.isSetPointIndices();
    if (this_present_pointIndices || that_present_pointIndices) {
      if (!(this_present_pointIndices && that_present_pointIndices))
        return false;
      if (!this.pointIndices.equals(that.pointIndices))
        return false;
    }

    boolean this_present_chomboVolumeIndex = true && this.isSetChomboVolumeIndex();
    boolean that_present_chomboVolumeIndex = true && that.isSetChomboVolumeIndex();
    if (this_present_chomboVolumeIndex || that_present_chomboVolumeIndex) {
      if (!(this_present_chomboVolumeIndex && that_present_chomboVolumeIndex))
        return false;
      if (!this.chomboVolumeIndex.equals(that.chomboVolumeIndex))
        return false;
    }

    boolean this_present_finiteVolumeIndex = true && this.isSetFiniteVolumeIndex();
    boolean that_present_finiteVolumeIndex = true && that.isSetFiniteVolumeIndex();
    if (this_present_finiteVolumeIndex || that_present_finiteVolumeIndex) {
      if (!(this_present_finiteVolumeIndex && that_present_finiteVolumeIndex))
        return false;
      if (!this.finiteVolumeIndex.equals(that.finiteVolumeIndex))
        return false;
    }

    boolean this_present_movingBoundaryVolumeIndex = true && this.isSetMovingBoundaryVolumeIndex();
    boolean that_present_movingBoundaryVolumeIndex = true && that.isSetMovingBoundaryVolumeIndex();
    if (this_present_movingBoundaryVolumeIndex || that_present_movingBoundaryVolumeIndex) {
      if (!(this_present_movingBoundaryVolumeIndex && that_present_movingBoundaryVolumeIndex))
        return false;
      if (!this.movingBoundaryVolumeIndex.equals(that.movingBoundaryVolumeIndex))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_pointIndices = true && (isSetPointIndices());
    list.add(present_pointIndices);
    if (present_pointIndices)
      list.add(pointIndices);

    boolean present_chomboVolumeIndex = true && (isSetChomboVolumeIndex());
    list.add(present_chomboVolumeIndex);
    if (present_chomboVolumeIndex)
      list.add(chomboVolumeIndex);

    boolean present_finiteVolumeIndex = true && (isSetFiniteVolumeIndex());
    list.add(present_finiteVolumeIndex);
    if (present_finiteVolumeIndex)
      list.add(finiteVolumeIndex);

    boolean present_movingBoundaryVolumeIndex = true && (isSetMovingBoundaryVolumeIndex());
    list.add(present_movingBoundaryVolumeIndex);
    if (present_movingBoundaryVolumeIndex)
      list.add(movingBoundaryVolumeIndex);

    return list.hashCode();
  }

  @Override
  public int compareTo(VisPolygon other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPointIndices()).compareTo(other.isSetPointIndices());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPointIndices()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pointIndices, other.pointIndices);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChomboVolumeIndex()).compareTo(other.isSetChomboVolumeIndex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChomboVolumeIndex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.chomboVolumeIndex, other.chomboVolumeIndex);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFiniteVolumeIndex()).compareTo(other.isSetFiniteVolumeIndex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFiniteVolumeIndex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.finiteVolumeIndex, other.finiteVolumeIndex);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMovingBoundaryVolumeIndex()).compareTo(other.isSetMovingBoundaryVolumeIndex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMovingBoundaryVolumeIndex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.movingBoundaryVolumeIndex, other.movingBoundaryVolumeIndex);
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
    StringBuilder sb = new StringBuilder("VisPolygon(");
    boolean first = true;

    sb.append("pointIndices:");
    if (this.pointIndices == null) {
      sb.append("null");
    } else {
      sb.append(this.pointIndices);
    }
    first = false;
    if (isSetChomboVolumeIndex()) {
      if (!first) sb.append(", ");
      sb.append("chomboVolumeIndex:");
      if (this.chomboVolumeIndex == null) {
        sb.append("null");
      } else {
        sb.append(this.chomboVolumeIndex);
      }
      first = false;
    }
    if (isSetFiniteVolumeIndex()) {
      if (!first) sb.append(", ");
      sb.append("finiteVolumeIndex:");
      if (this.finiteVolumeIndex == null) {
        sb.append("null");
      } else {
        sb.append(this.finiteVolumeIndex);
      }
      first = false;
    }
    if (isSetMovingBoundaryVolumeIndex()) {
      if (!first) sb.append(", ");
      sb.append("movingBoundaryVolumeIndex:");
      if (this.movingBoundaryVolumeIndex == null) {
        sb.append("null");
      } else {
        sb.append(this.movingBoundaryVolumeIndex);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (pointIndices == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'pointIndices' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (chomboVolumeIndex != null) {
      chomboVolumeIndex.validate();
    }
    if (finiteVolumeIndex != null) {
      finiteVolumeIndex.validate();
    }
    if (movingBoundaryVolumeIndex != null) {
      movingBoundaryVolumeIndex.validate();
    }
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

  private static class VisPolygonStandardSchemeFactory implements SchemeFactory {
    public VisPolygonStandardScheme getScheme() {
      return new VisPolygonStandardScheme();
    }
  }

  private static class VisPolygonStandardScheme extends StandardScheme<VisPolygon> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, VisPolygon struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // POINT_INDICES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.pointIndices = new ArrayList<Integer>(_list0.size);
                int _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readI32();
                  struct.pointIndices.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setPointIndicesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CHOMBO_VOLUME_INDEX
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.chomboVolumeIndex = new ChomboVolumeIndex();
              struct.chomboVolumeIndex.read(iprot);
              struct.setChomboVolumeIndexIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FINITE_VOLUME_INDEX
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.finiteVolumeIndex = new FiniteVolumeIndex();
              struct.finiteVolumeIndex.read(iprot);
              struct.setFiniteVolumeIndexIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MOVING_BOUNDARY_VOLUME_INDEX
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.movingBoundaryVolumeIndex = new MovingBoundaryVolumeIndex();
              struct.movingBoundaryVolumeIndex.read(iprot);
              struct.setMovingBoundaryVolumeIndexIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, VisPolygon struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pointIndices != null) {
        oprot.writeFieldBegin(POINT_INDICES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.pointIndices.size()));
          for (int _iter3 : struct.pointIndices)
          {
            oprot.writeI32(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.chomboVolumeIndex != null) {
        if (struct.isSetChomboVolumeIndex()) {
          oprot.writeFieldBegin(CHOMBO_VOLUME_INDEX_FIELD_DESC);
          struct.chomboVolumeIndex.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.finiteVolumeIndex != null) {
        if (struct.isSetFiniteVolumeIndex()) {
          oprot.writeFieldBegin(FINITE_VOLUME_INDEX_FIELD_DESC);
          struct.finiteVolumeIndex.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.movingBoundaryVolumeIndex != null) {
        if (struct.isSetMovingBoundaryVolumeIndex()) {
          oprot.writeFieldBegin(MOVING_BOUNDARY_VOLUME_INDEX_FIELD_DESC);
          struct.movingBoundaryVolumeIndex.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class VisPolygonTupleSchemeFactory implements SchemeFactory {
    public VisPolygonTupleScheme getScheme() {
      return new VisPolygonTupleScheme();
    }
  }

  private static class VisPolygonTupleScheme extends TupleScheme<VisPolygon> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, VisPolygon struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.pointIndices.size());
        for (int _iter4 : struct.pointIndices)
        {
          oprot.writeI32(_iter4);
        }
      }
      BitSet optionals = new BitSet();
      if (struct.isSetChomboVolumeIndex()) {
        optionals.set(0);
      }
      if (struct.isSetFiniteVolumeIndex()) {
        optionals.set(1);
      }
      if (struct.isSetMovingBoundaryVolumeIndex()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetChomboVolumeIndex()) {
        struct.chomboVolumeIndex.write(oprot);
      }
      if (struct.isSetFiniteVolumeIndex()) {
        struct.finiteVolumeIndex.write(oprot);
      }
      if (struct.isSetMovingBoundaryVolumeIndex()) {
        struct.movingBoundaryVolumeIndex.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, VisPolygon struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
        struct.pointIndices = new ArrayList<Integer>(_list5.size);
        int _elem6;
        for (int _i7 = 0; _i7 < _list5.size; ++_i7)
        {
          _elem6 = iprot.readI32();
          struct.pointIndices.add(_elem6);
        }
      }
      struct.setPointIndicesIsSet(true);
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.chomboVolumeIndex = new ChomboVolumeIndex();
        struct.chomboVolumeIndex.read(iprot);
        struct.setChomboVolumeIndexIsSet(true);
      }
      if (incoming.get(1)) {
        struct.finiteVolumeIndex = new FiniteVolumeIndex();
        struct.finiteVolumeIndex.read(iprot);
        struct.setFiniteVolumeIndexIsSet(true);
      }
      if (incoming.get(2)) {
        struct.movingBoundaryVolumeIndex = new MovingBoundaryVolumeIndex();
        struct.movingBoundaryVolumeIndex.read(iprot);
        struct.setMovingBoundaryVolumeIndexIsSet(true);
      }
    }
  }

}

