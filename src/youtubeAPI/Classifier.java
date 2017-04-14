import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by ericmilton on 3/20/17.
 */
public class Classifier {

    static List<String> musicWords = null;
    static List<String> sportsWords = null;
    static List<String> cartoonWords = null;

    static List<String> gameWords = null;
    static List<String> artsandCultureWords = null;
    static List<String> newsMediaWords = null;
    static List<String> alcoholWords = null;
    static List<String> tobaccoWords = null;
    static List<String> politicalOrganizationsWords = null;

    static Scraper scraper = new Scraper();

    // uses populateList to fill each of the categorization lists
    public static void setup(){
        musicWords = populateList("music.txt");
        cartoonWords = populateList("cartoon.txt");


        gameWords = populateList("Games.txt");
        sportsWords = populateList("Sports.txt");
        newsMediaWords = populateList("NewsandMedia.txt");
        artsandCultureWords = populateList("ArtsandCulture.txt");
        alcoholWords = populateList("Alcohol.txt");
        tobaccoWords = populateList("Tobacco.txt");
        politicalOrganizationsWords = populateList("PoliticalOrganizations.txt");
    }

    // working as intended
    public static List<String> populateList(String filename){
        List<String> set = new LinkedList<>();

        try{
            FileReader fin = new FileReader(new File(filename));
            BufferedReader bin = new BufferedReader(fin);

            String line;
            while((line = bin.readLine()) != null){
                set.add(line);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return set;
    }

    public static void addToCatValList(List<CatValue> list, CatValue catValue){
        for(int i = 0; i < list.size(); i += 1){
            if(catValue.getCategoryValue() > list.get(i).getCategoryValue()){
                list.add(i,catValue);
                return;
            }
        }
        list.add(catValue);
        return;
    }

    public static Category classify(String url){
        LinkedList<CatValue> list = new LinkedList<CatValue>();

        String[] info = scraper.findTitleAndTags(getYoutubeId(url));
        if(info[0].equals("ERR")){
            System.out.println("Error detected for " + url);
            return null;
        }

        List<String> parsedTags = parseTags(info[1]);
        parsedTags.add(info[0]);

        int musicScore = doComparison(parsedTags,musicWords);
        int cartoonScore = doComparison(parsedTags,cartoonWords);
        int gamingScore = doComparison(parsedTags,gameWords);

        int sportsScore = doComparison(parsedTags,sportsWords);
        int newsMediaScore = doComparison(parsedTags,newsMediaWords);
        int artsandCultureScore = doComparison(parsedTags,artsandCultureWords);
        int alcoholScore = doComparison(parsedTags,alcoholWords);
        int tobaccoScore = doComparison(parsedTags,tobaccoWords);
        int politicalOrganizationScore = doComparison(parsedTags,politicalOrganizationsWords);

        addToCatValList(list,new CatValue("Music",musicScore));
        addToCatValList(list,new CatValue("Cartoon",cartoonScore));

        addToCatValList(list,new CatValue("Gaming",gamingScore));
        addToCatValList(list,new CatValue("Sports",sportsScore));
        addToCatValList(list,new CatValue("News and Media",newsMediaScore));
        addToCatValList(list,new CatValue("Arts and Culture",artsandCultureScore));
        addToCatValList(list,new CatValue("Alcohol",alcoholScore));
        addToCatValList(list,new CatValue("Tobacco",tobaccoScore));
        addToCatValList(list,new CatValue("Political Organizations",politicalOrganizationScore));

        System.out.println("\n" + info[0]);
/*        System.out.println("The Music score is " + entertainmentScore +
                " out of " + parsedTags.size() + " tags.");
        System.out.println("The Sports score is " + sportsScore +
                " out of " + parsedTags.size() + " tags.");
        System.out.println("The Cartoon score is " + cartoonScore +
                " out of " + parsedTags.size() + " tags.");
        System.out.println("The Gaming score is " + gamingScore +
                " out of " + parsedTags.size() + " tags.");
*/
        //System.out.println("Size of List: " + list.size());

        Category category = new Category(list.get(0).getCategoryName(), list.get(0).getCategoryValue(),
                list.get(1).getCategoryName(), list.get(1).getCategoryValue(),
                list.get(2).getCategoryName(), list.get(2).getCategoryValue(),
                parsedTags.size()); // modify

        /*System.out.println("Categories: ");
        System.out.println(category.getPrimaryCategory() + " " + category.getPrimaryScore());
        System.out.println(category.getSecondCategory() + " " + category.getSecondScore());
        System.out.println(category.getThirdCategory() + " " + category.getThirdScore());*/

        return category;
    }

    public static int doComparison(List<String> parsedTags, List<String> categoryListOfWords){
        int counter = 0;

        for (String tag:parsedTags) {
            for(String word: categoryListOfWords){
                if(tag.equals(word) || tag.contains(word)){
                    counter += 1;
                    break;
                }
            }
        }

        return counter;
    }

    // converts tag into a List of tags to be iterated for comparison
    public static List<String> parseTags(String tags){
        List<String> parsedTags = new LinkedList<String>();
        int start = 0;
        for(int i = 0; i < tags.length(); i += 1){
            if(tags.charAt(i) == '"'){
                start = i + 1;
                i += 1;
                while(tags.charAt(i) != '"' && i < tags.length()){
                    i += 1;
                }
                parsedTags.add(tags.substring(start,i));
            }
        }
        return parsedTags;
    }

    // converts the youtube url into just the youtube id
    public static String getYoutubeId(String urlName){
        if(urlName.contains("youtube") && urlName.contains("watch?v"))
            return urlName.substring(urlName.length() - 11);
        else{
            System.out.println("Improper video - must be youtube url");
            return "ERR";
        }
    }

    // constructor for external use
    Classifier(){
        setup();
    }

    public static void main(String[] args){
        setup();

        /*
        classify("https://www.youtube.com/watch?v=m1zaz3oJ3FQ").print();
        classify("https://www.youtube.com/watch?v=e-ORhEE9VVg").print();
        classify("https://www.youtube.com/watch?v=TFF5sPd09Ow").print();
        classify("https://www.youtube.com/watch?v=BpaRouocBes").print(); // cartoon

        classify("https://www.youtube.com/watch?v=2QdVEn13FFM").print(); // game
        classify("https://www.youtube.com/watch?v=S-DVtbRC72w").print(); // game

        classify("https://www.youtube.com/watch?v=56ucT_Hw4bg").print();

        classify("https://www.youtube.com/watch?v=erDaDZMO7WI").print(); // news
        classify("https://www.youtube.com/watch?v=_3KQZDMzOSg").print(); // news
*/
        classify("https://www.youtube.com/watch?v=zhplhhnvPQs").print();
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=zhplhhnvPQs")));

        classify("https://www.youtube.com/watch?v=PmT-djHyo3E").print(); // fine arts
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=PmT-djHyo3E")));

        classify("https://www.youtube.com/watch?v=PHrmoSlfLD0").print(); // fine arts
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=PHrmoSlfLD0")));

        classify("https://www.youtube.com/watch?v=zdn7NyDFTF0").print(); // museum tour of prison
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=zdn7NyDFTF0")));

        classify("https://www.youtube.com/watch?v=txXwg712zw4").print(); // halftime show
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=txXwg712zw4")));

        classify("https://www.youtube.com/watch?v=wx7unZ7enYg").print(); // bourbon show
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=wx7unZ7enYg")));

        classify("https://www.youtube.com/watch?v=iTsDZ0vWyXA").print();

        classify("https://www.youtube.com/watch?v=ZqsHLoYsklM").print(); // tobacco
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=ZqsHLoYsklM")));

        classify("https://www.youtube.com/watch?v=OfG27GGNSJM").print(); // tobacco
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=OfG27GGNSJM")));

        classify("https://www.youtube.com/watch?v=USLFDIUVSbU").print();
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=USLFDIUVSbU")));

        classify("https://www.youtube.com/watch?v=SM9s5cOKe8c").print(); // oculus
        System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=SM9s5cOKe8c")));

        // System.out.println(scraper.findTags(getYoutubeId("https://www.youtube.com/watch?v=2QdVEn13FFM")));
    }

    // deprecated
    public static List<String> parseTitle(String title){
        List<String> parsedTitle = new LinkedList<String>();
        int start = 0;
        for(int i = 0; i < title.length(); i += 1){
            if(title.charAt(i) == ' '){
                parsedTitle.add(title.substring(start,i));
                start = i+1;
            }
        }
        return parsedTitle;
    }
}
