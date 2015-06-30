package com.uslc.po.gui.client;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.jasperassistant.designer.viewer.ViewerComposite;
import com.uslc.gui.SystemCommons;
import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.gui.master.interfaces.SystemCommonMethods;
import com.uslc.po.jpa.entity.User;
import com.uslc.po.jpa.logic.UserRepo;

public class Client extends SystemCommons implements SystemCommonMethods {
	private com.uslc.po.gui.logic.ClientLogic client = null;
	private Table poDetailTbl;
	private Table packingDetailTbl;
	private Combo purchaseOrderCbx;
	private Logger log = null;
	private Combo sizeCbx;
	private Combo colorCbx;
	private Combo itemCbx;
	private Table scannedItemsTbl;
	private Text scannedBarTxt;
	private ViewerComposite polybagViewer = null;
	private ViewerComposite ticketViewer = null;
	private Composite printComposite = null;
	private Button btnPrintTicket = null;
	private Button btnPrintSmall = null;
	
	private int poDetailQty = 0;
	private Group grpTicketsComposite;
	private Combo printersCbx;
	private Group grpScanningInfo;
	private Spinner numberOfCopiesSpn;
	private ProgressBar progressBar;
	private Button savePrintingSettingsBtn;
	private Button autoPrintingChk;
	private Button printDialogChk;
	private Label infoUpcLbl;
	private Label barcodeLbl;
	private Label infoSizeLbl;
	private Label infoColorLbl;
	private Label infoItemLbl;
	private Label infoSkuLabel;
	private Button infoPreticketedCbx;
	private Label poDetailLbl;
	private Label packingDetailLbl;
	private Composite colorsComposite;
	private Group grpPoDetails;
	private Group grpPackingDetails;
	private Composite summaryScrolledComposite;
	private ScrolledComposite scrolledComposite;
	private Button btnCompleted;
	private Button btnAddCarton = null;
	private Composite topScanningComposite = null;
	private Composite rightScanComposite = null;
	private Label lblNowScanning = null;
	private TabFolder tabFolder = null;
	private TabItem tbtmScan = null;
	private Composite scanComposite = null;
	private Composite leftScanComposite = null;
	private Composite scanningArea = null;
	private Composite composite_1 = null;
	private Button delBtn = null;
	private Button cleanCartonBtn = null;
	private Button delCartonBtn = null;
	private Composite scanningTicketViewer = null;
	private Composite bottomScanComposite = null;
	private Group grpPrintingOptions = null;
	
	/**
	 * @wbp.parser.constructor
	 */
	public Client( Display display, User user) {
		super( display.getActiveShell(), display, user );
	}
	public Client( Shell shell, Display display, User user ){
		super(shell, display, user);
		getLog().info( Client.class + " constructor has been called." );
	}
	
