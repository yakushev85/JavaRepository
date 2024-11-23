package com.yakushev.ie.siles.datamodel.reaction;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import com.yakushev.ie.siles.datamodel.NetworkModel;

public class ReactionModel {
	public static final String TXT_CUSTOMCLASSWIWKI = "wiki";
	public static final String TXT_CUSTOMCLASSDYK = "dyk";
	public static final String TXT_CUSTOMCLASSNEWS = "news";
	public static final String TXT_CUSTOMCLASSWAD = "wad";
	public static final String TXT_CUSTOMCLASSMATH = "math";

	private final NetworkModel perceptronModel;
	private List<Reaction> userReactions;
	private List<Reaction> teachReactions;
	private ArrayList<BackReaction> backReactions;
	
	public ReactionModel(NetworkModel pm) throws IOException, XMLStreamException {
		perceptronModel = pm;
		loadReactions();
		loadPerceptronData();
		backReactions = new ArrayList<>();
		System.out.println("Reaction model has been started.");
	}
	
	public void loadReactions() throws IOException, XMLStreamException {
		userReactions = ReactionService.loadUserReactions();
		teachReactions = ReactionService.loadTeachReaction();
	}

	private void loadPerceptronData() throws IOException {
		perceptronModel.setLearningData(teachReactions);

		try {
			perceptronModel.loadPerceptronFromFile();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			perceptronModel.learn();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getAnswer(String msg) {
		return getAnswerSentence(msg);
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
		String teachResult = perceptronModel.exam(sent);

		if (teachResult.isEmpty()) {
			backReactions.add(new BackReaction(sent));
		}

		return teachResult;
	}
	
	public String getCustomAnswerSentence(String sentence, Reaction reaction, boolean isSkipSubValue) {
		String customClass = reaction.getCustomClass();

		switch (customClass) {
			case TXT_CUSTOMCLASSDYK -> {
				CustomReaction dykReaction = new DykCustomReaction(reaction);
				return dykReaction.getCustomAnswer(sentence);
			}
			case TXT_CUSTOMCLASSNEWS -> {
				CustomReaction newsReaction = new NewsCustomReaction(reaction);
				return newsReaction.getCustomAnswer(sentence);
			}
			case TXT_CUSTOMCLASSWAD -> {
				CustomReaction wadReaction = new WadCustomReaction(reaction);
				return wadReaction.getCustomAnswer(sentence);
			}
			case TXT_CUSTOMCLASSWIWKI -> {
				CustomReaction wikiReaction = new WikiCustomReaction(reaction);
				wikiReaction.setSkipFilreringSubValue(isSkipSubValue);
				return wikiReaction.getCustomAnswer(sentence);
			}
			case TXT_CUSTOMCLASSMATH -> {
				CustomReaction mathReaction = new MathCustomReaction(reaction);
				mathReaction.setSkipFilreringSubValue(isSkipSubValue);
				return mathReaction.getCustomAnswer(sentence);
			}
		}
		
		return "";
	}
	
	public List<Reaction> getTeachReactions() {
		return teachReactions;
	}

	public ArrayList<BackReaction> getBackReactions() {
		return backReactions;
	}

	public void removeFirstBackReaction() {
		backReactions.remove(0);
	}
}
