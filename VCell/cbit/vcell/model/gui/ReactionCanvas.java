package cbit.vcell.model.gui;
/*�
 * (C) Copyright University of Connecticut Health Center 2001.
 * All rights reserved.
�*/
import java.awt.*;

import cbit.vcell.dictionary.ReactionCanvasDisplaySpec;
import cbit.vcell.model.*;
import cbit.vcell.model.render.ReactionRenderer;

import java.util.Vector;
/**
 * This class was generated by a SmartGuide.
 * 
 */
public class ReactionCanvas extends javax.swing.JPanel implements java.beans.PropertyChangeListener {
	private java.awt.Dimension expressionBounds = new Dimension();
	private java.awt.Image offScreenImage = null;
	private java.awt.Dimension offScreenImageSize = null;
	private cbit.vcell.model.ReactionStep fieldReactionStep = null;
	private ReactionCanvasDisplaySpec fieldReactionCanvasDisplaySpec = null;

/**
 * Constructor
 */
public ReactionCanvas() {
	super();
	initialize();
}

//used by the publish package.
	public int getReactionAsImage(Image customImage, int width, int height, int fontSize) {

	try { 
		if (getReactionStep()== null){
			System.err.println("Cannot generate image: no reaction step.");
			return fontSize;
		}
		if (fontSize <= 0) {									//default font size.
			fontSize = 12;
		}
		fontSize = fontSize - 1;
		Dimension newBounds = ReactionRenderer.paint(getReactionCanvasDisplaySpec(),customImage,width,height,fontSize,getBackground());

	
	}catch (Exception e){
		offScreenImage = null;
		System.out.println("exception in ReactionCanvas.refreshGraphics()");
		e.printStackTrace(System.out);
	}
	
	return fontSize;
}


/**
 * Gets the reactionCanvasDisplaySpec property (cbit.vcell.model.gui.ReactionCanvasDisplaySpec) value.
 * @return The reactionCanvasDisplaySpec property value.
 * @see #setReactionCanvasDisplaySpec
 */
public ReactionCanvasDisplaySpec getReactionCanvasDisplaySpec() {
	return fieldReactionCanvasDisplaySpec;
}


/**
 * Gets the reactionStep property (cbit.vcell.model.ReactionStep) value.
 * @return The reactionStep property value.
 * @see #setReactionStep
 */
public cbit.vcell.model.ReactionStep getReactionStep() {
	return fieldReactionStep;
}


/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}


/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("ReactionCanvas");
		setLayout(null);
		setSize(160, 120);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}


/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		ReactionCanvas aReactionCanvas;
		aReactionCanvas = new ReactionCanvas();
		frame.setContentPane(aReactionCanvas);
		frame.setSize(aReactionCanvas.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.show();
		java.awt.Insets insets = frame.getInsets();
		frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight() + insets.top + insets.bottom);
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JPanel");
		exception.printStackTrace(System.out);
	}
}


/**
 * This method was created by a SmartGuide.
 * @param g java.awt.Graphics
 */
public void paintComponent(java.awt.Graphics g) {
	super.paintComponent(g);
	if (offScreenImage == null || offScreenImageSize == null || !offScreenImageSize.equals(getSize())){
		refreshGraphics();
	}
	if (offScreenImage != null){
		g.drawImage(offScreenImage,-1,-1,this);
	}	
	return;
}


/**
 * This method was created in VisualAge.
 * @param event java.beans.PropertyChangeEvent
 */
public void propertyChange(java.beans.PropertyChangeEvent event) {
	if (event.getSource() == getReactionStep()){
		if (event.getPropertyName().equals("kinetics")){
			if (event.getOldValue()!=null){
				((Kinetics)event.getOldValue()).removePropertyChangeListener(this);
			}
			if (event.getNewValue()!=null){
				((Kinetics)event.getNewValue()).addPropertyChangeListener(this);
			}
		}
		updateDisplaySpecFromReactionStep();
	} else if (event.getSource() instanceof Kinetics){
		updateDisplaySpecFromReactionStep();
	}
}


/**
 * This method was created by a SmartGuide.
 */
