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

package org.goranjovic.css.dom;

import java.util.LinkedList;
import java.util.List;

public class StyleSheet {
	
	private List<Selector> idSelectors = new LinkedList<Selector>();
	private List<Selector> tagSelectors = new LinkedList<Selector>();
	private List<Selector> globalSelectors = new LinkedList<Selector>();


	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		for(Selector s : idSelectors){
			buff.append(s.toString()+"\n");
		}
		for(Selector s : tagSelectors){
			buff.append(s.toString()+"\n");
		}
		return buff.toString();
	}

	public void addSelector(Selector selector){
		if(selector.getType()==SelectorType.ID){
			idSelectors.add(selector);
		}else if(selector.getType()==SelectorType.TAG){
			tagSelectors.add(selector);
		}else if(selector.getType()==SelectorType.GLOBAL){
			globalSelectors.add(selector);
		}
	}
	
	private String findValueForGlobal(String property){
		return findValue("*", property, globalSelectors);
	}
	
	private String findValueForTag(String tagName, String property){
		return findValue(tagName, property, tagSelectors);
	}
	
	private String findValueForId(String id, String property){
		return findValue(id, property, idSelectors);
	}
	
	public String findValue(String identifier, String property, List<Selector> selectors){
		for(Selector s : selectors){
			if(s.getName().equals(identifier)){
				for(Declaration r : s.getDeclarations()){
					if(r.getProperty().getName().equals(property)){
						return r.getValue().getValue();
					}
				}
			}
		}
		return null;
	}
	
	public String findValue(String id, String tagName, String property){
		
		String value = findValueForId(id, property);
		if(value != null){
			return value;
		}
		
		value = findValueForTag(tagName, property);
		if(value != null){
			return value;
		}
		
		return findValueForGlobal(property);
	}

}
