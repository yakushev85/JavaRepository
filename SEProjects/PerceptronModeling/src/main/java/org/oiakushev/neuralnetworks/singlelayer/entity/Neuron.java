package org.oiakushev.neuralnetworks.singlelayer.entity;

public class Neuron {
	public static final double DEFAULT_VALUE_KOEF = 0.1;
	public static final double DEFAULT_VALUE_OFFSET_WIGHT = 0.0;
	public static final double DEFAULT_CONST_A = 8.0;
	
	private int inCount;
	private double[] inVector;
	private double[] weights;
	private double net;
	private double output;
	private double default_value_weight;
	private double sigma;
	private double[] delta;
	
	public Neuron(int inCount, double default_value_weight) {
		this.inCount = inCount;
		this.default_value_weight = default_value_weight;
		weights = new double[inCount+1];
		weights[0] = DEFAULT_VALUE_OFFSET_WIGHT;
		for (int i=1;i<=this.inCount;i++) {
			weights[i] = this.default_value_weight * (0.1 + 0.8*Math.random());
		}

		delta = new double[inCount+1];
	}
	
	public Neuron(int inCount) {
		this(inCount, DEFAULT_VALUE_KOEF);
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
		net = weights[0];
		for (int i=1;i<=inCount;i++) {
			net += weights[i]*inVector[i-1];
		}

		output = 1 / (1 + Math.exp(-2 * DEFAULT_CONST_A * net));
		
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
}
