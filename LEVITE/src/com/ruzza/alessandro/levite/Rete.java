/*
   
    Copyright 2018 Alessandro Ruzza
    
    This file is part of LEVITE.

    Nome-Programma is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Nome-Programma is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Nome-Programma.  If not, see <http://www.gnu.org/licenses/>.

*/

package com.ruzza.alessandro.levite;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

//3
public class Rete {
	//Questa classe non contiene la logica dell'algoritmo genetico.
	//Da implementare invece nell'Incubatore
	
	//COSTANTI CARATTERISTICHE DI CIASCUNA RETE NEURALE
	public static final int N_NEUR_INPUT = 4; 	//3.1.1
	public static final int N_NEUR_LAYER = 5;	//3.1.2	//ogni layer ha lo stesso numero di neuroni
	public static final int N_NEUR_OUTPUT = 4;	//3.1.3
	public static final int N_HLAYER = 1;		//3.1.4
	//3.1.5
	public static final int DIM_DNA = N_NEUR_INPUT*(1+1)+N_NEUR_LAYER*(N_NEUR_INPUT+1)+(N_HLAYER-1)*N_NEUR_LAYER*(N_NEUR_LAYER+1)+N_NEUR_OUTPUT*(N_NEUR_LAYER+1);
	
	private ArrayList<Layer> net;				//3.1.6
	//float;
	private Body body;							//3.1.7
	private boolean verbose = false;			//3.1.8
	//private ArrayList<Float> DNA;
	private Incubatore inc;						//3.1.9

	//3.2.1
	public Rete(ArrayList<Float> DNA, Incubatore inc)
	{
		this(DNA);
		this.inc = inc;
	}
	
	//3.2.2
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
		if(verbose)
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
			
			if(verbose)
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
		
		if(verbose)
			System.out.println("Sto generando il layer di output da " +i + " a "+f);
		net.add( new Layer( new ArrayList<Float>(DNA.subList(i, f)), N_NEUR_OUTPUT));
		
		//**** Cambiare in questa riga in base al problema da risolvere *********************
		body = new PlaySnake();
		//***********************************************************************************
	}
	
	//3.2.3
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
			body.sendOutput(out);
		}
		
		//ottiene gli input e li ritorna
		try {
			in = body.getInput();
			if(in.size()<1)
				throw new Exception();
		}catch(Exception e)
		{
			System.out.println(e);
			if(inc!=null) {
				inc.saveGenerazione();
				inc.error = true;
			}
			in.add(0, -1f);
		}
		return in;
	}
	
	//3.2.4
	public float run()
	{
		//TODO:	funzione che fa funzionare la rete neurale. 
		//		Restituisce il valore di fitness.
		ArrayList<Float> inp = this.sendOutput_getInput(null);
		ArrayList<Float> res = inp;
		
		while(inp.get(0)>=0)
		{
			for(int i=0;i<N_HLAYER+2;i++) {
				res = net.get(i).activate(inp);
				inp = res;
				if(verbose)
					System.out.println(res);
			}
			inp = this.sendOutput_getInput(res);
		}
		
		float result = -(inp.get(0));
		return result;
	}
	
	//3.2.5
	public void printData()
	{
		if(verbose)
		for(int i=0;i<net.size();i++)
		{
			System.out.println("Layer "+i);
			net.get(i).printData();
		}
	}
}
