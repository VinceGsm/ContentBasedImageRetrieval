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
		int[][] histoEiffel = histogrammeImageRGB(eiffel, false);
		
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
		
		int[] intArrayR = new int[9];
		int[] intArrayG = new int[9];
		int[] intArrayB = new int[9];
		
		for(int x=1; x<largeur-2 ; x++){
			for(int y=1; y<hauteur-2 ; y++) {
	
				intArrayR[0] = image.getPixelXYBByte(x, y, 0);
				intArrayR[1] = image.getPixelXYBByte(x-1, y-1, 0);
				intArrayR[2] = image.getPixelXYBByte(x, y-1, 0);
				intArrayR[3] = image.getPixelXYBByte(x+1, y-1, 0);
				intArrayR[4] = image.getPixelXYBByte(x-1, y, 0);
				intArrayR[5] = image.getPixelXYBByte(x+1, y, 0);
				intArrayR[6] = image.getPixelXYBByte(x-1, y+1, 0);
				intArrayR[7] = image.getPixelXYBByte(x, y+1, 0);
				intArrayR[8] = image.getPixelXYBByte(x+1, y+1, 0);
				
				intArrayG[0] = image.getPixelXYBByte(x, y, 1);
				intArrayG[1] = image.getPixelXYBByte(x-1, y-1, 1);
				intArrayG[2] = image.getPixelXYBByte(x, y-1, 1);
				intArrayG[3] = image.getPixelXYBByte(x+1, y-1, 1);
				intArrayG[4] = image.getPixelXYBByte(x-1, y, 1);
				intArrayG[5] = image.getPixelXYBByte(x+1, y, 1);
				intArrayG[6] = image.getPixelXYBByte(x-1, y+1, 1);
				intArrayG[7] = image.getPixelXYBByte(x, y+1, 1);
				intArrayG[8] = image.getPixelXYBByte(x+1, y+1, 1);
				
				intArrayB[0] = image.getPixelXYBByte(x, y, 2);
				intArrayB[1] = image.getPixelXYBByte(x-1, y-1, 2);
				intArrayB[2] = image.getPixelXYBByte(x, y-1, 2);
				intArrayB[3] = image.getPixelXYBByte(x+1, y-1, 2);
				intArrayB[4] = image.getPixelXYBByte(x-1, y, 2);
				intArrayB[5] = image.getPixelXYBByte(x+1, y, 2);
				intArrayB[6] = image.getPixelXYBByte(x-1, y+1, 2);
				intArrayB[7] = image.getPixelXYBByte(x, y+1, 2);
				intArrayB[8] = image.getPixelXYBByte(x+1, y+1, 2);
				
				Arrays.parallelSort(intArrayR);
				Arrays.parallelSort(intArrayG);
				Arrays.parallelSort(intArrayB);
				
				resultNoise.setPixelXYBByte(x, y, 0, intArrayR[4]);
				resultNoise.setPixelXYBByte(x, y, 1, intArrayG[4]);
				resultNoise.setPixelXYBByte(x, y, 2, intArrayB[4]);
			}	
		}
		
		
		
		
		if(affichage) { Viewer2D.exec(resultNoise); }	
		
		return resultNoise;
	}

	
	public static int[][] histogrammeImageRGB(Image image, Boolean affichage) {
		
		int[][] histogramRGB = new int[256][3];
		int largeur = image.getXDim();
		int hauteur = image.getYDim();
		
		for(int x=0; x<largeur ; x++){
			for(int y=0; y<hauteur ;y++) {
				
				int niveauR = image.getPixelXYBByte(x, y, 0);
				int niveauG = image.getPixelXYBByte(x, y, 1);
				int niveauB = image.getPixelXYBByte(x, y, 2);
				
				histogramRGB[niveauR][0]++;
				histogramRGB[niveauG][1]++;
				histogramRGB[niveauB][2]++;
			}	
		}
		
		if(affichage) { displayHistoRGB(histogramRGB); }
		
		return histogramRGB;
	}
	
	
	public static int[][] normalisation(int[][] histogram, int nbPixel) {
		
		for(int i=0; i<255; i++ ) {
			histogram[i][0] = histogram[i][0] / nbPixel ;
			histogram[i][1] = histogram[i][1] / nbPixel ;
			histogram[i][2] = histogram[i][2] / nbPixel ;
		}
	
		displayHistoRGB(histogram);
		return histogram;
	}
	
	//prendre tranche de 25 (/10), faire la moyenne --> histo de 10
	public static int[][] discretisation(int[][] histogram) {
		
		int[][] histogramme = new int[10][3];
		int cpt =0;
		
		for(int i=0; i<9; i++ ) {
			
			int moyenneTrancheR = 0;
			int moyenneTrancheG = 0;
			int moyenneTrancheB = 0;
			
			for(int x= cpt; x<24; x++ ) {
				moyenneTrancheR += histogram[x][0];
				moyenneTrancheG += histogram[x][1];
				moyenneTrancheB += histogram[x][2];
			}
			
			moyenneTrancheR = moyenneTrancheR /25;
			moyenneTrancheG = moyenneTrancheG /25;
			moyenneTrancheB = moyenneTrancheB /25;
			
			histogramme[i][0] = moyenneTrancheR;
			histogramme[i][1] = moyenneTrancheG;
			histogramme[i][2] = moyenneTrancheB;
			
			cpt += 25;
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
		List<int[][]> listHistogram = new ArrayList();
		
		for(Image uneImg : listImgMotos) { 
			int nbPixel = uneImg.getXDim() * uneImg.getYDim();
			
			uneImg = debruiterMedian(uneImg, false); 
			int[][] histo = histogrammeImageRGB(uneImg, false);
			
			histo = discretisation(histo);
			histo = normalisation(histo, nbPixel);
			
			listHistogram.add(histo); 
		}
		// --- //
		
		//B
		for(int[][] unHisto : listHistogram) {
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
	
	
	public static void displayHistoRGB(int[][] histogram) {
				
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
	
	
	private static void normalizeHistogram(int[][] histogram) {
	        
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
