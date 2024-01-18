package com.yakushev.ie.siles.datamodel;

import com.yakushev.ie.siles.datamodel.reaction.Reaction;
import com.yakushev.neuralnetworks.general.NetConfiguration;
import com.yakushev.neuralnetworks.general.TeachDataEntity;
import com.yakushev.neuralnetworks.multilayer.MultiNetwork;

import java.io.*;
import java.util.*;

public class NetworkModel {
    private final String fileDataName;
    private final HashSet<String> words;
    private List<Reaction> reactions;
    private MultiNetwork network;
    private List<TeachDataEntity> teachDataEntityList;

    public NetworkModel(String fileDataName) {
        this.fileDataName = fileDataName;
        words = new HashSet<>();
    }

    public void setLearningData(List<Reaction> reactionList) {
        reactions = reactionList;

        for (Reaction reaction : reactions) {
            for (String request : reaction.getPatternTags()) {
                words.addAll(getWords(request));
            }
        }

        int iReaction = 0;
        teachDataEntityList = new ArrayList<>();

        for (Reaction reaction : reactions) {
            teachDataEntityList.addAll(getTeachDataEntityFromReaction(reaction, iReaction));
            iReaction++;
        }
    }

    private List<TeachDataEntity> getTeachDataEntityFromReaction(Reaction reaction, int iReaction) {
        List<TeachDataEntity> result = new ArrayList<>();
        double[] output = new double[reactions.size()];

        for (int i=0;i<reactions.size();i++) {
            if (i == iReaction) {
                output[i] = 1;
            } else {
                output[i] = 0;
            }
        }

        for (String request : reaction.getPatternTags()) {
            double[] vector = getVectorFromMessage(request);

            result.add(new TeachDataEntity(vector, output));
        }

        return result;
    }

    private Set<String> getWords(String sentence) {
        Set<String> sentenceWords = new HashSet<>();

        String[] splitResults = sentence.trim().toLowerCase().split("[^а-я]");
        sentenceWords.addAll(Arrays.asList(splitResults));

        return sentenceWords;
    }

    public void learn() throws IOException {
        learn(teachDataEntityList, getInCount(), getNeuronCount());
        savePerceptronIntoFile();
    }

    private int getInCount() {
        return words.size();
    }

    private int getNeuronCount() {
        return reactions.size();
    }

    private void learn(List<TeachDataEntity> teachData, int inCount, int neuronCount) throws IOException {
        if (!isReadyForLearning()) {
            System.out.println("no data for learning!");
            return;
        }

        NetConfiguration configuration = new NetConfiguration();
        configuration.setInCount(inCount);
        configuration.setTeachData(teachData);
        configuration.setNeuronCounts(new int[] {2*neuronCount, neuronCount});
        configuration.setMaxLearningIterations(100000);
        configuration.setInitialWeightValue(0.1);
        configuration.setAlpha(0.5);
        configuration.setSpeed(0.1);

        network = new MultiNetwork(configuration);

        System.out.println(network);
        System.out.println("Learning...");
        network.learn(false);
    }

    public String exam(String msg) {
        if (!isReadyForExam()) {
            System.out.println("no data for exam!");
            return "";
        }

        double[] result = network.execute(getVectorFromMessage(msg));
        return convertExamResultToResponse(result);
    }

    private String convertExamResultToResponse(double[] result) {
        int resultsCount = 0;
        int indexResult = -1;

        for (int i=0;i<result.length;i++) {
            if (result[i] > 0.5) {
                indexResult = i;
                resultsCount++;
            }
        }

        if (resultsCount == 1) {
            return reactions.get(indexResult).getReaction();
        }

        return "";
    }

    private double[] getVectorFromMessage(String msg) {
        double[] vector = new double[words.size()];
        Set<String> wordsInRequest = getWords(msg);

        int i=0;
        for (String word : words) {
            if (wordsInRequest.contains(word)) {
                vector[i] = 1.0;
            } else {
                vector[i] = 0.0;
            }

            i++;
        }

        return vector;
    }

    public boolean isReadyForLearning() {
        return teachDataEntityList != null && !teachDataEntityList.isEmpty();
    }

    public boolean isReadyForExam() {
        return network != null;
    }

    public void loadPerceptronFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileDataName);

        try (fileInputStream; ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            network = (MultiNetwork) objectInputStream.readObject();
        }
    }

    private void savePerceptronIntoFile() throws IOException {
        if (!isReadyForExam()) {
            return;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(fileDataName);

        try (fileOutputStream; ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(network);
            objectOutputStream.flush();
        }
    }
}
