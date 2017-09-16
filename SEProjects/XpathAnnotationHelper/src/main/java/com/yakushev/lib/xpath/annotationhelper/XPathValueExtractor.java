package com.yakushev.lib.xpath.annotationhelper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XPathValueExtractor {
    private Document xmlDocument;
    private XPath xPath;

    public XPathValueExtractor(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        xmlDocument = builder.parse(is);
        xPath =  XPathFactory.newInstance().newXPath();
    }

    public String getValueByXPath(String expression) throws XPathExpressionException {
        return xPath.compile(expression).evaluate(xmlDocument);
    }

    public <T> T createClass(Class<T> xpathClass) throws IllegalAccessException, InstantiationException, XPathExpressionException {
        Field[] fields = xpathClass.getDeclaredFields();
        T xpathObj = xpathClass.newInstance();

        for (Field field : fields) {
            XpathExtractValue extractValue = field.getAnnotation(XpathExtractValue.class);
            if (extractValue != null) {
                field.setAccessible(true);
                field.set(xpathObj, getValueByXPath(extractValue.value()));
            }
        }

        return xpathObj;
    }
}
