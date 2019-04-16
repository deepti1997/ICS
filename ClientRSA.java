
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	public static void main(String[] args) throws IOException 
	{
		try { 
			String pstr, gstr, Astr; 
			String serverName = "localhost"; 
			int port = 8088; 
			
			// Established the connection 
			System.out.println("Connecting to " + serverName + " on port " + port); 
			Socket client = new Socket(serverName, port); 
			System.out.println("Just connected to "	+ client.getRemoteSocketAddress()); 
			
			Scanner sc= new Scanner(System.in);

			int p,q,n,f,d=0,e,i;
			System.out.println("Enter 1st prime number p");
			p=sc.nextInt();
			System.out.println("Enter 2nd prime number q");
			q=sc.nextInt();			
			n=p*q;
			f=(p-1)*(q-1);
			
			for(e=2;e<f;e++)
			{
				if(gcd(e,f)==1)
				{
					break;
				}
			}
			System.out.println("\nPublic key: { e,n} =  {"+e+", n= "+n+"}");
			
			for(i=0;i<=9;i++)
			{
				int x=1+(i*f);
				if(x%e==0)      //d is for private key exponent 
				{
					d=x/e;
					break;
				}
			}
			
			System.out.println(" \n Private key {d,n}  = {"+d +"," +n+"}");		
			
			// Sends the data to client 
			OutputStream outToServer = client.getOutputStream(); 
			DataOutputStream out = new DataOutputStream(outToServer); 

			String estr = Integer.toString(e); 
			out.writeUTF(estr); // Sending e 

			String nstr = Integer.toString(n); 
			out.writeUTF(nstr); // Sending n 
			
			// Accepts the data from client 
			DataInputStream in = new DataInputStream(client.getInputStream()); 
			long encrypted = (long) Double.parseDouble(in.readUTF()); // to accept encrypted
			
			System.out.println("\nEncrypted message received from server ( CipherText ): "+encrypted);
			
			//converting int value of n to BigInteger
			BigInteger N = BigInteger.valueOf(n);
			//converting float value of c to BigInteger
			BigInteger C = BigDecimal.valueOf(encrypted).toBigInteger();
			
			//Decrypting message
			BigInteger decrypted = (C.pow(d)).mod(N);
			System.out.println("Derypted message is (Plaintext) : "+decrypted);
			

		}	
	
		catch (Exception e) { 
		e.printStackTrace(); 
		}
		
			}
	static int gcd(int e, int f)
	{
		if(e==0)
			return f;
		else 
			return gcd(f%e,e);
	}


}