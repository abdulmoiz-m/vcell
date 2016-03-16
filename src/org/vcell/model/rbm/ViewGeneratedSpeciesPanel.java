/*
 * Copyright (C) 1999-2011 University of Connecticut Health Center
 *
 * Licensed under the MIT License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *  http://www.opensource.org/licenses/mit-license.php
 */

package org.vcell.model.rbm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.vcell.model.bngl.ParseException;
import org.vcell.util.gui.EditorScrollTable;

import cbit.vcell.bionetgen.BNGSpecies;
import cbit.vcell.client.desktop.biomodel.DocumentEditorSubPanel;
import cbit.vcell.graph.LargeShapePanel;
import cbit.vcell.graph.SpeciesPatternLargeShape;
import cbit.vcell.graph.ZoomShape;
import cbit.vcell.model.Model;
import cbit.vcell.model.ModelException;
import cbit.vcell.model.Species;
import cbit.vcell.model.SpeciesContext;
import cbit.vcell.model.Structure;

@SuppressWarnings("serial")
public class ViewGeneratedSpeciesPanel extends DocumentEditorSubPanel  {
	private BNGSpecies[] speciess = null;
	private EventHandler eventHandler = new EventHandler();
	private GeneratedSpeciesTableModel tableModel = null; 
	private EditorScrollTable table = null;
	private JTextField textFieldSearch = null;
	
	private JButton zoomLargerButton = null;
	private JButton zoomSmallerButton = null;

	private final NetworkConstraintsPanel owner;
	LargeShapePanel shapePanel = null;
	private SpeciesPatternLargeShape spls;

	private class EventHandler implements ActionListener, DocumentListener, ListSelectionListener, TableModelListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == getZoomLargerButton()) {
				boolean ret = shapePanel.zoomLarger();
				getZoomLargerButton().setEnabled(ret);
				getZoomSmallerButton().setEnabled(true);
				int[] selectedRows = table.getSelectedRows();
				if(selectedRows.length == 1) {
					updateShape(selectedRows[0]);
				}
			} else if (e.getSource() == getZoomSmallerButton()) {
				boolean ret = shapePanel.zoomSmaller();
				getZoomLargerButton().setEnabled(true);
				getZoomSmallerButton().setEnabled(ret);
				int[] selectedRows = table.getSelectedRows();
				if(selectedRows.length == 1) {
					updateShape(selectedRows[0]);
				}
			}
		}
		public void insertUpdate(DocumentEvent e) {
			searchTable();
		}
		public void removeUpdate(DocumentEvent e) {
			searchTable();
		}
		public void changedUpdate(DocumentEvent e) {
			searchTable();
		}
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int[] selectedRows = table.getSelectedRows();
			if(selectedRows.length == 1 && !e.getValueIsAdjusting()) {
				updateShape(selectedRows[0]);
			}
		}
		@Override
		public void tableChanged(TableModelEvent e) {
			if(table.getModel().getRowCount() == 0) {
				System.out.println("table is empty");
				spls = null;
				shapePanel.repaint();
			} else {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {				
						table.setRowSelectionInterval(0,0);
					}
				});
			}
		}
	}
	
public ViewGeneratedSpeciesPanel(NetworkConstraintsPanel owner) {
	super();
	this.owner = owner;
	initialize();
}

public ArrayList<GeneratedSpeciesTableRow> getTableRows() {
	return tableModel.getTableRows();
}

private void handleException(java.lang.Throwable exception) {
	/* Uncomment the following lines to print uncaught exceptions to stdout */
	 System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	 exception.printStackTrace(System.out);
}

private void initialize() {
	try {
		setName("ViewGeneratedSpeciesPanel");
		setLayout(new GridBagLayout());
			
		shapePanel = new LargeShapePanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(spls != null) {
					spls.paintSelf(g);
				}
			}
		};
		Border loweredBevelBorder = BorderFactory.createLoweredBevelBorder();
		shapePanel.setLayout(new GridBagLayout());
		shapePanel.setBackground(Color.white);

		JScrollPane scrollPane = new JScrollPane(shapePanel);
		scrollPane.setBorder(loweredBevelBorder);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridBagLayout());
		
		getZoomSmallerButton().setEnabled(true);
		getZoomLargerButton().setEnabled(false);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0,0,0,10);
		gbc.anchor = GridBagConstraints.WEST;
		optionsPanel.add(getZoomLargerButton(), gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(2,0,4,10);
		gbc.anchor = GridBagConstraints.WEST;
		optionsPanel.add(getZoomSmallerButton(), gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;		// fake cell used for filling all the vertical empty space
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(4, 4, 4, 10);
		optionsPanel.add(new JLabel(""), gbc);

		JPanel containerOfScrollPanel = new JPanel();
		containerOfScrollPanel.setLayout(new BorderLayout());
		containerOfScrollPanel.add(optionsPanel, BorderLayout.WEST);
		containerOfScrollPanel.add(scrollPane, BorderLayout.CENTER);
		containerOfScrollPanel.setPreferredSize(new Dimension(500, 135));	// dimension of shape panel
		
		// ------------------------------------------------------------------------
		
		table = new EditorScrollTable();
		tableModel = new GeneratedSpeciesTableModel(table);
		table.setModel(tableModel);
		table.getSelectionModel().addListSelectionListener(eventHandler);
		table.getModel().addTableModelListener(eventHandler);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(GeneratedSpeciesTableModel.iColIndex).setCellRenderer(rightRenderer);	// right align
		table.getColumnModel().getColumn(GeneratedSpeciesTableModel.iColIndex).setMaxWidth(60);		// left column wide enough for 6-7 digits
		
		int gridy = 0;
		gbc = new java.awt.GridBagConstraints();		
		gbc.gridx = 0;
		gbc.gridy = gridy;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 8;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(4, 4, 4, 4);
		table.setPreferredScrollableViewportSize(new Dimension(400,200));
		add(table.getEnclosingScrollPane(), gbc);
		
//		gbc = new java.awt.GridBagConstraints();
//		gbc.gridx = 9;
//		gbc.gridy = gridy;
		
		// add toolTipText for each table cell
		table.addMouseMotionListener(new MouseMotionAdapter() { 
		    public void mouseMoved(MouseEvent e) { 	
		            Point p = e.getPoint(); 
		            int row = table.rowAtPoint(p);
		            int column = table.columnAtPoint(p);
		            table.setToolTipText(String.valueOf(table.getValueAt(row,column)));
		    } 
		});

		gridy ++;	
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = gridy;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(4,4,4,4);
		add(new JLabel("Search "), gbc);

		textFieldSearch = new JTextField(70);
		textFieldSearch.addActionListener(eventHandler);
		textFieldSearch.getDocument().addDocumentListener(eventHandler);
		textFieldSearch.putClientProperty("JTextField.variant", "search");
		
		gbc = new java.awt.GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.gridx = 1; 
		gbc.gridy = gridy;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4, 0, 4, 4);
		add(textFieldSearch, gbc);

		gridy ++;	
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = gridy;
		gbc.weightx = 1.0;
		gbc.gridwidth = 8;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.fill = java.awt.GridBagConstraints.BOTH;
		gbc.insets = new Insets(4,4,4,4);
		add(containerOfScrollPanel, gbc);

				
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
}

