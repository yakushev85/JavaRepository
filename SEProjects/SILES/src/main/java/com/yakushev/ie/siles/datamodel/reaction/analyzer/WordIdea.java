package com.yakushev.ie.siles.datamodel.reaction.analyzer;

import java.util.ArrayList;

public class WordIdea extends ArrayList<BasicWord> {
	private static final long serialVersionUID = 8227951499587966356L;

	public static final int QUESTION_PART = 0;
	public static final int IDEA_PART = 1;
	public static final int NOINFORMATION_PART = 2;
	
	private int typeOfIdea;

	public int getTypeOfIdea() {
		return typeOfIdea;
	}

	public void setTypeOfIdea(int typeOfIdea) {
		this.typeOfIdea = typeOfIdea;
	}
	
	public void autoSetTypeOfIdea() {
		if (this.isEmpty()) {
			setTypeOfIdea(NOINFORMATION_PART);
		} else if (this.get(0) instanceof QuestionWord) {
			setTypeOfIdea(QUESTION_PART);
		} else {
			setTypeOfIdea(IDEA_PART);
		}
	}
	
	public String toString() {	
		return "Type: " + getTypeOfIdea() + "; " + super.toString();
	}
}
