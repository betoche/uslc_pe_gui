package com.uslc.po.gui.master;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.Collections;
import java.util.HashMap;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRFontNotFoundException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.jasperassistant.designer.viewer.IReportViewer;
import com.jasperassistant.designer.viewer.ViewerComposite;
import com.uslc.po.gui.logic.ClientLogic;
import com.uslc.po.gui.master.MasterLeftComposite.PurchaseOrderStatus;
import com.uslc.po.gui.master.catalog.FormCenterMaster;
import com.uslc.po.gui.master.interfaces.MasterCompositeInterface;
import com.uslc.po.gui.util.ImageUtils;
import com.uslc.po.jpa.comparator.PackageDetailComparator;
import com.uslc.po.jpa.comparator.PurchaseOrderDetailComparator;
import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;
import com.uslc.po.jpa.entity.Upc;
import com.uslc.po.jpa.util.Constants;

public class PackingDetailComposite extends FormCenterMaster implements MasterCompositeInterface {
	private Logger log = null;
	
	private PurchaseOrderStatus po = null;
	
	private Label titleLbl = null;
	private Table packingDetailTbl = null;
	private ViewerComposite ticketReportViewer = null;
	private ViewerComposite polybagReportViewer = null;
	private GridData gdHorizontal = null;
	
	public PackingDetailComposite( MasterCenterComposite composite ){
		super( composite, SWT.NONE );
		initComposite();
	}
	
	private void initComposite(){
		//getLog().info("initComposite");
		FormData data = new FormData( 550, 620);
		setLayoutData(data);
		
		setLayout( new GridLayout(2, false) );
		
		getTitleLbl();
		getPackingDetailTbl();
		getTicketReportViewer();
		getPolybagReportViewer();
	}
	
