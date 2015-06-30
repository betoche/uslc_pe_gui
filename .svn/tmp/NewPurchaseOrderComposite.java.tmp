package com.uslc.po.gui.master;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import com.uslc.po.gui.logic.NewPurchaseOrderLogic;
import com.uslc.po.gui.master.catalog.FormCenterMaster;
import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.gui.master.interfaces.MasterCompositeInterface;
import com.uslc.po.jpa.entity.Upc;

public class NewPurchaseOrderComposite extends FormCenterMaster implements MasterCompositeInterface {
	private Label titleLbl = null;
	private Text ordenNumberTxt = null;
	private Label shipFromLbl = null;
	private Text shipFromTxt = null;
	private Label shipToLbl = null;
	private Text shipToTxt = null;
	private Label cartonLbl = null;
	private Text cartonTxt = null;
	private Label itemsLbl = null;
	private Combo itemsCbx = null;
	private Label deptLbl = null;
	private Text deptTxt = null;
	private Label timestampLbl = null;
	private Text timestampTxt = null;
	private Label poDetailLbl = null;
	private Table poDetailTbl = null;
	private Button add = null;
	private Button del = null;
	private Label totalItemsLbl = null;
	
	private Font fontLabels = null;
	private GridData gdLabels = null;
	private GridData gdTextAreas = null;
	private GridData gdTexts = null;
	private GridData gdSmallButtons = null;
	private GridData gdHorizontal = null;
	
	private Logger log = null;
	
	private LiveDataAccessLifeCicle ldalc = null;
	
	public NewPurchaseOrderComposite( MasterCenterComposite composite ) {
		super( composite, SWT.NONE );
		initComposite();
	}
	
	private void initComposite() {
		FormData data = new FormData( 550, 550);
		setLayoutData(data);
		
		setLayout( new GridLayout( 6, false ) );
		
		getTitleLbl();
		getOrderNumberTxt();
		getShipFromLbl();
		getShipFromTxt();
		getShipToLbl();
		getShipToTxt();
		getCartonLbl();
		getCartonTxt();
		getItemsLbl();
		getItemsCbx();
		getDeptLbl();
		getDeptTxt();
		getTimestampLbl();
		getTimestampTxt();
		getPoDetailLbl();
		getPoDetailTbl();
		getAdd();
		getDel();
		getTotalItemsLbl();
		
		getLiveDataAccessLifeCicle();
	}
	
