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
		this.write("/home/arkx/TScrivania/delta.txt", differenza+"\n\r");
		
		System.out.println(valore_ottenuto);
		
		this.write("/home/arkx/TScrivania/out.txt", valore_ottenuto.toString()+"\n");
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
