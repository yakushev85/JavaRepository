package com.yakushev.ie.siles.datamodel.reaction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WadCustomReaction extends CustomReaction {
    public WadCustomReaction(Reaction creaction) {
        super(creaction);
    }

    @Override
    public String getCustomAnswer(String msg) {
        try {
            return getSummaryForCurrentDay();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    private String getSummaryForCurrentDay() throws IOException {
        Document doc = Jsoup.connect(URL_WIKI).get();
        Elements mainWad = doc.select("#main-itd");
        Elements titleWad = mainWad.select("h2");
        Elements preWad = mainWad.select("p");

        return titleWad.text() + ". " + preWad.text() + '.';
    }
}
