package vn.edu.hcmuaf.fit.model;

import vn.edu.hcmuaf.fit.service.ProductService;

import java.security.Key;
import java.sql.SQLException;
import java.util.*;

public class Product{
    private String id;
    private String name;
    private String brand;
    private long price;

    private String type;
    private double discount;
    private List<ImageProduct> img;
    private double star;
    private int amount;
    private Map<String, List<String>> comment;

    private String decrispe;
    private Date release;
    private List<DetailProduct> detail;
    private int quantity;

    public Product() {
    }

    public Product(String id, String name, long price, String brand, String type, double discount, double star, int amount, String decrispe, Date release) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.type = type;
        this.discount = discount;
        this.img = new ArrayList<ImageProduct>();
        this.star = star;
        this.amount = amount;
        this.comment = new HashMap<String, List<String>>();
        this.decrispe =decrispe;
        this.release = release;
        this.detail = new ArrayList<DetailProduct>();
    }

    public String getId() {
        return this.id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<ImageProduct> getImg() {
        return img;
    }

    public void setImg(List<ImageProduct> img) {
        this.img = img;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Map<String, List<String>> getComment() {
        return comment;
    }

    public void setComment(Map<String,List<String>> comment) {
        this.comment = comment;
    }

    public String getDecrispe() {
        return decrispe;
    }

    public void setDecrispe(String decrispe) {
        this.decrispe = decrispe;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public List<DetailProduct> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailProduct> detail) {
        this.detail = detail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", discount=" + discount +
                ", img=" + img +
                ", star=" + star +
                ", amount=" + amount +
                ", comment=" + comment +
                ", decrispe='" + decrispe + '\'' +
                ", release=" + release +
                ", detail=" + detail +
                '}';
    }
    public int sumQuantity() {
        int sum = 0;
        for(DetailProduct dp : detail){
            sum+= dp.getQuantity();
        }
        return sum;
    }
    public String getKey(){
        return this.id+""+this.getDetail().get(0).getId();
    }
    public List<String> getListSize(){
        List<String> list = new LinkedList<String>();
        for(DetailProduct size : getDetail()){
            if(!list.contains(size.getSize())){
                list.add(size.getSize());
            }
        }
        return list;
    }
    public List<String> getListColor(){
        List<String> list = new LinkedList<String>();
        for(DetailProduct size : getDetail()){
            if(!list.contains(size.getColor())){
                list.add(size.getColor());
            }
        }
        return list;
    }
    public int getQuantitySizeColor(String size,String color){
        int sum = 0;
        for(DetailProduct dp : getDetail()){
            if(dp.getSize().equalsIgnoreCase(size) && dp.getColor().equalsIgnoreCase(color)){
                sum = dp.getQuantity();
            }
        }
        return sum;
    }
    public int getQuantitySize(String size){
        int sum = 0;
        for(DetailProduct dp : getDetail()){
            if(dp.getSize().equalsIgnoreCase(size)){
                sum += dp.getQuantity();
            }
        }
        return sum;
    }
}
