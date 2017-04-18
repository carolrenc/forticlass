package youtubeAPI;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

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
	static void getTweets(String searchquery) throws TwitterException, IOException{
		
		Properties prop=new Properties();
		InputStream input= new FileInputStream("config.properties");
		
		prop.load(input);
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(prop.getProperty("OAuthConsumerKey"));
		cb.setOAuthConsumerSecret(prop.getProperty("OAuthConsumerSecret"));
		cb.setOAuthAccessToken(prop.getProperty("OAuthAccessToken"));
		cb.setOAuthAccessTokenSecret(prop.getProperty("OAuthAccessTokenSecret"));
		  	
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
	public static void main(String[] args) throws TwitterException, IOException
	{
		String searchquery="lol cats";
		getTweets(searchquery);
	}
}
