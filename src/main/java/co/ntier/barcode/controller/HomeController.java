package co.ntier.barcode.controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFrame;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.impl.upcean.UPCA;
import org.krysalis.barcode4j.output.bitmap.BitmapBuilder;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public ModelAndView home(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}
	
	public static enum BarcodeType{
		UPCA
	}
	
	private static final UPCA bargen = new UPCA();
	
	@RequestMapping(value="/{type}/{barcode}")
	public void test(@PathVariable BarcodeType type, @PathVariable String barcode, HttpServletResponse response) throws IOException{
		//String barcode = "400010001002";
		
		int resolution = 300;
		BarcodeDimension dim = bargen.calcDimensions(barcode);
        final BufferedImage bi = BitmapBuilder.prepareImage(dim, resolution, BufferedImage.TYPE_BYTE_GRAY);
        int orientation = 0;
        Graphics2D g2d = BitmapBuilder.prepareGraphics2D(bi, dim, orientation, true);
        Java2DCanvasProvider provider = new Java2DCanvasProvider(g2d, orientation);
        bargen.generateBarcode(provider, barcode);
        bi.flush();
        
        ImageIO.write(bi, "jpg", response.getOutputStream());
        response.setContentType("image/jpg");
        response.flushBuffer();
	}
	
	public static void main(String[] args) throws Exception{
		String msg = "400010001002";
		int resolution = 300;
		
		
		BarcodeGenerator bargen = new UPCA();
		
		BarcodeDimension dim = bargen.calcDimensions(msg);
        final BufferedImage bi = BitmapBuilder.prepareImage(dim, resolution, BufferedImage.TYPE_BYTE_GRAY);
        int orientation = 0;
        Graphics2D g2d = BitmapBuilder.prepareGraphics2D(bi, dim, orientation, true);
        Java2DCanvasProvider provider = new Java2DCanvasProvider(g2d, orientation);
        bargen.generateBarcode(provider, msg);
        bi.flush();
		
        
        @SuppressWarnings("serial")
		JFrame frame = new JFrame("Hello World"){
        	@Override
        	public void paint(Graphics g) {
        		super.paint(g);
        		g.drawImage(bi, 0, 0, null);
        	}
        };
        
        frame.setSize(250, 250);
        frame.setVisible(true);
		
//		CanvasProvider canvas = new Java2DCanvasProvider(g2d, );
//		bargen.generateBarcode(canvas, "12345");
	}
}
