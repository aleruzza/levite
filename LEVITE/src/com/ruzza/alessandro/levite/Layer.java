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

import java.util.ArrayList;

//2
public class Layer {

	private ArrayList<Neuron> neurons;					//2.1.1
	private boolean inp = false;						//2.1.2
	public static final boolean LAYER_INPUT = true;		//2.1.3
	private boolean verbose = false;					//2.1.4
	
	//2.2.1
	public Layer(ArrayList<Float> gene, int nn, boolean inp)
	{
		this(gene, nn);
		this.inp = inp;
	}
	
	//2.2.2
	public Layer(ArrayList<Float> gene, int nn)
	{
		/*
		System.out.println("gene Layer con nn= " + nn);
		for(Float f:gene)
			System.out.print(f+"\t");*/
		//la dimensione del gene è dimensione_gene_neurone*n_neuroni
		neurons = new ArrayList<Neuron>();
		ArrayList<Float> val = new ArrayList<>();
		int d = gene.size()/nn;
		for(int i=0;i<gene.size();i+=d)
		{
			val.clear();
			for(int j=0;j<d;j++)
			{
				val.add(j, gene.get(i+j));
			}
			neurons.add(new Neuron((ArrayList<Float>) val.clone()));
		}
	}
	
	//2.2.3
	public ArrayList<Float> activate(ArrayList<Float> input)
	{
		/*
		 * Questa funzione riceve riceve in input i valori 
		 * di uscita dei neuroni del Layer precedente. Vengono quindi 
		 * dati "in pasto" alla funzione activate() di ciascun neurone.
		 * I valori di ritorno vengono immagazzinati in un Array e
		 *  ritornati dalla funzione, sarà poi la classe Rete che si
		 *  occuperà di inviarli al Layer successivo.
		 */
		
		ArrayList<Float> result = new ArrayList<>();
		if(inp)
		{
			ArrayList<Float>  in = new ArrayList<>();
			//logica solo per il layer di input
			for(int i=0;i<Rete.N_NEUR_INPUT;i++)
			{
				in.clear();
				in.add(input.get(i));
				result.add(neurons.get(i).activate(in));
			}
		}
		else
		{
		for(Neuron neuron: neurons)
		{
			result.add(neuron.activate(input));
		}}
		return result;
	}
	
	//2.2.4
	public void printData()
	{
		if(verbose)
		for(int i=0;i<neurons.size();i++)
		{
			System.out.println("Neurone "+i);
			neurons.get(i).printData();
		}
	}
}
