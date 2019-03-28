package tp1;

import fr.unistra.pelican.ByteImage;
import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;
import fr.unistra.pelican.algorithms.io.ImageSave;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;

public class main1 {

	public static void main(String[] args) {
		
		Image velo = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\velo.jpg");
		Image eiffel = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\eiffel.jpg");
		
		//lectureImage("C:\\Users\\Vincent\\Downloads\\velo.jpg");
		//affichageMatricielle(velo);
		extremaImage(velo);
	}

	
	public static Image lectureImage(String path) {
		
		Image velo = ImageLoader.exec(path);
				
		int largeur = velo.getXDim();
		int hauteur = velo.getYDim();
		
		int nbCanaux = velo.getBDim();
		
		
		System.out.println("Image chargée. Sa taille est "+largeur+" x "+hauteur +" pixels. Son nombre de canaux est : "+ nbCanaux  );
		
		Viewer2D.exec(velo);
		
		return velo;
	}
	
	
	public static void affichageMatricielle(Image image) {
		
		int largeur = image.getXDim();
		int hauteur = image.getYDim();
		
		for(int x=0; x < largeur ;x++){
			for(int y=0; y<hauteur ;y++) {
				
				System.out.println(image.getPixelXYBByte(x, y, 0) +","+ image.getPixelXYBByte(x, y, 1) +","+ image.getPixelXYBByte(x, y, 2));
			}
		}
	}
	
	
	public static void extremaImage(Image image) {
		
		int max = 0;
		int min = 255;
		int largeur = image.getXDim();
		int hauteur = image.getYDim();
		
		for(int x=0; x<largeur ; x++){
			for(int y=0; y<hauteur ;y++) {
		
				//recup valeur niveau de gris (je crois)
				int r = image.getPixelXYBByte(x, y, 0);
				//int g = image.getPixelXYBByte(x, y, 1);
				//int b = image.getPixelXYBByte(x, y, 2);
				
				if(r > max) { max = r; }
				if(r < min) { min = r; }
				
				System.out.println(r);
			}
			System.out.println("min = "+min+", max =" +max);
		}
	}

	//pas finis
	public static void inverserImage(Image image) {
		
		Image inverser = image;
		
		int largeur = image.getXDim();
		int hauteur = image.getYDim();
		
		for(int x=0; x<largeur ; x++){
			for(int y=0; y<hauteur ;y++) {
				
				for(int y1=0; y<hauteur ; x++){
					for(int x1=0; x<largeur ;y++) {
						
						inverser.setPixelXYBByte(x, y, 0, 0);
						inverser.setPixelXYBByte(x, y, 0, 0);
						inverser.setPixelXYBByte(x, y, 0, 0);
					}
				}		
				
			}
		}
		
		ImageSave.exec(inverser,"C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\imageTp\\inverserTP1.jpg");
	}
	
	
}

