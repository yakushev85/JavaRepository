package org.yakushev.shopwebapp.util;

import org.yakushev.shopwebapp.model.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultValues {
    public static List<Product> getDefaultProducts() {
        ArrayList<Product> defaultProducts = new ArrayList<>();
        defaultProducts.add(new Product("Standard", 100.00f, new Date(), "Admin"));
        defaultProducts.add(new Product("Premium", 250.99f, new Date(), "Admin"));
        defaultProducts.add(new Product("Ultra", 345.45f, new Date(), "Admin"));
        return defaultProducts;
    }
}
