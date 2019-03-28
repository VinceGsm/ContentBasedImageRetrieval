package tp5;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

import fr.unistra.pelican.ByteImage;
import fr.unistra.pelican.Image;
import fr.unistra.pelican.IntegerImage;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;


/**
 * Example of how to take single picture.
 * 
 * @author Bartosz Firyn (SarXos)
 */
public class Camera {

	public static Image getImageFromWebcam() throws IOException {
		// get default webcam and open it
		Webcam webcam = Webcam.getDefault();
		webcam.open();

		// get image
		BufferedImage image = webcam.getImage();
		// save image to PNG file
		//ImageIO.write(image, "PNG", new File("/home/ckurtz/Bureau/testCamera.png"));
				
		//Transform the Image to the Pelican Format
		ByteImage result = new ByteImage(image.getWidth(), image.getHeight(), 1, 1, 3);

		//Parcourir l'ensemble des pixels d'une image 
		for(int x=0; x<result.getXDim();x++){
			for(int y=0; y<result.getYDim();y++){
				 //get pixel value
			    int p = image.getRGB(x,y);

			    //get red
			    int r = (p>>16) & 0xff;

			    //get green
			    int g = (p>>8) & 0xff;

			    //get blue
			    int b = p & 0xff;
				
				result.setPixelXYBByte(x, y, 0, r);
				result.setPixelXYBByte(x, y, 1, g);
				result.setPixelXYBByte(x, y, 2, b);
			}
		}
		
		
		return result;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		Image test= getImageFromWebcam();
		
		//Afficher une image 
		test.setColor(true);
		Viewer2D.exec(test);
	}
}

