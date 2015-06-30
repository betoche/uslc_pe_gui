package com.uslc.po.gui.client;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;
import com.uslc.po.jpa.entity.Upc;
import com.uslc.po.jpa.logic.PurchaseOrderRepo;
import com.uslc.po.jpa.logic.UpcRepo;

public class POClient_ extends Shell {
	private Table table;
	private Table table_1;
	private Table table_2;
	private Logger log = null; 
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			POClient_ shell = new POClient_(display);
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

	/**
	 * Create the shell.
	 * @param display
	 */
	public POClient_(Display display) {
		super(display, SWT.SHELL_TRIM);
		addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent event) {
				if(event.detail == SWT.TRAVERSE_RETURN) {
					//String str = ((Text)event.getSource()).getText();
					getLog().info( "Enter Pressed: " );
					text.setFocus();
	            }
			}
		});
		
		Composite composite = new Composite(this, SWT.BORDER);
		composite.setBounds(10, 10, 220, 582);
		
		Label lblPo = new Label(composite, SWT.NONE);
		lblPo.setBounds(10, 10, 38, 21);
		lblPo.setText("PO #:");
		
		final Combo combo = new Combo(composite, SWT.READ_ONLY);
		
		combo.setBounds(50, 10, 160, 33);
		
		Label lblPoDetail = new Label(composite, SWT.NONE);
		lblPoDetail.setBounds(10, 49, 71, 21);
		lblPoDetail.setText("PO Detail:");
		
		table = new Table(composite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		
		table.setBounds(10, 82, 200, 314);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn upc = new TableColumn(table, SWT.NONE);
		TableColumn items = new TableColumn(table, SWT.NONE);
		upc.setText("upc");
		items.setText( "items" );
		upc.setWidth(140);
		items.setWidth(50);
		
		Label lblPackingDetail = new Label(composite, SWT.NONE);
		lblPackingDetail.setBounds(10, 402, 105, 20);
		lblPackingDetail.setText("Packing Detail");
		
		table_2 = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table_2.setBounds(10, 424, 200, 144);
		table_2.setHeaderVisible(true);
		table_2.setLinesVisible(true);
		TableColumn sku = new TableColumn(table_2, SWT.NONE);
		TableColumn qty = new TableColumn(table_2, SWT.NONE);
		TableColumn packed = new TableColumn(table_2, SWT.NONE);
		sku.setText("upc");
		qty.setText( "items" );
		packed.setText( "packed" );
		sku.setWidth(50);
		qty.setWidth(50);
		packed.setWidth(50);
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadPoDetail( combo, table );
			}
		});
		
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadPackingDetail( table, table_2 );
			}
		});
		
		Composite composite_1 = new Composite(this, SWT.BORDER);
		composite_1.setBounds(236, 10, 362, 582);
		
		Label lblScanList = new Label(composite_1, SWT.NONE);
		lblScanList.setBounds(10, 10, 71, 21);
		lblScanList.setText("Scan List:");
		
		table_1 = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(10, 37, 342, 416);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		TableColumn upcCol = new TableColumn(table_1, SWT.NONE);
		TableColumn itemCol = new TableColumn(table_1, SWT.NONE);
		TableColumn sizeCol = new TableColumn(table_1, SWT.NONE);
		upcCol.setText("upc");
		itemCol.setText( "item-code" );
		sizeCol.setText( "size" );
		upcCol.setWidth(130);
		itemCol.setWidth(100);
		sizeCol.setWidth(100);
		
		Group grpLastItemScanned = new Group(composite_1, SWT.NONE);
		grpLastItemScanned.setText("Last Item Scanned");
		grpLastItemScanned.setBounds(10, 459, 340, 109);
		
		Group grpBarcode_1 = new Group(grpLastItemScanned, SWT.NONE);
		grpBarcode_1.setText("barcode");
		grpBarcode_1.setBounds(10, 20, 160, 79);
		
		Label lblUpcCode = new Label(grpLastItemScanned, SWT.NONE);
		lblUpcCode.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblUpcCode.setBounds(176, 20, 164, 20);
		lblUpcCode.setText("upc code: 410013940956");
		
		Label lblSizex = new Label(grpLastItemScanned, SWT.NONE);
		lblSizex.setBounds(176, 40, 96, 20);
		lblSizex.setText("Size: 23x30");
		
		Label lblColorOnyx = new Label(grpLastItemScanned, SWT.NONE);
		lblColorOnyx.setBounds(176, 60, 96, 20);
		lblColorOnyx.setText("Color: ONYX");
		
		Label lblItem = new Label(grpLastItemScanned, SWT.NONE);
		lblItem.setOrientation(SWT.RIGHT_TO_LEFT);
		lblItem.setBounds(226, 79, 104, 20);
		lblItem.setText("Item: 4772");
		
		text = new Text(composite_1, SWT.BORDER);
		text.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent event) {
				if(event.detail == SWT.TRAVERSE_RETURN) {
					String str = ((Text)event.getSource()).getText();
					getLog().info( "Enter Pressed: " + text.getText() );
					if( str.length()>10 && str.length()<13 ) {
						Upc upc = UpcRepo.findByCode( str );
						TableItem item = new TableItem(table_1, SWT.NONE);
						String[] texts = {  str, upc.getColorItemCode(), (upc.getSize().getWaist()+"x"+upc.getSize().getInseam()) };
						item.setData( upc );
						item.setText(texts);
					}
	            }
				text.setText("");
				text.setFocus();
			}
		});
		text.setBounds(87, 5, 142, 26);
		
		Label lblTotal = new Label(composite_1, SWT.NONE);
		lblTotal.setOrientation(SWT.RIGHT_TO_LEFT);
		lblTotal.setBounds(278, 10, 70, 20);
		lblTotal.setText("Total: 10");
		
		Composite composite_2 = new Composite(this, SWT.BORDER);
		composite_2.setBounds(604, 10, 209, 582);
		
		Group grpPoInfo = new Group(composite_2, SWT.NONE);
		grpPoInfo.setText("PO Info");
		grpPoInfo.setBounds(10, 10, 187, 236);
		
		Label lblFromUslcApparel = new Label(grpPoInfo, SWT.NONE |SWT.WRAP );
		lblFromUslcApparel.setBounds(10, 31, 167, 45);
		lblFromUslcApparel.setText("From: \nUSLC Apparel Nicaragua");
		
		Label lblToOtherLocation = new Label(grpPoInfo, SWT.WRAP);
		lblToOtherLocation.setText("To: \nOther location in the world");
		lblToOtherLocation.setBounds(10, 82, 167, 45);
		
		Label lblCreated = new Label(grpPoInfo, SWT.NONE);
		lblCreated.setBounds(10, 154, 70, 20);
		lblCreated.setText("Created:");
		
		Label lblSatthOf = new Label(grpPoInfo, SWT.NONE);
		lblSatthOf.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblSatthOf.setBounds(10, 180, 167, 20);
		lblSatthOf.setText("Sat 18th of January of 2014");
		
		Group grpDetailInfo = new Group(composite_2, SWT.NONE);
		grpDetailInfo.setText("Detail Info");
		grpDetailInfo.setBounds(10, 252, 187, 188);
		
		Group grpBarcode = new Group(grpDetailInfo, SWT.NONE);
		grpBarcode.setText("barcode");
		grpBarcode.setBounds(10, 22, 167, 87);
		
		Label lblTotalItems_1 = new Label(grpDetailInfo, SWT.NONE);
		lblTotalItems_1.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblTotalItems_1.setBounds(10, 115, 109, 20);
		lblTotalItems_1.setText("Total Items: 15");
		
		Label lblScannedItems = new Label(grpDetailInfo, SWT.NONE);
		lblScannedItems.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblScannedItems.setText("Scanned Items: 3");
		lblScannedItems.setBounds(10, 137, 124, 20);
		
		Label lblPendingItems = new Label(grpDetailInfo, SWT.NONE);
		lblPendingItems.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblPendingItems.setText("Pending Items: 12");
		lblPendingItems.setBounds(10, 160, 124, 20);
		
		Label lblCartons = new Label(grpDetailInfo, SWT.NONE);
		lblCartons.setOrientation(SWT.RIGHT_TO_LEFT);
		lblCartons.setBounds(101, 115, 76, 20);
		lblCartons.setText("Cartons");
		
		Label label = new Label(grpDetailInfo, SWT.NONE);
		label.setOrientation(SWT.RIGHT_TO_LEFT);
		label.setBounds(141, 137, 36, 20);
		label.setText("2");
		
		Group grpUserInfo = new Group(composite_2, SWT.NONE);
		grpUserInfo.setText("User Info");
		grpUserInfo.setBounds(10, 446, 187, 122);
		
		Label lblTotalPo = new Label(grpUserInfo, SWT.NONE);
		lblTotalPo.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblTotalPo.setBounds(100, 100, 85, 20);
		lblTotalPo.setText("Total PO: 43");
		
		Label lblTotayPo = new Label(grpUserInfo, SWT.NONE);
		lblTotayPo.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblTotayPo.setBounds(10, 20, 95, 20);
		lblTotayPo.setText("Totay' PO: 12");
		
		Label lblTotalItems = new Label(grpUserInfo, SWT.NONE);
		lblTotalItems.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblTotalItems.setBounds(10, 38, 113, 20);
		lblTotalItems.setText("Total Items: 1023");
		
		Label lblTotalCartons = new Label(grpUserInfo, SWT.NONE);
		lblTotalCartons.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblTotalCartons.setBounds(10, 55, 113, 20);
		lblTotalCartons.setText("Total Cartons: 96");
		createContents();
		
		List<PurchaseOrder> pos = PurchaseOrderRepo.findAll();
		if( pos!=null ){
			getLog().info( "pos.size()[" + pos.size() + "]" );
			for (PurchaseOrder po : pos) {
				combo.add( po.getReferenceNumber() );
				combo.setData(po.getReferenceNumber(), po);
			}
		}
		
		text.setFocus();
	}

	protected void loadPackingDetail(Table table2, Table table_22) {
		TableItem[] selection = table2.getSelection();
		PurchaseOrderDetail pod = null;
		
		if( selection!=null ){
			for (TableItem item : selection) {
				pod = (PurchaseOrderDetail)item.getData();
			}
		}
		
		if( pod!=null ){
			table_22.removeAll();
			for( PackingDetail pd : pod.getPackingDetails() ){
				TableItem item = new TableItem( table_22, SWT.NONE);
				String[] texts = {  String.valueOf( pd.getSku() ), String.valueOf( pd.getQuantity() ), "0" };
				item.setData( pod );
				item.setText(texts);
			}
		}
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(838, 649);

	}
	
	public void loadPoDetail( Combo combo, Table table ){
		PurchaseOrder po = (PurchaseOrder)combo.getData( combo.getItem( combo.getSelectionIndex() ) );
		if( po!=null ){
			table.removeAll();
			for( PurchaseOrderDetail pod : po.getPurchaseOrderDetails() ){
				TableItem item = new TableItem( table, SWT.NONE);
				String[] texts = {  pod.getUpc().getUpcCode(),  String.valueOf( pod.getTotal() ) };
				item.setData( pod );
				item.setText(texts);
			}
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger(POClient_.class);
			PropertyConfigurator.configure("log4j.properties");
		}
		return log;
	}
}
