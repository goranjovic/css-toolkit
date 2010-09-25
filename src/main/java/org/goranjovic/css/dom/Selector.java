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

public class Selector {
	
	private List<Declaration> declarations = new LinkedList<Declaration>();
	
	private SelectorType type;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SelectorType getType() {
		return type;
	}

	public void setType(SelectorType type) {
		this.type = type;
	}

	public List<Declaration> getDeclarations() {
		return declarations;
	}	
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("name: "+getName()+"\n");
		for(Declaration r : getDeclarations()){
			buff.append(r.toString()+"\n");
		}
		return buff.toString();
	}
	

}
