package com.uslc.pe.gui.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ibm.icu.util.Calendar;
import com.uslc.pe.gui.master.NewPurchaseOrderComposite.PODetailData;
import com.uslc.pe.jpa.entity.PackingDetail;
import com.uslc.pe.jpa.entity.PurchaseOrderDetail;
import com.uslc.pe.jpa.entity.Upc;
import com.uslc.pe.jpa.logic.PackingDetailRepo;
import com.uslc.pe.jpa.logic.PurchaseOrderDetailRepo;
import com.uslc.pe.jpa.logic.PurchaseOrderRepo;
import com.uslc.pe.jpa.logic.UpcRepo;

public class PurchaseOrder {
	private com.uslc.pe.jpa.entity.PurchaseOrder po = null;
	private com.uslc.pe.jpa.entity.PurchaseOrderDetail[] details = null;
	private HashMap<Integer,PackingDetail[]> packingDetails = null;
	private PoStatus status = null;
	private Logger log = null;
	private HashMap<String,PoStatistics> poStats = null;
	
	public PurchaseOrder( com.uslc.pe.jpa.entity.PurchaseOrder po ){
		this.po = po;
		init();
	}
	private void init(){
	}
	public static PurchaseOrder[] getPurchaseOrders(){
		PurchaseOrder[] pos = new PurchaseOrder[0];
		
		List<com.uslc.pe.jpa.entity.PurchaseOrder> list = PurchaseOrderRepo.findAll();
		if( list!=null ){
			pos = new PurchaseOrder[list.size()];
			for ( int i = 0 ; i < list.size() ; i++ ) {
				pos[i] = new PurchaseOrder( list.get(i) );
			}
		}
		
		return pos;
	}
	public com.uslc.pe.jpa.entity.PurchaseOrder getPo(){
		return po;
	}
	private PurchaseOrderDetail[] getDetails(){
		if( details == null ){
			details = new PurchaseOrderDetail[0];
			List<PurchaseOrderDetail> dts = po.getPurchaseOrderDetails();
			if( dts!=null ){
				details = new PurchaseOrderDetail[dts.size()];
				for( int i = 0 ; i < dts.size() ; i++ ){
					details[i] = dts.get(i);
				}
			}
		}
		return details;
	}
	private HashMap<Integer,PackingDetail[]> getPackingDetail() throws Exception{
		if( packingDetails == null ){
			packingDetails = new HashMap<Integer,PackingDetail[]>();
			for (PurchaseOrderDetail pod : getDetails()) {
				PackingDetail[] pds = new PackingDetail[0];
				List<PackingDetail> pdList = PackingDetailRepo.findByPurchaseOrderDetail( pod );
				if( pdList!=null ){
					pds = new PackingDetail[pdList.size()];
					for( int i = 0 ; i < pdList.size() ; i++ ){
						pds[i] = pdList.get(i);
					}
				}
				packingDetails.put( pod.getId(), pds);
			}
			
		}
		return packingDetails;
	}
	public String getCode(){
		return getPo().getReferenceNumber();
	}
	public PoStatus getStatus() throws Exception {
		if( status==null ){
			int finished = 0;
			int missing = 0;
			int total = 0;
			
			for ( PurchaseOrderDetail pod : getDetails() ) {
				for (PackingDetail pd : getPackingDetail().get(pod.getId())) {
					if( !pd.getDeleted() ){
						boolean completed = true;
						if(pd.getCarton()==null){
							completed=false;
						}else if( pd.getCarton().getDeleted() ) {
							completed=false;
						}
						
						if( completed ) {
							finished++;
						} else {
							missing++;
						}
						total++;
					}
				}
			}
			
			//getLog().info( "total["+total+"]{finished:"+finished+",pending:"+missing+"}" );
			
			status = new PoStatus(finished, missing, total);
		}
		return status;
	}
	
