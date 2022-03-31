package org.oiakushev.neuralnetworks.multilayer;

import org.oiakushev.neuralnetworks.singlelayer.entity.Neuron;
import org.oiakushev.neuralnetworks.singlelayer.entity.TeachDataEntity;

import java.util.ArrayList;
import java.util.List;

public class MultiNetwork {
    public final int MAX_LEARNING_ITERATIONS = 100000;
    public static final double ALPHA_VALUE = 0.5;
    public static final double SPEED_VALUE = 0.5;

    private final MultiNetConfiguration configuration;
    private final ArrayList<Layer> layers;

    public MultiNetwork(MultiNetConfiguration configuration) {
        this.configuration = configuration;

        int[] neuronCounts = configuration.getNeuronCounts();
        int preIn = configuration.getInCount();

        this.layers = new ArrayList<>();

        for (int neuronCount : neuronCounts) {
            Layer layer = new Layer(preIn, neuronCount);
            layers.add(layer);
            preIn = neuronCount;
        }
    }

    public double[] execute(double[] inVector) {
        double[] layerInVector = inVector;

        for (Layer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                neuron.setInVector(layerInVector);
                neuron.generateOutput();
            }

            layerInVector = new double[layer.getNeurons().size()];
            for (int i=0;i<layer.getNeurons().size();i++) {
                layerInVector[i] = layer.getNeurons().get(i).getOutput();
            }
        }

        return layerInVector;
    }

    @Override
    public String toString() {
        return "MultiNetwork{" +
                "layers=" + layers +
                '}';
    }

    public void learn() {
        int currentIteration = 1;
        double outputError = Double.MAX_VALUE - 1;

        while (outputError > 0 && currentIteration < MAX_LEARNING_ITERATIONS) {
            outputError = iteration();

            System.out.println(currentIteration + ". error = " + outputError);

            currentIteration++;
        }

        System.out.println("MultiNetwork.learn finished with error = " + outputError +
                " iteration = " + currentIteration);
    }

    private double iteration() {
        double totalErrorSum = 0.0;
        List<TeachDataEntity> learningData = configuration.getTeachData();

        for (TeachDataEntity teachData : learningData) {
            // step 1 execute net with teach data
            double[] actualOutput = execute(teachData.getVector());
            double[] expectedOutput = teachData.getOutput();

            // step 2 generate sigma for last layer and update errorSum
            for (int j=0;j<actualOutput.length;j++) {
                layers.get(layers.size()-1).getNeurons().get(j).setSigma(
                        -1.0*actualOutput[j]*(1-actualOutput[j])*(expectedOutput[j]-actualOutput[j])
                );

                totalErrorSum += (Math.abs(expectedOutput[j]-actualOutput[j]) > 0.5)? 1.0 : 0.0;
            }

            // step 3 generate sigma for other layers
            for (int i=layers.size()-2;i>=0;i--) {
                for (int j=0;j<layers.get(i).getNeurons().size();j++) {
                    double currentSigma = 0.0;
                    double output = layers.get(i).getNeurons().get(j).getOutput();
                    double preSigma = output*(1-output);

                    for (int k=0;k<layers.get(i+1).getNeurons().size();k++) {
                        currentSigma += layers.get(i+1).getNeurons().get(k).getWeight(j) *
                                layers.get(i+1).getNeurons().get(k).getSigma();
                    }

                    currentSigma = preSigma * currentSigma;

                    layers.get(i).getNeurons().get(j).setSigma(currentSigma);
                }
            }

            // step 4.1 generate delta
            for (int k=0;k<layers.size();k++) {
                double[] output;
                if (k == 0) {
                    output = teachData.getVector();
                } else {
                    output = new double[layers.get(k-1).getNeurons().size()];

                    for (int l=0;l<layers.get(k-1).getNeurons().size();l++) {
                        output[l] = layers.get(k-1).getNeurons().get(l).getOutput();
                    }
                }

                for (int j=0;j<layers.get(k).getNeurons().size();j++) {
                    double[] delta = layers.get(k).getNeurons().get(j).getDelta();
                    double[] newDelta = new double[delta.length];

                    for (int i=0;i<delta.length;i++) {
                        newDelta[i] = ALPHA_VALUE*delta[i] +
                                (1-ALPHA_VALUE)*SPEED_VALUE*layers.get(k).getNeurons().get(j).getSigma()*output[i];
                    }

                    layers.get(k).getNeurons().get(j).setDelta(newDelta);
                }
            }

            // step 4.2 update weights
            for (Layer layer : layers) {
                for (Neuron neuron : layer.getNeurons()) {
                    double[] currentDelta = neuron.getDelta();

                    for (int i=0;i<neuron.getDelta().length;i++) {
                        neuron.setWeight(i, neuron.getWeight(i) - currentDelta[i]);
                    }
                }
            }
        }

        return totalErrorSum;
    }
}
