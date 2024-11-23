package org.oiakushev.neuralnetworks.general;

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
