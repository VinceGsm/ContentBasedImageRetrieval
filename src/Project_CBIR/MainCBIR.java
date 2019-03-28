package Project_CBIR;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

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
import fr.unistra.pelican.algorithms.noise.Speckle;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;

public class MainCBIR {

	public static void main(String[] args) {
		String pathBroad = "C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTp\\Projet\\broad";
		String pathMotos = "C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTp\\Projet\\motos";
		//ImageSave.exec(xxxxx,"C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\imageTp\\xxxxx.jpg");
		Image velo = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\velo.jpg");
		Image eiffel = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\eiffel.jpg");
		Image maldive = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\maldive.jpg");
		
		int xEiffel = eiffel.getXDim();
		int yEiffel = eiffel.getYDim();
		
		//eiffel = debruiterMedian(eiffel, false);
		double[][] histoEiffel = histogrammeImageRGB(eiffel, false);
		
		//normalisation(histoEiffel, (xEiffel * yEiffel) );
		discretisation(histoEiffel);
		//normalizeHistogram(histoEiffel);
		//int nbPixelEiffel = eiffel.getXDim() * eiffel.getYDim();
		//normalisation(histogrammeImageRGB(eiffel,false), nbPixelEiffel );
		
	}
	
	
	public static Image debruiterMoyenne(Image image, Boolean affichage) {
		// Simulation Bruitage //
	
		Image resultNoise =  Speckle.exec(image, 0.1, 2);
		
		if(affichage) { Viewer2D.exec(resultNoise); }		
		// Simulation Bruitage //
		
		int largeur = image.getXDim();
		int hauteur = image.getYDim();
		
		for(int x=1; x<largeur-2 ; x++){
			for(int y=1; y<hauteur-2 ; y++) {
				
				int grisR0 = image.getPixelXYBByte(x, y, 0);
				int grisR1 = image.getPixelXYBByte(x-1, y-1, 0);
				int grisR2 = image.getPixelXYBByte(x, y-1, 0);
				int grisR3 = image.getPixelXYBByte(x+1, y-1, 0);
				int grisR4 = image.getPixelXYBByte(x-1, y, 0);
				int grisR5 = image.getPixelXYBByte(x+1, y, 0);
				int grisR6 = image.getPixelXYBByte(x-1, y+1, 0);
				int grisR7 = image.getPixelXYBByte(x, y+1, 0);
				int grisR8 = image.getPixelXYBByte(x+1, y+1, 0);
				
				int grisG0 = image.getPixelXYBByte(x, y, 1);
				int grisG1 = image.getPixelXYBByte(x-1, y-1, 1);
				int grisG2 = image.getPixelXYBByte(x, y-1, 1);
				int grisG3 = image.getPixelXYBByte(x+1, y-1, 1);
				int grisG4 = image.getPixelXYBByte(x-1, y, 1);
				int grisG5 = image.getPixelXYBByte(x+1, y, 1);
				int grisG6 = image.getPixelXYBByte(x-1, y+1, 1);
				int grisG7 = image.getPixelXYBByte(x, y+1, 1);
				int grisG8 = image.getPixelXYBByte(x+1, y+1, 1);
				
				int grisB0 = image.getPixelXYBByte(x, y, 2);
				int grisB1 = image.getPixelXYBByte(x-1, y-1, 2);
				int grisB2 = image.getPixelXYBByte(x, y-1, 2);
				int grisB3 = image.getPixelXYBByte(x+1, y-1, 2);
				int grisB4 = image.getPixelXYBByte(x-1, y, 2);
				int grisB5 = image.getPixelXYBByte(x+1, y, 2);
				int grisB6 = image.getPixelXYBByte(x-1, y+1, 2);
				int grisB7 = image.getPixelXYBByte(x, y+1, 2);
				int grisB8 = image.getPixelXYBByte(x+1, y+1, 2);
				
				int moyenneR = (grisR0+grisR1+grisR2+grisR3+grisR4+grisR5+grisR6+grisR7+grisR8)/9;
				int moyenneG = (grisG0+grisG1+grisG2+grisG3+grisG4+grisG5+grisG6+grisG7+grisG8)/9;
				int moyenneB = (grisB0+grisB1+grisB2+grisB3+grisB4+grisB5+grisB6+grisB7+grisB8)/9;
				
				resultNoise.setPixelXYBByte(x, y, 0, moyenneR);
				resultNoise.setPixelXYBByte(x, y, 1, moyenneG);
				resultNoise.setPixelXYBByte(x, y, 2, moyenneB);
			}	
		}
		if(affichage) { Viewer2D.exec(resultNoise); }	
		
		return resultNoise;
	}

