package vn.edu.hcmuaf.fit.model;

import java.util.LinkedList;
import java.util.List;

public class DetailProduct {
    private String id;
    private String size;
    private String color;
    private int quantity;


    public DetailProduct(String id, String size, String color, int quantity) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId(){
        return id;
    }

    @Override
    public String toString() {
        return "DetailProduct{" +
                "id='" + id + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
