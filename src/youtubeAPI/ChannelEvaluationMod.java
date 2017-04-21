package youtubeAPI;

/**
 * Created by ericmilton on 4/11/17.
 */

// https://developers.google.com/youtube/v3/docs/channels

public class ChannelEvaluationMod {

    public static boolean checkChannel(String url){
        Scraper scraper = new Scraper();
        String channelId = scraper.getChannelID(scraper.getYoutubeId(url));
        return scraper.checkIfReasonableChannel(channelId);
    }

    ChannelEvaluationMod(String url){
        checkChannel(url);
    }

    // Under here is just for testing
    ChannelEvaluationMod(){}

    public static void ChannelInfo(String url){
        Scraper scraper = new Scraper();
        String channelId = scraper.getChannelID(scraper.getYoutubeId(url));
        System.out.println(channelId);
        System.out.println();
        scraper.getChannelInfo(channelId);

        if(scraper.checkIfReasonableChannel(channelId)){
            System.out.println("Trustable");
        }else{
            System.out.println("Not Necessarily");
        }
    }

    public static void main(String[] args){
        //ChannelInfo("https://www.youtube.com/watch?v=uMChtnYO-0Y");
        ChannelInfo("https://www.youtube.com/watch?v=uVG-l8eVaB0");
        if (checkChannel("https://www.youtube.com/watch?v=uVG-l8eVaB0")){
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }
}
