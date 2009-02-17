package cbit.vcell.geometry.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

import loci.formats.IFormatReader;
import loci.formats.ImageReader;
import loci.formats.ImageTools;
import cbit.gui.DialogUtils;
import cbit.gui.UtilCancelException;
import cbit.util.BeanUtils;
import cbit.util.ISize;
import cbit.util.NumberUtils;
import cbit.util.Range;
import cbit.vcell.VirtualMicroscopy.ImageDataset;
import cbit.vcell.VirtualMicroscopy.ROI;
import cbit.vcell.VirtualMicroscopy.UShortImage;
import cbit.vcell.VirtualMicroscopy.Image.ImageStatistics;
import cbit.vcell.client.PopupGenerator;
import cbit.vcell.client.task.UserCancelException;
//comments added Jan 2008, this is the panel that displayed at the top of the FRAPDataPanel which deals with serials of images.
/**
 */
public class OverlayEditorPanelJAI extends JPanel {

	private JComboBox roiComboBox;
	public static final String INITIAL_BLEACH_AREA_TEXT = "Initial Bleach Area";
	
	private JButton contrastButtonMinus;
	private JButton contrastButtonPlus;
	private JToggleButton cropButton;
	private JToggleButton fillButton;
	private JToggleButton eraseButton;
	private JToggleButton paintButton;
	private JButton importROIMaskButton;
	private JButton clearROIbutton;
	private JButton roiTimePlotButton;
	private JToggleButton autoCropButton;
	private JButton roiAssistButton;
	private static final long serialVersionUID = 1L;
	private OverlayImageDisplayJAI imagePane = null;
	private JSlider timeSlider = null;
	private ImageDataset imageDataset = null;
	private ROI roi = null;
	private StringBuffer sb = new StringBuffer();
	private JScrollPane jScrollPane2 = null;
	private JPanel leftJPanel = null;
	private JButton zoomInButton = null;
	private JButton zoomOutButton = null;
	private Color highlightColor = Color.yellow.darker();
	private JSlider zSlider = null;
	private ButtonGroup roiDrawButtonGroup = new ButtonGroup();
	private Point lastHighlightPoint = null;
	private JLabel textLabel = null;
	private JPanel topJPanel = null;
	private JPanel editROIPanel = null;
	public static final String FRAP_DATA_AUTOROI_PROPERTY = "FRAP_DATA_AUTOROI_PROPERTY";
	public static final String FRAP_DATA_CROP_PROPERTY = "FRAP_DATA_CROP_PROPERTY";
	public static final String FRAP_DATA_TIMEPLOTROI_PROPERTY = "FRAP_DATA_TIMEPLOTROI_PROPERTY";
	private ISize originalISize;
	public static final String FRAP_DATA_CURRENTROI_PROPERTY = "FRAP_DATA_CURRENTROI_PROPERTY";
	public static final String FRAP_DATA_UNDOROI_PROPERTY = "FRAP_DATA_UNDOROI_PROPERTY";
	private JLabel viewTLabel;
	private JLabel viewZLabel;
	
	private JButton addROIButton;
	private JButton delROIButton;
	private boolean bAllowAddROI = true;
	
	UndoableEditSupport undoableEditSupport;
	ROI undoableROI;
	
	public static final String WHOLE_CELL_AREA_TEXT = "Whole Cell Area";
	public static final String ROI_ASSIST_TEXT = "ROI Assist";
	
	ActionListener ROI_COMBOBOX_ACTIONLISTENER =
		new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				delROIButton.setEnabled(((ComboboxROIName)roiComboBox.getSelectedItem()).isEditable());
				firePropertyChange(FRAP_DATA_CURRENTROI_PROPERTY, null,((ComboboxROIName)roiComboBox.getSelectedItem()).getROIName());
			}
		};
	private class ComboboxROIName {
		private String roiName;
		private boolean bEdit;
		public ComboboxROIName(String roiName,boolean bEdit){
			this.roiName = roiName;
			this.bEdit = bEdit;
		}
		public String getROIName(){
			return roiName;
		}
		public boolean isEditable(){
			return bEdit;
		}
		public String toString(){
			return getROIName();
		}
	}
	
	public static final double DEFAULT_SCALE_FACTOR = 1.0;
	public static final double DEFAULT_OFFSET_FACTOR = 0.0;
	private double originalScaleFactor = DEFAULT_SCALE_FACTOR;
	private double originalOffsetFactor = DEFAULT_OFFSET_FACTOR;
	
	public static final UndoableEdit CLEAR_UNDOABLE_EDIT =
		new AbstractUndoableEdit(){
			public boolean canUndo() {
				return false;
			}
			public String getUndoPresentationName() {
				return null;
			}
			public void undo() throws CannotUndoException {
				super.undo();
			}
		};
	
	private Hashtable<String, Cursor> cursorsForROIsHash = null;
	
	public interface CustomROIImport {
		public boolean importROI(File importROIFile) throws Exception;
	};

	private CustomROIImport customROIImport;
	private JFileChooser openJFileChooser = new JFileChooser();
	
	private Range minmaxPixelValues = null;
	private static final double SHORT_TO_BYTE_FACTOR = 256;


	/**
	 * This is the default constructor
	 */
	public OverlayEditorPanelJAI() {
		super();
		initialize();
	}

	public void setUndoableEditSupport(UndoableEditSupport undoableEditSupport){
		this.undoableEditSupport = undoableEditSupport;
	}
	
	private boolean isAutoCroppable(){
		try {
			Rectangle cropRectangle = null;
			if(imageDataset != null){
				cropRectangle = imageDataset.getNonzeroBoundingRectangle();
			}
			if(cropRectangle != null &&
				cropRectangle.x == 0 && cropRectangle.y == 0 &&
				cropRectangle.width == imageDataset.getISize().getX() &&
				cropRectangle.height == imageDataset.getISize().getY()){
				return false;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void waitCursor(boolean bOn){
		Container topLevelContainer = (JDesktopPane)BeanUtils.findTypeParentOfComponent(this, JDesktopPane.class);
		if(topLevelContainer == null){
			topLevelContainer = (Frame)BeanUtils.findTypeParentOfComponent(this, Frame.class);
		}
		BeanUtils.setCursorThroughout(topLevelContainer,
				(bOn?Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR):Cursor.getDefaultCursor()));
		if(!bOn){
			updateROICursor();
		}
	}
	public void updateROICursor(){
		getImagePane().setCursor(getROICursor());
	}
	private Cursor getROICursor(){
		if(roi == null || cursorsForROIsHash == null || cursorsForROIsHash.get(roi.getROIName()) == null){
			return Cursor.getDefaultCursor();
		}
		return cursorsForROIsHash.get(roi.getROIName());
//		if(roi.getROIName().equals(FRAPData.VFRAP_ROI_ENUM.ROI_CELL.name())){
//			return FRAPStudyPanel.ROI_CURSORS[FRAPStudyPanel.CURSOR_CELLROI];
//		}else if(roi.getROIName().equals(FRAPData.VFRAP_ROI_ENUM.ROI_BLEACHED.name())){
//			return FRAPStudyPanel.ROI_CURSORS[FRAPStudyPanel.CURSOR_BLEACHROI];
//		}else if(roi.getROIName().equals(FRAPData.VFRAP_ROI_ENUM.ROI_BACKGROUND.name())){
//			return FRAPStudyPanel.ROI_CURSORS[FRAPStudyPanel.CURSOR_BACKGROUNDROI];
//		}
//		throw new RuntimeException("Unknown ROI type "+roi.getROIName()+" while getting cursor");
	}
	
	public void setCursorsForROIs(Hashtable<String, Cursor> cursorsForROIsHash){
		this.cursorsForROIsHash = cursorsForROIsHash;
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
		gridBagConstraints2.anchor = GridBagConstraints.NORTH;
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.gridx = 0;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.insets = new Insets(2, 2, 2, 2);
		gridBagConstraints12.fill = GridBagConstraints.BOTH;
		gridBagConstraints12.weighty = 1.0;
		gridBagConstraints12.gridx = 1;
		gridBagConstraints12.gridy = 1;
		gridBagConstraints12.weightx = 1.0;
		this.setSize(734, 471);
		final GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.rowHeights = new int[] {0,7,0};
		this.setLayout(gridBagLayout_1);

		editROIPanel = new JPanel();
		final GridBagLayout gridBagLayout_2 = new GridBagLayout();
		gridBagLayout_2.rowHeights = new int[] {0,0,7};
		gridBagLayout_2.columnWidths = new int[] {0,7};
		editROIPanel.setLayout(gridBagLayout_2);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.anchor = GridBagConstraints.WEST;
		gridBagConstraints_6.insets = new Insets(8, 2, 0, 0);
		gridBagConstraints_6.weightx = 0;
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.gridx = 1;
		add(editROIPanel, gridBagConstraints_6);

		final JLabel infoLabel = new JLabel();
		infoLabel.setText("Data Info:");
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(0, 0, 0, 4);
		gridBagConstraints_12.anchor = GridBagConstraints.EAST;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 0;
		editROIPanel.add(infoLabel, gridBagConstraints_12);

		textLabel = new JLabel();
		textLabel.setPreferredSize(new Dimension(500, 20));
		textLabel.setMinimumSize(new Dimension(500, 20));
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2.weightx = 1;
		gridBagConstraints_2.insets = new Insets(0, 2, 0, 0);
		gridBagConstraints_2.anchor = GridBagConstraints.WEST;
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 1;
		editROIPanel.add(textLabel, gridBagConstraints_2);
		textLabel.setText("No FRAP DataSet loaded.");

		topJPanel = new JPanel();
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 1, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 1;
		editROIPanel.add(topJPanel, gridBagConstraints);
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0,7,7,0,7};
		topJPanel.setLayout(gridBagLayout);

		autoCropButton = new JToggleButton(new ImageIcon(getClass().getResource("/images/autoCrop.gif")));
		autoCropButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					waitCursor(true);
					if(isAutoCroppable()){
						Rectangle cropRect = null;
						if(imageDataset != null){
							cropRect = imageDataset.getNonzeroBoundingRectangle();
						}
						imagePane.setCrop(
								new Point(
									(int)(cropRect.x*imagePane.getZoom()),
									(int)(cropRect.y*imagePane.getZoom())),
								new Point(
									(int)((cropRect.x+cropRect.width)*imagePane.getZoom()),
									(int)((cropRect.y+cropRect.height)*imagePane.getZoom())));
						waitCursor(false);
						if(!cropConfirm(cropRect)){
							imagePane.setCrop(null, null);
							return;
						}
						waitCursor(true);
						imagePane.setCrop(null, null);
						firePropertyChange(FRAP_DATA_CROP_PROPERTY, null,cropRect);						
					}else{
						DialogUtils.showInfoDialog("AutoCrop: No zero values around outer edges.");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					DialogUtils.showErrorDialog("Error AutoCrop:\n"+e1.getMessage());
				}finally{
					waitCursor(false);				
				}
			}
		});
