import java.util.*;
public class SDes 
{
	public int[] K1, K2;

	public static final int P10[] = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
	public static final int P10max = 10;

	public static final int P8[] = { 6, 3, 7, 4, 8, 5, 10, 9};
	public static final int P8max = 8;

	public static final int P4[] = { 2, 4, 3, 1};
	public static final int P4max = 4;

	public static final int IP[] = { 2, 6, 3, 1, 4, 8, 5, 7};
	public static final int IPmax = 8;

	public static final int IPI[] = { 4, 1, 3, 5, 7, 2, 8, 6};
	public static final int IPImax = 8;

	public static final int EP[] = { 4, 1, 2, 3, 2, 3, 4, 1};
	public static final int EPmax = 8;

	public static final int S0[][] = {{ 1, 0, 3, 2},{ 3, 2, 1, 0},{ 0, 2, 1, 3},{ 3, 1, 3, 2}};

	public static final int S1[][] = {{ 0, 1, 2, 3},{ 2, 0, 1, 3},{ 3, 0, 1, 0},{ 2, 1, 0, 3}
			  };
	
	SDes()
	{
		K1=new int[8];
		K2=new int[8];
	}
	
	//==============for P10 and P8=============================
	public static int[] permute(int x[], int p[], int psize)
	{
		int[] val = new int[psize];
		for(int i=0;i<p.length;i++)
		{
			val[i]=x[p[i]-1];
		}
		return val;
	}
	
	//LEFT SHIFT of 10 bit key============================
	public int[] Leftshift(int X[])
	{
		int val[] = new int[10];
		int half1[] = new int[5];
		int half2[] = new int[5];
		for(int i=0;i<X.length;i++)
		{
			if(i<5)
			{
				half1[i]=X[i];
			}
			else
			{
				half2[i-5]=X[i];
			}
		}
		int temp1=half1[0];
		int temp2=half2[0];
		for(int i=0;i<4;i++)
		{
			half1[i]=half1[i+1];
			half2[i]=half2[i+1];
		}
		half1[4]=temp1;
		half2[4]=temp2;
		for(int i=0;i<10;i++)
		{
			if(i<5)
			{
				val[i]=half1[i];
			}
			else
			{
				val[i]=half2[i-5];
			}
		}
		return val;
	}
	
	public void keygeneration(int k[])
	{
		int p10_1[]=permute(k,P10,P10max);
		p10_1=Leftshift(p10_1);
		K1=permute(p10_1,P8,P8max);
		System.out.println("Key 1: ");
		display(K1);
		p10_1=Leftshift(p10_1);
		p10_1=Leftshift(p10_1);
		K2=permute(p10_1,P8,P8max);
		System.out.println("\nKey 2: ");
		display(K2);
	}
	
	void display(int X[])
	{
		//System.out.println(X.length);
		for(int i=0;i<X.length;i++)
		{
			System.out.print(X[i]+" ");
		}
	}

	public int[] getbinary(int num)
	{
		int val[] = new int[2];
		switch(num)
		{
		case 0: val[0]=0;val[1]=0;
				break;
		case 1: val[0]=0;val[1]=1;
				break;
		case 2: val[0]=1;val[1]=0;
				break;
		case 3: val[0]=1;val[1]=1;
				break;
		}
		return val;
	}
	
	public int getdec(int num1,int num2)
	{
		int val;
		if(num1==0 && num2==0)
			val=0;
		else if(num1==0 && num2==1)
			val=1;
		else if(num1==1 && num2==0)
			val=2;
		else
			val=3;
		return val;
	}
	
	public int[] xorfunc(int x[], int y[])
	{
		int val[]=new int[x.length];
		for(int i=0;i<x.length;i++)
		{
			if(x[i]==y[i])
				val[i]=0;
			else
				val[i]=1;
		}
		return val;
	}
	
	public int[] SBoxes(int x[])
	{
		int val[]=new int[4];
		int rows0=getdec(x[0],x[3]);
		int cols0=getdec(x[1],x[2]);
		int rows1=getdec(x[4],x[7]);
		int cols1=getdec(x[5],x[6]);
		int arrs0[] = getbinary(S0[rows0][cols0]);
		int arrs1[] = getbinary(S1[rows1][cols1]);
		for(int i=0;i<4;i++)
		{
			if(i<2)
				val[i]=arrs0[i];
			else
				val[i]=arrs1[i-2];
		}
		return val;
	}
	
	public int[] swap(int a[])
	{
		int val[]=new int[8];
		for(int i=0;i<4;i++)
		{
			val[i]=a[i+4];
			val[i+4]=a[i];
		}
		return val;		
	}
	
	public int[] fk(int IP1[],int keyval[])
	{
		int val[]=new int[8];
		//====get leftmost and rightmost 4 bits======
		int rightbits[] = new int[4];
		int leftbits[] = new int [4];
		for(int i=0;i<8;i++)
		{
			if(i<4)
				leftbits[i]=IP1[i];
			else
				rightbits[i-4]=IP1[i];
		}
		//====find EP for rightmost 4 bits===========
		int EP1[]=permute(rightbits,EP,EPmax);
		System.out.println("\nIP1 to EP1: ");
		display(EP1);
		//======XOR the obtained EP with the key mentioned=====
		int EPXORKey[]=xorfunc(EP1,keyval);
		System.out.println("\nEP1 XOR given Key: ");
		display(EPXORKey);
		//=======Find the S0 and S1 values wala array from this key=======
		int Sboxans[]=SBoxes(EPXORKey);
		System.out.println("\nFinding the Sboxes array: ");
		display(Sboxans);
		//=======Applying P4 permutation on the S boxes array==========
		int P41[]=permute(Sboxans,P4,P4max);
		System.out.println("\nAfter P4 permutation: ");
		display(P41);
		//======Xoring answer with the leftpart of IP1========
		int P4XOR[]=xorfunc(P41,leftbits);
		System.out.println("\nXORing with left bits: ");
		display(P4XOR);
		//=======Swapping the two halves=========
		//val=swap(P4XOR,rightbits);
		for(int i=0;i<8;i++)
		{
			if(i<4)
				val[i]=P4XOR[i];
			else
				val[i]=rightbits[i-4];
		}
		System.out.println("\nFinal after applying fk: ");
		display(val);
		return val;
	}
	