	public static Image debruiterMedian(Image image, Boolean affichage) {
		// Simulation Bruitage //
		
		Image resultNoise =  Speckle.exec(image, 0.1, 2);
		
		if(affichage) { Viewer2D.exec(resultNoise); }		
		// ------------------ //
		
		int largeur = image.getXDim();
		int hauteur = image.getYDim();
		
		double[] doubleArrayR = new double[9];
		double[] doubleArrayG = new double[9];
		double[] doubleArrayB = new double[9];
		
		for(int x=1; x<largeur-2 ; x++){
			for(int y=1; y<hauteur-2 ; y++) {
	
				doubleArrayR[0] = image.getPixelXYBByte(x, y, 0);
				doubleArrayR[1] = image.getPixelXYBByte(x-1, y-1, 0);
				doubleArrayR[2] = image.getPixelXYBByte(x, y-1, 0);
				doubleArrayR[3] = image.getPixelXYBByte(x+1, y-1, 0);
				doubleArrayR[4] = image.getPixelXYBByte(x-1, y, 0);
				doubleArrayR[5] = image.getPixelXYBByte(x+1, y, 0);
				doubleArrayR[6] = image.getPixelXYBByte(x-1, y+1, 0);
				doubleArrayR[7] = image.getPixelXYBByte(x, y+1, 0);
				doubleArrayR[8] = image.getPixelXYBByte(x+1, y+1, 0);
				
				doubleArrayG[0] = image.getPixelXYBByte(x, y, 1);
				doubleArrayG[1] = image.getPixelXYBByte(x-1, y-1, 1);
				doubleArrayG[2] = image.getPixelXYBByte(x, y-1, 1);
				doubleArrayG[3] = image.getPixelXYBByte(x+1, y-1, 1);
				doubleArrayG[4] = image.getPixelXYBByte(x-1, y, 1);
				doubleArrayG[5] = image.getPixelXYBByte(x+1, y, 1);
				doubleArrayG[6] = image.getPixelXYBByte(x-1, y+1, 1);
				doubleArrayG[7] = image.getPixelXYBByte(x, y+1, 1);
				doubleArrayG[8] = image.getPixelXYBByte(x+1, y+1, 1);
				
				doubleArrayB[0] = image.getPixelXYBByte(x, y, 2);
				doubleArrayB[1] = image.getPixelXYBByte(x-1, y-1, 2);
				doubleArrayB[2] = image.getPixelXYBByte(x, y-1, 2);
				doubleArrayB[3] = image.getPixelXYBByte(x+1, y-1, 2);
				doubleArrayB[4] = image.getPixelXYBByte(x-1, y, 2);
				doubleArrayB[5] = image.getPixelXYBByte(x+1, y, 2);
				doubleArrayB[6] = image.getPixelXYBByte(x-1, y+1, 2);
				doubleArrayB[7] = image.getPixelXYBByte(x, y+1, 2);
				doubleArrayB[8] = image.getPixelXYBByte(x+1, y+1, 2);
				
				Arrays.parallelSort(doubleArrayR);
				Arrays.parallelSort(doubleArrayG);
				Arrays.parallelSort(doubleArrayB);
				
				resultNoise.setPixelXYBByte(x, y, 0, (int) doubleArrayR[4]);
				resultNoise.setPixelXYBByte(x, y, 1, (int) doubleArrayG[4]);
				resultNoise.setPixelXYBByte(x, y, 2, (int) doubleArrayB[4]);
			}	
		}
		
		
		if(affichage) { Viewer2D.exec(resultNoise); }	
		
		return resultNoise;
	}

	
	public static double[][] histogrammeImageRGB(Image image, Boolean affichage) {
		
		double[][] histogramRGB = new double[256][3];
		double largeur = image.getXDim();
		double hauteur = image.getYDim();
		
		for(int x=0; x<largeur ; x++){
			for(int y=0; y<hauteur ;y++) {
				
				double niveauR = image.getPixelXYBByte(x, y, 0);
				double niveauG = image.getPixelXYBByte(x, y, 1);
				double niveauB = image.getPixelXYBByte(x, y, 2);
				
				histogramRGB[(int) niveauR][0]++;
				histogramRGB[(int) niveauG][1]++;
				histogramRGB[(int) niveauB][2]++;
			}	
		}
		
		if(affichage) { displayHistoRGB(histogramRGB); }
		
		return histogramRGB;
	}
	
	
	public static double[][] normalisation(double[][] histogram, int nbPixel) {
		
		for(int i=0; i<255; i++ ) {
			histogram[i][0] = histogram[i][0] / nbPixel ;
			histogram[i][1] = histogram[i][1] / nbPixel ;
			histogram[i][2] = histogram[i][2] / nbPixel ;
		}
	
		displayHistoRGB(histogram);
		return histogram;
	}

