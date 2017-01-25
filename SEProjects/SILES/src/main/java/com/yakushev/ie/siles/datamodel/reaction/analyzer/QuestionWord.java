package com.yakushev.ie.siles.datamodel.reaction.analyzer;

public class QuestionWord extends BasicWord {
	public static String QUESTION_WHERE_WORD = "где";
	public static String QUESTION_WHEN_WORD = "когда";
	public static String QUESTION_WHAT1_WORD = "кто";
	public static String QUESTION_WHAT2_WORD = "что";
	public static String QUESTION_WHAT3_WORD = "как";
	public static String QUESTION_WHY_WORD = "почему";
	public static String[] QUESTION_WORDS = 
		{QUESTION_WHERE_WORD, QUESTION_WHEN_WORD,  QUESTION_WHY_WORD,
		QUESTION_WHAT1_WORD, QUESTION_WHAT2_WORD, QUESTION_WHAT3_WORD,
		"куда", "сколько", "какой", "откуда", "зачем"};

	public QuestionWord(String word) {
		super(word);
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "Question Word : " + this.getWord();
	}	
}
