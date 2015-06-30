package com.uslc.pe.gui.master.catalog;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.uslc.pe.gui.master.MasterCenterComposite;
import com.uslc.pe.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.pe.gui.master.interfaces.MasterCompositeInterface;
import com.uslc.pe.gui.util.MyGridData;
import com.uslc.pe.jpa.entity.Style;
import com.uslc.pe.jpa.logic.StyleRepo;
import com.uslc.pe.jpa.util.Constants;
import com.uslc.pe.jpa.util.UslcJpa;

public class ItemManagerComposite extends FormCenterMaster implements MasterCompositeInterface {
	private Label titleLbl = null;
	private Table itemsTbl = null;
	private Label infoLbl = null;
	private Label itemLbl = null;
	private Text itemTxt = null;
	private Button action = null;
	private Button cancel = null;
	
	private Logger log = null;
	private Style selectedStyle = null;
	private boolean editing = false;
	private final String infoAddText = "Info: Add a new Item Number";
	
	private LiveDataAccessLifeCicle ldalc = null;
	
	public ItemManagerComposite( MasterCenterComposite composite ) {
		super( composite, SWT.NONE );
		getLog().info( ItemManagerComposite.class + " constructor has been called!" );
		initComposite();
	}
	private void initComposite() {
		GridLayout layout = new GridLayout( 4, false );
		setLayout(layout);
		
		FormData data = new FormData( 400, 220 );
		setLayoutData(data);
		
		getTitleLbl();
		getItemsTbl();
		getInfoLbl();
		getItemLbl();
		getItemTxt();
		getAction();
		getCancel();
		
		getLiveDataAccessLifeCicle();
	}

	
	public Label getTitleLbl() {
		if( titleLbl == null ) {
			titleLbl = new Label( this, SWT.NONE );
			titleLbl.setText( "Item Manager" );
			titleLbl.setAlignment(SWT.LEFT);
			
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
			data.horizontalSpan=4;
			titleLbl.setLayoutData(data);
			
			Label horizontalLine = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			horizontalLine.setLayoutData(data);
		}
		return titleLbl;
	}
	public Table getItemsTbl() {
		if( itemsTbl == null ){
			itemsTbl = new Table( this, SWT.SINGLE );
			Font f = itemsTbl.getFont();
			FontData[] fds = f.getFontData();
			for (int i = 0; i < fds.length; i++) {
				fds[i].setHeight(8);
			}
			itemsTbl.setFont( new Font(getDisplay(), fds));
			
			TableColumn id = new TableColumn(itemsTbl, SWT.NONE);
			id.setText("id");
			TableColumn size = new TableColumn(itemsTbl, SWT.NONE);
			size.setText( "number" );
			
			id.setWidth(30);
			size.setWidth(70);
			itemsTbl.setHeaderVisible(true);
			
			GridData data = new GridData( GridData.FILL_VERTICAL );
			data.verticalSpan=5;
			itemsTbl.setLayoutData(data);
			
			itemsTbl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					setEditMode();
				}
			});
			
			GridData data1 = new GridData(SWT.FILL, SWT.FILL, false, false);
			data1.verticalSpan=5;
			data1.widthHint=15;
			Label horizontalLine = new Label(this, SWT.SEPARATOR | SWT.VERTICAL );
			horizontalLine.setLayoutData(data1);
			
			itemsTbl.addListener( SWT.MouseHover, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					setInfoText( new InfoForm( "item ~> codes list", 
							"list all existing item codes in the database", 
							new String[]{ "double click for modifying the item" }) );
				}
			});
			itemsTbl.addListener( SWT.MouseExit, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					cleanInfoText();
				}
			});
		}
		return itemsTbl;
	}
	public Label getInfoLbl() {
		if( infoLbl == null ){
			infoLbl = new Label(this, SWT.NONE);
			infoLbl.setText(infoAddText);
			infoLbl.setLayoutData(MyGridData.getDgHorizontalDoubleSpan());
			
			Label horizontalLine = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			horizontalLine.setLayoutData(MyGridData.getDgHorizontalDoubleSpan());
		}
		return infoLbl;
	}
	public Label getItemLbl() {
		if( itemLbl == null ){
			itemLbl = new Label(this, SWT.NONE);
			itemLbl.setText("item:");
			itemLbl.setAlignment( SWT.RIGHT );
			
			GridData labelGd = new GridData( 70, 23 );
			itemLbl.setLayoutData(labelGd);
		}
		return itemLbl;
	}
	public Text getItemTxt() {
		if( itemTxt == null ){
			itemTxt = new Text(this, SWT.BORDER);
			
			GridData textGd = new GridData(100, 23);
			itemTxt.setLayoutData(textGd);
			
			itemTxt.addListener( SWT.MouseHover, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					setInfoText( new InfoForm( "item ~> code input field", 
							"input the code for updating a current item or adding a new item code", 
							null ) );
				}
			});
			itemTxt.addListener( SWT.MouseExit, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					cleanInfoText();
				}
			});
			
			Label horizontalLine = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			horizontalLine.setLayoutData(MyGridData.getDgHorizontalDoubleSpan());
		}
		return itemTxt;
	}
	public Button getAction() {
		if( action == null ){
			action = new Button(this, SWT.PUSH);
			action.setText( "add" );
			GridData gd = new GridData();
			gd.widthHint = 70;
			gd.horizontalAlignment = SWT.RIGHT;
			action.setLayoutData(gd);
			action.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						performAction();
					} catch (Exception e1) {
						getLog().error( "error", e1);
					}
				}
			});
		
			action.addListener( SWT.MouseHover, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					setInfoText( new InfoForm( "item ~> add/update button", 
							"hit to add or commit the currect change to the current selected item", 
							null ) );
				}
			});
			action.addListener( SWT.MouseExit, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					cleanInfoText();
				}
			});
		}
		return action;
	}
	public Button getCancel() {
		if( cancel == null ){
			cancel = new Button(this, SWT.PUSH);
			cancel.setText( "cancel" );
			GridData gd = new GridData();
			gd.horizontalAlignment = SWT.CENTER;
			gd.widthHint = 70;
			cancel.setLayoutData(gd);
			cancel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if( editing ){
						getLiveDataAccessLifeCicle().clean();
					}else{
						hide();
					}
				}
			});
			
			cancel.addListener( SWT.MouseHover, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					setInfoText( new InfoForm( "item ~> cancel button", 
							"hit for canceling the current action or hiding the item catalog form", 
							null ) );
				}
			});
			cancel.addListener( SWT.MouseExit, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					cleanInfoText();
				}
			});
			
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
			data.horizontalSpan = 4;
			Label horizontalLine = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL );
			horizontalLine.setLayoutData(data);
		}
		return cancel;
	}
	private Logger getLog() {
		if( log == null ){
			log = Logger.getLogger( ItemManagerComposite.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}

	private void setEditMode(){
		editing = true;
		TableItem[] selection = getItemsTbl().getSelection();
		Style style = null;
		if( selection!=null ){
			for( TableItem it : selection ){
				style = (Style)it.getData();
			}
		}
		if( style!=null ){
			editing = true;
			selectedStyle = style;
			getItemTxt().setText( String.valueOf( style.getCode() ) );
			getInfoLbl().setText( "Item["+style.getId()+"] - UPDATE" );
			getInfoLbl().setAlignment( SWT.RIGHT );
			getAction().setText("update");
		}else{
			editing = false;
			selectedStyle = null;
		}
	}
	private void performAction() throws Exception{
		Style style = null;
		String code = getItemTxt().getText().trim();
		String successMsg = "";
		String errorMsg = "";
		
		if( editing ){
			style = selectedStyle;
			successMsg = "Item updated correctly.";
			errorMsg = "There was a problem while updating the item.";
		}else{
			style = new Style();
			successMsg = "Item added correctly.";
			errorMsg = "There was a problem adding the item.";
		}
		
		style.setCode( code );
		
		UslcJpa jpa = new UslcJpa();
		int iconStyle = SWT.ICON_INFORMATION;
		MessageBox diag = new MessageBox(this.getShell(), iconStyle );
		diag.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
		if( jpa.persist(style) ){
			diag.setMessage(successMsg);
			getLiveDataAccessLifeCicle().clean();
			getLiveDataAccessLifeCicle().displayValues();
		}else{
			iconStyle = SWT.ICON_ERROR;
			diag.setMessage(errorMsg);
		}
		diag.open();
	}
	public void hide(){
		getLiveDataAccessLifeCicle().clean();
		this.setParent( getParent().getMaster().getHiddenShell() );
		this.setVisible(false);
	}

	@Override
	public InfoForm getInfoForm() {
		String title = "item catalog manager";
		String desc = "in this form you can add or modify an item number, you CAN NOT delete an existing item";
		String[] features = {"list existing items codes ordered", 
				"add new item codes to the database",
				"modify the item number for an existing item in the database" };
		
		return new InfoForm(title, desc, features);
	}
	
	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		if( ldalc == null ) {
			ldalc = new ItemManagerCompositeLogic();
		}
		return ldalc;
	}

	public class ItemManagerCompositeLogic implements LiveDataAccessLifeCicle {

		public ItemManagerCompositeLogic() {
			displayValues();
		}
		
		@Override
		public void displayValues() {
			List<Style> styles = getUslcJpaManager().getItems();
			getItemsTbl().removeAll();
			for (Style style : styles) {
				TableItem it = new TableItem(getItemsTbl(), SWT.NONE);
				String[] texts = { String.valueOf( style.getId() ), String.valueOf( style.getCode() ) };
				it.setData(style);
				it.setText( texts );
			}
			getTitleLbl().setText( "items ("+styles.size()+")" );
		}

		@Override
		public void clean() {
			editing = false;
			getItemTxt().setText("");
			getAction().setText( "add" );
			getInfoLbl().setText(infoAddText);
			getInfoLbl().setAlignment( SWT.LEFT );
		}

		@Override
		public void refreshFormData() {
			clean();
			displayValues();
			layout();
		}
		
	}
}