package org.oiakushev.neuralnetworks.multilayer;

import org.oiakushev.neuralnetworks.singlelayer.entity.TeachDataEntity;

import java.util.List;

public class MultiNetConfiguration {
    private int inCount;
    private int[] neuronCounts;
    private List<TeachDataEntity> teachData;
    private List<TeachDataEntity> examData;

    public int getInCount() {
        return inCount;
    }

    public void setInCount(int inCount) {
        this.inCount = inCount;
    }

    public int[] getNeuronCounts() {
        return neuronCounts;
    }

    public void setNeuronCounts(int[] neuronCounts) {
        this.neuronCounts = neuronCounts;
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
