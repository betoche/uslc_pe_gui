package com.uslc.po.gui.master;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.jpa.entity.User;

public class MasterBottomComposite extends MasterSections {
	private CLabel sessionInfo = null;
	private Label usersInfo = null;
	private Link lastAction = null;
	private Composite lastOpenedComposite = null;
	private String text = "last action";
	private LiveDataAccessLifeCicle ldalc = null;
	
	private Logger log = null;
	
	public MasterBottomComposite( Master master ){
		super( master, SWT.BORDER );
		
		initComposite();
		getLiveDataAccessLifeCicle();
	}
	
	protected void initComposite(){
		GridData data = new GridData(GridData.FILL_HORIZONTAL );
		data.heightHint = 25;
		data.horizontalSpan = 3;
		setLayoutData( data );
		
		GridLayout layout = new GridLayout( 3, false );
		layout.verticalSpacing = SWT.FILL;
		layout.marginBottom=0;
		layout.marginTop=0;
		layout.marginLeft=10;
		layout.marginRight=10;
		layout.marginWidth=0;
		layout.marginHeight=0;
		setLayout(layout);
		
		getSessionInfo();
		getUsersInfo();
		getLastAction();
	}
	
	public void setLastAction( String text, Composite composite ) {
		this.lastOpenedComposite = composite;
		this.text = text;
		getLastAction().setText( "<a>"+text+"</a>" );
	}
	public Composite getLastOpenedComposite(){
		return this.lastOpenedComposite;
	}
	
	public CLabel getSessionInfo(){
		if( sessionInfo == null ){
			sessionInfo = new CLabel(this, SWT.NONE);
			sessionInfo.setImage( new Image(getDisplay(), "images/user.png") );
			//getSessionInfo().setText( "user:[No username still]" );
			
			GridData data = new GridData(SWT.FILL, SWT.FILL, false, true );
			data.horizontalAlignment=SWT.LEFT;
			data.verticalAlignment=SWT.CENTER;
			data.widthHint = 200;
			sessionInfo.setLayoutData( data );
		}
		return sessionInfo;
	}
	public Label getUsersInfo(){
		if( usersInfo == null ){
			usersInfo = new Label(this, SWT.NONE);
			usersInfo.setAlignment( SWT.CENTER );
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true );
			data.horizontalAlignment=SWT.CENTER;
			data.verticalAlignment=SWT.CENTER;
			usersInfo.setLayoutData( data );
		}
		return usersInfo;
	}
	public Link getLastAction() {
		if( lastAction == null ){
			lastAction = new Link(this, SWT.NONE);
			
			lastAction.addSelectionListener( new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if( getLastOpenedComposite()!=null ) {
						getMaster().getMasterCenter().showComposite( getLastOpenedComposite() );
						getMaster().getMasterCenter();
						getMaster().getShell().layout();
					}
				}
			});
			//lastAction.setAlignment( SWT.RIGHT );
			
			GridData data = new GridData(SWT.FILL, SWT.FILL, false, true );
			data.horizontalAlignment=SWT.RIGHT;
			data.verticalAlignment=SWT.CENTER;
			data.widthHint = 200;
			lastAction.setLayoutData( data );
		}
		return lastAction;
	}
	
	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		if( ldalc == null ) {
			ldalc = new MasterBottomCompositeLogic();
		}
		return ldalc;
	}
	
	public class MasterBottomCompositeLogic implements LiveDataAccessLifeCicle {
		public MasterBottomCompositeLogic() {
			displayValues();
			new AutoUpdate( getDisplay(), getUsersInfo() ) .start();
		}
		
		@Override
		public void displayValues() {
			getSessionInfo().setText( "user:["+getMaster().getUser().getFirstName()+" "+getMaster().getUser().getLastName()+"]" );
			lastAction.setText("<a>"+text+"</a>");
		}
		@Override
		public void clean() {
		}
		@Override
		public void refreshFormData() {
			
		}
		
	}
	
	public class AutoUpdate extends Thread {
		Display display = null;
		Label usersInfo = null;
		
		public AutoUpdate( Display display, Label usersInfo ) {
			this.display = display;
			this.usersInfo = usersInfo;
		}
		
		public void run() {
	        while( !display.isDisposed() ) {
	        	display.asyncExec( new Runnable() {
	        		
					@Override
					public void run() {
						if( sessionInfo.isDisposed() ) {
							return;
						}
						int active = 0;
						int inactive = 0;
						int total = 0;
						getUslcJpaManager().refreshUsers();
						for( User u : getUslcJpaManager().getUsers(true) ) {
							if( u.getUserType()==1 ){
								if( u.isActive() ) {
									active++;
								}else{
									inactive++;
								}
								total++;
							}
						}
						
						usersInfo.setText("clients["+total+"] = {active: "+active+", inactive: "+inactive+"}");
						layout();
						//getLog().info( "clients["+total+"] = {active: "+active+", inactive: "+inactive+"}" );
					}
	        		
	        	});
	        	try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger( MasterBottomComposite.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
}
