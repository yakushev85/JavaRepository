package org.oiakushev.neuralnetworks.singlelayer;

import org.oiakushev.neuralnetworks.general.Network;
import org.oiakushev.neuralnetworks.general.Neuron;
import org.oiakushev.neuralnetworks.general.TeachDataEntity;

import java.util.List;

public class Perceptron implements Network {
	private final int inCount;
	private final int neuronCount;
	private final Neuron[] network;
	private final List<TeachDataEntity> teachData;
	private final double delta;
	private final int maxLearningIterations;
	
	public Perceptron(PerceptronConfiguration configuration) {
		this.inCount = configuration.getInCount();
		this.neuronCount = configuration.getNeuronCount();
		this.delta = configuration.getDelta();

		this.maxLearningIterations = configuration.getMaxLearningIterations();
		this.teachData = configuration.getTeachData();
		
		network = new Neuron[neuronCount];
		for (int i=0;i<neuronCount;i++) {
			network[i] = new Neuron(inCount, configuration.getInitialWeightValue());
		}
	}

	public int getInCount() {
		return inCount;
	}

	public int getNeuronCount() {
		return neuronCount;
	}

	@Override
	public double[] execute(double[] inVector) {
		double[] result = new double[getNeuronCount()];
		for (int i=0;i<result.length;i++) {
			network[i].setInVector(inVector);
			result[i] = network[i].generateOutput();
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "Perceptron Information {\n InCount: "+getInCount()+" NeuronCount:"+getNeuronCount()+
				" Network is "+((network == null)?"not set":"set")+
				". TeachData is "+((teachData == null)?"not set":"set") + "\n}";
	}
	
	public String getCurrentState() {
		StringBuilder result = new StringBuilder();
		for (int i=0;i<neuronCount;i++) {
			result.append("Neuron ").append(i + 1).append(" { ");
			
			for (int j=0;j<=inCount;j++) {
				result.append("w").append(j).append(":").append(network[i].getWeight(j)).append(", ");
			}
			result.append("}\n");
		}
		
		return result.toString();
	}

	@Override
	public void learn() {
		int currentIteration = 1;
		double outputError = Double.MAX_VALUE - 1;

		while (outputError > 0 && currentIteration < maxLearningIterations) {
			outputError = iteration();
			currentIteration++;
		}

		System.out.println("Perceptron.learn finished with error = " + outputError +
				" iteration = " + currentIteration);
	}

	private double iteration() {
		double totalErrorSum = 0;
		double[][] errorsMap = new double[teachData.size()][getNeuronCount()];

		for (int i=0;i<teachData.size(); i++) {
			double[] inVector = teachData.get(i).getVector();
			double[] expectedOutVector = teachData.get(i).getOutput();

			for (int j=0;j<getNeuronCount();j++) {
				double[] outVector = execute(inVector);
				errorsMap[i][j] = expectedOutVector[j] - outVector[j];
				totalErrorSum += (Math.abs(errorsMap[i][j]) > 0.5)? 1.0 : 0.0;
			}
		}

		for (int i=0;i<teachData.size();i++) {
			for (int j=0;j<getNeuronCount();j++) {
				if (errorsMap[i][j] != 0) {
					double[] inVector = teachData.get(i).getVector();
					Neuron currentNeuron = network[j];

					boolean prizW0Impact = true;
					for (int k=0;k<getInCount();k++) {
						if (inVector[k] == 0) {
							prizW0Impact = false;
							break;
						}
					}

					if (prizW0Impact) {
						currentNeuron.setWeightOffset(currentNeuron.getWeightOffset() +
								errorsMap[i][j]*delta);
					} else {
						for (int k=0;k<getInCount();k++) {
							if (inVector[k] > 0) {
								double currentWeight = currentNeuron.getWeight(k) +
										errorsMap[i][j]*delta;
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
