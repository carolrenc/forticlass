package youtubeAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by ericmilton on 3/20/17.
 */
public class TextTagsTitleMod {

    static private List<String> musicWords = null;
    static private List<String> sportsWords = null;
    static private List<String> cartoonWords = null;

    static private List<String> gameWords = null;
    static private List<String> artsandCultureWords = null;
    static private List<String> newsMediaWords = null;
    static private List<String> alcoholWords = null;
    static private List<String> tobaccoWords = null;
    static private List<String> politicalOrganizationsWords = null;

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

    public static Category classify(String url, int x){
        LinkedList<CatValue> list = new LinkedList<CatValue>();

        String[] info = scraper.getTitleAndTags(scraper.getYoutubeId(url));
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
        Category category = new Category(list.get(0).getCategoryName(), list.get(0).getCategoryValue(),
                list.get(1).getCategoryName(), list.get(1).getCategoryValue(),
                list.get(2).getCategoryName(), list.get(2).getCategoryValue(),
                parsedTags.size()); // modify
        return category;
    }

    // RUN THIS
    public static String classify(String videoURL){
        LinkedList<CatValue> list = new LinkedList<CatValue>();

        String[] info = scraper.getTitleAndTags(scraper.getYoutubeId(videoURL));
        if(info[0].equals("ERR")){
            System.out.println("Error detected for " + videoURL);
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

        // System.out.println("Title:" + info[0]);

        Category category = new Category(list.get(0).getCategoryName(), list.get(0).getCategoryValue(),
                list.get(1).getCategoryName(), list.get(1).getCategoryValue(),
                list.get(2).getCategoryName(), list.get(2).getCategoryValue(),
                parsedTags.size()); // modify

        return category.getPrimaryCategory();
    }

    public String classify(List<String> keywords){
        LinkedList<CatValue> list = new LinkedList<CatValue>();

        int musicScore = doComparison(keywords,musicWords);
        int cartoonScore = doComparison(keywords,cartoonWords);
        int gamingScore = doComparison(keywords,gameWords);

        int sportsScore = doComparison(keywords,sportsWords);
        int newsMediaScore = doComparison(keywords,newsMediaWords);
        int artsandCultureScore = doComparison(keywords,artsandCultureWords);
        int alcoholScore = doComparison(keywords,alcoholWords);
        int tobaccoScore = doComparison(keywords,tobaccoWords);
        int politicalOrganizationScore = doComparison(keywords,politicalOrganizationsWords);

        addToCatValList(list,new CatValue("Music",musicScore));
        addToCatValList(list,new CatValue("Cartoon",cartoonScore));

        addToCatValList(list,new CatValue("Gaming",gamingScore));
        addToCatValList(list,new CatValue("Sports",sportsScore));
        addToCatValList(list,new CatValue("News and Media",newsMediaScore));
        addToCatValList(list,new CatValue("Arts and Culture",artsandCultureScore));
        addToCatValList(list,new CatValue("Alcohol",alcoholScore));
        addToCatValList(list,new CatValue("Tobacco",tobaccoScore));
        addToCatValList(list,new CatValue("Political Organizations",politicalOrganizationScore));

        Category category = new Category(list.get(0).getCategoryName(), list.get(0).getCategoryValue(),
                list.get(1).getCategoryName(), list.get(1).getCategoryValue(),
                list.get(2).getCategoryName(), list.get(2).getCategoryValue(),
                keywords.size()); // modify

        return category.getPrimaryCategory();
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

    // constructor for external use
    TextTagsTitleMod() {
        setup();
    }

    TextTagsTitleMod(String url){
        setup();
        System.out.println(classify(url));
    }

    public static void main(String[] args) {
        setup();
        System.out.println(classify("https://www.youtube.com/watch?v=m1zaz3oJ3FQ"));
    }
}