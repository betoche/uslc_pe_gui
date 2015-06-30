package com.uslc.po.gui.client;

import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.ScanDetail;
import com.uslc.po.jpa.entity.Size;

public class ClientPackingDetailTable {
	private PackingDetail pd = null;
	private String color = "";
	private String size = "";
	private int sku = 0;
	private int qty = 0;
	private int scanned = 0;
	
	public String getColor() {
		if( color == null || color.compareTo("")==0 ){
			color = pd.getPurchaseOrderDetail().getUpc().getColor().getName();
		}
		return color;
	}
	public String getSize() {
		if( size==null || size.compareTo("")==0 ){
			Size s = pd.getPurchaseOrderDetail().getUpc().getSize();
			if( pd.getPurchaseOrderDetail().getPurchaseOrder().getReferenceNumber().endsWith("11") ){
				size = String.valueOf( s.getWaist() );
			}else{
				size = String.valueOf( s.getWaist() + "x" + s.getInseam() );
			}
			 
		}
		return size;
	}
	public ClientPackingDetailTable( PackingDetail pd ){
		this.pd = pd;
	}

	public PackingDetail getPd() {
		return pd;
	}

	public int getSku() {
		if( sku==0 ){
			sku = pd.getSku();
		}
		return sku;
	}
	public int getQty(){
		if( qty == 0 ){
			qty = pd.getQuantity();
		}
		return qty;
	}
	public int getScanned() {
		if( scanned == 0 ){
			if(pd.getCarton()!=null){
				if(pd.getCarton().getScanDetails()!=null){
					for (ScanDetail sd : pd.getCarton().getScanDetails() ) {
						if( !sd.getDeleted() ){
							scanned++;
						}
					}
				}
			}
		}
		return scanned;
	}
	public String[] getColumnValues(){
		return new String[]{ 
					getSize(), 
					getColor(), 
					String.valueOf( getSku() ), 
					String.valueOf( getQty() ), 
					String.valueOf( getScanned() )
				};
	}
}
