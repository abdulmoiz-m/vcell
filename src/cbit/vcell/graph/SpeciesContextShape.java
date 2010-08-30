package cbit.vcell.graph;
/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import cbit.gui.graph.*;
import cbit.gui.graph.visualstate.MutableVisualState;
import cbit.gui.graph.visualstate.VisualState;
import cbit.vcell.biomodel.meta.MiriamManager;
import cbit.vcell.biomodel.meta.MiriamManager.MiriamRefGroup;
import cbit.vcell.model.*;
import java.awt.*;
import java.util.*;

import org.vcell.sybil.models.miriam.MIRIAMQualifier;

public class SpeciesContextShape extends ElipseShape {
	SpeciesContext speciesContext = null;
	private static final int RADIUS = 8;
	public static final int DIAMETER = 2*RADIUS;
	private static final int SMALL_DIAMETER = DIAMETER-1;
	private Color darkerBackground = null;

	private static final int SCS_LABEL_WIDTHPARM = 3;
	private static final String SCS_LABEL_TRUCATED = "...";
	private String smallLabel = null;
	protected Dimension smallLabelSize = new Dimension();
	protected Point smallLabelPos = new Point(0,0);

	private boolean bTruncateLabelName = true;

	public SpeciesContextShape(SpeciesContext speciesContext, GraphModel graphModel) {
		super(graphModel);
		this.speciesContext = speciesContext;
		defaultBG = java.awt.Color.green;
		defaultFGselect = java.awt.Color.black;
		backgroundColor = defaultBG;
		darkerBackground = backgroundColor.darker().darker();
	}

	@Override
	public VisualState createVisualState() { 
		return new MutableVisualState(this, VisualState.PaintLayer.NODE); 
	}

	protected void delete() throws Exception, InUseException {
		Model model = ((ModelCartoon)graphModel).getModel();
		model.removeSpeciesContext(getSpeciesContext());
		//	graphModel.update(null,null);
	}


	/**
	 * This method was created in VisualAge.
	 * @return java.lang.Object
	 */
	@Override
	public Object getModelObject() {
		return speciesContext;
	}


	/**
	 * This method was created by a SmartGuide.
	 * @return int
	 * @param g java.awt.Graphics
	 */
	@Override
	public Dimension getPreferedSize(java.awt.Graphics2D g) {
		java.awt.FontMetrics fm = g.getFontMetrics();
		labelSize.height = fm.getMaxAscent() + fm.getMaxDescent();
		labelSize.width = fm.stringWidth(getLabel());
		smallLabelSize.width = (smallLabel != null?fm.stringWidth(smallLabel):labelSize.width);
		smallLabelSize.height = labelSize.height;
		//	preferedSize.height = radius*2 + labelSize.height;
		//	preferedSize.width = Math.max(radius*2,labelSize.width);
		preferredSize.height = DIAMETER;
		preferredSize.width = DIAMETER;
		return preferredSize;
	}


	/**
	 * This method was created by a SmartGuide.
	 * @return int
	 */
	@Override
	public Point getSeparatorDeepCount() {	
		return new Point(0,0);
	}


	/**
	 * This method was created by a SmartGuide.
	 * @return cbit.vcell.model.Species
	 */
	public SpeciesContext getSpeciesContext() {
		return speciesContext;
	}


	/**
	 * This method was created by a SmartGuide.
	 * @return int
	 * @param g java.awt.Graphics
	 */
	@Override
	public void refreshLayout() {

		//	if (screenSize.width<labelSize.width ||
		//		 screenSize.height<labelSize.height){
		//		 throw new Exception("screen size smaller than label");
		//	} 
		//
		// this is like a row/column layout  (1 column)
		//
		int centerX = shapeSize.width/2;

		//
		// position label
		//
		labelPos.x = centerX - labelSize.width/2;
		labelPos.y = 0;
		smallLabelPos.x = centerX - smallLabelSize.width/2;
		smallLabelPos.y = labelPos.y;
	}

