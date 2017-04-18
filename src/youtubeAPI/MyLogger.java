
package youtubeAPI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class MyLogger {
	
	static Logger LOGGER = Logger.getLogger(MyLogger.class.getName());
	
	public static void setupLogger() throws SecurityException, IOException{
		
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss") ;
		FileHandler filehandler= new FileHandler("log_"+ dateFormat.format(date) + ".txt");
		SimpleFormatter formattertxt;
		
		LOGGER.setLevel(Level.INFO);
		LOGGER.setUseParentHandlers(false);
		formattertxt= new SimpleFormatter();
		filehandler.setFormatter(formattertxt);
		LOGGER.addHandler(filehandler);
		
	}
	
	public void doSomeThingAndLog() {
        // ... more code

        // now we demo the logging

        // set the LogLevel to Severe, only severe Messages will be written
        LOGGER.setLevel(Level.SEVERE);
        LOGGER.severe("Info Log");
        LOGGER.warning("Info Log");
        LOGGER.info("Info Log");
        LOGGER.finest("Really not important");

        // set the LogLevel to Info, severe, warning and info will be written
        // finest is still not written
        LOGGER.setLevel(Level.INFO);
        LOGGER.severe("Info Log");
        LOGGER.warning("Info Log");
        LOGGER.info("Info Log");
        LOGGER.finest("Really not important");
        
        Exception e1 = new Exception();
        Exception e2 = new Exception(e1);
        LOGGER.log(Level.SEVERE, "an exception was thrown", e2);
}
	
	
	public static void main(String[] args) throws SecurityException, IOException{
		MyLogger tester= new MyLogger();
		
		MyLogger.setupLogger();
	
		tester.doSomeThingAndLog();
	
	}
}
