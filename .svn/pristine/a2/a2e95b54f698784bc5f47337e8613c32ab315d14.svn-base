package com.uslc.po.gui.master;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import com.ibm.icu.text.SimpleDateFormat;
import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.jpa.entity.Color;
import com.uslc.po.jpa.entity.PackingDetail;
import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.PurchaseOrderDetail;

public class MasterLeftComposite extends MasterSections {
	private Label purchaseOrderLbl = null;
	private Table purchaseOrderTbl = null;
	private Button showPackingDetails = null;
	private Group purchaseOrderInfoGrp = null;
	private Label refNum = null;
	private Label deptNum = null;
	private Label items = null;
	private Label cartons = null;
	private Label from = null;
	private Label to = null;
	private Label created = null;
	private Font infoFont = null;
	private GridData gdValues = null;
	private Font addressesFont = null;
	private Logger log = null;
	
	private SimpleDateFormat sdf = null;
	private LiveDataAccessLifeCicle ldalc = null;
	private TableListener tableListener = null;
	
	private PurchaseOrderStatus oldPosSelection = null;
	
	public MasterLeftComposite( Master master ){
		super( master, SWT.BORDER );
		initComposite();
		getLiveDataAccessLifeCicle();
	}
	
	protected void initComposite(){
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, true );
		data.widthHint = 200;
		setLayoutData( data );
		
		GridLayout layout = new GridLayout();
		layout.marginTop = 20;
		setLayout(layout);
		
		getPurchaseOrderLbl();
		getPurchaseOrderTbl();
		getShowPackingDetails();
		
