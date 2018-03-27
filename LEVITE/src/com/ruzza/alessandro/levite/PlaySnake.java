package com.ruzza.alessandro.levite;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PlaySnake implements Body {

	ServerSocket clientConnect;
	InputStream istream;
	OutputStream ostream;
	Socket clientSock;
	char[] str;
  	InputStreamReader f;
	
	public PlaySnake() {
		try {
			clientConnect = new ServerSocket(1735);
	    	//System.out.println("In ascolto per snake sulla porta " + 1735);
	
	  
	    	
	    	clientSock = clientConnect.accept();
	    	//System.out.println("collegato");
	    	istream = clientSock.getInputStream();
	    	ostream = clientSock.getOutputStream();
	    	f = new InputStreamReader(istream);
		    sendChar("w");	  
		}catch(Exception e)
		{
			//closeConnection();
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Float> getInput() throws Exception {
		ArrayList<Float> dati = new ArrayList<>();
		/*for(int j=0;j<4;j++)
				dati.add(0f);*/
		//ArrayList<Float> t = new ArrayList<>();
		try {
			str = new char[20];
  	  		int i = f.read(str, 0, 20);
  	  		//System.out.println("client> "+ String.valueOf(str));
  	  		String[] s = String.valueOf(str).split(" ");
  	  		for(String val: s)
  	  			dati.add(Float.valueOf(val));
  	  		
  	  		/*trasformazione input
  	  		float ew = t.get(2)-t.get(1);
  	  		float ns = t.get(3)-t.get(2);
  	  		
  	  		if(ns>0)
  	  		{
  	  			dati.set(0, ns/20);
  	  			dati.set(2, 0.001f);
  	  		}
  	  		else
  	  		{
  	  			dati.set(0, 0.001f);
	  			dati.set(2, ns/(-20));
  	  		}
  	  		
  	  		if(ew>0)
  	  		{
  	  			dati.set(1, ew/40);
  	  			dati.set(3, 0.001f);
  	  		}
  	  		else
  	  		{
  	  			dati.set(1, 0.001f);
	  			dati.set(3, ew/(-40));
  	  		}
  	  		*/
  	  		if(Float.valueOf(s[0])<0) {
  	  			write("/home/arkx/TScrivania/snakeR.txt", (Float.valueOf(s[0])*(-1))+"\n");
  	  			dati.set(0, Float.valueOf(s[0]));
  	  			clientConnect.close();
  	  		}
  	  		//TODO: normalizzare l'input
  	  		//per ora divido tutto per 40 che è il valore massimo (fatto sopra)
		}catch(Exception e)
		{
			//throw e;
			closeConnection();
		}
		return dati;
	}

	/*
	@Override
	public void sendOutput(ArrayList<Float> out) {
		int td = getPosMax(out);
		System.out.println("posmax: "+td);
		try {
			Robot r = new Robot();
		switch(td)
		{
			case 0:
				//vai a nord
				r.keyPress(KeyEvent.VK_W);
				r.keyRelease(KeyEvent.VK_W);
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);
				break;
			case 1:
				//vai ad est
				r.keyPress(KeyEvent.VK_D);
				r.keyRelease(KeyEvent.VK_D);
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);
				break;
			case 2:
				//vai a sud
				r.keyPress(KeyEvent.VK_S);
				r.keyRelease(KeyEvent.VK_S);
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);
				break;
			case 3:
				//vai ad ovest
				r.keyPress(KeyEvent.VK_A);
				r.keyRelease(KeyEvent.VK_A);
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);
				break;
		}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	
	@Override
	public void sendOutput(ArrayList<Float> out) {
		// TODO Auto-generated method stub
		int td = getPosMax(out);
		//System.out.println("posmax: "+td);
		switch(td)
		{
			case 0:
				sendChar("w");
				break;
			case 1:
				sendChar("d");
				break;
			case 2:
				sendChar("s");
				break;
			case 3:
				sendChar("a");
				break;
		}
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
	
	private void sendChar(String t)
	{
		try {
			ostream.write(t.getBytes());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void closeConnection()
	{
		try {
			clientConnect.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
