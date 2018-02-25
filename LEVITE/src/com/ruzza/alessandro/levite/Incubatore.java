package com.ruzza.alessandro.levite;

import java.util.ArrayList;
import java.util.Random;

public class Incubatore {

	private ArrayList<ArrayList<Float>> generazione;
	private static final int N_INDIVIDUI_GEN = 5;
	private static final float PROB_MUTAZIONE = 0.3f;
	
	public Incubatore()
	{
		//genero la prima generazione
		generazione = new ArrayList<>();
		for(int i=0;i<N_INDIVIDUI_GEN;i++)
		{
			generazione.add(new ArrayList<>());
			
			for(int j=0;j<Rete.DIM_DNA;j++);
				generazione.get(i).add((float) Math.random());
		}
	}
	
	private void nuovaGenerazione(ArrayList<Float> madre, ArrayList<Float> padre)
	{
		ArrayList<ArrayList<Float>> ngenerazione = new ArrayList<>();
		
		
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
}