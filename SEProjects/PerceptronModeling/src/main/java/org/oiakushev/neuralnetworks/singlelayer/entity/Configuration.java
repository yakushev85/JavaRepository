package org.oiakushev.neuralnetworks.singlelayer.entity;

import java.util.List;

public class Configuration {
    private int inCount;
    private int neuronCount;
    private List<TeachDataEntity> teachData;
    private List<TeachDataEntity> examData;

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

    public List<TeachDataEntity> getExamData() {
        return examData;
    }

    public void setExamData(List<TeachDataEntity> examData) {
        this.examData = examData;
    }
}
