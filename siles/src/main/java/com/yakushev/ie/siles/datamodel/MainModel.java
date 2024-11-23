package com.yakushev.ie.siles.datamodel;

import com.yakushev.ie.siles.datamodel.reaction.ReactionModel;
import com.yakushev.ie.siles.observ.Observable;
import com.yakushev.ie.siles.observ.Observer;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainModel implements Observable {
	public static final String PERCEPTRON_DATA_FILE = "perceptron.dat";

	private final List<Observer> observers;
	private final ReactionModel reactionModel;
	private final NetworkModel perceptronModel;
	
	public MainModel() throws IOException, XMLStreamException {
		observers = new ArrayList<>();
		perceptronModel = new NetworkModel(PERCEPTRON_DATA_FILE);
		reactionModel = new ReactionModel(perceptronModel);
	}
	
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	public void notifyObservers(String msg) {
		for (Observer o : observers) {
			o.updateObserver(msg);
		}
	}

	public void evaluateAnswer(String userMsg) {
		String answer = reactionModel.getAnswer(userMsg);

		if (!answer.isEmpty()) {
			notifyObservers(answer);
		}
	}
	
	public ReactionModel getReactionModel() {
		return reactionModel;
	}

	public NetworkModel getPerceptronModel() {
		return perceptronModel;
	}
}
