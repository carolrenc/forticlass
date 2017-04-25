/**
 * 
 */
package youtubeAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jaunt.JauntException;

/**
 * @author danielcantrelle
 *
 */
public class GoogleSearchMod {
	
	static Logger LOGGER = Logger.getLogger(Logger.class.getName());

	public static void getClassification(String video_url){
	    Scraper scraper = new Scraper();
        String category = null;
	    try{
	        category = GoogleSearchClassification.classify(scraper.getTitle(video_url));
        } catch(JauntException e){
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "an exception was thrown in GoogleSearchMod getClassification ", e);
        }

        System.out.println(category);
        //SARA DO THIS
    }

	GoogleSearchMod(){}

	public static void main(String[] args) {
		try {
			String result;
			result = GoogleSearchClassification.classify("Benjamin Zander");
			System.out.print("\n\nFinal Classification: " + result);
		} catch (JauntException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "an exception was thrown in GoogleSearchMod main", e);
		}
	}

}
