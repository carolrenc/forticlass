package youtubeAPI;

import java.awt.List;
import java.util.ArrayList;

import javax.management.Query;
import javax.net.ssl.SSLEngineResult.Status;

import twitter4j.GeoLocation;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.*;

public class twitter {
	static void getTweets(String searchquery) throws TwitterException{
		  ConfigurationBuilder cb = new ConfigurationBuilder();
		  cb.setDebugEnabled(true);
		  cb.setOAuthConsumerKey("oYLvX1gHpQ9N5cPzAaOGoGxBh");
		  cb.setOAuthConsumerSecret("ATiTPNI3J0GjARXDcmzRCtWMZgqVhlhwRvuCuYlA9NnNCQHsvO");
		  cb.setOAuthAccessToken("29058814-ic7ONJr2yXnniIq3jfHYZgRJFIwB2DiIKcOWbgbSx");
		  cb.setOAuthAccessTokenSecret("QxYofqyjfUPaUzXRCZhFuyPNedRCD9pewwCr6ndJaHkEa");
		  	
		  TwitterFactory tf = new TwitterFactory(cb.build());
		  Twitter twitter = tf.getInstance();

		  //Posting on Twitter
		  /*
		   * String latestStatus="Hounds by The Districts~~~~~~";
		   * twitter4j.Status status = twitter.updateStatus(latestStatus);
		   * System.out.println("Successfully updated the status to [" + status.getText() + "].");
		  */

	        try {
	            twitter4j.Query query = new twitter4j.Query(searchquery);
	            QueryResult result;
	            do {
	                result = twitter.search(query);
	                java.util.List<twitter4j.Status> tweets = result.getTweets();
	                for (twitter4j.Status tweet : tweets) {
	                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
	                }
	            } while ((query = result.nextQuery()) != null);
	            System.exit(0);
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	        }
		  

	}
	public static void main(String[] args) throws TwitterException
	{
		String searchquery="lol cats";
		getTweets(searchquery);
	}
}
