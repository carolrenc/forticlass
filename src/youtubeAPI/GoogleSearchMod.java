/**
 * 
 */
package youtubeAPI;

import com.jaunt.JauntException;

/**
 * @author danielcantrelle
 *
 */
public class GoogleSearchMod {

	public static void getClassification(String video_url){
	    Scraper scraper = new Scraper();
        String category = null;
	    try{
	        category = GoogleSearchClassification.classify(scraper.getTitle(scraper.getYoutubeId(video_url)));
        } catch(JauntException e){
            e.printStackTrace();
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
		}
	}

}
