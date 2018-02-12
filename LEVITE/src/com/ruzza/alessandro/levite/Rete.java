package com.ruzza.alessandro.levite;

import java.util.ArrayList;

public class Rete {
	//Questa classe non contiene la logica dell'algoritmo genetico.
	//Da implementare invece nell'Incubatore
	
	//COSTANTI CARATTERISTICHE DI CIASCUNA RETE NEURALE
	private final int N_NEUR_INPUT = 4;
	private final int N_NEUR_LAYER = 5; //ogni layer ha lo stesso numero di neuroni
	private final int N_NEUR_OUTPUT = 4;
	private final int N_HLAYER = 3;
	
	private ArrayList<Layer> net;
	private ArrayList<Float> DNA;
	
	public Rete(ArrayList<Float> DNA)
	{
		this.DNA = DNA;
		int i,f, step; //rappresentano dei segnalibri per orientarsi nel DNA
		
		//TODO: controllare che il metodo subList funzioni come mi aspettavo
		
		//genero il layer di input
		//il gene di questo layer ha dimensione
		//N_NEUR_INPUT*(1+1)
		step = N_NEUR_INPUT*(1+1);
		i = 0; f = i + step;
		net.add( new Layer( ((ArrayList<Float>) DNA.subList(i, f)), N_NEUR_INPUT));
		
		//genero gli hidden layer
		//il primo hidden layer ha un gene lungo
		// N_NEUR_LAYER*(N_NEUR_INPUT+1)
		step = N_NEUR_LAYER*(N_NEUR_INPUT+1);
		for(int j=0;j<N_HLAYER;j++)
		{
			i = f+1;
			f = i + step;
			net.add( new Layer( ((ArrayList<Float>) DNA.subList(i, f)), N_NEUR_LAYER));
			//gli altri hidden layer hanno un gene lungo
			//N_NEUR_LAYER*(N_NEUR_LAYER+1)
			step = N_NEUR_LAYER*(N_NEUR_LAYER+1);
		}
		
		//genero il layer di output
		//il gene di questo layer ha dimensione
		//N_NEUR_OUTPUT*(N_NEUR_LAYER+1)
		step = N_NEUR_OUTPUT*(N_NEUR_LAYER+1);
		i = f+1;
		f = i + step;
		net.add( new Layer( ((ArrayList<Float>) DNA.subList(i, f)), N_NEUR_OUTPUT));
	}
	
	private ArrayList<Float> getInput(){
		//TODO: funzione che riceva gli input (con uno specifico segnale in caso
		//		di interruzione, con relativo valore di fitness)
		
		return null;
	}
	
	private void sendOutput(ArrayList<Float> out)
	{
		//TODO: funzione che spedisce gli output
	}
	
	public float run()
	{
		//TODO:	funzione che fa "giocare" la rete neurale. 
		//		Restituisce il valore di fitness.
		return 0;
	}
	
}
