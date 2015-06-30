package com.uslc.pe.gui.master;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.MessageBox;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.uslc.pe.gui.client.ClientPurchaseOrderSumary;
import com.uslc.pe.gui.client.ClientPurchaseOrderSumary.PurchaseOrderStats;
import com.uslc.pe.gui.logic.ClientLogic;
import com.uslc.pe.gui.master.catalog.FormCenterMaster;
import com.uslc.pe.gui.report.PoComparativeByColorItemSize;
import com.uslc.pe.gui.report.SizeDistributionByColorReport;
import com.uslc.pe.gui.report.PoComparativeByColorItemSize.ItemColorSizeCount;
import com.uslc.pe.gui.report.SizeDistributionByColorReport.ColorScanDistribution;
import com.uslc.pe.gui.report.SizeDistributionByColorReport.SizeScanDistribution;
import com.uslc.pe.jpa.comparator.PackageDetailComparator;
import com.uslc.pe.jpa.entity.PackingDetail;
import com.uslc.pe.jpa.entity.PurchaseOrder;
import com.uslc.pe.jpa.entity.PurchaseOrderDetail;
import com.uslc.pe.jpa.entity.Size;
import com.uslc.pe.jpa.entity.Upc;
import com.uslc.pe.jpa.entity.User;
import com.uslc.pe.jpa.logic.PurchaseOrderRepo;
import com.uslc.pe.jpa.logic.UserRepo;
import com.uslc.pe.jpa.util.Constants;

public class ReportsManagerComposite extends FormCenterMaster {
	private Logger log = null;
	private Combo purchaseOrderCbx;
	private Combo userCbx;
	private MessageBox questionBox = null;
	
	public ReportsManagerComposite( MasterCenterComposite composite ){
		this( composite, SWT.NONE );
		
		getLog().info( "ReportsManagerComposite constructor" );
		FormData data = new FormData( 520, 430 );
		setLayoutData(data);
		
		try {
			loadValues();
		} catch (CommunicationsException e) {
			getLog().error( "error", e );
		} catch (ConnectException e) {
			getLog().error( "error", e );
		}
	}
	
