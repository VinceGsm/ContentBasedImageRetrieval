package tp3;

import java.util.Arrays;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;
import fr.unistra.pelican.algorithms.io.ImageSave;
import fr.unistra.pelican.algorithms.noise.Speckle;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;

public class main3 {

	public static void main(String[] args) {
		//ImageSave.exec(xxxxx,"C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\imageTp\\xxxxx.jpg");
		Image velo = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\velo.jpg");
		Image eiffel = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\eiffel.jpg");
		Image geek = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\geek.jpg");
		
		moyenneImage(eiffel);
	}
	
	//faire une fonction générique avec une matrice 3/3 --> pas comme ce qui suit
	public static void moyenneImage(Image image) {
		
		Image resultNoise = image;
		resultNoise =  Speckle.exec(resultNoise, 0.1, 2);
		
		Viewer2D.exec(resultNoise);
		
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
		Viewer2D.exec(resultNoise);
	}
	
	
	public static void medianImage(Image image) {
		
		Image resultNoise = image;
		resultNoise =  Speckle.exec(resultNoise, 0.1, 2);
		
		Viewer2D.exec(resultNoise);
		
		int largeur = image.getXDim();
		int hauteur = image.getYDim();
		
		int[] intArrayR = new int[9];
		int[] intArrayG = new int[9];
		int[] intArrayB = new int[9];
		
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
			
				
				 Arrays.parallelSort(intArrayR);
				
				//PAS FINIT
				//resultNoise.setPixelXYBByte(x, y, 0, moyenneR);
				//resultNoise.setPixelXYBByte(x, y, 1, moyenneG);
				//resultNoise.setPixelXYBByte(x, y, 2, moyenneB);
			}	
		}
		Viewer2D.exec(resultNoise);
	}
	

}
