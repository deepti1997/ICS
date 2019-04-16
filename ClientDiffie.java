

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
	public static void main(String[] args) 
    { 
        try { 
            
            String serverName = "localhost"; 
            int port = 8088; 
            System.out.println("Connecting to " + serverName 
                               + " on port " + port); 
            Socket client = new Socket(serverName, port); 
            System.out.println("Just connected to "
                               + client.getRemoteSocketAddress()); 
   
            Scanner sc= new Scanner(System.in);

			int p,q,a;
		
			System.out.println("Enter the value of  P");
			p=sc.nextInt();
			
			System.out.println("Enter the value Q");
			q=sc.nextInt();		
            
			System.out.println("Enter the value a");
			a=sc.nextInt();		
			
			
			double R= ((Math.pow(q, a)) % p);
			System.out.println("Value of R is "+R);
			
			// Sends the data to client 
						OutputStream outToServer = client.getOutputStream(); 
						DataOutputStream out = new DataOutputStream(outToServer); 

						String p_value = Integer.toString(p); 
						out.writeUTF(p_value); // Sending p 
						
						String q_value=Integer.toString(q);
						out.writeUTF(q_value); // Sending q
						
						String R_value=Double.toString(R);
						out.writeUTF(R_value); // Sending p 
						
						DataInputStream in = new DataInputStream(client.getInputStream()); 
						  
			            double S= Double.parseDouble(in.readUTF()); 
			            System.out.println("Receiving Value of S at Client is "+S);
			            
			           double R_key =  ((Math.pow(S, a)) % p); // calculation of Rkey
						
			           System.out.println("Secret Key to perform Symmetric Encryption = "
                               + R_key); 
						String R_key_value=Double.toString(R_key);
						out.writeUTF(R_key_value); // Sending RKey
            client.close();
          } 
               
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 

}
/* Client OUTPUT 
 * Connecting to localhost on port 8088
Just connected to localhost/127.0.0.1:8088
Enter the value of  P

11
Enter the value Q
2
Enter the value a
9
Value of R is 6.0
Receiving Value of S at Client is 5.0
Secret Key to perform Symmetric Encryption = 9.0
*/