	public Label getTitleLbl() {
		if( titleLbl == null ){
			titleLbl = new Label(this, SWT.NONE);
			titleLbl.setText("purchase order: ");
		}
		return titleLbl;
	}
	public Text getOrderNumberTxt(){
		if( ordenNumberTxt == null ){
			ordenNumberTxt = new Text(this, SWT.NONE);
			ordenNumberTxt.setLayoutData(getGdTexts());
			ordenNumberTxt.setFont(getMaster().getSystemVariables().getSmallFont());
			
			Label horizontaLabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			horizontaLabel.setLayoutData( getGdHorizontal() );
		}
		return ordenNumberTxt;
	}
	public Label getShipFromLbl() {
		if( shipFromLbl == null ){
			shipFromLbl = new Label(this, SWT.NONE);
			shipFromLbl.setText( "ship from:" );
			shipFromLbl.setLayoutData( getGdLabels() );
			shipFromLbl.setAlignment( SWT.RIGHT );
			shipFromLbl.setFont( getFontLabels() );
		}
		return shipFromLbl;
	}
	public Text getShipFromTxt() {
		if( shipFromTxt == null ){
			shipFromTxt = new Text(this, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
			shipFromTxt.setLayoutData(getGdTextAreas());
			shipFromTxt.setFont(getMaster().getSystemVariables().getSmallFont());
		}
		return shipFromTxt;
	}
	public Label getShipToLbl() {
		if( shipToLbl == null ){
			shipToLbl = new Label(this, SWT.NONE);
			shipToLbl.setText( "ship to:" );
			shipToLbl.setLayoutData( getGdLabels() );
			shipToLbl.setAlignment( SWT.RIGHT );
			shipToLbl.setFont( getFontLabels() );
		}
		return shipToLbl;
	}
	public Text getShipToTxt() {
		if( shipToTxt == null ) {
			shipToTxt = new Text(this, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
			shipToTxt.setLayoutData(getGdTextAreas());
			shipToTxt.setFont(getMaster().getSystemVariables().getSmallFont());
		}
		return shipToTxt;
	}
	public Label getCartonLbl() {
		if( cartonLbl == null ) {
			cartonLbl = new Label(this, SWT.NONE);
			cartonLbl.setText( "carton:" );
			cartonLbl.setLayoutData( getGdLabels() );
			cartonLbl.setAlignment( SWT.RIGHT );
			cartonLbl.setFont( getFontLabels() );
		}
		return cartonLbl;
	}
	public Text getCartonTxt() {
		if( cartonTxt == null ){
			cartonTxt = new Text(this, SWT.SINGLE | SWT.READ_ONLY);
			cartonTxt.setLayoutData(getGdTexts());
			cartonTxt.setFont(getMaster().getSystemVariables().getSmallFont());
		}
		return cartonTxt;
	}
	public Label getItemsLbl() {
		if( itemsLbl == null ){
			itemsLbl = new Label(this, SWT.NONE);
			itemsLbl.setText( "items:" );
			itemsLbl.setLayoutData( getGdLabels() );
			itemsLbl.setAlignment( SWT.RIGHT );
			itemsLbl.setFont( getFontLabels() );
		}
		return itemsLbl;
	}
	public Combo getItemsCbx() {
		if( itemsCbx == null ){
			itemsCbx = new Combo( this, SWT.READ_ONLY );
			itemsCbx.setLayoutData(getGdTexts());
			itemsCbx.setFont(getMaster().getSystemVariables().getSmallFont());
		}
		return itemsCbx;
	}
	public Label getDeptLbl() {
		if( deptLbl == null ){
			deptLbl = new Label(this, SWT.NONE);
			deptLbl.setText( "dept #:" );
			deptLbl.setLayoutData( getGdLabels() );
			deptLbl.setAlignment( SWT.RIGHT );
			deptLbl.setFont( getFontLabels() );
		}
		return deptLbl;
	}
	public Text getDeptTxt() {
		if( deptTxt == null ){
			deptTxt = new Text(this, SWT.SINGLE);
			deptTxt.setLayoutData(getGdTexts());
			deptTxt.setFont(getMaster().getSystemVariables().getSmallFont());
		}
		return deptTxt;
	}
	public Label getTimestampLbl() {
		if( timestampLbl == null ){
			timestampLbl = new Label(this, SWT.NONE);
			timestampLbl.setText( "timestamp:" );
			timestampLbl.setLayoutData( getGdLabels() );
			timestampLbl.setAlignment( SWT.RIGHT );
			timestampLbl.setFont( getFontLabels() );
		}
		return timestampLbl;
	}

	public Text getTimestampTxt() {
		if( timestampTxt == null ){
			timestampTxt = new Text(this, SWT.SINGLE | SWT.READ_ONLY);
			timestampTxt.setFont(getMaster().getSystemVariables().getSmallFont());
			
			GridData gd = new GridData();
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace=true;
			gd.horizontalSpan=2;
			gd.widthHint = 150;
			gd.heightHint = 23;
			gd.horizontalAlignment = SWT.CENTER;
			timestampTxt.setLayoutData(gd);
			
			Label horizontaLabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			horizontaLabel.setLayoutData( getGdHorizontal() );
		}
		return timestampTxt;
	}
	public Label getPoDetailLbl() {
		if( poDetailLbl == null ){
			poDetailLbl = new Label(this, SWT.NONE);
			poDetailLbl.setText( "PO Details:" );
			poDetailLbl.setLayoutData( getGdHorizontal() );
		}
		return poDetailLbl;
	}
	public Table getPoDetailTbl() {
		if( poDetailTbl == null ){
			poDetailTbl = new Table(this, SWT.MULTI );
			poDetailTbl.setHeaderVisible(true);
			Font f = poDetailTbl.getFont();
			FontData[] fds = f.getFontData();
			for (int i = 0; i < fds.length; i++) {
				fds[i].setHeight(8);
			}
			
			poDetailTbl.setFont( new Font(getDisplay(), fds));
			
			TableColumn upc = new TableColumn(poDetailTbl, SWT.NONE);
			upc.setAlignment(SWT.CENTER);
			upc.setText( "upc" );
			upc.setWidth( 106 );
			TableColumn colorName = new TableColumn(poDetailTbl, SWT.NONE);
			colorName.setText( "color name" );
			colorName.setWidth( 89 );
			TableColumn colorItem = new TableColumn(poDetailTbl, SWT.NONE);
			colorItem.setText( "" );
			colorItem.setWidth( 106 );
			TableColumn colorNumber = new TableColumn(poDetailTbl, SWT.NONE);
			colorNumber.setText( "color #" );
			colorNumber.setWidth( 65 );
			TableColumn sku = new TableColumn(poDetailTbl, SWT.NONE);
			sku.setText( "sku" );
			sku.setWidth( 40 );
			TableColumn size = new TableColumn(poDetailTbl, SWT.NONE);
			size.setText( "size" );
			size.setWidth( 63 );
			TableColumn quantity = new TableColumn(poDetailTbl, SWT.NONE);
			quantity.setText( "qty" );
			quantity.setWidth( 39 );
			
			GridData gd = new GridData( GridData.FILL_HORIZONTAL );
			gd.heightHint = 250;
			gd.horizontalSpan=5;
			gd.verticalSpan=10;
			gd.verticalAlignment = SWT.TOP;
			poDetailTbl.setLayoutData(gd);
		}
		return poDetailTbl;
	}
	public Button getAdd() {
		if( add == null ){
			add = new Button(this, SWT.PUSH);
			add.setText("+");
			add.setLayoutData(getGdSmallButtons());
		}
		return add;
	}
	public Button getDel() {
		if( del == null ){
			del = new Button(this, SWT.PUSH);
			del.setText("-");
			del.setLayoutData(getGdSmallButtons());
		}
		return del;
	}
	public Label getTotalItemsLbl(){
		if( totalItemsLbl == null ){
			totalItemsLbl = new Label(this, SWT.BORDER);
			totalItemsLbl.setText( "total items: 0" );
			totalItemsLbl.setAlignment( SWT.RIGHT );
			GridData gd = new GridData( GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER );
			gd.grabExcessHorizontalSpace = true;
			gd.grabExcessVerticalSpace = true;
			gd.horizontalSpan=5;
			gd.widthHint = 140;
			totalItemsLbl.setLayoutData(gd);
			
			Label horizontaLabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			horizontaLabel.setLayoutData( getGdHorizontal() );
		}
		return totalItemsLbl;
	}
	
	public GridData getGdLabels() {
		if( gdLabels == null ){
			gdLabels = new GridData(70, 23);
			gdLabels.horizontalAlignment = SWT.RIGHT;
			gdLabels.verticalAlignment = SWT.TOP;
			
		}
		return gdLabels;
	}
	public GridData getGdTexts() {
		if( gdTexts == null ){
			gdTexts = new GridData();
			gdTexts.horizontalSpan=2;
			gdTexts.widthHint = 150;
			gdTexts.heightHint = 23;
		}
		return gdTexts;
	}
	public GridData getGdTextAreas() {
		if( gdTextAreas == null ){
			gdTextAreas = new GridData();
			gdTextAreas.horizontalSpan=2;
			gdTextAreas.widthHint = 150;
			gdTextAreas.heightHint = 46;
		}
		return gdTextAreas;
	}
	public GridData getGdSmallButtons(){
		if( gdSmallButtons == null ){
			gdSmallButtons = new GridData(23,23);
			gdSmallButtons.horizontalAlignment = SWT.CENTER;
			gdSmallButtons.verticalAlignment = SWT.CENTER;
		}
		return gdSmallButtons;
	}
	public GridData getGdHorizontal(){
		if( gdHorizontal == null ){
			gdHorizontal = new GridData( GridData.FILL_HORIZONTAL );
			gdHorizontal.grabExcessHorizontalSpace = true;
			gdHorizontal.horizontalSpan = 6;
		}
		return gdHorizontal;
	}
	
	public void hide(){
		getLiveDataAccessLifeCicle().clean();
		this.setParent( getParent().getMaster().getHiddenShell() );
		this.setVisible(false);
		getParent().getMaster().getMasterTop().getCancel().setEnabled(false);
	}
	private Font getFontLabels(){
		if( fontLabels == null ){
			fontLabels = new Font(getDisplay(), "Segoe UI", 7, SWT.NORMAL);
		}
		return fontLabels;
	}
	
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger(NewPurchaseOrderComposite.class);
			PropertyConfigurator.configure("log4j.properties");
		}
		return log;
	}

