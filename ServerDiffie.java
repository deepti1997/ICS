
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
public class Server {
	public static void main(String[] args) throws IOException 
    { 
        try { 
            int port = 8088; 
            Scanner scan=new Scanner(System.in);
            
            ServerSocket serverSocket = new ServerSocket(port); 
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "..."); 
            Socket server = serverSocket.accept(); 
            System.out.println("Just connected to " + server.getRemoteSocketAddress()); 
            
            DataInputStream in = new DataInputStream(server.getInputStream()); 
        	
			int p = Integer.parseInt(in.readUTF()); // receive value of p
			System.out.println("The value of p we received is "+p);
	
			int q = Integer.parseInt(in.readUTF()); // receive value of q
			System.out.println("The value of q we received is "+q);
			
			double R = Double.parseDouble(in.readUTF()); // receive value of R
			System.out.println("The value of R we received is "+R);
			
			System.out.println("enter the value of b");
			int b=scan.nextInt();
			
			double S=  ((Math.pow(q, b)) % p);
			
			System.out.println("Value of S is "+S);
			
			 OutputStream outToclient = server.getOutputStream(); 
	         DataOutputStream out = new DataOutputStream(outToclient); 
	         
	         String S_value=Double.toString(S);
				out.writeUTF(S_value); // Sending S_value or S 
				
				
				 double S_key = ((Math.pow(R, b)) % p); // calculation of Bdash 
				  
		            System.out.println("Secret Key to perform Symmetric Encryption = "
		                               + S_key); 
		      
		            double R_key = Double.parseDouble(in.readUTF()); // receive value of Rkey
					
		            boolean result=Double.compare(S_key,R_key) == 0;

                          if(result)
		            	System.out.println("Ramesh and Suresh agree on for future communication");
		          
            server.close(); 
        } 
  
        catch (SocketTimeoutException s) { 
            System.out.println("Socket timed out!"); 
        } 
        catch (IOException e) { 
        } 
    } 
/*
 * SERVER OUTPUT
 * Waiting for client on port 8088...
Just connected to /127.0.0.1:62798
The value of p we received is 11
The value of q we received is 2
The value of R we received is 6.0
enter the value of b
4
Value of S is 5.0
Secret Key to perform Symmetric Encryption = 9.0
Ramesh and Suresh agree on for future communication
*/
 

}