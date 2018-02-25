package com.ruzza.alessandro.levite;

import java.util.ArrayList;

public class Layer {

	private ArrayList<Neuron> neurons;
	private boolean inp = false;
	public static final boolean LAYER_INPUT = true;
	
	public Layer(ArrayList<Float> gene, int nn, boolean inp)
	{
		this(gene, nn);
		this.inp = inp;
	}
	
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
	
	public void printData()
	{
		for(int i=0;i<neurons.size();i++)
		{
			System.out.println("Neurone "+i);
			neurons.get(i).printData();
		}
	}
}
