package com.uslc.po.gui.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import com.uslc.po.gui.master.NewPurchaseOrderComposite;
import com.uslc.po.gui.master.NewPurchaseOrderComposite.PODetailData;
import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.gui.util.PurchaseOrder;
import com.uslc.po.gui.util.PurchaseOrderDetailUpload;
import com.uslc.po.jpa.entity.Color;
import com.uslc.po.jpa.entity.Item;
import com.uslc.po.jpa.entity.Size;
import com.uslc.po.jpa.entity.Upc;
import com.uslc.po.jpa.logic.ColorRepo;
import com.uslc.po.jpa.logic.ItemRepo;
import com.uslc.po.jpa.logic.SizeRepo;
import com.uslc.po.jpa.logic.UpcRepo;
import com.uslc.po.jpa.util.Constants;

public class NewPurchaseOrderLogic implements LiveDataAccessLifeCicle {
	private NewPurchaseOrderComposite newPo = null;
	private Logger log = null;
	private int totalItems = 0;
	private List<PODetailData> poDetailData = null;
		
	public NewPurchaseOrderLogic( NewPurchaseOrderComposite newPo ) {
		this.newPo = newPo;
		addListeners();
		displayValues();
	}
	
	public void addListeners() {
		getNewPo().getItemsCbx().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				getNewPo().getParent().getMaster().getMasterRight().getNewPurchaseOrderDetail().loadVaues();
			}
		});
		
		getNewPo().getAdd().addListener( SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				Item item = null;
				try{ 
					item = (Item)getNewPo().getItemsCbx().getData( getNewPo().getItemsCbx().getItem(getNewPo().getItemsCbx().getSelectionIndex()) );
					}catch(Exception e){
						
					}
				if( item!=null ){
					getNewPo().getParent().getMaster().getMasterRight().showComposite( getNewPo().getParent().getMaster().getMasterRight().getNewPurchaseOrderDetail() );
				}else{
					MessageBox box = new MessageBox(getNewPo().getShell(), SWT.ICON_INFORMATION);
					box.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
					box.setMessage( "please select an item before adding the purchase order details" );
					box.open();
					getNewPo().getItemsCbx().setFocus();
				}
			}
		});
		
		getNewPo().getDel().addListener( SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				removeSelectedPoDetail();
			}
		});
	}
	
	public NewPurchaseOrderComposite getNewPo() {
		return newPo;
	}
	
	private void removeSelectedPoDetail(){
		TableItem[] items = getNewPo().getPoDetailTbl().getSelection();
		for (TableItem tableItem : items) {
			PODetailData obj = (PODetailData)tableItem.getData();
			getPoDetailData().remove(obj);
		}
		loadPoDetails();
	}
	public boolean addPoDetail( PODetailData poDetail ) {
		boolean success = false;
		boolean alreadyIn = false;
		
		Upc newUpc = poDetail.getUpc();
		int qty = poDetail.getQty();
		int itemsPerCarton = poDetail.getItemsPerCarton();
		boolean preticketed = poDetail.isPreticketed();
		
		for (PODetailData o : getPoDetailData()) {
			Upc tmpUpc = o.getUpc();
			int tmpItemsPerCarton = o.getItemsPerCarton();
			boolean tmpPreticketed = o.isPreticketed();
			
			if( tmpUpc.getId()==newUpc.getId() 
					&& tmpItemsPerCarton==itemsPerCarton
					&& tmpPreticketed==preticketed ){
				getLog().info( "tmpUpc["+tmpUpc.getId()+","+tmpUpc.getUpcCode()+"] - newUpc["+newUpc.getId()+","+newUpc.getUpcCode()+"]" );
				alreadyIn=true;
				break;
			}
		}
		
		if( alreadyIn ){
			MessageBox msg = new MessageBox(getNewPo().getShell(), SWT.ICON_ERROR );
			msg.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
			msg.setMessage( "selected upc["+newUpc.getUpcCode()+"] is already in list, select a different one, or change the quantity" );
			msg.open();
		}else{
			getPoDetailData().add(poDetail);
			loadPoDetails();
			success = true;
		}
		
		return success;
	}
	private void loadPoDetails(){
		getNewPo().getPoDetailTbl().removeAll();
		getLog().info(  "loadPoDetails - getPoDetailTbl().getItemCount( " + getNewPo().getPoDetailTbl().getItemCount() + " )" );
		
		int sku = 0;
		totalItems = 0;
		getLog().info( "loadPoDetails - getPoDetailData().size( " + getPoDetailData().size() + " )" );
		for (PODetailData det : getPoDetailData()) {
			Upc upc = det.getUpc();
			int qty = det.getQty();
			int itemsPerCarton = det.getItemsPerCarton();
			
			int cartons = (int)Math.ceil( new Double(qty)/new Double(itemsPerCarton) );
			int qtyControl = qty;
			getLog().info( "qty["+qty+"], itemsPerCarton[" + itemsPerCarton + "], cartons[" + cartons + "], qtyControl[" + qtyControl + "]" );
			for( int i = 0 ; i < cartons ; i++ ){
				//getLog().info( "adding carton detail" );
				int itemsInCarton = 0;
				if( qtyControl < itemsPerCarton ){
					itemsInCarton = qtyControl;
				}else{
					itemsInCarton = itemsPerCarton;
				}
				qtyControl = qtyControl - itemsInCarton;
				
				TableItem row = new TableItem(getNewPo().getPoDetailTbl(), SWT.READ_ONLY);
				String size = "";
				if( getNewPo().getOrderNumberTxt().getText().trim().endsWith( "11" ) ){
					size = String.valueOf( upc.getSize().getWaist() );
				}else{
					size = upc.getSize().getWaist() + " x " + upc.getSize().getInseam();
				}
				String[] texts = {upc.getUpcCode(), 
						upc.getColor().getName(), 
						( upc.getColor().getName()+"-"+upc.getItem().getCode() ), 
						upc.getColor().getNumber(), 
						String.valueOf( ++sku ), 
						size,
						String.valueOf( itemsInCarton ) };
				row.setText(texts);
				row.setData( det );
			}
			totalItems += qty;
		}
		
		if( getNewPo().getPoDetailTbl().getItems().length>0 ){
			//getItemsCbx().setEnabled(false);
			getNewPo().getParent().getMaster().getMasterTop().getSavePo().setEnabled(true);
			getNewPo().getCartonTxt().setText( String.valueOf( sku ) );
		}else{
			//getItemsCbx().setEnabled(true);
			getNewPo().getParent().getMaster().getMasterTop().getSavePo().setEnabled(false);
			getNewPo().getCartonTxt().setText( "0" );
			totalItems = 0;
		}
		getLog().info( "total items: " + totalItems );
		getNewPo().getTotalItemsLbl().setText( "total items: " + String.valueOf( totalItems ) );
		getNewPo().layout();
		getNewPo().getParent().layout();
		getNewPo().getParent().getMaster().getShell().layout();
	}
	public void savePo() {
		String departmentNumber = "";
		String referenceNumber = "";
		String shipFrom = "";
		String shipTo = "";
		int totalCartons = 0;
		
		com.uslc.po.jpa.entity.PurchaseOrder po = null;
		
		try{
			departmentNumber = getNewPo().getDeptTxt().getText().trim();
			referenceNumber = getNewPo().getOrderNumberTxt().getText();
			shipFrom = getNewPo().getShipFromTxt().getText();
			shipTo = getNewPo().getShipToTxt().getText();
			totalCartons = Integer.parseInt( getNewPo().getCartonTxt().getText() );
			
			getLog().info( "getPoDetailData().size()[" + getPoDetailData().size() + "] - departmentNumber["+departmentNumber+"]" );
			po = PurchaseOrder.createPurchaseOrderCascade(departmentNumber, referenceNumber, shipFrom, shipTo, totalCartons, totalItems, getPoDetailData());
			
			
			MessageBox box = null;
			if( po!=null ){
				box = new MessageBox(getNewPo().getShell(), SWT.ICON_INFORMATION);
				getNewPo().getLiveDataAccessLifeCicle().clean();
				getNewPo().getParent().getMaster().getMasterRight().hideAllComposites();
				getNewPo().getParent().getMaster().getMasterLeft().getLiveDataAccessLifeCicle().displayValues();
				box.setMessage( "purchase order added correctly to the system." );
			}else{
				box = new MessageBox(getNewPo().getShell(), SWT.ICON_ERROR);
				box.setMessage( "there was a problem adding the purchase order." );
			}
			box.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
			box.open();
		}catch( Exception e ){
			e.printStackTrace();
			MessageBox box = new MessageBox(getNewPo().getShell(), SWT.ICON_ERROR);
			box.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
			box.setMessage( e.getMessage() );
			box.open();
		}
	}
	
	private Logger getLog() {
		if( log == null ) {
			log = Logger.getLogger( NewPurchaseOrderLogic.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	public Exception fillWithUploadedFile(String path) {
		int upcCol = -1;
		int deptCol = -1;
		int itemCol = -1;
		int poCol = -1;
		int colorNameCol = -1;
		int colorNumCol = -1;
		int sizeCol = -1;
		int qtyCol = -1;
		int qtyPerCartonCol = -1;
		
		getPoDetailData().clear();
		
		Exception e = null;
		try {
			FileInputStream file = new FileInputStream(new File(path));
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			
			List<PurchaseOrderDetailUpload> podUpload = new ArrayList<PurchaseOrderDetailUpload>();
			
			HashMap<String,Upc> uploadUpc = new HashMap<String, Upc>();
			HashMap<String,Color> uploadColor = new HashMap<String, Color>();
			HashMap<String,Size> uploadSize = new HashMap<String, Size>();
			HashMap<String,Item> uploadItem = new HashMap<String, Item>();
			
			List<Object[]> det = new ArrayList<Object[]>();
			
			boolean missingInCatalog = false;
			while( rows.hasNext() ){
				Row row = rows.next();
				
				if( upcCol==-1 || deptCol==-1 || itemCol==-1 
						|| poCol==-1 || colorNameCol == -1
						|| colorNumCol==-1 || sizeCol==-1 || qtyCol==-1 ){
				
					Iterator<Cell> cells = row.iterator();
					while( cells.hasNext() ){
						Cell cell = cells.next();
						String name = cell.getStringCellValue().toLowerCase().trim();
						int colIndex = cell.getColumnIndex();
						getLog().info( "name["+name+"] - colIndex["+colIndex+"]" );
						
						if( name.contains( "upc" ) ){
							upcCol=colIndex;
						}
						if( name.contains("dept") ){
							deptCol=colIndex;
						}
						if( name.contains("item") ){
							itemCol=colIndex;
						}
						if( name.contains( "po #" ) ){
							poCol = colIndex;
						}
						if( name.contains( "color name" ) ){
							colorNameCol = colIndex;
						}
						if( name.contains("color #") ){
							colorNumCol=colIndex;
						}
						if( name.contains( "size" ) ){
							sizeCol=colIndex;
						}
						if( name.contains("po qty") ){
							qtyCol=colIndex;
						}
						if( name.contains("carton") ){
							qtyPerCartonCol=colIndex;
						}
					}
					getLog().info( "upcCol[" + upcCol + "], deptCol[" + deptCol + "], " + 
							"itemCol[" + itemCol + "], poCol["+ poCol + "], colorNameCol[" + 
							colorNameCol + "], colorNumCol[" + colorNumCol + "], sizeCol[" + sizeCol + "], "
							+ "qtyCol[" + qtyCol + "], qtyPerCartonCol["+qtyPerCartonCol+"]" );
				}else{
					try{
						String upcCodeVal = getStringValue(row.getCell(upcCol));
						String deptNumberVal = getStringValue(row.getCell(deptCol));
						String itemCodeVal = getStringValue(row.getCell(itemCol)); 
						String poNumberVal = getStringValue(row.getCell(poCol));
						String colorNameVal = getStringValue(row.getCell(colorNameCol));
						String colorNumberVal = getStringValue(row.getCell(colorNumCol)); 
						String sizeVal = getStringValue( row.getCell( sizeCol ) );
						int itemsVal = getIntValue(row.getCell(qtyCol));
						int itemsPerCartonVal = qtyPerCartonCol!=-1?getIntValue(row.getCell(qtyPerCartonCol)):getNewPo().getParent().getMaster().getCommonPurchaseOrderValues().getDefaultItemsByCarton();
						
						if( colorNameVal!=null && 
								colorNameVal.compareTo("")!=0 && 
								itemCodeVal.compareTo("")!=0 && 
								sizeVal!=null && sizeVal.compareTo("")!=0 && 
								upcCodeVal!=null && upcCodeVal.compareTo("")!=0 ){
							Color color = ColorRepo.findByNumber(colorNumberVal);
							Item item = ItemRepo.findByCode(itemCodeVal);
							Size size = null;
							if( poNumberVal.endsWith("11") ){
								size = SizeRepo.findByWaist(Integer.parseInt(sizeVal));
							}else{
								size = SizeRepo.findByWaistInstream(sizeVal);
							}
							
							Upc upc = UpcRepo.findByCode(upcCodeVal);
							
							if( color==null){
								color = new Color();
								color.setName(colorNameVal);
								color.setNumber(colorNumberVal);
								missingInCatalog = true;
								getLog().info( "not found color{"+colorNameVal+"}" );
							}
							if( item==null ){
								item = new Item();
								item.setCode(itemCodeVal);
								missingInCatalog = true;
								getLog().info( "not found item{"+itemCodeVal+"}" );
							}
							if( size==null ){
								size = new Size();
								int waist = Integer.parseInt( sizeVal.substring(0, 2) );
								int inseam = 36;
								if( !poNumberVal.endsWith("11") ){
									inseam = Integer.parseInt( sizeVal.substring(3, 5) );
								}
								size.setWaist(waist);
								size.setHip(30);
								size.setInseam(inseam);
								missingInCatalog = true;
								getLog().info( "not found size{"+sizeVal+"}" );
							}
							if( upc==null ){
								upc = new Upc();
								upc.setColor(color);
								upc.setColorItemCode(color.getName()+"-"+item.getCode());
								upc.setDeleted(false);
								upc.setItem(item);
								upc.setSize(size);
								upc.setUpcCode(upcCodeVal);
								upc.setTimestamp( Calendar.getInstance().getTime() );
								missingInCatalog = true;
								getLog().info( "not found upc{"+upcCodeVal+"}" );
							}	
							
							uploadColor.put(colorNumberVal, color);
							uploadItem.put(itemCodeVal, item);
							uploadSize.put(sizeVal, size);
							uploadUpc.put(upcCodeVal, upc);
							podUpload.add( new PurchaseOrderDetailUpload(upcCodeVal, deptNumberVal, itemCodeVal, poNumberVal, colorNameVal, colorNumberVal, sizeVal, itemsVal, itemsPerCartonVal ) );
						}else{
							break;
						}
					}catch( Exception e1 ){
						getLog().error( "error while reading po details", e1 );
					}
				}
			}
			
			getLog().info( "podUpload.size( " + podUpload.size() + ")" );
			if( missingInCatalog ){
				String message = countMissingCatalogs( uploadColor, uploadItem, uploadSize, uploadUpc );
				MessageBox msg = new MessageBox(getNewPo().getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				msg.setText(Constants.MESSAGE_BOX_DIAG_TITLE.toString());
				msg.setMessage(message);
				if( msg.open() == SWT.YES ){
					if( addMisingCatalogs( uploadColor, uploadItem, uploadSize, uploadUpc ) ){
						e = addUploadedPurchaseOrder( uploadColor, uploadItem, uploadSize, uploadUpc, podUpload );
					}else{
						e =new Exception("there was a problem adding the catalogs.");
					}
				}else{
					e = new Exception( "there are missing catalogs that need to be added before" );
				}
			}else{
				e = addUploadedPurchaseOrder( uploadColor, uploadItem, uploadSize, uploadUpc, podUpload );
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			getLog().error(e1);
			e = e1;
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
			getLog().error(e1);
			e = e1;
		} catch (IOException e1) {
			e1.printStackTrace();
			getLog().error(e1);
			e = e1;
		} catch( NullPointerException e1 ){
			e1.printStackTrace();
			getLog().error(e1);
			e = e1;
		} catch (Exception e1) {
			e1.printStackTrace();
			getLog().error(e1);
			e = e1;
		}
		return e;
	}
	private Exception addUploadedPurchaseOrder( HashMap<String, Color> uploadColor, HashMap<String, Item> uploadItem, HashMap<String, Size> uploadSize, HashMap<String, Upc> uploadUpc, List<PurchaseOrderDetailUpload> podUpload) {
		Exception e = null;
		
		getLog().info( "uploadItem.size(" + uploadItem.size() + ")" );
		String poReference = "";
		for (PurchaseOrderDetailUpload podu : podUpload) {
			poReference = podu.getPoNumber();
			if( poReference!=null && poReference.compareTo("")!=0 )
				break;
		}
		
		getNewPo().getOrderNumberTxt().setText( poReference );
		getNewPo().getShipFromTxt().setText( getNewPo().getParent().getMaster().getCommonPurchaseOrderValues().getDefaultShipFrom() );
		getNewPo().getShipToTxt().setText( getNewPo().getParent().getMaster().getCommonPurchaseOrderValues().getDefaultShipTo() );
		getNewPo().getDeptTxt().setText( getNewPo().getParent().getMaster().getCommonPurchaseOrderValues().getDefaultDepartmentNumber() );
		getPoDetailData().clear();
		for (PurchaseOrderDetailUpload podu : podUpload) {
			PODetailData poData = getNewPo().new PODetailData(uploadUpc.get(podu.getUpcCode()), podu.getItems(), podu.getItemsPerCarton(), (poReference.endsWith("0011")?false:true) );
			addPoDetail(poData);
		}
		loadPoDetails();
		
		return e;
	}
	private boolean addMisingCatalogs(HashMap<String, Color> uploadColor, HashMap<String, Item> uploadItem, HashMap<String, Size> uploadSize, HashMap<String, Upc> uploadUpc) throws Exception {
		boolean success = false;
		
		Iterator<Entry<String, Color>> itColor = uploadColor.entrySet().iterator();
		while( itColor.hasNext() ){
			Map.Entry<String, Color> entry = itColor.next();
			if( !(entry.getValue().getId()>0) )
				uploadColor.put( entry.getKey(), ColorRepo.createColor(entry.getValue() ) );
		}
		Iterator<Entry<String, Item>> itItem = uploadItem.entrySet().iterator();
		while( itItem.hasNext() ){
			Map.Entry<String, Item> entry = itItem.next();
			if( !(entry.getValue().getId()>0) )
				uploadItem.put( entry.getKey(), ItemRepo.createItem( entry.getValue() ) );
		}
		Iterator<Entry<String, Size>> itSize = uploadSize.entrySet().iterator();
		while( itSize.hasNext() ){
			Map.Entry<String, Size> entry = itSize.next();
			if( !(entry.getValue().getId()>0) )
				uploadSize.put( entry.getKey(), SizeRepo.createSize(entry.getValue() ) );
		}
		Iterator<Entry<String, Upc>> itUpc = uploadUpc.entrySet().iterator();
		while( itUpc.hasNext() ){
			Map.Entry<String,Upc> entry = itUpc.next();
			Upc upc = entry.getValue();
			upc.setColor(ColorRepo.findByNumber(upc.getColor().getNumber()));
			upc.setItem(ItemRepo.findByCode(upc.getItem().getCode()));
			upc.setSize(SizeRepo.findByWaistInstream(String.valueOf(upc.getSize().getWaist()+"x"+upc.getSize().getInseam())));
			if( !(entry.getValue().getId()>0) )
				uploadUpc.put(entry.getKey(), UpcRepo.createUpc( entry.getValue() ) );
		}
		success = true;
		return success;
	}
	
	@Override
	public void displayValues() {
		getLog().info( "loadValues method" );
		getNewPo().getItemsCbx().removeAll();
		List<Item> items = getNewPo().getUslcJpaManager().getItems();
		for (Item item : items) {
			getNewPo().getItemsCbx().add( String.valueOf( item.getCode() ) );
			getNewPo().getItemsCbx().setData( String.valueOf( item.getCode() ), item );
		}
		
		Calendar cal = Calendar.getInstance();
		getNewPo().getTimestampTxt().setText( getNewPo().getMaster().getSimpleDateFormat().format( cal.getTime() ) );
		getNewPo().getCartonTxt().setText("0");
		try {
			getNewPo().getShipFromTxt().setText( getNewPo().getParent().getMaster().getCommonPurchaseOrderValues().getDefaultShipFrom() );
			getNewPo().getShipToTxt().setText( getNewPo().getParent().getMaster().getCommonPurchaseOrderValues().getDefaultShipTo() );
			getNewPo().getDeptTxt().setText( getNewPo().getParent().getMaster().getCommonPurchaseOrderValues().getDefaultDepartmentNumber() );
		} catch (NullPointerException e) {
			getLog().error( "Error at loadVaues method", e);
		}
	}

	@Override
	public void clean() {
		poDetailData = null;
		getNewPo().getShipFromTxt().setText("");
		getNewPo().getShipToTxt().setText("");
		getNewPo().getCartonTxt().setText("0");
		getNewPo().getDeptTxt().setText("");
		getNewPo().getItemsCbx().select(-1);
		displayValues();
		loadPoDetails();
	}

	@Override
	public void refreshFormData() {
		clean();
		displayValues();
		getNewPo().layout();
	}
	
	public List<PODetailData> getPoDetailData(){
		if( poDetailData == null ){
			poDetailData = new ArrayList<PODetailData>();
		}
		return poDetailData;
	}

	private String getStringValue( Cell cell ) throws Exception{
		String val = "";
		if( cell.getCellType()==Cell.CELL_TYPE_NUMERIC ){
			DecimalFormat decimalFormat=new DecimalFormat("#");
			val = decimalFormat.format( cell.getNumericCellValue() );
		}else if( cell.getCellType()==Cell.CELL_TYPE_STRING ){
			val = cell.getStringCellValue();
		}
		return val;
	}
	private int getIntValue( Cell cell ) throws Exception{
		int val = 0;
		if( cell.getCellType()==Cell.CELL_TYPE_NUMERIC ){
			val = Integer.parseInt( getStringValue( cell ) );
		}else if( cell.getCellType()==Cell.CELL_TYPE_STRING ){
			val = Integer.parseInt( cell.getStringCellValue() );
		}
		return val;
	}
	
	private String countMissingCatalogs(HashMap<String, Color> uploadColor,
			HashMap<String, Item> uploadItem,
			HashMap<String, Size> uploadSize, HashMap<String, Upc> uploadUpc) {
		String msg = "there are ";
		int missingColors = 0;
		int missingItems = 0;
		int missingSizes = 0;
		int missindUpcs = 0;
		
		Iterator<Entry<String,Color>> itColor = uploadColor.entrySet().iterator();
		while( itColor.hasNext() ){
			Map.Entry<String, Color> entry = itColor.next();
			if( !(entry.getValue().getId()>0) ){
				missingColors++;
			}
		}
		if(missingColors>0)msg = msg + missingColors + " colors, ";
		Iterator<Entry<String,Item>> itItem = uploadItem.entrySet().iterator();
		while( itItem.hasNext() ){
			Map.Entry<String, Item> entry = itItem.next();
			if( !( entry.getValue().getId()>0 ) ){
				missingItems++;
			}
		}
		if(missingItems>0)msg = msg + missingItems + " items, ";
		Iterator<Entry<String,Size>> itSize = uploadSize.entrySet().iterator();
		while( itSize.hasNext() ){
			Map.Entry<String, Size> entry = itSize.next();
			if( !( entry.getValue().getId()>0 ) ){
				missingSizes++;
			}
		}
		if(missingSizes>0)msg = msg + missingSizes + " sizes, ";
		Iterator<Entry<String,Upc>> itUpc = uploadUpc.entrySet().iterator();
		while( itUpc.hasNext() ){
			Map.Entry<String, Upc> entry = itUpc.next();
			if( !( entry.getValue().getId()>0 ) ){
				missindUpcs++;
			}
		}
		if(missindUpcs>0)msg = msg + missindUpcs + " upc ";
		
		StringBuilder b = new StringBuilder(msg);
		try{b.replace(msg.lastIndexOf(","), msg.lastIndexOf(",") + 1, "" );}catch(Exception e){}
		msg = b.toString();
		msg = msg + "missing, would you like to add them before?";
		
		return msg;
	}
}
