package com.ruzza.alessandro.levite;

import java.util.ArrayList;

public interface Body {

	ArrayList<Float> getInput() throws Exception;
	
	void sendOutput(ArrayList<Float> out);
	
	void writeReasult(Float fit);
	
	void write(String f, String t);
	
}
