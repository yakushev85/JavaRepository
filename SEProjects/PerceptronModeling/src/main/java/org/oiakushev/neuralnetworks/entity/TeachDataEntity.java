package org.oiakushev.neuralnetworks.entity;

public class TeachDataEntity {
	private int[] vector;
	private int[] output;
	
	public TeachDataEntity(int[] vector, int[] output) {
		this.vector = vector;
		this.output = output;
	}
	
	public int[] getVector() {
		return vector;
	}
	
	public int[] getOutput() {
		return output;
	}
}
