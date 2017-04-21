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

public class TwitterMod {

	static Twitter twitter;

	static void setupTwitter() throws IOException
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(new ConfigReader().configTwitter()[0]);
		cb.setOAuthConsumerSecret(new ConfigReader().configTwitter()[1]);
		cb.setOAuthAccessToken(new ConfigReader().configTwitter()[2]);
		cb.setOAuthAccessTokenSecret(new ConfigReader().configTwitter()[3]);

		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	static void getTweets(String searchquery) throws TwitterException, IOException{

		setupTwitter();

		try {
			twitter4j.Query query = new twitter4j.Query(searchquery);
			//query.setCount(10);
			QueryResult result;
			//result.
			do {
				result = twitter.search(query);
				java.util.List<twitter4j.Status> tweets = result.getTweets();
				int tweetsSize=tweets.size();
				for (int i=0; i<1000 && i<tweetsSize; i++) {
					twitter4j.Status tweet= tweets.get(i);
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