	public void encryption(int pt[])
	{
		//=====generate IP==========
		int IP1[]=permute(pt,IP,IPmax);
		System.out.println("Plain text to IP: ");
		display(IP1);
		//=====Apply fk1============
		System.out.println("\n============== Fk1 ==================");
		int Postfk1[]=fk(IP1,K1);
		//==========================
		int swapval[]=swap(Postfk1);
		//=======Apply fk2==========
		System.out.println("\n============== Fk2 ==================");
		int Postfk2[]=fk(swapval,K2);
		//========Applying IP inverse================
		int Ciphertext[]=permute(Postfk2,IPI,IPImax);
		System.out.println("\n===============================\nRequired Ciphertext:  ");
		display(Ciphertext);
	}
	
	public void decryption(int pt[])
	{
		//=====generate IP==========
		int IP1[]=permute(pt,IP,IPmax);
		System.out.println("Plain text to IP: ");
		display(IP1);
		//=====Apply fk1============
		System.out.println("\n============== Fk1 ==================");
		int Postfk1[]=fk(IP1,K2);
		//==========================
		int swapval[]=swap(Postfk1);
		//=======Apply fk2==========
		System.out.println("\n============== Fk2 ==================");
		int Postfk2[]=fk(swapval,K1);
		//========Applying IP inverse================
		int Ciphertext[]=permute(Postfk2,IPI,IPImax);
		System.out.println("\n===============================\nRequired Ciphertext:  ");
		display(Ciphertext);
	}
	
	public static void main(String[] args) 
	{
		SDes s1 = new SDes();
		Scanner sc = new Scanner(System.in);
		int key[]=new int[10];
		System.out.println("Key: ");
		s1.display(key);
		System.out.println("\nEnter the key: ");
		for(int i=0;i<10;i++)
		{
			key[i]=sc.nextInt();
		}
		s1.keygeneration(key);
		int plaintext[]=new int[8];
		System.out.println("\nEnter the plaintext: ");
		for(int i=0;i<8;i++)
		{
			plaintext[i]=sc.nextInt();
		}
		System.out.println("\n****************Encryption********************\n");
		s1.encryption(plaintext);
		System.out.println("\n**********************************************\n");
		int Cipher[]=new int[8];
		System.out.println("\nEnter the Cipher text: ");
		for(int i=0;i<8;i++)
		{
			Cipher[i]=sc.nextInt();
		}
		System.out.println("\n****************Decryption********************\n");
		s1.decryption(Cipher);
	}

}

/**************************OUTPUT*********************************
Key: 
0 0 0 0 0 0 0 0 0 0 
Enter the key: 
1 0 1 0 0 0 0 0 1 0
Key 1: 
1 0 1 0 0 1 0 0 
Key 2: 
0 1 0 0 0 0 1 1 
Enter the plaintext: 
0 1 1 1 0 0 1 0

****************Encryption********************

Plain text to IP: 
1 0 1 0 1 0 0 1 
============== Fk1 ==================

IP1 to EP1: 
1 1 0 0 0 0 1 1 
EP1 XOR given Key: 
0 1 1 0 0 1 1 1 
Finding the Sboxes array: 
1 0 1 1 
After P4 permutation: 
0 1 1 1 
XORing with left bits: 
1 1 0 1 
Final after applying fk: 
1 1 0 1 1 0 0 1 
============== Fk2 ==================

IP1 to EP1: 
1 1 1 0 1 0 1 1 
EP1 XOR given Key: 
1 0 1 0 1 0 0 0 
Finding the Sboxes array: 
1 0 1 1 
After P4 permutation: 
0 1 1 1 
XORing with left bits: 
1 1 1 0 
Final after applying fk: 
1 1 1 0 1 1 0 1 
===============================
Required Ciphertext:  
0 1 1 1 0 1 1 1 
**********************************************


Enter the Cipher text: 
0 1 1 1 0 1 1 1

****************Decryption********************

Plain text to IP: 
1 1 1 0 1 1 0 1 
============== Fk1 ==================

IP1 to EP1: 
1 1 1 0 1 0 1 1 
EP1 XOR given Key: 
1 0 1 0 1 0 0 0 
Finding the Sboxes array: 
1 0 1 1 
After P4 permutation: 
0 1 1 1 
XORing with left bits: 
1 0 0 1 
Final after applying fk: 
1 0 0 1 1 1 0 1 
============== Fk2 ==================

IP1 to EP1: 
1 1 0 0 0 0 1 1 
EP1 XOR given Key: 
0 1 1 0 0 1 1 1 
Finding the Sboxes array: 
1 0 1 1 
After P4 permutation: 
0 1 1 1 
XORing with left bits: 
1 0 1 0 
Final after applying fk: 
1 0 1 0 1 0 0 1 
===============================
Required Ciphertext:  
0 1 1 1 0 0 1 0 

*************************************************************************/
