package com.uslc.po.gui.master;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import com.ibm.icu.util.Calendar;
import com.uslc.po.jpa.entity.Log;
import com.uslc.po.jpa.logic.Actions;
import com.uslc.po.jpa.logic.Forms;
import com.uslc.po.jpa.util.Constants;
import com.uslc.po.jpa.util.UslcJpa;

public class MasterMenu extends Menu {
	private Menu catalogs = null;
	private MenuItem catalogsMenu = null;
	private MenuItem colorMenuItem = null;
	private MenuItem sizeMenuItem = null;
	private MenuItem itemMenuItem = null;
	private MenuItem upcMenuItem = null;
	private MenuItem userMenuItem = null;
	private MenuItem optimizeCatalogsMenuItem = null;
	
	private Menu purchaseOrder = null;
	private MenuItem purchaseOrderMenu = null;
	private MenuItem newMenuItem = null;
	private MenuItem uploadMenuItem = null;
	private MenuItem assignPurchaseOrderItem = null;

	private MenuItem closeMenuItem = null;
	
	private Menu reports = null;
	private MenuItem reportsMenu = null;
	private MenuItem purchaseOrderReporMenuItem = null;
	
	private Menu help = null;
	private MenuItem helpMenu = null;
	private MenuItem aboutMenuItem = null;
	
	private Master master = null;
	
	private Logger log = null;
	
	public MasterMenu( Master master ){
		super( master, SWT.BAR );
		this.master = master;
		getPurchaseOrderMenu();
		getCatalogsMenu();
		getReportsMenu();
		getHelpMenu();
	}
	
