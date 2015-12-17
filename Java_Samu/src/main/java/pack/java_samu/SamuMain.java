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

import pack.java_samu.Samu;

public class SamuMain {

	public static void main(String[] args) {
		System.out.println("This program will try to provide a disembodied DevRob agent called Samu Bátfai.");
		System.out.println("Copyright (C) 2015 Norbert Bátfai");
		System.out.println("License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>");
		System.out.println("This is free software: you are free to change and redistribute it.");
		System.out.println("There is NO WARRANTY, to the extent permitted by law.");
		System.out.println();

		System.out.println("fejlesztési szakasz kezdődött.");

		System.out.println("fejlesztési szakasz befejeződött.");

		String test[] = { "A rare black squirrel has become a regular visitor to a suburban garden", "This is a car",
				"This car is mine", "I have a little car", "The sky is blue",
				"The little brown bear has eaten all of the honey", "I love Samu" };

		int j = 0;

		for (Samu samu = new Samu(); samu.run();) {
			double sum = 0.0;
			for (int i = 0; i < 7; ++i) {
				samu.viworker(test[i]);
				sum += samu.jutalom();
			}
			System.out.println("###### " + ++j + "-th iter " + sum);
		}

	}

}
