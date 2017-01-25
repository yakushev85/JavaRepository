package com.yakushev.ie.siles.datamodel.reaction.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeHelper {
	public static String ALPHABET = "йцукенгшщзхъёфывапролджэячсмитьбю";
	public static String BOOK_FILENAME = "book.dat";
	public static String VOICE_CHARS_END = "йуеыаоэяиью";
		
	public static BasicWord getBasicWord(String word) {
		for (String itemDefinedWord : DefinedWord.DEFINED_WORDS) {
			if (word.equals(itemDefinedWord)) {
				return new DefinedWord(word);
			}
		}
		
		for (String itemPreWord : PreWord.PRE_WORDS) {
			if (word.equals(itemPreWord)) {
				return new PreWord(word);
			}
		}
		
		for (String itemQuestionWortd : QuestionWord.QUESTION_WORDS) {
			if (word.equals(itemQuestionWortd)) {
				return new QuestionWord(word);
			}
		}
		
		for (String propsEndsWord : PropertyWord.PROPERTY_ENDS) {
			if (word.endsWith(propsEndsWord)) {
				return new PropertyWord(word);
			}
		}
		
		for (String methodEndsWord : MethodWord.METHOD_ENDS) {
			if (word.endsWith(methodEndsWord)) {
				return new MethodWord(word);
			}
		}

		return new ObjectWord(word);
	}
	
	public static WordMessage getWordMessage(String msg) {
		StringBuilder curWord = new StringBuilder("");
		BasicWord curBasicWord = null;
		WordMessage resMessage = new WordMessage();
		WordIdea wordIdea = new WordIdea();
		
		for (int i=0;i<msg.length();i++) {
			char itemCharAt = msg.charAt(i);
			
			if (ALPHABET.indexOf(itemCharAt) >= 0) {
				curWord.append(itemCharAt);
			} else {
				if (curWord.length() > 0) {
					curBasicWord = getBasicWord(curWord.toString());
					wordIdea.add(curBasicWord);
				}
				
				if (!Character.isSpaceChar(itemCharAt)) {
					if (itemCharAt == '?') {
						wordIdea.setTypeOfIdea(WordIdea.QUESTION_PART);
					} else {
						wordIdea.autoSetTypeOfIdea();
					}
					
					resMessage.add(wordIdea);
					wordIdea = new WordIdea();
				}
				
				curWord.setLength(0);
			}
		}
		
		return resMessage;
	}
	
	public static String getResponseFromIdea(WordIdea wordIdea) throws IOException {
		//System.out.println("Begin getResponseFromIdea "+wordIdea.toString());
		
		if (wordIdea.getTypeOfIdea() == WordIdea.NOINFORMATION_PART) {
			return "";
		}
		
		if (wordIdea.getTypeOfIdea() == WordIdea.QUESTION_PART) {
			return getQuestionResponseFromIdea(wordIdea);
		} else {
			return getIdeaResponseFromIdea(wordIdea);
		}
	}
	
	private static String filterVoiceEnd(String word) {
		if (word == null || word.isEmpty()) return word;
		
		StringBuilder wordProcessing = new StringBuilder(word);
		
		while (VOICE_CHARS_END.indexOf(wordProcessing.charAt(wordProcessing.length()-1)) >= 0) {
			wordProcessing.deleteCharAt(wordProcessing.length()-1);
			
			if (wordProcessing.length() == 0) return word;
		}
		
		return wordProcessing.toString();
	}
	
	private static String getQuestionResponseFromIdea(WordIdea wordIdea) throws IOException {
		BookSearcher bookSearcher = new BookSearcher(BOOK_FILENAME);
		List<String> searchWords = new ArrayList<String> ();
		
		for (BasicWord basicWord : wordIdea) {
			if (basicWord instanceof ObjectWord) {
				ObjectWord objectWord = (ObjectWord) basicWord;
				searchWords.add(filterVoiceEnd(objectWord.getWord()));
			} else if (basicWord instanceof PropertyWord) {
				PropertyWord propertyWord = (PropertyWord) basicWord;
				searchWords.add(filterVoiceEnd(propertyWord.getWord()));
			}
		}
		
		if (searchWords.isEmpty()) {
			return "";
		} 
		
		List<String> responseList = bookSearcher.searchContainsByListOfWords(searchWords);
		
		if (responseList.isEmpty()) {
			return "";
		}
		
		return responseList.get( (int) (Math.random() * responseList.size()) );
	}
	
	private static String getIdeaResponseFromIdea(WordIdea wordIdea) throws IOException {
		BookSearcher bookSearcher = new BookSearcher(BOOK_FILENAME);
		List<String> searchWords = new ArrayList<String> ();
		
		for (BasicWord basicWord : wordIdea) {
			if (basicWord instanceof ObjectWord) {
				ObjectWord objectWord = (ObjectWord) basicWord;
				searchWords.add(filterVoiceEnd(objectWord.getWord()));
			}
		}
		
		if (searchWords.isEmpty()) {
			return "";
		} 
		
		List<String> responseList = bookSearcher.searchEqualsByListOfWords(searchWords);
		
		if (responseList.isEmpty()) {
			return "";
		}
		
		return responseList.get( (int) (Math.random() * responseList.size()) );
	}
}
