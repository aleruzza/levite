package com.ruzza.alessandro.levite;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

//4
public class Incubatore {

	private ArrayList<ArrayList<Float>> generazione;		//4.1.1
	private static final int N_INDIVIDUI_GEN = 10;			//4.1.2
	private static final float PROB_MUTAZIONE = 0.5f;		//4.1.3	
	private static final int N_PART_PER_GEN = 3;			//4.1.4
	private static final float P_ACC = 15;					//4.1.5
	private static final boolean SAVE_EVERY_GEN = false;
	private boolean verbose = false;						//4.1.6
	public boolean error = false;							//4.1.7
	private FileOutputStream log;							//4.1.8
	int ng;													//4.1.9
	
	
	//4.2.1
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
	
	//4.2.2
	public Incubatore(boolean v)
	{
		this();
		verbose=v;
	}
	
	//4.2.3
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
	//4.2.4
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
	
	//4.2.5
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
			if(i==0 || i==1) {
				ngenerazione.add(generazione.get(pm));
				if(i==0) {
					System.out.println(pm + " best -> "+ris.get(pm));
					write("/home/arkx/TScrivania/best.txt", ris.get(pm)+"\n");
				}
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
			ngenerazione.add(inserisciMutazioni(getFiglio(generazione.get(m), generazione.get(p))));
		}
		generazione = ngenerazione;
	}
	
	//4.2.6
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
	
	//4.2.7
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
	
	//4.2.8
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
			
			if(SAVE_EVERY_GEN)
			{
				for(ArrayList<Float> arr: generazione)
					saveOneDNA(arr);
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
	
	//4.2.9
	private float getProbMutazione()
	{
		return PROB_MUTAZIONE;
	}
	
	//4.2.10
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
	
	//4.2.11
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
	
	//4.2.12
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
	
	//4.2.13
	public void saveOneDNA(ArrayList<Float> dna)
	{
		String f = "/home/arkx/TScrivania/goodnn.txt";
		try {
			FileOutputStream fout = new FileOutputStream(f, true);
			for(Float n: dna) {
					fout.write((n+";").getBytes());
			}
			fout.write(("\n").getBytes());
			fout.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//4.2.14
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
