package com.uslc.po.gui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.uslc.po.gui.client.POClient;
import com.uslc.po.gui.master.POMaster;

public class Master extends CommonMasterClient {
	private POMaster master = null;
	private POClient client = null;
	private Font defaultTicketFont = null;
	private Font defaultMasterFont = null;
	private String dbHost = "";
	private String dbPort = "";
	private String dbName = "";
	private String defaultTicketPrinter = "";
	private int numberOfCopies = 0;
	private String shipTo = "";
	private String shipFrom = "";
	private String departmentNumber = "";
	private String countryOfOrigin = "";
	private int defaultQtyPerCarton = 0;
	private String preticketedReportPath = "";
	private String noPreticketedReportPath = "";
	private String blanckPreticketedPath = "";
	private String stickerReportPath = "";
	private String blankStickerReportPath = "";
	private boolean automaticPrinting = false;
	private Properties masterProps = null;
	private Logger log = null;
	private boolean printingDialogEnabled = false;
	
	public Master( POMaster master ) throws IOException{
		super(master.getShell());
		this.master = master;
		getMasterProperties();
	}
	public Master( POClient client ) throws IOException{
		super(client.getShell());
		this.client = client;
		getMasterProperties();
	}
	public POClient getClient(){
		return client;
	}
	public POMaster getMaster(){
		return master;
	}
	public Properties getMasterProperties() throws IOException{
		getLog().info( "getMasterProps[" + masterProps + "] method" );
		if( masterProps == null ){
			InputStream input = null;
			try{
				//getLog().info( (new File("")).getAbsolutePath() );
				input = new FileInputStream( new File("uslc_master.properties" ) );
				masterProps = new Properties();
				masterProps.load(input);
				
				dbHost = String.valueOf( masterProps.getProperty( "db-host", "db" ) );
				dbPort = String.valueOf( masterProps.getProperty( "db-port", "3306" ) );
				dbName = String.valueOf( masterProps.getProperty( "db-name", "uslc" ) );
				defaultTicketPrinter = String.valueOf( masterProps.getProperty( "ticket-default-printer", "" ) );
				shipTo = String.valueOf( masterProps.getProperty( "ship-to", "ED 6755 ROAD\nGROVEPORT OH 43125" ) );
				shipFrom = String.valueOf( masterProps.getProperty( "ship-from", "USLC APPAREL S.A.\nCARRETERA NORTE ANTIGUA PEPSI 2C\nAL NORTE\nMANAGUA, A 49\nNICARAGUA" ) );
				departmentNumber = String.valueOf( masterProps.getProperty( "department-number", "003" ) );
				countryOfOrigin = String.valueOf( masterProps.getProperty( "country-of-origin", "Nicaragua" ) );
				defaultQtyPerCarton = Integer.parseInt( String.valueOf( masterProps.getProperty( "items-per-carton", "12" ) ) );
				preticketedReportPath = String.valueOf( masterProps.getProperty( "preticketed-report-path", "" ) );
				noPreticketedReportPath = String.valueOf( masterProps.getProperty( "no-preticketed-report-path", "" ) );
				blanckPreticketedPath = String.valueOf( masterProps.getProperty( "blank-preticketed-report-path", "" ) );
				stickerReportPath = String.valueOf( masterProps.getProperty( "sticker-report-path", "" ) );
				blankStickerReportPath = String.valueOf( masterProps.getProperty( "blank-sticker-report-path", "" ) );
				numberOfCopies = Integer.parseInt( masterProps.getProperty( "number-of-copies", "1" ) );
				automaticPrinting = Boolean.parseBoolean( masterProps.getProperty( "automatic-printing", "true" ) );
				printingDialogEnabled = Boolean.parseBoolean( masterProps.getProperty( "printing-dialog-enabled" , "true" ) );
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						getLog().error("Error at getMasterProperties method", e);
					}
				}
			}
		}
		return masterProps;
	}
	public Font getDefaultTicketFont() throws IOException{
		if( defaultTicketFont == null ){
			String fontName = getMasterProperties().getProperty("ticket-font-family");
				if(getPoMaster().getDisplay().getCurrent().loadFont( "font/" + fontName )){
					defaultTicketFont = new Font( getMaster().getDisplay(), fontName, 8, SWT.NORMAL );
				}else{
					getLog().info("couldn't load font in font/" + fontName );
					defaultTicketFont = getMaster().getDisplay().getSystemFont();
				}
		}
		return defaultTicketFont;
	}
	public Font getDefaultMasterFont() throws IOException {
		if( defaultMasterFont == null ){
			String fontName = getMasterProperties().get("master-default-font-family").toString();
			if(getMaster().getDisplay().getCurrent().loadFont( "fonts/" + fontName)){
				defaultMasterFont = new Font(getMaster().getDisplay(), "Segoe UI"/*fontName*/, 8, SWT.NORMAL);
			}else{
				getLog().info( "fonts/"+fontName + " couldn't be loaded" );
				defaultMasterFont = getMaster().getDisplay().getSystemFont();
			}
			
		}
		return defaultMasterFont;
	}
	public String getDbHost() {
		return dbHost;
	}
	public String getDbPort() {
		return dbPort;
	}
	public String getDbName() {
		return dbName;
	}
	public String getDefaultTicketPrinter() {
		return defaultTicketPrinter;
	}
	public boolean isAutomaticPrinting() {
		return automaticPrinting;
	}
	public int getDefaultQtyPerCarton() {
		return defaultQtyPerCarton;
	}
	public int getNumberOfCopies() {
		return numberOfCopies;
	}
	public String getShipTo() {
		return shipTo;
	}
	public String getShipFrom() {
		return shipFrom;
	}
	public String getDepartmentNumber() {
		return departmentNumber;
	}
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	public String getPreticketedReportPath() {
		return preticketedReportPath;
	}
	public String getNoPreticketedReportPath() {
		return noPreticketedReportPath;
	}
	public String getBlanckPreticketedPath() {
		return blanckPreticketedPath;
	}
	public String getStickerReportPath() {
		return stickerReportPath;
	}
	public String getBlankStickerReportPath() {
		return blankStickerReportPath;
	}

	private Logger getLog(){
		if( log == null ){
			log = Logger.getLogger( Master.class );
			PropertyConfigurator.configure( "log4j.properties" );
		}
		return log;
	}
	public POMaster getPoMaster(){
		return master;
	}
	public Date getCurrDate(){
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime();
	}
	
	public void getColumnsWidth( Table t ){
		for (TableColumn col : t.getColumns()) {
			getLog().info( col.getText() + ": " + col.getWidth() );
		}
	}
	public boolean saveMasterProperties(){
		boolean success = false;
		
		FileOutputStream out;
		try {
			out = new FileOutputStream( new File("uslc_master.properties" ) );
			
			getMasterProperties().setProperty( "db-host", getDbHost() );
			getMasterProperties().setProperty( "db-port", getDbPort() );
			getMasterProperties().setProperty( "db-name", getDbName() );
			getMasterProperties().setProperty( "ticket-default-printer", getDefaultTicketPrinter() );
			getMasterProperties().setProperty( "ship-to", getShipTo() );
			getMasterProperties().setProperty( "ship-from", getShipFrom() );
			getMasterProperties().setProperty( "department-number", getDepartmentNumber() );
			getMasterProperties().setProperty( "country-of-origin", getCountryOfOrigin() );
			getMasterProperties().setProperty( "items-per-carton", String.valueOf( getDefaultQtyPerCarton() ) );
			getMasterProperties().setProperty( "preticketed-report-path", getPreticketedReportPath() );
			getMasterProperties().setProperty( "no-preticketed-report-path", getNoPreticketedReportPath() );
			getMasterProperties().setProperty( "blank-preticketed-report-path", getBlanckPreticketedPath() );
			getMasterProperties().setProperty( "sticker-report-path", getStickerReportPath() );
			getMasterProperties().setProperty( "blank-sticker-report-path", getBlankStickerReportPath() );
			getMasterProperties().setProperty( "number-of-copies",String.valueOf( getNumberOfCopies() ) );
			getMasterProperties().setProperty( "automatic-printing", String.valueOf( isAutomaticPrinting() ) );
			getMasterProperties().setProperty( "printing-dialog-enabled", String.valueOf( isPrintingDialogEnabled() ) );
			
			getMasterProperties().store(out, null);
			out.close();
			success = true;
		} catch (FileNotFoundException e) {
			getLog().info( "error saving master properties", e );
		} catch (IOException e) {
			getLog().info( "error saving master properties", e );
		}
		
		return success;
	}
	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}
	public void setDefaultTicketPrinter(String defaultPrinter) {
		this.defaultTicketPrinter = defaultPrinter;
	}
	public void setAutomaticPrinting(boolean automaticPrinting) {
		this.automaticPrinting = automaticPrinting;
		
	}
	public boolean isPrintingDialogEnabled() {
		return printingDialogEnabled;
	}
}
