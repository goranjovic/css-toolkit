/*CSSToolkit - a Java parser and object model for CSS
Copyright (C) 2009. Goran Jovic

This file is part of CSSToolkit.

CSSToolkit is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CSSToolkit is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the Lesser GNU General Public License
along with CSSToolkit.  If not, see <http://www.gnu.org/licenses/>.*/

package org.goranjovic.css.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.goranjovic.css.dom.Declaration;
import org.goranjovic.css.dom.Selector;
import org.goranjovic.css.dom.SelectorType;
import org.goranjovic.css.dom.StyleSheet;

public class Parser {

	protected List<String> readInput(FileReader reader) {
		try {
			List<String> lines = new LinkedList<String>();
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			return lines;
		} catch (IOException e) {
			return null;
		}
	}

	protected String connectLines(List<String> lines) {
		StringBuffer buff = new StringBuffer();
		for (String s : lines) {
			buff.append(s);
			buff.append(" ");
		}
		return buff.toString();
	}

	protected String normalizeSpace(String s) {
		s = s.trim();
		s = s.replaceAll(" ", "");
		s = s.replaceAll("\t*", "");
		s = s.replaceAll("\n*", "");
		s = s.replaceAll("\\b\\s{2,}\\b", " ");
		return s;
	}

	protected String prepareForParsing(FileReader reader) {
		return normalizeSpace(connectLines(readInput(reader)));
	}

	public StyleSheet parse(FileReader reader) {
		String input = prepareForParsing(reader);
		StyleSheet style = new StyleSheet();
		StringTokenizer t = new StringTokenizer(input, "{}");

		while (t.hasMoreElements()) {
			String[] selectors = t.nextToken().split(",");
			List<Selector> selectorList = new LinkedList<Selector>();
			for (String name : selectors) {
				Selector s = new Selector();
				if (name.equals("*")){
					s.setName("*");
					s.setType(SelectorType.GLOBAL);
				}else if (name.startsWith("#")) {
					s.setType(SelectorType.ID);
					s.setName(name.substring(1));
				} else {
					s.setName(name);
					s.setType(SelectorType.TAG);
				}
				selectorList.add(s);
			}
				List<Declaration> rulesList = new LinkedList<Declaration>();

				String[] rules = t.nextToken().split(";");
				for (int j = 0; j < rules.length; j++) {
					String[] ruleContents = rules[j].split(":");
					String prop = ruleContents[0];
					String val = ruleContents[1];
					Declaration rule = new Declaration(prop, val);
					rulesList.add(rule);
				}
				
				for(Selector s : selectorList){
					s.getDeclarations().addAll(rulesList);
					style.addSelector(s);
				}

			
		}
		return style;
	}

}
