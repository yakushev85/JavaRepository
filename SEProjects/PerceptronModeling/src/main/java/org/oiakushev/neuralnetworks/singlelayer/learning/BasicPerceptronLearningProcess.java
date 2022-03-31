package org.oiakushev.neuralnetworks.singlelayer.learning;

import org.oiakushev.neuralnetworks.singlelayer.entity.Neuron;
import org.oiakushev.neuralnetworks.singlelayer.entity.Perceptron;
import org.oiakushev.neuralnetworks.singlelayer.entity.TeachDataEntity;

public class BasicPerceptronLearningProcess {
	public static final double DELTA_VALUE_KOEF = 0.1;
	
	public final int MAX_LEARNING_ITERATIONS = 1000;
	
	private final Perceptron perceptron;
	private final Neuron[] network;
	private final TeachDataEntity[] learningData;
	
	public BasicPerceptronLearningProcess(Perceptron perceptron) {
		this.perceptron = perceptron;
		network = this.perceptron.getNetwork();
		learningData = this.perceptron.getDataForTeaching();
	}
	
	public void start() {
		int currentIteration = 1;
		double outputError = Double.MAX_VALUE - 1;

		while (outputError > 0 && currentIteration < MAX_LEARNING_ITERATIONS) {
			outputError = iteration();
			currentIteration++;
		}

		System.out.println("BasicPerceptronLearningProcess finished with error = " + outputError +
				" iteration = " + currentIteration);
	}
	
	private double iteration() {
		double totalErrorSum = 0;
		double[][] errorsMap = new double[learningData.length][perceptron.getNeuronCount()];
		
		for (int i=0;i<learningData.length; i++) {
			double[] inVector = learningData[i].getVector();
			double[] expectedOutVector = learningData[i].getOutput();
			
			for (int j=0;j<perceptron.getNeuronCount();j++) {
				double[] outVector = perceptron.execute(inVector);
				errorsMap[i][j] = expectedOutVector[j] - outVector[j];
				totalErrorSum += (Math.abs(errorsMap[i][j]) > 0.5)? 1.0 : 0.0;
			}
		}
		
		for (int i=0;i<learningData.length;i++) {
			for (int j=0;j<perceptron.getNeuronCount();j++) {
				if (errorsMap[i][j] != 0) {
					double[] inVector = learningData[i].getVector();
					Neuron currentNeuron = network[j];
					
					boolean prizW0Impact = true;
					for (int k=0;k<perceptron.getInCount();k++) {
						if (inVector[k] == 0) {
							prizW0Impact = false;
							break;
						}
					}
					
					if (prizW0Impact) {
						currentNeuron.setWeightOffset(currentNeuron.getWeightOffset() +
								errorsMap[i][j]*(DELTA_VALUE_KOEF / perceptron.getInCount()));
					} else {
						for (int k=0;k<perceptron.getInCount();k++) {
							if (inVector[k] > 0) {
								double currentWeight = 
										currentNeuron.getWeight(k) +
												errorsMap[i][j]*(DELTA_VALUE_KOEF / perceptron.getInCount());
								currentNeuron.setWeight(k, currentWeight);
							}
						}
					}
				}
			}
		}
		
		return totalErrorSum;
	}
}
