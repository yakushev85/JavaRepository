package com.yakushev.ie.siles.datamodel.reaction.analyzer;

public class MethodWord extends BasicWord {
	public static String[] METHOD_ENDS = {"шь", "ть", "ться", "тся", "шься", "шся"};

	public MethodWord(String word) {
		super(word);
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return "Method Word : " + this.getWord();
	}
}
