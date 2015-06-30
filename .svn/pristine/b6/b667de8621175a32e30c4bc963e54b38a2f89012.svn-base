package com.uslc.po.gui.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.uslc.po.gui.logic.ClientLogic;
import com.uslc.po.jpa.comparator.SizeComparator;
import com.uslc.po.jpa.entity.Color;
import com.uslc.po.jpa.entity.Item;
import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;
import com.uslc.po.jpa.entity.Size;
import com.uslc.po.jpa.entity.Upc;

public class SizeDistributionByColorReport {
	private PurchaseOrder po = null;
	private HashMap<String, ColorScanDistribution> colorSizeScanDistribution = null;
	
	
	public SizeDistributionByColorReport( PurchaseOrder po ){
		this.po = po;
		
		HashSet<String> colorsItems = new HashSet<String>();
		for (PurchaseOrderDetail pod : po.getPurchaseOrderDetails()) {
			colorsItems.add( pod.getUpc().getColor().getName() + "-" + pod.getUpc().getItem().getCode() );
		}
		
		for( String colorItem : colorsItems ) {
			getColorSizeScanDistribution().put( colorItem, new ColorScanDistribution(colorItem) );
		}
	}
	
	public PurchaseOrder getPurchaseOrder() {
		return po;
	}
	
	public HashMap<String, ColorScanDistribution> getColorSizeScanDistribution(){
		if( colorSizeScanDistribution == null ){
			colorSizeScanDistribution = new HashMap<String,ColorScanDistribution>();
		}
		return colorSizeScanDistribution;
	}
	
	public class ColorScanDistribution {
		private String colorItem = "";
		private List<SizeScanDistribution> sizeScanDistribution = null;
		
		public ColorScanDistribution( String colorItem ){
			this.colorItem = colorItem;
			
			List<Size> sizes = new ArrayList<Size>();
			
			for( PurchaseOrderDetail pod : getPurchaseOrder().getPurchaseOrderDetails() ){
				Upc upc = pod.getUpc();
				String cName = upc.getColor().getName();
				String iCode = upc.getItem().getCode();
				
				String tmpColorItem = cName + "-" + iCode;
				if( tmpColorItem.compareTo(colorItem)==0 ){
					sizes.add( upc.getSize() );
				}
			}
			
			Collections.sort(sizes, new SizeComparator());
			for( Size size : sizes ){
				getSizeScanDistribution().add( new SizeScanDistribution(getColorItem(), size) );
			}
		}
		
		public String getColorItem(){
			return colorItem;
		}
		public List<SizeScanDistribution> getSizeScanDistribution(){
			if( sizeScanDistribution == null ){
				sizeScanDistribution = new ArrayList<SizeScanDistribution>();
			}
			return sizeScanDistribution;
		}
	}
	
	public class SizeScanDistribution {
		private Size size = null;
		private int poQty = 0;
		private int packedQty = 0;
		
		public SizeScanDistribution( String colorItem, Size size ){
			this.size = size;
			
			for( PurchaseOrderDetail pod : getPurchaseOrder().getPurchaseOrderDetails() ){
				Upc upc = pod.getUpc();
				Color tmpColor = upc.getColor();
				Item tmpItem = upc.getItem();
				Size tmpSize = upc.getSize();
				String tmpColorItem = tmpColor.getName() + "-" + tmpItem.getCode();
				
				if( tmpColorItem.compareTo( colorItem )==0 && size.getId()==tmpSize.getId() ){
					poQty = poQty + pod.getTotal();
					
					for( PackingDetail pd : pod.getPackingDetails() ){
						if( !pd.getDeleted() && pd.getCarton()!=null && !pd.getCarton().getDeleted() ){
							packedQty = packedQty + ClientLogic.getNumberOfScannedItems( pd.getCarton() );
						}
					}
				}
			}
		}

		public Size getSize() {
			return size;
		}
		public int getPoQty() {
			return poQty;
		}
		public int getPackedQty() {
			return packedQty;
		}
	}
}
