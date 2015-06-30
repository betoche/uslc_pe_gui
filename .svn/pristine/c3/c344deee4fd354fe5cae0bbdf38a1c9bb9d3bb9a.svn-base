package com.uslc.po.gui.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageUtils {
	public static Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, 
		image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}
	
	public static Image getBarcodeImage( Display display, String code ){
		Image img = null;
		ByteArrayInputStream in = getGeneratedBarcodeImage( code );
		if( in !=null ){
			img = ImageUtils.resize( new Image(display, in), 105, 70 );
		}else{
			System.out.println( "in["+in+"]" );
		}
		return img;
	}
	
	public static BufferedImage createResizedCopy(java.awt.Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
    	System.out.println("resizing...");
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) {
    		g.setComposite(AlphaComposite.Src);
    	}
    	g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    	g.dispose();
    	return scaledBI;
    }
	
	public static java.awt.Image getAwtBarcodeImage( String code ){
		ByteArrayInputStream bis = getGeneratedBarcodeImage( code );
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("png");
 
        //ImageIO is a class containing static methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding. 
 
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; 
        ImageInputStream iis;
        java.awt.Image image = null;
		try {
			iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			
			ImageReadParam param = reader.getDefaultReadParam();
			image = reader.read(0, param);
		} catch (IOException e) {
			e.printStackTrace();
		} 
               
        return image;
	}
	public static ByteArrayInputStream getGeneratedBarcodeImage( String code ){
		Barcode4JGenerator barcode4j = new Barcode4JGenerator();
		ByteArrayInputStream in = barcode4j.getGeneratedBarcode( code );
		return in;
	}
}
