package youtubeAPI;

/**
 * Created by ericmilton on 4/11/17.
 */

// https://developers.google.com/youtube/v3/docs/channels

public class ChannelClassifier {

    public static void ChannelInfo(String url){
        Scraper scraper = new Scraper();
        String channelId = scraper.findChannelID(scraper.getYoutubeId(url));
        System.out.println(channelId);
        System.out.println();
        scraper.findChannelInfo(channelId);
    }

    public static void main(String[] args){
        ChannelInfo("https://www.youtube.com/watch?v=uMChtnYO-0Y");
    }
}
