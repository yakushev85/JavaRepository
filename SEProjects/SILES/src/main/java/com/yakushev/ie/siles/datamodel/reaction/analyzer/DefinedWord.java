package com.yakushev.ie.siles.datamodel.reaction.analyzer;

public class DefinedWord extends BasicWord {
	public static String[] DEFINED_WORDS = {"тебе", "мне", "ему", "им", "ты", "вы", "они", "мы", "я", "он", "оно"};
	
	public DefinedWord(String word) {
		super(word);
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "Defined Word : " + this.getWord();
	}	
}