	public ViewerComposite getPolybagViewer() {
		if( polybagViewer == null ){
			polybagViewer = new ViewerComposite( getGrpTicketsComposite(), SWT.NONE);
			GridData gd_ticketViewer = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
			gd_ticketViewer.heightHint = 150;
			gd_ticketViewer.widthHint = 200;
			polybagViewer.setLayoutData(gd_ticketViewer);
		}
		return polybagViewer;
	}
	public Group getGrpTicketsComposite() {
		if( grpTicketsComposite == null ) {
			grpTicketsComposite = new Group(getScanningTicketViewer(), SWT.NONE);
			grpTicketsComposite.setText("tickets");
			grpTicketsComposite.setLayout(new GridLayout(2, false));
			grpTicketsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		}
		return grpTicketsComposite;
	}
	public ViewerComposite getTicketViewer() {
		if( ticketViewer == null ){
			Composite printComposite = new Composite(getGrpTicketsComposite(), SWT.NONE);
			printComposite.setLayout(new GridLayout(1, false));
			GridData gd_printComposite = new GridData(SWT.RIGHT, SWT.FILL, true, true, 1, 1);
			gd_printComposite.widthHint = 90;
			printComposite.setLayoutData(gd_printComposite);
			
			ticketViewer = new ViewerComposite( getGrpTicketsComposite(), SWT.NONE);
			GridData gd_polybagViewer = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
			gd_polybagViewer.heightHint = 200;
			gd_polybagViewer.widthHint = 300;
			ticketViewer.setLayoutData(gd_polybagViewer);
		}
		return ticketViewer;
	}
	public Button getBtnPrintSmall() {
		if( btnPrintSmall == null ) {
			btnPrintSmall = new Button(getPrintComposite(), SWT.NONE);
			btnPrintSmall.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
			btnPrintSmall.setText("print small");
		}
		return btnPrintSmall;
	}
	public Composite getPrintComposite() {
		if( printComposite == null ) {
			printComposite = new Composite(getGrpTicketsComposite(), SWT.NONE);
			printComposite.setLayout(new GridLayout(1, false));
			GridData gd_printComposite = new GridData(SWT.RIGHT, SWT.FILL, true, true, 1, 1);
			gd_printComposite.widthHint = 90;
			printComposite.setLayoutData(gd_printComposite);
		}
		return printComposite;
	}
	public Button getBtnPrintTicket() {
		if( btnPrintTicket == null ) {
			btnPrintTicket = new Button(getPrintComposite(), SWT.NONE);
			btnPrintTicket.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
			btnPrintTicket.setText("print ticket");
		}
		return btnPrintTicket;
	}
	public TabFolder getTabFolder() {
		if( tabFolder == null ) {
			tabFolder = new TabFolder(this, SWT.NONE);
			tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		}
		return tabFolder;
	}
	public TabItem getTbtmScan() {
		if(  tbtmScan == null) {
			tbtmScan = new TabItem(getTabFolder(), SWT.NONE);
			tbtmScan.setText("scan");
		}
		return tbtmScan;
	}
	public Composite getScanComposite() {
		if( scanComposite == null ) {
			scanComposite = new Composite(getTabFolder(), SWT.NONE);
			getTbtmScan().setControl(getScanComposite());
			
			GridLayout gl_bottomScanComposite = new GridLayout(2, false);
			gl_bottomScanComposite.marginBottom = 5;
			gl_bottomScanComposite.marginTop = 5;
			gl_bottomScanComposite.marginRight = 5;
			gl_bottomScanComposite.marginLeft = 5;
			scanComposite.setLayout(gl_bottomScanComposite);
		}
		return scanComposite;
	}
	public Composite getLeftScanComposite() {
		if( leftScanComposite == null ) {
			leftScanComposite = new Composite(getScanComposite(), SWT.NONE);
			GridLayout gl_leftScanComposite = new GridLayout(4, false);
			gl_leftScanComposite.marginTop = 2;
			gl_leftScanComposite.marginRight = 2;
			gl_leftScanComposite.marginLeft = 2;
			gl_leftScanComposite.marginBottom = 2;
			leftScanComposite.setLayout(gl_leftScanComposite);
			GridData gd_leftScanComposite = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2);
			gd_leftScanComposite.widthHint = 300;
			leftScanComposite.setLayoutData(gd_leftScanComposite);
		}
		return leftScanComposite;
	}
	public Combo getPurchaseOrderCbx() {
		if( purchaseOrderCbx == null ) {
			Label lblPo = new Label(getLeftScanComposite(), SWT.NONE);
			lblPo.setAlignment(SWT.RIGHT);
			lblPo.setFont(getSystemVariables().getSmallFont());
			lblPo.setText("po:");
			
			purchaseOrderCbx = new Combo(getLeftScanComposite(), SWT.READ_ONLY);
			purchaseOrderCbx.setFont(getSystemVariables().getSmallFont());
			GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
			gd.heightHint=50;
			purchaseOrderCbx.setLayoutData(gd);
		}
		return purchaseOrderCbx;
	}
	public Combo getItemCbx() {
		if( itemCbx == null ) {
			Label itemLbl = new Label(getLeftScanComposite(), SWT.NONE);
			itemLbl.setAlignment(SWT.RIGHT);
			itemLbl.setFont(getSystemVariables().getSmallFont());
			itemLbl.setText("item:");
			
			itemCbx = new Combo(getLeftScanComposite(), SWT.READ_ONLY);
			itemCbx.setFont(getSystemVariables().getSmallFont());
			GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
			gd.heightHint=50;
			itemCbx.setLayoutData(gd);
		}
		return itemCbx;
	}
	public Combo getColorCbx() {
		if( colorCbx == null ) {
			Label colorLbl = new Label(getLeftScanComposite(), SWT.NONE);
			colorLbl.setAlignment(SWT.RIGHT);
			colorLbl.setFont(getSystemVariables().getSmallFont());
			colorLbl.setText("color:");
			
			colorCbx = new Combo(getLeftScanComposite(), SWT.READ_ONLY);
			colorCbx.setFont(getSystemVariables().getSmallFont());
			GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
			gd.heightHint=50;
			colorCbx.setLayoutData(gd);
		}
		return colorCbx;
	}
	public Combo getSizeCbx() {
		if( sizeCbx == null ) {
			Label sizeLbl = new Label(getLeftScanComposite(), SWT.NONE);
			sizeLbl.setAlignment(SWT.RIGHT);
			sizeLbl.setFont(getSystemVariables().getSmallFont());
			sizeLbl.setText("size:");
			
			sizeCbx = new Combo(getLeftScanComposite(), SWT.READ_ONLY);
			sizeCbx.setFont(getSystemVariables().getSmallFont());
			GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
			gd.heightHint=50;
			sizeCbx.setLayoutData(gd);
			
			Label horizontalLine2 = new Label(getLeftScanComposite(), SWT.SEPARATOR | SWT.HORIZONTAL);
			horizontalLine2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		}
		return sizeCbx;
	}
	public Label getPoDetailLbl() {
		if( poDetailLbl == null ) {
			poDetailLbl = new Label(getLeftScanComposite(), SWT.NONE);
			poDetailLbl.setFont(getSystemVariables().getSmallFont());
			poDetailLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
			poDetailLbl.setText("po detail:");
			
			//new Label(getLeftScanComposite(), SWT.NONE);
		}
		return poDetailLbl;
	}
	public Label getPackingDetailLbl() {
		if( packingDetailLbl == null ) {
			packingDetailLbl = new Label(getLeftScanComposite(), SWT.NONE);
			packingDetailLbl.setFont(getSystemVariables().getSmallFont());
			packingDetailLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
			packingDetailLbl.setText("packing detail:");
		}
		return packingDetailLbl;
	}
	public Button getBtnAddCarton(){
		if( btnAddCarton == null ) {
			btnAddCarton = new Button(getLeftScanComposite(), SWT.NONE);
			btnAddCarton.setFont(getSystemVariables().getSmallFont());
			btnAddCarton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));
			btnAddCarton.setText("add carton");
		}
		return btnAddCarton;
	}
	public Table getPackingDetailTbl() {
		if( packingDetailTbl == null ) {
			packingDetailTbl = new Table(getLeftScanComposite(), SWT.BORDER | SWT.FULL_SELECTION);
			packingDetailTbl.setFont(getSystemVariables().getSmallFont());
			
			GridData gd_packingDetailTbl = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1);
			gd_packingDetailTbl.heightHint = 120;
			packingDetailTbl.setLayoutData(gd_packingDetailTbl);
			packingDetailTbl.setHeaderVisible(true);
			packingDetailTbl.setLinesVisible(true);
			
			TableColumn tblclmnSize = new TableColumn(packingDetailTbl, SWT.NONE);
			tblclmnSize.setWidth(44);
			tblclmnSize.setText("size");
			
			TableColumn tblclmnColor = new TableColumn(packingDetailTbl, SWT.NONE);
			tblclmnColor.setWidth(95);
			tblclmnColor.setText("color");
			
			TableColumn tblclmnSku = new TableColumn(packingDetailTbl, SWT.NONE);
			tblclmnSku.setWidth(36);
			tblclmnSku.setText("sku");
			
			TableColumn tblclmnQty_1 = new TableColumn(packingDetailTbl, SWT.NONE);
			tblclmnQty_1.setWidth(30);
			tblclmnQty_1.setText("qty");
			
			TableColumn tblclmnScanned = new TableColumn(packingDetailTbl, SWT.NONE);
			tblclmnScanned.setWidth(60);
			tblclmnScanned.setText("scanned");
		}
		return packingDetailTbl;
	}
	public ScrolledComposite getScrolledComposite() {
		if( scrolledComposite == null ) {
			Group grpSumary = new Group(getLeftScanComposite(), SWT.NONE);
			grpSumary.setFont(getSystemVariables().getNormalFont());
			GridLayout gl_grpSumary = new GridLayout(1, false);
			gl_grpSumary.verticalSpacing = 0;
			grpSumary.setLayout(gl_grpSumary);
			grpSumary.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 4, 1));
			grpSumary.setText("summary");
			
			scrolledComposite = new ScrolledComposite(grpSumary, SWT.H_SCROLL | SWT.V_SCROLL);
			scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);
		}
		return scrolledComposite;
	}
	public Composite getSummaryScrolledComposite() {
		if( summaryScrolledComposite == null ) {
			summaryScrolledComposite = new Composite(getScrolledComposite(), SWT.NONE);
			getScrolledComposite().setContent(summaryScrolledComposite);
			getScrolledComposite().setMinSize(summaryScrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			summaryScrolledComposite.setLayout(new GridLayout(3, false));
			
			new Label(summaryScrolledComposite, SWT.NONE);
		}
		return summaryScrolledComposite;
	}
	public Group getGrpPoDetails() {
		if( grpPoDetails == null ) {
			//grpPoDetails = new Group(getMaster().getMaster().getHiddenShell(), SWT.NONE);
			grpPoDetails = new Group(getSummaryScrolledComposite(), SWT.NONE);
			grpPoDetails.setFont(getSystemVariables().getXSmallFont());
			GridLayout gl_grpPoDetails = new GridLayout(2, true);
			gl_grpPoDetails.marginTop = 2;
			grpPoDetails.setLayout(gl_grpPoDetails);
			GridData gd_grpPoDetails = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2);
			gd_grpPoDetails.widthHint = 75;
			grpPoDetails.setLayoutData(gd_grpPoDetails);
			grpPoDetails.setText("PO Details");
		}
		return grpPoDetails;
	}
	public Group getGrpPackingDetails() {
		if( grpPackingDetails == null ) {
			grpPackingDetails = new Group(getSummaryScrolledComposite(), SWT.NONE);
			grpPackingDetails.setFont(getSystemVariables().getXSmallFont());
			GridLayout gl_grpPackingDetails = new GridLayout(2, true);
			gl_grpPackingDetails.marginTop = 5;
			grpPackingDetails.setLayout(gl_grpPackingDetails);
			GridData gd_grpPackingDetails = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 2);
			gd_grpPackingDetails.widthHint = 75;
			grpPackingDetails.setLayoutData(gd_grpPackingDetails);
			grpPackingDetails.setText("Pk Details");
		}
		return grpPackingDetails;
	}
	public Composite getColorsComposite() {
		if( colorsComposite == null ) {
			colorsComposite = new Composite(getSummaryScrolledComposite(), SWT.NONE);
			colorsComposite.setLayout(new GridLayout(1, false));
			colorsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		}
		return colorsComposite;
	}
	public Composite getRightScanComposite() {
		if( rightScanComposite == null ) {
			rightScanComposite = new Composite(getScanComposite(), SWT.BORDER);
			rightScanComposite.setLayout(new GridLayout(2, false));
			rightScanComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		}
		return rightScanComposite;
	}
	public Composite getTopScanningComposite() {
		if( topScanningComposite == null ) {
			topScanningComposite = new Composite(getRightScanComposite(), SWT.NONE);
			topScanningComposite.setLayout(new FormLayout());
			topScanningComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		}
		
		return topScanningComposite;
	}
	public Label getLblNowSanning() {
		if( lblNowScanning == null ) {
			lblNowScanning = new Label(getTopScanningComposite(), SWT.NONE);
			FormData fd_lblNowScanning = new FormData();
			fd_lblNowScanning.bottom = new FormAttachment(100);
			fd_lblNowScanning.left = new FormAttachment(0, 29);
			lblNowScanning.setLayoutData(fd_lblNowScanning);
			lblNowScanning.setText("now scanning:");
		}
		return lblNowScanning;
	}
	public Text getScannedBarTxt() {
		if( scannedBarTxt == null ) {
			scannedBarTxt = new Text(getTopScanningComposite(), SWT.BORDER);
			FormData fd_scannedBarTxt = new FormData();
			fd_scannedBarTxt.top = new FormAttachment(100, -23);
			fd_scannedBarTxt.bottom = new FormAttachment(100);
			fd_scannedBarTxt.right = new FormAttachment( getLblNowSanning(), 167, SWT.RIGHT);
			fd_scannedBarTxt.left = new FormAttachment( getLblNowSanning(), 5);
			scannedBarTxt.setLayoutData(fd_scannedBarTxt);
		}
		return scannedBarTxt;
	}
	public Table getScannedItemsTbl() {
		if( scannedItemsTbl == null ) {
			scannedItemsTbl = new Table(getScanningArea(), SWT.BORDER | SWT.FULL_SELECTION);
			scannedItemsTbl.setFont(getSystemVariables().getSmallFont());
			scannedItemsTbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			scannedItemsTbl.setHeaderVisible(true);
			scannedItemsTbl.setLinesVisible(true);
			
			TableColumn tblclmnItems = new TableColumn(scannedItemsTbl, SWT.NONE);
			tblclmnItems.setWidth(45);
			tblclmnItems.setText("items");
			
			TableColumn tblclmnUpc_1 = new TableColumn(scannedItemsTbl, SWT.NONE);
			tblclmnUpc_1.setWidth(117);
			tblclmnUpc_1.setText("upc");
			
			TableColumn tblclmnSize_1 = new TableColumn(scannedItemsTbl, SWT.NONE);
			tblclmnSize_1.setWidth(57);
			tblclmnSize_1.setText("size");
			
			TableColumn tblclmnColor_1 = new TableColumn(scannedItemsTbl, SWT.NONE);
			tblclmnColor_1.setWidth(84);
			tblclmnColor_1.setText("color");
		}
		return scannedItemsTbl;
	}
	public Composite getScanningArea() {
		if( scanningArea == null ) {
			scanningArea = new Composite(getRightScanComposite(), SWT.BORDER);
			scanningArea.setLayout(new GridLayout(1, false));
			GridData gd_scanningArea = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
			gd_scanningArea.widthHint = 318;
			scanningArea.setLayoutData(gd_scanningArea);
		}
		return scanningArea;
	}
	public Composite getComposite_1() {
		if( composite_1 == null ) {
			composite_1 = new Composite( getScanningArea(), SWT.NONE );
			composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			composite_1.setLayout(new GridLayout(4, false));
		}
		return composite_1;
	}
	public Button getBtnCompleted() {
		if( btnCompleted == null ) {
			btnCompleted = new Button(getComposite_1(), SWT.TOGGLE);
			btnCompleted.setFont(getSystemVariables().getSmallFont());
			btnCompleted.setText("completed");
		}
		return btnCompleted;
	}
	public Button getDelBtn() {
		if( delBtn==null ) {
			delBtn = new Button(getComposite_1(), SWT.NONE);
			delBtn.setFont(getSystemVariables().getSmallFont());
			delBtn.setText("del scan");
		}
		return delBtn;
	}
	public Button getCleanCartonBtn() {
		if( cleanCartonBtn == null ) {
			cleanCartonBtn = new Button(getComposite_1(), SWT.NONE);
			cleanCartonBtn.setFont(getSystemVariables().getSmallFont());
			cleanCartonBtn.setText("clean carton");
		}
		return cleanCartonBtn;
	}
	public Button getDelCartonBtn() {
		if( delCartonBtn == null ) {
			delCartonBtn = new Button(getComposite_1(), SWT.NONE);
			delCartonBtn.setFont(getSystemVariables().getSmallFont());
			delCartonBtn.setText("del carton");
		}
		return delCartonBtn;
	}
	public Composite getScanningTicketViewer() {
		if( scanningTicketViewer == null ) {
			scanningTicketViewer = new Composite(getRightScanComposite(), SWT.BORDER);
			scanningTicketViewer.setLayout(new GridLayout(1, false));
			scanningTicketViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		}
		return scanningTicketViewer;
	}
	@Override
	protected void checkSubclass() {
	}
	public Table getPoDetailTbl() {
		if( poDetailTbl == null ) {
			poDetailTbl = new Table(getLeftScanComposite(), SWT.BORDER | SWT.FULL_SELECTION);
			poDetailTbl.setFont(getSystemVariables().getSmallFont());
			GridData gd_poDetailTbl = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1);
			gd_poDetailTbl.heightHint = 100;
			poDetailTbl.setLayoutData(gd_poDetailTbl);
			poDetailTbl.setHeaderVisible(true);
			poDetailTbl.setLinesVisible(true);
			
			TableColumn tableColumn = new TableColumn(getPoDetailTbl(), SWT.NONE);
			tableColumn.setWidth(30);
			
			TableColumn tblclmnUpc = new TableColumn(getPoDetailTbl(), SWT.NONE);
			tblclmnUpc.setWidth(105);
			tblclmnUpc.setText("upc");
			
			TableColumn tblclmnQty = new TableColumn(getPoDetailTbl(), SWT.NONE);
			tblclmnQty.setWidth(30);
			tblclmnQty.setText("qty");
			
			TableColumn tblclmnCarton = new TableColumn(getPoDetailTbl(), SWT.NONE);
			tblclmnCarton.setWidth(50);
			tblclmnCarton.setText("carton");
			
			TableColumn tblclmnReady = new TableColumn(getPoDetailTbl(), SWT.NONE);
			tblclmnReady.setWidth(45);
			tblclmnReady.setText("ready");
		}
		return poDetailTbl;
	}
	/*
	 * scannin control methods
	 */
	public int getPoDetailQty(){
		return poDetailQty;
	}
	public void setPoDetailQty( int poDetailQty ){
		this.poDetailQty = poDetailQty;
	}
	public Combo getPrintersCbx() {
		if( printersCbx == null ) {
			Label lblPrinters = new Label(getGrpPrintingOptions(), SWT.NONE);
			lblPrinters.setFont(getSystemVariables().getSmallFont());
			lblPrinters.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblPrinters.setText("printers:");
			
			printersCbx = new Combo(getGrpPrintingOptions(), SWT.READ_ONLY);
			printersCbx.setFont(getSystemVariables().getXSmallFont());
			GridData gd_printersCbx = new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1);
			gd_printersCbx.heightHint = 14;
			printersCbx.setLayoutData(gd_printersCbx);
		}
		return printersCbx;
	}
	public Group getGrpScanningInfo() {
		if( grpScanningInfo == null ) {
			grpScanningInfo = new Group(getBottomScanComposite(), SWT.NONE);
			grpScanningInfo.setFont(getSystemVariables().getSmallFont());
			grpScanningInfo.setLayout(new GridLayout(4, false));
			grpScanningInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			grpScanningInfo.setText("scanning info");
		}
		return grpScanningInfo;
	}
	public Spinner getNumberOfCopiesSpn() {
		if( numberOfCopiesSpn == null ) {
			Label lblCopies = new Label(getGrpPrintingOptions(), SWT.NONE);
			lblCopies.setFont(getSystemVariables().getSmallFont());
			lblCopies.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblCopies.setText("copies:");
			
			numberOfCopiesSpn = new Spinner(getGrpPrintingOptions(), SWT.BORDER);
			numberOfCopiesSpn.setFont(getSystemVariables().getSmallFont());
			GridData gd_numberOfCopiesSpn = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1);
			gd_numberOfCopiesSpn.heightHint = 14;
			numberOfCopiesSpn.setLayoutData(gd_numberOfCopiesSpn);
			Label autoPrintingLbl = new Label(getGrpPrintingOptions(), SWT.NONE);
			autoPrintingLbl.setFont(getSystemVariables().getSmallFont());
			autoPrintingLbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			autoPrintingLbl.setText( "auto:" );
		}
		return numberOfCopiesSpn;
	}
	public ProgressBar getProgressBar() {
		if( progressBar == null ) {
			progressBar = new ProgressBar(getGrpScanningInfo(), SWT.NONE);
			GridData gd_progressBar = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
			gd_progressBar.heightHint = 5;
			progressBar.setLayoutData(gd_progressBar);
		}
		return progressBar;
	}
	public Button getSavePrintingSettingsBtn() {
		if( savePrintingSettingsBtn == null ) {
			savePrintingSettingsBtn = new Button(getGrpPrintingOptions(), SWT.NONE);
			savePrintingSettingsBtn.setFont(getSystemVariables().getSmallFont());
			savePrintingSettingsBtn.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, false, 1, 1));
			savePrintingSettingsBtn.setText("save");
		}
		return savePrintingSettingsBtn;
	}
	public Button getAutoPrintingChk() {
		if( autoPrintingChk == null ) {
			autoPrintingChk = new Button(getGrpPrintingOptions(), SWT.CHECK);
			autoPrintingChk.setFont(getSystemVariables().getSmallFont());
			autoPrintingChk.setToolTipText("automatic printing after completing the carton");
		}
		return autoPrintingChk;
	}
	public Button getPrintDialogChk() {
		if( printDialogChk == null ) {
			Label lblPrintdialog = new Label(getGrpPrintingOptions(), SWT.NONE);
			lblPrintdialog.setFont(getSystemVariables().getSmallFont());
			lblPrintdialog.setText("print-dialog:");
			
			printDialogChk = new Button(getGrpPrintingOptions(), SWT.CHECK);
			printDialogChk.setFont(getSystemVariables().getSmallFont());
			new Label(grpPrintingOptions, SWT.NONE);
		}
		return printDialogChk;
	}
	public Label getInfoUpcLbl() {
		if( infoUpcLbl == null ) {
			infoUpcLbl = new Label(getGrpScanningInfo(), SWT.NONE);
			infoUpcLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			infoUpcLbl.setFont(getSystemVariables().getSmallFont());
			infoUpcLbl.setText("upc:");
			
			Label verticalLineLbl = new Label(getGrpScanningInfo(), SWT.SEPARATOR | SWT.VERTICAL);
			verticalLineLbl.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 5));
		}
		return infoUpcLbl;
	}
	public Label getBarcodeLbl() {
		if( barcodeLbl == null ) {
			barcodeLbl = new Label( getGrpScanningInfo(), SWT.IMAGE_PNG );
			barcodeLbl.setAlignment(SWT.CENTER);
			GridData gd_barcodeLbl = new GridData(SWT.CENTER, SWT.FILL, false, true, 1, 4);
			gd_barcodeLbl.widthHint = 150;
			barcodeLbl.setLayoutData(gd_barcodeLbl);
		}
		return barcodeLbl;
	}
	public Label getInfoSizeLbl() {
		if( infoSizeLbl == null ) {
			Label lblSize = new Label(getGrpScanningInfo(), SWT.NONE);
			lblSize.setAlignment(SWT.RIGHT);
			lblSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			lblSize.setFont(getSystemVariables().getSmallFont());
			lblSize.setText("size:");
			
			infoSizeLbl = new Label(getGrpScanningInfo(), SWT.NONE);
			infoSizeLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			infoSizeLbl.setFont(getSystemVariables().getSmallFont());
			infoSizeLbl.setText("");
		}
		return infoSizeLbl;
	}
	public Label getInfoColorLbl() {
		if( infoColorLbl == null ) {
			Label lblColor = new Label(getGrpScanningInfo(), SWT.NONE);
			lblColor.setAlignment(SWT.RIGHT);
			lblColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			lblColor.setFont(getSystemVariables().getSmallFont());
			lblColor.setText("color:");
			
			infoColorLbl = new Label(getGrpScanningInfo(), SWT.NONE);
			infoColorLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			infoColorLbl.setFont(getSystemVariables().getSmallFont());
			infoColorLbl.setText("");
		}
		return infoColorLbl;
	}
	public Label getInfoItemLbl() {
		if( infoItemLbl == null ) {
			Label lblItem = new Label(getGrpScanningInfo(), SWT.NONE);
			lblItem.setAlignment(SWT.RIGHT);
			lblItem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			lblItem.setFont(getSystemVariables().getSmallFont());
			lblItem.setText("item:");
			
			infoItemLbl = new Label(getGrpScanningInfo(), SWT.NONE);
			infoItemLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			infoItemLbl.setFont(getSystemVariables().getSmallFont());
			infoItemLbl.setText("");
		}
		return infoItemLbl;
	}
	public Label getInfoSkuLabel() {
		if( infoSkuLabel == null ) {
			Label lblSku = new Label(getGrpScanningInfo(), SWT.NONE);
			lblSku.setAlignment(SWT.RIGHT);
			lblSku.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			lblSku.setFont(getSystemVariables().getSmallFont());
			lblSku.setText("sku:");
			
			infoSkuLabel = new Label(getGrpScanningInfo(), SWT.NONE);
			infoSkuLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			infoSkuLabel.setFont(getSystemVariables().getSmallFont());
			infoSkuLabel.setText("sku");
		}
		return infoSkuLabel;
	}
	public Button getInfoPreticketedCbx() {
		if( infoPreticketedCbx == null ) {
			infoPreticketedCbx = new Button(getGrpScanningInfo(), SWT.CHECK);
			infoPreticketedCbx.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			infoPreticketedCbx.setText("preticketed");
		}
		return infoPreticketedCbx;
	}
	public Composite getBottomScanComposite() {
		if( bottomScanComposite == null ) {
			bottomScanComposite = new Composite(getScanComposite(), SWT.NONE);
			bottomScanComposite.setLayout(new GridLayout(2, false));
			GridData gd_bottomScanComposite = new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1);
			gd_bottomScanComposite.heightHint = 150;
			bottomScanComposite.setLayoutData( gd_bottomScanComposite );
		}
		return bottomScanComposite;
	}
	public Group getGrpPrintingOptions() {
		if( grpPrintingOptions == null ) {
			grpPrintingOptions = new Group(bottomScanComposite, SWT.NONE);
			//grpPrintingOptions.setFont(getSystemVariables().getSmallFont());
			grpPrintingOptions.setFont(getSystemVariables().getSmallFont());
			GridLayout gl_grpPrintingOptions = new GridLayout(2, false);
			gl_grpPrintingOptions.verticalSpacing = 2;
			grpPrintingOptions.setLayout(gl_grpPrintingOptions);
			
			GridData gd_grpPrintingOptions = new GridData(SWT.RIGHT, SWT.FILL, false, true, 1, 1);
			gd_grpPrintingOptions.widthHint = 250;
			grpPrintingOptions.setLayoutData(gd_grpPrintingOptions);
			grpPrintingOptions.setText("printing settings");
		}
		return grpPrintingOptions;
	}
	
	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		if( client == null ) {
			client = new com.uslc.po.gui.logic.ClientLogic(this, getUser());
		}
		return client;
	}
	
	@Override
	public Logger getLog() {
		if( log == null ) {
			log = Logger.getLogger( Client.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	@Override
	public void setSize() {
		super.setSize(1037, 748);
	}
	@Override
	public void initGui() {
		try {
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginTop = 5;
			gridLayout.marginRight = 5;
			gridLayout.marginLeft = 5;
			gridLayout.marginWidth = 0;
			setLayout(gridLayout);
			
			getTabFolder();
			getTbtmScan();
			getScanComposite();
			getLeftScanComposite();
			getPurchaseOrderCbx();
			getItemCbx();
			getColorCbx();
			getSizeCbx();
			getPoDetailLbl();
			getPoDetailTbl();
			getBtnAddCarton();
			getPackingDetailLbl();
			getPackingDetailTbl();
			getGrpPoDetails();
			getGrpPackingDetails();
			getColorsComposite();
			getRightScanComposite();
			getTopScanningComposite();
			getLblNowSanning();
			
			getScanningArea();
			getComposite_1();
			getScanningTicketViewer();
			getScannedItemsTbl();
			getDelBtn();
			getCleanCartonBtn();
			getDelCartonBtn();
			getBtnCompleted();
			
			getBottomScanComposite();
			getGrpScanningInfo();
			getProgressBar();
			getInfoUpcLbl();
			getInfoSizeLbl();
			getBarcodeLbl();
			getInfoColorLbl();
			getInfoItemLbl();
			getInfoSkuLabel();
			getInfoPreticketedCbx();
			
			getGrpPrintingOptions();
			getPrintersCbx();
			
			getNumberOfCopiesSpn();
			getAutoPrintingChk();
			getPrintDialogChk();
			getSavePrintingSettingsBtn();
			
			Composite composite = new Composite(this, SWT.BORDER);
			GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_composite.heightHint = 30;
			composite.setLayoutData(gd_composite);
			
			getPolybagViewer();
			getTicketViewer();
			
			getLiveDataAccessLifeCicle();
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/*
	 * main method
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			User user = UserRepo.findUser( "admin", "guacalito");
			
			System.out.println( "display["+display+"] - user["+user+"]" );
			
			Client shell = new Client(display, user);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
