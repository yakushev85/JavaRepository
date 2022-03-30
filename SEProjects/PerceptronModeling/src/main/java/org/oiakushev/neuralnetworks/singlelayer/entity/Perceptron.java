package org.oiakushev.neuralnetworks.singlelayer.entity;

public class Perceptron {
	private int inCount;
	private int neuronCount;
	private Neuron[] network;
	private TeachDataEntity[] teachData;
	
	public Perceptron(int inCount, int neuronCount) {
		this.inCount = inCount;
		this.neuronCount = neuronCount;
		
		network = new Neuron[neuronCount];
		for (int i=0;i<neuronCount;i++) {
			network[i] = new Neuron(inCount);
		}
	}
	
	public Perceptron(int inCount) {
		this(inCount, inCount);
	}	
	
	public void setDataForTeaching(TeachDataEntity[] teachData) {
		this.teachData = teachData;
	}
	
	public TeachDataEntity[] getDataForTeaching() {
		return teachData;
	}
	
	public Neuron[] getNetwork() {
		return network;
	}

	public int getInCount() {
		return inCount;
	}

	public int getNeuronCount() {
		return neuronCount;
	}
	
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
			result.append("Neuron "+(i+1)+" { ");
			
			for (int j=0;j<=inCount;j++) {
				result.append("w"+j+":"+network[i].getWeight(j)+", ");
			}
			result.append("}\n");
		}
		
		return result.toString();
	}
}