//		autoCropButton.setText("Auto Crop DataSet");
//		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
//		gridBagConstraints_1.gridx = 0;
//		gridBagConstraints_1.gridy = 0;
//		gridBagConstraints_1.insets = new Insets(2, 2, 2, 2);
//		topJPanel.add(autoCropButton, gridBagConstraints_1);

		importROIMaskButton = new JButton(new ImageIcon(getClass().getResource("/images/importROI.gif")));
		importROIMaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				File inputFile = null;
				IFormatReader iFormatReader = null;
				try {
					int option = openJFileChooser.showOpenDialog(OverlayEditorPanelJAI.this);
					if (option == JFileChooser.APPROVE_OPTION){
						inputFile = openJFileChooser.getSelectedFile();
					}else{
						throw UserCancelException.CANCEL_GENERIC;
					}
					if(!customROIImport.importROI(inputFile)){
						ImageReader imageReader = new ImageReader();
						iFormatReader = imageReader.getReader(inputFile.getAbsolutePath());
						iFormatReader.setId(inputFile.getAbsolutePath());
						if(iFormatReader.getSizeX() * iFormatReader.getSizeY() != 
							getImagePane().getHighlightImage().getWidth()*getImagePane().getHighlightImage().getHeight()){
							throw new Exception(
								"Imported ROI mask size ("+
								iFormatReader.getSizeX()+","+iFormatReader.getSizeY()+")"+
								" does not match current Frap DataSet size ("+
								getImagePane().getHighlightImage().getWidth()+","+
								getImagePane().getHighlightImage().getHeight()+")");
						}
						BufferedImage roiMaskImage = iFormatReader.openImage(0);
						int maskColor = highlightColor.getRGB();
						for (int y = 0; y < iFormatReader.getSizeY(); y++) {
							for (int x = 0; x < iFormatReader.getSizeX(); x++) {
								if((roiMaskImage.getRGB(x, y)&0x00FFFFFF) != 0){
									getImagePane().getHighlightImage().setRGB(x,y,maskColor);
								}
							}
						}
						getImagePane().refreshImage();
					}
				} catch (UserCancelException uce) {
					//Do Nothing
				} catch (Exception e1) {
					e1.printStackTrace();
					DialogUtils.showErrorDialog("Error importing ROI"+e1.getMessage());
				}finally{
					if(iFormatReader != null){try{iFormatReader.close();}catch(Exception e2){e2.printStackTrace();}}
				}
			}
		});
		
//		importROIMaskButton.setText("Import ROI Mask...");
//		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
//		gridBagConstraints_5.insets = new Insets(2, 2, 2, 2);
//		gridBagConstraints_5.gridy = 0;
//		gridBagConstraints_5.gridx = 1;
//		topJPanel.add(importROIMaskButton, gridBagConstraints_5);

		clearROIbutton = new JButton(new ImageIcon(getClass().getResource("/images/clearROI.gif")));
		clearROIbutton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				clearROI();
			}
		});
