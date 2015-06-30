package com.uslc.pe.gui.master;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.uslc.pe.gui.master.MasterLeftComposite.PurchaseOrderStatistics;
import com.uslc.pe.gui.master.MasterLeftComposite.PurchaseOrderStatus;
import com.uslc.pe.gui.master.catalog.FormCenterMaster;
import com.uslc.pe.jpa.comparator.PurchaseOrderDetailComparator;
import com.uslc.pe.jpa.entity.PurchaseOrderDetail;

public class PurchaseOrderDetailComposite extends FormCenterMaster {
	private Label titleLbl = null;
	private Table detailsTbl = null;
	private Group statsGrp = null;
	private GridData gdFill = null;
	private Table statsTbl = null;
	
	private Logger log = null;
	
	public PurchaseOrderDetailComposite( MasterCenterComposite composite ){
		super( composite, SWT.BORDER );
		initComposite();
	}
	
	private void initComposite(){
		//getLog().info("initComposite");
		FormData data = new FormData( 550, 550);
		setLayoutData(data);
		
		setLayout( new GridLayout( 1, true ) );
		
		getTitleLbl();
		getDetailsTbl();
		getStatsGrp();
	}
	
	public Label getTitleLbl() {
		if( titleLbl == null ){
			titleLbl = new Label(this, SWT.NONE);
			titleLbl.setText("Purchase Detail");
			titleLbl.setLayoutData(getFillGridData());
		}
		return titleLbl;
	}
	public Table getDetailsTbl() {
		if( detailsTbl == null ){
			detailsTbl = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION);
			detailsTbl.setHeaderVisible(true);
			detailsTbl.setFont( getParent().getMaster().getSystemVariables().getXSmallFont() );
			
			TableColumn index = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn upc = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn dept = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn itemCode = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn poNumber = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn colorName = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn colorItem = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn colorNumber = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn size = new TableColumn(detailsTbl, SWT.READ_ONLY);
			TableColumn qty = new TableColumn(detailsTbl, SWT.READ_ONLY);
			
			index.setText( "#" );
			upc.setText("upc");
			dept.setText("dept");
			itemCode.setText("item code");
			poNumber.setText("po #");
			colorName.setText("color name");
			colorItem.setText("");
			colorNumber.setText("color #");
			size.setText("size");
			qty.setText("qty");
			
			index.setWidth(20);
			upc.setWidth(79);
			dept.setWidth(35);
			itemCode.setWidth(56);
			poNumber.setWidth(83);
			colorName.setWidth(64);
			colorItem.setWidth(67);
			colorNumber.setWidth(49);
			size.setWidth(45);
			qty.setWidth(41);
			
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.heightHint=300;
			detailsTbl.setLayoutData(gd);
			
			detailsTbl.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					for( TableItem item : detailsTbl.getItems() ) {
						item.setFont( getParent().getMaster().getSystemVariables().getXSmallFont() );
					}
	                if(detailsTbl.getSelectionIndex() != -1) {
	                    TableItem item = detailsTbl.getItem(detailsTbl.getSelectionIndex());
	                    item.setFont( getParent().getMaster().getSystemVariables().getNormalFont() );
	                    detailsTbl.setSelection( detailsTbl.getSelectionIndex() );
	                }
	            }
			});
		}
		return detailsTbl;
	}
	public Group getStatsGrp() {
		if( statsGrp == null ){
			statsGrp = new Group(this, SWT.SHADOW_ETCHED_IN);
			statsGrp.setText( "Summary" );
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END);
			data.widthHint = 260;
			data.heightHint=150;
			data.grabExcessHorizontalSpace=true;
			statsGrp.setLayoutData(data);
			
			GridLayout lay = new GridLayout(1,false);
			statsGrp.setLayout(lay);
			
			getStatsTbl();
		}
		return statsGrp;
	}
	public Table getStatsTbl(){
		if( statsTbl == null ){
			statsTbl = new Table(getStatsGrp(), SWT.NONE);
			statsTbl.setHeaderVisible(true);
			statsTbl.setFont(new Font(getDisplay(), "Segoe UI", 8, SWT.NORMAL));
			
			TableColumn color = new TableColumn(statsTbl, SWT.READ_ONLY);
			TableColumn units = new TableColumn(statsTbl, SWT.READ_ONLY);
			TableColumn boxes = new TableColumn(statsTbl, SWT.READ_ONLY);
			
			color.setText("color");
			units.setText("units");
			boxes.setText("boxes");
			
			color.setWidth(139);
			units.setWidth(56);
			boxes.setWidth(51);
			
			GridData gd = new GridData(GridData.FILL_BOTH);
			statsTbl.setLayoutData(gd);
			
			/*( new Button( getStatsGrp(), SWT.PUSH ) ).addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					try {
						getParent().getMaster().getMaster().getColumnsWidth(getDetailsTbl());
						getParent().getMaster().getMaster().getColumnsWidth(getStatsTbl());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});*/
		}
		return statsTbl;
	}
	public GridData getFillGridData(){
		if( gdFill == null ){
			gdFill = new GridData( GridData.FILL_HORIZONTAL );
		}
		return gdFill;
	}

	public void hide(){
		clean();
		this.setParent( getParent().getMaster().getHiddenShell() );
		this.setVisible(false);
	}
	private void clean(){
	}
	
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger(NewPurchaseOrderComposite.class);
			PropertyConfigurator.configure("log4j.properties");
		}
		return log;
	}

	public void displayPurchaseDetails(PurchaseOrderStatus po) {
		
		Collections.sort(po.getPo().getPurchaseOrderDetails(), new PurchaseOrderDetailComparator() );
		int podCount = 0;
		
		getDetailsTbl().removeAll();
		
		for( PurchaseOrderDetail pod : po.getPo().getPurchaseOrderDetails()) {
			if( !pod.getDeleted() ) {
				podCount++;
				TableItem row = new TableItem( getDetailsTbl(), SWT.READ_ONLY );
				String[] texts = {
						String.valueOf(podCount),
						pod.getUpc().getUpcCode(),
						po.getPo().getDepartmentNumber(),
						String.valueOf( pod.getUpc().getStyle().getCode() ),
						po.getPo().getReferenceNumber(),
						pod.getUpc().getColor().getName(),
						pod.getUpc().getColorItemCode(),
						pod.getUpc().getColor().getNumber(),
						(po.getPo().getReferenceNumber().endsWith("11")?String.valueOf( pod.getUpc().getSize().getWaist() ):String.valueOf( pod.getUpc().getSize().getWaist()+"x"+pod.getUpc().getSize().getInseam() )),
						String.valueOf(pod.getTotal())
						};
				row.setText( texts );
				row.setData( pod );
			}
		}
		
		getTitleLbl().setText( "Purchase Detail ("+podCount+")" );

		int items = 0;
		int cartons = 0;
		getStatsTbl().removeAll();
		for( PurchaseOrderStatistics stats : po.getPurchaseOrderStatistics()) {
			
			TableItem row = new TableItem(getStatsTbl(),SWT.READ_ONLY);
			String[] texts = {stats.getColor(), String.valueOf(stats.getUnits()), String.valueOf(stats.getBoxes())};
			row.setText(texts);
			row.setData(stats);
			items += stats.getUnits();
			cartons += stats.getBoxes();
		}
		
		TableItem row = new TableItem(getStatsTbl(),SWT.READ_ONLY);
		String[] texts = { "total", String.valueOf( items ), String.valueOf(cartons) };
		row.setText(texts);
		row.setData(null);
		row.setFont(new Font(getDisplay(), "Segoe UI", 8, SWT.BOLD));
	}
}
