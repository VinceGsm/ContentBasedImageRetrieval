package tp4;


import tp4.Pixel;
import java.util.List;
import java.util.TreeSet;
import java.util.ArrayList;
import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;

public class Main4 {

	public static void main(String[] args) {
		
		//ImageSave.exec(xxxxx,"C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\imageTp\\xxxxx.jpg");
		Image foie = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\foie.jpg");
		Image poivron = ImageLoader.exec("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\poivron.png");

	}

	
	public static void segmentationGrowingRegion(Image image, List<Pixel> seeds) {
		
		List<TreeSet<Pixel>> regions = new ArrayList();
		
		for(Pixel pixel :seeds) {
			
			int xPixel = pixel.getX();
			int yPixel = pixel.getY();
			TreeSet<Pixel> region = new TreeSet<Pixel>();
			
			if(regions.contains(pixel)) {
				
			}
			else {
				region.add(pixel);
				
				int moyenne = 0;
				for(Pixel unPixel : region) {
					moyenne += image.getPixelXYBByte(xPixel, yPixel, 0);
				}
				List<Pixel> nearPixels = new ArrayList();
				nearPixels.add(new Pixel(xPixel -1, yPixel-1));
			}
		}
		
	}
	
	
}
