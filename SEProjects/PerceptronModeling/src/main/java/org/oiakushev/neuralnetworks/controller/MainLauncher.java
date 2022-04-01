package org.oiakushev.neuralnetworks.controller;

import com.google.gson.Gson;
import org.oiakushev.neuralnetworks.general.Network;
import org.oiakushev.neuralnetworks.general.TeachDataEntity;
import org.oiakushev.neuralnetworks.multilayer.MultiNetConfiguration;
import org.oiakushev.neuralnetworks.multilayer.MultiNetwork;
import org.oiakushev.neuralnetworks.singlelayer.Perceptron;
import org.oiakushev.neuralnetworks.singlelayer.PerceptronConfiguration;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainLauncher {
	private static final Gson gson = new Gson();

	public static void main(String[] args) throws IOException {
		testPerceptron();
//		testMultiNet();
	}

	private static void testPerceptron()  throws IOException {
		System.out.println("Perceptron initialization...");

		PerceptronConfiguration configuration =
				gson.fromJson(new FileReader("configurationPerceptron.json"), PerceptronConfiguration.class);
		Perceptron perceptron = new Perceptron(configuration);

		learnAndTestNet(perceptron, configuration.getTeachData());
	}

	private static void testMultiNet()  throws IOException {
		System.out.println("Multilayer network initialization...");

		MultiNetConfiguration configuration = gson.fromJson(
				new FileReader("configurationMulti.json"), MultiNetConfiguration.class);
		MultiNetwork multiNetwork = new MultiNetwork(configuration);

		learnAndTestNet(multiNetwork, configuration.getTeachData());
	}

	private static void learnAndTestNet(Network network, List<TeachDataEntity> teachData) {
		System.out.println("Learning...");
		network.learn();

		System.out.println("Testing...");

		for (TeachDataEntity testItem : teachData) {
			String outputStr = vectorToString(network.execute(testItem.getVector()));
			String answerStr = vectorToString(testItem.getOutput());
			System.out.println("Actual: " + outputStr);
			System.out.println("Expected: " + answerStr);
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
