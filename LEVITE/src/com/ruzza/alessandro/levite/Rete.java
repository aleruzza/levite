package com.ruzza.alessandro.levite;

import java.util.ArrayList;
import java.util.Scanner;

public class Rete {
	//Questa classe non contiene la logica dell'algoritmo genetico.
	//Da implementare invece nell'Incubatore
	
	//COSTANTI CARATTERISTICHE DI CIASCUNA RETE NEURALE
	public static final int N_NEUR_INPUT = 4;
	public static final int N_NEUR_LAYER = 5; //ogni layer ha lo stesso numero di neuroni
	public static final int N_NEUR_OUTPUT = 4;
	public static final int N_HLAYER = 3;
	public static final int DIM_DNA = N_NEUR_INPUT*(1+1)+N_NEUR_LAYER*(N_NEUR_INPUT+1)+(N_HLAYER-1)*N_NEUR_LAYER*(N_NEUR_LAYER+1)+N_NEUR_OUTPUT*(N_NEUR_LAYER+1);
	
	private ArrayList<Layer> net;
	//private ArrayList<Float> DNA;
	
	public Rete(ArrayList<Float> DNA)
	{
		net = new ArrayList<Layer>();
		//this.DNA = DNA;
		int i,f, step; //rappresentano dei segnalibri per orientarsi nel DNA
		
		//TODO: controllare che il metodo subList funzioni come mi aspettavo
		
		//genero il layer di input
		//il gene di questo layer ha dimensione
		//N_NEUR_INPUT*(1+1)
		step = N_NEUR_INPUT*(1+1);
		i = 0; f = i + step;
		System.out.println("Sto generando il layer di input da " +i + " a "+f);
		net.add( new Layer( new ArrayList<Float>(DNA.subList(i, f)), N_NEUR_INPUT, Layer.LAYER_INPUT));
		
		//genero gli hidden layer
		//il primo hidden layer ha un gene lungo
		// N_NEUR_LAYER*(N_NEUR_INPUT+1)
		step = N_NEUR_LAYER*(N_NEUR_INPUT+1);
		for(int j=0;j<N_HLAYER;j++)
		{
			i = f;
			f = i + step;
			
			System.out.println("Sto generando l'hidden layer "+j+" da " +i + " a "+f);
			net.add( new Layer( new ArrayList<Float>(DNA.subList(i, f)), N_NEUR_LAYER));
			//gli altri hidden layer hanno un gene lungo
			//N_NEUR_LAYER*(N_NEUR_LAYER+1)
			step = N_NEUR_LAYER*(N_NEUR_LAYER+1);
		}
		
		//genero il layer di output
		//il gene di questo layer ha dimensione
		//N_NEUR_OUTPUT*(N_NEUR_LAYER+1)
		step = N_NEUR_OUTPUT*(N_NEUR_LAYER+1);
		i = f;
		f = i + step;
		System.out.println("Sto generando il layer di output da " +i + " a "+f);
		net.add( new Layer( new ArrayList<Float>(DNA.subList(i, f)), N_NEUR_OUTPUT));
	}
	
	private ArrayList<Float> sendOutput_getInput(ArrayList<Float> out){
		/*  TODO: funzione che spedisce gli output e attende per ricevere gli input
		 *  (con uno specifico segnale in caso
		 *	di interruzione (primo valore array < 0), con relativo valore di fitness 
		 * 	(modulo del codice di interruzione)
		 */		
		ArrayList<Float> in = new ArrayList<>();
		if(out==null)
		{
			//La rete inizia
			
		}
		else
		{
			//invia gli output 
			System.out.println("Ecco gli output");
			for(Float o: out)
				System.out.print(o+"\t");
			System.out.println();
		}
		
		//ottiene gli input e li ritorna
		Scanner scan = new Scanner(System.in);
		for(int i=0;i<N_NEUR_INPUT;i++)
		{
			System.out.print("Inserisci input " + i + " (NB [0;1]) : ");
			in.add(scan.nextFloat());
		}
		return in;
	}
	

	public float run()
	{
		//TODO:	funzione che fa funzionare la rete neurale. 
		//		Restituisce il valore di fitness.
		ArrayList<Float> inp = this.sendOutput_getInput(null);
		ArrayList<Float> res = inp;
		while(inp.get(0)>0)
		{
			for(int i=0;i<N_HLAYER+2;i++) {
				//qua potrebbe esserci un problema
				res = net.get(i).activate(inp);
				inp = res;
				System.out.println(res);
			}
			inp = this.sendOutput_getInput(res);
		}
		
		float result = -(inp.get(0));
		return result;
	}
	
	public void printData()
	{
		for(int i=0;i<net.size();i++)
		{
			System.out.println("Layer "+i);
			net.get(i).printData();
		}
	}
}
