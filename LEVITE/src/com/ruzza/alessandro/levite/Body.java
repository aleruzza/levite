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

public interface Body {

	ArrayList<Float> getInput() throws Exception;
	
	void sendOutput(ArrayList<Float> out);
	
	void writeReasult(Float fit);
	
	void write(String f, String t);
	
}
