/*
 * @brief SAMU - the potential ancestor of developmental robotics chatter bots
 *
 * @file nlp.hpp
 * @author  Norbert Bátfai <nbatfai@gmail.com>
 * @version 0.0.1
 *
 * Samu Java Version : Mucsina Dávid Csaba <mucsina0130@gmail.com>
 *
 *
 * @section LICENSE
 *
 * Copyright (C) 2015 Norbert Bátfai, batfai.norbert@inf.unideb.hu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @section DESCRIPTION
 * SAMU
 * 
 * The main purpose of this project is to allow the evaluation and 
 * verification of the results of the paper entitled "A disembodied 
 * developmental robotic agent called Samu Bátfai". It is our hope 
 * that Samu will be the ancestor of developmental robotics chatter 
 * bots that will be able to chat in natural language like humans do.
 *
 */
package pack.java_samu;

import java.io.PrintStream;

public class SPOTriplet {
	public String s;
	public String p;
	public String o;

	public SPOTriplet() {
		s = new String();
		p = new String();
		o = new String();
	}

	public SPOTriplet(String S, String P, String O) {
		s = S;
		p = P;
		o = O;
	}

	public void print(PrintStream out) {
		out.print(s + " " + p + " " + o);
	}

	public boolean equal(SPOTriplet other) {
		return (s.equals(other.s) && p.equals(other.p) && o.equals(other.o));
	}

	public boolean lessThan(SPOTriplet other) {
		String a = s + p + o;
		String b = other.s + other.p + other.o;
		return a.compareTo(b) < 0;
	}

	public double compare(SPOTriplet other) {
		if (this == other || other == null)
			return 1.0;
		else if ((s.equals(other.s) && p.equals(other.p)) || (s.equals(other.s) && o.equals(other.o))
				|| (o.equals(other.o) && p.equals(other.p)))
			return 2.0 / 3.0;
		else if (equal(other))
			return 1.0 / 3.0;

		return 0.0;
	}

	public void cut() {

		try{
			s = s.substring(0, s.indexOf("."));
		}catch(Exception e){
		}
		try{
		s = s.substring(0, s.indexOf("["));
		}catch(Exception e){
		}
		try{
		p = p.substring(0, p.indexOf("."));
		}catch(Exception e){
		}
		try{
		p = p.substring(0, p.indexOf("["));
		}catch(Exception e){
		}
		try{
		o = o.substring(0, o.indexOf("."));
		}catch(Exception e){
		}
		try{
		o = o.substring(0, o.indexOf("["));
		}catch(Exception e){
		}
	}
	

	@Override
    public boolean equals(Object obj){
		return equal((SPOTriplet) obj);
	}
	@Override
    public int hashCode(){
		return s.hashCode()+p.hashCode()+o.hashCode();
		
	}
}
