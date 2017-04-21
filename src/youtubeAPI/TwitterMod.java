package youtubeAPI;

import java.io.IOException;
import java.util.*;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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

	static List<String> getTweets(String searchquery) throws TwitterException, IOException{
		setupTwitter();
        List<String> list = new LinkedList<>();

        try {
			twitter4j.Query query = new twitter4j.Query(searchquery);
			//query.setCount(10);
			QueryResult result;

            result = twitter.search(query);
            java.util.List<twitter4j.Status> tweets = result.getTweets();
            int tweetsSize=tweets.size();
            for (int i=0; i<10 && i<tweetsSize; i++) {
                twitter4j.Status tweet= tweets.get(i);
                list.add("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
		return list;
	}

	static String getCategoryFromTweets(List<String> tweets){
	    TextTagsTitleMod classifier = new TextTagsTitleMod();
        String category = classifier.classify(tweets);
        System.out.println(category + " is the category.");
        return category;
    }

    TwitterMod(String url){
        Scraper scraper = new Scraper();
        String title = scraper.getTitle(scraper.getYoutubeId(url));
        List<String> tweets = null;
        try{
            tweets = getTweets(title);
        } catch (Exception e){
            e.printStackTrace();
        }
        String category = getCategoryFromTweets(tweets);
    }

    TwitterMod(){}

	public static void main(String[] args) throws TwitterException, IOException
	{
		String searchquery="Chronicles of Narnia";
		List<String> tweets = getTweets(searchquery);
        getCategoryFromTweets(tweets);
	}
}
