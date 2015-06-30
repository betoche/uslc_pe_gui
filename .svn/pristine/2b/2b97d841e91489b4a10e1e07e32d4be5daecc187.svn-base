package com.uslc.po.gui.master;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridLayout;
import com.uslc.po.gui.master.catalog.FormCenterMaster;

public class AboutComposite extends FormCenterMaster{
	private Logger log = null;
	
	public AboutComposite( MasterCenterComposite composite ){
		super( composite, SWT.BORDER );
		initComposite();
		
		getLog().info( AboutComposite.class + " constructor called." );
	}
	
	private void initComposite(){
		FormData data = new FormData( 550, 550);
		setLayoutData(data);
		
		setLayout( new GridLayout( 6, false ) );
	}
	
	public void hide(){
		clean();
		this.setParent( getParent().getMaster().getHiddenShell() );
		this.setVisible(false);
		getParent().getMaster().getMasterTop().getCancel().setEnabled( false );
	}
	private void clean(){
	}
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger(AboutComposite.class);
			PropertyConfigurator.configure("log4j.properties");
		}
		return log;
	}
}
