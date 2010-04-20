package cbit.vcell.xml.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;

import org.vcell.sybil.models.miriam.MIRIAMQualifier;
import org.vcell.sybil.models.miriam.MIRIAMRef.URNParseFailureException;
import org.vcell.util.gui.DialogUtils;

import cbit.vcell.biomodel.BioModel;
import cbit.vcell.biomodel.meta.Identifiable;
import cbit.vcell.biomodel.meta.MiriamManager;
import cbit.vcell.biomodel.meta.VCMetaData;
import cbit.vcell.biomodel.meta.MiriamManager.DataType;
import cbit.vcell.biomodel.meta.MiriamManager.MiriamRefGroup;
import cbit.vcell.biomodel.meta.MiriamManager.MiriamResource;
import cbit.vcell.client.PopupGenerator;
import cbit.vcell.desktop.BioModelCellRenderer;
import cbit.vcell.xml.gui.MiriamTreeModel.IdentifiableNode;
import cbit.vcell.xml.gui.MiriamTreeModel.LinkNode;

public class MIRIAMAnnotationEditor extends JPanel implements ActionListener{

	private JTree jTreeMIRIAM = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private JButton jButtonEdit = null;
	private JButton jButtonAdd = null;
	private JButton jButtonDelete = null;
	
	private Vector<ActionListener> actionListenerV = new Vector<ActionListener>();
	public static final String ACTION_EDIT ="Edit Annotation ...";  //  @jve:decl-index=0:
	public static final String ACTION_ADD ="Add Annotation ...";  //  @jve:decl-index=0:
	public static final String ACTION_DELETE ="Delete Annotation";
	
	private JPanel jPanelNewIdentifier = null;  //  @jve:decl-index=0:visual-constraint="86,363"
	private JComboBox jComboBoxURI = null;
	private JLabel jLabel2 = null;
	private JTextField jTextFieldFormalID = null;
	private JLabel jLabel3 = null;
	private JComboBox jComboBoxQualifier = null;
	private JLabel jLabel4 = null;
	private JPanel jPanelTimeUTC = null;  //  @jve:decl-index=0:visual-constraint="88,417"
	private JTextField jTextFieldTimeUTC = null;
	private JLabel jLabelTimeUTCEG = null;
	private JLabel jLabelTimeUTC = null;
	private JComboBox jComboBoxTimeUTCType = null;
	private JPanel jPanelCreator = null;  //  @jve:decl-index=0:visual-constraint="646,75"
	private JLabel jLabelGiven = null;
	private JTextField jTextFieldGiven = null;
	private JLabel jLabelFamily = null;
	private JLabel jLabelEmail = null;
	private JLabel JLabelOrganization = null;
	private JTextField jTextFieldFamily = null;
	private JTextField jTextFieldEmail = null;
	private JTextField jTextFieldOrganization = null;
	
