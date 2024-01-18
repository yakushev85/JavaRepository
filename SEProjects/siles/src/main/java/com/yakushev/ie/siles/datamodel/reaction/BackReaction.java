package com.yakushev.ie.siles.datamodel.reaction;

public class BackReaction {
    private String msg;

    public BackReaction(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String generateQuestion() {
        return "Як я повинна відповідати на '" + msg + "'?";
    }
}
