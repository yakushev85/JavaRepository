package com.yakushev.lib.xpath.annotationhelper;

import com.yakushev.lib.xpath.annotationhelper.XpathExtractValue;

public class TestXpath {
    @XpathExtractValue("/values/name")
    private String name;

    @XpathExtractValue("/values/price")
    private String price;

    public TestXpath() {}

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "TestXpath{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
