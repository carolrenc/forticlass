package youtubeAPI;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by ericmilton on 4/4/17.
 */
public class DescriptionInterpreter {

    public List<String> getURLsFromDescription(String description){
        List<String> urls = new LinkedList<>();

        String [] parts = description.split("\\s+"); // splits up description based on spaces; URLs dont have spaces

        // Attempt to convert each item into an URL.
        for( String item : parts ) try {
            URL url = new URL(item);
            //System.out.print("<a href=\"" + url + "\">"+ url + "</a> " );
            urls.add(item);
        } catch (MalformedURLException e) {
            // catches unreal URLs
        }

        for(int i = 0; i < urls.size(); i += 1){
            /*System.out.println(urls.get(i));
            for(int j = 0; j < urls.get(i).length(); j += 1){
                if(urls.get(i).charAt(j) == '\\'){
                    urls.remove(j);
                }
            }*/ // get rid of dashes
            System.out.println(urls.get(i));
        }

        return urls;
    }

    // not working as intended
    public void getReroute(String url){
        try {
            UserAgent userAgent = new UserAgent();         //create new userAgent (headless browser)
            userAgent.visit("http://getlinkinfo.com");     //visit getlinkinfo
            userAgent.doc.apply(url);            //apply form input (starting at first editable field)
            userAgent.doc.submit("link-form");

            Elements links = userAgent.doc.findEvery("<h3 class=r>").findEvery("<a>");  //find search result links
            for(Element link : links) System.out.println(link.getAt("href"));           //print results

        }catch(JauntException e){
            System.err.println(e);
        }
    }

    // http://getlinkinfo.com
    // https://fortiguard.com/webfilter

    public static void main(String[] args){
        DescriptionInterpreter de = new DescriptionInterpreter();
        de.getURLsFromDescription("My friends at FXX (big thanks for sponsoring this video) gave me access to the Archer, P.I. augmented reality app that you can use to solve cases in each episode of Archer: Dreamland Season 8 and in the real world. Download the Archer, P.I. App here: http:\\/\\/bit.ly\\/ArcherPI and don’t miss the premiere of Archer: Dreamland Season 8 on Wednesday 4\\/5 on FXX.\\n\\nFollow me!\\n►TWITCH - http:\\/\\/www.twitch.tv\\/imaqtpie\\n►TWITTER - https:\\/\\/www.twitter.com\\/Imaqtpielol\\n►FACEBOOK - https:\\/\\/www.facebook.com\\/imaqtpielol\\n►INSTAGRAM - https:\\/\\/www.instagram.com\\/imaqtpielol\\n\\nEdited By:\\n► TWITTER - https:\\/\\/twitter.com\\/2ndSequence\\n► CONTACT - 2econdSequence@gmail.com\\n\\nArtwork By:\\n► Twitter - https:\\/\\/twitter.com\\/lilyloo\\n► CONTACT - brocre8@gmail.com\\n\\nMUSIC:\\n►OUTRO: Ephixa & Stephen Walking - Matches (Subtact Remix) [feat. Aaron Richards] http:\\/\\/bit.ly\\/2fscGPw");

        de.getReroute("");
    }
}
