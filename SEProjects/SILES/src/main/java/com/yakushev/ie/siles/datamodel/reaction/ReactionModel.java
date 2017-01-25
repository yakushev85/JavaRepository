package com.yakushev.ie.siles.datamodel.reaction;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;

import com.yakushev.ie.siles.datamodel.history.HistoryModel;
import com.yakushev.ie.siles.datamodel.reaction.analyzer.AnalyzerCustomReaction;
import com.yakushev.ie.siles.datamodel.reaction.dialog.DialogService;
import com.yakushev.ie.siles.log.LogSiles;

public class ReactionModel {
	public static final String TXT_CUSTOMCLASSWIWKI = "wiki";
	public static final String TXT_CUSTOMCLASSMATH = "math";
	public static final String TXT_CUSTOMBOOK = "book"; 
	public static final String TXT_CUSTOMCLASSUNKONOWN = "unknown";
	public static final String[] TXT_CALLBACKQUESTION = {"Как я должна отвечать на: '", "'?"};
	public static final String TXT_THANKS = "Спасибо!";
	public static final String TXT_ANOTHERTIME = "Расскажешь, когда захочешь.";
	public char[] SENTENCE_DELIMETERS = {'.', '!', '?'};
	
	private DialogService dialogService;
	private List<Reaction> userReactions;
	private List<Reaction> teachReactions;
	private HistoryModel historyModel;
	private Logger logger;
	
	public ReactionModel(HistoryModel hm) throws IOException, XMLStreamException {
		logger = LogSiles.getConfiguredLogger(ReactionModel.class);
		historyModel = hm;
		loadReactions();
		dialogService = new DialogService();
		logger.info("Reaction model has been started.");
	}
	
	public void loadReactions() throws IOException, XMLStreamException {
		userReactions = ReactionService.loadUserReactions();
		teachReactions = ReactionService.loadTeachReaction();
	}
	
	public void saveReactions() throws IOException {
		ReactionService.updateTeachReactions(teachReactions);
	}
	
	public void deleteTeachReactionByRowNum(int rowNum) {
		teachReactions.remove(rowNum);
	}
	
	public void addTeachReaction(Reaction reaction) {
		teachReactions.add(reaction);
	}
	
	private String getUserPatternFromCallBackMessage(String msg) {
		String resPattern = "^" + msg.substring(
				msg.indexOf(TXT_CALLBACKQUESTION[0]) + TXT_CALLBACKQUESTION[0].length(), 
				msg.indexOf(TXT_CALLBACKQUESTION[1])).toLowerCase().trim();
		
		resPattern = resPattern.replaceAll("\\s", "(\\\\s)+");
		
		if (!resPattern.isEmpty()) {
			resPattern = resPattern.substring(0, resPattern.length()-1) + "[\\.\\!\\?]$";
		}
		
		return resPattern;
	}
	
	public String getAnswer(String msg) throws IOException {
		String lastSilesMsg = historyModel.getLastSilesMessage();
		String answerStr = "";
		String msgReNew = msg.trim().toLowerCase();
		
		if (lastSilesMsg.indexOf(TXT_CALLBACKQUESTION[0]) >= 0) {
			if (msgReNew.isEmpty()) {
				return TXT_ANOTHERTIME;
			}
			
			Reaction newTeachReaction = new Reaction();
			String patternReaction = getUserPatternFromCallBackMessage(lastSilesMsg);
			
			newTeachReaction.addPatternTag(patternReaction);
			newTeachReaction.addReaction(msgReNew);
			
			teachReactions.add(newTeachReaction);
			ReactionService.updateTeachReactions(teachReactions);
			
			return TXT_THANKS;
		}
		
		while (!msgReNew.isEmpty() && answerStr.indexOf(TXT_CALLBACKQUESTION[0]) < 0) {
			int minIndex = msgReNew.length();
			
			for (char c : SENTENCE_DELIMETERS) {
				int newIndex = msgReNew.indexOf(c);
				char nextChar = (newIndex < (msgReNew.length()-1))?msgReNew.charAt(newIndex+1):' ';
				if (newIndex < minIndex && newIndex >= 0 && Character.isWhitespace(nextChar)) {
					minIndex = msgReNew.indexOf(c); 
				}
			}
			
			if (minIndex != msg.length()) {
				String sentence = msgReNew.substring(0,minIndex+1);
				answerStr += getAnswerSentence(sentence) + " ";
				
				msgReNew = msgReNew.substring(minIndex+1);
			} else {
				answerStr += getAnswerSentence(msgReNew) + " ";
				
				msgReNew = "";
			}
		}
		
		msgReNew = msg.trim().toLowerCase();
		if ((answerStr.isEmpty() || answerStr.indexOf(TXT_CALLBACKQUESTION[0]) >= 0) &&
				(dialogService.isDialogActive() || dialogService.checkDialogStart(msgReNew))) {
			String dialogResponse = dialogService.changePoint(msgReNew);
			
			if (!dialogResponse.isEmpty()) {
				answerStr = dialogResponse;
				
				String customClass = dialogService.getCurrentPoint().getCustomClass();
				if (customClass != null) {
					String customInput = dialogService.getCurrentPoint().getCustomInput();
					Reaction customReaction = new Reaction();
					customReaction.setCustomClass(customClass);
					String inData = (customInput != null)?customInput:msgReNew;
					answerStr += "\n" + getCustomAnswerSentence(inData, customReaction, true);
				}
			}
		}
		
		return answerStr;
	}
	
	public String getAnswerSentence(String sent) {
		// Manual users reactions
		for (Reaction reaction : userReactions) {
			if (reaction.checkStringForReaction(sent)) {
				if (reaction.getCustomClass() == null) {
					return reaction.getReaction();
				} else {
					return getCustomAnswerSentence(sent, reaction, false);
				}
			}
		}
		
		// Teach reactions
		for (Reaction reaction : teachReactions) {
			if (reaction.checkStringForReaction(sent)) {
				return reaction.getReaction();
			}
		}
		
		return TXT_CALLBACKQUESTION[0] + sent + TXT_CALLBACKQUESTION[1];
	}
	
	public String getCustomAnswerSentence(String sentence, Reaction reaction, boolean isSkipSubValue) {
		String customClass = reaction.getCustomClass();
		
		if (customClass.equals(TXT_CUSTOMCLASSWIWKI)) {
			CustomReaction wikiReaction = new WikiCustomReaction(reaction);
			wikiReaction.setSkipFilreringSubValue(isSkipSubValue);
			return wikiReaction.getCustomAnswer(sentence);
		} else if (customClass.equals(TXT_CUSTOMCLASSMATH)) {
			CustomReaction mathReaction = new MathCustomReaction(reaction);
			mathReaction.setSkipFilreringSubValue(isSkipSubValue);
			return mathReaction.getCustomAnswer(sentence);
		} else if (customClass.equals(TXT_CUSTOMBOOK)) {
			CustomReaction bookReaction =  new AnalyzerCustomReaction(reaction);
			bookReaction.setSkipFilreringSubValue(isSkipSubValue);
			return bookReaction.getCustomAnswer(sentence);
		}
		
		return "";
	}
	
	public List<Reaction> getTeachReactions() {
		return teachReactions;
	}
}
