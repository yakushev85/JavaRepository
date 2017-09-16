package com.yakushev.lib.xpath.annotationhelper;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MainTestRun {

    @Test
    public void basicTest() throws IOException, SAXException, ParserConfigurationException,
            IllegalAccessException, XPathExpressionException, InstantiationException {
        File file = new File(getClass().getClassLoader().getResource("test.xml").getFile());
        XPathValueExtractor xPathValueExtractor = new XPathValueExtractor(new FileInputStream(file));
        TestXpath testXpath = xPathValueExtractor.createClass(TestXpath.class);

        System.out.println("[basicTest] Created object: "+testXpath);

        assertEquals(testXpath.getName().equals("Pen") &&
                testXpath.getPrice().equals("1.0"), true);
    }
}