		getPurchaseOrderInfoGrp();
	}
	public Label getPurchaseOrderLbl(){
		if( purchaseOrderLbl == null ){
			purchaseOrderLbl = new Label(this, SWT.NONE);
			purchaseOrderLbl.setText( "purchase orders:" );
		}
		return purchaseOrderLbl;
	}
	public Table getPurchaseOrderTbl() {
		if( purchaseOrderTbl == null ) {
			//getLog().info( "instantiating purchaseOrderTbl object" );
			purchaseOrderTbl = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION);
			
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
			data.heightHint = 200;
			purchaseOrderTbl.setLayoutData(data);
			
			TableColumn po = new TableColumn(purchaseOrderTbl, SWT.NONE);
			TableColumn status = new TableColumn(purchaseOrderTbl, SWT.NONE);
			status.setToolTipText( "finished vs total" );
			
			po.setText( "po" );
			status.setText("status");
			po.setWidth(100);
			status.setWidth(90);
			purchaseOrderTbl.setHeaderVisible( true );
			
			purchaseOrderTbl.addListener( SWT.DefaultSelection, new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					switch(e.type) {
						case SWT.MouseDoubleClick:
							getLog().info( "MouseUp event" );
							break;
						case SWT.Selection:
							getLog().info( "Selection event" );
							break;
						default:
							getLog().info( "default event["+e.type+"]" );
							break;
					}
					
				}
			});
			
			purchaseOrderTbl.addListener(SWT.Selection, getTableListener());
			purchaseOrderTbl.addListener(SWT.MouseDoubleClick, getTableListener());
		}
		return purchaseOrderTbl;
	}
	private Listener getTableListener() {
		if( tableListener == null ) {
			tableListener = new TableListener();
		}
		return tableListener;
	}

	public void showPoDetails() {
		//getLog().info( "showPoDetails()::purchaseOrderTbl["+purchaseOrderTbl+"]" );
		TableItem[] items = getPurchaseOrderTbl().getSelection();
		PurchaseOrderStatus po = getSelectedPurchaseOrder();
		
		if( po!=null ) {
			getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getPurchaseDetailViewer() );
			getMaster().getMasterCenter().getPurchaseDetailViewer().displayPurchaseDetails(po);
		}
	}
	public Button getShowPackingDetails(){
		if( showPackingDetails == null ){
			showPackingDetails = new Button(this, SWT.PUSH);
			showPackingDetails.setText( "packing" );
			GridData rightAlign = new GridData(GridData.HORIZONTAL_ALIGN_END);
			rightAlign.grabExcessHorizontalSpace = true;
			showPackingDetails.setLayoutData(rightAlign);
			showPackingDetails.setImage(new Image(getDisplay(), "images/box.png"));
			showPackingDetails.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					showPackingDetail();
				}
			});
			
			GridData gdHorizontal = new GridData( GridData.FILL_HORIZONTAL );
			gdHorizontal.grabExcessHorizontalSpace = true;
			gdHorizontal.heightHint=20;
			Label horizontaLabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			horizontaLabel.setLayoutData( gdHorizontal );
		}
		return showPackingDetails;
	}
	
	public Group getPurchaseOrderInfoGrp(){
		if( purchaseOrderInfoGrp == null ){
			purchaseOrderInfoGrp = new Group( this, SWT.SHADOW_ETCHED_IN );
			purchaseOrderInfoGrp.setText( "info" );

			GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
			data.heightHint = 240;
			purchaseOrderInfoGrp.setLayoutData(data);
			
			GridLayout layout = new GridLayout();
			layout.marginLeft=0;
			layout.marginRight=0;
			layout.marginWidth=0;
			layout.horizontalSpacing=0;
			layout.makeColumnsEqualWidth=true;
			layout.numColumns=2;
			purchaseOrderInfoGrp.setLayout( layout );
			
			//getRefNum().setFont(getInfoFont());
			
			//GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_END, GridData.VERTICAL_ALIGN_BEGINNING, true, false );
			GridData gd = new GridData();
			gd.widthHint=60;
			
			Label d = new Label( purchaseOrderInfoGrp, SWT.NONE );
			d.setText( "dept#:" );
			d.setLayoutData(gd);
			d.setAlignment( SWT.RIGHT );
			d.setFont(getMaster().getSystemVariables().getSmallFont());
			getDeptNum().setFont(getMaster().getSystemVariables().getSmallFont());
			
			Label i = new Label( purchaseOrderInfoGrp, SWT.NONE );
			i.setText( "items:" );
			i.setLayoutData(gd);
			i.setAlignment( SWT.RIGHT );
			i.setFont(getMaster().getSystemVariables().getSmallFont());
			getItems().setFont(getMaster().getSystemVariables().getSmallFont());
			
			Label c = new Label( purchaseOrderInfoGrp, SWT.NONE );
			c.setText( "cartons:" );
			c.setLayoutData(gd);
			c.setAlignment( SWT.RIGHT );
			c.setFont(getMaster().getSystemVariables().getSmallFont());
			getCartons().setFont(getMaster().getSystemVariables().getSmallFont());
			
			Label f = new Label( purchaseOrderInfoGrp, SWT.NONE );
			f.setText( "from:" );
			f.setLayoutData(gd);
			f.setAlignment( SWT.RIGHT );
			f.setFont(getMaster().getSystemVariables().getSmallFont());
			getFrom().setFont(getMaster().getSystemVariables().getXxSmallFont());
			
			Label t = new Label( purchaseOrderInfoGrp, SWT.NONE );
			t.setText( "to:" );
			t.setLayoutData(gd);
			t.setAlignment( SWT.RIGHT );
			t.setFont(getMaster().getSystemVariables().getSmallFont());
			getTo().setFont(getMaster().getSystemVariables().getXxSmallFont());
			
			Label cr = new Label( purchaseOrderInfoGrp, SWT.NONE );
			cr.setText( "created:" );
			cr.setLayoutData(gd);
			cr.setAlignment( SWT.RIGHT );
			cr.setFont(getMaster().getSystemVariables().getSmallFont());
			getCreated().setFont(getMaster().getSystemVariables().getSmallFont());
		}
		return purchaseOrderInfoGrp;
	}
	public Label getRefNum(){
		if( refNum == null ){
			refNum = new Label(purchaseOrderInfoGrp, SWT.NONE);
			refNum.setText("po#");
			refNum.setAlignment( SWT.RIGHT );
			GridData refNumGd = new GridData(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
			refNumGd.horizontalSpan=2;
			refNum.setLayoutData(refNumGd);
		}
		return refNum;
	}
	public Label getDeptNum() {
		if( deptNum == null ){
			deptNum = new Label( purchaseOrderInfoGrp, SWT.NONE);
			deptNum.setText( "" );
			deptNum.setAlignment( SWT.LEFT );
			deptNum.setLayoutData(getGdValues());
		}
		return deptNum;
	}
	public Label getItems() {
		if( items == null ){
			items = new Label(purchaseOrderInfoGrp, SWT.NONE);
			items.setText( "" );
			items.setAlignment( SWT.LEFT );
			items.setLayoutData(getGdValues());
		}
		return items;
	}
	public Label getCartons() {
		if( cartons == null ){
			cartons = new Label(purchaseOrderInfoGrp, SWT.NONE);
			cartons.setText( "" );
			cartons.setAlignment( SWT.LEFT );
			cartons.setLayoutData(getGdValues());
		}
		return cartons;
	}
	public Label getFrom() {
		if( from == null ){
			from = new Label(purchaseOrderInfoGrp, SWT.NONE | SWT.WRAP );
			from.setText( "" );
			from.setAlignment( SWT.RIGHT );
			GridData gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			gd.grabExcessHorizontalSpace=true;
			gd.horizontalSpan=2;
			gd.heightHint=50;
			from.setLayoutData(gd);
			from.setFont( getMaster().getSystemVariables().getXxSmallFont() );
		}
		return from;
	}
	public Label getTo() {
		if( to == null ){
			to = new Label(purchaseOrderInfoGrp, SWT.NONE | SWT.WRAP);
			to.setText( "" );
			to.setAlignment( SWT.RIGHT );
			GridData gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			gd.grabExcessHorizontalSpace=true;
			gd.horizontalSpan=2;
			gd.heightHint=40;
			to.setLayoutData(gd);
			to.setFont( getMaster().getSystemVariables().getXxSmallFont() );
		}
		return to;
	}
	public Label getCreated() {
		if( created == null ){
			created = new Label(purchaseOrderInfoGrp, SWT.NONE);
			created.setText( "" );
			created.setAlignment( SWT.LEFT );
			created.setLayoutData(getGdValues());
		}
		return created;
	}
	public GridData getGdValues(){
		if( gdValues == null ){
			gdValues = new GridData(GridData.FILL_HORIZONTAL);
			gdValues.grabExcessHorizontalSpace=true;
			/*gdValues.horizontalAlignment=GridData.FILL;
			gdValues.grabExcessHorizontalSpace=true;
			gdValues.widthHint=140;*/
		}
		return gdValues;
	}
	public void showPackingDetail() {
		TableItem[] items = getPurchaseOrderTbl().getSelection();
		PurchaseOrderStatus po = getSelectedPurchaseOrder();
		
		if( po!=null ) {
			getMaster().getMasterCenter().getPackingDetailViewer().displayPackingDetails( po );
		}
	}
	public void showPoInfo() {
		getLog().info( "showPoInfo()::purchaseOrderTbl["+purchaseOrderTbl+"] - count["+purchaseOrderTbl.getItemCount() + "]" );
		PurchaseOrderStatus po = getSelectedPurchaseOrder();
		
		if( po!=null ){
			getPurchaseOrderInfoGrp().setText( "info: " + po.getPurchaseOrderCode() );
			getDeptNum().setText( String.valueOf( po.getDeptNumber() ) );
			getItems().setText( String.valueOf( po.getItems() ) );
			getCartons().setText( String.valueOf( po.getCartoons() ) );
			getFrom().setText( po.getFrom() );
			getTo().setText( po.getTo() );
			getCreated().setText( po.getCreated() );
			
			getMaster().getMasterTop().getEditPo().setEnabled( true );
			getMaster().getMasterTop().getPrint().setEnabled( true );
			getMaster().getMasterTop().getDeletePo().setEnabled( true );
		}else{
			getPurchaseOrderInfoGrp().setText( "info" );
			getRefNum().setText( "" );
			getDeptNum().setText( "" );
			getItems().setText( "" );
			getCartons().setText( "" );
			getFrom().setText( "" );
			getTo().setText( "" );
			getCreated().setText( "" );
			getMaster().getMasterTop().getEditPo().setEnabled(false);
			getMaster().getMasterTop().getPrint().setEnabled(false );
			getMaster().getMasterTop().getDeletePo().setEnabled(false);
		}
	}
	public SimpleDateFormat getSdf(){
		if( sdf == null ){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		return sdf;
	}
	public PurchaseOrderStatus getSelectedPurchaseOrder() {
		TableItem[] items = getPurchaseOrderTbl().getSelection();
		PurchaseOrderStatus po = null;
		for (TableItem ti : items) {
			po = (PurchaseOrderStatus)ti.getData();
		}
		
		if( po!=null ){
			oldPosSelection = po;
		}
		
		return oldPosSelection;
	}
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger( MasterLeftComposite.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	
	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		if( ldalc == null ) {
			ldalc = new MasterLeftCompositeLogic();
		}
		return ldalc;
	}

	public class MasterLeftCompositeLogic implements LiveDataAccessLifeCicle {
		private PurchaseOrderStatus[] poStatus = null;
		
		public MasterLeftCompositeLogic () {
			displayValues();
		}
		
		public PurchaseOrderStatus[] getPurchaseOrdersStatus () {
			if( poStatus == null ) {
				poStatus = new PurchaseOrderStatus[getMaster().getLiveDataAccess().getUslcData().getPurchaseOrderList().size()];
				
				for ( int i = 0 ; i < getMaster().getLiveDataAccess().getUslcData().getPurchaseOrderList().size() ; i++ ) {
					poStatus[i] = new PurchaseOrderStatus( getMaster().getLiveDataAccess().getUslcData().getPurchaseOrderList().get(i) );
					getLog().info( poStatus[i].getPurchaseOrderCode() + ": {" + poStatus[i] + "}" );
				}
			}
			return poStatus;
		}
		
		@Override
		public void refreshFormData() {
			clean();
			poStatus = null;
			displayValues();
			if( getSelectedPurchaseOrder()!=null ) {
				for( int j = 0 ; j < getPurchaseOrderTbl().getItems().length ; j++ ) {
					TableItem i = getPurchaseOrderTbl().getItems()[j];
					if( ((PurchaseOrderStatus)i.getData()).getPo().getId()==getSelectedPurchaseOrder().getPo().getId() ){
						getPurchaseOrderTbl().select(j);
						break;
					}
				}
				showPoInfo();
			}
			layout();
		}
		@Override
		public void displayValues() {
			getPurchaseOrderTbl().removeAll();
			try{
				for (PurchaseOrderStatus po : getPurchaseOrdersStatus() ) {
					TableItem ti = new TableItem(getPurchaseOrderTbl(), SWT.NONE);
					ti.setFont( getMaster().getSystemVariables().getSmallFont() );
					
					String[] texts = { po.getPurchaseOrderCode(), po.toString() };
					ti.setData( po );
					ti.setText( texts );
				}
				
				for( TableColumn col : purchaseOrderTbl.getColumns() ) {
					col.pack();
				}
			}catch( Exception e ){
				e.printStackTrace();
			}
		}

		@Override
		public void clean() {
			getDeptNum().setText("");
			getItems().setText("");
			getCartons().setText("");
			getFrom().setText("");
			getTo().setText("");
			getCreated().setText("");
			getPurchaseOrderTbl().setSelection(-1);
		}
	}
	
	public class TableListener implements Listener {

		@Override
		public void handleEvent( Event e ) {
			switch( e.type ) {
				case SWT.Selection:
					showPoInfo();
					break;
				case SWT.MouseDoubleClick:
					showPoDetails();
					break;
				default:
					getLog().info( "detault::e.type["+e.type+"]" );
					break;
			}
		}
		
	}
	public class PurchaseOrderStatus {
		private PurchaseOrder po = null;
		private int finished = 0;
		private int missing = 0;
		private int total = 0;
		private String poCode = "";
		private String deptNumber = "";
		private int items = 0;
		private int cartoons = 0;
		private String from = "";
		private String to = "";
		private String created = "";
		
		private PurchaseOrderStatistics[] purchaseOrderStatistics = null;
		
		public PurchaseOrderStatus( PurchaseOrder po ) {
			if( po!=null ) {
				this.po=po;
				
				for( PurchaseOrderDetail pod : po.getPurchaseOrderDetails() ) {
					if( !pod.getDeleted() ) {
						for( PackingDetail pd : pod.getPackingDetails() ) {
							if( !pd.getDeleted() ) {
								boolean completed = true;
								if( pd.getCarton()==null ) {
									completed = false;
								} else if( pd.getCarton().getDeleted() ) {
									completed = false;
								}
								
								if( completed ) {
									finished++;
								} else {
									missing++;
								}
								total++;
							}
						}
						
						items+=pod.getTotal();
					}	//	END of if( !pod.getDeleted()...
				}	//	END of for( PurchaseOrderDetail pod : po.getPurchaseOrderDetails()...
				
				poCode = po.getReferenceNumber();
				deptNumber = po.getDepartmentNumber();
				cartoons = total;
				from = po.getShipFrom();
				to = po.getShipTo();
				created = getMaster().getSimpleDateFormat().format( po.getTimestamp() );
			}	//	END of if( po!=null ...
		}

		public com.uslc.po.jpa.entity.PurchaseOrder getPo() {
			return po;
		}
		public int getFinished() {
			return finished;
		}
		public int getMissing() {
			return missing;
		}
		public int getTotal() {
			return total;
		}
		public String getPurchaseOrderCode() {
			return poCode;
		}
		public String getDeptNumber() {
			return deptNumber;
		}
		public int getItems() {
			return items;
		}
		public int getCartoons() {
			return cartoons;
		}
		public String getFrom() {
			return from;
		}
		public String getTo() {
			return to;
		}
		public String getCreated() {
			return created;
		}
		public PurchaseOrderStatistics[] getPurchaseOrderStatistics() {
			if( purchaseOrderStatistics == null ) {
				Color[] colors = getUslcJpaManager().getColors( getPo(), false);
				purchaseOrderStatistics = new PurchaseOrderStatistics[colors.length];
				
				for( int i = 0 ; i < colors.length ; i++ ) {
					purchaseOrderStatistics[i] = new PurchaseOrderStatistics( getPo(),colors[i] );
				}
			}
			return purchaseOrderStatistics;
		}
		
		public String toString(){
			String s = "";
			if( getFinished()==getTotal() ){
				s = "compl ["+getTotal()+"]";
			}else{
				s = "pend [ "+getFinished()+"/"+getTotal()+" ]";
			}
			return s; 
		}
	}

	public class PurchaseOrderStatistics {
		private String color = "";
		private int units = 0;
		private int boxes = 0;
		
		public PurchaseOrderStatistics( PurchaseOrder po, Color color ) {
			if( po!=null && color!=null ){
				this.color = color.getName();
				
				for( PurchaseOrderDetail pod : getUslcJpaManager().getPurchaseOrderDetails(po, color, false) ) {
					units += pod.getTotal();
					for( PackingDetail pd : pod.getPackingDetails() ) {
						if( !pd.getDeleted() ) {
							boxes++;
						}
					}
				}	//	END of for( PackingDetail pd : pod.getPackingDetails()...
			}
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
}
