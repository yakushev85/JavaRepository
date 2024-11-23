package org.oiakushev.neuralnetworks.general;

public interface Network {
    double[] execute(double[] inVector);
    void learn(boolean showInfo);
}
