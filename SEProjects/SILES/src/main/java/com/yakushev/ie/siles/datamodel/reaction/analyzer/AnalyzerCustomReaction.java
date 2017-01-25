package com.yakushev.ie.siles.datamodel.reaction.analyzer;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.yakushev.ie.siles.datamodel.reaction.CustomReaction;
import com.yakushev.ie.siles.datamodel.reaction.Reaction;
import com.yakushev.ie.siles.log.LogSiles;

public class AnalyzerCustomReaction extends CustomReaction {
	private Logger logger;

	public AnalyzerCustomReaction(Reaction creaction) {
		super(creaction);
		logger = LogSiles.getConfiguredLogger(AnalyzerCustomReaction.class);
	}

	@Override
	public String getCustomAnswer(String msg) {
		WordMessage wordMessage = AnalyzeHelper.getWordMessage(this.getSubValue(msg));
		StringBuilder response = new StringBuilder("\"...");
		
		for (WordIdea itemIdea : wordMessage) {
			try {
				response.append(AnalyzeHelper.getResponseFromIdea(itemIdea)).append(" ");
			} catch (IOException e) {
				logger.error("IO error in Analyzer", e);
			}
		}
		
		return response.append("...\"").toString().trim();
	}

}
