package com.uslc.po.gui.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import com.ibm.icu.util.Calendar;
import com.uslc.gui.SystemCommons;
import com.uslc.po.gui.master.POMaster;
import com.uslc.po.jpa.entity.Log;
import com.uslc.po.jpa.logic.Actions;
import com.uslc.po.jpa.logic.Forms;
import com.uslc.po.jpa.util.UslcJpa;

public class ConfirmCloseListener implements Listener {
	private SystemCommons system = null;
	private Logger log = null;
	
	
	public ConfirmCloseListener(SystemCommons system) {
		this.system = system;
	}

	@Override
	public void handleEvent(Event event) {
		int style = SWT.ICON_QUESTION | SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
		MessageBox messageBox = new MessageBox(system.getShell(), style);
		messageBox.setText("Information");
		messageBox.setMessage("Are you sure you want to exit?");
		event.doit = messageBox.open() == SWT.YES;
		if (event.doit == true){
			try {
				UslcJpa jpa = new UslcJpa();
				system.getUser().setActive(false);
				jpa.persist(system.getUser());
				Log newLog = new Log();
				newLog.setActionId(Actions.EXIT.getId());
				newLog.setFormId(Forms.MASTER.getId());
				newLog.setDescription( "application exits" );
				newLog.setTimestamp(Calendar.getInstance().getTime());
				newLog.setUser(system.getUser());
				jpa.persist(newLog);
				
				system.getShell().dispose();
			} catch (Exception e) {
				getLog().error(e);
			}
		}
	}
	
	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger(ConfirmCloseListener.class);
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
}