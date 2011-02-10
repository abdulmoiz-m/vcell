package cbit.vcell.client.desktop.biomodel;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTable;

import org.vcell.util.gui.sorttable.DefaultSortTableModel;

import cbit.vcell.client.desktop.biomodel.IssueManager.IssueEvent;
import cbit.vcell.client.desktop.biomodel.IssueManager.IssueEventListener;
import cbit.vcell.client.desktop.biomodel.SelectionManager.ActiveView;

@SuppressWarnings("serial")
public abstract class DocumentEditorSubPanel extends JPanel implements PropertyChangeListener, IssueEventListener {
	private SelectionManager selectionManager = null;
	protected IssueManager issueManager = null;
	
	public DocumentEditorSubPanel() {
		super();
	}
	
	public void setSelectionManager(SelectionManager selectionManager) {
		this.selectionManager = selectionManager;
		if (selectionManager != null) {
			selectionManager.removePropertyChangeListener(this);
			selectionManager.addPropertyChangeListener(this);
		}
	}

	public void setIssueManager(IssueManager issueManager) {
		this.issueManager = issueManager;
		if (issueManager != null) {
			issueManager.removeIssueEventListener(this);
			issueManager.addIssueEventListener(this);
		}
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == selectionManager) {
			if (evt.getPropertyName().equals(SelectionManager.PROPERTY_NAME_SELECTED_OBJECTS)) {
				onSelectedObjectsChange(selectionManager.getSelectedObjects());
			} else if (evt.getPropertyName().equals(SelectionManager.PROPERTY_NAME_ACTIVE_VIEW)) {
				onActiveViewChange(selectionManager.getActiveView());
			}
		}
	}

	protected void onActiveViewChange(ActiveView activeView){};

	protected abstract void onSelectedObjectsChange(Object[] selectedObjects);
	protected <T> void setSelectedObjectsFromTable(JTable table, DefaultSortTableModel<T> tableModel) {
		int[] rows = table.getSelectedRows();
		Object[] selectedObjects = null;
		selectedObjects = new Object[rows.length];
		for (int i = 0; i < rows.length; i++) {
			if (rows[i] < tableModel.getDataSize()) {
				selectedObjects[i] = tableModel.getValueAt(rows[i]);
			}
		}
		setSelectedObjects(selectedObjects);
	}
	
	public static <T> void setTableSelections(Object[] selectedObjects, JTable table, DefaultSortTableModel<T> tableModel) {
		if (selectedObjects == null || selectedObjects.length == 0) {
			table.clearSelection();
			return;
		}
		Set<Integer> oldSelectionSet = new HashSet<Integer>();
		for (int row : table.getSelectedRows()) {
			oldSelectionSet.add(row);
		}
		Set<Integer> newSelectionSet = new HashSet<Integer>();
		for (Object object : selectedObjects) {
			for (int i = 0; i < tableModel.getDataSize(); i ++) {
				if (tableModel.getValueAt(i) == object) {
					newSelectionSet.add(i);
					break;
				}
			}
		}
		
		Set<Integer> removeSet = new HashSet<Integer>(oldSelectionSet);
		removeSet.removeAll(newSelectionSet);
		Set<Integer> addSet = new HashSet<Integer>(newSelectionSet);
		addSet.removeAll(oldSelectionSet);
		for (int row : removeSet) {
			table.removeRowSelectionInterval(row, row);
		}
		for (int row : addSet) {
			table.addRowSelectionInterval(row, row);
		}
		if (removeSet.size() > 0 || addSet.size() > 0) {
			Rectangle r = table.getCellRect(table.getSelectedRow(), 0, true);
			table.scrollRectToVisible(r);
		}
	}
	protected void setSelectedObjects(Object[] selectedObjects) {
		if (selectionManager != null) {
			selectionManager.setSelectedObjects(selectedObjects);
		}
	}
	
	protected void setActiveView(ActiveView activeView) {
		if (selectionManager != null) {
			selectionManager.setActiveView(activeView);
		}
	}
	
	public void issueChange(IssueEvent issueEvent) {
	}
}
