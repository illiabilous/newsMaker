import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageTreatment {
	
	  ImageIO loadImage(String href, String title) {
		  int minH=150;
		  int minW=300;
		  try {
			    BufferedImage image = null;
			    if (href.contains("http")) {
			    	System.out.println(href);
			    	URL url = new URL(href);
			    	image = ImageIO.read(url);
			    }
			    
                if (image != null){
                	if(image.getHeight()>minH&&image.getWidth()>minW){
                		ImageIO.write(image, "jpg", new File(title.replace(" ","_")+".jpg"));
                		System.out.println("Image ["+title+".jpg] was successfully downloaded");
                    }
                }
                else System.out.println("No image");

                //Thread.sleep(2000);
		  
	  }  catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;

	  }
}