	public void loadValues() throws CommunicationsException, ConnectException {
		List<PurchaseOrder> pos = PurchaseOrderRepo.findAll();
		List<User> users = UserRepo.findAllClients(true);
		
		getPurchaseOrderCbx().removeAll();
		for( PurchaseOrder po : pos ){
			getPurchaseOrderCbx().add( po.getReferenceNumber() );
			getPurchaseOrderCbx().setData( po.getReferenceNumber(), po );
		}
		
		getUserCbx().removeAll();
		for (User user : users) {
			getUserCbx().add( user.getFirstName() + " " + user.getLastName() );
			getUserCbx().setData( user.getFirstName() + " " + user.getLastName(), user );
		}
	}
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ReportsManagerComposite( MasterCenterComposite parent, int style ) {
		super(parent, style);
		setLayout(new GridLayout(4, false));
		
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		new Label(this, SWT.NONE);
		
		Label label_1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));
		
		Label lblCentralReport = new Label(this, SWT.NONE);
		lblCentralReport.setText("reports");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(5, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Label lblPo = new Label(composite, SWT.NONE);
		lblPo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPo.setText("po:");
		
		purchaseOrderCbx = new Combo(composite, SWT.READ_ONLY);
		GridData gd_purchaseOrderCbx = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_purchaseOrderCbx.widthHint = 150;
		purchaseOrderCbx.setLayoutData(gd_purchaseOrderCbx);

		
		Label label = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
		gd_label.widthHint = 30;
		label.setLayoutData(gd_label);
		
		Link shippingListLnk = new Link(composite, SWT.NONE);
		shippingListLnk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				generateEBShippingList();
			}
		});
		shippingListLnk.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		shippingListLnk.setText("<a>- eb shipping list</a>");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Link varianceReportLnk = new Link(composite, SWT.NONE);
		varianceReportLnk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					generateVarianceReport();
				} catch (Exception e1) {
					getLog().error( "error", e1 );
				}
			}
		});
		varianceReportLnk.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		varianceReportLnk.setText("<a>- eb variance report</a>");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label label_2 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 7, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblUser = new Label(composite, SWT.NONE);
		lblUser.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUser.setText("user:");
		
		userCbx = new Combo(composite, SWT.READ_ONLY);
		userCbx.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label_3 = new Label(composite, SWT.SEPARATOR | SWT.VERTICAL);
		GridData gd_label_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
		gd_label_3.widthHint = 30;
		label_3.setLayoutData(gd_label_3);
		
		Link link_2 = new Link(composite, SWT.NONE);
		link_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		link_2.setText("<a>- po distribution by user</a>");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label label_4 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 5, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hide();
			}
		});
		btnCancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnCancel.setText("cancel");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
	}
	public PurchaseOrder getSelectedPurchaseOrder(){
		PurchaseOrder po = null;
		
		try{
			po = (PurchaseOrder)getPurchaseOrderCbx().getData(getPurchaseOrderCbx().getItem(getPurchaseOrderCbx().getSelectionIndex()));
		}catch( Exception e ){
			getLog().error( "error", e );
		}
		
		return po;
	}
	public void generateEBShippingList() {
		if( getSelectedPurchaseOrder()!=null ){
			try{
				Workbook workbook = new XSSFWorkbook();
				Font arialBold14 = workbook.createFont();
				Font arialBlackBold10 = workbook.createFont();
				Font calibriBold11 = workbook.createFont();
				Font arialRedBold10 = workbook.createFont();
				Font arialBold10 = workbook.createFont();
				Font arialBold9 = workbook.createFont();
				Font calibri10 = workbook.createFont();
				
				arialBold14.setFontHeightInPoints((short)14);
				arialBold14.setColor(IndexedColors.BLACK.getIndex());
				arialBold14.setFontName( "Arial Black" );
				arialBold14.setItalic(false);
				arialBold14.setBoldweight( Font.BOLDWEIGHT_BOLD );
				
				arialBlackBold10.setFontHeightInPoints((short)10);
				arialBlackBold10.setColor( IndexedColors.BLACK.getIndex() );
				arialBlackBold10.setFontName( "Arial Black" );
				arialBlackBold10.setItalic(false);
				arialBlackBold10.setBoldweight( Font.BOLDWEIGHT_BOLD );
				
				calibriBold11.setFontHeightInPoints((short)11);
				calibriBold11.setColor( IndexedColors.BLACK.getIndex() );
				calibriBold11.setFontName("Calibri");
				calibriBold11.setItalic( false );
				calibriBold11.setBoldweight( Font.BOLDWEIGHT_BOLD );
				
				arialRedBold10.setFontHeightInPoints((short)10);
				arialRedBold10.setColor( IndexedColors.RED.getIndex() );
				arialRedBold10.setFontName( "Arial Black" );
				arialRedBold10.setItalic(false);
				arialRedBold10.setBoldweight( Font.BOLDWEIGHT_BOLD );
				
				arialBold10.setFontHeightInPoints((short)10);
				arialBold10.setColor( IndexedColors.BLACK.getIndex() );
				arialBold10.setFontName( "Arial" );
				arialBold10.setItalic(false);
				arialBold10.setBoldweight( Font.BOLDWEIGHT_BOLD );
				
				arialBold9.setFontHeightInPoints((short)9);
				arialBold9.setColor( IndexedColors.BLACK.getIndex() );
				arialBold9.setFontName( "Arial" );
				arialBold9.setItalic(false);
				arialBold9.setBoldweight( Font.BOLDWEIGHT_BOLD );
				
				calibri10.setFontHeightInPoints((short)10);
				calibri10.setColor( IndexedColors.BLACK.getIndex() );
				calibri10.setFontName( "Calibri" );
				calibri10.setItalic(false);
				
				String sheetName = getSelectedPurchaseOrder().getReferenceNumber().substring( 2, 8 );
				Sheet sheet = workbook.createSheet( sheetName );
				
				int rowIndex = 0;
				int cellIndex = 0;
				String folderPath = "reports/eb_shipping_list_reports/";
				String fileName = folderPath + getSelectedPurchaseOrder().getReferenceNumber()+"_eb_shipping_list.xlsx";
				File folder = new File( folderPath );
				
				if( !folder.exists() ){
					folder.mkdir();
				}
				
				
				//	DOCUMENT TITLE
				Row row = sheet.createRow(rowIndex);
				Cell cel = row.createCell(cellIndex);
				
				cel.setCellValue( "PACKING LIST FROM USLC APPAREL S.A." );
				
				CellStyle titleStyle = workbook.createCellStyle();
				titleStyle.setFont( arialBold14 );
				titleStyle.setAlignment( CellStyle.ALIGN_CENTER );
				cel.setCellStyle( titleStyle );
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex+9));
				
				rowIndex++;
				Row addressRow = sheet.createRow(rowIndex);
				Cell addressCell = addressRow.createCell( cellIndex );
				addressCell.setCellValue( "DE DONDE FUE LA PEPSI 1 1/2 AL LAGO , ZONA FRANCA INDEX" );
				
				CellStyle addressStyle = workbook.createCellStyle();
				addressStyle.setFont(arialBlackBold10);
				addressStyle.setAlignment( CellStyle.ALIGN_CENTER );
				addressCell.setCellStyle( addressStyle );
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex+9));
				
				rowIndex++;
				Row contactRow = sheet.createRow(rowIndex);
				Cell contactCell = contactRow.createCell( cellIndex );
				contactCell.setCellValue( "CONTACT PERSON: ALFREDO FERNANDEZ / TEL: 22481700" );
				
				CellStyle contactStyle = workbook.createCellStyle();
				contactStyle.setFont(calibriBold11);
				contactStyle.setAlignment( CellStyle.ALIGN_CENTER );
				contactCell.setCellStyle( contactStyle );
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex+9));
				
				rowIndex++;
				Row reportTitleRow = sheet.createRow(rowIndex);
				Cell reportTitleCell = reportTitleRow.createCell( cellIndex );
				reportTitleCell.setCellValue( "PACKING LIST / DETAIL BY CARTON / CARTON SIZE 23x17x5.5" );
				
				Cell poRefNumCell = reportTitleRow.createCell(cellIndex+11);
				poRefNumCell.setCellValue( getSelectedPurchaseOrder().getReferenceNumber() );
				
				CellStyle reportTitleStyle = workbook.createCellStyle();
				reportTitleStyle.setFont(arialRedBold10);
				reportTitleStyle.setAlignment( CellStyle.ALIGN_CENTER );
				reportTitleCell.setCellStyle( reportTitleStyle );
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex+9));
				
				//	PO DETAIL
				rowIndex++;
				Row chartColumnTitleRow = sheet.createRow( rowIndex );
				
				CellStyle chartColumnStyle = workbook.createCellStyle();
				chartColumnStyle.setFont(arialBold10);
				chartColumnStyle.setAlignment( CellStyle.ALIGN_CENTER );
				chartColumnStyle.setBorderBottom( CellStyle.BORDER_THIN );
				chartColumnStyle.setBorderTop( CellStyle.BORDER_THIN );
				chartColumnStyle.setBorderLeft( CellStyle.BORDER_THIN );
				chartColumnStyle.setBorderRight( CellStyle.BORDER_THIN );
				
				String[] colNames = { "UPC", "DEPT", "ITEM CODE", "PO #", "COLOR NAME", "", "COLOR #", "SKU", "SIZE", "QTY" };
				
				List<PackingDetail> packingDetailList = new ArrayList<PackingDetail>();
				
				for( PurchaseOrderDetail pod : getSelectedPurchaseOrder().getPurchaseOrderDetails() ){
					for( PackingDetail pd : pod.getPackingDetails() ){
						packingDetailList.add(pd);
					}
				}
				
				Collections.sort( packingDetailList, new PackageDetailComparator() );
				
				for( int i = 0 ; i < 10 ; i++ ){
					Cell cell = chartColumnTitleRow.createCell( cellIndex+i );
					cell.setCellValue( colNames[i] );
					cell.setCellStyle( chartColumnStyle );
				}
				
				CellStyle dataTableCellStyle = workbook.createCellStyle();
				dataTableCellStyle.setFont(arialBold9);
				dataTableCellStyle.setAlignment( CellStyle.ALIGN_CENTER );
				dataTableCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
				dataTableCellStyle.setBorderTop( CellStyle.BORDER_THIN );
				dataTableCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
				dataTableCellStyle.setBorderRight( CellStyle.BORDER_THIN );
				
				CellStyle numbersCellStyle = workbook.createCellStyle();
				numbersCellStyle.setFont(arialBold9);
				numbersCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				
				CellStyle yellowCellStyle = workbook.createCellStyle();
				yellowCellStyle.setFont(arialBold10);
				yellowCellStyle.setAlignment( CellStyle.ALIGN_CENTER );
				yellowCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
				yellowCellStyle.setBorderTop( CellStyle.BORDER_THIN );
				yellowCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
				yellowCellStyle.setBorderRight( CellStyle.BORDER_THIN );
				yellowCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
				yellowCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
				int totalScannedItems = 0;
				int totalBoxes = 0;
				for( PackingDetail pd : packingDetailList ) {
					rowIndex++;
					Row dataRow = sheet.createRow(rowIndex);
					Upc upc = pd.getPurchaseOrderDetail().getUpc();
					
					Size size = upc.getSize();
					String sizeStr = String.valueOf( size.getWaist() );
					
					if( !pd.getPurchaseOrderDetail().getPurchaseOrder().getReferenceNumber().endsWith( "11" ) ){
						sizeStr = sizeStr + "x" + size.getInseam();
					}
					
					for( int i = 0 ; i < 10 ; i++ ){
						Cell cell = dataRow.createCell( i );
						cell.setCellStyle( dataTableCellStyle );
						
						switch(i) {
							case 0:
								cell.setCellValue( upc.getUpcCode() );
								break;
							case 1:
								cell.setCellValue( pd.getPurchaseOrderDetail().getPurchaseOrder().getDepartmentNumber() );
								break;
							case 2:
								cell.setCellValue( upc.getStyle().getCode() );
								break;
							case 3:
								cell.setCellValue( pd.getPurchaseOrderDetail().getPurchaseOrder().getReferenceNumber() );
								break;
							case 4:
								cell.setCellValue( upc.getColor().getName() );
								break;
							case 5:
								cell.setCellValue( upc.getColorItemCode() );
								break;
							case 6:
								cell.setCellValue( upc.getColor().getNumber() );
								break;
							case 7:
								if( !pd.getDeleted() && pd.getCarton()!=null && !pd.getCarton().getDeleted() ) {
									totalBoxes++;
								}
								if( pd.getPurchaseOrderDetail().getPurchaseOrder().getTotalCartons()<pd.getSku() ){
									dataRow.getCell(7).setCellStyle( yellowCellStyle );
								}
								cell.setCellValue( pd.getSku() );
								break;
							case 8:
								cell.setCellValue( sizeStr );
								break;
							case 9:
								int scannedItems = 0;
								if( !pd.getDeleted() && pd.getCarton()!=null && !pd.getCarton().getDeleted() ){
									scannedItems = pd.getCarton()!=null?ClientLogic.getNumberOfScannedItems( pd.getCarton() ):0;
									totalScannedItems = totalScannedItems + scannedItems;
									if( pd.getQuantity()!=scannedItems ){
										dataRow.getCell(7).setCellStyle( yellowCellStyle );
									}
								}
								cell.setCellValue( scannedItems );
								break;
						}
					}
				}
				
				rowIndex++;
				Row totalChartRow = sheet.createRow(rowIndex);
				Cell cel1 = totalChartRow.createCell(cellIndex);cel1.setCellValue(1);cel1.setCellStyle(numbersCellStyle);
				Cell cel2 = totalChartRow.createCell(cellIndex+1);cel2.setCellValue(2);cel2.setCellStyle(numbersCellStyle);
				Cell cel3 = totalChartRow.createCell(cellIndex+2);cel3.setCellValue(3);cel3.setCellStyle(numbersCellStyle);
				Cell cel4 = totalChartRow.createCell(cellIndex+6);cel4.setCellValue(4);cel4.setCellStyle(numbersCellStyle);
				Cell cel5 = totalChartRow.createCell(cellIndex+7);cel5.setCellValue("TOTAL:");cel5.setCellStyle(numbersCellStyle);
				Cell cel6 = totalChartRow.createCell(cellIndex+8);cel6.setCellValue(5);cel6.setCellStyle(numbersCellStyle);
				Cell cel7 = totalChartRow.createCell(cellIndex+9);cel7.setCellValue(totalScannedItems);cel7.setCellStyle(dataTableCellStyle);
				rowIndex++;
				Cell cel8 = sheet.createRow(rowIndex).createCell(cellIndex+9);cel8.setCellValue(totalBoxes);cel8.setCellStyle(dataTableCellStyle);
				rowIndex++;
				rowIndex++;
				
				//	SUMMARY
				Row summaryTitleRow = sheet.createRow(rowIndex);
				Cell summaryColorCell = summaryTitleRow.createCell(cellIndex);
				summaryColorCell.setCellValue( "COLOR" );
				summaryColorCell.setCellStyle( contactStyle );
				Cell summaryUnitsCell = summaryTitleRow.createCell( cellIndex + 2 );
				summaryUnitsCell.setCellValue( "UNITS" );
				summaryUnitsCell.setCellStyle( contactStyle );
				Cell summaryBoxesCell = summaryTitleRow.createCell( cellIndex + 3 );
				summaryBoxesCell.setCellValue( "BOXES" );
				summaryBoxesCell.setCellStyle( contactStyle );
				
				ClientPurchaseOrderSumary cpos = new ClientPurchaseOrderSumary(getSelectedPurchaseOrder());
				Iterator<Entry<String,PurchaseOrderStats>> it = cpos.getPoInfo().entrySet().iterator();
				
				CellStyle calibriBordered10 = workbook.createCellStyle();
				calibriBordered10.setFont(calibri10);
				calibriBordered10.setAlignment( CellStyle.ALIGN_CENTER );
				calibriBordered10.setBorderBottom( CellStyle.BORDER_THIN );
				calibriBordered10.setBorderTop( CellStyle.BORDER_THIN );
				calibriBordered10.setBorderLeft( CellStyle.BORDER_THIN );
				calibriBordered10.setBorderRight( CellStyle.BORDER_THIN );
				
				int totalSummaryQty = 0;
				int totalSummaryBoxes = 0;
				while( it.hasNext() ) {
					Map.Entry<String,PurchaseOrderStats> entry = it.next();
					rowIndex++;
					Row summaryRow = sheet.createRow( rowIndex );
					Cell dataColorCell = summaryRow.createCell(cellIndex);
					dataColorCell.setCellValue( entry.getKey() );
					dataColorCell.setCellStyle( dataTableCellStyle );
					Cell dataUnitsCell = summaryRow.createCell(cellIndex+2);
					dataUnitsCell.setCellValue( entry.getValue().getWorkedQty() );
					dataUnitsCell.setCellStyle( calibriBordered10 );
					Cell dataBoxesCell = summaryRow.createCell(cellIndex+3);
					dataBoxesCell.setCellValue( entry.getValue().getWorkedBoxes() );
					dataBoxesCell.setCellStyle( calibriBordered10 );
					
					totalSummaryQty = totalSummaryQty + entry.getValue().getWorkedQty();
					totalSummaryBoxes = totalSummaryBoxes + entry.getValue().getWorkedBoxes();
				}
				
				rowIndex++;
				Row summaryTotalRow = sheet.createRow(rowIndex);
				Cell summaryTotalColorCell = summaryTotalRow.createCell(cellIndex);
				summaryTotalColorCell.setCellValue( "TOTAL BY PO" );
				summaryTotalColorCell.setCellStyle( contactStyle );
				Cell summaryTotalUnitsCell = summaryTotalRow.createCell( cellIndex + 2 );
				summaryTotalUnitsCell.setCellValue( totalSummaryQty );
				summaryTotalUnitsCell.setCellStyle( contactStyle );
				Cell summaryTotalBoxesCell = summaryTotalRow.createCell( cellIndex + 3 );
				summaryTotalBoxesCell.setCellValue( totalSummaryBoxes );
				summaryTotalBoxesCell.setCellStyle( contactStyle );
				
				for( int i = 0 ; i < 10 ; i++ ){
					sheet.autoSizeColumn(i);
				}
				
				//	SCAN BY COLOR AND SIZE
				CellStyle colorItemTitleStyle = workbook.createCellStyle();
				colorItemTitleStyle.setFont(arialBold9);
				
				CellStyle yellowSizeTitleCellStyle = workbook.createCellStyle();
				yellowSizeTitleCellStyle.setFont(calibri10);
				yellowSizeTitleCellStyle.setAlignment( CellStyle.ALIGN_CENTER );
				yellowSizeTitleCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
				yellowSizeTitleCellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
				yellowSizeTitleCellStyle.setBorderBottom( CellStyle.BORDER_MEDIUM );
				yellowSizeTitleCellStyle.setBorderTop( CellStyle.BORDER_MEDIUM );
				yellowSizeTitleCellStyle.setBorderLeft( CellStyle.BORDER_MEDIUM );
				yellowSizeTitleCellStyle.setBorderRight( CellStyle.BORDER_MEDIUM );
				
				HSSFPalette palette = (new HSSFWorkbook()).getCustomPalette();
				palette.setColorAtIndex((short)45, (byte) 255, (byte) 217, (byte) 239 );
				palette.setColorAtIndex((short)50, (byte)102, (byte)204, (byte)255 );
				
				CellStyle lightBlueSizeCellStyle = workbook.createCellStyle();
				lightBlueSizeCellStyle.setFont( calibri10 );
				lightBlueSizeCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				lightBlueSizeCellStyle.setFillForegroundColor( (short)50 );
				lightBlueSizeCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				lightBlueSizeCellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
				lightBlueSizeCellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
				lightBlueSizeCellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
				lightBlueSizeCellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
				
				CellStyle countMediumBorderedCellStyle = workbook.createCellStyle();
				countMediumBorderedCellStyle.setFont(calibri10);
				countMediumBorderedCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				countMediumBorderedCellStyle.setBorderBottom( CellStyle.BORDER_MEDIUM );
				countMediumBorderedCellStyle.setBorderTop( CellStyle.BORDER_MEDIUM );
				countMediumBorderedCellStyle.setBorderLeft( CellStyle.BORDER_MEDIUM );
				countMediumBorderedCellStyle.setBorderRight( CellStyle.BORDER_MEDIUM );
				
				CellStyle percentageFucsiaBorderedCellStyle = workbook.createCellStyle();
				percentageFucsiaBorderedCellStyle.setFont(calibri10);
				percentageFucsiaBorderedCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				percentageFucsiaBorderedCellStyle.setFillForegroundColor((short)45);
				percentageFucsiaBorderedCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				percentageFucsiaBorderedCellStyle.setBorderBottom( CellStyle.BORDER_MEDIUM );
				percentageFucsiaBorderedCellStyle.setBorderTop( CellStyle.BORDER_MEDIUM );
				percentageFucsiaBorderedCellStyle.setBorderLeft( CellStyle.BORDER_MEDIUM );
				percentageFucsiaBorderedCellStyle.setBorderRight( CellStyle.BORDER_MEDIUM );
				percentageFucsiaBorderedCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0%"));
				
				CellStyle percentageBorderedCellStyle = workbook.createCellStyle();
				percentageBorderedCellStyle.setFont(calibri10);
				percentageBorderedCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				percentageBorderedCellStyle.setBorderBottom( CellStyle.BORDER_MEDIUM );
				percentageBorderedCellStyle.setBorderTop( CellStyle.BORDER_MEDIUM );
				percentageBorderedCellStyle.setBorderLeft( CellStyle.BORDER_MEDIUM );
				percentageBorderedCellStyle.setBorderRight( CellStyle.BORDER_MEDIUM );
				percentageBorderedCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0%"));
				
				rowIndex=4;
				cellIndex=11;
				SizeDistributionByColorReport sdbcr = new SizeDistributionByColorReport(getSelectedPurchaseOrder());
				Iterator<Entry<String,ColorScanDistribution>> sdbcrIterator = sdbcr.getColorSizeScanDistribution().entrySet().iterator();
				while( sdbcrIterator.hasNext() ) {
					Map.Entry<String,ColorScanDistribution> entry = sdbcrIterator.next();
					Row colorItemRow = getRowAt(rowIndex, sheet);
					Cell colorItemCell = colorItemRow.createCell(cellIndex);
					colorItemCell.setCellValue( entry.getKey() );
					colorItemCell.setCellStyle(colorItemTitleStyle);
					
					rowIndex++;
					Row colorItemTitleRow = getRowAt(rowIndex, sheet);
					Cell sizeTitleCell = colorItemTitleRow.createCell( cellIndex );sizeTitleCell.setCellValue( "SIZE" );sizeTitleCell.setCellStyle(yellowSizeTitleCellStyle);
					Cell poQtyTitleCell = colorItemTitleRow.createCell( cellIndex+1 );poQtyTitleCell.setCellValue( "PO QTY" );poQtyTitleCell.setCellStyle(yellowSizeTitleCellStyle);
					Cell qtyPackTitleCell = colorItemTitleRow.createCell( cellIndex+2 );qtyPackTitleCell.setCellValue( "QTY PACK" );qtyPackTitleCell.setCellStyle(yellowSizeTitleCellStyle);
					Cell difTitleCell = colorItemTitleRow.createCell( cellIndex+3 );difTitleCell.setCellValue( "DIF" );difTitleCell.setCellStyle(yellowSizeTitleCellStyle);
					Cell percentageTitleCell = colorItemTitleRow.createCell( cellIndex+4 );percentageTitleCell.setCellValue( "%" );percentageTitleCell.setCellStyle(yellowSizeTitleCellStyle);
					
					int totalPoQty = 0;
					int totalPackedQty = 0;
					int totalDif = 0;
					
					for( SizeScanDistribution ssd : entry.getValue().getSizeScanDistribution() ){
						rowIndex++;
						Row colorItemDataRow = getRowAt(rowIndex, sheet);
						String sizeStr = "";
						if( getSelectedPurchaseOrder().getReferenceNumber().endsWith( "11" ) ){
							sizeStr = ssd.getSize().getWaist() + "";
						}else{
							sizeStr = ssd.getSize().getWaist() + "x" + ssd.getSize().getInseam();
						}
						Cell size = colorItemDataRow.createCell( cellIndex );size.setCellValue( sizeStr );size.setCellStyle( lightBlueSizeCellStyle );
						Cell poQty = colorItemDataRow.createCell( cellIndex+1 );poQty.setCellValue( ssd.getPoQty() );poQty.setCellStyle( countMediumBorderedCellStyle );
						Cell packedQty = colorItemDataRow.createCell( cellIndex+2 );packedQty.setCellValue( ssd.getPackedQty() );packedQty.setCellStyle( countMediumBorderedCellStyle );
						Cell diff = colorItemDataRow.createCell( cellIndex+3 );diff.setCellValue( ssd.getPoQty() - ssd.getPackedQty() );diff.setCellStyle( countMediumBorderedCellStyle );
						double poQtyF = ssd.getPoQty();
						double packedQtyF = ssd.getPackedQty();
						
						double perc = ( 1 - ( (poQtyF-packedQtyF)/poQtyF ) );
						Cell percentage = colorItemDataRow.createCell( cellIndex+4 );percentage.setCellValue( perc );percentage.setCellStyle(percentageFucsiaBorderedCellStyle);
						if( perc<0.95 || perc>1.05 ){
							percentage.setCellStyle( percentageFucsiaBorderedCellStyle );
						}else{
							percentage.setCellStyle( percentageBorderedCellStyle );
						}
						
						totalPoQty = totalPoQty + ssd.getPoQty();
						totalPackedQty = totalPackedQty + ssd.getPackedQty();
						totalDif = totalDif + (ssd.getPoQty() - ssd.getPackedQty());
					}
					
					rowIndex++;
					
					Row blankRow = getRowAt(rowIndex, sheet);
					blankRow.createCell(cellIndex).setCellStyle( lightBlueSizeCellStyle );
					blankRow.createCell(cellIndex+1).setCellStyle( countMediumBorderedCellStyle );
					blankRow.createCell(cellIndex+2).setCellStyle( countMediumBorderedCellStyle );
					blankRow.createCell(cellIndex+3).setCellStyle( countMediumBorderedCellStyle );
					blankRow.createCell(cellIndex+4).setCellStyle( percentageFucsiaBorderedCellStyle );
					
					rowIndex++;
					
					Row totalColorSizeScanRow = getRowAt(rowIndex, sheet);
					Cell totalPoQtyCell = totalColorSizeScanRow.createCell( cellIndex+1 );totalPoQtyCell.setCellValue(totalPoQty);totalPoQtyCell.setCellStyle( yellowSizeTitleCellStyle );
					Cell totalPackedQtyCell = totalColorSizeScanRow.createCell( cellIndex+2 );totalPackedQtyCell.setCellValue(totalPackedQty);totalPackedQtyCell.setCellStyle( yellowSizeTitleCellStyle );
					Cell totalDifCell = totalColorSizeScanRow.createCell( cellIndex+3 );totalDifCell.setCellValue(totalPoQty);totalDifCell.setCellStyle( yellowSizeTitleCellStyle );
					
					rowIndex++;
					rowIndex++;
				}
				
				try {
					File f = new File( fileName );
					
				    FileOutputStream out = new FileOutputStream( f );
				    workbook.write(out);
				    out.close();
				    
				    if( getQuestionBox( "the report was generated at " + f.getAbsolutePath() + "\nwould you like to open it?" ).open() == SWT.YES ) {
					    //System.out.println("Excel written successfully..");
					    Desktop desktop = null;
						
						if( Desktop.isDesktopSupported() ){
							desktop = Desktop.getDesktop();
							
							try {
								desktop.open(f);
							}catch ( IOException e ){
								getLog().error( "error", e );
							}
						}
					}
				} catch ( FileNotFoundException e) {
				    getLog().error( "error", e );
				} catch (IOException e) {
					getLog().error( "error", e );
				}
			}catch( Exception e ){
				getLog().error( "error", e );
			}
		}
	}
	public void setMergedCells(Sheet sheet, int rowFrom, int rowTo, int cellFrom, int cellTo ){
		sheet.addMergedRegion(new CellRangeAddress(rowFrom, rowTo, cellFrom, cellTo));
	}
	
	public void generateVarianceReport() throws Exception {
		if( getSelectedPurchaseOrder()!=null ){
			Workbook workbook = new XSSFWorkbook();
			
			Sheet sheet = workbook.createSheet( "Variance Report" );
			sheet.setZoom( 9, 10 );
			sheet.createFreezePane(4/*cols*/, 6/*rows*/);//(cols,rows)
			int rowIndex = 0;
			int cellIndex = 0;
			PoComparativeByColorItemSize poComp = new PoComparativeByColorItemSize(getSelectedPurchaseOrder());
			/*
			 * Font definitions
			 */
			Font arialGreen8 = workbook.createFont();
			arialGreen8.setColor( IndexedColors.GREEN.index );
			arialGreen8.setFontHeightInPoints((short)8);
			arialGreen8.setFontName( "Arial" );
			
			Font arialGreenBold10 = workbook.createFont();
			arialGreenBold10.setColor( IndexedColors.GREEN.index );
			arialGreenBold10.setFontHeightInPoints((short)10);
			arialGreenBold10.setFontName( "Arial" );
			arialGreenBold10.setBoldweight( Font.BOLDWEIGHT_BOLD );
			
			Font arial20 = workbook.createFont();
			arial20.setFontName( "Arial" );
			arial20.setColor( IndexedColors.BLACK.index );
			arial20.setFontHeightInPoints((short)20);
			arial20.setItalic(false);
			
			Font arial10 = workbook.createFont();
			arial10.setFontName("Arial");
			arial10.setBoldweight( Font.BOLDWEIGHT_NORMAL );
			arial10.setItalic(false);
			arial10.setFontHeightInPoints((short)10);
			arial10.setColor(IndexedColors.BLACK.index);
			
			Font arialRed10 = workbook.createFont();
			arialRed10.setFontName("Arial");
			arialRed10.setBoldweight( Font.BOLDWEIGHT_NORMAL );
			arialRed10.setItalic(false);
			arialRed10.setFontHeightInPoints((short)10);
			arialRed10.setColor(IndexedColors.RED.index);
			
			Font arialBlue10 = workbook.createFont();
			arialBlue10.setFontName("Arial");
			arialBlue10.setBoldweight( Font.BOLDWEIGHT_NORMAL );
			arialBlue10.setItalic(false);
			arialBlue10.setFontHeightInPoints((short)10);
			arialBlue10.setColor(IndexedColors.BLUE.index);
			
			Font arialBold10 = workbook.createFont();
			arialBold10.setFontName("Arial");
			arialBold10.setBoldweight( Font.BOLDWEIGHT_BOLD );
			arialBold10.setItalic(false);
			arialBold10.setFontHeightInPoints((short)10);
			arialBold10.setColor(IndexedColors.BLACK.index);
			
			Font arialBold14 = workbook.createFont();
			arialBold14.setFontName("Arial");
			arialBold14.setItalic(false);
			arialBold14.setFontHeightInPoints((short)14);
			arialBold14.setColor( IndexedColors.BLACK.index );
			arialBold14.setBoldweight( Font.BOLDWEIGHT_BOLD );
			
			Font arialBold12 = workbook.createFont();
			arialBold12.setFontName("Arial");
			arialBold12.setBoldweight( Font.BOLDWEIGHT_BOLD );
			arialBold12.setFontHeightInPoints( (short)12 );
			arialBold12.setColor( IndexedColors.BLACK.index );
			arialBold12.setItalic( false );
			/*
			 * END of Font definitions
			 */
			/*
			 * CellStile definitions
			 */
			CellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
			titleStyle.setFont(arial20);
			
			CellStyle arialBold10Style = workbook.createCellStyle();
			arialBold10Style.setFont( arialBold10 );
			
			CellStyle arial10CenteredCellStyle = workbook.createCellStyle();
			arial10CenteredCellStyle.setFont( arial10 );
			arial10CenteredCellStyle.setAlignment( CellStyle.ALIGN_CENTER );
			
			CellStyle titleArial14BoldCellStyle = workbook.createCellStyle();
			titleArial14BoldCellStyle.setFont(arialBold14);
			titleArial14BoldCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			titleArial14BoldCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
			
			CellStyle titleArial12BoldCellStyle = workbook.createCellStyle();
			titleArial12BoldCellStyle.setFont(arialBold12);
			titleArial12BoldCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
			titleArial12BoldCellStyle.setBorderTop( CellStyle.BORDER_THIN );
			titleArial12BoldCellStyle.setBorderRight( CellStyle.BORDER_THIN );
			titleArial12BoldCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
			
			
			CellStyle colorItemCellStyle = workbook.createCellStyle();
			colorItemCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			colorItemCellStyle.setFont( arialBold10 );
			colorItemCellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
			colorItemCellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
			colorItemCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			colorItemCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			colorItemCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			colorItemCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			
			CellStyle orderedCountsCellStyle = workbook.createCellStyle();
			orderedCountsCellStyle.setAlignment( CellStyle.ALIGN_CENTER );
			orderedCountsCellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
			orderedCountsCellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
			orderedCountsCellStyle.setFont(arial10);
			
			CellStyle shippedBlueCellStyle = workbook.createCellStyle();
			shippedBlueCellStyle.setAlignment( CellStyle.ALIGN_CENTER );
			shippedBlueCellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
			shippedBlueCellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
			shippedBlueCellStyle.setFont(arialBlue10);
			
			CellStyle differenceCellStyle = workbook.createCellStyle();
			differenceCellStyle.setFont( arial10 );
			differenceCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
			differenceCellStyle.setBorderTop( CellStyle.BORDER_THIN );
			differenceCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
			differenceCellStyle.setBorderRight( CellStyle.BORDER_THIN );
			differenceCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle totalDifferenceCellStyle = workbook.createCellStyle();
			totalDifferenceCellStyle.setFont(arialGreen8);
			totalDifferenceCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
			totalDifferenceCellStyle.setBorderTop( CellStyle.BORDER_THIN );
			totalDifferenceCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
			totalDifferenceCellStyle.setBorderRight( CellStyle.BORDER_THIN );
			totalDifferenceCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle shippedWordCellStyle = workbook.createCellStyle();
			shippedWordCellStyle.setFont( arialBlue10 );
			
			CellStyle differenceWordCellStyle = workbook.createCellStyle();
			differenceWordCellStyle.setFont( arialBold10 );
			differenceWordCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
			differenceWordCellStyle.setBorderTop( CellStyle.BORDER_THIN );
			differenceWordCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
			differenceWordCellStyle.setBorderRight( CellStyle.BORDER_THIN );
			
			CellStyle percentageWordCellStyle = workbook.createCellStyle();
			percentageWordCellStyle.setFont( arialGreenBold10 );
			percentageWordCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
			percentageWordCellStyle.setBorderTop( CellStyle.BORDER_THIN );
			percentageWordCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
			percentageWordCellStyle.setBorderRight( CellStyle.BORDER_THIN );
			
			CellStyle differenceRedCellStyle = workbook.createCellStyle();
			differenceRedCellStyle.setFont( arialRed10 );
			differenceRedCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
			differenceRedCellStyle.setBorderTop( CellStyle.BORDER_THIN );
			differenceRedCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
			differenceRedCellStyle.setBorderRight( CellStyle.BORDER_THIN );
			
			CellStyle shippedBlueTransparentCellStyle = workbook.createCellStyle();
			shippedBlueTransparentCellStyle.setFont(arialBlue10);
			shippedBlueTransparentCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle percentageGreenCellStyle = workbook.createCellStyle();
			percentageGreenCellStyle.setFont(arialGreen8);
			percentageGreenCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			percentageGreenCellStyle.setBorderBottom( CellStyle.BORDER_THIN );
			percentageGreenCellStyle.setBorderTop( CellStyle.BORDER_THIN );
			percentageGreenCellStyle.setBorderRight( CellStyle.BORDER_THIN );
			percentageGreenCellStyle.setBorderLeft( CellStyle.BORDER_THIN );
			percentageGreenCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0%"));
			/*
			 * END of CellStyle definitions
			 */
			
			Row reportTitleRow = getRowAt(rowIndex, sheet);
			Cell reportTitleCell = reportTitleRow.createCell(cellIndex);
			reportTitleCell.setCellValue( "Eddie Bauer Variance Report" );
			reportTitleCell.setCellStyle(titleStyle);
			setMergedCells(sheet, rowIndex, rowIndex, cellIndex, cellIndex+poComp.getSizeList().size()+6);
			
			rowIndex++;getRowAt(rowIndex, sheet, arialBold10Style).createCell(cellIndex).setCellValue( "DRN/Style Description:" );
			rowIndex++;getRowAt(rowIndex, sheet, arialBold10Style).createCell(cellIndex).setCellValue( "P.O. Number: " + getSelectedPurchaseOrder().getReferenceNumber() );
			rowIndex++;getRowAt(rowIndex, sheet, arialBold10Style).createCell(cellIndex).setCellValue( "Ship Date: " );
			
			rowIndex++;rowIndex++;
			
			Cell titleItem = getRowAt(rowIndex, sheet).createCell(cellIndex);titleItem.setCellStyle( titleArial14BoldCellStyle );
			Cell titlePrt = getRowAt(rowIndex, sheet).createCell(cellIndex+1);titlePrt.setCellStyle( titleArial14BoldCellStyle );
			Cell titleColor = getRowAt(rowIndex, sheet).createCell(cellIndex+2);titleColor.setCellStyle( titleArial14BoldCellStyle );
			Cell titleSize = getRowAt(rowIndex, sheet).createCell(cellIndex+3);titleSize.setCellStyle( titleArial14BoldCellStyle );
			
			titleItem.setCellValue( "Item #" );
			titlePrt.setCellValue( "P-R-T" );
			titleColor.setCellValue( "Color" );
			titleSize.setCellValue( "Size" );
			
			for( int i = 0 ; i < poComp.getSizeList().size() ; i++ ) {
				Cell titleSizeCell = getRowAt(rowIndex, sheet).createCell(cellIndex+4+i);
				titleSizeCell.setCellStyle( titleArial12BoldCellStyle );
				String sizeStr = poComp.getSizeList().get(i).getWaist() + "";
				if( !getSelectedPurchaseOrder().getReferenceNumber().endsWith( "11" ) ){
					sizeStr += "x" + poComp.getSizeList().get(i).getInseam();
				}
				titleSizeCell.setCellValue( sizeStr );
			}
			
			Cell totalCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 1 );
			totalCell.setCellValue( "Total" );
			totalCell.setCellStyle( titleArial14BoldCellStyle );
			
			for ( ItemColorSizeCount icsc : poComp.getItemColorSizeCountList() ) {
				rowIndex++;rowIndex++;rowIndex++;
				Cell itemValueCell = getRowAt(rowIndex, sheet).createCell( cellIndex );itemValueCell.setCellStyle( colorItemCellStyle );itemValueCell.setCellValue( icsc.getStyle().getCode() );
				Cell prtValueCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 1 );prtValueCell.setCellStyle( colorItemCellStyle );prtValueCell.setCellValue( "Regular" );
				Cell colorValueCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 2 );colorValueCell.setCellStyle( colorItemCellStyle );colorValueCell.setCellValue( icsc.getColor().getName() );
				Cell sizeValueCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 3 );sizeValueCell.setCellStyle( colorItemCellStyle );
				
				for( int i = 0 ; i < icsc.getSizeCountsList().size() ; i++ ){
					Cell sizeCountCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + i );
					sizeCountCell.setCellStyle( orderedCountsCellStyle );
					sizeCountCell.setCellValue( icsc.getSizeCountsList().get(i).getOrdered() );
				}
				Cell totalOrderedCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 1 );
				totalOrderedCell.setCellValue(icsc.getTotalOrdered());
				totalOrderedCell.setCellStyle( arial10CenteredCellStyle );
				
				getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 2 ).setCellValue("Ordered");
				
				rowIndex++;rowIndex++;
				for( int i = 0 ; i < icsc.getSizeCountsList().size() ; i++ ){
					Cell sizeCountCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + i );
					if( icsc.getSizeCountsList().get(i).getDifference()!=0 ) {
						sizeCountCell.setCellStyle( shippedBlueCellStyle );
					} else {
						sizeCountCell.setCellStyle( orderedCountsCellStyle );
					}
					
					sizeCountCell.setCellValue( icsc.getSizeCountsList().get(i).getShipped() );
				}
				Cell totalShippedCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 1 );
				totalShippedCell.setCellStyle( shippedBlueTransparentCellStyle );totalShippedCell.setCellValue(icsc.getTotalShipped());
				Cell shippedWordCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 2 );
				shippedWordCell.setCellStyle( shippedWordCellStyle );shippedWordCell.setCellValue("Shipped");
				
				rowIndex++;rowIndex++;
				for( int i = 0 ; i < icsc.getSizeCountsList().size() ; i++ ){
					Cell sizeCountCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + i );
					if( icsc.getSizeCountsList().get(i).getDifference()<0 ) {
						sizeCountCell.setCellStyle( differenceRedCellStyle );
					} else {
						sizeCountCell.setCellStyle( differenceCellStyle );
					}
					sizeCountCell.setCellValue( icsc.getSizeCountsList().get(i).getDifference() );
				}
				Cell totalDifferenceCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 1 );
				totalDifferenceCell.setCellValue(icsc.getTotalDifference());totalDifferenceCell.setCellStyle( totalDifferenceCellStyle );
				Cell differenceWordCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 2 );
				differenceWordCell.setCellValue("Difference");differenceWordCell.setCellStyle( differenceWordCellStyle );
				
				rowIndex++;
				for( int i = 0 ; i < icsc.getSizeCountsList().size() ; i++ ){
					Cell sizeCountCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + i );
					sizeCountCell.setCellStyle( percentageGreenCellStyle );
					sizeCountCell.setCellValue( icsc.getSizeCountsList().get(i).getPercentage() );
				}
				Cell totalPercentageCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 1 );
				totalPercentageCell.setCellValue(icsc.getTotalPercentage());totalPercentageCell.setCellStyle(percentageGreenCellStyle);
				Cell percentageWordCell = getRowAt(rowIndex, sheet).createCell( cellIndex + 4 + poComp.getSizeList().size() + 2 );
				percentageWordCell.setCellValue("Percentage");percentageWordCell.setCellStyle(percentageWordCellStyle);
			}
			
			rowIndex++;rowIndex++;rowIndex++;rowIndex++;
			CellStyle leftTopBorderedCornerCellStyle = workbook.createCellStyle();
			leftTopBorderedCornerCellStyle.setBorderTop( CellStyle.BORDER_DOUBLE );
			leftTopBorderedCornerCellStyle.setBorderLeft( CellStyle.BORDER_DOUBLE );
			CellStyle topBorderedCornerCellStyle = workbook.createCellStyle();
			topBorderedCornerCellStyle.setBorderTop(CellStyle.BORDER_DOUBLE);
			CellStyle leftBorderedCornerCellStyle = workbook.createCellStyle();
			leftBorderedCornerCellStyle.setBorderLeft( CellStyle.BORDER_DOUBLE );
			CellStyle rightBorderedCornerCellStyle = workbook.createCellStyle();
			rightBorderedCornerCellStyle.setBorderRight(CellStyle.BORDER_DOUBLE);
			CellStyle bottomBorderedCornerCellStyle = workbook.createCellStyle();
			bottomBorderedCornerCellStyle.setBorderBottom(CellStyle.BORDER_DOUBLE);
			CellStyle rightTopBorderedCornerCellStyle = workbook.createCellStyle();
			rightTopBorderedCornerCellStyle.setBorderTop( CellStyle.BORDER_DOUBLE );
			rightTopBorderedCornerCellStyle.setBorderRight( CellStyle.BORDER_DOUBLE );
			CellStyle leftBottomBorderedCornerCellStyle = workbook.createCellStyle();
			leftBottomBorderedCornerCellStyle.setBorderBottom( CellStyle.BORDER_DOUBLE );
			leftBottomBorderedCornerCellStyle.setBorderLeft( CellStyle.BORDER_DOUBLE );
			CellStyle rightBottomBorderedCornerCellStyle = workbook.createCellStyle();
			rightBottomBorderedCornerCellStyle.setBorderBottom( CellStyle.BORDER_DOUBLE );
			rightBottomBorderedCornerCellStyle.setBorderRight( CellStyle.BORDER_DOUBLE );
			
			getRowAt(rowIndex, sheet).createCell(cellIndex).setCellStyle(leftTopBorderedCornerCellStyle);
			cellIndex++;
			for( int i = 0 ; i < (poComp.getTotalBySizes().size() + 6) ; i++ ) {
				getRowAt(rowIndex, sheet).createCell(cellIndex).setCellStyle( topBorderedCornerCellStyle );
				cellIndex++;
			}
			
			getRowAt(rowIndex, sheet).createCell(cellIndex).setCellStyle(rightTopBorderedCornerCellStyle);
			
			cellIndex=0;
			rowIndex++;
			for( int i = 0 ; i < 6 ; i++ ) {
				getRowAt(rowIndex+i, sheet).createCell(cellIndex).setCellStyle(leftBorderedCornerCellStyle);
			}
			
			cellIndex+=2;
			getRowAt(rowIndex, sheet).createCell(cellIndex).setCellValue( "TOTAL" );
			cellIndex+=2;
			
			int totalOrdered = 0;
			int totalShipped = 0;
			int totalDifference = 0;
			
			for( int i = 0 ; i < poComp.getTotalBySizes().size() ; i++ ){
				Cell cellOrdered = getRowAt(rowIndex, sheet).createCell(cellIndex);
				cellOrdered.setCellValue( poComp.getTotalBySizes().get(i).getOrdered() );
				cellOrdered.setCellStyle( arial10CenteredCellStyle );
				
				Cell cellShipped = getRowAt(rowIndex+2, sheet).createCell(cellIndex);
				cellShipped.setCellValue( poComp.getTotalBySizes().get(i).getShipped() );
				cellShipped.setCellStyle( shippedBlueTransparentCellStyle );
				
				Cell cellDifference = getRowAt(rowIndex+4, sheet).createCell(cellIndex);
				cellDifference.setCellValue( poComp.getTotalBySizes().get(i).getDifference() );
				cellDifference.setCellStyle(differenceCellStyle);
				
				Cell cellPercentage = getRowAt(rowIndex+5, sheet).createCell(cellIndex);
				cellPercentage.setCellValue( poComp.getTotalBySizes().get(i).getPercentage() );
				cellPercentage.setCellStyle(percentageGreenCellStyle);
				
				totalOrdered += poComp.getTotalBySizes().get(i).getOrdered();
				totalShipped += poComp.getTotalBySizes().get(i).getShipped();
				
				cellIndex++;
			}
			
			cellIndex++;
			Cell grandTotalOrdered = getRowAt(rowIndex, sheet).createCell(cellIndex);
			grandTotalOrdered.setCellValue( totalOrdered );
			grandTotalOrdered.setCellStyle( arial10CenteredCellStyle );
			Cell grandTotalShipped = getRowAt(rowIndex+2, sheet).createCell(cellIndex);
			grandTotalShipped.setCellValue( totalShipped );grandTotalShipped.setCellStyle( shippedBlueTransparentCellStyle );
			Cell grandTotalDifference = getRowAt(rowIndex+4, sheet).createCell(cellIndex);
			grandTotalDifference.setCellValue( totalDifference );grandTotalDifference.setCellStyle( totalDifferenceCellStyle );
			Cell grandTotalPercentage = getRowAt(rowIndex+5, sheet).createCell(cellIndex);
			grandTotalPercentage.setCellValue( (new Double(totalShipped)/new Double(totalOrdered)) );grandTotalPercentage.setCellStyle(percentageGreenCellStyle);
			
			cellIndex++;
			getRowAt(rowIndex, sheet).createCell( cellIndex ).setCellValue("Ordered");
			Cell shippedWordCell = getRowAt(rowIndex+2, sheet).createCell( cellIndex );
			shippedWordCell.setCellStyle( shippedWordCellStyle );shippedWordCell.setCellValue("Shipped");
			Cell differenceWordCell = getRowAt(rowIndex+4, sheet).createCell( cellIndex );
			differenceWordCell.setCellValue("Difference");differenceWordCell.setCellStyle( differenceWordCellStyle );
			Cell percentageWordCell = getRowAt(rowIndex+5, sheet).createCell( cellIndex );
			percentageWordCell.setCellValue("Percentage");percentageWordCell.setCellStyle(percentageWordCellStyle);
			
			cellIndex++;
			
			for( int i = 0 ; i < 6 ; i++ ) {
				getRowAt(rowIndex+i, sheet).createCell(cellIndex).setCellStyle(rightBorderedCornerCellStyle);
			}
			
			rowIndex+=6;
			cellIndex=0;
			
			getRowAt(rowIndex, sheet).createCell(cellIndex).setCellStyle(leftBottomBorderedCornerCellStyle);
			cellIndex++;
			for( int i = 0 ; i < (poComp.getTotalBySizes().size() + 6) ; i++ ) {
				getRowAt(rowIndex, sheet).createCell(cellIndex).setCellStyle( bottomBorderedCornerCellStyle );
				cellIndex++;
			}
			
			getRowAt(rowIndex, sheet).createCell(cellIndex).setCellStyle(rightBottomBorderedCornerCellStyle);
			cellIndex=0;
			rowIndex+=2;
			Cell lastUpdateCell = getRowAt(rowIndex, sheet).createCell(cellIndex);
			lastUpdateCell.setCellValue( "last update " + ClientLogic.getSimpleDateFormat().format( ( Calendar.getInstance().getTime() ) ) );
			
			for( int i = 0 ; i < poComp.getSizeList().size()+7 ; i++ ){
				sheet.autoSizeColumn(i);
			}
			getLog().info( "column width: " + sheet.getColumnWidth(poComp.getSizeList().size()+8) );
			sheet.setColumnWidth(poComp.getSizeList().size()+7, 10);
			
			for( int i = 0 ; i < rowIndex ; i++ ){
				getRowAt(rowIndex+i, sheet).setHeight((short)0);
			}
			
			/*
			rowIndex++;rowIndex++;rowIndex++;
			Iterator<Entry<Integer,HSSFColor>> it = HSSFColor.getIndexHash().entrySet().iterator();
			while( it.hasNext() ) {
				Map.Entry<Integer, HSSFColor> entry = it.next();
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor( entry.getValue().getIndex() );
				cellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
				
				Cell colorCell = getRowAt(rowIndex, sheet).createCell(cellIndex);
				colorCell.setCellStyle(cellStyle);
				colorCell.setCellValue( entry.getValue().toString() );
				rowIndex++;
			}
			*/
			
			try {
				String folderPath = "reports/quantity_variance_po/";
				String fileName = folderPath + "Quantity Variance PO " + getSelectedPurchaseOrder().getReferenceNumber().substring( 0, 8 ) + ".xlsx";
				File folder = new File( folderPath );
				
				if( !folder.exists() ){
					folder.mkdir();
				}
				
				File f = new File( fileName );
				
			    FileOutputStream out = new FileOutputStream( f );
			    workbook.write(out);
			    out.close();
			    
			    if( getQuestionBox( "the report was generated at " + f.getAbsolutePath() + "\nwould you like to open it?" ).open() == SWT.YES ) {
				    //System.out.println("Excel written successfully..");
				    Desktop desktop = null;
					
					if( Desktop.isDesktopSupported() ){
						desktop = Desktop.getDesktop();
						
						try {
							desktop.open(f);
						}catch ( IOException e ){
							getLog().error( "error", e );
						}
					}
				}
			} catch ( FileNotFoundException e) {
			    getLog().error( "error", e );
			} catch (IOException e) {
				getLog().error( "error", e );
			}
		}
	}
	
	@Override
	protected void checkSubclass() {
	}
	
	public Row getRowAt( int rowIndex, Sheet sheet, CellStyle style ){
		Row row = null;
		
		try{
			row = getRowAt(rowIndex, sheet);
			row.setRowStyle(style);
		}catch( Exception e ){
			getLog().error( "error", e );
		}
		
		return row;
	}
	public Row getRowAt( int rowIndex, Sheet sheet ){
		Row row = sheet.getRow(rowIndex);
		if( row == null ){
			row = sheet.createRow( rowIndex );
		}
		return row;
	}
	
	public void hide(){
		this.setParent( getParent().getMaster().getHiddenShell() );
		this.setVisible(false);
	}
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger(ReportsManagerComposite.class);
			PropertyConfigurator.configure("log4j.properties");
		}
		return log;
	}
	public Combo getPurchaseOrderCbx() {
		return purchaseOrderCbx;
	}
	public Combo getUserCbx() {
		return userCbx;
	}
	
	public MessageBox getQuestionBox( String message ){
		if( questionBox == null ){
			questionBox = new MessageBox( getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO );
			questionBox.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
		}
		questionBox.setMessage(message);
		return questionBox;
	}

	public static void main( String[] args ) {
		HSSFPalette palette= (new HSSFWorkbook()).getCustomPalette();
		HSSFColor lightBlue = palette.addColor((byte) 153, (byte) 0, (byte) 0);
		
		Iterator<Entry<Integer,HSSFColor>> itHash = HSSFColor.getIndexHash().entrySet().iterator();
		System.out.println( "HSSFColor.getIndexHash():" );
		while( itHash.hasNext() ) {
			Map.Entry< Integer, HSSFColor> entry = itHash.next();
			System.out.println( "\t" + entry.getValue() + "[ " + entry.getKey() + " ]" );
		}
		
		Set<Integer> mutableIndexKeys = HSSFColor.getMutableIndexHash().keySet();
		System.out.println( "\nHSSFColor.getMutableIndexHash():" );
		for( Integer key : mutableIndexKeys ) {
			System.out.println( "\t" + HSSFColor.getMutableIndexHash().get(key) + "[ " + key + " ]" );
		}
		
		Set<String> tripleKeys = HSSFColor.getTripletHash().keySet();
		System.out.println( "\nHSSFHSSFColor.getTripletHash():" );
		for( String key : tripleKeys ) {
			System.out.println( "\t" + HSSFColor.getTripletHash().get(key) + "[ " + key + " ]" );
		}
	}
}