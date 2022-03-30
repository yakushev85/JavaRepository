package org.oiakushev.neuralnetworks.controller;

import com.google.gson.Gson;
import org.oiakushev.neuralnetworks.multilayer.MultiNetConfiguration;
import org.oiakushev.neuralnetworks.multilayer.MultiNetwork;
import org.oiakushev.neuralnetworks.singlelayer.entity.Configuration;
import org.oiakushev.neuralnetworks.singlelayer.entity.Perceptron;
import org.oiakushev.neuralnetworks.singlelayer.entity.TeachDataEntity;
import org.oiakushev.neuralnetworks.singlelayer.learning.BasicPerceptronLearningProcess;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainLauncher {
	public static void main(String[] args) throws IOException {
//		testPerceptron();
		testMultiNet();
	}

	private static void testPerceptron()  throws IOException {
		System.out.println("Initialization...");
		Gson gson = new Gson();

		Configuration configuration = gson.fromJson(new FileReader("configuration.json"), Configuration.class);

		List<TeachDataEntity> teachData = configuration.getTeachData();

		Perceptron perceptron = new Perceptron(configuration.getInCount(), configuration.getNeuronCount());
		perceptron.setDataForTeaching(teachData.toArray(new TeachDataEntity[0]));

		System.out.println("Learning...");
		BasicPerceptronLearningProcess learningProcess = new BasicPerceptronLearningProcess(perceptron);
		learningProcess.start();

		System.out.println("Testing..");
		for (TeachDataEntity examItem : teachData) {
			String outputStr = vectorToString(perceptron.execute(examItem.getVector()));
			String answerStr = vectorToString(examItem.getOutput());
			String mark = (outputStr.equals(answerStr))? "PASSED": "FAILED";
			System.out.println(mark);
		}

		System.out.println("Done.");
	}

	private static void testMultiNet()  throws IOException {
		System.out.println("Initialization...");
		Gson gson = new Gson();

		MultiNetConfiguration configuration = gson.fromJson(new FileReader("configurationMulti.json"), MultiNetConfiguration.class);
		MultiNetwork multiNetwork = new MultiNetwork(configuration);

		System.out.println("Learning...");
		multiNetwork.learn();

		System.out.println("Testing..");
		List<TeachDataEntity> teachData = configuration.getTeachData();
		for (TeachDataEntity examItem : teachData) {
			String outputStr = vectorToString(multiNetwork.execute(examItem.getVector()));
			String answerStr = vectorToString(examItem.getOutput());
			String mark = (outputStr.equals(answerStr))? "PASSED": "FAILED";
			System.out.println(mark);
		}

		System.out.println("Done.");
	}
	
	public static String vectorToString(double[] x) {
		StringBuilder result = new StringBuilder();
		for (double j : x) {
			result.append(Math.round(j));
		}
		return result.toString();
	}
}