	public Label getTitleLbl() {
		if( titleLbl == null ){
			titleLbl = new Label(this, SWT.NONE);
			titleLbl.setText("packing details");
			titleLbl.setAlignment( SWT.RIGHT );
			titleLbl.setLayoutData(getGdHorizontal());
			
			Label hl = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			hl.setLayoutData((Object)getGdHorizontal());
		}
		return titleLbl;
	}
	public Table getPackingDetailTbl() {
		if( packingDetailTbl == null ){
			packingDetailTbl = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION );
			packingDetailTbl.setHeaderVisible(true);
			Font f = packingDetailTbl.getFont();
			
			packingDetailTbl.setFont( getMaster().getSystemVariables().getSmallFont() );
			packingDetailTbl.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					showTicket();
				}
			});
			
			TableColumn upc = new TableColumn(packingDetailTbl, SWT.NONE);
			upc.setAlignment(SWT.CENTER);
			upc.setText( "upc" );
			upc.setWidth( 106 );
			TableColumn colorName = new TableColumn(packingDetailTbl, SWT.NONE);
			colorName.setText( "color name" );
			colorName.setWidth( 89 );
			TableColumn colorItem = new TableColumn(packingDetailTbl, SWT.NONE);
			colorItem.setText( "" );
			colorItem.setWidth( 106 );
			TableColumn colorNumber = new TableColumn(packingDetailTbl, SWT.NONE);
			colorNumber.setText( "color #" );
			colorNumber.setWidth( 65 );
			TableColumn sku = new TableColumn(packingDetailTbl, SWT.NONE);
			sku.setText( "sku" );
			sku.setWidth( 40 );
			TableColumn size = new TableColumn(packingDetailTbl, SWT.NONE);
			size.setText( "size" );
			size.setWidth( 63 );
			TableColumn quantity = new TableColumn(packingDetailTbl, SWT.NONE);
			quantity.setText( "qty" );
			quantity.setWidth( 39 );
			TableColumn ready = new TableColumn( packingDetailTbl, SWT.None );
			ready.setText( "ready" );
			ready.setWidth( 63 );
			
			GridData gd = new GridData( GridData.FILL_HORIZONTAL );
			gd.heightHint = 180;
			gd.verticalAlignment = SWT.TOP;
			gd.horizontalSpan=2;
			packingDetailTbl.setLayoutData(gd);
			
			Label hl = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			hl.setLayoutData(getGdHorizontal());
		}
		return packingDetailTbl;
	}
	public ViewerComposite getTicketReportViewer() {
		if( ticketReportViewer == null ){
			ticketReportViewer = new ViewerComposite(this, SWT.NONE);
			GridData gd = new GridData( GridData.FILL_HORIZONTAL );
			gd.heightHint = 300;
			gd.verticalAlignment = SWT.TOP;
			ticketReportViewer.setLayoutData(gd);
		}
		return ticketReportViewer;
	}
	public ViewerComposite getPolybagReportViewer() {
		if( polybagReportViewer == null ){
			polybagReportViewer = new ViewerComposite(this, SWT.NONE);
			GridData gd = new GridData();
			//gd.heightHint = 300;
			gd.widthHint=170;
			gd.verticalAlignment = SWT.TOP;
			polybagReportViewer.setLayoutData(gd);
			
			Label hl = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			hl.setLayoutData(getGdHorizontal());
		}
		return polybagReportViewer;
	}
	public GridData getGdHorizontal(){
		if( gdHorizontal == null ){
			gdHorizontal = new GridData(GridData.FILL, GridData.FILL, true, false);
			gdHorizontal.horizontalSpan=2;
		}
		return gdHorizontal;
	}

	public void displayPackingDetails( PurchaseOrderStatus po ) {
		clean();
		this.po = po;
		
		getMaster().getMasterCenter().showComposite(this);
		
		Collections.sort( po.getPo().getPurchaseOrderDetails(), new PurchaseOrderDetailComparator() );
		for ( PurchaseOrderDetail pod : po.getPo().getPurchaseOrderDetails() ) {
			Upc upc = pod.getUpc();
			//orderList( pod.getPackingDetails() );
			Collections.sort( pod.getPackingDetails(), new PackageDetailComparator());
			
			for( PackingDetail pd : pod.getPackingDetails() ){
				
				TableItem row = new TableItem(getPackingDetailTbl(), SWT.READ_ONLY);
				String[] texts = {upc.getUpcCode(),
						upc.getColor().getName(),
						( upc.getColor().getName()+"-"+upc.getItem().getCode() ), 
						upc.getColor().getNumber(), 
						String.valueOf( pd.getSku() ), 
						( upc.getSize().getWaist() + " x " + upc.getSize().getInseam() ), 
						String.valueOf( pd.getQuantity() ),
						String.valueOf( ClientLogic.getNumberOfScannedItems( pd.getCarton() ) )};
				row.setText(texts);
				row.setData( pd );
				if( pd.getQuantity() == ClientLogic.getNumberOfScannedItems( pd.getCarton() ) ) {
					row.setBackground( getMaster().getSystemVariables().getReadyColor() );
				}else{
					row.setBackground( getMaster().getSystemVariables().getMissingColor() );
				}
			}
		}
		
		for( TableColumn col : getPackingDetailTbl().getColumns() ) {
			col.pack();
		}
		
		layout();
	}
	public void hide(){
		clean();
		this.setParent( getParent().getMaster().getHiddenShell() );
		this.setVisible(false);
	}
	private void clean() {
		getPackingDetailTbl().removeAll();
		try {
			getTicketReportViewer().getReportViewer().setDocument( getMaster().getTicketPrinter().getLabelJp(null) );
			getTicketReportViewer().getReportViewer().setZoomMode(IReportViewer.ZOOM_MODE_FIT_HEIGHT);
			
			getPolybagReportViewer().getReportViewer().setDocument(getMaster().getTicketPrinter().getStickerLabelJp(null));
			getPolybagReportViewer().getReportViewer().setZoomMode(IReportViewer.ZOOM_MODE_FIT_WIDTH);
		} catch (JRException e) {
			e.printStackTrace();
		}
		//getReportViewer().getReportViewer().setDocument(new JasperPrint());
	}
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger(PackingDetailComposite.class);
			PropertyConfigurator.configure("log4j.properties");
		}
		return log;
	}
	private void showTicket(){
		getLog().info( "showTicket method called" );
		TableItem[] items = getPackingDetailTbl().getSelection();
		PackingDetail pd = null;
		for (TableItem it : items) {
			pd = (PackingDetail)it.getData();
		}
		try{
			
			if( pd!=null ){
				getPolybagReportViewer().getReportViewer().setDocument(getMaster().getTicketPrinter().getStickerLabelJp(pd));
				getPolybagReportViewer().getReportViewer().setZoomMode(IReportViewer.ZOOM_MODE_FIT_WIDTH);
				
				getTicketReportViewer().getReportViewer().setDocument(getMaster().getTicketPrinter().getLabelJp(pd));
				getTicketReportViewer().getReportViewer().setZoomMode(IReportViewer.ZOOM_MODE_FIT_HEIGHT);
				
			}else{
				getPolybagReportViewer().getReportViewer().setDocument(getMaster().getTicketPrinter().getStickerLabelJp(null));
				getTicketReportViewer().getReportViewer().setDocument(getMaster().getTicketPrinter().getStickerLabelJp(null));
			}
		}catch( Exception e ){
			getLog().error( "error generating the report", e );
			MessageBox box = new MessageBox( getShell(), SWT.ICON_ERROR);
			box.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
			box.setMessage( "error while creating the report\n" + e.getMessage() );
		}
	}
	public void printTicket(){
		TableItem[] items = getPackingDetailTbl().getSelection();
		PackingDetail pd = null;
		for (TableItem it : items) {
			pd = (PackingDetail)it.getData();
		}
		try{
			
			if( pd!=null ){
				JasperPrintManager.printReport( getMaster().getTicketPrinter().getLabelJp(pd), false );
			}
		}catch( Exception e ){
			getLog().error( "error", e );
		}
	}
	
	public void exportToImage(){
		getLog().info( "exportToImage method called" );
		TableItem[] items = getPackingDetailTbl().getSelection();
		PackingDetail pd = null;
		for (TableItem it : items) {
			pd = (PackingDetail)it.getData();
		}
		try{
			if( pd!=null ){
				JasperPrint print = getMaster().getTicketPrinter().getLabelJp(pd);
				
				PrinterJob job = PrinterJob.getPrinterJob();
				
				PageFormat pf = job.defaultPage();
			    Paper paper = pf.getPaper();
			    double margin = 0;
			    paper.setImageableArea(margin, margin, paper.getWidth()-2.0, paper.getHeight()-2.0);
			    pf.setPaper(paper);
			    Book pBook = new Book();
			    pBook.append(new MyPrintable(), pf);
			    job.setPageable(pBook);
			    
			    //job.setPrintable(new MyPrintable(), pf);
			    
				/* Create an array of PrintServices */
				PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
				int selectedService = 0;
				/* Scan found services to see if anyone suits our needs */
				for(int i = 0; i < services.length;i++){
					if(services[i].getName().toUpperCase().contains("Your printer's name")){
						/*If the service is named as what we are querying we select it */
						selectedService = i;
					}
				}
				job.setPrintService(services[selectedService]);
				PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
				//MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
				//getLog().info( "mediaSizeName["+mediaSizeName+"]" );
				//printRequestAttributeSet.add(mediaSizeName);
				printRequestAttributeSet.add(new Copies(1));
				
				JRPrintServiceExporter exporter;
				exporter = new JRPrintServiceExporter();
				getLog().info( print.getLeftMargin() + " - " + print.getTopMargin() + " - " + print.getPageWidth() + " - " + print.getPageHeight() );
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				/* We set the selected service and pass it as a paramenter */
				exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
				exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
				exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
				exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.TRUE);
				exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
				exporter.setParameter(JRPrintServiceExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				exporter.exportReport();
			}else{
				getTicketReportViewer().getReportViewer().setDocument( getMaster().getTicketPrinter().getLabelJp(null));
			}
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	class MyPrintable implements Printable {
		  public int print(Graphics g, PageFormat pf, int pageIndex) {
		    if (pageIndex != 0)
		      return NO_SUCH_PAGE;
		    Graphics2D g2 = (Graphics2D) g;
		    g2.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 36));
		    g2.setPaint(Color.black);
		    g2.drawString("www.java2s.com", 100, 100);
		    Rectangle2D outline = new Rectangle2D.Double(pf.getImageableX(), pf.getImageableY(), pf
		        .getImageableWidth(), pf.getImageableHeight());
		    g2.draw(outline);
		    return PAGE_EXISTS;
		  }
		}

	@Override
	public InfoForm getInfoForm() {
		String title = "pack detail "+ po.getPo().getReferenceNumber();
		String desc = "";
		return new InfoForm(title, desc, null);
	}

	public void setPurchaseOrder(PurchaseOrderStatus po) {
		this.po = po;
	}
}
