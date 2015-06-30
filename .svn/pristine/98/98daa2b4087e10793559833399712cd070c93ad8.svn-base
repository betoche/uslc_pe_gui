package com.uslc.po.gui.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.uslc.po.gui.logic.ClientLogic;
import com.uslc.po.gui.report.PoComparativeByColorItemSize.ItemColorSizeCount.SizeCounts;
import com.uslc.po.jpa.comparator.SizeComparator;
import com.uslc.po.jpa.entity.Color;
import com.uslc.po.jpa.entity.Item;
import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;
import com.uslc.po.jpa.entity.ScanDetail;
import com.uslc.po.jpa.entity.Size;
import com.uslc.po.jpa.entity.Upc;

public class PoComparativeByColorItemSize {
	private PurchaseOrder po = null;
	private List<Color> colorList = null;
	private List<Size> sizeList = null;
	private List<ScanDetail> packedItems = null;
	private List<ItemColorSizeCount> itemColorSizeCountList = null;
	private List<TotalBySizes> totalBySizes = null;
	private Logger log = null;
	
	public PoComparativeByColorItemSize( PurchaseOrder po ) {
		this.po = po;
		
		for( PurchaseOrderDetail pod : po.getPurchaseOrderDetails() ) {
			Upc upc = pod.getUpc();
			if( !getColorList().contains( upc.getColor() ) ){
				getColorList().add( upc.getColor() );
			}
			if( !getSizeList().contains( upc.getSize() ) ){
				getSizeList().add( upc.getSize() );
			}
			Collections.sort( getSizeList(), new SizeComparator() );
			
			if( !pod.getDeleted() ) {
				for( PackingDetail pd : pod.getPackingDetails() ) {
					if( !pd.getDeleted() && pd.getCarton()!=null && !pd.getCarton().getDeleted() ) {
						for( ScanDetail sd : pd.getCarton().getScanDetails() ) {
							if( !sd.getDeleted() ){
								getPackedItems().add(sd);
							}
						}
					}
				}
			}
		}
		
		for( PurchaseOrderDetail pod : po.getPurchaseOrderDetails() ) {
			Upc upc = pod.getUpc();
			/*
			 * ItemColorSizeCount
			 */
			
			if( !containsItemColorSizeCount(upc) ){
				getItemColorSizeCountList().add( new ItemColorSizeCount( upc ) );
			}
		}
		
		for ( Size s : getSizeList() ) {
			getTotalBySizes().add( new TotalBySizes(s) );
		}
	}

	public PurchaseOrder getPo() {
		return po;
	}
	public List<ItemColorSizeCount> getItemColorSizeCountList() {
		if( itemColorSizeCountList == null ) {
			itemColorSizeCountList = new ArrayList<PoComparativeByColorItemSize.ItemColorSizeCount>();
		}
		return itemColorSizeCountList;
	}
	public List<TotalBySizes> getTotalBySizes() {
		if( totalBySizes == null ) {
			totalBySizes = new ArrayList<TotalBySizes>();
		}
		return totalBySizes;
	}
	public boolean containsItemColorSizeCount( Upc upc ){
		boolean contains = false;
		for( ItemColorSizeCount icsc : getItemColorSizeCountList() ){
			Color color = upc.getColor();
			Item item = upc.getItem();
			
			if( color.getId()==icsc.getColor().getId() && item.getId()==icsc.getItem().getId() ){
				contains = true;
				break;
			}
		}
		return contains;
	}
	public List<Color> getColorList() {
		if( colorList == null ) {
			colorList = new ArrayList<Color>();
		}
		return colorList;
	}
	public List<Size> getSizeList(){
		if( sizeList == null ) {
			sizeList = new ArrayList<Size>();
		}
		return sizeList;
	}
	public List<ScanDetail> getPackedItems() {
		if( packedItems == null ) {
			packedItems = new ArrayList<ScanDetail>();
		}
		return packedItems;
	}
	public class ItemColorSizeCount {
		private Color color = null;
		private Item item = null;
		private int totalOrdered = 0;
		private int totalShipped = 0;
		private int totalDifference = 0;
		private double totalPercentage = 0d;
		
