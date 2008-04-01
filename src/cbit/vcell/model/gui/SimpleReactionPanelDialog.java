package cbit.vcell.model.gui;

/**
 * Insert the type's description here.
 * Creation date: (7/25/2002 3:03:15 PM)
 * @author: Anuradha Lakshminarayana
 */
public class SimpleReactionPanelDialog extends javax.swing.JDialog {
	private javax.swing.JPanel ivjJDialogContentPane = null;
	private SimpleReactionPanel ivjSimpleReactionPanel = null;
	private cbit.vcell.model.SimpleReaction fieldSimpleReaction = null;
	private boolean ivjConnPtoP1Aligning = false;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private cbit.vcell.model.SimpleReaction ivjsimpleReaction1 = null;

class IvjEventHandler implements java.beans.PropertyChangeListener {
		public void propertyChange(java.beans.PropertyChangeEvent evt) {
			if (evt.getSource() == SimpleReactionPanelDialog.this && (evt.getPropertyName().equals("simpleReaction"))) 
				connPtoP1SetTarget();
		};
	};
/**
 * SimpleReactionPanelDialog constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public SimpleReactionPanelDialog(java.awt.Dialog owner, boolean modal) {
	super(owner, modal);
	initialize();
}
/**
 * SimpleReactionPanelDialog constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public SimpleReactionPanelDialog(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
	initialize();
}
/**
 * Insert the method's description here.
 * Creation date: (8/9/2006 9:57:55 PM)
 */
public void cleanupOnClose() {
	getSimpleReactionPanel().cleanupOnClose();
}
/**
 * connEtoM1:  (simpleReaction1.this --> SimpleReactionPanel.simpleReaction)
 * @param value cbit.vcell.model.SimpleReaction
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM1(cbit.vcell.model.SimpleReaction value) {
	try {
		// user code begin {1}
		// user code end
		if ((getsimpleReaction1() != null)) {
			getSimpleReactionPanel().setSimpleReaction(getsimpleReaction1());
		}
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connPtoP1SetSource:  (SimpleReactionPanelDialog.simpleReaction <--> simpleReaction1.this)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP1SetSource() {
	/* Set the source from the target */
	try {
		if (ivjConnPtoP1Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP1Aligning = true;
			if ((getsimpleReaction1() != null)) {
				this.setSimpleReaction(getsimpleReaction1());
			}
			// user code begin {2}
			// user code end
			ivjConnPtoP1Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP1Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connPtoP1SetTarget:  (SimpleReactionPanelDialog.simpleReaction <--> simpleReaction1.this)
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connPtoP1SetTarget() {
	/* Set the target from the source */
	try {
		if (ivjConnPtoP1Aligning == false) {
			// user code begin {1}
			// user code end
			ivjConnPtoP1Aligning = true;
			setsimpleReaction1(this.getSimpleReaction());
			// user code begin {2}
			// user code end
			ivjConnPtoP1Aligning = false;
		}
	} catch (java.lang.Throwable ivjExc) {
		ivjConnPtoP1Aligning = false;
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * Return the JDialogContentPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJDialogContentPane() {
	if (ivjJDialogContentPane == null) {
		try {
			ivjJDialogContentPane = new javax.swing.JPanel();
			ivjJDialogContentPane.setName("JDialogContentPane");
			ivjJDialogContentPane.setLayout(new java.awt.BorderLayout());
			getJDialogContentPane().add(getSimpleReactionPanel(), "Center");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJDialogContentPane;
}
/**
 * Gets the simpleReaction property (cbit.vcell.model.SimpleReaction) value.
 * @return The simpleReaction property value.
 * @see #setSimpleReaction
 */
public cbit.vcell.model.SimpleReaction getSimpleReaction() {
	return fieldSimpleReaction;
}
/**
 * Return the simpleReaction1 property value.
 * @return cbit.vcell.model.SimpleReaction
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private cbit.vcell.model.SimpleReaction getsimpleReaction1() {
	// user code begin {1}
	// user code end
	return ivjsimpleReaction1;
}
/**
 * Return the SimpleReactionPanel property value.
 * @return cbit.vcell.model.gui.SimpleReactionPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private SimpleReactionPanel getSimpleReactionPanel() {
	if (ivjSimpleReactionPanel == null) {
		try {
			ivjSimpleReactionPanel = new cbit.vcell.model.gui.SimpleReactionPanel();
			ivjSimpleReactionPanel.setName("SimpleReactionPanel");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSimpleReactionPanel;
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
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
	this.addPropertyChangeListener(ivjEventHandler);
	connPtoP1SetTarget();
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("SimpleReactionPanelDialog");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setSize(576, 631);
		setContentPane(getJDialogContentPane());
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}
/**
 * Sets the simpleReaction property (cbit.vcell.model.SimpleReaction) value.
 * @param simpleReaction The new value for the property.
 * @see #getSimpleReaction
 */
public void setSimpleReaction(cbit.vcell.model.SimpleReaction simpleReaction) {
	cbit.vcell.model.SimpleReaction oldValue = fieldSimpleReaction;
	fieldSimpleReaction = simpleReaction;
	firePropertyChange("simpleReaction", oldValue, simpleReaction);
}
/**
 * Set the simpleReaction1 to a new value.
 * @param newValue cbit.vcell.model.SimpleReaction
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void setsimpleReaction1(cbit.vcell.model.SimpleReaction newValue) {
	if (ivjsimpleReaction1 != newValue) {
		try {
			cbit.vcell.model.SimpleReaction oldValue = getsimpleReaction1();
			ivjsimpleReaction1 = newValue;
			connPtoP1SetSource();
			connEtoM1(ivjsimpleReaction1);
			firePropertyChange("simpleReaction", oldValue, newValue);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	// user code begin {3}
	// user code end
}
/**
 * 
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private static void getBuilderData() {
/*V1.1
**start of data**
	D0CB838494G88G88GE1FBB0B6GGGGGGGGGGGG8CGGGE2F5E9ECE4E5F2A0E4E1F4E1359A8BF0D45515315121CED09C3102C3BB99C40BEDC45A22258E1DC1473136E6469841268354502688B52D1F628CD51C5E7D190D8A86891DF40C9A85B5C00C7901E18D102FA430A19992B0BA1901176CCB32E6F75F7A76EDF613EC121EF36F3BEFDF12773241414C1C396F1E7BBB7FFB4E5D100277D29C96EB81A19687D17FBE3390F27090A1FB52D7BC4CF1B59FE764915D5F7BGFB099436C35B82E339
	34DF99F9835ABDG178107EDAA347964CC24D045A729F88E96ABA149A5B8C70C94293867E00E93F0ADGBDA5EC3E82ADA360DAD70DBA2F672695E4479FE46BEE5CC737A707756A5A1DEADBCCDE32249C4233EE20F4621AF2688F82A073B63230349A5A5A0E210113FD3DDE25EC38CF74794A7C12C774158D043DAED727579F740967C521CF71CA018EA1A07A1A3C02CF9AA8BA0C7BBE99F152FD884906BDD7D05CFB844F3A8C5FD1000507B9FE018571B970EDBF1211D7F40473F76263DA157E6E31DF137515FE7B
	1BA67CD59FE1FC759C097337E9E83375FFD497778C234C974358DA00F4009DG852599F955GEF4358BEF2DF78B034753DCA236477CB017AE0304167115B10DF78F639E0C4451D977BA459C388537322C82981BD3381DB376C0A57B1192C2862BACFBD3F12EC7A550F2C3986B25934E24C6AAEE532701446E5424E5EE8FAF6D269D41B1CFA7617C4FFF6741E8DB60741590B16EE30ADD677C9551D1DD50A3A3E195AC09AFC63BADC4F70BF2178031F44714CBEBF24F86C57405852CF38EDF46F6134386D4BC8FE45
	2D06F2D87CE0321514B19A8A4B2653E231625ECDE56C4CDC0D165D945F2343B3DAEE26F60AFD8546B28118FD2D09223F56FE1A11578D9084188F308260A1004CCF390D7DA77C699CED2CC310150630576791E542562E997EAF34E2D4110556C0C89182FD62C5C1768A3DBEB1C454B98F8CA3ED3DD356BDCCED3F8F4EB8AD86C4D9E04EAA8BFED19165901B735870DE1CA30BF4344B6B9761GFF1070FD7FB83C8FDA1F90D2AE84BD02A246E730712706D1A74342C55891B600EFA6173F0D203CF6827F796599F945
	65DC8EDFC7F03F33228CB14365F2CB52D0B858051C10E0B94A79859ABB2A615BDD4EE53CE594F12B211F266DF3D364AD5DBECDDE9A0984F9047B589202EB32E03E33025B4DBF3B395DB46D3E1B14EE384550EE1CBF5EE0BBD8416C2536E23A7FAC277BA2ED1E8A9E6F0F106EB97AB15202B4DDF94CE81A7399AC06DFD9FCE1F2EE0F214E1200469400F595DC56E911E2E63B8DC2C864565AAE0321091E3864991D39D6FE2758D4DA98FE9F45E7421E24B2A32F24124BEE0925FB5A7CAB21FBEE327BA5BDC3CF4F55
	BC03496841A85ECF623098D01A4420987040C7C89D7BFD946DCF755FFEC9DB73984587415F9B4141C29AFEC394FDD2D094595B9BD644CED1D13C01017878E6FAD6A85C9B0C078AFED6F694F914A2606F5A7C6DD17415BEA99090293234A56A78B354D7C51F6887229B25F0C0E17867C7B02F9992C754D87162482B84FD5D974638436CEC1570BA72A0FB7DE082983A4872CA74633CE821CEC69D00431CA78AEDF2A5774748816E0F87C76FA26BAE852DC6F74A62BBEE3466D4B21FA1D5F1FFE4B4ED9DB526BD0746
	8C959FE234E60D211D1BA6AF2A0D6CA34B4629FC274C2BD0FDE47EB81ED78CF4D483382B380F8C523DF9BCD21D23D172791848E1AC398A65320D9428FCF9209F2BE2F2095B5105D8BE1EADFE9B96A14CE3E0BC2B493FA7765634310B2AAE5838A7063A7069E2EAE1B51E79051567146B5573F22AD87CF242783B00DFCB67655B78BCCFF5FCBC8610D24DE82CBE0A739A9DFC1E73A8736762237179E5045B4679EFD1A6B40F7CC6989622E5218858314BF5C613B74B97C3D1273AE65ED11E97C628DE57C0BF9DE05B
	D1EEB30F6F66F96122219524EB55FA1BD95ECB0EB11A8F9E0B5BCCC9F244467418B101E7FAE0CCB946F578D4CCFFB75507C2D21F17DE0B2AFCEF98575B94435DBC7E9A347DDEB924340253C739AE160CA33DF8EF2AE8B50FF8136691E8E773615CF5GBB0FA15EEDC1DFC33DB0BE7B2AB81F8BC76FA017EF394D7A3A891F351F29796FE7F1BEF7669D353173A4ABBFEF910C2781B09B6F356C19E1635C7EDF32A2AD4A20B744E8F9213ECEBDABED65B232A6B759EA140B8F7EF43155DE43E8A82D095332F64BE6EB
	BAG2367380D539303B92B81D83C38FEC29FAFF2GDFF29C7C8B6072F1CE73E107914D0C463B5F555368942EB524B1F97B00B5FA1C5116F2BCCEE34D931B2D6EA7B80DBE8727F19B4CA9F6713A2860A436B47E91FEEE6CABFE6E3C345524F05F8243F3913ADD6C3CF92E7839F610CBA1G3B2722D14C4D53DD4679C136811E652C4FD0DF5969E2B92BCD433FC8714E4F63F8465F78A8672F9046E21F73BC2B5D02BA48027EAE00025A0C3CD2GB7002F167B487B93D5A4C19E5B9A0074B2A078BA27DCD0BC1F1C40
	F361CED17B9BA774B2E5F258EFC1B94C2F1DFB1E44569D206BD22EE09D5361212F753534FD6F992B4DC407696A5ED935F19DB259919B4675629399F935GDDGBE006493DCEE17A7CB93490D67742A4098CFE1EB22DAB17544D4FE593A9FA4DC17BEED9D6363229DE37BB698331F04FA9C20862087A08A3074A467E765E4C3A2FE6AF98253A5E91F0D531390CE6FA95BF76333AD613A55A7272EE33E7C32956F29ABFB63A0EAEED3BA260FD5E9A7D92D729C3476D3A0B3FAFFBDB8415FC00ACE71BB4DB709FE5785
	FD9FC052A96EF70F527BF4A97457G248364G6457017F81F454F1199372F9A219B7E3828A7226C44F94B613196C78EE321E17F0DD304E48264E66A2FF73E1ECB940DA00CC00DDG057560AF751C3F73A421CDE9656B6CB645EA499BE9DE51857BC78198EEAB45A5C3FFF9BD2FC11E9F457B6E912D7FB26D637D237A5268DCEB2F39821EB5D536CB2F7A997A9A1049375BE2DE23DC329857A8224538C699326CE5F858C37FE24147C31659EB18B10BD18D738E257BC91A3348104BFB7DA2BD16792C98FD1F594779
	F00019881E9C1A9EBFB53B7B21937538CD332F3D56CA837BEA76F895C9661BF409025C5E5F2F99D7E7D06C63B4BBED6672FB53E6AC3F7DB6E3793DED1BDDFE87ECE672E3B2FADA59CF8CEA3AF3609D0467F12956BCF5CE07A2F5D4E8FBDF361B73B1E0B76643EF47BCBBA40565BEF1BA2D0A3DD09B0BBBE32BA27A119CB6E71C2ED784F9C01C41EB92DD4F464C56AF30E37E5B6B75F8E53522E2BC8E5BB80F4D0A5406BCB2DB7957A15C53207620111C45E84F982FBB6C8DBCC6EF2075DDAD743BGFC8DBC867CEE
	E236581754E0161BE551183F380165E0968D3F1D62176B70ACB713A7797B49AA98ABE864363B41E12E33CD8EE31D7D45E12E336D0E44BAFB41E12E33FFBB926B4C7A2A198DB35D5D65F81D5BE73FAC7941C2559AB8571C571EDCE3DEFBF34DF97566A666D54EB567F5A4B7B12F57649A5B671DD174D30F58AF04FD90822410589ED41FE2D1470D287387347AB75A48EB0DDFC4F03FFE386DC0E4B4884DEF42392FE86FAABBE02E93F0A5GADG03CD3CCE3ADC0B6F84EA158D84ABB44684C44E4F8267EE3DDE5418
	416C5CBBB65D4E531B62361D5DE4E6672D933486E91ADE03F4D23C33F9FA8D32F1DC2BC1E0AC564C6F67BFCF707BB92B197B5E56C92251772C6EBBBAF9356F4C19B94FCD5F6B99CCDF19346E53052932AF3D810F94815561EB3F92AAEBEA6D6CE86FEC3E54FE6ED2239BBED85D12925157ADB52FDDE378FE06704697EA3D22363B765FE6E1B1704008D3DD538DE38B3FC05CBDA38553E8FC96AE52B2E8597816A96368630708FEFC5F28FE9CE97D0DCDBF5EBCE55C21DB4F78FA74C37E76316B51D544F7EF1269EF
	1B191C9F35DD183D44123DF15446ECE99E0D6F552741BFCEF37B3AFD1C5B176FB4372F76894C9396EA7D2FE89F5F3DD8FF4B785530815C5F5C4EDE3AAAE7183D7BE71E266F819345EAFF1581AD8F29B816BBB5CAA8276296484FDB789D5496B8826DC5611B191993A0BBE489A21BB2423074E5D2656D3A116F76FB6783395C8ED65F7FB332663AA653B7301E96C6FBCCEDCB7E3A449267656EA0464CD498FB84A087405E8A3587C0F9AB67EDD1C02D4F11FC487E043EC17A4A27A517DEB192C7E3C0E63EFB6B983E
	097B0491A92C00DCDBD14EAB357A30897ABDGC9G0BDB555F61691C7B478BE7611F6D7D7407D8739995224CF75F196477C65D999E7BAFCCA2BD0DA23E41F0B9C6C4AE47221F5FCE0A5E2B336EB511E3748C23F17919787B8636CCC6AD92FEEB7786D331BA56AE21CFFB83DE85EE9D6FA83D60BAE1E2E0006920C64671CC58F787C061D928ED816A4EF279BFA1FD4064EFA07BE64F0048E3A1BF6F3E20F90E705BE0625FCB9F8E623BED2F24A8129F77677834A07D0DD78AD2A443BD9EC4B94A5E01C105CD65EFD9
	C1143FCF6C27E8F62FBDB94673379E60ED51D9FE1FEFAD43B5DABEDAA6C494172BC1425F895C4CF818CE5F0D71F81CF6166724D6FAD75A5D99F9C5G55EE7EC69C7D086BB5754EE524A55578ADBC2BEB5C6AF6B3FD865DF13DBEB734594A7679E6C8EF9FB8E696EFD35575696E2976417C7C03C93D1F6737E5649DEC1B7DAD0E594687CAB134B5B0378BA08A3090E0B940DA00F400EDED5CCE863EADA166B10889145A0AACF879EFC1F532392D1C16935BCA07CC5F7DC147DDA3C1B55F3BA0231FC97D7DA1D13349
	2F65FC8D579A6A10C2DE7482A61BCDB3722AC440567D7DBB2E7BD36CCA5731FCED18595A3969754AA445671F1BDE2FB847393D9741583CF37CCD6B7AB04637EC6813F670F700F2008EGDFBB576533F2B9C9705E53A5855D20B81F2EC4E67BE7537FED1887FBAD85D88710891083E06F607B9F8FD54DE2AB67A509E64518A0032D684DC6FDBFBACE63EBA16C07798B431D2438F240E1CE444E79C4B1372FEAA531FD1DD00CE3D123B2B39635A84631681C220FC58C57296EABEB8F838C7F15027E4D6E2CB6A1A08C
	50DA0759CDEC1CE85F576A3E77DC157CE4F61B7D78FBBDC37D3DAEEC2E2B5B424635E5CA58788D674E706CEFDCAB43663F535F9B1E79BB7DBF064DE95BB1077F81FAE558580EEC1119F6640C985B511E085E0E585DA5FC04BE2E5E6DDFFABD4AA04397C5F74771ADA26E857D7F83D0CB87884344183DD48EGGCCA9GGD0CB818294G94G88G88GE1FBB0B64344183DD48EGGCCA9GG8CGGGGGGGGGGGGGGGGGE2F5E9ECE4E5F2A0E4E1F4E1D0CB8586GGGG81G81GBAGGG
	0E8EGGGG
**end of data**/
}
}