private void resizeToExpression() {
	try {
		if (getReactionCanvasDisplaySpec() != null) {
			setSize(expressionBounds.width, expressionBounds.height);
			setPreferredSize(new Dimension(expressionBounds.width, expressionBounds.height));
			revalidate();
		} else {
			java.awt.Dimension parentDim = getParent().getSize();
			setSize(parentDim.width - 10, parentDim.height - 10);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}


/**
 * Sets the reactionCanvasDisplaySpec property (cbit.vcell.model.gui.ReactionCanvasDisplaySpec) value.
 * @param reactionCanvasDisplaySpec The new value for the property.
 * @see #getReactionCanvasDisplaySpec
 */
public void setReactionCanvasDisplaySpec(ReactionCanvasDisplaySpec reactionCanvasDisplaySpec) {
	ReactionCanvasDisplaySpec oldValue = fieldReactionCanvasDisplaySpec;
	fieldReactionCanvasDisplaySpec = reactionCanvasDisplaySpec;
	firePropertyChange("reactionCanvasDisplaySpec", oldValue, reactionCanvasDisplaySpec);
	refreshGraphics();
	repaint();
}


/**
 * Sets the reactionStep property (cbit.vcell.model.ReactionStep) value.
 * @param reactionStep The new value for the property.
 * @see #getReactionStep
 */
public void setReactionStep(cbit.vcell.model.ReactionStep reactionStep) {
	ReactionStep oldReactionStep = fieldReactionStep;
	fieldReactionStep = reactionStep;
	if (oldReactionStep != null){
		oldReactionStep.removePropertyChangeListener(this);
		if (oldReactionStep.getKinetics()!=null){
			oldReactionStep.getKinetics().removePropertyChangeListener(this);
		}
	}	
	if (fieldReactionStep != null){
		fieldReactionStep.addPropertyChangeListener(this);
		if (fieldReactionStep.getKinetics()!=null){
			fieldReactionStep.getKinetics().addPropertyChangeListener(this);
		}
	}
	firePropertyChange("reactionStep", oldReactionStep, reactionStep);
	updateDisplaySpecFromReactionStep();
}


/**
 * This method was created by a SmartGuide.
 * @param observable java.util.Observable
 * @param object java.lang.Object
 */
private void updateDisplaySpecFromReactionStep() {
	try {
		if (getReactionStep()!=null){
			setReactionCanvasDisplaySpec(ReactionCanvasDisplaySpec.fromReactionStep(getReactionStep()));
		}	
	}catch (Exception e){
		System.out.println("exception in ReactionCanvas.update(Observable, Object)");
		e.printStackTrace(System.out);
	}		
}


/**
 * This method was created by a SmartGuide.
 * @param g java.awt.Graphics
 */
private synchronized void refreshGraphics () {

	try {
		if (offScreenImage==null || offScreenImageSize==null || !offScreenImageSize.equals(getSize())){
			offScreenImageSize = new java.awt.Dimension(getSize().width,getSize().height);
			offScreenImage = createImage(offScreenImageSize.width,offScreenImageSize.height);
		}
		if (offScreenImage==null){
			return;
		}
		Dimension newBounds = ReactionRenderer.paint(getReactionCanvasDisplaySpec(),offScreenImage,offScreenImageSize.width,offScreenImageSize.height,12,getBackground());
		if (! newBounds.equals(expressionBounds)) {
			expressionBounds = newBounds;
			resizeToExpression();
		}
	}catch (Exception e){
		offScreenImage = null;
		System.out.println("exception in ReactionCanvas.refreshGraphics()");
		e.printStackTrace(System.out);
	}			
	return;
}

/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88G570171B4GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E135D8EBECDC4595F6A0C181C5A215A89894890B828D158B01B82AC19624D5ABD9442A0288089F7E410F140794900F702A02B43B36E3D2BB41F934B52D1B38100792ADCBA0910F45009CBB42A68E1844EC92E1895F3BF7F677FA6FAB775EFDF9ED52F3E66E6CDEBB3BC9090225CFF3674C4C19B367714D2C490EDDB5ED01608EC282ED447B7BA8C0C84DF6C2FE9D6A3FC748969B518E627B7BAFE0ABF9F822
	955A9A98EBE77D6559CEE837G6AC106ED2B996D68A22B325D1E3C59138B8402047C114DF9A25B63490620BF812886541A25791B584E8BA29FFB7D71DFACA56B9F34031BD10FCF27C02745579E745ADE725740E3966E75E62E53DB1304FE8DG8F5FC214B37B216D9BCDA5BE0F492A9B4E44282605F5D321DAB811D6A31193D40A392AE98CC8C6C6F2E06E58D954F7AA7786DBCFC88F749FE7321B26F10FEA78DE86E884F07BBF5B576759F73A70BB32B67127CDEFD630FF374D6D9E31CB76779E10DB5EE23E5EAE
	23CF9742D8ADE0A5E0AD205509F66C867C9B463613FFC987206D175D81D357CD235F320EE80AFD9C4F835F11880CF83293B4E65A8AA1BC0EFF4F054A58F3A170F56F7C64F55CA7CB8F0918561E3813B4AF3FBE58DE46A74DCB8B41BE077BE24CA979046FBDDEF1EF57191DF33377BEC57CFB1327BFE8EFAB33F74F239F34AF7474AC716D5D64E02C374CE02C9B60BB60658117F7B3F917DB12737C2BE139017DDE981B6F0A5CB83DC558B252FA8B69FA7F0632FE287E432260D3AE370124CB36703C68D1300EC2A0
	9F82CCGF200EAC09D20B1ADF2A3A63D77FF664628E43BC7522A26D01B7035BD72BF2025B95716068D47150C980DCA36AA499A052260F37648E83BBCEB5D811633B15863CBEAD0DB42D2826D12CEDDEABBFC4EA1F9AB4E31A99B9DD3F58A9B689691FABF15011A08A6B96EF716A23934B4030F8F48684B0C94850D20G3E39DF06A97A7CA9B8BF49C08CB2428F77B3FD4728EDD0AD9299B14DD45A9A431310891CC79CD65B3B613B8F40F59D0D236CD668B794753CA4EF736999D499CFC8F6DE54460E99644A5599
	916BE7CF0BD88FEE7EAD89BD724B3231DE8CF937AE63F1E9F6EE4E6FE4BA51AE5E3FFD7A741C752BB365F37FD24077F8603CBDDAAE137EAD45D8ADCFE02CC2F05EA1C0B2ABFCFCB0564BF3760864D011255F5810E0D4A9F91C4F5D2BA02F528CB55CC1EAD1C3018F474B11F715CD257C0D1B455CBA446496647BGA418D314FF22E0CECA2EEB2BF25A25A7296B2AC622B45E4F76F25232D5AA9531571002FCE2E6A15F0B730FAB5BD89D9886E5C69717F8635F30DC259A55416881B3ED38DC7E9D45F827E85E2B15
	B3F4A34BF5AD0DF5476F30E592DE54162D6A608AACDD32BA0BF9FCCDCE5C4108AA1061DDFCAB3475D9111359F7C4CE6E1E3A032C7C418A1663426A5B2E8DF667F8DE05F2251C64B6FD95AFEF7BA82B99CF6EF0DB559C2E1DFB29729CD817C4BDA320FF82104B099CE80A3F652BB3AF78832626F1D796FDFCEF228BE3C24F26A95093967431225FD6A6364D990BFABE64630D897421666382EE6F0B7B51076CFDB0A9E524DC5849C29EC4A2DF0DCAE08DFF8B2CE7FC0173976744DB6049821E25917B00D7F3425F8F
	ED96EF016BD2CBC973536F37D7FA4BF465391F8F67CB7E8EBD573F117B6A43C95C53053179F9612701241F577A9D470C290CD23DB33E107447034BDEC97E8D5A38EABB6EB094CCCE786375A45A0B1C6B09BDAEF86D3CBB37BADF621B5A7C795C436F456F597D37B24F6F45C0D12EB0F913CF4E6F4BE78A623D76980C75CD899EF6D2980F9A68B7G56G5681BA8B510ED0C178E101FA08DC641E9BB6607AB1A46D642C8296774DE48B2FB92F7F45E41B28B9E8799D5115C7DF8E411E63GAE0BCE212C8A7A558541
	4F52B4A97AEE52777DE2E1AE27DECE70BDBA4F636D4B896E1B69D4E54E3DB2D51EF32FCE15675CEBD3DB399CF478DFA0E27C065405B97966D4B9CEEEE1E37B729BD9BE674D348B35B40D717ABCAF78318162352640F3770DE9FE6F737A3FBB0B3C9007878BECF55222B132012D5527445AA4742781CBG8D00E6G4F61F8DE6470608C4C1991BA93EF6FA162B7072907D5D98F27B3FAD8125DB0D5D4B79279BA2DE9A31E290473492F182EBA505184D8BBA378A437D770C96D6D371055FFA94FA70F0F4D84B74CF8
	BC72E309CF8E43EF8B4EE14F335C45F563B0FE5D0FA85BD558615301FEFC9E90867071EFE70DE3EC764F9A3FF256B85A5B9F700F2F18B55E66DB4F4F36EA0FB85B06D54B0836E2FE455FFD4D5E1948B96FECFF3EB13030FAF4A32F83ADFFE93FED26FE72BA4EE97F1C42D8FDFCEEAE5775B279603939DCF7C7B1CFC6E12C6E1C60BAC9479C6B2E0AF69C860CG9440FCC0CD15603A435AC1F2912E9BB32D91A8D04DD7D27CFCAFCFDD5AEF375F370B38545D779BD2FF55E03023C2DC6A2AF8BC56F8EDFD530D81EC
	7979569BF83E56F9510EDE40A0E09CE081964E93671B56C27CFCC721B27ADDD70AA5596DA58E97D5E936A4468265E77B4F94A946F12F6F7B2E7C4F4F3B0DBF6B9E5CAF13FAE55EBD2B17675D2CDE1EF7E7748B736A823D72DBF7116EFF6B7238C6AC2C6BA59043FAC0B360B9C06B95457FD198EF7318160967D1A5C187CC768B53AD3EC50C4AE7EDB5AF7E3BF43309EF9C59F4DDD3C77DC23E5D44F839264504DC56ED220FECB511F479D4CF3E53C4FE54E81C09396C08D5592EE16B62F61D3058FB8C740D65ADCF
	67A98B6FA9B39EF7E8717C63D6D7D1B66C0C1A0E0A0F3A7F81D0CB8788D00DBE7A3F87GG4893GGD0CB818294G94G88G88G570171B4D00DBE7A3F87GG4893GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG7987GGGG
**end of data**/
}
}