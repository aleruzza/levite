package com.ruzza.alessandro.levite;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Incubatore {

	private ArrayList<ArrayList<Float>> generazione;
	private static final int N_INDIVIDUI_GEN = 10;
	private static final float PROB_MUTAZIONE = 0.6f;
	private static final float N_PART_PER_GEN = 1;
	private static final float P_ACC = 10;
	private boolean verbose = false;
	public boolean error = false;
	private FileOutputStream log;
	int ng;
	
	public Incubatore()
	{
		ng=0;
		/*genero la prima generazione a caso
		generazione = new ArrayList<>();
		for(int i=0;i<N_INDIVIDUI_GEN;i++)
		{
			generazione.add(new ArrayList<>());
			
			for(int j=0;j<Rete.DIM_DNA;j++)
				generazione.get(i).add((float) Math.random()*2);
		}
		*/
		
		//genero la prima generazione con tutto a 0.5
		generazione = new ArrayList<>();
		for(int i=0;i<N_INDIVIDUI_GEN;i++)
		{
			generazione.add(new ArrayList<>());
			for(int j=0;j<Rete.DIM_DNA;j++)
				generazione.get(i).add(0.5f);
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
		ng++;
		System.out.println(ng);
		ArrayList<ArrayList<Float>> ngenerazione = new ArrayList<>();
		for(int i=0;i<N_INDIVIDUI_GEN;i++)
			ngenerazione.add(inserisciMutazioni(getFiglio(madre, padre)));
		generazione = ngenerazione;
	}
	
	//funzione per il controllo, genera una generazione a caso
	private void nuovaGenerazione(ArrayList<Float> ris, boolean controllo)
	{
		ng++;
		if(ng>500)
		{
			System.out.println("Arrivato alla generazione 500 fermare il programma");
			while(true)
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("interrotto sleep");
				}
		}	
		System.out.println(ng);
		int pm = getPosMax(ris);
		System.out.println(pm + " best -> "+ris.get(pm));
		write("/home/arkx/TScrivania/best.txt", ris.get(pm)+"\n");
		
		ArrayList<ArrayList<Float>> ngenerazione = new ArrayList<>();
		
		for(int i=0;i<N_INDIVIDUI_GEN;i++)
		{
			ngenerazione.add(new ArrayList<>());
			
			for(int j=0;j<Rete.DIM_DNA;j++)
				ngenerazione.get(i).add((float) Math.random()*2);
		}
		generazione = ngenerazione;
	}
	private void nuovaGenerazione(ArrayList<Float> ris)
	{
		ng++;
		if(ng>500)
		{
			System.out.println("Arrivato alla generazione 500 fermare il programma");
			while(true)
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("interrotto sleep");
				}
		}	
		System.out.println(ng);
		ArrayList<ArrayList<Float>> ngenerazione = new ArrayList<>();
		//ottengo i 3 migliori
		for(int i=0;i<3;i++) {
			int pm = getPosMax(ris);
			if(i==0) {
				ngenerazione.add(generazione.get(pm));
				System.out.println(pm + " best -> "+ris.get(pm));
				write("/home/arkx/TScrivania/best.txt", ris.get(pm)+"\n");
			}
			else
				ngenerazione.add(inserisciMutazioni(generazione.get(pm)));
			generazione.remove(pm);
			ris.remove(pm);
		}
		
		//aggiungo 2 dei peggiori
		for(int i=0;i<2;i++)
		{
			int n = (int) (Math.random()*5);
			ngenerazione.add(inserisciMutazioni(generazione.get(n)));
		}
		
		//faccio fare 5 figli
		for(int i=5;i<N_INDIVIDUI_GEN;i++)
		{
			int m = (int) (Math.random())*5;
			int p = (int) (Math.random())*5;
			ngenerazione.add(getFiglio(generazione.get(m), generazione.get(p)));
		}
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
				f.add(p, (float) Math.random()*2);
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
				float r = 0;
				for(int j=0;j<N_PART_PER_GEN;j++) {
					rete = new Rete(generazione.get(i), this);
					r+=rete.run();
				}
				ris.add(r/N_PART_PER_GEN);
			}
			
			//si cercano i migliori due e si crea una nuova generazione
			//int p = getPosMax(ris);
			//ris.set(p, 0f);
			//int m = getPosMax(ris);
			
			//controllo se il migliore ha un punteggio maggiore di P_ACC e nel caso lo salvo
			if(ris.get(getPosMax(ris))>P_ACC)
			{
				saveOneDNA(generazione.get(getPosMax(ris)));
			}
			
			if(!error) {
			//nuovaGenerazione(generazione.get(p), generazione.get(m));
			nuovaGenerazione(ris);
			//nuovaGenerazione(ris, true);
			}
			else
			{
				error = false;
			}
			
		}
	}
	
	private float getProbMutazione()
	{
		return PROB_MUTAZIONE;
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
		if(verbose)
		{try {
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
	
	public void saveGenerazione() {
		// TODO Auto-generated method stub
		String f = "/home/arkx/TScrivania/lgen.txt";
		try {
			FileOutputStream fout = new FileOutputStream(f, true);
			for(ArrayList<Float> arr: generazione) {
				for(Float n: arr)
					fout.write((n+";").getBytes());
				fout.write(("\n").getBytes());
			}
			fout.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveOneDNA(ArrayList<Float> dna)
	{
		String f = "/home/arkx/TScrivania/goodnn.txt";
		try {
			FileOutputStream fout = new FileOutputStream(f, true);
			for(Float n: dna) {
					fout.write((n+";").getBytes());
				fout.write(("\n").getBytes());
			}
			fout.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void write(String f, String t) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fout = new FileOutputStream(f, true);
			fout.write(t.getBytes());
			fout.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
