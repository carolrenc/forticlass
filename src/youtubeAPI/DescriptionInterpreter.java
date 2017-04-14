import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
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
        System.out.println("parts.length is " + parts.length);

        // Attempt to convert each item into an URL.
        for( String item : parts ) try {
            if(item.contains("http")) {
                URL url = new URL(item);
                //System.out.print("<a href=\"" + url + "\">"+ url + "</a> " );
                urls.add(item);
                System.out.println("Item added: " + item + "\n");
            }else{
                URL url = new URL(item);
                //System.out.print("<a href=\"" + url + "\">"+ url + "</a> " );
                urls.add(item);
                System.out.println("Item added: " + item + "\n");
            }
        } catch (MalformedURLException e) {
            //System.err.println("Error in function: getURLsFromDescription");
            //e.printStackTrace();

            //these are to remain ignored, as they are essentially just making bad URLs to check
        }
        try {
            for (int i = 0; i < urls.size(); i += 1) {
                //System.out.println(urls.get(i));
                String temp = urls.get(i);
                urls.remove(i);
                if (temp.contains("\\n")) {
                    temp = temp.substring(0, temp.indexOf("\\n"));//temp.split("\\n", 2)[0];
                }

                urls.add(i, temp.replace("\\", "")); // get rid of dashes

                String expandedUrl;
                if(urls.get(i).contains("bit.ly")) {
                    expandedUrl = expandUrl(urls.get(i));
                    // System.out.println("Expanded = " + expandedUrl);
                    urls.remove(i);
                    urls.add(i,expandedUrl);
                }
                System.out.println(urls.get(i));
            }
        }
        catch(Exception e){
            System.err.println("Error in function: getURLsFromDescription");
            e.printStackTrace();
        }

        if(urls.size() == 0){
            System.out.println("No URLs in description of video");
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
            System.err.println("Error in function: getReroute");
            System.err.println(e);
        }
    }

    // actually visits site
    public static String expandUrl(String shortenedUrl) throws IOException {
        URL url = new URL(shortenedUrl);

        System.out.println("Shortened URL: " + shortenedUrl);

        // open connection
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
        httpURLConnection.setInstanceFollowRedirects(false);

        // extract location header containing the actual destination URL
        String expandedURL = httpURLConnection.getHeaderField("Location");
        httpURLConnection.disconnect();

        return expandedURL;
    }

    // https://fortiguard.com/webfilter

    public void getRelatedWebsites(String url){
        Scraper scraper = new Scraper();

        String youtubeID = Classifier.getYoutubeId(url);
        List<String> urlsFromDesc = getURLsFromDescription(scraper.findDescription(youtubeID));

        for(String subURL : urlsFromDesc){
            try{
                System.out.println(FortiGuardLeverage.fortiClassify(subURL));
            } catch (Exception e){
                System.err.println("Error in function: getRelatedWebsites");
                e.printStackTrace();
            }
        }

        System.out.println("\n\nDone\n");
    }

    public static void main(String[] args){
        /*try{
            URL url = new URL("twitch.tv\\/firebat"); // I thought would work but failed
        }catch(Exception e){
            System.out.println("Failed");
        }*/

        DescriptionInterpreter de = new DescriptionInterpreter();
        de.getRelatedWebsites("https://www.youtube.com/watch?v=pMqrMC1VZhA");
        System.out.println("\n");
        de.getRelatedWebsites("https://www.youtube.com/watch?v=itSTzV29bS0");
        //System.out.println("\n\n\n\n\n");
        //de.getRelatedWebsites(args[0]);
        /*DescriptionInterpreter de = new DescriptionInterpreter();
        Scraper scraper = new Scraper();
        String description = scraper.findDescription(Classifier.getYoutubeId("https://www.youtube.com/watch?v=m1zaz3oJ3FQ"));
        de.getURLsFromDescription(description);

        System.out.println("\n\nNext video");
        de.getURLsFromDescription("My friends at FXX (big thanks for sponsoring this video) gave me access to the Archer, " +
                "P.I. augmented reality app that you can use to solve cases in each episode of Archer: Dreamland Season 8 " +
                "and in the real world. Download the Archer, P.I. App here: http:\\/\\/bit.ly\\/ArcherPI and" +
                " don’t miss the premiere of Archer: Dreamland Season 8 on Wednesday 4\\/5 on FXX.\\n\\nFollow " +
                "me!\\n►TWITCH - http:\\/\\/www.twitch.tv\\/imaqtpie\\n►TWITTER - " +
                "https:\\/\\/www.twitter.com\\/Imaqtpielol\\n►FACEBOOK - https:\\/\\/www.f" +
                "acebook.com\\/imaqtpielol\\n►INSTAGRAM - https:\\/\\/www.instagram.com\\/imaq" +
                "tpielol\\n\\nEdited By:\\n► TWITTER - https:\\/\\/twitter.com\\/2ndSequence\\n► CON" +
                "TACT - 2econdSequence@gmail.com\\n\\nArtwork By:\\n► Twitter - https:\\/\\/twitter" +
                ".com\\/lilyloo\\n► CONTACT - brocre8@gmail.com\\n\\nMUSIC:\\n►OUTRO: Ephixa & Stephen " +
                "Walking - Matches (Subtact Remix) [feat. Aaron Richards] http:\\/\\/bit.ly\\/2fscGPw");

        System.out.println("\n\nNext video");
        description = scraper.findDescription(Classifier.getYoutubeId("https://www.youtube.com/watch?v=pMqrMC1VZhA"));
        de.getURLsFromDescription(description);

        System.out.println("\n");*/
    }
}
