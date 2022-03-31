package org.oiakushev.neuralnetworks.singlelayer.entity;

public class Neuron {
	public static final double DEFAULT_VALUE_K = 0.1;
	public static final double DEFAULT_VALUE_OFFSET_WIGHT = 0.0;
	public static final double DEFAULT_CONST_A = 8.0;
	
	private final int inCount;
	private double[] inVector;
	private double[] weights;
	private double net;
	private double output;
	private double default_value_weight;
	private double sigma;
	private double[] delta;
	private double weightOffset;
	
	public Neuron(int inCount, double default_value_weight) {
		this.inCount = inCount;
		this.default_value_weight = default_value_weight;
		weights = new double[inCount];
		weightOffset = DEFAULT_VALUE_OFFSET_WIGHT;
		delta = new double[inCount];

		for (int i=0;i<this.inCount;i++) {
			weights[i] = this.default_value_weight * (0.1 + 0.8*Math.random());
		}
	}
	
	public Neuron(int inCount) {
		this(inCount, DEFAULT_VALUE_K);
	}
	
	public void setInVector(double[] inVector) {
		if (inVector.length != inCount) {
			throw new IllegalArgumentException("Input vector size doesn't match allowed size.");
		}
		
		this.inVector = inVector;
	}
	
	public double[] getInVector() {
		return this.inVector;
	}
	
	public double generateOutput() {
		net = weightOffset;
		for (int i=0;i<inCount;i++) {
			net += weights[i]*inVector[i];
		}

		output = 1 / (1 + Math.exp(-2 * DEFAULT_CONST_A * net));
		
		return output;
	}
	
	private void checkIndexWeight(int indexWeight) {
		if (!(0<=indexWeight && indexWeight < this.inCount)) {
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

	public double getNet() {
		return net;
	}

	public double getOutput() {
		return output;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public double[] getDelta() {
		return delta;
	}

	public void setDelta(double[] delta) {
		this.delta = delta;
	}

	public double getWeightOffset() {
		return weightOffset;
	}

	public void setWeightOffset(double weightOffset) {
		this.weightOffset = weightOffset;
	}
}
