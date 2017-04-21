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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String result;
			result = GoogleSearchClassification.classify("Benjamin Zander");
			System.out.print("\n\nFinal Classification: " + result);
		} catch (JauntException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
