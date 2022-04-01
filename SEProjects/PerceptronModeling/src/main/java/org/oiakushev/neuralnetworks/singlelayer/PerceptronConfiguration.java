package org.oiakushev.neuralnetworks.singlelayer;

import org.oiakushev.neuralnetworks.general.TeachDataEntity;

import java.util.List;

public class PerceptronConfiguration {
    private int inCount;
    private int neuronCount;
    private List<TeachDataEntity> teachData;
    private double delta;
    private int maxLearningIterations;
    private double initialWeightValue;

    public int getInCount() {
        return inCount;
    }

    public void setInCount(int inCount) {
        this.inCount = inCount;
    }

    public int getNeuronCount() {
        return neuronCount;
    }

    public void setNeuronCount(int neuronCount) {
        this.neuronCount = neuronCount;
    }

    public List<TeachDataEntity> getTeachData() {
        return teachData;
    }

    public void setTeachData(List<TeachDataEntity> teachData) {
        this.teachData = teachData;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public int getMaxLearningIterations() {
        return maxLearningIterations;
    }

    public void setMaxLearningIterations(int maxLearningIterations) {
        this.maxLearningIterations = maxLearningIterations;
    }

    public double getInitialWeightValue() {
        return initialWeightValue;
    }

    public void setInitialWeightValue(double initialWeightValue) {
        this.initialWeightValue = initialWeightValue;
    }
}
