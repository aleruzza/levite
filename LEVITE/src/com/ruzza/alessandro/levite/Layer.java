package com.ruzza.alessandro.levite;

import java.util.ArrayList;

public class Layer {

	private ArrayList<Neuron> neurons;
	
	public Layer(ArrayList<Float> gene, int nn)
	{
		neurons = new ArrayList<>();
		ArrayList<Float> val = new ArrayList<>();
		int d = gene.size()/nn;
		for(int i=0;i<nn;i++)
		{
			val.clear();
			for(int j=0;j<d;j++)
			{
				val.add(j, gene.get(i+j));
			}
			ArrayList<Float> cval = (ArrayList<Float>) val.clone();
			neurons.add(new Neuron(cval));
		}
	}
}
