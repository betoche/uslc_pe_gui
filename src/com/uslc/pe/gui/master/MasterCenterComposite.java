package com.uslc.pe.gui.master;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.uslc.pe.gui.master.catalog.ColorManagerComposite;
import com.uslc.pe.gui.master.catalog.ItemManagerComposite;
import com.uslc.pe.gui.master.catalog.PurchaseOrderByUserComposite;
import com.uslc.pe.gui.master.catalog.SizeManagerComposite;
import com.uslc.pe.gui.master.catalog.UpcManagerComposite;
import com.uslc.pe.gui.master.catalog.UserManagerComposite;
import com.uslc.pe.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.pe.gui.master.interfaces.MasterCompositeInterface;

public class MasterCenterComposite extends MasterSections {
	private ColorManagerComposite colorManager = null;
	private SizeManagerComposite sizeManager = null;
	private UpcManagerComposite upcManager = null;
	private ItemManagerComposite itemManager = null;
	private UserManagerComposite userManager = null;
	private NewPurchaseOrderComposite newPoManager = null;
	private PackingDetailComposite packingDetailViewer = null;
	private PurchaseOrderDetailComposite purchaseDetailViewer = null;
	private AboutComposite about = null;
	private PurchaseOrderByUserComposite purchaseByUserManager = null;
	private ReportsManagerComposite reportsCentre = null;
	
	private Composite left = null;
	private Composite center = null;
	private Composite right = null;
	
	private LiveDataAccessLifeCicle ldal = null;
	
	public MasterCenterComposite( Master master ){
		super( master, SWT.BORDER );
		
		initComposite();
	}
	
	protected void initComposite() {
		GridData data = new GridData( GridData.FILL_BOTH );
		//data.widthHint = 400;
		setLayoutData( data );
		
		GridLayout layout = new GridLayout( 3, false );
		//FormLayout layout = new FormLayout();
		layout.marginBottom = 20;
		layout.marginTop = 20;
		layout.marginLeft = 20;
		layout.marginRight = 20;
		
		setLayout(layout);
	}
	
	public void showComposite( Composite composite ) {
		getMaster().hideAllComposites();
		composite.setParent( getCenterComposite() );
		composite.setVisible(true);
		composite.layout();
		layout();
		getMaster().getShell().layout();
		if( composite instanceof NewPurchaseOrderComposite ){
			getMaster().getMasterTop().getCancel().setEnabled(true);
		}else{
			getMaster().getMasterTop().getCancel().setEnabled(false);
		}
		
		if( composite instanceof MasterCompositeInterface ) {
			getMaster().setInfoText( ((MasterCompositeInterface)composite).getInfoForm() );
		}
	}
	public SizeManagerComposite getSizeManager(){
		if( sizeManager == null ){
			sizeManager = new SizeManagerComposite( this );
		}
		return sizeManager;
	}
	public ColorManagerComposite getColorManager(){
		if( colorManager == null ){
			colorManager = new ColorManagerComposite( this );
		}
		return colorManager;
	}
	public UpcManagerComposite getUpcManager() {
		if( upcManager == null ){
			upcManager = new UpcManagerComposite(this);
		}
		return upcManager;
	}
	public Composite getCenterComposite(){
		if( center == null ){
			GridData data = new GridData( GridData.FILL_BOTH );
			
			left = new Composite(this, SWT.NONE);
			center = new Composite(this,SWT.NONE);
			right = new Composite(this, SWT.NONE);
			
			left.setLayoutData(data);
			right.setLayoutData(data);
			
			//GridLayout layout = new GridLayout();
			FormLayout layout = new FormLayout();
			//layout.numColumns=1;
			center.setLayout( layout );
		}
		return center;
	}
	public void setCenterComposite( Composite composite ){
		center = composite;
	}
	public NewPurchaseOrderComposite getNewPurchaseOrder() {
		if( newPoManager == null ){
			newPoManager = new NewPurchaseOrderComposite(this);
		}
		return newPoManager;
	}
	public PackingDetailComposite getPackingDetailViewer( /*PurchaseOrder po*/ ){
		if( packingDetailViewer == null ){
			packingDetailViewer = new PackingDetailComposite(this);
		}
		//packingDetailViewer.setPurchaseOrder( po );
		return packingDetailViewer;
	}
	public PurchaseOrderDetailComposite getPurchaseDetailViewer(){
		if( purchaseDetailViewer == null ){
			purchaseDetailViewer = new PurchaseOrderDetailComposite(this);
		}
		return purchaseDetailViewer;
	}
	
	public ItemManagerComposite getItemManager() {
		if( itemManager == null ){
			itemManager = new ItemManagerComposite(this);
		}
		return itemManager;
	}

	public AboutComposite getAbout() {
		if( about == null ){
			about = new AboutComposite(this);
		}
		return about;
	}

	public UserManagerComposite getUserManager() {
		if( userManager == null ){
			userManager = new UserManagerComposite(this);
		}
		return userManager;
	}

	public PurchaseOrderByUserComposite getPurchaseOrderByUserManager() {
		if( purchaseByUserManager==null ){
			purchaseByUserManager = new PurchaseOrderByUserComposite(this);
		}
		return purchaseByUserManager;
	}
	
	public ReportsManagerComposite getReportsManagerCentre() {
		if( reportsCentre == null ) {
			reportsCentre = new ReportsManagerComposite(this);
		}
		return reportsCentre;
	}

	@Override
	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		if( ldal == null ) {
			ldal = new MasterCenterCompositeLogic();
		}
		return ldal;
	}
	
	public class MasterCenterCompositeLogic implements LiveDataAccessLifeCicle {

		@Override
		public void displayValues() {
			
		}

		@Override
		public void clean() {
			
		}

		@Override
		public void refreshFormData() {
			getCenterComposite().layout();
			layout();
		}
		
	}
}
