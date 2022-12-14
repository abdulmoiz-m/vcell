/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package cbit.vcell.modeldb;
import cbit.sql.Field;
import cbit.sql.Field.SQLDataType;
import cbit.sql.Table;
/**
 * This type was created in VisualAge.
 */
public class AnalysisTaskXMLTable extends cbit.sql.Table {
	private static final String TABLE_NAME = "vc_analysistask";
	public static final String REF_TYPE = "REFERENCES " + TABLE_NAME + "(" + Table.id_ColumnName + ")";

	public final Field simContextRef =		new Field("simContextRef",		SQLDataType.integer,	"NOT NULL "+SimContextTable.REF_TYPE+" ON DELETE CASCADE");
	public final Field analysisTaskXML =	new Field("analysisTaskXML",	SQLDataType.clob_text,	"NOT NULL");
	public final Field insertDate =			new Field("insertDate",			SQLDataType.date,		"NOT NULL");
	
	private final Field fields[] = {simContextRef,analysisTaskXML,insertDate};
	
	public static final AnalysisTaskXMLTable table = new AnalysisTaskXMLTable();

/**
 * ModelTable constructor comment.
 */
private AnalysisTaskXMLTable() {
	super(TABLE_NAME);
	addFields(fields);
}
}
