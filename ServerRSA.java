


import java.net.*; 
import java.io.*; 
import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException 
	{
		try { 
			int port = 8088; 
			Scanner s= new Scanner(System.in);
						
			// Established the Connection 
			ServerSocket serverSocket = new ServerSocket(port); 
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "..."); 
			Socket server = serverSocket.accept(); 
			System.out.println("Just connected to " + server.getRemoteSocketAddress()); 
			
			// Accepts the data from client 
			DataInputStream in = new DataInputStream(server.getInputStream()); 
	
			int e = Integer.parseInt(in.readUTF()); // to accept encryption key 
			int n = Integer.parseInt(in.readUTF()); // to accept n 
			System.out.println("\nPublic key from client: \ne = "+e+", n = "+n); 
			
			//Encrypting
			System.out.println("Enter message to be encrypted: ");
			int msg= s.nextInt();
			double c=(Math.pow(msg,e))%n;
			System.out.println("Encrypted message is : "+c);
			String encrypted = Double.toString(c);
			
			// Sends the data to client 
			OutputStream outToServer = server.getOutputStream(); 
			DataOutputStream out = new DataOutputStream(outToServer); 
			out.writeUTF(encrypted); // Sending encrypted message 
			
			
		}
		catch (SocketTimeoutException s) { 
			System.out.println("Socket timed out!"); 
		} 
		catch (IOException e) { 
		} 
	}
}
/*
 Sample examples from tech-max book page no : 3-13 ICS book 
 
OUTPUT 
Connecting to localhost on port 8088
Just connected to localhost/127.0.0.1:8088
Enter 1st prime number p
7
Enter 2nd prime number q
17

Public key: { e,n} =  {5, n= 119}
 
 Private key {d,n}  = {77,119}

Encrypted message received from server ( CipherText ): 40.0
Derypted message is (Plaintext) : 10
*/