	private VCMetaData vcMetaData = null;
	private DefaultComboBoxModel qualifierComboBoxModel = null;
	/**
	 * This method initializes 
	 * 
	 */
	public MIRIAMAnnotationEditor() {
		super();
		initialize();
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < actionListenerV.size(); i++) {
			actionListenerV.get(i).actionPerformed(e);
		}
	}

	public void addActionListener(ActionListener actionListiner){
		if(!actionListenerV.contains(actionListiner)){
			actionListenerV.add(actionListiner);
		}
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
        gridBagConstraints5.gridx = 0;
        gridBagConstraints5.fill = GridBagConstraints.NONE;
        gridBagConstraints5.insets = new Insets(4, 4, 4, 4);
        gridBagConstraints5.gridy = 2;
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.fill = GridBagConstraints.BOTH;
        gridBagConstraints1.weighty = 1.0;
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 0;
        gridBagConstraints1.weightx = 1.0;
        this.setLayout(new GridBagLayout());
        this.setSize(new Dimension(627, 333));
        this.add(getJScrollPane(), gridBagConstraints1);
        this.add(getJPanel(), gridBagConstraints5);
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTreeMIRIAM());
		}
		return jScrollPane;
	}

	private JTree getJTreeMIRIAM() {
		if (jTreeMIRIAM == null) {
			try {
				DefaultTreeSelectionModel ivjLocalSelectionModel;
				ivjLocalSelectionModel = new DefaultTreeSelectionModel();
				ivjLocalSelectionModel.setSelectionMode(1);
				jTreeMIRIAM = new JTree();
				jTreeMIRIAM.setName("JTree1");
				jTreeMIRIAM.setToolTipText("");
				jTreeMIRIAM.setBounds(0, 0, 357, 405);
				jTreeMIRIAM.setMinimumSize(new java.awt.Dimension(100, 72));
				jTreeMIRIAM.setSelectionModel(ivjLocalSelectionModel);
				jTreeMIRIAM.setRowHeight(0);
				
				// Add cellRenderer
				DefaultTreeCellRenderer dtcr = new BioModelCellRenderer(null) {
					@Override
					public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
							boolean leaf, int row, boolean hasFocus) {
						super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
								row, hasFocus);
						setBackgroundSelectionColor(Color.LIGHT_GRAY);
						if (value instanceof LinkNode) {
							LinkNode ln = (LinkNode)value;
							String predicate = ln.getMiriamQualifier().getDescription();
							String link = ln.getLink();
							String text = ln.getText();
							if (link != null) {
								setToolTipText("Double-click to open link");
								setText("<html><font color='black'>" + predicate + "</font>" + 
								"&nbsp;&nbsp;&nbsp;&nbsp;<a href=" + link + ">" + text + "</a></html>");
							}else{
								setText("<html><font color='black'>" + predicate + "</font>" + 
										"&nbsp;&nbsp;&nbsp;&nbsp;" + text + "</html>");
							}
						}
						return this;
					}
				};
				jTreeMIRIAM.setCellRenderer(dtcr);
				
				MouseListener mouseListener = new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if(e.getClickCount() == 2) {
							Object node = jTreeMIRIAM.getLastSelectedPathComponent();
							if (node instanceof LinkNode) {
								String link = ((LinkNode)node).getLink();
								if (link != null) {
									DialogUtils.browserLauncher(jTreeMIRIAM, link, "failed to launch", false);
								}
							}
						}
					} 
				};
				jTreeMIRIAM.addMouseListener(mouseListener);

			} catch (java.lang.Throwable ivjExc) {
				ivjExc.printStackTrace(System.out);
			}
		}
		return jTreeMIRIAM;
	}
	
	/**
	 * This method initializes jButtonEdit
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonEdit() {
		if (jButtonEdit == null) {
			jButtonEdit = new JButton();
			jButtonEdit.setText(ACTION_EDIT);
			jButtonEdit.setEnabled(false);
			jButtonEdit.addActionListener(this);
		}
		return jButtonEdit;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 3;
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jPanel = new JPanel();
			final GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {0,0,0,0,0,7};
			jPanel.setLayout(gridBagLayout);
			jPanel.add(getJButtonEdit(), gridBagConstraints);
			jPanel.add(getJButtonAdd(), gridBagConstraints2);
			jPanel.add(getJButtonDelete(), gridBagConstraints4);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButtonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setText(ACTION_ADD);
			jButtonAdd.addActionListener(this);
			jButtonAdd.setEnabled(false);
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButtonDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDelete() {
		if (jButtonDelete == null) {
			jButtonDelete = new JButton();
			jButtonDelete.setText(ACTION_DELETE);
			jButtonDelete.addActionListener(this);
			jButtonDelete.setEnabled(false);
		}
		return jButtonDelete;
	}

	public Identifiable getSelectedIdentifiable(){
		Object treeNode = jTreeMIRIAM.getLastSelectedPathComponent();
		if (treeNode instanceof IdentifiableNode) {
			Identifiable identifiable = ((IdentifiableNode)treeNode).getIdentifiable();
			return identifiable;
		} else if (treeNode instanceof LinkNode) {
			treeNode = jTreeMIRIAM.getSelectionPath().getParentPath().getPathComponent(0);
			if (treeNode instanceof IdentifiableNode) {
				Identifiable identifiable = ((IdentifiableNode)treeNode).getIdentifiable();
				return identifiable;
			}else{
				return null;
			}
		}
		return null;
	}
	
	public void removeSelectedRefGroups(){
		Object treeNode = jTreeMIRIAM.getLastSelectedPathComponent();
		if (treeNode instanceof IdentifiableNode) {
			Identifiable identifiable = ((IdentifiableNode)treeNode).getIdentifiable();
			Map<MiriamRefGroup, MIRIAMQualifier> refGroupsToRemove = vcMetaData.getMiriamManager().getAllMiriamRefGroups(identifiable);
			for (MiriamRefGroup refGroup : refGroupsToRemove.keySet()){
				MIRIAMQualifier qualifier = refGroupsToRemove.get(refGroup);
				vcMetaData.getMiriamManager().remove(identifiable, qualifier, refGroup);
			}
		}
	}
	
	public void setBioModel(BioModel bioModel) {
		vcMetaData = bioModel.getVCMetaData();

		// set tree model on jTableMIRIAM here, since we have access to miriamDescrHeir here
		jTreeMIRIAM.setModel(new MiriamTreeModel(new DefaultMutableTreeNode("MODEL ANNOTATION",true), vcMetaData));
	}

	/**
	 * This method initializes jPanelNewIdentifier	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelNewIdentifier() {
		if (jPanelNewIdentifier == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(0, 20, 0, 4);
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.gridx = 4;
			jLabel4 = new JLabel();
			jLabel4.setText("Qualifier");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints7.gridx = 5;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.weightx = 0.0;
			gridBagConstraints7.insets = new Insets(4, 0, 4, 4);
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 20, 0, 4);
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridx = 2;
			jLabel3 = new JLabel();
			jLabel3.setText("Immortal ID");
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.gridy = 0;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.gridx = 3;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.insets = new Insets(0, 4, 0, 4);
			gridBagConstraints41.gridy = 0;
			gridBagConstraints41.gridx = 0;
			jLabel2 = new JLabel();
			jLabel2.setText("Identitiy Provider");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.NONE;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 0.0D;
			gridBagConstraints3.insets = new Insets(4, 0, 4, 0);
			jPanelNewIdentifier = new JPanel();
			jPanelNewIdentifier.setLayout(new GridBagLayout());
			jPanelNewIdentifier.setPreferredSize(new Dimension(725, 37));
			jPanelNewIdentifier.setBorder(BorderFactory.createLineBorder(SystemColor.windowBorder, 2));
			jPanelNewIdentifier.add(getJComboBoxURI(), gridBagConstraints3);
			jPanelNewIdentifier.add(jLabel2, gridBagConstraints41);
			jPanelNewIdentifier.add(getJTextFieldFormalID(), gridBagConstraints51);
			jPanelNewIdentifier.add(jLabel3, gridBagConstraints6);
			jPanelNewIdentifier.add(getJComboBoxQualifier(), gridBagConstraints7);
			jPanelNewIdentifier.add(jLabel4, gridBagConstraints12);
		}
		return jPanelNewIdentifier;
	}

	/**
	 * This method initializes jComboBoxURI	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxURI() {
		if (jComboBoxURI == null) {
			jComboBoxURI = new JComboBox();
			DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
			for (DataType dataType : vcMetaData.getMiriamManager().getAllDataTypes().values()){
				defaultComboBoxModel.addElement(dataType);
			}
			jComboBoxURI.setModel(defaultComboBoxModel);
			jComboBoxURI.setRenderer(new DefaultListCellRenderer() {
				public Component getListCellRendererComponent(JList list, Object value,
						int index, boolean isSelected, boolean cellHasFocus) {
					return super.getListCellRendererComponent(list,((DataType)value).getDataTypeName(),index,isSelected,cellHasFocus);
				}
			});
		}
		return jComboBoxURI;
	}

	/**
	 * This method initializes jTextFieldFormalID	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldFormalID() {
		if (jTextFieldFormalID == null) {
			jTextFieldFormalID = new JTextField();
			jTextFieldFormalID.setText("NewID");
		}
		return jTextFieldFormalID;
	}

	/**
	 * This method initializes jComboBoxQualifier	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxQualifier() {
		if (jComboBoxQualifier == null) {
			jComboBoxQualifier = new JComboBox();
			jComboBoxQualifier.setModel(getQualifierComboBoxModel() );
		}
		return jComboBoxQualifier;
	}

	private DefaultComboBoxModel getQualifierComboBoxModel() {
		if (qualifierComboBoxModel == null) {
			qualifierComboBoxModel = new DefaultComboBoxModel();
			Set<MIRIAMQualifier> allQualifiers = MIRIAMQualifier.all;
			for (MIRIAMQualifier qualifier : allQualifiers){
				qualifierComboBoxModel.addElement(qualifier);
			}
		}
		return qualifierComboBoxModel;
	}


	public void addIdentifierDialog() throws URISyntaxException{
		if(PopupGenerator.showComponentOKCancelDialog(MIRIAMAnnotationEditor.this, getJPanelNewIdentifier(), "Define New Formal Identifier") == JOptionPane.OK_OPTION){
			MIRIAMQualifier qualifier = (MIRIAMQualifier)jComboBoxQualifier.getSelectedItem();
			MiriamManager.DataType objectNamespace = (MiriamManager.DataType)jComboBoxURI.getSelectedItem();
			String objectID = jTextFieldFormalID.getText();
			MiriamManager miriamManager = vcMetaData.getMiriamManager();
			HashSet<MiriamResource> miriamResources = new HashSet<MiriamResource>();
			try {
				miriamResources.add(miriamManager.createMiriamResource(objectNamespace.getBaseURN()+":"+objectID));
				miriamManager.addMiriamRefGroup(getSelectedIdentifiable(), qualifier, miriamResources);
			} catch (URNParseFailureException e) {
				e.printStackTrace();
				DialogUtils.showErrorDialog(this,e.getMessage());
			}
		}
	}

	
	public void addTimeUTCDialog(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		getJTextFieldTimeUTC().setText(sdf.format(new Date()));
		if(PopupGenerator.showComponentOKCancelDialog(MIRIAMAnnotationEditor.this, getJPanelTimeUTC(), "Define New Date") == JOptionPane.OK_OPTION){
			vcMetaData.addDateToAnnotation(getSelectedIdentifiable(),
					getJTextFieldTimeUTC().getText(),
					(String)getJComboBoxTimeUTCType().getSelectedItem());
//			String qualifierName = (String)jComboBoxQualifier.getSelectedItem();
//			URI qualifierURI = null;
//			if(qualifierName.endsWith("(bio)")){
//				qualifierURI = new URI(XMLTags.BMBIOQUAL_NAMESPACE_URI);
//			}else if(qualifierName.endsWith("(model)")){
//				qualifierURI = new URI(XMLTags.BMMODELQUAL_NAMESPACE_URI);
//			}
//			qualifierName = qualifierName.substring(0,qualifierName.indexOf(" ("));
//			Element newID =
//				MIRIAMHelper.createRDFIdentifier((String)jComboBoxURI.getSelectedItem(), jTextFieldFormalID.getText());
//			MIRIAMHelper.addIdentifierToAnnotation(
//					newID,
//					getSelectedMIRIAMAnnotatable().getMIRIAMAnnotation(),
//					qualifierName,
//					qualifierURI);
		}
	}

	public void addCreatorDialog(){
		if(PopupGenerator.showComponentOKCancelDialog(MIRIAMAnnotationEditor.this, getJPanelCreator(), "Define New Creator") == JOptionPane.OK_OPTION){
			Identifiable identifiable = getSelectedIdentifiable();
			String familyName = getJTextFieldFamily().getText();
			String givenName = getJTextFieldGiven().getText();
			String email = getJTextFieldEmail().getText();
			String organization = getJTextFieldOrganization().getText();
			vcMetaData.addCreatorToAnnotation(identifiable,familyName,givenName,email,organization);
//			MIRIAMHelper.addCreatorToAnnotation(
//					getSelectedMIRIAMAnnotatable(),
//					getJTextFieldFamily().getText(),
//					getJTextFieldGiven().getText(),
//					getJTextFieldEmail().getText(),
//					getJTextFieldOrganization().getText());
			
//			MIRIAMHelper.addDateToAnnotation(
//					getSelectedMIRIAMAnnotatable(),
//					getJTextFieldTimeUTC().getText(),
//					(String)getJComboBoxTimeUTCType().getSelectedItem());
		}
	}

	/**
	 * This method initializes jPanelTimeUTC	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTimeUTC() {
		if (jPanelTimeUTC == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.NONE;
			gridBagConstraints13.gridy = 2;
			gridBagConstraints13.weightx = 0.0;
			gridBagConstraints13.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints13.gridx = 0;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridwidth = 2;
			gridBagConstraints9.fill = GridBagConstraints.NONE;
			gridBagConstraints9.gridy = 0;
			jLabelTimeUTC = new JLabel();
			jLabelTimeUTC.setText("Enter W3C-DTF compliant Data-Time");
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.fill = GridBagConstraints.NONE;
			gridBagConstraints10.gridy = 1;
			jLabelTimeUTCEG = new JLabel();
			jLabelTimeUTCEG.setText("yyyy-MM-dd'T'HH:mm:ssZ (Z is signed hour offset from GMT)");
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints8.gridx = 1;
			jPanelTimeUTC = new JPanel();
			jPanelTimeUTC.setLayout(new GridBagLayout());
			jPanelTimeUTC.setSize(new Dimension(735, 95));
			jPanelTimeUTC.add(getJTextFieldTimeUTC(), gridBagConstraints8);
			jPanelTimeUTC.add(jLabelTimeUTCEG, gridBagConstraints10);
			jPanelTimeUTC.add(jLabelTimeUTC, gridBagConstraints9);
			jPanelTimeUTC.add(getJComboBoxTimeUTCType(), gridBagConstraints13);
		}
		return jPanelTimeUTC;
	}

	/**
	 * This method initializes jTextFieldTimeUTC	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldTimeUTC() {
		if (jTextFieldTimeUTC == null) {
			jTextFieldTimeUTC = new JTextField();
		}
		return jTextFieldTimeUTC;
	}

	/**
	 * This method initializes jComboBoxTimeUTCType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxTimeUTCType() {
		if (jComboBoxTimeUTCType == null) {
			jComboBoxTimeUTCType = new JComboBox();
		}
		return jComboBoxTimeUTCType;
	}

	/**
	 * This method initializes jPanelCreator	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelCreator() {
		if (jPanelCreator == null) {
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.gridy = 3;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints22.gridx = 1;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints20.gridx = 1;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints19.gridy = 1;
			gridBagConstraints19.weightx = 1.0;
			gridBagConstraints19.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints19.gridx = 1;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints18.anchor = GridBagConstraints.EAST;
			gridBagConstraints18.gridy = 3;
			JLabelOrganization = new JLabel();
			JLabelOrganization.setText("Organization");
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints17.anchor = GridBagConstraints.EAST;
			gridBagConstraints17.gridy = 2;
			jLabelEmail = new JLabel();
			jLabelEmail.setText("Email");
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints16.anchor = GridBagConstraints.EAST;
			gridBagConstraints16.gridy = 1;
			jLabelFamily = new JLabel();
			jLabelFamily.setText("Family Name");
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.gridy = 0;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints14.anchor = GridBagConstraints.EAST;
			gridBagConstraints14.gridy = 0;
			jLabelGiven = new JLabel();
			jLabelGiven.setText("Given Name");
			jPanelCreator = new JPanel();
			jPanelCreator.setLayout(new GridBagLayout());
			jPanelCreator.setSize(new Dimension(331, 147));
			jPanelCreator.add(jLabelGiven, gridBagConstraints14);
			jPanelCreator.add(getJTextFieldGiven(), gridBagConstraints15);
			jPanelCreator.add(jLabelFamily, gridBagConstraints16);
			jPanelCreator.add(jLabelEmail, gridBagConstraints17);
			jPanelCreator.add(JLabelOrganization, gridBagConstraints18);
			jPanelCreator.add(getJTextFieldFamily(), gridBagConstraints19);
			jPanelCreator.add(getJTextFieldEmail(), gridBagConstraints20);
			jPanelCreator.add(getJTextFieldOrganization(), gridBagConstraints22);
		}
		return jPanelCreator;
	}

	/**
	 * This method initializes jTextFieldGiven	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldGiven() {
		if (jTextFieldGiven == null) {
			jTextFieldGiven = new JTextField();
		}
		return jTextFieldGiven;
	}

	/**
	 * This method initializes jTextFieldFamily	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldFamily() {
		if (jTextFieldFamily == null) {
			jTextFieldFamily = new JTextField();
		}
		return jTextFieldFamily;
	}

	/**
	 * This method initializes jTextFieldEmail	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldEmail() {
		if (jTextFieldEmail == null) {
			jTextFieldEmail = new JTextField();
		}
		return jTextFieldEmail;
	}

	/**
	 * This method initializes jTextFieldOrganization	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldOrganization() {
		if (jTextFieldOrganization == null) {
			jTextFieldOrganization = new JTextField();
		}
		return jTextFieldOrganization;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
