package youtubeAPI;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ericmilton on 4/10/17.
 */
public class RelatedVideosModule {
    public static String getYoutubeId(String urlName){
        if(urlName.contains("youtube") && urlName.contains("watch?v"))
            return urlName.substring(urlName.length() - 11);
        else{
            System.out.println("Improper video - must be youtube url");
            return "ERR";
        }
    }

    public static List<String> relatedVideoIDs(String relatedVideos){
        List<String> list = new LinkedList<>();

        int firstQuote = 0;

        for(int i = 0; i < relatedVideos.length(); i += 1){
            if(relatedVideos.charAt(i) == '\"'){
                if(firstQuote != 0){
                    list.add(relatedVideos.substring(firstQuote,i));
                    firstQuote = 0;
                }else{
                    firstQuote = i + 1;
                }
            }
        }

        // System.out.println(relatedVideos);

        return list;
    }

    public static void main(String[] args){
        String videoURL = args[0];
        Scraper scraper = new Scraper();
        RelatedVideos relatedVideosFinder = new RelatedVideos();
        FortiGuardLeverage fGuardLeverage = new FortiGuardLeverage();

        String videoID = getYoutubeId(videoURL);
        String relatedVideos = relatedVideosFinder.getRelatedVideos(videoID);

        //String relatedVideos = "[\"5wcLOEAL0Pg\",\"q89jQfeIroM\",\"CzWWmX9sczM\",\"QxslzOSX8ZA\",\"Rtvf0lvCPss\"]";
        System.out.println("Here: " + relatedVideos);

        List<String> relatedVideoIDs = relatedVideoIDs(relatedVideos);

        for(String s: relatedVideoIDs){
            System.out.println(s);
            try{
                System.out.println(fGuardLeverage.fortiClassify(s,0));
            }
            catch(Exception e){
                System.out.println("Error in RelatedVideosModule");
                e.printStackTrace();
            }
        }



    }
}