//		clearROIbutton.setText("Clear ROI");
//		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
//		gridBagConstraints_4.insets = new Insets(2, 2, 2, 2);
//		gridBagConstraints_4.gridy = 0;
//		gridBagConstraints_4.gridx = 2;
//		topJPanel.add(clearROIbutton, gridBagConstraints_4);

		roiTimePlotButton = new JButton(new ImageIcon(getClass().getResource("/images/plotROI.gif")));
		roiTimePlotButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if(getImagePane() == null || getImagePane().getHighlightImage() == null){
					return;
				}
				boolean bHasROI = false;
				for (int y = 0; y < getImagePane().getHighlightImage().getHeight(); y++) {
					for (int x = 0; x < getImagePane().getHighlightImage().getWidth(); x++) {
						if((getImagePane().getHighlightImage().getRGB(x, y)&0x00FFFFFF) != 0){
							bHasROI = true;
							break;
						}
					}
					if(bHasROI){
						break;
					}
				}
				if(bHasROI){
					firePropertyChange(FRAP_DATA_TIMEPLOTROI_PROPERTY, null,new Boolean(true));
				}else{
					DialogUtils.showInfoDialog(
						"ROI for "+roi.getROIName()+" is empty.\n"+
						"Paint, Fill or Import ROI using ROI tools.");
				}
			}
		});
//		roiTimePlotButton.setText("Time Plot ROI...");
//		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
//		gridBagConstraints_3.anchor = GridBagConstraints.WEST;
//		gridBagConstraints_3.gridx = 3;
//		gridBagConstraints_3.gridy = 0;
//		gridBagConstraints_3.insets = new Insets(2, 2, 2, 2);
//		topJPanel.add(roiTimePlotButton, gridBagConstraints_3);
//		BeanUtils.enableComponents(topJPanel, false);

		roiAssistButton = new JButton(new ImageIcon(getClass().getResource("/images/assistantROI.gif")));
		roiAssistButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				firePropertyChange(FRAP_DATA_AUTOROI_PROPERTY, null,new Object());						
			}
		});
