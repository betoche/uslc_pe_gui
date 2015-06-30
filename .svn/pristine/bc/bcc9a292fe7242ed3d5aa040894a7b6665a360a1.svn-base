package com.uslc.po.gui.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.print.PrintService;
import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import com.ibm.icu.util.Calendar;
import com.jasperassistant.designer.viewer.IReportViewer;
import com.uslc.po.gui.client.Client;
import com.uslc.po.gui.client.ClientPackingDetailTable;
import com.uslc.po.gui.client.ClientPurchaseOrderDetailTable;
import com.uslc.po.gui.client.ClientPurchaseOrderSumary;
import com.uslc.po.gui.client.ClientScannedItemsTable;
import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.gui.util.CommonMasterClient;
import com.uslc.po.gui.util.ImageUtils;
import com.uslc.po.gui.util.PrintingUtils;
import com.uslc.po.jpa.comparator.PackageDetailComparator;
import com.uslc.po.jpa.comparator.PurchaseOrderDetailComparator;
import com.uslc.po.jpa.comparator.SizeComparator;
import com.uslc.po.jpa.entity.Carton;
import com.uslc.po.jpa.entity.Color;
import com.uslc.po.jpa.entity.Item;
import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.PurchaseOrderByUser;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;
import com.uslc.po.jpa.entity.ScanDetail;
import com.uslc.po.jpa.entity.Size;
import com.uslc.po.jpa.entity.Upc;
import com.uslc.po.jpa.entity.User;
import com.uslc.po.jpa.logic.UpcRepo;
import com.uslc.po.jpa.util.UslcJpa;

/*
 * 
 * Procedures:
 * + Before working a Carton
 * 		- Load available PO for the current client
 * + On Scanning
 * 		- Add scanned item to carton
 * 		- Show scanned item in table
 * 		- Update PO summary
 * 		- Update scanned in packing detail
 * 		- Update ready column in PO Detail
 * 		- Show scanning info
 * 		- Show tickets
 * + On cleaning a carton
 * 		- Set as deleted all scanned items
 * 		- Update PO summary
 * 		- Update scanned column in packing detail
 * 		- Update ready column in PO Detail
 * 		- Cleaning scanning info
 * + On deleting a carton
 * 		- Update PO summary
 * 		- Clean now scanning area: { scanned items, tickets view, scanning info }
 * 		- Updating packing detail list in table
 * 		- Updating PO Detail list in table
 * + On deleting a scanned item
 * 		- Cleaning scanning info
 * 		- Update PO summary
 * 		- Update scanned column in packing detail table
 * 		- Update ready column in packing detail table
 * 	+ On adding a carton to packing detail
 * 		- Update carton column in PO Detail table
 * 		- Update packing detail table list
 * 		- Clean now scanning area
 * 		- Update PO summary info
 * 		- Select new packing detail item in table
 * 
 * + On selecting a PO
 * 		- Clean tickets view
 * 		- Clean now scanning area
 * 		- Clean scanning info
 * 		- Fill filters with PO values
 * 		- Display PO Details according with selected filters
 * 		- Display Packing Detail of selected PO Detail
 * 		- Show PO Summary info
 * + On selecting a filter
 * 		- Clean tickets view
 * 		- Clean now scanning area
 * 		- Clean scanning info
 * 		- Display PO Details according with selected filters
 * 		- Display Packing Detail of selected PO Detail
 * + On selecting a PO detail
 * 		- Clean now scanning area
 * 		- Clean scanning info
 * 		- Clean tickets view
 * 		- Load packing detail table
 * + On selecting a packing detail
 * 		- Load scanned items
 * 		- Show tickets
 */

public class ClientLogic extends CommonMasterClient implements LiveDataAccessLifeCicle {
	private Client client = null;
	private UslcJpa jpa = null;
	private Logger log = null;
	private User user = null;
	private static SimpleDateFormat sdf = null;
	
	private int cartonQty = 0;

