package com.iakushev.tetris;

import java.io.Serializable;

public class DataUserScore implements Serializable {
    private final static String DEFAULT_NAME = "NONAME";

    private String playerName;
    private int playerScore;

    DataUserScore(String name, int scr) {
        if (name != null) playerName = ((!name.trim().isEmpty())) ? name : DEFAULT_NAME;
        playerScore = scr;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }
}