//		roiAssistButton.setText(ROI_ASSIST_TEXT);
//		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
//		gridBagConstraints_16.insets = new Insets(2, 2, 2, 0);
//		gridBagConstraints_16.gridy = 0;
//		gridBagConstraints_16.gridx = 4;
//		topJPanel.add(roiAssistButton, gridBagConstraints_16);

		viewZLabel = new JLabel();
		viewZLabel.setText("View Z:");
		final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
		gridBagConstraints_17.insets = new Insets(0, 0, 0, 4);
		gridBagConstraints_17.anchor = GridBagConstraints.EAST;
		gridBagConstraints_17.gridy = 2;
		gridBagConstraints_17.gridx = 0;
		editROIPanel.add(viewZLabel, gridBagConstraints_17);

		final JPanel panel_1 = new JPanel();
		final GridBagLayout gridBagLayout_4 = new GridBagLayout();
		gridBagLayout_4.columnWidths = new int[] {7,0};
		panel_1.setLayout(gridBagLayout_4);
		final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
		gridBagConstraints_18.insets = new Insets(0, 2, 0, 0);
		gridBagConstraints_18.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_18.weightx = 0;
		gridBagConstraints_18.gridy = 2;
		gridBagConstraints_18.gridx = 1;
		editROIPanel.add(panel_1, gridBagConstraints_18);
		final GridBagConstraints gridBagConstraints_19 = new GridBagConstraints();
		gridBagConstraints_19.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_19.anchor = GridBagConstraints.WEST;
		gridBagConstraints_19.weightx = 1;
		gridBagConstraints_19.gridy = 0;
		gridBagConstraints_19.gridx = 0;
		panel_1.add(getZSlider(), gridBagConstraints_19);

		viewTLabel = new JLabel();
		viewTLabel.setText("View Time:");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.insets = new Insets(0, 0, 0, 4);
		gridBagConstraints_13.anchor = GridBagConstraints.EAST;
		gridBagConstraints_13.gridy = 3;
		gridBagConstraints_13.gridx = 0;
		editROIPanel.add(viewTLabel, gridBagConstraints_13);

		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_15.weightx = 1;
		gridBagConstraints_15.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_15.anchor = GridBagConstraints.WEST;
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 0;
		panel.add(getTimeSlider(), gridBagConstraints_15);
		
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_14.insets = new Insets(0, 2, 0, 0);
		gridBagConstraints_14.anchor = GridBagConstraints.WEST;
		gridBagConstraints_14.gridy = 3;
		gridBagConstraints_14.gridx = 1;
		editROIPanel.add(panel, gridBagConstraints_14);

		final JLabel editRoiLabel = new JLabel();
		editRoiLabel.setText("Active ROI:");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.insets = new Insets(0, 0, 0, 4);
		gridBagConstraints_7.anchor = GridBagConstraints.EAST;
		gridBagConstraints_7.gridy = 4;
		gridBagConstraints_7.gridx = 0;
		editROIPanel.add(editRoiLabel, gridBagConstraints_7);

		final JPanel editROIButtonPanel = new JPanel();
		final GridBagLayout gridBagLayout_3 = new GridBagLayout();
		gridBagLayout_3.columnWidths = new int[] {0,7,7};
		editROIButtonPanel.setLayout(gridBagLayout_3);
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.weightx = 0;
		gridBagConstraints_8.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_8.insets = new Insets(0, 2, 0, 0);
		gridBagConstraints_8.anchor = GridBagConstraints.WEST;
		gridBagConstraints_8.gridy = 4;
		gridBagConstraints_8.gridx = 1;
		editROIPanel.add(editROIButtonPanel, gridBagConstraints_8);
		this.add(getLeftJPanel(), gridBagConstraints2);
		this.add(getJScrollPane2(), gridBagConstraints12);

		roiComboBox = new JComboBox();
		roiComboBox.addActionListener(ROI_COMBOBOX_ACTIONLISTENER);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1.insets = new Insets(4, 4, 4, 4);
		gridBagConstraints_1.weightx = 1;
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 0;
		editROIButtonPanel.add(roiComboBox, gridBagConstraints_1);

		addROIButton = new JButton();
		addROIButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try{
					String newROIName = PopupGenerator.showInputDialog0(OverlayEditorPanelJAI.this, "New ROI Name", "");
					if(newROIName == null || newROIName.length() == 0){
						PopupGenerator.showErrorDialog("No ROI Name entered, try again.");
					}else{
						boolean bNameOK = true;
						for (int i = 0; i < roiComboBox.getItemCount(); i++) {
							if(((ComboboxROIName)roiComboBox.getItemAt(i)).getROIName().equals(newROIName)){
								bNameOK = false;
								break;
							}
						}
						if(bNameOK){
							addROIName(newROIName, true, newROIName);
						}else{
							PopupGenerator.showErrorDialog("ROI Name "+newROIName+" already used, try again.");
						}
					}
				}catch(UtilCancelException cancelExc){
					//do Nothing
				}
			}
		});
		addROIButton.setText("Add ROI");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(4, 4, 4, 4);
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 1;
		editROIButtonPanel.add(addROIButton, gridBagConstraints_3);

		delROIButton = new JButton();
		delROIButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				roiComboBox.removeItemAt(roiComboBox.getSelectedIndex());
			}
		});
		delROIButton.setText("Delete ROI");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(4, 4, 4, 4);
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 2;
		editROIButtonPanel.add(delROIButton, gridBagConstraints_4);
		
		roiDrawButtonGroup.add(paintButton);
		roiDrawButtonGroup.add(eraseButton);
		roiDrawButtonGroup.add(fillButton);
		roiDrawButtonGroup.add(cropButton);
		
		BeanUtils.enableComponents(getLeftJPanel(), false);
		BeanUtils.enableComponents(editROIPanel, false);
	}
	
	private void clearROI(){
		saveUndoableROI();
		if (roi!=null){
			short[] roiPixels = roi.getRoiImages()[getRoiImageIndex()].getPixels();
			for (int i = 0; i < roiPixels.length; i++) {
				roiPixels[i] = 0;
			}
			refreshROI();
		}
		fireUndoableEditROI(EDITTYPE_CLEAR);
	}
	
	private void refreshROI(){
		if (roi!=null && imageDataset != null){
//			BufferedImage highlightImage = createHighlightImageFromROI();
//			short[] highlightImageWritebackBuffer = roi.getRoiImages()[getRoiImageIndex()].getPixels();
			getImagePane().setHighlightImageAndWritebackBuffer(
				createHighlightImageFromROI(),
				roi.getRoiImages()[getRoiImageIndex()].getPixels());
		}else{
			getImagePane().setHighlightImageAndWritebackBuffer(null,null);
		}
	}

	/**
	 * Method setROI.
	 * @param argROI ROI
	 */
	public void setROI(ROI argROI){
		roi = argROI;
		refreshROI();
		if(roi != null){
			for (int i = 0; i < roiComboBox.getItemCount(); i++) {
				if(((ComboboxROIName)roiComboBox.getItemAt(i)).getROIName().equals(argROI.getROIName())){
					roiComboBox.setSelectedIndex(i);
					break;
				}
			}
//			if(roi.getROIName().equals(FRAPData.VFRAP_ROI_ENUM.ROI_CELL.name())){
//			}else if(roi.getROIName().equals(FRAPData.VFRAP_ROI_ENUM.ROI_BLEACHED.name())){
//				bleachRadioButton.setSelected(true);
//			}else if(roi.getROIName().equals(FRAPData.VFRAP_ROI_ENUM.ROI_BACKGROUND.name())){
//				backgroundRadioButton.setSelected(true);
//			}
		}
		updateROICursor();
	}
		
	public void setTimeIndex(int timeIndex){
		if(timeSlider.getValue() == timeIndex){
			forceImage();
		}else{
			timeSlider.setValue(timeIndex);
		}
	}
	
	public void saveUserChangesToROI(){
		short[] roiPixels = getImagePane().getHighlightImageWritebackImageBuffer();
		if (roiPixels!=null){
			BufferedImage highlightImage = imagePane.getHighlightImage();
			byte[] redChannelPixels = ImageTools.getBytes(highlightImage, false, 0);
			for (int i = 0; i < roiPixels.length; i++) {
				if (redChannelPixels[i]==0){
					roiPixels[i] = 0;
				}else{
					roiPixels[i] = (short)0xffff;
				}
			}
		}
	}
	
	/**
	 * Method createUnderlyingImage.
	 * @param image UShortImage
	 * @return BufferedImage
	 */
	private BufferedImage createUnderlyingImage(UShortImage image){
		int width = image.getNumX();
		int height = image.getNumY();
		ImageStatistics imageStats = image.getImageStatistics();
		short[] pixels = image.getPixels();
		byte[][] byteData = new byte[3][width*height];
		for (int i = 0; i < byteData[0].length; i++) {
			byteData[0][i] = (imageStats.maxValue < SHORT_TO_BYTE_FACTOR?(byte)pixels[i]:(byte)(((int)(pixels[i]&0x0000FFFF))/SHORT_TO_BYTE_FACTOR));
			byteData[1][i] = byteData[0][i];
			byteData[2][i] = byteData[0][i];
		}
		return ImageTools.makeImage(byteData, width, height);
	}
	
	public void displaySpecialData(short[] specialData,int width, int height) throws Exception{
		if(specialData == null){
			forceImage();
			return;
		}
		UShortImage specialUShortImage = new UShortImage(specialData,null,null,width,height,1);
		BufferedImage specialBufferedImage = createUnderlyingImage(specialUShortImage);
		imagePane.setUnderlyingImage(specialBufferedImage, false,null);
	}
	/**
	 * Method createHighlightImageFromROI.
	 * @return BufferedImage
	 */
	private BufferedImage createHighlightImageFromROI(){
		UShortImage roiImage = roi.getRoiImages()[getRoiImageIndex()];
		int width = roiImage.getNumX();
		int height = roiImage.getNumY();
		byte[][] highlightData = new byte[3][width*height];
		for (int i = 0; i < highlightData[0].length; i++) {
			if (roiImage.getPixels()[i] != 0){
				highlightData[0][i] = (byte)highlightColor.getRed();
				highlightData[1][i] = (byte)highlightColor.getGreen();
				highlightData[2][i] = (byte)highlightColor.getBlue();
			}
		}
		return ImageTools.makeImage(highlightData, width, height);
	}
	
	public void setAllowAddROI(boolean bAllowAddROI){
		this.bAllowAddROI = bAllowAddROI;
	}
	public void addROIName(String roiName,boolean isEditable,String selectROIName){
//		roiComboBox.removeAllItems();
		try{
			roiComboBox.removeActionListener(ROI_COMBOBOX_ACTIONLISTENER);
			roiComboBox.addItem(new ComboboxROIName(roiName,isEditable));
			for (int i = 0; i < roiComboBox.getItemCount(); i++) {
				if(((ComboboxROIName)roiComboBox.getItemAt(i)).getROIName().equals(selectROIName)){
					roiComboBox.setSelectedIndex(i);
					break;
				}
			}

//			for (int i = 0; i < predefinedROINames.length; i++) {
//				roiComboBox.addItem(predefinedROINames[i]);
//			}
//			roiComboBox.setSelectedIndex(0);
		}finally{
			roiComboBox.addActionListener(ROI_COMBOBOX_ACTIONLISTENER);
			ROI_COMBOBOX_ACTIONLISTENER.actionPerformed(new ActionEvent(roiComboBox,0,roiComboBox.getSelectedItem().toString()));
		}
	}
	/** Sets the viewer to display the given images. * @param argImageDataset ImageDataset
	 */
	public void setImages(ImageDataset argImageDataset,boolean bNew,double originalScaleFactor,double originalOffsetFactor) {
		imageDataset = argImageDataset;
		BufferedImage underlyingImage = null;
		if (imageDataset!=null){
			this.originalScaleFactor = originalScaleFactor;
			this.originalOffsetFactor = originalOffsetFactor;
			originalISize = (bNew?imageDataset.getISize():originalISize);
			BeanUtils.enableComponents(leftJPanel, true);
			BeanUtils.enableComponents(topJPanel, true);
			BeanUtils.enableComponents(editROIPanel, true);
			if(!bAllowAddROI){
				addROIButton.setEnabled(false);
			}
			
			timeSlider.setVisible(imageDataset.getSizeT() > 1);
			viewTLabel.setVisible(timeSlider.isVisible());
			int currTimeSliderValue = timeSlider.getValue();
			if(imageDataset.getSizeT() > 1){
//				timeSlider.setLabelTable(timeSlider.createStandardLabels(imageDataset.getSizeT()-1,1));
				Hashtable<Integer, JComponent> labeltable = new Hashtable<Integer, JComponent>();
				labeltable.put(1, new JLabel(imageDataset.getImageTimeStamps()[0]+""));
				labeltable.put(imageDataset.getSizeT()-1,
					new JLabel(NumberUtils.formatNumber(imageDataset.getImageTimeStamps()[imageDataset.getSizeT()-1])));
				timeSlider.setLabelTable(labeltable);
				timeSlider.setMaximum(imageDataset.getSizeT());
				//Added because setMaximum SOMETIMES resets the label table
				timeSlider.setLabelTable(labeltable);
			}
			timeSlider.setValue(Math.max(1,Math.min(imageDataset.getSizeT(),currTimeSliderValue)));
			timeSlider.setMajorTickSpacing(imageDataset.getSizeT());
			timeSlider.setEnabled(imageDataset.getSizeT() > 1);
			
			zSlider.setVisible(imageDataset.getSizeZ() > 1);
			viewZLabel.setVisible(zSlider.isVisible());
			if(imageDataset.getSizeZ() > 1){
				zSlider.setLabelTable(zSlider.createStandardLabels(imageDataset.getSizeZ()-1,1));
			}
			int currZSliderValue = timeSlider.getValue();
			zSlider.setValue(Math.max(1,Math.min(imageDataset.getSizeZ(),currZSliderValue)));
			zSlider.setMaximum(imageDataset.getSizeZ());
			zSlider.setMajorTickSpacing(imageDataset.getSizeZ());
			zSlider.setEnabled(imageDataset.getSizeZ() > 1);
			
			minmaxPixelValues = calcMinMaxPixelValueRange(imageDataset);
			underlyingImage = createUnderlyingImage(imageDataset.getImage((zSlider.getValue()-1),0,(timeSlider.getValue()-1)));
			autoCropButton.setEnabled(isAutoCroppable());
		}else{
			minmaxPixelValues = null;
			this.originalScaleFactor = DEFAULT_SCALE_FACTOR;
			this.originalOffsetFactor = DEFAULT_OFFSET_FACTOR;
			originalISize = null;
			timeSlider.setValue(1);
			timeSlider.setMaximum(1);
			timeSlider.setLabelTable(null);
			timeSlider.setEnabled(false);
			zSlider.setValue(1);
			zSlider.setMaximum(1);
			zSlider.setLabelTable(null);
			zSlider.setEnabled(false);
			BeanUtils.enableComponents(leftJPanel, false);
			BeanUtils.enableComponents(topJPanel, false);
			BeanUtils.enableComponents(editROIPanel, false);
			underlyingImage = null;
			setROI(null);
		}

		updateLabel(-1, -1);
		getImagePane().setUnderlyingImage(underlyingImage,bNew,minmaxPixelValues);
		if(imageDataset != null && bNew){
			contrastButtonPlus.doClick();
		}
	}

	private Range calcMinMaxPixelValueRange(ImageDataset argImageDataset){
		UShortImage[] allImages = argImageDataset.getAllImages();
		double min = 0;
		double max = min;
		for (int i = 0; i < allImages.length; i++) {
			ImageStatistics imageStats = allImages[i].getImageStatistics();
			if(i==0 || imageStats.minValue < min){min = imageStats.minValue;}
			if(i==0 || imageStats.maxValue > max){max = imageStats.maxValue;}
		}
		if(max < SHORT_TO_BYTE_FACTOR){
			return new Range(min,max);
		}
		return new Range(min/SHORT_TO_BYTE_FACTOR,max/SHORT_TO_BYTE_FACTOR);
//		short min = argImageDataset.getPixelsZ(0, 0)[0];
//		short max = min;
//		for (int c = 0; c < argImageDataset.getSizeC(); c++) {
//			for (int t = 0; t < argImageDataset.getSizeT(); t++) {
//				short[] pixelVals = argImageDataset.getPixelsZ(c, t);
//				for (int p = 0; p < pixelVals.length; p++) {
//					if(pixelVals[p] < min){min = pixelVals[p];}
//					if(pixelVals[p] > max){max = pixelVals[p];}
//				}
//			}
//		}
//		return new Range(min,max);
	}
	/** Gets the currently displayed image. * @return BufferedImage
	 */
	private BufferedImage getImage() {
		if(imageDataset == null){
			return null;
		}
		int ndx = getImageIndex();
		if (imageDataset == null || ndx >= imageDataset.getAllImages().length){
			return null;
		}
		UShortImage image = imageDataset.getAllImages()[ndx];
		return createUnderlyingImage(image);
	}

	private void saveUndoableROI(){
		if(roi == null){
			return;
		}
		saveUserChangesToROI();
		try{
			undoableROI = new ROI(roi);
		}catch(Exception e2){
			e2.printStackTrace();
			//can't happen
		}
	}
	private static final String EDITTYPE_FILL = "fill";
	private static final String EDITTYPE_PAINT = "paint";
	private static final String EDITTYPE_ERASE = "erase";
	private static final String EDITTYPE_CLEAR = "clear";
	private void fireUndoableEditROI(final String editType){
		if(undoableROI == null){
			return;
		}
		final ROI originalROI = undoableROI;
		undoableROI = null;
		if(editType != null){
			undoableEditSupport.postEdit(
				new AbstractUndoableEdit(){
					public boolean canUndo() {
						return true;
					}
					public String getUndoPresentationName() {
						return editType+" "+originalROI.getROIName();
					}
					public void undo() throws CannotUndoException {
						super.undo();
						firePropertyChange(FRAP_DATA_UNDOROI_PROPERTY, null,originalROI);
					}
				}
			);			
		}else{
			undoableEditSupport.postEdit(OverlayEditorPanelJAI.CLEAR_UNDOABLE_EDIT);
		}
		
	}
	/**
	 * Method getImagePane.
	 * @return OverlayImageDisplayJAI
	 */
	private OverlayImageDisplayJAI getImagePane(){
		if (imagePane == null){
			imagePane = new OverlayImageDisplayJAI();
			//imagePane = new ZoomableOverlayImagePane();
			imagePane.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(MouseEvent e){
					updateLabel(-1, -1);
				}
				public void mousePressed(MouseEvent e){
					updateLabel(e.getX(), e.getY());
					lastHighlightPoint = e.getPoint();
					saveUndoableROI();
					if(paintButton.isSelected() || eraseButton.isSelected()){
						drawHighlight(e.getX(), e.getY(), 10, eraseButton.isSelected());
					}
				}
				public void mouseReleased(MouseEvent e){

					if(paintButton.isSelected()){
						fireUndoableEditROI(EDITTYPE_PAINT);
					}else if(eraseButton.isSelected()){
						fireUndoableEditROI(EDITTYPE_ERASE);
					}else if(fillButton.isSelected()){
						//done later in 'click'
					}else{
						fireUndoableEditROI(null);//remove any pending undoableEdit
					}
					
					if(cropButton.isSelected()){
						Rectangle cropRect =
							(imagePane.getHighlightImage() == null
							?null
							:(imagePane.getCrop() == null
								?null
								:(Rectangle)imagePane.getCrop().clone()));
						if(cropRect == null){
							imagePane.setCrop(null, null);
							return;
						}
						cropRect.x = (int)(cropRect.x/imagePane.getZoom());
						cropRect.y = (int)(cropRect.y/imagePane.getZoom());
						cropRect.width = (int)(cropRect.width/imagePane.getZoom());
						cropRect.height = (int)(cropRect.height/imagePane.getZoom());
						if(cropRect.x < 0){cropRect.x = 0;}
						if(cropRect.y < 0){cropRect.y = 0;}
						if(cropRect.x >= imagePane.getHighlightImage().getWidth() ||
							cropRect.y >= imagePane.getHighlightImage().getHeight()){
							imagePane.setCrop(null, null);
							return;
						}
						if(cropRect.x+cropRect.width >= imagePane.getHighlightImage().getWidth()){
							cropRect.width = imagePane.getHighlightImage().getWidth()-cropRect.x;
						}
						if(cropRect.y+cropRect.height >= imagePane.getHighlightImage().getHeight()){
							cropRect.height = imagePane.getHighlightImage().getHeight()-cropRect.y;
						}
						if(cropRect.width <= 0){
							cropRect.width = 1;
						}if(cropRect.height <= 0){
							cropRect.height = 1;
						}
						if(!cropConfirm(cropRect)){
							return;
						}
						imagePane.setCrop(null, null);
						firePropertyChange(FRAP_DATA_CROP_PROPERTY, null, cropRect);
					}
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					if(fillButton.isSelected()){
						fireUndoableEditROI(EDITTYPE_FILL);
						try{
							waitCursor(true);
							ROI.fillAtPoint(
								(int)(e.getPoint().getX()/imagePane.getZoom()),
								(int)(e.getPoint().getY()/imagePane.getZoom()),
								imagePane.getHighlightImage(),
								highlightColor.getRGB());
//								VirtualFrapLoader.mf.setSaveStatus(true);
						}finally{
							imagePane.refreshImage();
							waitCursor(false);
						}
					}
				}
			});
			imagePane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {   
				@Override
				public void mouseDragged(java.awt.event.MouseEvent e) {
					updateLabel(e.getX(), e.getY());
					if(paintButton.isSelected() || eraseButton.isSelected()){
						drawHighlight(e.getX(), e.getY(), 10, eraseButton.isSelected());
						lastHighlightPoint = e.getPoint();
//						VirtualFrapLoader.mf.setSaveStatus(true);
					}else if(cropButton.isSelected()){
						imagePane.setCrop(lastHighlightPoint, e.getPoint());
					}
				}
				@Override
				public void mouseMoved(java.awt.event.MouseEvent e) {
					updateLabel(e.getX(), e.getY());
				}
			});
		}
		return imagePane;
	}
	
	private boolean cropConfirm(Rectangle cropRect){
		JLabel cropConfirmJlabel =
			new JLabel("Crop FRAP data to new bounds?: ("+cropRect.x+","+cropRect.y+") to ("+
					(cropRect.x+cropRect.width-1)+","+(cropRect.y+cropRect.height-1)+")");
		cropConfirmJlabel.setPreferredSize(new Dimension(300,40));
		cropConfirmJlabel.setMinimumSize(new Dimension(300,40));
		if(DialogUtils.showComponentOKCancelDialog(
			OverlayEditorPanelJAI.this,
			cropConfirmJlabel,
			"Confirm Crop FRAP Data to new boundaries.") != JOptionPane.OK_OPTION){
			imagePane.setCrop(null, null);
			return false;
		}
//		VirtualFrapLoader.mf.setSaveStatus(true);
		return true;
	}
	/**
	 * Method drawHighlight.
	 * @param x int
	 * @param y int
	 * @param radius int
	 * @param erase boolean
	 */
	private void drawHighlight(int x, int y, int radius, boolean erase){
		imagePane.drawHighlight(x, y, radius, erase, highlightColor, lastHighlightPoint);
		repaint();
	}

	/**
	 * This method initializes timeSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getTimeSlider() {
		if (timeSlider == null) {
			timeSlider = new JSlider(1,1);
			timeSlider.setPaintLabels(true);
			timeSlider.setMajorTickSpacing(100);
			timeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					forceImage();
//					updateLabel(-1, -1);
//					if(imageDataset != null){
//						BufferedImage image = getImage();
//						if (image != null){
//							imagePane.setUnderlyingImage(image,false);
//						}
//					}
//					imagePane.repaint();
				}
			});
		}
		return timeSlider;
	}
	private void forceImage(){
		updateLabel(-1, -1);
		if(imageDataset != null){
			BufferedImage image = getImage();
			if (image != null){
				imagePane.setUnderlyingImage(image,false,minmaxPixelValues);
			}
		}
		imagePane.repaint();

	}
	/** Gets the index of the currently displayed image. * @return int
	 */
	private int getImageIndex() { return imageDataset.getIndexFromZCT(getZ(),0,getT()); }
	
	/** Gets the index of the currently displayed image. * @return int
	 */
	private int getRoiImageIndex() { return getZ(); }
	
	/** Gets the T value of the currently displayed image. * @return int
	 */
	public int getT() { return Math.max(0,Math.min(imageDataset.getSizeT(),timeSlider.getValue())) - 1; }

	/** Gets the Z value of the currently displayed image. * @return int
	 */
	public int getZ() { return Math.max(0,Math.min(imageDataset.getSizeZ(),zSlider.getValue())) - 1; }


	/** Updates cursor probe label. * @param x int
	 * @param y int
	 */
	protected void updateLabel(int inx, int iny) {
		if (imageDataset == null) {
			return;
		}
		float zoom = imagePane.getZoom();
		if((inx/zoom)>= imageDataset.getISize().getX() || (iny/zoom)>= imageDataset.getISize().getY()){
			inx = -1;
			iny = -1;
		}
		int x = (int)(inx/zoom);
		int y = (int)(iny/zoom);
		UShortImage[] images = imageDataset.getAllImages();
		int ndx = getImageIndex();
		sb.setLength(0);
		boolean bMultipleZ = imageDataset.getSizeZ() > 1;
		if (bMultipleZ) {
			sb.append("; Z=");
			sb.append(getZ() + 1);
			sb.append("/");
			sb.append(imageDataset.getSizeZ());
		}
		if (imageDataset.getSizeT() > 1) {
			sb.append((bMultipleZ?"; ":"")+"T=");
//			sb.append(getT() + 1);
			sb.append(imageDataset.getImageTimeStamps()[getT()]);
//			sb.append("/");
//			sb.append(imageDataset.getSizeT());
		}
//		BufferedImage image = ImageTools.makeImage(images[ndx].getPixels(), images[ndx].getNumX(), images[ndx].getNumY());
//		int w = image == null ? -1 : image.getWidth();
//		int h = image == null ? -1 : image.getHeight();
		int w = imageDataset == null ? -1 : imageDataset.getISize().getX();
		int h = imageDataset == null ? -1 : imageDataset.getISize().getY();
		if (x >= w) x = w - 1;
		if (y >= h) y = h - 1;
		if (x >= 0 && y >= 0 && inx >= 0 && iny >= 0 &&
				x < imageDataset.getISize().getX() &&
				y < imageDataset.getISize().getY()){
			if (images.length > 1) sb.append("; ");
			sb.append("X=");
			sb.append(x);
			if (w > 0) {
				sb.append("/");
				sb.append(w);
			}
			sb.append("; Y=");
			sb.append(y);
			if (h > 0) {
				sb.append("/");
				sb.append(h);
			}
			sb.append("; zoom("+NumberUtils.formatNumber(imagePane.getZoom(), 3)+")");
			sb.append("; contr("+imagePane.getContrastDescription()+")");
			if (imageDataset != null) {
//				Raster r = image.getRaster();
//				double[] pix = r.getPixel(x, y, (double[]) null);
				short[] pix = null;
				try{
					pix = new short[imageDataset.getSizeC()];
					for (int i = 0; i < pix.length; i++) {
						pix[i] = imageDataset.getImage(getZ(), i, getT()).getPixel(x, y, 0);						
					}
				}catch(Exception e){
					pix = null;
					e.printStackTrace();
					//do nothing
				}
				sb.append("; value"+(isOriginalValueScaled()?"(scld)":""));
				if(pix != null){
					sb.append(pix.length > 1 ? "s=(" : "=");
					for (int i=0; i<pix.length; i++) {
						if (i > 0) sb.append(", ");
						sb.append((int)(pix[i]&0x0000FFFF));
					}
					if (pix.length > 1) sb.append(")");
				}else{
					sb.append(" error");
				}
				if(isOriginalValueScaled()){
					sb.append("; value(orig)");
					if(pix != null){
						sb.append(pix.length > 1 ? "s=(" : "=");
						for (int i=0; i<pix.length; i++) {
							if (i > 0) sb.append(", ");
							sb.append(
								NumberUtils.formatNumber(
								(((int)(pix[i]&0x0000FFFF))-originalOffsetFactor)/
								originalScaleFactor
								, 6));
						}
						if (pix.length > 1) sb.append(")");
					}else{
						sb.append(" error");
					}
					
				}
				
//				sb.append(" udnerly="+Integer.toHexString(imagePane.getUnderlyingImage().getRGB(x, y)));
			}
		}else{
			sb.append("; zoom("+NumberUtils.formatNumber(imagePane.getZoom(), 3)+")");
			sb.append("; contr("+imagePane.getContrastDescription()+")");
		}
		sb.append(" ");
		textLabel.setText(sb.toString());
	}

	private boolean isOriginalValueScaled(){
		if(originalOffsetFactor != 0 || originalScaleFactor != 1){
			return true;
		}
		return false;
	}
	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getImagePane());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getLeftJPanel() {
		if (leftJPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipady = 0;
			gridBagConstraints3.weighty = 1.0D;
			gridBagConstraints3.anchor = GridBagConstraints.NORTH;
			gridBagConstraints3.ipadx = 0;
			gridBagConstraints3.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipady = 0;
			gridBagConstraints.gridy = 0;
			
			leftJPanel = new JPanel();
			final GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.rowHeights = new int[] {0,0,7,7,7,0,7};
			leftJPanel.setLayout(gridBagLayout);
			leftJPanel.add(getZoomInButton(), gridBagConstraints);
			leftJPanel.add(getZoomOutButton(), gridBagConstraints3);

			contrastButtonPlus = new JButton(new ImageIcon(getClass().getResource("/images/contrastUp.gif")));
			contrastButtonPlus.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					imagePane.increaseContrast();
					updateLabel(-1,-1);
				}
			});
			contrastButtonPlus.setPreferredSize(new Dimension(32, 32));
			contrastButtonPlus.setMinimumSize(new Dimension(32, 32));
			contrastButtonPlus.setMaximumSize(new Dimension(32, 32));
			contrastButtonPlus.setMargin(new Insets(2, 2, 2, 2));
			contrastButtonPlus.setToolTipText("Increase Contrast");
			final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			gridBagConstraints_4.gridy = 2;
			gridBagConstraints_4.gridx = 0;
			leftJPanel.add(contrastButtonPlus, gridBagConstraints_4);

			contrastButtonMinus = new JButton(new ImageIcon(getClass().getResource("/images/contrastDown.gif")));
			contrastButtonMinus.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					imagePane.decreaseContrast();
					updateLabel(-1,-1);
				}
			});
			contrastButtonMinus.setPreferredSize(new Dimension(32, 32));
			contrastButtonMinus.setMinimumSize(new Dimension(32, 32));
			contrastButtonMinus.setMaximumSize(new Dimension(32, 32));
			contrastButtonMinus.setMargin(new Insets(2, 2, 2, 2));
			contrastButtonMinus.setToolTipText("Decrease Contrast");
			final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
			gridBagConstraints_6.insets = new Insets(0, 0, 10, 0);
			gridBagConstraints_6.gridy = 3;
			gridBagConstraints_6.gridx = 0;
			leftJPanel.add(contrastButtonMinus, gridBagConstraints_6);

			cropButton = new JToggleButton(new ImageIcon(getClass().getResource("/images/crop.gif")));
			cropButton.setPreferredSize(new Dimension(32, 32));
			cropButton.setMinimumSize(new Dimension(32, 32));
			cropButton.setMaximumSize(new Dimension(32, 32));
			cropButton.setMargin(new Insets(2, 2, 2, 2));
			cropButton.setToolTipText("Crop");
			final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			gridBagConstraints_5.gridy = 4;
			gridBagConstraints_5.gridx = 0;
			leftJPanel.add(cropButton, gridBagConstraints_5);
			
			
			autoCropButton.setPreferredSize(new Dimension(32, 32));
			autoCropButton.setMinimumSize(new Dimension(32, 32));
			autoCropButton.setMaximumSize(new Dimension(32, 32));
			autoCropButton.setMargin(new Insets(2, 2, 2, 2));
			autoCropButton.setToolTipText("Auto Crop");
			final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
			gridBagConstraints_7.insets = new Insets(0, 0, 10, 0);
			gridBagConstraints_7.gridy = 5;
			gridBagConstraints_7.gridx = 0;
			leftJPanel.add(autoCropButton, gridBagConstraints_7);
			
			paintButton = new JToggleButton(new ImageIcon(getClass().getResource("/images/paint.gif")));
			paintButton.setSelected(true);
			paintButton.setPreferredSize(new Dimension(32, 32));
			paintButton.setMinimumSize(new Dimension(32, 32));
			paintButton.setMaximumSize(new Dimension(32, 32));
			paintButton.setPreferredSize(new Dimension(32, 32));
			paintButton.setMinimumSize(new Dimension(32, 32));
			paintButton.setMaximumSize(new Dimension(32, 32));
			paintButton.setMargin(new Insets(2, 2, 2, 2));
			paintButton.setToolTipText("Paint");
			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
			gridBagConstraints_1.gridy = 6;
			gridBagConstraints_1.gridx = 0;
			leftJPanel.add(paintButton, gridBagConstraints_1);

			eraseButton = new JToggleButton(new ImageIcon(getClass().getResource("/images/eraser.gif")));
			eraseButton.setPreferredSize(new Dimension(32, 32));
			eraseButton.setMinimumSize(new Dimension(32, 32));
			eraseButton.setMaximumSize(new Dimension(32, 32));
			eraseButton.setPreferredSize(new Dimension(32, 32));
			eraseButton.setMinimumSize(new Dimension(32, 32));
			eraseButton.setMaximumSize(new Dimension(32, 32));
			eraseButton.setMargin(new Insets(2, 2, 2, 2));
			eraseButton.setToolTipText("Erase");
			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
			gridBagConstraints_2.gridy = 7;
			gridBagConstraints_2.gridx = 0;
			leftJPanel.add(eraseButton, gridBagConstraints_2);

			fillButton = new JToggleButton(new ImageIcon(getClass().getResource("/images/fill.gif")));
			fillButton.setPreferredSize(new Dimension(32, 32));
			fillButton.setMinimumSize(new Dimension(32, 32));
			fillButton.setMaximumSize(new Dimension(32, 32));
			fillButton.setPreferredSize(new Dimension(32, 32));
			fillButton.setMinimumSize(new Dimension(32, 32));
			fillButton.setMaximumSize(new Dimension(32, 32));
			fillButton.setMargin(new Insets(2, 2, 2, 2));
			fillButton.setToolTipText("Fill");
			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
			gridBagConstraints_3.gridy = 8;
			gridBagConstraints_3.gridx = 0;
			gridBagConstraints_3.insets = new Insets(0, 0, 10, 0);
			leftJPanel.add(fillButton, gridBagConstraints_3);

			importROIMaskButton.setPreferredSize(new Dimension(32, 32));
			importROIMaskButton.setMinimumSize(new Dimension(32, 32));
			importROIMaskButton.setMaximumSize(new Dimension(32, 32));
			importROIMaskButton.setPreferredSize(new Dimension(32, 32));
			importROIMaskButton.setMinimumSize(new Dimension(32, 32));
			importROIMaskButton.setMaximumSize(new Dimension(32, 32));
			importROIMaskButton.setMargin(new Insets(2, 2, 2, 2));
			importROIMaskButton.setToolTipText("Import ROI mask from file");
			final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
			gridBagConstraints_8.gridy = 9;
			gridBagConstraints_8.gridx = 0;
			leftJPanel.add(importROIMaskButton, gridBagConstraints_8);
			
			clearROIbutton.setPreferredSize(new Dimension(32, 32));
			clearROIbutton.setMinimumSize(new Dimension(32, 32));
			clearROIbutton.setMaximumSize(new Dimension(32, 32));
			clearROIbutton.setPreferredSize(new Dimension(32, 32));
			clearROIbutton.setMinimumSize(new Dimension(32, 32));
			clearROIbutton.setMaximumSize(new Dimension(32, 32));
			clearROIbutton.setMargin(new Insets(2, 2, 2, 2));
			clearROIbutton.setToolTipText("Clear ROI");
			final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
			gridBagConstraints_9.gridy = 10;
			gridBagConstraints_9.gridx = 0;
			leftJPanel.add(clearROIbutton, gridBagConstraints_9);
			
			roiTimePlotButton.setPreferredSize(new Dimension(32, 32));
			roiTimePlotButton.setMinimumSize(new Dimension(32, 32));
			roiTimePlotButton.setMaximumSize(new Dimension(32, 32));
			roiTimePlotButton.setPreferredSize(new Dimension(32, 32));
			roiTimePlotButton.setMinimumSize(new Dimension(32, 32));
			roiTimePlotButton.setMaximumSize(new Dimension(32, 32));
			roiTimePlotButton.setMargin(new Insets(2, 2, 2, 2));
			roiTimePlotButton.setToolTipText("ROI time plot");
			final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
			gridBagConstraints_10.gridy = 11;
			gridBagConstraints_10.gridx = 0;
			leftJPanel.add(roiTimePlotButton, gridBagConstraints_10);
			
			roiAssistButton.setPreferredSize(new Dimension(32, 32));
			roiAssistButton.setMinimumSize(new Dimension(32, 32));
			roiAssistButton.setMaximumSize(new Dimension(32, 32));
			roiAssistButton.setPreferredSize(new Dimension(32, 32));
			roiAssistButton.setMinimumSize(new Dimension(32, 32));
			roiAssistButton.setMaximumSize(new Dimension(32, 32));
			roiAssistButton.setMargin(new Insets(2, 2, 2, 2));
			roiAssistButton.setToolTipText("ROI assistant");
			final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
			gridBagConstraints_11.gridy = 12;
			gridBagConstraints_11.gridx = 0;
			leftJPanel.add(roiAssistButton, gridBagConstraints_11);
			
		}
		return leftJPanel;
	}

	/**
	 * This method initializes zoomInButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getZoomInButton() {
		if (zoomInButton == null) {
			zoomInButton = new JButton();
			zoomInButton.setMargin(new Insets(2, 2, 2, 2));
			zoomInButton.setMinimumSize(new Dimension(32, 32));
			zoomInButton.setMaximumSize(new Dimension(32, 32));
			zoomInButton.setIcon(new ImageIcon(getClass().getResource("/images/zoomin.gif")));
			zoomInButton.setPreferredSize(new Dimension(32, 32));
			zoomInButton.setIcon(new ImageIcon(getClass().getResource("/images/zoomin.gif")));
			zoomInButton.setToolTipText("Zoom In");
			zoomInButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getImagePane().setZoom(getImagePane().getZoom()*1.3f);
					updateLabel(-1,-1);
				}
			});
		}
		return zoomInButton;
	}
	
	/**
	 * This method initializes zoomOutButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getZoomOutButton() {
		if (zoomOutButton == null) {
			zoomOutButton = new JButton();
			zoomOutButton.setPreferredSize(new Dimension(32, 32));
			zoomOutButton.setMinimumSize(new Dimension(32, 32));
			zoomOutButton.setMaximumSize(new Dimension(32, 32));
			zoomOutButton.setMargin(new Insets(2, 2, 2, 2));
			zoomOutButton.setIcon(new ImageIcon(getClass().getResource("/images/zoomout.gif")));
			zoomOutButton.setPreferredSize(new Dimension(32, 32));
			zoomOutButton.setToolTipText("Zoom Out");
			zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getImagePane().setZoom(getImagePane().getZoom()/1.3f);
					updateLabel(-1,-1);
				}
			});
			zoomOutButton.setIcon(new ImageIcon(getClass().getResource("/images/zoomout.gif")));
		}
		return zoomOutButton;
	}


	/**
	 * This method initializes zSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getZSlider() {
		if (zSlider == null) {
			zSlider = new JSlider(1,1);
			zSlider.setMajorTickSpacing(100);
			zSlider.setPaintLabels(true);
			zSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					saveUserChangesToROI();
					updateLabel(-1, -1);
					BufferedImage image = getImage();
					if (image != null){
						imagePane.setUnderlyingImage(image,false,minmaxPixelValues);
					}
					refreshROI();
					imagePane.repaint();
				}
			});
		}
		return zSlider;
	}
	
	public void setCustomROIImport(CustomROIImport customROIImport){
		this.customROIImport = customROIImport;
	}

	public void setDefaultImportDirAndFilters(File defaultImportDir,FileFilter[] fileFilterArr){
	    openJFileChooser.setCurrentDirectory(defaultImportDir);
	    for (int i = 0; i < fileFilterArr.length; i++) {
	    	openJFileChooser.addChoosableFileFilter(fileFilterArr[i]);
		}
	}
}
