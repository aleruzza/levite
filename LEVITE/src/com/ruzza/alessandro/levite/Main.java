package com.ruzza.alessandro.levite;

import java.io.FileInputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	
	public static void main(String[] args) {
				if(args.length==0)
						showHelp();
				else
				{
					switch(args[0])
					{
						case "-tc":
							avviaTestCreazione();
							break;
						case "-tr":
							avviaTestRete();
							break;
						case "-ti":
							avviaTestIncubatore();
							break;
						default:
							showHelp();
					}
				}
				
				return;
	}
	
	private static ArrayList<Float> getDataFromFile() throws IOException
	{
		//il file deve finire con un numero negativo
		ArrayList<Float> inp =  new ArrayList<>();
		InputStreamReader tast = new InputStreamReader(new FileInputStream("/home/arkx/TScrivania/data.txt"));
		BufferedReader buff = new BufferedReader(tast);
		float n = Float.parseFloat(buff.readLine());
		while(n>0)
		{
			inp.add(n);
			n = Float.parseFloat(buff.readLine());
		}
		return inp;
	}
	
	private static void avviaTestCreazione() {
		Rete rete;
		
		//ottengo il DNA
		ArrayList<Float> inp =  new ArrayList<>();
		try {
			inp = getDataFromFile();
		}
		catch (Exception e) {
			System.out.println(e);
			inp = getDataFromKeyboard();
		}
		
		//do vita alla rete
		rete = new Rete(inp);
		
		System.out.println("La rete è nata");
		
		//visualizzo i dati della rete
		rete.printData();
		
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
	
	private static void showHelp()
	{
		String help = "Rete neurale LEVITE, usare i seguenti parametri \n\n"
				    + "\t-tc\tavvia sequenza di test per la creazione della rete, recupera i dati dal file data.txt o in sua assenza li richiede da tastiera";
		System.out.println(help);
	}
	
	private static void avviaTestRete()
	{
		Rete rete;
		try {
			rete = new Rete(getDataFromFile());
		}
		catch(Exception e)
		{
			return;
		}
		System.out.println("La rete è nata");
		rete.run();
	}
	
	private static void avviaTestIncubatore() {
		Incubatore incubatore = new Incubatore();
		incubatore.evolvi();
	}
}
