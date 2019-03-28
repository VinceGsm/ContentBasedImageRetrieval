package tp5;

import java.io.File;
import java.io.FileNotFoundException;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

public class VisualRecognitionExample {

	public static void main(String[] args) throws FileNotFoundException {
		
		
		IamOptions opt = new IamOptions.Builder()
			    .apiKey("subYUbM3JOjpl7orM-lws31o8K5HiWLwI7mCYmqQsJm0")
			    .build();
		VisualRecognition service = new VisualRecognition("2018-03-25",opt);
		
		
		System.out.println("Classify an image");
		ClassifyOptions options = new ClassifyOptions.Builder()
				.imagesFile(new File("C:\\Users\\Vincent\\eclipse-workspace\\M4105C\\ImageTP\\machu.jpg"))
				.imagesFilename("switch.png")
				.build();
		ClassifiedImages result = service.classify(options).execute();
		System.out.println(result);

	}
}