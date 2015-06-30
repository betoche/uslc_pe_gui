package com.uslc.po.gui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.uslc.po.jpa.util.Constants;

public class CommonMasterClient {
	private MessageBox errorBox = null;
	private MessageBox informationBox = null;
	private MessageBox questionBox = null;
	private Shell shell = null;
	
	public CommonMasterClient( Shell shell ) {
		this.shell = shell;
	}
	
	public MessageBox getErrorBox( String message ) {
		if( errorBox == null ){
			errorBox = new MessageBox( getShell(), SWT.ICON_ERROR );
			errorBox.setText( Constants.MESSAGE_BOX_DIAG_TITLE.toString() );
		}
		errorBox.setMessage( message );
		errorBox.open();
		return errorBox;
	}
	public MessageBox getInformationBox( String message ) {
		if( informationBox == null ){
			informationBox = new MessageBox( getShell(), SWT.ICON_INFORMATION );
		}
		informationBox.setMessage( message );
		informationBox.open();
		return informationBox;
	}
	public int getQuestionBox( String message ) {
		if( questionBox == null ){
			questionBox = new MessageBox( getShell(), SWT.ICON_QUESTION | SWT.NO | SWT.YES );
		}
		questionBox.setMessage( message );
		
		return questionBox.open();
	}
	public Shell getShell() {
		return shell;
	}
}
