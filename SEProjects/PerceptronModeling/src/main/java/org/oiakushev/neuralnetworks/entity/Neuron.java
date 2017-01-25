package org.oiakushev.neuralnetworks.entity;

public class Neuron {
	public static final double DEFAULT_VALUE_KOEF = 0.1;
	public static final double DEFAULT_VALUE_OFFSET_WIGHT = 0.0;
	public static final double DEFAULT_FILTER_LIMIT = 0.5;
	
	private int inCount;
	private int[] inVector;
	private double[] weights;
	private double net;
	private int output;
	private double default_value_weight;
	
	public Neuron(int inCount, double default_value_weight) {
		this.inCount = inCount;
		this.default_value_weight = default_value_weight;
		weights = new double[inCount+1];
		weights[0] = DEFAULT_VALUE_OFFSET_WIGHT;
		for (int i=1;i<=this.inCount;i++) {
			weights[i] = this.default_value_weight;
		}
	}
	
	public Neuron(int inCount) {
		this(inCount, DEFAULT_VALUE_KOEF / inCount);
	}
	
	public void setInVector(int[] inVector) {
		if (inVector.length != inCount) {
			throw new IllegalArgumentException("Input vector size doesn't match allowed size.");
		}
		
		this.inVector = inVector;
	}
	
	public int[] getInVector() {
		return this.inVector;
	}
	
	public int generateOutput() {
		net = weights[0];
		for (int i=1;i<=inCount;i++) {
			net += weights[i]*inVector[i-1];
		}
		output = (net > DEFAULT_FILTER_LIMIT)? 1:0;
		
		return output;
	}
	
	public int getLastOutput() {
		return output;
	}
	
	private void checkIndexWeight(int indexWeight) {
		if (!(0<=indexWeight && indexWeight <= this.inCount)) {
			throw new IllegalArgumentException("Index Weight out of bounds.");
		}
	}
	
	public double getWeight(int indexWeight) {
		checkIndexWeight(indexWeight);
		return weights[indexWeight];
	}
	
	public void setWeight(int indexWeight, double value) {
		checkIndexWeight(indexWeight);
		weights[indexWeight] = value;		
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}
}
