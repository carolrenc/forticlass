package youtubeAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jaunt.*;

import twitter4j.TwitterException;

public class Scraper {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws NotFound, IOException {

		  try{
			  UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
			  userAgent.sendGET("https://www.googleapis.com/youtube/v3/videos" +
					  "?key=AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q&fields=" +
					  "items(contentDetails(contentRating),snippet" +
					  "(title,description,tags,categoryId))&part=" +
					  "snippet&id=bek1y2uiQGA"); //send request
			  System.out.println(userAgent.json);
			  JNode categoryId = userAgent.json.findFirst("categoryId");
			  System.out.println("categoryId: " + categoryId);
			  YoutubeMapping ym= new YoutubeMapping();
			  Map hm= YoutubeMapping.getHm();
			  

			  System.out.println("The category is: " + hm.get(categoryId.toInt()));
			  String youtubeURL="https://www.youtube.com/watch?v=bek1y2uiQGA";
			  JNode tags = userAgent.json.findFirst("tags");
			  System.out.println("tags: " + tags);
			  
			  JNode title= userAgent.json.findFirst("title");
			  System.out.println("title: " + title);
			  //findTweets(title.toString());
			  findTweets(youtubeURL);
			  	
			  }
		  catch(JauntException e){
				  System.err.println(e);
		  }
	}

	// case-insensitivity is important
	String findTags(String video_id){
		try{
			UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
			userAgent.sendGET("https://www.googleapis.com/youtube/v3/videos?key=" +
					"AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q&fields=items(" +
					"contentDetails(contentRating),snippet(title,description,tags,categoryId))" +
					"&part=snippet&id=" + video_id); //send request
			//System.out.println(userAgent.json);
			JNode categoryId = userAgent.json.findFirst("categoryId");
			// System.out.println("categoryId: " + categoryId);
			YoutubeMapping ym= new YoutubeMapping();
			Map hm= YoutubeMapping.getHm();


			// System.out.println("The category is: " + hm.get(categoryId.toInt()));

			JNode tags = userAgent.json.findFirst("tags");
			//System.out.println("tags: " + tags);
			return tags.toString().toLowerCase();

		}
		catch(JauntException e){
			System.err.println(e);
		}
		return "ERR";
	}

	String[] findTitleAndTags(String video_id){
		String[] retList = {"",""};
		try{
			UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
			userAgent.sendGET("https://www.googleapis.com/youtube/v3/videos?key=" +
					"AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q&fields=items(" +
					"contentDetails(contentRating),snippet(title,description,tags,categoryId))" +
					"&part=snippet&id=" + video_id); //send request
			//System.out.println(userAgent.json);
			//JNode categoryId = userAgent.json.findFirst("categoryId");
			// System.out.println("categoryId: " + categoryId);
			YoutubeMapping ym= new YoutubeMapping();
			Map hm= YoutubeMapping.getHm();


			// System.out.println("The category is: " + hm.get(categoryId.toInt()));

			JNode title = userAgent.json.findFirst("title");
			//System.out.println("title: " + title);
			retList[0] = title.toString().toLowerCase();

			JNode tags = userAgent.json.findFirst("tags");
			//System.out.println("tags: " + tags);
			retList[1] = tags.toString().toLowerCase();
			return retList;
		}
		catch(JauntException e){
			System.err.println(e);
		}
		retList[0] = "ERR";
		retList[1] = "ERR";
		return retList;
	}

	void findDescription(String video_id){
		try{
			UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
			userAgent.sendGET("https://www.googleapis.com/youtube/v3/videos?key=" +
					"AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q&fields=items(" +
					"contentDetails(contentRating),snippet(title,description,tags,categoryId))" +
					"&part=snippet&id=" + video_id); //send request

			JNode description = userAgent.json.findFirst("description");
			System.out.println(description);
		}
		catch(JauntException e){
			System.err.println(e);
		}
	}

	// gives related videos list
	String[] findRelatedVideos(String video_id){
		try{
			UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
			userAgent.sendGET("https://www.googleapis.com/youtube/v3/search?part=snippet&relatedToVideoId=" +
					video_id + "&type=video&key=" +
					"AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q"); //send request

			System.out.println(userAgent.json);
		}
		catch(JauntException e){
			System.err.println(e);
		}
		return null;
	}
	
	static void findTweets(String query) throws IOException{
		try {
			twitter.getTweets(query);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
