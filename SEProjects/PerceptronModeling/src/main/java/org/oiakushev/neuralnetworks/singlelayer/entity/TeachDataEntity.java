package org.oiakushev.neuralnetworks.singlelayer.entity;

public class TeachDataEntity {
	private final double[] vector;
	private final double[] output;
	
	public TeachDataEntity(double[] vector, double[] output) {
		this.vector = vector;
		this.output = output;
	}
	
	public double[] getVector() {
		return vector;
	}
	
	public double[] getOutput() {
		return output;
	}
}
