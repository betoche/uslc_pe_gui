package com.uslc.po.gui.master.catalog;

import java.net.ConnectException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.uslc.po.gui.master.MasterCenterComposite;
import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.gui.master.interfaces.MasterCompositeInterface;
import com.uslc.po.jpa.entity.PurchaseOrder;
import com.uslc.po.jpa.entity.PurchaseOrderByUser;
import com.uslc.po.jpa.entity.User;
import com.uslc.po.jpa.logic.PurchaseOrderRepo;
import com.uslc.po.jpa.logic.UserRepo;
import com.uslc.po.jpa.util.Constants;
import com.uslc.po.jpa.util.UslcJpa;

public class PurchaseOrderByUserComposite extends FormCenterMaster implements MasterCompositeInterface {
	private Combo userCbx;
	private Table purchaseOrderTbl;
	private Label label;
	private Table userTable;
	private Label lblUser;
	private Text poSearchTxt;
	private Label lblNewLabel_1;
	private Button cancelBtn;
	private Label label_3;
	
	private Logger log = null;
	private Button btnAdd;
	private Button btnDel;
	private TableColumn tblclmnPo;
	private TableColumn tblclmnUser;
	private TableColumn tblclmnUsers;
	private TableColumn tblclmnPos;
	
	private UslcJpa jpa = null;
	
	private LiveDataAccessLifeCicle ldalc = null;
	
