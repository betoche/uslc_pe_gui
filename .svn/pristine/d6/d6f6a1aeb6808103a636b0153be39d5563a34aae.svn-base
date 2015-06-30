package com.uslc.po.gui.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.uslc.po.jpa.entity.Carton;
import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;
import com.uslc.po.jpa.entity.ScanDetail;
import com.uslc.po.jpa.entity.Upc;

public class ClientPurchaseOrderSumary {
	private PurchaseOrder po = null;
	private HashMap<String, PurchaseOrderStats> poInfo = null;
	
	public ClientPurchaseOrderSumary( PurchaseOrder po ){
		this.po = po;
		
		HashSet<String> colorItem = new HashSet<String>();
		for( PurchaseOrderDetail pod : po.getPurchaseOrderDetails() ){
			Upc upc = pod.getUpc();
			colorItem.add( upc.getColor().getName() + "-" + upc.getItem().getCode() );
		}
		
		for (String str : colorItem) {
			getPoInfo().put(str, new PurchaseOrderStats(str));
		}
	}
	
	public void addInfoToComposites( Composite colors, Group poDetails, Group packingDetails ) {
		for( Control c : colors.getChildren() ) {
			c.dispose();
		}
		for( Control c : poDetails.getChildren() ) {
			c.dispose();
		}
		for( Control c : packingDetails.getChildren() ) {
			c.dispose();
		}
		
		Iterator<Entry<String,PurchaseOrderStats>> it = getPoInfo().entrySet().iterator();

		GridData gd = new GridData( GridData.FILL_HORIZONTAL );
		//gd.horizontalAlignment = GridData.CENTER;
		gd.grabExcessHorizontalSpace = true;
		gd.heightHint = 16;
		
		FontData[] fsd = colors.getFont().getFontData();
		Font f = null;
		Font fc = null;
		for (FontData fd : fsd) {
			f = new Font( colors.getDisplay(), fd.getName(), 7, fd.getStyle() );
			fc = new Font( colors.getDisplay(), fd.getName(), 6, fd.getStyle() );
		}
		
		Label blankSpace = new Label( colors, SWT.NONE );
		blankSpace.setFont(fc);
		blankSpace.setLayoutData( gd );
		
		Label origQtyTitle = new Label( poDetails, SWT.NONE);
		origQtyTitle.setText( "Qty" );
		origQtyTitle.setFont(f);
		origQtyTitle.setLayoutData( gd );
		origQtyTitle.setAlignment( SWT.CENTER );
		Label origBoxTitle = new Label( poDetails, SWT.NONE);
		origBoxTitle.setText( "Boxes" );
		origBoxTitle.setFont(f);
		origBoxTitle.setLayoutData( gd );
		origBoxTitle.setAlignment( SWT.CENTER );
		
		Label workQtyTitle = new Label( packingDetails, SWT.NONE);
		workQtyTitle.setText( "Qty" );
		workQtyTitle.setFont(f);
		workQtyTitle.setLayoutData( gd );
		workQtyTitle.setAlignment( SWT.CENTER );
		Label workBoxTitle = new Label( packingDetails, SWT.NONE);
		workBoxTitle.setText( "Boxes" );
		workBoxTitle.setFont(f);
		workBoxTitle.setLayoutData( gd );
		workBoxTitle.setAlignment( SWT.CENTER );
		
		int rows = 0;
		while( it.hasNext() ){
			rows++;
			Entry<String, PurchaseOrderStats> entry = it.next();
			
			Label color = new Label( colors, SWT.NONE );
			color.setText( entry.getKey() );
			color.setFont(fc);
			color.setLayoutData( gd );
			//color.setAlignment( SWT.CENTER );
			
			Label origQty = new Label( poDetails, SWT.NONE );
			origQty.setText( String.valueOf( entry.getValue().getOriginalQty() ) );
			origQty.setFont(f);
			origQty.setLayoutData( gd );
			origQty.setAlignment( SWT.CENTER );
			Label origBoxes = new Label( poDetails, SWT.NONE );
			origBoxes.setText( String.valueOf( entry.getValue().getOriginalBoxes() ) );
			origBoxes.setFont( f );
			origBoxes.setLayoutData( gd );
			origBoxes.setAlignment( SWT.CENTER );
			Label workQty = new Label( packingDetails, SWT.NONE );
			workQty.setText( String.valueOf( entry.getValue().getWorkedQty() ) );
			workQty.setFont( f );
			workQty.setLayoutData( gd );
			workQty.setAlignment( SWT.CENTER );
			Label workBoxes = new Label( packingDetails, SWT.NONE );
			workBoxes.setText( String.valueOf( entry.getValue().getWorkedBoxes() ) );
			workBoxes.setFont( f );
			workBoxes.setLayoutData( gd );
			workBoxes.setAlignment( SWT.CENTER );
		}
		
		colors.layout();
		poDetails.layout();
		packingDetails.layout();
		colors.getParent().layout();
		((ScrolledComposite)colors.getParent().getParent()).setContent(colors.getParent());
		((ScrolledComposite)colors.getParent().getParent()).setMinSize(colors.getParent().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		((ScrolledComposite)colors.getParent().getParent()).layout();
	}
	
	public HashMap<String, PurchaseOrderStats> getPoInfo() {
		if( poInfo == null ) {
			poInfo = new HashMap<String, PurchaseOrderStats>();
		}
		return poInfo;
	}
	public PurchaseOrder getPo() {
		return po;
	}


	public class PurchaseOrderStats {
		private String colorItem = "";
		private int originalQty = 0;
		private int originalBoxes = 0;
		private int workedQty = 0;
		private int workedBoxes = 0;
		
		public PurchaseOrderStats( String colorItem ) {
			this.colorItem = colorItem;
			for( PurchaseOrderDetail pod : getPo().getPurchaseOrderDetails() ){
				Upc upc = pod.getUpc();
				String ci = upc.getColor().getName() + "-" + upc.getItem().getCode();
				
				if( ci.compareTo(colorItem)==0 ){
					originalQty += pod.getTotal();
					originalBoxes += pod.getPackingDetails().size();
				
					if( !pod.getDeleted() ){
						for( PackingDetail pd : pod.getPackingDetails() ){
							if( !pd.getDeleted() ) {
								Carton carton = pd.getCarton();
								if( carton!=null && !carton.getDeleted() ){
									boolean boxHasAtLeastOneScan = false;
									
									for( ScanDetail sd : carton.getScanDetails() ){
										if( !sd.getDeleted() ){
											boxHasAtLeastOneScan = true;
											workedQty++;
										}
									}
									
									if( boxHasAtLeastOneScan )
										workedBoxes++;
								}
							}
						}
					}
				}
			}
		}
		
		public PurchaseOrderStats(String colorItem, int originalQty, int originalBoxes, int workedQty, int workedBoxes) {
			this.colorItem = colorItem;
			this.originalQty = originalQty;
			this.originalBoxes = originalBoxes;
			this.workedQty = workedQty;
			this.workedBoxes = workedBoxes;
		}
		
		public String getColorItem() {
			return colorItem;
		}
		public int getOriginalQty() {
			return originalQty;
		}
		public int getOriginalBoxes() {
			return originalBoxes;
		}
		public int getWorkedQty() {
			return workedQty;
		}
		public int getWorkedBoxes() {
			return workedBoxes;
		}
	}
}
