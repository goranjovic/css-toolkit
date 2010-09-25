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
		}
	}
	
	public String findValueForTag(String tagName, String property){
		for(Selector s : tagSelectors){
			if(s.getName().equals(tagName)){
				for(Declaration r : s.getDeclarations()){
					if(r.getProperty().getName().equals(property)){
						return r.getValue().getValue();
					}
				}
			}
		}
		return null;
	}
	
	public String findValueForId(String id, String property){
		for(Selector s : idSelectors){
			if(s.getName().equals(id)){
				for(Declaration r : s.getDeclarations()){
					if(r.getProperty().getName().equals(property)){
						return r.getValue().getValue();
					}
				}
			}
		}
		return null;
	}
	
	public String findValue(String identifier, String property, SelectorType type){
		if(type == SelectorType.TAG){
			return findValueForTag(identifier, property);
		}else if(type == SelectorType.ID){
			return findValueForId(identifier, property);
		}else{
			return null;
		}
		
	}

}
