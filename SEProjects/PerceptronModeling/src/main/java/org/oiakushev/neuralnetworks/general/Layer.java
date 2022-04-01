package org.oiakushev.neuralnetworks.general;

import java.util.ArrayList;

public class Layer {
    private final ArrayList<Neuron> neurons = new ArrayList<>();

    public Layer(int inCount, int neuronLayerCount, double initialWeightValue) {
        for (int i=0;i<neuronLayerCount;i++) {
            neurons.add(new Neuron(inCount, initialWeightValue));
        }
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }
}
