package com.uslc.po.gui.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.xml.sax.SAXException;

public class Barcode4JGenerator {
	public Barcode4JGenerator(){
	}
	
	public ByteArrayInputStream getGeneratedBarcode( String msg ){
		ByteArrayInputStream in = null;
		
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
		Configuration cfg;
		try {
			System.out.println( new File("") .getAbsolutePath() );
			cfg = builder.buildFromFile(new File("barcode-cfg.xml"));
			BarcodeGenerator gen = BarcodeUtil.getInstance().createBarcodeGenerator( cfg );
			
			//OutputStream out = new java.io.FileOutputStream(new File(msg+".png"));
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); 
			
			
			//BitmapCanvasProvider provider = new BitmapCanvasProvider( out, "image/x-png", 300, BufferedImage.TYPE_BYTE_GRAY, true, 0);
			BitmapCanvasProvider provider = new BitmapCanvasProvider( byteOut, "image/x-png", 300, BufferedImage.TYPE_BYTE_GRAY, true, 0);
			
			gen.generateBarcode(provider, msg);
			provider.finish();
			
			in = new ByteArrayInputStream( byteOut.toByteArray() );
		} catch( IllegalArgumentException e ){
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BarcodeException e) {
			e.printStackTrace();
		}
		
		return in;
	}
}