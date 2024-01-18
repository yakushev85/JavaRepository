package com.yakushev.ie.siles.datamodel.reaction;

import java.io.IOException;

public class NewsCustomReaction extends CustomReaction {
    public NewsCustomReaction(Reaction creaction) {
        super(creaction);
    }

    @Override
    public String getCustomAnswer(String msg) {
        try {
            return getSectionTextFromMainPage("#main-cur");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
