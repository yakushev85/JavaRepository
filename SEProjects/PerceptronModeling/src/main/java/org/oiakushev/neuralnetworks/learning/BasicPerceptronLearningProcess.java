package org.oiakushev.neuralnetworks.learning;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.oiakushev.neuralnetworks.entity.Neuron;
import org.oiakushev.neuralnetworks.entity.Perceptron;
import org.oiakushev.neuralnetworks.entity.TeachDataEntity;

public class BasicPerceptronLearningProcess {
	public static final String EXCEL_STATISTICS_FILENAME = "learning_data.xls";
	public static final double DELTA_VALUE_KOEF = 0.1;
	
	public final int MAX_LEARNING_ITERATIONS = 1000;
	
	private Perceptron perceptron;
	private Neuron[] network;
	private TeachDataEntity[] learningData;
	
	public BasicPerceptronLearningProcess(Perceptron perceptron) {
		this.perceptron = perceptron;
		network = this.perceptron.getNetwork();
		learningData = this.perceptron.getDataForTeaching();
	}
	
	public void start() throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(new File(EXCEL_STATISTICS_FILENAME));
			fileWriter.write("Iteration\tMistake\n");
			int currentIteration = 1;
			int outputError = Integer.MAX_VALUE-1; 

			while (outputError > 0 && currentIteration < MAX_LEARNING_ITERATIONS) {
				outputError = iteration();
				fileWriter.write(currentIteration+"\t"+outputError+"\n");
				currentIteration++;
			}
		} finally {
			fileWriter.close();
		}
	}
	
	private int iteration() {
		int totalErrorSum = 0;
		int[][] errorsMap = new int[learningData.length][perceptron.getNeuronCount()]; 
		
		for (int i=0;i<learningData.length; i++) {
			int[] inVector = learningData[i].getVector(); 
			int[] expectedOutVector = learningData[i].getOutput();
			
			for (int j=0;j<perceptron.getNeuronCount();j++) {
				int[] outVector = perceptron.execute(inVector);
				errorsMap[i][j] = expectedOutVector[j] - outVector[j];
				totalErrorSum += Math.abs(errorsMap[i][j]);
			}
		}
		
		for (int i=0;i<learningData.length;i++) {
			for (int j=0;j<perceptron.getNeuronCount();j++) {
				if (errorsMap[i][j] != 0) {
					int[] inVector = learningData[i].getVector();
					Neuron currentNeuron = network[j];
					
					boolean prizW0Impact = true;
					for (int k=0;k<perceptron.getInCount();k++) {
						if (inVector[k] == 0) {
							prizW0Impact = false;
							break;
						}
					}
					
					if (prizW0Impact) {
						currentNeuron.setWeight(0, 
								currentNeuron.getWeight(0)+errorsMap[i][j]*(DELTA_VALUE_KOEF / perceptron.getInCount())); 
					} else {
						for (int k=0;k<perceptron.getInCount();k++) {
							if (inVector[k] > 0) {
								double currentWeight = 
										currentNeuron.getWeight(k+1)+errorsMap[i][j]*(DELTA_VALUE_KOEF / perceptron.getInCount());
								currentNeuron.setWeight(k+1, currentWeight);
							}
						}
					}
				}
			}
		}
		
		return totalErrorSum;
	}
}