		private List<SizeCounts> sizeCountsList = null;
		
		public ItemColorSizeCount( Upc upc ) {
			this.color = upc.getColor();
			this.item = upc.getItem();
			
			getLog().info( "there are " + getSizeList().size() + " sizes in the po." );
			for( Size size : getSizeList() ){
				SizeCounts sc = new SizeCounts( size );
				getSizeCountsList().add( sc );
				
				totalOrdered += sc.getOrdered();
				totalShipped += sc.getShipped();
			}
			
			totalDifference = totalOrdered - totalShipped;
			totalPercentage = new Double(totalShipped)/new Double(totalOrdered);
		}
		
		public int getTotalDifference() {
			return totalDifference;
		}
		public double getTotalPercentage() {
			return totalPercentage;
		}
		public int getTotalOrdered() {
			return totalOrdered;
		}
		public int getTotalShipped() {
			return totalShipped;
		}
		public Color getColor() {
			return color;
		}
		public Item getItem() {
			return item;
		}
		public List<SizeCounts> getSizeCountsList() {
			if( sizeCountsList == null ){
				sizeCountsList = new ArrayList<PoComparativeByColorItemSize.ItemColorSizeCount.SizeCounts>();
			}
			return sizeCountsList;
		}

		public class SizeCounts {
			private Size size = null;
			private int ordered = 0;
			private int shipped = 0;
			private int difference = 0;
			private double percentage = 0d;
			
			public SizeCounts( Size size ) {
				this.size = size;
				for( PurchaseOrderDetail pod : getPo().getPurchaseOrderDetails() ) {
					Upc upc = pod.getUpc();
					if( getColor().getId()==upc.getColor().getId() && getItem().getId()==upc.getItem().getId() && size.getId()==upc.getSize().getId() ) {
						ordered += pod.getTotal();
						if( !pod.getDeleted() ) {
							for( PackingDetail pd : pod.getPackingDetails() ) {
								shipped += ClientLogic.getNumberOfScannedItems( pd );
							}
						}
					}
				}
				
				difference = ordered - shipped;
				percentage = new Double(shipped)/new Double(ordered);
				getLog().info( "shipped["+shipped+"]/ordered["+ordered+"]=percentage["+percentage+"]" );
			}

			public Size getSize() {
				return size;
			}
			public int getOrdered() {
				return ordered;
			}
			public int getShipped() {
				return shipped;
			}
			public int getDifference() {
				return difference;
			}
			public double getPercentage() {
				return percentage;
			}
		}
	}
	public class TotalBySizes {
		private Size size = null;
		private int ordered = 0;
		private int shipped = 0;
		private int difference = 0;
		private double percentage = 0d;
		
		private TotalBySizes( Size size ) {
			this.size = size;
			
			for( ItemColorSizeCount icsc : getItemColorSizeCountList() ) {
				for( SizeCounts sc : icsc.getSizeCountsList() ) {
					if( size.getId() == sc.getSize().getId() ) {
						ordered = ordered + sc.getOrdered();
						shipped = shipped + sc.getShipped();
						difference = difference + sc.getDifference();
					}
				}
			}
			
			percentage = new Double(shipped)/new Double(ordered);
		}

		public Size getSize() {
			return size;
		}
		public int getOrdered() {
			return ordered;
		}
		public int getShipped() {
			return shipped;
		}
		public int getDifference() {
			return difference;
		}
		public double getPercentage() {
			return percentage;
		}
	}
	
	private Logger getLog(){
		if( log == null ) {
			log = Logger.getLogger( PoComparativeByColorItemSize.class );
			PropertyConfigurator.configure("log4j.properties");
		}
		return log;
	}
}
