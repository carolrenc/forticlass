package youtubeAPI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jaunt.*;

public class scraper {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws NotFound {

		  try{
			  UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
			  userAgent.sendGET("https://www.googleapis.com/youtube/v3/videos?key=AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q&fields=items(contentDetails(contentRating),snippet(title,description,tags,categoryId))&part=snippet&id=bek1y2uiQGA"); //send request
			  System.out.println(userAgent.json);
			  JNode categoryId = userAgent.json.findFirst("categoryId");
			  System.out.println("categoryId: " + categoryId);
			  YoutubeMapping ym= new YoutubeMapping();
			  Map hm= YoutubeMapping.getHm();
			  

			  System.out.println("The category is: " + hm.get(categoryId.toInt()));
			  
			  JNode tags = userAgent.json.findFirst("tags");
			  System.out.println("tags: " + tags);
			  	
			  }
		  catch(JauntException e){
				  System.err.println(e);
			  }
	}

}
