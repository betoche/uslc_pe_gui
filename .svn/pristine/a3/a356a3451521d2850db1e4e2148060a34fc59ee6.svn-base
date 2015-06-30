package com.uslc.po.gui.client;

import com.uslc.po.gui.logic.ClientLogic;
import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;

public class ClientPurchaseOrderDetailTable {
	private PurchaseOrderDetail pod = null;
	private String upc = "";
	private int qty = 0;
	private int carton = 0;
	private int ready = 0;
	private int row = 0;
	
	public ClientPurchaseOrderDetailTable( PurchaseOrderDetail pod, int row ){
		this.pod = pod;
		this.row = row;
	}

	public int getRow(){
		return row;
	}
	public PurchaseOrderDetail getPod() {
		return pod;
	}
	public String getUpc(){
		if( upc==null || upc.compareTo("")==0 ){
			upc = getPod().getUpc().getUpcCode();
		}
		return upc;
	}
	public int getQty() {
		if( qty == 0 ){
			for( PackingDetail pd : getPod().getPackingDetails() ){
				if( !pd.getDeleted() )
					qty += pd.getQuantity();
			}
		}
		return qty;
	}
	public int getCarton() {
		if( carton == 0 ){
			for (PackingDetail pd : getPod().getPackingDetails()) {
				if( !pd.getDeleted() ){
					carton++;
				}
			}
		}
		return carton;
	}
	public int getReady() {
		if( ready == 0 ){
			for( PackingDetail pd : getPod().getPackingDetails() ) {
				if( !pd.getDeleted() && pd.getCarton()!=null && ClientLogic.getNumberOfScannedItems(pd.getCarton())>0 )
					ready++;
			}
		}
		return ready;
	}
	public String[] getColumnValues(){
		return new String[]{
					String.valueOf( row ), 
					getUpc(),
					String.valueOf(getQty()),
					String.valueOf(getCarton()),
					String.valueOf(getReady())
				};
	}
}