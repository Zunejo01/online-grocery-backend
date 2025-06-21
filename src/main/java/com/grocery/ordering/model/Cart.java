package com.grocery.ordering.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getProductId() == productId);
    }

    public void updateItem(int productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductId() == productId) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    removeItem(productId);
                }
                return;
            }
        }
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public void clear() {
        items.clear();
    }
}
