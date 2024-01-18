package com.yakushev.ie.siles.controller;

import com.yakushev.ie.siles.datamodel.MainModel;
import com.yakushev.ie.siles.datamodel.reaction.BackReaction;
import com.yakushev.ie.siles.datamodel.reaction.ReactionService;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataController {
	private final MainModel mainModel;
	private BackReaction currentBackReaction;
	
	public DataController(MainModel mainModel) {
		currentBackReaction = null;
		this.mainModel = mainModel;
	}
	
	public void getAnswer(String userMsg) {
		Thread evaluateAnswerThread = new Thread(new EvaluateAnswer(userMsg));
		evaluateAnswerThread.start();
	}
	
	class EvaluateAnswer implements Runnable {
		private final String uMsg;
		
		public EvaluateAnswer(String msg) {
			this.uMsg = msg;
		}

		public void run() {
			try {
				if (currentBackReaction != null) {
					String filteredUserQuestion =
							currentBackReaction.getMsg()
									.replaceAll("[\\.\\!\\?\\,\\;\\:]", " ")
									.replaceAll("\\s+", " ")
									.toLowerCase()
									.trim();

					if (!uMsg.trim().isEmpty()) {
						updateLearningFile(filteredUserQuestion, uMsg);

						mainModel.notifyObservers("Навчаюсь...");
						mainModel.getPerceptronModel()
								.setLearningData(ReactionService.loadTeachReaction());
						mainModel.getPerceptronModel().learn();
						mainModel.notifyObservers("Навчання закінчено.");
					} else {
						mainModel.notifyObservers("Добре, іншим разом!");
					}

					mainModel.getReactionModel().removeFirstBackReaction();
					currentBackReaction = null;
				} else {
					for (String sentence : uMsg.split("[\\.\\!\\?]\\s")) {
						mainModel.evaluateAnswer(sentence);
					}
				}

				ArrayList<BackReaction> backReactions =
						mainModel.getReactionModel().getBackReactions();

				if (backReactions.size() > 0) {
					currentBackReaction = backReactions.get(0);
				}

				if (currentBackReaction != null) {
					mainModel.notifyObservers(currentBackReaction.generateQuestion());
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	public void saveHistoryToFile(String filename, String data) throws IOException {
		try (FileWriter fileWriter = new FileWriter(filename)) {
			fileWriter.write(data);
		}
	}
	
	public String openHistoryFromFile(String filename) throws FileNotFoundException {
		Scanner fileScanner = new Scanner(new FileInputStream(filename));
		StringBuilder resData = new StringBuilder();

		try {
			while (fileScanner.hasNext()) {
				resData.append(fileScanner.nextLine()).append("\n");
			}
		} finally {
			fileScanner.close();
		}
		
		return resData.toString();
	}

	private void updateLearningFile(String userQuestion, String answer) throws IOException {
		File oldFile = new File(ReactionService.TXT_FILENAMETEACHREACTIONS);
		File newFile = new File("t_" + ReactionService.TXT_FILENAMETEACHREACTIONS);

		try (
				Scanner fileScanner = new Scanner(new FileInputStream(oldFile));
				FileWriter fileWriter = new FileWriter(newFile)) {

			while (fileScanner.hasNext()) {
				String oldLine = fileScanner.nextLine();

				if (oldLine.contains("</reactions>")) {
					String newLine = String.format(
							"""
									\t<reaction>
									\t\t<pattern>%s</pattern>
									\t\t<outreaction>%s</outreaction>
									\t</reaction>
									</reactions>""",
							userQuestion, answer
					);

					fileWriter.write(newLine + "\n");
				} else {
					fileWriter.write(oldLine + "\n");
				}
			}
		}

		oldFile.delete();
		newFile.renameTo(new File(ReactionService.TXT_FILENAMETEACHREACTIONS));
	}

	public void learn() throws IOException {
		mainModel.getPerceptronModel().learn();
	}
}
