package youtubeAPI;
import java.util.*;

public class YoutubeMapping {
	 // Create a hash map
    static HashMap<Integer, String> hm = new HashMap<Integer, String>();
    
    //String category;
    // Put elements to the map
    YoutubeMapping()
    {
	    hm.put(2, "Cars & Vehicles");
	    hm.put(10, "Music");
	    hm.put(15, "Pets & Animals");
		hm.put(17, "Sports");
		hm.put(19, "Travel & Events");
		hm.put(20, "Gaming");
		hm.put(22, "People & Blogs");
		hm.put(23, "Comedy");
		hm.put(24, "Entertainment");
		hm.put(25, "News & Politics");
		hm.put(26, "How-to & Style");
		hm.put(27, "Education");
		hm.put(28, "Science & Technology");
		hm.put(29, "Non-profits & Activism");
    }

   public static HashMap<Integer, String> getHm() {
		return hm;
	}


@SuppressWarnings("rawtypes")
public static void main(String args[]) {
   
     
      // Get a set of the entries
      Set<?> set = hm.entrySet();
      
      // Get an iterator
      Iterator<?> i = set.iterator();
      
      // Display elements
      while(i.hasNext()) {
         Map.Entry me = (Map.Entry)i.next();
         System.out.print(me.getKey() + ": ");
         System.out.println(me.getValue());
      }
      System.out.println();
      
   }
}