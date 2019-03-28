package tp2;

import java.awt.Color;
import java.lang.reflect.Array;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;
import fr.unistra.pelican.algorithms.io.ImageSave;

public class main {

	public static void main(String[] args) {
		//ImageSave.exec(xxxxx,"C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\imageTp\\xxxxx.jpg");
		Image velo = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\velo.jpg");
		Image eiffel = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\eiffel.jpg");
		Image geek = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\geek.jpg");
		
		histogrammeImage(geek,true);
		//egalisationHisto(geek);
		
	}

	public static void couleurToGris(Image image) {
		
		
		
	}
	
	
	
	public static int[] histogrammeImage(Image image, Boolean affichage) {
		
		int[] histogram = new int[256];
		int largeur = image.getXDim();
		int hauteur = image.getYDim();
		
		for(int x=0; x<largeur ; x++){
			for(int y=0; y<hauteur ;y++) {
				
				int niveauGris = image.getPixelXYBByte(x, y, 0);
				
				histogram[niveauGris]++;
			}	
		}
		
		if(affichage) {
			///////////////// FUNCTION PROF ////////////////////////////
			XYSeries myseries = new XYSeries("Nombre de pixels");  
			for(int i=0;i<histogram.length;i++){
				myseries.add(new Double(i), new Double(histogram[i]));   
			}
			XYSeriesCollection myseriescollection = new XYSeriesCollection(myseries);   
			
	        JFreeChart jfreechart = ChartFactory.createXYBarChart("Histogramme de l'image", "Niveaux de gris", false, "Nombre de pixels", myseriescollection, PlotOrientation.VERTICAL, true, false, false);   
			jfreechart.setBackgroundPaint(Color.white);   
			XYPlot xyplot = jfreechart.getXYPlot();   
		
			xyplot.setBackgroundPaint(Color.lightGray);   
			xyplot.setRangeGridlinePaint(Color.white);   
			NumberAxis axis = (NumberAxis) xyplot.getDomainAxis();   
			
			axis.setLowerMargin(0);   
			axis.setUpperMargin(0);   

			// create and display a frame...
			ChartFrame frame = new ChartFrame("LP IOT - Image", jfreechart);
			frame.pack();
			frame.setVisible(true);
		}
		
		return histogram;
	}
	
	//pas fait
	public static void egalisationHisto(Image image) {

		
		int[] histogram = histogrammeImage(image, false);
		
		
	}
	
	
	
}
