package com.ruzza.alessandro.levite;

import java.util.ArrayList;

public class Neuron {

	private float soglia_attivazione;
	private float alpha = 100; //TODO: scegliere intervallo
	private ArrayList<Float> pesi;
	
	public Neuron(ArrayList<Float> gene)
	{
		//il gene ha una grandezza uguale a
		// numero di dendriti + 1
		//la soglia di attivazione è il primo valore del gene
		this.soglia_attivazione = gene.get(0);
		gene.remove(0);
		this.pesi = gene;
		
	}
	
	public float activate(ArrayList<Float> input)
	{
		float sum=0,ris;
		//sommo i pesi ottenuti
		if(input.size() != pesi.size())
		{
			System.out.println("[ERROR] -> numero di collegamenti utilizzati diverso da quello previsto");
		}
		for(int i=0;i<input.size();i++)
		{
			sum+=input.get(i)*pesi.get(i);
		}
		ris = sum/input.size();
		return funzioneDiAttivazione(ris);
	}

	public int getNDendriti()
	{
		return pesi.size();
	}
	
	private float funzioneDiAttivazione(float inp)
	{
		//funzione di attivazione
		//1/(1+e^-a(x+1/2))
		float ris = (float) ((float) 1/(1+Math.pow(Math.E, -alpha*(inp-soglia_attivazione))));
		return ris;
	}
	
	public void printData()
	{
		System.out.print("sa: "+soglia_attivazione+"; pesi: ");
		for(Float f: pesi)
			System.out.print(f);
		System.out.println();
	}
}