	private Logger getLog() {
		if( log == null ){
			log = Logger.getLogger( PurchaseOrder.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	public static int getNextId() {
		return PurchaseOrderRepo.getNextId();
	}
	public static String getNextReferenceNumber() {
		String lastRef = PurchaseOrderRepo.getLastReferenceNumber();
		String newRef = "E";
		if( lastRef.compareTo("")==0 ){
			newRef = "E0000001-0001";
		}else{
			int n = Integer.parseInt( lastRef.substring( 1, lastRef.indexOf("-") ) );
			System.out.println( n + " has " + String.valueOf( n ).length() + " digits" );
			n=n+1;
			for( int i = 0 ; i < (7-String.valueOf( n ).length()) ; i++ ){
				newRef += "0";
			}
			newRef += n+"-0001";
		}
		return newRef;
	}
	
	public static com.uslc.pe.jpa.entity.PurchaseOrder createPurchaseOrderCascade( String departmentNumber, 
			String referenceNumber,
			String shipFrom,
			String shipTo,
			int totalCartons,
			int totalItems,
			List<PODetailData> poDetails ) throws Exception{
		com.uslc.pe.jpa.entity.PurchaseOrder po = null;
		try{
			if(PurchaseOrderRepo.findPOByRefNumber( referenceNumber )==null ) {
				po = new com.uslc.pe.jpa.entity.PurchaseOrder();
				List<PurchaseOrderDetail> purchaseOrderDetailList = new ArrayList<PurchaseOrderDetail>();
				Date timestamp = Calendar.getInstance().getTime();
				
				po.setDeleted(false);
				po.setDepartmentNumber(departmentNumber);
				po.setPurchaseOrderDetails(purchaseOrderDetailList);
				po.setReferenceNumber(referenceNumber);
				po.setShipFrom(shipFrom);
				po.setShipTo(shipTo);
				po.setTotalCartons(totalCartons);
				po.setTotalItems(totalItems);
				po.setTimestamp( timestamp );
				int sku = 0;
				
				for (PODetailData ob : poDetails) {
					PurchaseOrderDetail pod = new PurchaseOrderDetail();
					Upc upc = ob.getUpc();
					int qty = ob.getQty();
					int itemsPerCarton = ob.getItemsPerCarton();
					
					List<PackingDetail> packingDetailList = new ArrayList<PackingDetail>();
					boolean preticketed = ob.isPreticketed();
					
					pod.setDeleted(false);
					pod.setPackingDetails(packingDetailList);
					pod.setPreticketed(preticketed);
					pod.setPurchaseOrder(po);
					pod.setTotal(qty);
					pod.setUpc(upc);
					pod.setTimestamp(timestamp);
					
					int cartons = (int)Math.ceil( new Double(qty)/new Double(itemsPerCarton) );
					int qtyControl = qty;
					for( int i = 0 ; i < cartons ; i++ ){
						int itemsInCarton = 0;
						if( qtyControl < itemsPerCarton ){
							itemsInCarton = qtyControl;
						}else{
							itemsInCarton = itemsPerCarton;
						}
						qtyControl = qtyControl - itemsInCarton;
						
						PackingDetail pd = new PackingDetail();
						pd.setDeleted(false);
						pd.setPurchaseOrderDetail(pod);
						pd.setQuantity(itemsInCarton);
						pd.setSku(++sku);
						
						packingDetailList.add(pd);
					}
					purchaseOrderDetailList.add(pod);
				}
				
				if( !PurchaseOrderRepo.getJpa().persist(po) ){
					throw new Exception( "there was a problem persisting the purchase order" );
				}
			}else{
				throw new Exception( "purchase order reference number already exists in the system." );
			}
		}catch( Exception e ){
			e.printStackTrace();
			throw new Exception( e.getMessage() );
		}
		return po;
	}
	
	public static com.uslc.pe.jpa.entity.PurchaseOrder createPurchaseOrder( int departmentNumber, 
			String referenceNumber,
			String shipFrom,
			String shipTo,
			int totalCartons,
			int totalItems,
			List<Object[]> poDetails ) throws Exception{
		com.uslc.pe.jpa.entity.PurchaseOrder po = null;
		
		po.setReferenceNumber(referenceNumber);
		po.setShipFrom(shipFrom);po.setShipTo(shipTo);
		po.setTotalCartons(totalCartons);
		po.setTotalItems(totalItems);
		po.setDeleted(true);
		
		po = PurchaseOrderRepo.createPurchaseOrder( po );
		if( po.getId()>0 ){
			List<PurchaseOrderDetail> purchaseOrderDetailList = new ArrayList<PurchaseOrderDetail>();
			int sku = 0;
			for (Object[] ob : poDetails) {
				Upc upc = (Upc)ob[0];
				int qty = Integer.parseInt( String.valueOf( ob[1] ) );
				int itemsPerCarton = Integer.parseInt( String.valueOf( ob[2] ) );
				boolean preticketed = Boolean.parseBoolean( String.valueOf( ob[3] ) );
				
				PurchaseOrderDetail pod = PurchaseOrderDetailRepo.createPurchaseOrderDetail( preticketed, qty, po, upc);
				int cartons = (int)Math.ceil( new Double(totalItems)/new Double(itemsPerCarton) );
				List<PackingDetail> packingDetailList = new ArrayList<PackingDetail>();
				for( int i = 0 ; i < cartons ; i++ ){
					int itemsInCarton = 0;
					if( qty < itemsPerCarton ){
						itemsInCarton = qty;
					}else{
						itemsInCarton = itemsPerCarton;
					}
					qty = qty - itemsInCarton;
					
					PackingDetail pd = PackingDetailRepo.createPackingDetail( itemsInCarton, ++sku, pod );
					packingDetailList.add(pd);
				}
				PurchaseOrderDetailRepo.setDeleted(pod, false);
				purchaseOrderDetailList.add(pod);
			}
			
		}else{
			return null;
		}
		
		return po;
	}
	
	public static void main( String[] args ){
		List<PurchaseOrderDetail> podList = new ArrayList<PurchaseOrderDetail>();
		Upc upc = UpcRepo.findAll().get(0);
		
		com.uslc.pe.jpa.entity.PurchaseOrder po = new com.uslc.pe.jpa.entity.PurchaseOrder();
		po.setDepartmentNumber("1234");
		po.setPurchaseOrderDetails(podList);
		po.setReferenceNumber("98789798");
		po.setShipFrom("from");
		po.setShipTo("to");
		po.setTotalCartons(4);
		po.setTotalItems(40);
		
		PurchaseOrderDetail pod1 = new PurchaseOrderDetail();
		pod1.setPreticketed(false);
		pod1.setPurchaseOrder(po);
		pod1.setTotal(27);
		pod1.setUpc(upc);
		pod1.setDeleted(false);
		
		PurchaseOrderDetail pod2 = new PurchaseOrderDetail();
		pod2.setPreticketed(false);
		pod2.setPurchaseOrder(po);
		pod2.setTotal(13);
		pod2.setUpc(upc);
		pod2.setDeleted(false);
		
		podList.add(pod1);
		podList.add(pod2);
		
		try {
			PurchaseOrderRepo.getJpa().persist( po );
			System.out.println( po.getId() + ", " + pod1.getId() + ", " + pod2.getId() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*String lastRef = "E0001001-0001";
		String newRef = "E";
		int n = Integer.parseInt( lastRef.substring( 1, lastRef.indexOf("-") ) );
		System.out.println( n + " has " + String.valueOf( n ).length() + " digits" );
		n=n+1;
		for( int i = 0 ; i < (7-String.valueOf( n ).length()) ; i++ ){
			newRef += "0";
		}
		newRef += n+"-001";
		System.out.println( lastRef + "\n" + newRef );*/
	}
	public HashMap<String,PoStatistics> getPoStatistics() {
		if( poStats == null ) {
			poStats = new HashMap<String,PoStatistics>();
			
			for (PurchaseOrderDetail pod : getDetails() ) {
				if( poStats.get( pod.getUpc().getColor().getNumber() )==null ) {
					Upc upc = pod.getUpc();
					int boxes = 0;
					for (PackingDetail pd : pod.getPackingDetails()) {
						if( !pd.getDeleted() ) {
							boxes++;
						}
					}
					poStats.put( 
							upc.getColor().getNumber(), 
							new PoStatistics(upc.getColor().getName(), pod.getTotal(), boxes)
					);
				} else {
					Upc upc = pod.getUpc();
					int boxes = 0;
					for (PackingDetail pd : pod.getPackingDetails()) {
						if( !pd.getDeleted() ) {
							boxes++;
						}
					}
					PoStatistics pos = poStats.get( pod.getUpc().getColor().getNumber() );
					boxes += pos.getBoxes();
					poStats.put( 
							upc.getColor().getNumber(), 
							new PoStatistics(pos.getColor(), pod.getTotal()+pos.getUnits(), boxes )
					);
				}
			}
		}
		return poStats;
	}
	
	public class PoStatistics {
		private String color = "";
		private int units = 0;
		private int boxes = 0;
		
		
		public PoStatistics( String color, int units, int boxes ) {
			this.color = color;
			this.units = units;
			this.boxes = boxes;
		}

		public String getColor() {
			return color;
		}
		public int getUnits() {
			return units;
		}
		public int getBoxes() {
			return boxes;
		}
	}

	public class PoStatus {
		private int finished = 0;
		private int missing = 0;
		private int total = 0;
		
		public PoStatus( int finished, int missing, int total ) {
			this.finished = finished;
			this.missing = missing;
			this.total = total;
		}

		public int getFinished() {
			return finished;
		}
		public void setFinished(int finished) {
			this.finished = finished;
		}
		public int getMissing() {
			return missing;
		}
		public void setMissing(int missing) {
			this.missing = missing;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		
		public String toString(){
			String s = "";
			if( getFinished()==getDetails().length ){
				s = "compl ["+getTotal()+"]";
			}else{
				s = "pend [ "+getFinished()+"/"+getTotal()+" ]";
			}
			return s; 
		}
	}
}
