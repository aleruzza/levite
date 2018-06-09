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

//1
public class Neuron {

	private float soglia_attivazione;							//1.1.1
	private float alpha = 5; //TODO: scegliere intervallo       //1.1.2
	private ArrayList<Float> pesi;								//1.1.3
	private boolean verbose = false;							//1.1.4
	
	//1.2.1
	public Neuron(ArrayList<Float> gene)
	{
		//il gene ha una grandezza uguale a
		// numero di dendriti + 1
		//la soglia di attivazione è il primo valore del gene
		this.soglia_attivazione = 0.5f; //gene.get(0);
		gene.remove(0);
		this.pesi = gene;
		
	}
	
	//1.2.2
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

	//1.2.3
	public int getNDendriti()
	{
		return pesi.size();
	}
	
	//1.2.4
	private float funzioneDiAttivazione(float inp)
	{
		//funzione di attivazione
		//1/(1+e^-a(x+1/2))
		float ris = (float) ((float) 1/(1+Math.pow(Math.E, -alpha*(inp-soglia_attivazione))));
		return ris;
	}
	
	//1.2.5
	public void printData()
	{
		if(verbose) {
		System.out.print("sa: "+soglia_attivazione+"; pesi: ");
		for(Float f: pesi)
			System.out.print(f+"\t");
		System.out.println();
		}
	}
}
