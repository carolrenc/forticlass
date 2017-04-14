package youtubeAPI;
import java.util.List;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.Node;
import com.jaunt.UserAgent;
import com.jaunt.component.Table;

public class countryIdentifier {
	
	public static void getCountry(String url){
		
		String searchUrl="https://check-host.net/ip-info?host="+url;
		
        try {
            UserAgent userAgent = new UserAgent();        
            userAgent.visit(searchUrl);

            Table table = userAgent.doc.getTable("<table class=\"hostinfo result\" cellspacing=\"0\" cellpadding=\"0\">");

            Element element =table.getRowRightOf("Country");
            //System.out.println(element.outerHTML());
            //System.out.println(element.findFirst("<strong>").innerHTML());
            String country=element.findFirst("<strong>").innerHTML();
            System.out.println(country);

        }catch(JauntException e){
            System.err.println(e);
        }
    }

	static String cleanurl(String url)
	{
		int slashNum=0;
		int strlen=url.length();
		char arr[]= url.toCharArray();
		for(int i=0; i<strlen; i++){
		    if(arr[i]=='/'){
		    	slashNum ++;    
		    }
		}

		int newlen=strlen+(slashNum*3);
		char newurl[];
		newurl= new char[newlen];
		int j=0;
		for(int i=0; i<strlen; i++)
		{
			if(arr[i]=='/')
			{
				newurl[j]='%';
				j++;
				newurl[j]='2';
				j++;
				newurl[j]='F';
			}
			else
			{
				newurl[j]=arr[i];
			}
			j++;
		}

		return url;
	}

    public static void main(String[] args){
    	//String url= "www.codecademy.com/ru/tracks/twitter";
    	String url= "https://www.canada.ca/en.html";
    	String cleanurl=cleanurl(url);
    	//remember to replace all slashes with %2F
    	getCountry(cleanurl);
    		
    }

}