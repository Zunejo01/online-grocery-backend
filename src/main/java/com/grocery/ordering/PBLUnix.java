package com.grocery.ordering;

import com.grocery.ordering.dao.ProductDAO;
import com.grocery.ordering.model.Product;

import java.util.List;

public class PBLUnix {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println("  sort");
            System.out.println("  search <productName>");
            System.out.println("  replace <oldName> <newName>");
            return;
        }

        ProductDAO productDAO = new ProductDAO();
        String command = args[0];
        switch (command) {
            case "sort":
                List<Product> sortedProducts = productDAO.getAllProductsSortedByName();
                System.out.println("Products sorted by name:");
                for (Product p : sortedProducts) {
                    System.out.println(p.getProductId() + ": " + p.getProductName() + " - $" + p.getPrice());
                }
                break;
            case "search":
                if (args.length < 2) {
                    System.out.println("Usage: search <productName>");
                    return;
                }
                String searchName = args[1];
                List<Product> foundProducts = productDAO.searchProductsByName(searchName);
                if (foundProducts.isEmpty()) {
                    System.out.println("No products found with name: " + searchName);
                } else {
                    System.out.println("Found products:");
                    for (Product p : foundProducts) {
                        System.out.println(p.getProductId() + ": " + p.getProductName() + " - $" + p.getPrice());
                    }
                }
                break;
            case "replace":
                if (args.length < 3) {
                    System.out.println("Usage: replace <oldName> <newName>");
                    return;
                }
                String oldName = args[1];
                String newName = args[2];
                int updated = productDAO.replaceProductName(oldName, newName);
                System.out.println("Updated " + updated + " product(s) from '" + oldName + "' to '" + newName + "'.");
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }
} 