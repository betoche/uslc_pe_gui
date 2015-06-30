package com.uslc.po.gui.master;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import com.uslc.gui.SystemCommons;
import com.uslc.po.gui.master.catalog.FormCenterMaster;
import com.uslc.po.gui.master.catalog.FormCenterMaster.InfoForm;
import com.uslc.po.gui.master.interfaces.SystemCommonMethods;
import com.uslc.po.jpa.entity.User;

public class Master extends SystemCommons implements SystemCommonMethods {
	private Logger log = null;
	private Shell hiddenShell = null;
	private MasterMenu masterMenuBar = null;
	private MasterTopComposite masterTop = null;
	private MasterLeftComposite masterLeft = null;
	private MasterCenterComposite masterCenter = null;
	private MasterRightComposite masterRight = null;
	private MasterBottomComposite masterBottom = null;
	
	public Master(Shell shell, Display display, User user) {
		super(shell, display, user);
		getLog().info( Master.class + " constructor called." );
	}

	@Override
	public void setSize() {
		super.setSize( 1050, 800 );
		getLog().info( "current size["+getSize().x+","+getSize().y+"]" );
	}

	@Override
	public void initGui() {
		GridLayout gridLayout = new GridLayout( 3, false );
		gridLayout.horizontalSpacing = 2;
		gridLayout.marginBottom = 2;
		gridLayout.marginTop = 2;
		gridLayout.marginLeft = 2;
		gridLayout.marginRight = 2;
		super.setLayout( gridLayout );
		setMenuBar( getMasterMenuBar() );
		super.setText("purchase order master - USLC Apparel");
		Image img = new Image(getDisplay(), "images/po.ico");
		super.setImage(img);
		
		getMasterTop();
		getMasterLeft();
		getMasterCenter();
		getMasterRight();
		getMasterBottom();
	}

	public MasterTopComposite getMasterTop() {
		if( masterTop == null ) {
			masterTop = new MasterTopComposite(this);
		}
		return masterTop;
	}
	public MasterLeftComposite getMasterLeft() {
		if( masterLeft == null ) {
			masterLeft = new MasterLeftComposite(this);
		}
		return masterLeft;
	}
	public MasterCenterComposite getMasterCenter() {
		if( masterCenter == null ) {
			masterCenter = new MasterCenterComposite(this);
		}
		return masterCenter;
	}
	public MasterRightComposite getMasterRight() {
		if( masterRight == null ) {
			masterRight = new MasterRightComposite(this);
		}
		return masterRight;
	}
	public MasterBottomComposite getMasterBottom() {
		if( masterBottom == null ) {
			masterBottom = new MasterBottomComposite(this);
		}
		return masterBottom;
	}
	public void setLastAction( String text, Composite composite ){
		getMasterBottom().setLastAction(text, composite);
	}

	public void hideAllComposites(){
		for( MasterSections section : getOpenedMasterSections() ) {
			for( Composite form : section.getOpenedComposite() ) {
				try{
					if( form instanceof FormCenterMaster ) {
						((FormCenterMaster)form).hide();
					}
				}catch( Exception e ) {
					getLog().error( "error hidding the composite", e );
				}
			}
		}
	}
	public void setInfoText( InfoForm info ){
		getMasterRight().setInfoText(info);
	}
	public Shell getHiddenShell(){
		if( hiddenShell == null ){
			hiddenShell = new Shell();
		}
		return hiddenShell;
	}
	public void cleanInfoText(){
		getMasterRight().setInfoText(null);
	}
	
	@Override
	public Logger getLog() {
		if( log == null ) {
			log = Logger.getLogger( Master.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	public MasterMenu getMasterMenuBar() {
		if( masterMenuBar == null ) {
			masterMenuBar = new MasterMenu(this);
		}
		return masterMenuBar;
	}
}
