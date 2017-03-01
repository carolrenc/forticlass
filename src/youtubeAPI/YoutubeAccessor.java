package youtubeAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by ericmilton on 2/16/17.
 */
public class YoutubeAccessor {

    static String API_key = "AIzaSyAwBpR_XiTmp7mmY3Bgzt0NGpwcLeS5M1Q";

    static String[] web_version = {"https://www.googleapis.com/youtube/v3/videos?key=",
            "&fields=items(contentDetails(contentRating),snippet(title,description,tags,categoryId))&part=snippet&id="};

    public static void main (String[] args){
        BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder strBuilder = new StringBuilder().append(web_version[0]).append(API_key).append(web_version[1]);
        String video_id;
        System.out.println("Input video id: ");

        try{
            video_id = "a6wJxjePPCA";// bin.readLine();
            strBuilder.append(video_id);

            System.out.println(strBuilder.toString());

            URI uri = new URI(strBuilder.toString());
            java.awt.Desktop.getDesktop().browse(uri);
        }
        catch (Exception e){

        }
    }
}