	public ClientLogic( Client client, User user ){
		super( client.getShell() );
		this.client = client;
		this.user = user;
		
		addListeners();
		displayValues();
	}
	public void addListeners() {
		getClient().getBtnPrintTicket().addListener( SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if( getSelectedPackingDetail()!=null ) {
					Carton carton = getSelectedPackingDetail().getPd().getCarton();
					getClient().getTicketPrinter().printCartonTicket( carton, getSelectedPrintService(), getClient().getPrintDialogChk().getSelection() );
				}else{ 
					getClient().getInformationBox( "please select a packing detail first" );
				}
			}
		});
		getClient().getPurchaseOrderCbx().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					selectingPurchaseOrder();
				} catch (JRException e) {
					getLog().error( "error on purchase order cbx selection", e );
					getClient().getErrorBox( e.getLocalizedMessage() );
				}
			}
		});
		getClient().getItemCbx().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					//Need to rewrite com.uslc.po.gui.util.Client class using the standard common classes
					selectingFilter();
				} catch (JRException e1) {
					getLog().error( "error on selecting item filter", e1 );
					getClient().getErrorBox( e1.getMessage() );
				}
				
			}
		});
		getClient().getColorCbx().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					selectingFilter();
				} catch (JRException e1) {
					getLog().error( "error on selecting color filter", e1 );
					getClient().getErrorBox( e1.getMessage() );
				}
			}
		});
		getClient().getSizeCbx().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					selectingFilter();
				} catch (JRException e1) {
					getLog().error(  "error on selecting size filter", e1 );
					getClient().getErrorBox( e1.getMessage() );
				}
			}
		});
		getClient().getPoDetailTbl().addListener( SWT.Selection , new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				try {
					selectingPurchaseOrderDetail();
				} catch (JRException e1) {
					getLog().error( "error on selecting a purchase order detail", e1 );
					getClient().getErrorBox( e1.getMessage() );
				}
			}
		});
		getClient().getBtnAddCarton().addListener( SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				addCarton();
			}
		});
		getClient().getPackingDetailTbl().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					selectingPackingDetail();
				} catch (JRException e) {
					e.printStackTrace();
				}
			}
		});
		getClient().getScannedBarTxt().addListener( SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					scanning();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		getClient().getScannedItemsTbl().addListener( SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				selectingScanDetail();
			}
		});
		getClient().getBtnCompleted().addListener( SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				try {
					completeCarton();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		getClient().getDelBtn().addListener( SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					deleteScan();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		getClient().getCleanCartonBtn().addListener( SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				try {
					cleanCarton();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		getClient().getDelCartonBtn().addListener( SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				try {
					deleteCarton();
				} catch (Exception e1) {
					getLog().error( "error deleting the carton", e1 );
					getErrorBox( e1.getMessage() );
				}
			}
		});
		getClient().getSavePrintingSettingsBtn().addListener( SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				String defaultPrinter = getClient().getPrintersCbx().getText();
				int numberOfCopies = Integer.parseInt( getClient().getNumberOfCopiesSpn().getText() );
				boolean automaticPrinting = getClient().getAutoPrintingChk().getSelection();
				
				getClient().getTicketPrinter().setDefaultPrinterName( defaultPrinter );
				getClient().getTicketPrinter().setDefaultNumberOfCopies( numberOfCopies );
				getClient().getTicketPrinter().setAutoPrint( automaticPrinting );
				
				if( getClient().getTicketPrinter().saveTicketChanges() ){
					getInformationBox( "printing settings saved:\n - default-printer: "+defaultPrinter+"\n - number-of-copies: " + numberOfCopies );
				}else{
					getErrorBox( "there is a problem saving printing settings, please contact your sysadmin" );
				}
			}
		});
	}
	
	/*
	 * Events
	 */
	public void selectingPurchaseOrder() throws JRException {
		cleanTicketsView();
		cleanScanningArea();
		cleanScaningInfo();
		
		fillFiltersFromPurchaseOrder();
		showPurchaseOrderDetail();
		showPackingDetail();
		showPurchaseOrderSummary();
	}
	public void selectingFilter() throws JRException{
		cleanTicketsView();
		cleanScanningArea();
		cleanScaningInfo();
		showPurchaseOrderDetail();
		showPackingDetail();
	}
	public void selectingPurchaseOrderDetail() throws JRException{
		cleanScanningArea();
		cleanScaningInfo();
		cleanTicketsView();
		
		showPackingDetail();
	}
	public void selectingPackingDetail() throws JRException{
		if( getSelectedPackingDetail()!=null ){
			loadScannedItems( null );
			showTickets(getSelectedPackingDetail());
		}
	}
	public void selectingScanDetail(){
		showScanningInfo();		
	}
	public void addCarton() {
		if( getSelectedPurchaseOrderDetail()!=null ){
			try{
				final Shell shell = new Shell( getClient().getShell(), SWT.BORDER | SWT.ON_TOP | SWT.APPLICATION_MODAL );
				shell.setData("qty", 0);
				shell.setSize(102, 100);
				shell.setText(getClient().getText());
				
				Rectangle bounds = Display.getDefault().getPrimaryMonitor().getBounds();
				Rectangle rect = shell.getBounds();
				
				int x = bounds.x + (bounds.width - rect.width)/2;
				int y = bounds.y + (bounds.height - rect.height )/2;
				
				shell.setLocation( x, y );

				shell.setLayout( new GridLayout(2,false) );
				
				Label qtyLbl = new Label(shell, SWT.NONE);
				qtyLbl.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 2, 1) );
				qtyLbl.setText("quantity:");
				final Text qtyTxt = new Text(shell, SWT.NONE);
				qtyTxt.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 2, 1) );
				qtyTxt.setText("");
				
				Button cancelBtn = new Button(shell, SWT.PUSH);
				cancelBtn.setLayoutData( new GridData( SWT.RIGHT, SWT.CENTER, false, false, 1, 1) );
				cancelBtn.setText("cancel");
				cancelBtn.addSelectionListener( new SelectionAdapter () {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						shell.close();
					}
				} );
				
				Button okBtn = new Button(shell, SWT.PUSH);
				okBtn.setLayoutData( new GridData( SWT.RIGHT, SWT.CENTER, false, false, 1, 1 ) );
				okBtn.setText("ok");
				
				okBtn.addSelectionListener( new SelectionAdapter () {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						try{
							cartonQty = Integer.parseInt( qtyTxt.getText() );
							shell.close();
						}catch( Exception e ){
							getLog().error( "error on adding a new carton", e );
							getErrorBox( e.getMessage() );
						}
					}
				} );
				
				shell.open();
				shell.layout();
				
				Display display = shell.getDisplay();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				
				if( cartonQty > 0 ) {
					PurchaseOrderDetail pod = getSelectedPurchaseOrderDetail().getPod();
					
					PackingDetail pd = new PackingDetail();
					pd.setDeleted(false);
					pd.setPurchaseOrderDetail(pod);
					pd.setQuantity(cartonQty);
					int newSku = getLastSku( pod.getPurchaseOrder() ) +1;
					pd.setSku( newSku );
					
					pd = (PackingDetail)getJpa().merge(pd);
					if( pd.getId()>0 ) {
						pod.getPackingDetails().add(pd);
						showPackingDetail();
						cleanScanningArea();
						showScanningInfo();
						TableItem[] items = getClient().getPackingDetailTbl().getItems();
						int rowIndex = 0;
						for (TableItem item : items) {
							ClientPackingDetailTable cpdt = (ClientPackingDetailTable)item.getData();
							if( cpdt.getPd().getId() == pd.getId() ){
								getClient().getPackingDetailTbl().select( rowIndex );
								break;
							}
							rowIndex++;
						}
					}else{
						throw new Exception( "the packingdetail { purchase_order_detail_id:"+pod.getId()+", quantity:"+cartonQty+", sku:"+newSku+" }" );
					}
				}
			}catch( Exception e2 ){
				getLog().error( "error", e2 );
			}
		} else {
			getInformationBox( "please select a purchase order detail first" );
		}
	}
	public void scanning() throws Exception {
		boolean success = false;
		
		String upcCode = getClient().getScannedBarTxt().getText().trim();
		Upc scannedUpc = UpcRepo.findByCode(upcCode);
		getClient().getScannedBarTxt().setText("");

		Carton carton = null;
		ScanDetail sd = null;
		
		if( getSelectedPackingDetail()==null )
			throw new Exception( "please select a packing detail first" );
		
		if( scannedUpc == null )
			throw new Exception( "the passed upc does not match with anyone in the database, please contact the sysadmin" );
		
		if( scannedUpc.getId() != getSelectedPackingDetail().getPd().getPurchaseOrderDetail().getUpc().getId() )
			throw new Exception( "the packing details requires upc [" + 
									getSelectedPackingDetail().getPd().getPurchaseOrderDetail().getUpc().getUpcCode() +
									"], not the one scanned [" + scannedUpc.getUpcCode() + "]");
		
		carton = getSelectedPackingDetail().getPd().getCarton();
		
		if( carton==null ){
			carton = new Carton();
			carton.setCompleted(false);
			carton.setDeleted(false);
			carton.setPackingDetail( getSelectedPackingDetail().getPd() );
			carton.setReferenceNumber( getSelectedPackingDetail().getPd().getPurchaseOrderDetail().getPurchaseOrder().getReferenceNumber() );
			carton.setUpcCode( getSelectedPackingDetail().getPd().getPurchaseOrderDetail().getUpc().getUpcCode() );
			carton.setUser( getUser() );
			
			carton = (Carton)getJpa().merge( carton );
			if( carton.getId() > 0 ){
				getSelectedPackingDetail().getPd().setCarton(carton);
			}
		}
		if( carton!=null && carton.getId() > 0 ){
			if( getSelectedPackingDetail().getPd().getQuantity()<=ClientLogic.getNumberOfScannedItems(carton) ){
				if( getQuestionBox( "the carton is already completed, do you want to add an extra item?" ) != SWT.YES ){
					return;
				}
			}
			
			sd = new ScanDetail();
			sd.setCarton(carton);
			sd.setDeleted(false);
			sd.setTimestamp( Calendar.getInstance().getTime() );
			sd.setUpc( scannedUpc );
			sd.setUpcReferenceNumber( scannedUpc.getUpcCode() );
			
			sd = (ScanDetail)getJpa().merge( sd );
			if( sd.getId() > 0 ) {
				carton.addScanDetail(sd);
				success = true;
			}else {
				throw new Exception( "there was a problem adding the scanned upc to the carton # "+ carton.getPackingDetail().getSku() );
			}
		}
		
		if( success ) {
			if( ClientLogic.getNumberOfScannedItems( carton ) >= getSelectedPackingDetail().getQty() ){
				carton.setCompleted( true );
				if( !getJpa().persist( carton ) )
					getErrorBox( "there's an error trying to mark the carton # "+ carton.getPackingDetail().getSku() +" as completed" );
			}
			
			loadScannedItems( sd );
			showPurchaseOrderSummary();
			updatePackingDetailTableItem();
			updatePurchaseOrderDetailTableItem();
			showScanningInfo();
			showTickets( getSelectedPackingDetail() );
			
			if( ClientLogic.getNumberOfScannedItems( carton ) >= getSelectedPackingDetail().getQty() ){
				if( getClient().getAutoPrintingChk().getSelection() )
					getClient().getTicketPrinter().printCartonTicket(carton, getSelectedPrintService(), getClient().getPrintDialogChk().getSelection());
			}
			
			getClient().getScannedBarTxt().setFocus();
		}
	}
	public void deleteScan() throws Exception {
		/* + On deleting a scanned item
		 * 		- Cleaning scanning info
		 * 		- Update PO summary
		 * 		- Update scanned column in packing detail table
		 * 		- Update ready column in packing detail table
		 */
		if( getSelectedScannedItem()!=null ) {
			getSelectedScannedItem().getSd().setDeleted(true);
			
			if( getJpa().persist( getSelectedScannedItem().getSd() ) ) {
				if( ClientLogic.getNumberOfScannedItems(getSelectedScannedItem().getSd().getCarton())<getSelectedPackingDetail().getQty() ){
					getSelectedPackingDetail().getPd().getCarton().setCompleted( false );
					if( !getJpa().persist( getSelectedPackingDetail().getPd().getCarton() ) ){
						throw new Exception( "there was a problem setting the carton #" + getSelectedPackingDetail().getPd().getSku() + " deleted" );
					}
				}
				
				loadScannedItems(null);
				cleanScaningInfo();
				showPurchaseOrderSummary();
				updatePackingDetailTableItem();
				updatePurchaseOrderDetailTableItem();
				showTickets(getSelectedPackingDetail());
			} else {
				getErrorBox( "there was an error while deleting the selected scan" );
			}
		} else {
			getInformationBox( "please select a scanned item first" );
		}
	}
	public void cleanCarton() throws Exception {
		if( getSelectedPackingDetail()!=null ){
			while( getClient().getScannedItemsTbl().getItems().length>0 ){
				getClient().getScannedItemsTbl().setSelection( getClient().getScannedItemsTbl().getItems().length-1 );
				deleteScan();
			}
			getInformationBox( "carton #" + getSelectedPackingDetail().getSku() + " cleaned." );
		}else{
			getInformationBox( "please select a packing detail first" );
		}
	}
	public void deleteCarton() throws Exception {
		if( getSelectedPackingDetail()!=null ){
			if( getQuestionBox( "do you want to delete the carton #"+getSelectedPackingDetail().getSku()+"?" )==SWT.YES ){
				getSelectedPackingDetail().getPd().setDeleted(true);
				if( getJpa().persist( getSelectedPackingDetail().getPd() ) ){
					getInformationBox( "carton #" + getSelectedPackingDetail().getSku() + " deleted correctly." );
					
					showPurchaseOrderSummary();
					cleanTicketsView();
					cleanScaningInfo();
					showPurchaseOrderDetail();
					showPackingDetail();
					cleanScanningArea();
				}else {
					getErrorBox( "there was an error trying to delete the carton #" + getSelectedPackingDetail().getSku() );
				}
			}
		}else{
			getInformationBox( "please select a packing detail first" );
		}
	}
	public void completeCarton() throws Exception {
		if( getSelectedPackingDetail()!=null && getSelectedPackingDetail().getPd()!=null && getSelectedPackingDetail().getPd().getCarton()!=null ){
			getSelectedPackingDetail().getPd().getCarton().setCompleted(true);
			if( !getJpa().persist( getSelectedPackingDetail().getPd().getCarton() ) ) {
				throw new Exception( "there was a problem trying to mark the carton as completed." );
			} else {
				getInformationBox( "carton #" + getSelectedPackingDetail().getPd().getSku() + " was marked as completed." );
			}
			
			getClient().getBtnCompleted().setSelection(getSelectedPackingDetail().getPd().getCarton().getCompleted());
		}
	}
	/*
	 * END of Events
	 */
	
	//	Load available PO for the current client
	public void loadAvailablePurchaseOrders(){
		List<PurchaseOrderByUser> purchaseOrdersByUser = getUser().getPurchaseOrders();
		for (PurchaseOrderByUser poByUser : purchaseOrdersByUser) {
			if( !poByUser.getDeleted() && !poByUser.getPurchaseOrder().getDeleted() ){
				com.uslc.po.jpa.entity.PurchaseOrder po = poByUser.getPurchaseOrder();
				getClient().getPurchaseOrderCbx().add( po.getReferenceNumber() );
				getClient().
				getPurchaseOrderCbx().
				setData( po.getReferenceNumber(), po );
			}
		}
	}
	//	Clean tickets view
	public void cleanTicketsView() throws JRException{
		getClient().getTicketViewer().getReportViewer().setDocument( getClient().getTicketPrinter().getLabelJp( null ) );
		getClient().getPolybagViewer().getReportViewer().setDocument( getClient().getTicketPrinter().getStickerLabelJp(null) );
	}
	//	Clean now scanning area
	public void cleanScanningArea(){
		loadScannedItems( null );
	}
	//	Clean scanning info
	public void cleanScaningInfo() {
		showScanningInfo();
	}
	//	Fill filters with PO values
	public void fillFiltersFromPurchaseOrder() {
		if( getSelectedPurchaseOrder()!=null ) {
			List<Item> itemList = new ArrayList<Item>();
			List<Color> colorList = new ArrayList<Color>();
			List<Size> sizeList = new ArrayList<Size>();
			
			for( PurchaseOrderDetail pod : getSelectedPurchaseOrder().getPurchaseOrderDetails() ) {
				Upc upc = pod.getUpc();
				if( !itemList.contains( upc.getItem() ) )itemList.add(upc.getItem());
				if( !colorList.contains( upc.getColor() ) )colorList.add(upc.getColor());
				if( !sizeList.contains( upc.getSize() ) )sizeList.add( upc.getSize() );
			}
			
			getClient().getItemCbx().removeAll();
			for (Item item : itemList) {
				getClient().getItemCbx().add( String.valueOf( item.getCode() ) );
				getClient().getItemCbx().setData( String.valueOf( item.getCode() ), item);
			}
			getClient().getColorCbx().removeAll();
			
			for (Color color : colorList) {
				getClient().getColorCbx().add( color.getName() );
				getClient().getColorCbx().setData( color.getName(), color );
			}
			getClient().getSizeCbx().removeAll();
			Collections.sort(sizeList, new SizeComparator());
			for (Size size : sizeList) {
				String sizeStr = "";
				if( getSelectedPurchaseOrder().getReferenceNumber().endsWith( "11" ) ){
					sizeStr = String.valueOf( size.getWaist() );
				}else{
					sizeStr = String.valueOf( size.getWaist() ) + " x " + String.valueOf( size.getInseam() );
				}
				getClient().getSizeCbx().add( sizeStr );
				getClient().getSizeCbx().setData( sizeStr, size );
			}
		}
	}
	//	Display PO Details according with selected filters
	public void showPurchaseOrderDetail(){
		List<PurchaseOrderDetail> purchaseOrderDetailList = new ArrayList<PurchaseOrderDetail>();
		
		for( PurchaseOrderDetail pod : getSelectedPurchaseOrder().getPurchaseOrderDetails() ){
			if( !pod.getDeleted() ){
				Upc upc = pod.getUpc();
				boolean add = true;
				if( getSelectedItem()!=null && upc.getItem().getId()!=getSelectedItem().getId() ){
					add = false;
				}
				if( getSelectedColor()!=null && upc.getColor().getId() != getSelectedColor().getId() ){
					add = false;
				}
				if( getSelectedSize()!=null && upc.getSize().getId() != getSelectedSize().getId() ){
					add = false;
				}
				
				if( add ){
					purchaseOrderDetailList.add(pod);
				}
			}
		}
		
		getClient().getPoDetailTbl().removeAll();
		int row = 0;
		
		Collections.sort(purchaseOrderDetailList, new PurchaseOrderDetailComparator() );
		for (PurchaseOrderDetail pod : purchaseOrderDetailList) {
			row++;
			ClientPurchaseOrderDetailTable clientPurchaseOrder = new ClientPurchaseOrderDetailTable(pod, row);
			TableItem item = new TableItem( getClient().getPoDetailTbl(), SWT.READ_ONLY );
			item.setText(clientPurchaseOrder.getColumnValues());
			item.setData(clientPurchaseOrder);
		}
		
		if( row>0 ){
			getClient().getPoDetailLbl().setText( "po detail: " + row );
		}else{
			getClient().getPoDetailLbl().setText( "po detail: " );
		}
		
	}
	//	Display Packing Detail of selected PO Detail
	public void showPackingDetail() {
		getClient().getPackingDetailTbl().removeAll();
		TableItem[] items = getClient().getPoDetailTbl().getItems();
		if( items!=null && items.length>0 ){
			List<PackingDetail> packingDetailList = new ArrayList<PackingDetail>();
			for( TableItem ti : items ){
				ClientPurchaseOrderDetailTable cpodt = (ClientPurchaseOrderDetailTable)ti.getData();
				PurchaseOrderDetail pod = cpodt.getPod();
				if( !pod.getDeleted() ){
					for( PackingDetail pd : pod.getPackingDetails() ){
						if( !pd.getDeleted() ){	
							packingDetailList.add( pd );
						}
					}
				}
			}
			
			Collections.sort(packingDetailList, new PackageDetailComparator() );
			for (PackingDetail pd : packingDetailList) {
				boolean add = true;
				if( getSelectedPurchaseOrderDetail()!=null ){
					add = false;
					for (PackingDetail pd2 : getSelectedPurchaseOrderDetail().getPod().getPackingDetails() ) {
						if( pd.getId() == pd2.getId() ){
							add = true;
						}
					}
				}
				if( add ){
					ClientPackingDetailTable clientPackingDetail = new ClientPackingDetailTable(pd);
					TableItem item = new TableItem( getClient().getPackingDetailTbl(), SWT.READ_ONLY);
					item.setText( clientPackingDetail.getColumnValues() );
					item.setData( clientPackingDetail );
				}
			}
			
			if( packingDetailList.size() > 0 ){
				getClient().getPackingDetailLbl().setText( "packing detail: " + getClient().getPackingDetailTbl().getItems().length );
			} else {
				getClient().getPackingDetailLbl().setText( "packing detail: " );
			}
		}
	}
	//	Show PO Summary info
	public void showPurchaseOrderSummary(){
		if( getSelectedPurchaseOrder()!=null ){
			//PurchaseOrderSummary pos = new PurchaseOrderSummary(getSelectedPurchaseOrder());
			ClientPurchaseOrderSumary cpos = new ClientPurchaseOrderSumary(getSelectedPurchaseOrder());
			
			cpos.addInfoToComposites(getClient().getColorsComposite(), getClient().getGrpPoDetails(), getClient().getGrpPackingDetails());
		}
	}
	//	Show scanning info
	public void showScanningInfo(){
		if( getSelectedScannedItem()!=null ){
			Upc upc = getSelectedScannedItem().getUpc();
			getClient().getInfoUpcLbl().setText("upc: " + upc.getUpcCode() );
			getClient().getBarcodeLbl().setImage( ImageUtils.resize( ImageUtils.getBarcodeImage( getClient().getDisplay(), upc.getUpcCode() ), 150, 75) );
			
			getClient().getInfoSizeLbl().setText( getSelectedScannedItem().getSize() );
			getClient().getInfoColorLbl().setText( getSelectedScannedItem().getColor() );
			getClient().getInfoItemLbl().setText( String.valueOf( upc.getItem().getCode() ) );
			getClient().getInfoSkuLabel().setText( String.valueOf( getSelectedScannedItem().getSd().getCarton().getPackingDetail().getSku() ) );
			getClient().getInfoPreticketedCbx().setSelection( getSelectedScannedItem().getSd().getCarton().getPackingDetail().getPurchaseOrderDetail().getPreticketed() );
			
		}else{
			getClient().getInfoUpcLbl().setText("upc:");
			getClient().getBarcodeLbl().setImage( null );
			
			getClient().getInfoSizeLbl().setText("");
			getClient().getInfoColorLbl().setText("");
			getClient().getInfoItemLbl().setText("");
			getClient().getInfoSkuLabel().setText("");
			getClient().getInfoPreticketedCbx().setSelection(false);
		}
	}
	//	Load scanned items
	public void loadScannedItems(ScanDetail scannedDetail ){
		Table table = getClient().getScannedItemsTbl();
		table.removeAll();
		
		if( getSelectedPackingDetail()!=null && getSelectedPackingDetail().getPd().getCarton()!=null ){
			int order = 0;
			for (ScanDetail sd : getSelectedPackingDetail().getPd().getCarton().getScanDetails() ) {
				if( !sd.getDeleted() ){
					order++;
					ClientScannedItemsTable clientScanned = new ClientScannedItemsTable(order, sd);
					TableItem item = new TableItem( getClient().getScannedItemsTbl(), SWT.READ_ONLY );
					item.setText( clientScanned.getColumnValues() );
					item.setData( clientScanned );
				}
				if( scannedDetail!=null && sd.getId()==scannedDetail.getId() ){
					getClient().getScannedItemsTbl().setSelection( order-1 );
				}
			}
			
			getClient().getBtnCompleted().setSelection( getSelectedPackingDetail().getPd().getCarton().getCompleted() );
		}
	}
	//	Show tickets
	public void showTickets( ClientPackingDetailTable cpdt ) throws JRException {
		getClient().getTicketViewer().getReportViewer().setDocument( getClient().getTicketPrinter().getLabelJp( cpdt.getPd() ) );
		getClient().getPolybagViewer().getReportViewer().setDocument( getClient().getTicketPrinter().getStickerLabelJp( cpdt.getPd() ) );
		
		getClient().getTicketViewer().getReportViewer().setZoomMode( IReportViewer.ZOOM_MODE_FIT_WIDTH );
		getClient().getPolybagViewer().getReportViewer().setZoomMode( IReportViewer.ZOOM_MODE_FIT_WIDTH );
	
		int selection = (int)((cpdt.getScanned()*100)/cpdt.getQty());
		getClient().getProgressBar().setSelection( selection );
	}
	//	Update scanned in packing detail
	public void updatePackingDetailTableItem(){
		for( int i = 0 ; i < getClient().getPackingDetailTbl().getItems().length ; i++ ){
			ClientPackingDetailTable cpdt = (ClientPackingDetailTable)getClient().getPackingDetailTbl().getItems()[i].getData();
			if( cpdt.getPd().getId() == getSelectedPackingDetail().getPd().getId() ) {
				cpdt = new ClientPackingDetailTable( cpdt.getPd() );
				getClient().getPackingDetailTbl().getItems()[i].setText( cpdt.getColumnValues() );
				getClient().getPackingDetailTbl().getItems()[i].setData( cpdt );
				break;
			}
		}
	}
	//	Update ready column in PO Detail
	public void updatePurchaseOrderDetailTableItem(){
		for( int i = 0 ; i < getClient().getPoDetailTbl().getItems().length ; i++ ){
			ClientPurchaseOrderDetailTable cpodt = (ClientPurchaseOrderDetailTable)getClient().getPoDetailTbl().getItems()[i].getData();
			if( cpodt.getPod().getId() == getSelectedPackingDetail().getPd().getPurchaseOrderDetail().getId() ){
				cpodt = new ClientPurchaseOrderDetailTable(cpodt.getPod(), cpodt.getRow());
				getClient().getPoDetailTbl().getItems()[i].setText( cpodt.getColumnValues() );
				getClient().getPoDetailTbl().getItems()[i].setData( cpodt );
				getLog().info( cpodt.getColumnValues() );
				break;
			}
		}
		
		for( TableItem item : getClient().getPoDetailTbl().getItems() ){
			ClientPurchaseOrderDetailTable cpodt = (ClientPurchaseOrderDetailTable)item.getData();
			if( cpodt.getPod().getId() == getSelectedPackingDetail().getPd().getPurchaseOrderDetail().getId() ){
				cpodt = new ClientPurchaseOrderDetailTable(cpodt.getPod(), cpodt.getRow());
				item.setText( cpodt.getColumnValues() );
				break;
			}
		}
	}
	
	private User getUser() {
		getLog().info( "user["+user +"]" );
		return user;
	}
	private UslcJpa getJpa() {
		if( jpa == null ){
			jpa = new UslcJpa();
		}
		return jpa;
	}
	private Logger getLog() {
		if( log == null ){
			log = Logger.getLogger(ClientLogic.class);
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	private Client getClient() {
		return client;
	}
	private PurchaseOrder getSelectedPurchaseOrder(){
		PurchaseOrder po = null;
		
		try{
			Combo c = getClient().getPurchaseOrderCbx();
			if( c.getSelectionIndex()>-1 )
				po = (PurchaseOrder)c.getData( c.getItem( c.getSelectionIndex() ) );
		}catch( Exception e ){
			getLog().error( "error on retrieving the selected purchase order", e );
		}
		
		return po;
	}
	private Item getSelectedItem(){
		Item item = null;
		
		try {
			Combo c = getClient().getItemCbx();
			if( c.getSelectionIndex()>-1 )
				item = (Item)c.getData( c.getItem( c.getSelectionIndex() ) );
		} catch( Exception e ) {
			getLog().info( "error on retrieving the selected item", e );
		}
		
		return item;
	}
	private Color getSelectedColor(){
		Color color = null;
		
		try {
			Combo c = getClient().getColorCbx();
			if( c.getSelectionIndex()>-1 )
				color = (Color)c.getData(c.getItem(c.getSelectionIndex()));
		} catch( Exception e ) {
			getLog().error( "error on retrieving the selected color", e );
		}
		
		return color;
	}
	private Size getSelectedSize(){
		Size size = null;
		
		try {
			Combo c = getClient().getSizeCbx();
			if( c.getSelectionIndex()>-1 )
				size = (Size)c.getData(c.getItem(c.getSelectionIndex()));
		} catch( Exception e ) {
			getLog().error( "error on retrieving the selected size", e );
		}
		
		return size;
	}
	private ClientPurchaseOrderDetailTable getSelectedPurchaseOrderDetail(){
		ClientPurchaseOrderDetailTable cpodt = null;
		
		try {
			TableItem[] items = getClient().getPoDetailTbl().getSelection();
			
			if( items != null ) {
				for (TableItem item : items) {
					cpodt = (ClientPurchaseOrderDetailTable)item.getData();
				}
			}
		} catch( Exception e ){
			getLog().error( "error on retrieving the selected purchase order detail", e );
		}
		
		return cpodt;
	}
	private ClientPackingDetailTable getSelectedPackingDetail(){
		ClientPackingDetailTable cpdt = null;
		
		try{
			TableItem[] items = getClient().getPackingDetailTbl().getSelection();
			if( items!=null ){
				for (TableItem item : items) {
					cpdt = (ClientPackingDetailTable)item.getData();
				}
			}
		}catch( Exception e ){
			getLog().error( "error on retrieving the selected packing detail", e );
		}
		
		return cpdt;
	}
	private ClientScannedItemsTable getSelectedScannedItem(){
		ClientScannedItemsTable csit = null;
		
		try {
			TableItem[] items = getClient().getScannedItemsTbl().getSelection();
			
			if( items != null ) {
				for (TableItem item : items) {
					csit = (ClientScannedItemsTable)item.getData();
				}
			}
		} catch( Exception e ) {
			getLog().error( "error on retrievint the selected scanned item", e );
		}
		
		return csit;
	}
	public PrintService getSelectedPrintService() {
		PrintService service = null;
		try{
			service = (PrintService)getClient().getPrintersCbx().getData( 
					getClient().getPrintersCbx().getItem( 
							getClient().getPrintersCbx().getSelectionIndex() ) );
		}catch( Exception e1 ){
			getLog().error( "Error selecting the printService", e1 );
			getClient().getErrorBox( e1.getMessage() );
		}
		return service;
	}
	public boolean isScanning(){
		boolean scanning = false;
		
		scanning = !(!getClient().getPoDetailTbl().getEnabled() && !getClient().getSizeCbx().getEnabled() && !getClient().getColorCbx().getEnabled() && 
				!getClient().getItemCbx().getEnabled() && !getClient().getPoDetailTbl().getEnabled() && !getClient().getPackingDetailTbl().getEnabled());
		
		return scanning;
	}
	public boolean lockForScanning(){
		boolean locked = false;
		
		getClient().getPoDetailTbl().setEnabled(false);
		getClient().getSizeCbx().setEnabled(false);
		getClient().getColorCbx().setEnabled(false);
		getClient().getItemCbx().setEnabled(false);
		getClient().getPoDetailTbl().setEnabled(false);
		getClient().getPackingDetailTbl().setEnabled(false);
		
		locked = !(!getClient().getPoDetailTbl().getEnabled() && !getClient().getSizeCbx().getEnabled() && !getClient().getColorCbx().getEnabled() && 
				!getClient().getItemCbx().getEnabled() && !getClient().getPoDetailTbl().getEnabled() && !getClient().getPackingDetailTbl().getEnabled());
		
		return locked;
	}
	public boolean unlockScanningFinished(){
		boolean unlocked = false;
		
		getClient().getPoDetailTbl().setEnabled(true);
		getClient().getSizeCbx().setEnabled(true);
		getClient().getColorCbx().setEnabled(true);
		getClient().getItemCbx().setEnabled(true);
		getClient().getPoDetailTbl().setEnabled(true);
		getClient().getPackingDetailTbl().setEnabled(true);
		getClient().getLayout();
		
		unlocked = (getClient().getPoDetailTbl().getEnabled() && getClient().getSizeCbx().getEnabled() && getClient().getColorCbx().getEnabled() && 
				getClient().getItemCbx().getEnabled() && getClient().getPoDetailTbl().getEnabled() && getClient().getPackingDetailTbl().getEnabled() );
		
		return unlocked;
	}
	public int getLastSku( PurchaseOrder po ){
		int lastSku = 0;
		
		List<PackingDetail> pdList = new ArrayList<PackingDetail>();
		
		for( PurchaseOrderDetail pod : po.getPurchaseOrderDetails() ){
			if( !pod.getDeleted() ){
				for( PackingDetail pd : pod.getPackingDetails() ){
					pdList.add( pd );
				}
			}
		}
		
		Collections.sort(pdList, new PackageDetailComparator() );
		lastSku = pdList.get(pdList.size()-1).getSku();
		
		return lastSku;
	}
	public void printTicket() {
		if( getSelectedPackingDetail()!=null ){
			getClient().getTicketPrinter().printCartonTicket(getSelectedPackingDetail().getPd().getCarton(), getSelectedPrintService(), getClient().getAutoPrintingChk().getSelection());
		}else{
			getInformationBox( "please select a packing detail first" );
		}
	}

	public static int getNumberOfScannedItems( Carton carton ){
		int scanned = 0;
		
		if( carton!=null )
			for( ScanDetail scan : carton.getScanDetails() ){
				if( !scan.getDeleted() ){
					scanned++;
				}
			}
		
		return scanned;
	}
	public static int getNumberOfScannedItems(PackingDetail pd) {
		int scanned = 0;
		if( !pd.getDeleted() ) {
			if( pd.getCarton()!=null ) {
				scanned = getNumberOfScannedItems(pd.getCarton() );
			}
		}
		return scanned;
	}
	public static SimpleDateFormat getSimpleDateFormat() {
		if( sdf == null ) {
			sdf = new SimpleDateFormat( "mm/dd/yy" );
		}
		return sdf;
	}

	@Override
	public void displayValues() {
		getClient().setText("purchase order client");
		getClient().setSize(1037, 748);
		
		PrintingUtils printing = new PrintingUtils(getClient());
		int printerIndex = -1;
		int i = 0;
		for( PrintService print : printing.getServices() ){
			if( getClient().getTicketPrinter().getDefaultPrinterName()!=null && !getClient().getTicketPrinter().getDefaultPrinterName().isEmpty() ){
				if( getClient().getTicketPrinter().getDefaultPrinterName().trim().compareTo( print.getName().trim() )==0 ){
					printerIndex = i;
				}
				//getLog().info( "default-ticket-printer:" + getMaster().getDefaultTicketPrinter() + ", print: " + print.getName() + ", printerIndex: " + printerIndex );
			}
			i++;
			getClient().getPrintersCbx().add( print.getName() );
			getClient().getPrintersCbx().setData( print.getName(), print);
		}
		getClient().getPrintersCbx().select(printerIndex);
		getClient().getNumberOfCopiesSpn().setSelection(getClient().getTicketPrinter().getDefaultNumberOfCopies() );
		getClient().getAutoPrintingChk().setSelection( getClient().getTicketPrinter().isAutoPrint() );
		getClient().getPrintDialogChk().setSelection( getClient().getTicketPrinter().isShowPrintDialog() );
		
		loadAvailablePurchaseOrders();
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshFormData() {
		// TODO Auto-generated method stub
		
	}
}