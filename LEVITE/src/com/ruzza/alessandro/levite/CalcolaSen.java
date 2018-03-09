package com.ruzza.alessandro.levite;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class CalcolaSen implements Body{
	ArrayList<Float> seni;
	Float[] val_test = {0f, 30f, 45f, 60f, 90f, 120f, 150f, 180f};
	float fit;
	int i;
	
	public CalcolaSen() {
		i=-1;
	}

	@Override
	public ArrayList<Float> getInput() {
		// TODO Auto-generated method stub
		i++;
		ArrayList<Float> inp = new ArrayList<>();
		if(i>=val_test.length)
		{
			//invio fit
			inp.add(-fit);
			this.write("/home/arkx/TScrivania/out.txt", "\n");
		}
		else
		{
			inp.add(val_test[i]/360);
		}
		return inp;
	}
	
	@Override
	public void sendOutput(ArrayList<Float> out) {
		float valore_reale = (float) Math.sin(val_test[i]);
		Float valore_ottenuto = (float) out.get(0)*2 -1;
		
		float differenza = Math.abs(valore_reale - valore_ottenuto) + 0.000001f;
		fit += 1/differenza;
		
		System.out.println(valore_ottenuto);
		
		this.write("/home/arkx/TScrivania/out.txt", valore_ottenuto.toString()+",");
	}
	
	@Override
	public void writeReasult(Float fit) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
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