	public class PODetailData {
		private Upc upc = null;
		private int qty = 0;
		private int itemsPerCarton = 0;
		private boolean preticketed = false;
		public PODetailData(Upc newUpc, int qty, int itemsPerCarton, boolean preticketed) {
			this.upc = newUpc;
			this.qty = qty;
			this.itemsPerCarton = itemsPerCarton;
			this.preticketed = preticketed;
		}
		
		public Upc getUpc() {
			return upc;
		}
		public int getQty() {
			return qty;
		}
		public int getItemsPerCarton() {
			return itemsPerCarton;
		}
		public boolean isPreticketed() {
			return preticketed;
		}
	}

	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		if( ldalc == null ) {
			ldalc = new NewPurchaseOrderLogic( this );
		}
		return ldalc;
	}
	
	@Override
	public InfoForm getInfoForm() {
		String title = "new purchase order:";
		String desc = "manually or automatic (upload button) creation of a purchase order.\nManually:";
		String[] features = { "add a po number", 
								"select the item number",
								"press the + button",
								"select size in right panel",
								"select color in right panel",
								"select upc in right panel",
								"add a qty",
								"select ticketed y/n",
								"press \"add detail\" button",
								"press \"save\" button above"
							};
		return new InfoForm( title, desc, features );
	}
}
