package com.uslc.po.gui.client;

import java.util.ArrayList;
import java.util.List;

import com.uslc.po.jpa.entity.Carton;
import com.uslc.po.jpa.entity.Color;
import com.uslc.po.jpa.entity.Item;
import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;
import com.uslc.po.jpa.entity.ScanDetail;
import com.uslc.po.jpa.entity.Size;
import com.uslc.po.jpa.entity.Upc;

/*
 * @Deprecated, use ClientPurchaseOrderSumary class instead
 */
@Deprecated
public class PurchaseOrderSummary {
	private PurchaseOrder po = null;
	private OriginalPurchaseOrder original = null;
	private WorkedPurchaseOrder worked = null;
	
	private List<Item> itemList = null;
	private List<Color> colorList = null;
	private List<Size> sizeList = null;
	private List<Upc> upcList = null;
	private List<PackingDetail> packingDetailList 	= null;
	private List<Carton> cartonList = null;
	
	public PurchaseOrderSummary( PurchaseOrder po ){
		this.po = po;
		
		upcList = new ArrayList<Upc>();
		itemList = new ArrayList<Item>();
		colorList = new ArrayList<Color>();
		sizeList = new ArrayList<Size>();
		packingDetailList = new ArrayList<PackingDetail>();
		cartonList = new ArrayList<Carton>();
		
		for( PurchaseOrderDetail pod : po.getPurchaseOrderDetails() ) {
			Upc upc = pod.getUpc();
			
			if( !upcList.contains(upc) )upcList.add(upc);
			if( !itemList.contains( upc.getItem() ) )itemList.add(upc.getItem());
			if( !colorList.contains( upc.getColor() ) )colorList.add(upc.getColor());
			if( !sizeList.contains( upc.getSize() ) )sizeList.add( upc.getSize() );
			for( PackingDetail pd : pod.getPackingDetails() ) {
				if( !packingDetailList.contains( pd ) )packingDetailList.add( pd );
				if( pd.getCarton()!=null && !pd.getCarton().getDeleted() ){
					if( !cartonList.contains( pd.getCarton() ) )cartonList.add( pd.getCarton() );
				}
			}
		}
	}

	public PurchaseOrder getPo() {
		return po;
	}
	public OriginalPurchaseOrder getOriginal() {
		if( original == null ) {
			original = new OriginalPurchaseOrder();
		}
		return original;
	}
	public WorkedPurchaseOrder getWorked() {
		if( worked == null ) {
			worked = new WorkedPurchaseOrder();
		}
		return worked;
	}
	public List<PackingDetail> getPackingDetailList() {
		return packingDetailList;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public List<Color> getColorList() {
		return colorList;
	}
	public List<Size> getSizeList() {
		return sizeList;
	}
	public List<Upc> getUpcList() {
		return upcList;
	}
	public List<Carton> getCartonList() {
		return cartonList;
	}

	public int getRemainingUpcs() {
		return getOriginal().getUpcCount() - getWorked().getUpcCount();
	}
	public int getRemainingItems() {
		return getOriginal().getItemsCount()- getWorked().getItemsCount();
	}
	public int getRemainingCartons() {
		return getOriginal().getCartonsCount() - getWorked().getCartonsCount();
	}
	public int getRemainingColors() {
		return getOriginal().getColorsCount() - getWorked().getColorsCount();
	}
	public int getRemainingSizes(){
		return getOriginal().getSizesCount() - getWorked().getSizesCount();
	}
	
	public class OriginalPurchaseOrder {
		private int upcCount = 0;
		private int itemsCount = 0;
		private int cartonsCount = 0;
		private int colorsCount = 0;
		private int sizesCount = 0;
		
		public OriginalPurchaseOrder(){
			upcCount = getUpcList().size();
			for( PurchaseOrderDetail pod : getPo().getPurchaseOrderDetails() ) {
				itemsCount = itemsCount + pod.getTotal();
			}
			cartonsCount = getPackingDetailList().size();
			colorsCount = getColorList().size();
			sizesCount = getSizeList().size();
		}

		public int getUpcCount() {
			return upcCount;
		}
		public int getItemsCount() {
			return itemsCount;
		}
		public int getCartonsCount() {
			return cartonsCount;
		}
		public int getColorsCount() {
			return colorsCount;
		}
		public int getSizesCount() {
			return sizesCount;
		}
	}
	public class WorkedPurchaseOrder {
		private int upcCount = 0;
		private int itemsCount = 0;
		private int cartonsCount = 0;
		private int colorsCount = 0;
		private int sizesCount = 0;
		
		public WorkedPurchaseOrder() {
			boolean isPodComplete = true;
			for( PurchaseOrderDetail pod : getPo().getPurchaseOrderDetails() ){
				for( PackingDetail pd : pod.getPackingDetails() ) {
					if( pd.getCarton()!=null && pd.getCarton().getCompleted() ){
						cartonsCount++;
						
						for( ScanDetail sd : pd.getCarton().getScanDetails() ) {
							if( !sd.getDeleted() ) {
								itemsCount++;
							}
						}
					} else {
						isPodComplete = false;
					}
				}
				
				if( isPodComplete )upcCount++;
			}
			
			for (Color color : getColorList()) {
				boolean isColorCompleted = true;
				
				podIt:
				for( PurchaseOrderDetail pod : getPo().getPurchaseOrderDetails() ){
					Upc upc = pod.getUpc();
					
					if( upc.getColor().getId() == color.getId() ){
						for( PackingDetail pd : pod.getPackingDetails() ){
							if( pd.getCarton()==null || !pd.getCarton().getCompleted() ) {
								isColorCompleted = false;
								break podIt;
							}
						}
					}
				}
				
				if( isColorCompleted )colorsCount++;
			}
			
			for( Size size : getSizeList() ) {
				boolean isSizeCompleted = true;
				
				podIt:
					for( PurchaseOrderDetail pod : getPo().getPurchaseOrderDetails() ) {
						if( pod.getUpc().getSize().getId() == size.getId() ){
							for( PackingDetail pd : pod.getPackingDetails() ){
								if( pd.getCarton()==null || !pd.getCarton().getCompleted() ) {
									isSizeCompleted = false;
									break podIt;
								}
							}
						}
					}
				
				if( isSizeCompleted )sizesCount++;
			}
		}

		public int getUpcCount() {
			return upcCount;
		}
		public int getItemsCount() {
			return itemsCount;
		}
		public int getCartonsCount() {
			return cartonsCount;
		}
		public int getColorsCount() {
			return colorsCount;
		}
		public int getSizesCount() {
			return sizesCount;
		}
	}
}