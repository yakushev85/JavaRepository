package org.oiakushev.neuralnetworks.controller;

import com.google.gson.Gson;
import org.oiakushev.neuralnetworks.entity.Configuration;
import org.oiakushev.neuralnetworks.entity.Perceptron;
import org.oiakushev.neuralnetworks.entity.TeachDataEntity;
import org.oiakushev.neuralnetworks.learning.BasicPerceptronLearningProcess;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainLauncher {
	private static final String CONFIGURATION_FILE_NAME = "configuration.json";
	
	public static void main(String[] args) throws IOException {
		System.out.println("Initialization...");
		Gson gson = new Gson();

		Configuration configuration = gson.fromJson(new FileReader(CONFIGURATION_FILE_NAME), Configuration.class);

		List<TeachDataEntity> teachData = configuration.getTeachData();

		Perceptron perceptron = new Perceptron(configuration.getInCount(), configuration.getNeuronCount());
		perceptron.setDataForTeaching(teachData.toArray(new TeachDataEntity[0]));

		System.out.println("Learning...");
		BasicPerceptronLearningProcess learningProcess = new BasicPerceptronLearningProcess(perceptron);
		learningProcess.start();

		System.out.println("Examing..");
		for (TeachDataEntity examItem : teachData) {
			String outputStr = vectorToString(perceptron.execute(examItem.getVector()));
			String answerStr = vectorToString(examItem.getOutput());
			String mark = (outputStr.equals(answerStr))? "PASSED": "FAILED";
			System.out.println(mark);
		}

		System.out.println("Done.");
	}
	
	public static String vectorToString(int[] x) {
		StringBuilder result = new StringBuilder();
		for (int j : x) {
			result.append(j);
		}
		return result.toString();
	}
}
