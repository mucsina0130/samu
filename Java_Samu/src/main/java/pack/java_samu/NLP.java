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

import java.util.ArrayList;
import java.util.List;

import org.linkgrammar.LinkGrammar;
import org.linkgrammar.Linkage;

public class NLP {

	public NLP() {

		LinkGrammar.init();
	}

	public List<SPOTriplet> tirper(String s) {
		List<SPOTriplet> found = new ArrayList<SPOTriplet>();

		LinkGrammar.parse(s);
		int linkageSize = LinkGrammar.getNumLinkages();
		SPOTriplet triplet = new SPOTriplet();
		String alter_p = new String();
		boolean ready = false;

		for (int l = 0; l < linkageSize; ++l) {
			LinkGrammar.makeLinkage(l);
			Linkage linkage = new Linkage();
			linkage.setDisjunctCost(LinkGrammar.getLinkageDisjunctCost());
			linkage.setLinkCost(LinkGrammar.getLinkageLinkCost());
			linkage.setLinkedWordCount(LinkGrammar.getNumWords());
			linkage.setNumViolations(LinkGrammar.getLinkageNumViolations());
			String[] disjuncts = new String[LinkGrammar.getNumWords()];
			String[] words = new String[LinkGrammar.getNumWords()];
			for (int i = 0; i < words.length; i++) {
				disjuncts[i] = LinkGrammar.getLinkageDisjunct(i);
				words[i] = LinkGrammar.getLinkageWord(i);
			}

			int numLinks = LinkGrammar.getNumLinks();
			for (int i = 0; i < numLinks && !ready; i++) {
				String c = LinkGrammar.getLinkLabel(i);
				if (c.charAt(0) == 'S') {
					triplet.p = LinkGrammar.getLinkageWord(i);
					alter_p = words[LinkGrammar.getLinkRWord(i)];
					triplet.s = words[LinkGrammar.getLinkLWord(i)];
				}
				if (c.charAt(0) == 'O') {
					triplet.o = words[LinkGrammar.getLinkRWord(i)];
					if (triplet.p.equals(words[LinkGrammar.getLinkLWord(i)])) {
						triplet.cut();
						found.add(triplet);
						ready = true;
						break;
					} else if (alter_p.equals(words[LinkGrammar.getLinkLWord(i)])) {
						triplet.p = alter_p;
						triplet.cut();
						found.add(triplet);
						ready = true;
						break;

					}
				}
			}

		}

		return found;
	}

}