	public PurchaseOrderByUserComposite( MasterCenterComposite composite ) {
		super( composite, SWT.NONE );
		getLog().info( PurchaseOrderByUser.class + " constructor has been called!" );
		init();
		getLiveDataAccessLifeCicle();
	}
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PurchaseOrderByUserComposite(MasterCenterComposite parent, int style) {
		super(parent, style);
		init();
		getLiveDataAccessLifeCicle();
	}
	private void addUser() throws Exception {
		try{
			PurchaseOrder po = null;
			User user = (User)getUserCbx().getData( getUserCbx().getItem( getUserCbx().getSelectionIndex() ) );
			PurchaseOrderByUser newPobu = null;
			TableItem[] items = getPurchaseOrderTbl().getSelection();
			TableItem selectedItem = null;
			for( int i = 0 ; i < items.length ; i++ ) {
				selectedItem = items[i];
				po = (PurchaseOrder)selectedItem.getData();
			}
			
			if( po!=null && user!=null ){
				for( PurchaseOrderByUser pobu : po.getPurchaseOrders() ){
					if( pobu.getUser().getId() == user.getId() ){
						newPobu = pobu;
						break;
					}
				}
				
				if( newPobu==null ){
					newPobu = new PurchaseOrderByUser();
					newPobu.setUser(user);
					newPobu.setPurchaseOrder(po);
					po.addpurchaseOrder(newPobu);
				}
				newPobu.setDeleted(false);
				
				newPobu = (PurchaseOrderByUser)getJpa().merge( newPobu );
				if( newPobu.getId()>0 && !newPobu.getDeleted() ){
					MessageBox box = new MessageBox(getShell(), SWT.ICON_INFORMATION);
					box.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
					box.setMessage( "po["+po.getReferenceNumber()+"] assigned to " + user.getFirstName() + " " + user.getLastName() );
					box.open();
					//	refresh purchase order table
					int users = 0;
					for( PurchaseOrderByUser pobu : po.getPurchaseOrders() ) {
						if( !pobu.getDeleted() ) {
							users++;
						}
					}
					selectedItem.setText( new String[]{ po.getReferenceNumber(), String.valueOf( users ) } );
					selectedItem.setData(po);
					//	load new user
					loadPurchaseOrderUsers();
				}else{
					throw new Exception( "error trying to assign the po to a user, please contact your sysadmin" );
				}
			}else{
				throw new Exception( "user["+user+"] - po["+po+"]" );
			}
			
			
		}catch( Exception e ){
			throw e;
		}
	}
	private void loadPurchaseOrderUsers(){
		getUserTable().removeAll();
		TableItem[] items = getPurchaseOrderTbl().getSelection();
		PurchaseOrder po = null;
		for( TableItem item : items ){
			po = (PurchaseOrder)item.getData();
		}
		
		if( po!=null ){
			for( PurchaseOrderByUser pobu : po.getPurchaseOrders() ){
				if( !pobu.getDeleted() ){
					User user = pobu.getUser();
					int pos = 0;
					for( PurchaseOrderByUser p : user.getPurchaseOrders() ){
						if( !p.getDeleted() )
							pos++;
					}
					TableItem item = new TableItem(getUserTable(), SWT.READ_ONLY);
					item.setText(new String[]{ user.getFirstName() + " " + user.getLastName(), String.valueOf( pos ) });
					item.setData( pobu );
				}
			}
		}
	}
	private void delUser() throws Exception{
		TableItem[] items = getUserTable().getSelection();
		PurchaseOrderByUser pobu = null;
		for( TableItem item : items ){
			pobu = (PurchaseOrderByUser)item.getData();
		}
		
		if( pobu!=null ){
			pobu.setDeleted(true);
			
			if( getJpa().persist(pobu) ){
				MessageBox box = new MessageBox(getShell(), SWT.ICON_INFORMATION);
				box.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
				box.setMessage( "po[" + pobu.getPurchaseOrder().getReferenceNumber() + "] unassigned to " + pobu.getUser().getFirstName() + " " + pobu.getUser().getLastName() );
				box.open();
				
				TableItem[] poItems = getPurchaseOrderTbl().getSelection();
				for( TableItem item : poItems ){
					PurchaseOrder po = (PurchaseOrder)item.getData();
					if( po.getId() == pobu.getPurchaseOrder().getId() ){
						int users = 0;
						for( PurchaseOrderByUser pu : po.getPurchaseOrders() ) {
							if( !pu.getDeleted() ) {
								users++;
							}
						}
						item.setText( new String[]{ po.getReferenceNumber(), String.valueOf( users ) } );
						item.setData(po);
						break;
					}
				}
				loadPurchaseOrderUsers();
			}else{
				throw new Exception( "there was an error trying to persist the change, please contact your sysadmin" );
			}
		}else{
			throw new Exception( "please select a user from the users table first" );
		}
	}
	public void init(){
		FormData data = new FormData( 550, 400 );
		setLayoutData(data);
		
		setLayout(new GridLayout(6, false));
		getLog().info( "init method called" );
		lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		lblNewLabel_1.setText("purchase order assigner");
		new Label(this, SWT.NONE);
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		
		lblUser = new Label(this, SWT.NONE);
		lblUser.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUser.setText("po:");
		
		poSearchTxt = new Text(this, SWT.BORDER);
		poSearchTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		poSearchTxt.addListener( SWT.MouseHover, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setInfoText( new InfoForm( "po assigner ~> search inp", 
						"easy type in search po bar", 
						null ) );
			}
		});
		poSearchTxt.addListener( SWT.MouseExit, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				cleanInfoText();
			}
		});
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("user:");
		
		userCbx = new Combo(this, SWT.READ_ONLY);
		userCbx.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		userCbx.addListener( SWT.MouseHover, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setInfoText( new InfoForm( "po assigner ~> users list", 
						"list of user-clients existing in the system", 
						null ) );
			}
		});
		userCbx.addListener( SWT.MouseExit, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				cleanInfoText();
			}
		});
		
		btnAdd = new Button(this, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					addUser();
				} catch (Exception e1) {
					getLog().error( "error while trying to add an user", e1 );
					MessageBox box = new MessageBox(getShell(), SWT.ICON_ERROR);
					box.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
					box.setMessage( e1.getMessage() );
					box.open();
				}
			}
		});
		btnAdd.addListener( SWT.MouseHover, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setInfoText( new InfoForm( "po assigner ~> add button", 
						"after selecting the purchase order and selecting the user hit this button to assign the po", 
						null ) );
			}
		});
		btnAdd.addListener( SWT.MouseExit, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				cleanInfoText();
			}
		});
		btnAdd.setText("add");
				
		purchaseOrderTbl = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		purchaseOrderTbl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadPurchaseOrderUsers();
			}
		});
		purchaseOrderTbl.addListener( SWT.MouseHover, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setInfoText( new InfoForm( "po assigner ~> po list", 
						"it displays all the current purchase orders and the numbers of users that have the po assigned to them", 
						new String[]{ 
						"click a po",
						"list assined users to the po" } ) );
			}
		});
		purchaseOrderTbl.addListener( SWT.MouseExit, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				cleanInfoText();
			}
		});
		purchaseOrderTbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		purchaseOrderTbl.setHeaderVisible(true);
		purchaseOrderTbl.setLinesVisible(true);
		
		tblclmnPo = new TableColumn(purchaseOrderTbl, SWT.NONE);
		tblclmnPo.setText("po");
		tblclmnPo.setWidth(120);
		
		tblclmnUsers = new TableColumn(purchaseOrderTbl, SWT.NONE);
		tblclmnUsers.setWidth(50);
		tblclmnUsers.setText("users");
		
		label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label.setText("->");
		
		userTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		userTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		userTable.setHeaderVisible(true);
		userTable.setLinesVisible(false);
		userTable.addListener( SWT.MouseHover, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setInfoText( new InfoForm( "po assigner ~> users lst", 
						"display the assigned users to the selected purchase order", 
						null ) );
			}
		});
		userTable.addListener( SWT.MouseExit, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				cleanInfoText();
			}
		});
		
		tblclmnUser = new TableColumn(userTable, SWT.NONE);
		tblclmnUser.setWidth(120);
		tblclmnUser.setText("user");
		
		tblclmnPos = new TableColumn(userTable, SWT.NONE);
		tblclmnPos.setWidth(40);
		tblclmnPos.setText("pos");
		
		btnDel = new Button(this, SWT.NONE);
		btnDel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					delUser();
				}catch( Exception e1 ){
					getLog().error( "error while trying to delete an user", e1 );
					MessageBox box = new MessageBox(getShell(), SWT.ICON_ERROR);
					box.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
					box.setMessage( e1.getMessage() );
					box.open();
				}
			}
		});
		btnDel.addListener( SWT.MouseHover, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setInfoText( new InfoForm( "po assigner ~> delete btn", 
						"hit the button in order to remove the selected user from the purchase order (the client user won't be able to see/work the purchase order in the client interface)", 
						null ) );
			}
		});
		btnDel.addListener( SWT.MouseExit, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				cleanInfoText();
			}
		});
		btnDel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnDel.setText("del");
		
		label_3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		
		cancelBtn = new Button(this, SWT.NONE);
		cancelBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hide();
			}
		});
		cancelBtn.addListener( SWT.MouseHover, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				setInfoText( new InfoForm( "po assigner ~> cancel btn", 
						"hit for hiding the color catalog form", 
						null) );
			}
		});
		cancelBtn.addListener( SWT.MouseExit, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				cleanInfoText();
			}
		});
		
		cancelBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 6, 1));
		cancelBtn.setText("cancel");
	}
	public void hide(){
		this.setParent( getParent().getMaster().getHiddenShell() );
		this.setVisible(false);
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public Table getPurchaseOrderTbl() {
		return purchaseOrderTbl;
	}
	public Table getUserTable() {
		return userTable;
	}
	public Text getPoSearchTxt() {
		return poSearchTxt;
	}
	public Combo getUserCbx() {
		return userCbx;
	}
	public Button getCancelBtn() {
		return cancelBtn;
	}
	
	private UslcJpa getJpa(){
		if( jpa == null ){
			jpa = new UslcJpa();
		}
		return jpa;
	}
	
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger( PurchaseOrderByUserComposite.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	@Override
	public InfoForm getInfoForm() {
		String title = "po assigner";
		String desc = "assign or unassign a purchase order to a client user in order to allow him to see the po in his account";
		String[] features = { "list all existing po in the system",
				"list all user clients existing in the system",
				"assign many pos to many clients",
				"unassign a po from a user"};
		
		return new InfoForm(title, desc, features);
	}
	
	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		if( ldalc == null ) {
			ldalc = new PurchaseOrderByUserCompositeLogic();
		}
		return ldalc;
	}
	
	public class PurchaseOrderByUserCompositeLogic implements LiveDataAccessLifeCicle{
		public PurchaseOrderByUserCompositeLogic() {
			displayValues();
		}
		@Override
		public void displayValues() {
			List<PurchaseOrder> purchaseOrderList = getUslcJpaManager().getPurchaseOrderList();
			
			for (PurchaseOrder po : purchaseOrderList) {
				TableItem item = new TableItem(getPurchaseOrderTbl(), SWT.READ_ONLY);
				int users = 0;
				for( PurchaseOrderByUser pobu : po.getPurchaseOrders() ) {
					if( !pobu.getDeleted() ) {
						users++;
					}
				}
				item.setText( new String[]{ po.getReferenceNumber(), String.valueOf( users ) } );
				item.setData(po);
			}
			
			User[] userList = getUslcJpaManager().getUsersClients(true);
			for (User user : userList) {
				String userStr = user.getFirstName() + " " + user.getLastName();
				getUserCbx().add( userStr );
				getUserCbx().setData(userStr, user);
			}

		}

		@Override
		public void clean() {
			
		}

		@Override
		public void refreshFormData() {
			clean();
			displayValues();
			layout();
		}
		
	}
}
