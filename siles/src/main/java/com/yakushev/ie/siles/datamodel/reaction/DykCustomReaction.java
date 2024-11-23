package com.yakushev.ie.siles.datamodel.reaction;

import java.io.IOException;

public class DykCustomReaction extends CustomReaction {
    public DykCustomReaction(Reaction creaction) {
        super(creaction);
    }

    @Override
    public String getCustomAnswer(String msg) {
        try {
            return getSectionTextFromMainPage("#news");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
