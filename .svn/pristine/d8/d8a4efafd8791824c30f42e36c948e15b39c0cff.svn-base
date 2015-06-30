package com.uslc.po.gui.client;

import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.ScanDetail;
import com.uslc.po.jpa.entity.Upc;

public class ClientScannedItemsTable {
	private int order = 0;
	private ScanDetail sd;
	private Upc upc = null;
	private String size = "";
	private String color = "";
	private PurchaseOrder po = null;
	
	public ClientScannedItemsTable( int order, ScanDetail sd ) {
		this.order = order;
		this.sd = sd;
	}

	public ScanDetail getSd(){
		return sd;
	}
	public int getOrder() {
		return order;
	}
	public Upc getUpc() {
		if( upc == null ){
			upc = sd.getUpc();
		}
		return upc;
	}
	public String getSize() {
		if( size==null || size.compareTo("")==0 ){
			if( getPo().getReferenceNumber().endsWith("11") )
				size = String.valueOf( getUpc().getSize().getWaist() );
			else
				size = getUpc().getSize().getWaist()+"x"+getUpc().getSize().getInseam();
		}
		return size;
	}
	public String getColor() {
		if( color == null || color.compareTo("")==0 ){
			color = getUpc().getColor().getName();
		}
		return color;
	}
	public PurchaseOrder getPo() {
		if( po==null ){
			po = sd.getCarton().getPackingDetail().getPurchaseOrderDetail().getPurchaseOrder();
		}
		return po;
	}

	public String[] getColumnValues(){
		return new String[]{
				String.valueOf( getOrder() ), 
				getUpc().getUpcCode(), 
				getSize(), 
				getColor()
		};
	}
}