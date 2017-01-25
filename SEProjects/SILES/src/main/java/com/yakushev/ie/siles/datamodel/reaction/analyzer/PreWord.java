package com.yakushev.ie.siles.datamodel.reaction.analyzer;

public class PreWord extends BasicWord {
	public static String[] PRE_WORDS = {"на", "под", "за", "к", "из", "по", "об", 
		"от", "в", "с", "у", "о", "над", "около", "при", "перед"};
	
	public PreWord(String word) {
		super(word);
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "Pre Word : " + this.getWord();
	}	
}
