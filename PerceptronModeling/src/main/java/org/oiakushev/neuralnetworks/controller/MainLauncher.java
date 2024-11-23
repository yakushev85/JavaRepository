package org.oiakushev.neuralnetworks.controller;

import com.google.gson.Gson;
import org.oiakushev.neuralnetworks.general.NetConfiguration;
import org.oiakushev.neuralnetworks.general.TeachDataEntity;
import org.oiakushev.neuralnetworks.multilayer.MultiNetwork;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainLauncher {
	private static final Gson gson = new Gson();

	public static void main(String[] args) throws IOException {
		testMultiNet("configurationSingle.json");
		testMultiNet("configurationMulti.json");
	}

	private static void testMultiNet(String filename)  throws IOException {
		System.out.println("Network initialization for " + filename + " ...");

		NetConfiguration configuration = gson.fromJson(
				new FileReader(filename), NetConfiguration.class);
		MultiNetwork multiNetwork = new MultiNetwork(configuration);

		System.out.println("Learning...");
		multiNetwork.learn(false);

		System.out.println("Testing...");
		List<TeachDataEntity> teachData = configuration.getTeachData();

		for (TeachDataEntity testItem : teachData) {
			String outputStr = vectorToString(multiNetwork.execute(testItem.getVector()));
			String answerStr = vectorToString(testItem.getOutput());
			System.out.print("Actual: " + outputStr);
			System.out.print(" Expected: " + answerStr);
			String mark = (outputStr.equals(answerStr))? " PASSED": " FAILED";
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