public void updateShape(int selectedRow) {
	GeneratedSpeciesTableRow speciesTableRow = tableModel.getValueAt(selectedRow);
	String inputString = speciesTableRow.getExpression();
//	System.out.println(selectedRows[0] + ": " + inputString);
	
	Model tempModel = null;
	try {
		tempModel = new Model("MyTempModel");
		tempModel.addFeature("c0");
	} catch (ModelException | PropertyVetoException e1) {
		e1.printStackTrace();
	}
	if(owner != null && owner.getSimulationContext() != null) {
		List <MolecularType> mtList = owner.getSimulationContext().getModel().getRbmModelContainer().getMolecularTypeList();
		try {
			tempModel.getRbmModelContainer().setMolecularTypeList(mtList);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
			throw new RuntimeException("Unexpected exception setting " + MolecularType.typeName + " list: "+e1.getMessage(),e1);
		}
	} else {
		System.out.println("something is wrong, we just do nothing rather than crash");
		return;
	}
	try {
	SpeciesPattern sp = (SpeciesPattern)RbmUtils.parseSpeciesPattern(inputString, tempModel);
	String strStructure = RbmUtils.parseCompartment(inputString, tempModel);
	sp.resolveBonds();
//	System.out.println(sp.toString());
	Structure structure;
	if(strStructure != null) {
		if(tempModel.getStructure(strStructure) == null) {
			tempModel.addFeature(strStructure);
		}
		structure = tempModel.getStructure(strStructure);
	} else {
		structure = tempModel.getStructure(0);
	}
	SpeciesContext sc = new SpeciesContext(new Species("a",""), structure, sp);
	spls = new SpeciesPatternLargeShape(20, 20, -1, sp, shapePanel, sc);
	
	} catch (ParseException | PropertyVetoException | ModelException e1) {
		e1.printStackTrace();
		spls = new SpeciesPatternLargeShape(20, 20, -1, shapePanel, true);	// error (red circle)
		shapePanel.repaint();
	}
	
	int xOffset = spls.getRightEnd() + 45;
	Dimension preferredSize = new Dimension(xOffset+90, 50);
	shapePanel.setPreferredSize(preferredSize);
	
	shapePanel.repaint();
}

private JButton getZoomLargerButton() {
	if (zoomLargerButton == null) {
		zoomLargerButton = new JButton();		// "+"
		Icon icon = new ZoomShape(ZoomShape.Sign.plus);
		zoomLargerButton.setIcon(icon);
		zoomLargerButton.setBorder(BorderFactory.createEmptyBorder());
		zoomLargerButton.setContentAreaFilled(false);
		zoomLargerButton.setFocusPainted(false);
		zoomLargerButton.addActionListener(eventHandler);
	}
	return zoomLargerButton;
}
private JButton getZoomSmallerButton() {
	if (zoomSmallerButton == null) {
		zoomSmallerButton = new JButton();		// -
		Icon icon = new ZoomShape(ZoomShape.Sign.minus);
		zoomSmallerButton.setIcon(icon);
		zoomSmallerButton.setBorder(BorderFactory.createEmptyBorder());
		zoomSmallerButton.setContentAreaFilled(false);
		zoomSmallerButton.setFocusPainted(false);
		zoomSmallerButton.addActionListener(eventHandler);
	}
	return zoomSmallerButton;
}

private void searchTable() {
	String searchText = textFieldSearch.getText();
	tableModel.setSearchText(searchText);
}

public void setSpecies(BNGSpecies[] newValue) {
	if (speciess == newValue) {
		return;
	}
	speciess = newValue;
	tableModel.setData(owner.getSimulationContext().getModel(), newValue);
}

@Override
protected void onSelectedObjectsChange(Object[] selectedObjects) {

}

public GeneratedSpeciesTableModel getTableModel(){
	return tableModel;
}

}