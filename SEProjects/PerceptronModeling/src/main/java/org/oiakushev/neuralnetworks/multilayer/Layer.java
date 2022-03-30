package org.oiakushev.neuralnetworks.multilayer;

import org.oiakushev.neuralnetworks.singlelayer.entity.Neuron;

import java.util.ArrayList;

public class Layer {
    private final ArrayList<Neuron> neurons = new ArrayList<>();

    public Layer(int inCount, int neuronLayerCount) {
        for (int i=0;i<neuronLayerCount;i++) {
            neurons.add(new Neuron(inCount));
        }
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }
}
