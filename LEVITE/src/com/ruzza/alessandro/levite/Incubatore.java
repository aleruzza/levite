package com.ruzza.alessandro.levite;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Incubatore {

	private ArrayList<ArrayList<Float>> generazione;
	private static final int N_INDIVIDUI_GEN = 5;
	private static final float PROB_MUTAZIONE = 0.3f;
	private boolean verbose = false;
	private FileOutputStream log;
	
	public Incubatore()
	{
		//genero la prima generazione
		generazione = new ArrayList<>();
		for(int i=0;i<N_INDIVIDUI_GEN;i++)
		{
			generazione.add(new ArrayList<>());
			
			for(int j=0;j<Rete.DIM_DNA;j++)
				generazione.get(i).add((float) Math.random());
		}
		
		try {
			log = new FileOutputStream("/home/arkx/TScrivania/logIncubatore.txt");
		}
		catch(Exception e)
		{
			System.out.println("errore nell'apertura del file di log dell'incubatore");
			e.printStackTrace();
		}
	}
	
	public Incubatore(boolean v)
	{
		verbose=v;
	}
	
	private void nuovaGenerazione(ArrayList<Float> madre, ArrayList<Float> padre)
	{
		ArrayList<ArrayList<Float>> ngenerazione = new ArrayList<>();
		for(int i=0;i<N_INDIVIDUI_GEN;i++)
			ngenerazione.add(inserisciMutazioni(getFiglio(madre, padre)));
		generazione = ngenerazione;
	}
	
	private ArrayList<Float> getFiglio(ArrayList<Float> madre, ArrayList<Float> padre)
	{
		ArrayList<Float> son = new ArrayList<>();
		int t=0,i;
		boolean m=true;
		
		for(i=0;i<madre.size();i++)
		{
			if(t<=0)
			{
				t=(int) Math.random()*10;
				m = !m;
			}

			if(m)
				son.add(madre.get(i));
			else
				son.add(padre.get(i));
			t--;
		}
		return son;
	}
	
	private ArrayList<Float> inserisciMutazioni(ArrayList<Float> f)
	{
		for(int i=0;i<Math.random()*100;i++)
		{
			if(Math.random()<PROB_MUTAZIONE)
			{
				int p = (int) ((Math.random()*10000)%f.size());
				f.remove(p);
				f.add(p, (float) Math.random());
			}
		}
		return f;
	}
	
	public void evolvi()
	{
		Rete rete;
		ArrayList<Float> ris = new ArrayList<>();
		
		while(true)
		{
			printGenerazione();
			ris.clear();
			for(int i=0;i<N_INDIVIDUI_GEN;i++)
			{
				rete = new Rete(generazione.get(i));
				ris.add(rete.run());
			}
			
			//si cercano i migliori due e si cra una nuova generazione
			int p = getPosMax(ris);
			ris.set(p, 0f);
			int m = getPosMax(ris);
			
			nuovaGenerazione(generazione.get(p), generazione.get(m));

		}
	}
	
	private int getPosMax(ArrayList<Float> arr)
	{
		int p=0;
		for(int i=0;i<arr.size();i++)
		{
			if(arr.get(i)>arr.get(p))
				p=i;
		}
		return p;
	}
	
	private void printGenerazione()
	{
		try {
			//log.write((generazione.toString()+"\n").getBytes());
			//System.out.println((generazione+"\n").getBytes());
			for(ArrayList<Float> arr: generazione)
			{
				for(Float f: arr)
				{
					System.out.print(f);
				}
				System.out.println();
			}
		}
		catch(Exception e)
		{
			System.out.println("errore nella scrittura sul file log dell'incubatore");
			e.printStackTrace();
		}
		
	}
}
