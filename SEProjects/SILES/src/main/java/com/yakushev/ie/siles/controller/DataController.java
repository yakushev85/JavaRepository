package com.yakushev.ie.siles.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import javax.swing.table.AbstractTableModel;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;

import com.yakushev.ie.siles.datamodel.MainModel;
import com.yakushev.ie.siles.datamodel.reaction.Reaction;
import com.yakushev.ie.siles.log.LogSiles;
import com.yakushev.ie.siles.viewmodel.ReactionsTableModel;

public class DataController {
	private MainModel mainModel;
	private Logger logger;
	
	public DataController(MainModel mainModel) {
		this.mainModel = mainModel;
		logger = LogSiles.getConfiguredLogger(DataController.class);
	}
	
	public void getAnswer(String userMsg) {
		Thread evaluateAnswerThread = new Thread(new EvaluateAnswer(userMsg));
		evaluateAnswerThread.start();
	}
	
	class EvaluateAnswer implements Runnable {
		private String uMsg;
		
		public EvaluateAnswer(String msg) {
			this.uMsg = msg;
		}

		public void run() {
			try {
				mainModel.evaluateAnswer(uMsg);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		
	}
	
	public void saveHistoryToFile(String filename, String data) throws IOException {
		FileWriter fileWriter = new FileWriter(filename);
		fileWriter.write(data);
		fileWriter.close();
	}
	
	public String openHistoryFromFile(String filename) throws FileNotFoundException {
		Scanner fileScanner = new Scanner(new FileInputStream(filename));
		String resData = "";
		
		while (fileScanner.hasNext()) {
			resData += fileScanner.nextLine()+"\n";
		}
		
		fileScanner.close();
		
		return resData;
	}
	
	public AbstractTableModel getTableModel() {
		return new ReactionsTableModel(mainModel.getModelReaction().getTeachReactions());
	}
	
	public void reloadReactions() throws IOException, XMLStreamException {
		mainModel.getModelReaction().loadReactions();
	}
	
	public void saveReactions() throws IOException {
		mainModel.getModelReaction().saveReactions();
	}
	
	public void deleteTeachReaction(int selectedRow) {
		mainModel.getModelReaction().deleteTeachReactionByRowNum(selectedRow);
	}
	
	public void addDefaultTeachReaction() {
		Reaction newReaction = new Reaction();
		Date dateNow = new Date();
		newReaction.addPatternTag("^новый паттерн "+dateNow.toString()+" [\\.\\!\\?]$");
		newReaction.addReaction("Новая реакция");
		
		mainModel.getModelReaction().addTeachReaction(newReaction);
	}
}