	@Override
	public void paintSelf(Graphics2D g, int absPosX, int absPosY ) {

		boolean isBound = false;
		SpeciesContext sc = (SpeciesContext)getModelObject();
		boolean bHasPCLink = false;
		if (graphModel instanceof ModelCartoon) {
			ModelCartoon mc = (ModelCartoon)graphModel;
			// check if species has Pathway Commons link by querying VCMetadata : if it does, need to change color of speciesContext.
			try {
				MiriamManager miriamManager = mc.getModel().getVcMetaData().getMiriamManager();
				Map<MiriamRefGroup,MIRIAMQualifier> miriamRefGroups = miriamManager.getAllMiriamRefGroups(sc.getSpecies());
				if (miriamRefGroups!=null && miriamRefGroups.size()>0){
					bHasPCLink = true;
				}
			}catch (Exception e){
				e.printStackTrace(System.out);
			}
		}
		if(sc.getSpecies().getDBSpecies() != null || bHasPCLink){
			isBound = true;
		}
		//
		// draw elipse
		//
		g.setColor((!isBound && !isSelected()?darkerBackground:backgroundColor));
		g.fillOval(absPosX+1,absPosY+1+labelPos.y,SMALL_DIAMETER,SMALL_DIAMETER);
		g.setColor(forgroundColor);
		g.drawOval(absPosX,absPosY+labelPos.y,DIAMETER,DIAMETER);

		//g.drawRect(absPosX,absPosY,DIAMETER,DIAMETER);
		//
		// draw label
		//
		//java.awt.FontMetrics fm = g.getFontMetrics();
		//int textX = labelPos.x + absPosX;
		//int textY = labelPos.y + absPosY;
		g.setColor(forgroundColor);
		if (getLabel()!=null && getLabel().length()>0){
			if(isSelected()){//clear background and outline to make selected label stand out
				drawRaisedOutline(
						labelPos.x + absPosX - 5,labelPos.y + absPosY-labelSize.height+3,labelSize.width + 10,labelSize.height,
						g,Color.white,forgroundColor,Color.gray);
			}
			g.setColor(forgroundColor);
			g.drawString(
					(isSelected() || smallLabel == null?getLabel():smallLabel),
					(isSelected() || smallLabel == null?labelPos.x:smallLabelPos.x) + absPosX,labelPos.y + absPosY);
		}

		//g.drawRect(smallLabelPos.x+absPosX,smallLabelPos.y+absPosY-smallLabelSize.height,smallLabelSize.width,smallLabelSize.height);

		//SpeciesContext sc = (SpeciesContext)getModelObject();
		//if(sc.getSpecies().getDBSpecies() != null && sc.getSpecies().getDBSpecies().getFormalSpeciesType().equals(cbit.vcell.dictionary.FormalSpeciesType.enzyme)){
		////
		//// draw triangle
		////
		//Polygon poly = new Polygon();
		//poly.addPoint(absPosX+radius,absPosY+1+labelPos.y);
		//poly.addPoint(absPosX+(2*radius),absPosY+(2*radius)+labelPos.y);
		//poly.addPoint(absPosX,absPosY+(2*radius)+labelPos.y);

		//g.setColor(backgroundColor);
		//g.fillPolygon(poly);
		//g.setColor(forgroundColor);
		//g.drawPolygon(poly);
		//}else{
		////
		//// draw elipse
		////
		//g.setColor(backgroundColor);
		//g.fillOval(absPosX+1,absPosY+1+labelPos.y,2*radius-1,2*radius-1);
		//g.setColor(forgroundColor);
		//g.drawOval(absPosX,absPosY+labelPos.y,2*radius,2*radius);
		//}
	}

	/**
	 * This method was created in VisualAge.
	 */
	@Override
	public void refreshLabel() {
		switch (GraphModelPreferences.getInstance().getSpeciesContextDisplayName()) {
		case GraphModelPreferences.DISPLAY_COMMON_NAME: {
			setLabel(getSpeciesContext().getSpecies().getCommonName());
			break;
		}
		case GraphModelPreferences.DISPLAY_CONTEXT_NAME: {
			setLabel(getSpeciesContext().getName());
			break;
		}
		//case GraphModelPreferences.DISPLAY_FORMAL_NAME: {
		//setLabel(getSpeciesContext().getSpecies().getCommonName());
		//break;
		//}
		}

		smallLabel = getLabel();
		if(bTruncateLabelName && getLabel().length() > (2*SCS_LABEL_WIDTHPARM + SCS_LABEL_TRUCATED.length())){
			smallLabel =
				getLabel().substring(0,SCS_LABEL_WIDTHPARM)+
				SCS_LABEL_TRUCATED+
				getLabel().substring(getLabel().length()-SCS_LABEL_WIDTHPARM);
		}
	}


	/**
	 * This method was created by a SmartGuide.
	 * @param newSize java.awt.Dimension
	 */
	@Override
	public void resize(Graphics2D g, Dimension newSize) {
		return;
	}


	/**
	 * Insert the method's description here.
	 * Creation date: (8/28/2006 9:53:57 AM)
	 * @param bTruncate boolean
	 */
	public void truncateLabelName(boolean bTruncate) {

		bTruncateLabelName = bTruncate;
	}
}