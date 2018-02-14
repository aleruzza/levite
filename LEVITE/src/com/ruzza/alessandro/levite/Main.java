package com.ruzza.alessandro.levite;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	
	public static void main(String[] args) {
				avviaTest();
				return;
	}

	private static void avviaTest() {
		Rete rete;
		ArrayList<Float> inp =  new ArrayList<>();
		try {
		Scanner tast = new Scanner(new FileInputStream("tdata.txt"));
		}
		catch (Exception e) {
			inp = getDataFromKeyboard();
		}
		return;
	}
	
	public static ArrayList<Float> getDataFromKeyboard()
	{
		 int i,j,k,t;
			ArrayList<Float> inp =  new ArrayList<>();
			Scanner tast = new Scanner(System.in);
			
			//faccio inserire i dati dei neuroni di input
			System.out.println("Inserire dati neuroni di input (ognuno ha 2 valori)");
			for(i=0;i<Rete.N_NEUR_INPUT;i++)
			{
				System.out.println("Neurone " + i +": ");
				inp.add(tast.nextFloat());
				inp.add(tast.nextFloat());
			}
			
			//faccio inserire i dati degli hidden layer
			for(i=0;i<Rete.N_HLAYER;i++) {
				System.out.println("Inserire dati neuroni hidden layer " + i + ":");
				for(j=0;j<Rete.N_NEUR_LAYER;j++)
				{
					if(i==0)
						t=Rete.N_NEUR_INPUT+1;
					else
						t=Rete.N_NEUR_LAYER+1;
					
					System.out.println("Neurone " + j +" ("+t+" valori): ");
					for(k=0;k<t;k++)
						inp.add(tast.nextFloat());
				}
			}
			
			//faccio inserire i dati dei neuroni di output
			System.out.println("Inserire dati neuroni di input (ognuno ha " + (Rete.N_NEUR_LAYER+1) + " valori)");
			for(i=0;i<Rete.N_NEUR_OUTPUT;i++)
			{
				System.out.println("Neurone " + i +": ");
				inp.add(tast.nextFloat());
				inp.add(tast.nextFloat());
			}
			
			return inp;
	}
}
