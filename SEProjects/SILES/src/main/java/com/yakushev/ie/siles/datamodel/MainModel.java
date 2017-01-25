package com.yakushev.ie.siles.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import com.yakushev.ie.siles.datamodel.history.HistoryItem;
import com.yakushev.ie.siles.datamodel.history.HistoryModel;
import com.yakushev.ie.siles.datamodel.reaction.ReactionModel;
import com.yakushev.ie.siles.observ.Observable;
import com.yakushev.ie.siles.observ.Observer;

public class MainModel implements Observable {
	private List<Observer> observers;
	private ReactionModel modelReaction;
	private HistoryModel historyModel;
	
	public MainModel() throws IOException, XMLStreamException {
		observers = new ArrayList<Observer>();
		historyModel = new HistoryModel();
		modelReaction = new ReactionModel(historyModel);
	}
	
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	public void notifyObservers(String msg) {
		historyModel.getHistoryItems().add(new HistoryItem(msg, true));
		
		for (Observer o : observers) {
			o.updateObserver(msg);
		}
	}

	public void evaluateAnswer(String userMsg) throws IOException {
		historyModel.getHistoryItems().add(new HistoryItem(userMsg, false));
		
		String answer = modelReaction.getAnswer(userMsg);
		if (!answer.isEmpty()) {
			notifyObservers(answer);
		}
	}
	
	public ReactionModel getModelReaction() {
		return modelReaction;
	}
	
	public HistoryModel getHistoryModel() {
		return historyModel;
	}
}