	public Menu getCatalogs() {
		if( catalogs == null ){
			catalogs = new Menu( getMaster().getShell(), SWT.DROP_DOWN );
			
			getColorMenuItem();
			getSizeMenuItem();
			getUpcMenuItem();
			getItemMenuItem();
			getUserMenuItem();
			getOptimizeCatalogsMenuItem();
		}
		return catalogs;
	}
	public MenuItem getColorMenuItem(){
		if( colorMenuItem == null ){
			colorMenuItem = new MenuItem( getCatalogs(), SWT.PUSH);
			colorMenuItem.setText( "&color" );
			Image img = new Image(getMaster().getDisplay(), "images/color.png");
			colorMenuItem.setImage(img);
			colorMenuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected( SelectionEvent e ){
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getColorManager() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			});
		}
		return colorMenuItem;
	}
	public MenuItem getSizeMenuItem() {
		if( sizeMenuItem == null ){
			sizeMenuItem = new MenuItem( getCatalogs(), SWT.PUSH);
			sizeMenuItem.setText( "&size" );
			Image img = new Image(getMaster().getDisplay(), "images/size.png");
			sizeMenuItem.setImage(img);
			sizeMenuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected( SelectionEvent e ){
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getSizeManager() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			});
		}
		return sizeMenuItem;
	}
	public MenuItem getUpcMenuItem() {
		if( upcMenuItem == null ){
			upcMenuItem = new MenuItem( getCatalogs(), SWT.PUSH);
			upcMenuItem.setText( "&upc" );
			Image img = new Image(getMaster().getDisplay(), "images/barcode.png");
			upcMenuItem.setImage(img);
			upcMenuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected( SelectionEvent e ){
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getUpcManager() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			});
		}
		return upcMenuItem;
	}
	public MenuItem getItemMenuItem() {
		if( itemMenuItem == null ){
			itemMenuItem = new MenuItem( getCatalogs(), SWT.PUSH);
			itemMenuItem.setText( "&item" );
			Image img = new Image(getMaster().getDisplay(), "images/item.png");
			itemMenuItem.setImage(img);
			itemMenuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected( SelectionEvent e ){
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getItemManager() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			});
		}
		return itemMenuItem;
	}
	public MenuItem getUserMenuItem() {
		if( userMenuItem == null ){
			userMenuItem = new MenuItem( getCatalogs(), SWT.PUSH);
			userMenuItem.setText( "&user" );
			Image img = new Image(getMaster().getDisplay(), "images/user.png");
			userMenuItem.setImage(img);
			userMenuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected( SelectionEvent e ){
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getUserManager() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			});
		}
		return userMenuItem;
	}
	public MenuItem getOptimizeCatalogsMenuItem() {
		if( optimizeCatalogsMenuItem == null ) {
			new MenuItem(getCatalogs(), SWT.SEPARATOR);
			
			optimizeCatalogsMenuItem = new MenuItem( getCatalogs(), SWT.PUSH);
			optimizeCatalogsMenuItem.setText( "&optimize" );
			Image img = new Image(getMaster().getDisplay(), "images/optimize.png");
			optimizeCatalogsMenuItem.setImage(img);
			optimizeCatalogsMenuItem.setEnabled(false);
		}
		return optimizeCatalogsMenuItem;
	}
	public MenuItem getCatalogsMenu() {
		if( catalogsMenu == null ){
			catalogsMenu = new MenuItem( this, SWT.CASCADE );
			catalogsMenu.setText( "&catalogs" );
			catalogsMenu.setMenu( getCatalogs() );
		}
		return catalogsMenu;
	}
	
	public Menu getPurchaseOrder() {
		if( purchaseOrder == null ){
			purchaseOrder = new Menu( getMaster().getShell(), SWT.DROP_DOWN );
			
			getNewMenuItem();
			getUploadMenuItem();
			getAssignPurchaseOrderItem();
			getCloseMenuItem();
			
		}
		return purchaseOrder;
	}
	public MenuItem getNewMenuItem(){
		if( newMenuItem == null ){
			newMenuItem = new MenuItem(getPurchaseOrder(), SWT.PUSH);
			newMenuItem.setText( "&new" );
			Image img = new Image(getMaster().getDisplay(), "images/new.png");
			newMenuItem.setImage(img);
			newMenuItem.addSelectionListener( new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getNewPurchaseOrder() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			} );
		}
		return newMenuItem;
	}
	public MenuItem getUploadMenuItem(){
		if( uploadMenuItem == null ){
			uploadMenuItem = new MenuItem(getPurchaseOrder(), SWT.PUSH);
			uploadMenuItem.setText( "&upload" );
			Image img = new Image(getMaster().getDisplay(), "images/upload.png");
			uploadMenuItem.setImage(img);
			uploadMenuItem.addSelectionListener( new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					getMaster().getMasterTop().showFileUploadDialog();
					/*getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getUploadManager() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();*/
				}
			} );
		}
		return uploadMenuItem;
	}
	public MenuItem getAssignPurchaseOrderItem(){
		if( assignPurchaseOrderItem == null ){
			assignPurchaseOrderItem = new MenuItem(getPurchaseOrder(), SWT.PUSH );
			assignPurchaseOrderItem.setText( "&assign po" );
			Image img = new Image( getMaster().getDisplay(), "images/assign.png" );
			assignPurchaseOrderItem.setImage(img);
			
			assignPurchaseOrderItem.addSelectionListener( new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getPurchaseOrderByUserManager() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			} );
			
			new MenuItem(getPurchaseOrder(), SWT.SEPARATOR);
			
		}
		return assignPurchaseOrderItem;
	}
	public MenuItem getCloseMenuItem(){
		if( closeMenuItem == null ){
			closeMenuItem = new MenuItem(getPurchaseOrder(), SWT.PUSH);
			closeMenuItem.setText( "&close" );
			Image img = new Image(getMaster().getDisplay(), "images/exit.png");
			closeMenuItem.setImage(img);
			closeMenuItem.addSelectionListener( new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					MessageBox msg = new MessageBox( getMaster().getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO );
					msg.setMessage( "Do you really want to exit?" );
					msg.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
					int response = msg.open();
					if (response == SWT.YES){
						UslcJpa jpa = new UslcJpa();
						Log newLog = new Log();
						newLog.setActionId(Actions.EXIT.getId());
						newLog.setFormId(Forms.MASTER.getId());
						newLog.setDescription( "application close" );
						newLog.setTimestamp(Calendar.getInstance().getTime());
						newLog.setUser(master.getUser());
						try {
							jpa.persist(newLog);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							getMaster().getShell().dispose();
							System.exit(0);
						}
					}
				}
			});
		}
		return closeMenuItem;
	}
	public MenuItem getPurchaseOrderMenu() {
		if( purchaseOrderMenu == null ){
			purchaseOrderMenu = new MenuItem(this, SWT.CASCADE);
			purchaseOrderMenu.setText( "&po manager" );
			purchaseOrderMenu.setMenu( getPurchaseOrder() );
		}
		return purchaseOrderMenu;
	}
	public Menu getReports() {
		if( reports == null ){
			reports = new Menu( getMaster().getShell(), SWT.DROP_DOWN );
			
			getPurchaseOrderReporMenuItem();
		}
		return reports;
	}
	public MenuItem getPurchaseOrderReporMenuItem() {
		if( purchaseOrderReporMenuItem == null ){
			purchaseOrderReporMenuItem = new MenuItem( getReports(), SWT.PUSH);
			purchaseOrderReporMenuItem.setText( "&reports" );
			Image img = new Image(getMaster().getDisplay(), "images/report.png");
			purchaseOrderReporMenuItem.setImage(img);
			purchaseOrderReporMenuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected( SelectionEvent e ){
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getReportsManagerCentre() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			});
		}
		return purchaseOrderReporMenuItem;
	}
	public MenuItem getReportsMenu() {
		if( reportsMenu == null ){
			reportsMenu = new MenuItem( this, SWT.CASCADE);
			reportsMenu.setText( "&reports" );
			reportsMenu.setMenu(getReports());
		}
		return reportsMenu;
	}
	public Menu getHelp() {
		if( help == null ){
			help = new Menu( getMaster().getShell(), SWT.DROP_DOWN );
			
			getAboutMenuItem();
		}
		return help;
	}

	public MenuItem getAboutMenuItem() {
		if( aboutMenuItem == null ){
			aboutMenuItem = new MenuItem( getHelp(), SWT.PUSH);
			aboutMenuItem.setText( "&about" );
			Image img = new Image(getMaster().getDisplay(), "images/about.png");
			aboutMenuItem.setImage(img);
			aboutMenuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected( SelectionEvent e ){
					getMaster().getMasterCenter().showComposite( getMaster().getMasterCenter().getAbout() );
					getMaster().getMasterCenter().layout();
					getMaster().getShell().layout();
				}
			});
		}
		return aboutMenuItem;
	}
	public MenuItem getHelpMenu() {
		if( helpMenu == null ){
			helpMenu = new MenuItem( this, SWT.CASCADE );
			helpMenu.setText( "&help" );
			helpMenu.setMenu( getHelp() );
		}
		return helpMenu;
	}
	public Master getMaster() {
		return master;
	}
	
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger(MasterMenu.class);
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	
	@Override
	protected void checkSubclass() {
	}
}
