package com.uslc.po.gui.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class WidgetsUtils {
	private static Logger log = null;
	
	public static void ColumnsDetailsExplorer( Table t, String tName ){
		for( TableColumn tc : t.getColumns() ){
			 getLog().info( tName + ": " + tc.getText() + "{width:"+tc.getWidth()+"}" );
		}
	}
	
	private static Logger getLog(){
		if( log == null ){
			log = Logger.getLogger( WidgetsUtils.class );
			PropertyConfigurator.configure("log4j.properties");
		}
		return log;
	}
}
