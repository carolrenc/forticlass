package youtubeAPI;

import com.jaunt.JNode;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ericmilton on 4/3/17.
 */
public class RelatedVideos {

    public static void main(String[] args){
        String video_id = "WYNpD7F1KtQ";

        try{
            FileWriter fout = new FileWriter("RelatedVideos.txt");

            UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
            userAgent.sendGET("https://www.googleapis.com/youtube/v3/search?part=snippet&relatedToVideoId=" +
                    video_id + "&type=video&key=" +
                    "AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q"); //send request

            JNode relatedVideos = userAgent.json;

            JNode relatedVideosArr = relatedVideos.findEach("videoId");
            //System.out.println(relatedVideosArr);
            //System.out.println(userAgent.json);
            fout.write(userAgent.json.toString());
        }
        catch(JauntException e){
            System.err.println("Error in function: getRelatedVideos");
            System.err.println(e);
        }
        catch(IOException e){
            System.err.println("Error in function: getRelatedVideos");
            System.err.println(e);
        }
    }

    public static String getRelatedVideos(String video_id){
        try {
            //FileWriter fout = new FileWriter("RelatedVideos.txt");

            UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
            userAgent.sendGET("https://www.googleapis.com/youtube/v3/search?part=snippet&relatedToVideoId=" +
                    video_id + "&type=video&key=" +
                    "AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q"); //send request

            JNode relatedVideos = userAgent.json;

            JNode relatedVideosArr = relatedVideos.findEach("videoId");
            //System.out.println(relatedVideosArr);
            //System.out.println(userAgent.json);
            //fout.write(userAgent.json.toString());
            return relatedVideosArr.toString();
        }
        catch(JauntException e){
            System.err.println("Error in function: getRelatedVideos");
            System.err.println(e);
        }
        /*catch(IOException e){
            System.err.println("Error in function: getRelatedVideos");
            System.err.println(e);
        }*/
        return null;
    }
}
