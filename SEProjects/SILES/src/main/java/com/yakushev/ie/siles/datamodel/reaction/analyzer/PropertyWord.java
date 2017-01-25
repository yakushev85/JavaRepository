package com.yakushev.ie.siles.datamodel.reaction.analyzer;

public class PropertyWord extends BasicWord {
	public static String[] PROPERTY_ENDS = {"ый", "ого", "ому", "ий", "его", "ему", "ой", "им"};
	
	public PropertyWord(String word) {
		super(word);
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "Property Word : " + this.getWord();
	}
}