	//prendre tranche de 25 (/10), faire la moyenne --> histo de 10
		public static double[][] discretisation(double[][] histogram) {
			
			double[][] histogramme = new double[10][3];
			
			
			int compteur = 0;
			
			for(int i=0; i<10; i++ ) {
				
				double moyenneTrancheR = 0;
				double moyenneTrancheG = 0;
				double moyenneTrancheB = 0;
				
				
				
				for(int x= 0; x<compteur+25; x++ ) {
					moyenneTrancheR += histogram[i][0];
					moyenneTrancheG += histogram[i][1];
					moyenneTrancheB += histogram[i][2];
					
				}
				compteur = compteur+25;
				
					//OK
					moyenneTrancheR = moyenneTrancheR /25;
					moyenneTrancheG = moyenneTrancheG /25;
					moyenneTrancheB = moyenneTrancheB /25;
					
					histogramme[i][0] = moyenneTrancheR;
					histogramme[i][1] = moyenneTrancheG;
					histogramme[i][2] = moyenneTrancheB;
					//OK

				
			}
			displayHistoRGB(histogramme);
			return histogramme;
		}
	
	public static List<Image> getAllImage(String pathToFolder) {
		
		List<Image> listImg = new ArrayList();
		String[] listFile = new File(pathToFolder).list();
		
		for(String unFile : listFile) {
			//System.out.println(pathToFolder+"\\"+unFile);
			Image uneImg = ImageLoader.exec(pathToFolder+"\\"+unFile);

			listImg.add(uneImg);
		}

		return listImg;
	}
	
	
	public static void rechercheImageSim(Image image, Boolean affichage) {
		
		TreeMap<String,Double> listImgSim = new TreeMap<>();
		
		//A
		List<Image> listImgMotos = getAllImage("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTp\\Projet\\motos");
		List<double[][]> listHistogram = new ArrayList();
		
		for(Image uneImg : listImgMotos) { 
			int nbPixel = uneImg.getXDim() * uneImg.getYDim();
			
			uneImg = debruiterMedian(uneImg, false); 
			double[][] histo = histogrammeImageRGB(uneImg, false);
			
			histo = discretisation(histo);
			histo = normalisation(histo, nbPixel);
			
			listHistogram.add(histo); 
		}
		// --- //
		
		//B
		for(double[][] unHisto : listHistogram) {
			Double similitudeR = (double) 0;
			Double similitudeG = (double) 0;
			Double similitudeB = (double) 0;
			
			
			
			
			Double similitudeValue = similitudeR + similitudeG + similitudeB;
			
			//C
			for(int i = 0; i< listImgMotos.size(); i++) {
				
				String name = "00"+ i;
				listImgSim.put(name,similitudeValue);
			}
			// --- //
		}
		// --- //
		
		//Afficher les 10 plus proches
		
	}
	
	
	public static void displayHistoRGB(double[][] histogram) {
				
		///////////////// FUNCTION PROF ////////////////////////////
		XYSeries myseriesR = new XYSeries("Nombre de pixels");
		XYSeries myseriesG = new XYSeries("Nombre de pixels");
		XYSeries myseriesB = new XYSeries("Nombre de pixels");
		
		for(int i=0;i<histogram.length;i++){
		    myseriesR.add(new Double(i), new Double(histogram[i][0]));
		}
		
		for(int i=0;i<histogram.length;i++){
		    myseriesG.add(new Double(i), new Double(histogram[i][1]));
		}
		
		for(int i=0;i<histogram.length;i++){
		    myseriesB.add(new Double(i), new Double(histogram[i][2]));
		}
		
		XYSeriesCollection myseriescollectionR = new XYSeriesCollection(myseriesR);
		XYSeriesCollection myseriescollectionG = new XYSeriesCollection(myseriesG);
		XYSeriesCollection myseriescollectionB = new XYSeriesCollection(myseriesB);
		
		JFreeChart jfreechartR = ChartFactory.createXYBarChart("Histogramme de l'image", "R", false, "Nombre de pixels", myseriescollectionR, PlotOrientation.VERTICAL, true, false, false);
		JFreeChart jfreechartG = ChartFactory.createXYBarChart("Histogramme de l'image", "G", false, "Nombre de pixels", myseriescollectionG, PlotOrientation.VERTICAL, true, false, false);
		JFreeChart jfreechartB = ChartFactory.createXYBarChart("Histogramme de l'image", "B", false, "Nombre de pixels", myseriescollectionB, PlotOrientation.VERTICAL, true, false, false);
		jfreechartR.setBackgroundPaint(Color.white);
		jfreechartG.setBackgroundPaint(Color.white);
		jfreechartB.setBackgroundPaint(Color.white);
		XYPlot xyplotR = jfreechartR.getXYPlot();
		XYPlot xyplotG = jfreechartG.getXYPlot();
		XYPlot xyplotB = jfreechartB.getXYPlot();
		
		xyplotR.setBackgroundPaint(Color.lightGray);
		xyplotG.setBackgroundPaint(Color.lightGray);
		xyplotB.setBackgroundPaint(Color.lightGray);
		
		xyplotR.setRangeGridlinePaint(Color.white);
		xyplotG.setRangeGridlinePaint(Color.white);
		xyplotB.setRangeGridlinePaint(Color.white);
		
		NumberAxis axisR = (NumberAxis) xyplotR.getDomainAxis();
		NumberAxis axisG = (NumberAxis) xyplotG.getDomainAxis();
		NumberAxis axisB = (NumberAxis) xyplotB.getDomainAxis();
		
		axisR.setLowerMargin(0);
		axisR.setUpperMargin(0);
		axisG.setLowerMargin(0);
		axisG.setUpperMargin(0);
		axisB.setLowerMargin(0);
		axisB.setUpperMargin(0);
		
		// create and display a frame...
		ChartFrame frameR = new ChartFrame("Red", jfreechartR);
		ChartFrame frameG = new ChartFrame("Green", jfreechartG);
		ChartFrame frameB = new ChartFrame("Blue", jfreechartB);
		frameR.pack();
		frameR.setVisible(true);
		frameG.pack();
		frameG.setVisible(true);
		frameB.pack();
		frameB.setVisible(true);
		///////////////// ---------- ////////////////////////////
		
	}
	
	
	private static void normalizeHistogram(double[][] histogram) {
	        
	    int numBins = histogram.length;
	    int numChannels = histogram[0].length;

	    // find maximum column total (ie. r+g+b)
	    int maxColTotal = 0;
	    int colTotal;
	    for (int w = 0; w < numBins; w++) {
		    colTotal = 0;
		    for (int c = 0; c < numChannels; c++) {
	        colTotal += histogram[w][c];
		    }
		    if(colTotal > maxColTotal) {
	        maxColTotal = colTotal;
		    }
	    }

	    // normalize histogram based on the maximum column total
	    for (int w = 0; w < numBins; w++) {
		    for (int c = 0; c < numChannels; c++) {
	        histogram[w][c] /= maxColTotal;
		    }
	    }
	    displayHistoRGB(histogram);
	}
}




			/* affichage histoRGB 3 en 1
			 ///////////////// FUNCTION PROF ////////////////////////////
			XYSeries myseries = new XYSeries("Nombre de pixels");  

			for(int i=0;i<histogramRGB.length;i++){
				myseries.add(new Double(i), new Double(histogramRGB[i][0]));   
			}
			for(int i=0;i<histogramRGB.length;i++){
				myseries.add(new Double(i), new Double(histogramRGB[i][1]));   
			}
			for(int i=0;i<histogramRGB.length;i++){
				myseries.add(new Double(i), new Double(histogramRGB[i][2]));   
			}
			
			XYSeriesCollection myseriescollection = new XYSeriesCollection(myseries);   
			
	        JFreeChart jfreechart = ChartFactory.createXYBarChart("Histogramme de l'image", "RGB", false, "Nombre de pixels", myseriescollection, PlotOrientation.VERTICAL, true, false, false);   
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
			///////////////// ---------- ////////////////////////////
 */
