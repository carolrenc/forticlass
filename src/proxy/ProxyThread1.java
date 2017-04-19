package proxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ProxyThread1 extends Thread {
	
	private Socket clientSocket = null;
    private static final int BUFFER_SIZE = 32768;
    
    public ProxyThread1(Socket clientSocket) {
        super("ProxyThread");
        this.clientSocket = clientSocket;
    }
    
    public void run() {
    	//get request from user
        try {
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            StringBuilder request = new StringBuilder("");
            String line;
            while (true) {
            	line = in.readLine();
            	if (line == null) {
            		break;
            	} else {
            		request.append(line);
            	}
            }
            System.out.println(request.toString());
        } catch (Exception e) {
        	// do nothing for now
        }
    	//user sign in
    	
    	//permision granted / denied
    }

}
