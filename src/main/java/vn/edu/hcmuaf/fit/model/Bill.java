package vn.edu.hcmuaf.fit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill {
    private String id;
    private Date date;
    private List<String> productList;
    private String status;
    private String id_cus;
    private String address;
    private String phone;

    public Bill(String id, Date date, List<String> list, String status, String id_cus, String address, String phone) {
        this.id = id;
        this.date = date;
        this.productList = list;
        this.status = status;
        this.id_cus = id_cus;
        this.address = address;
        this.phone = phone;
    }
    public Bill(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getProductList() {
        return productList;
    }

    public void setProductList(List<String> productList) {
        this.productList = productList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_cus() {
        return id_cus;
    }

    public void setId_cus(String id_cus) {
        this.id_cus = id_cus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", productList=" + productList +
                ", status='" + status + '\'' +
                ", id_cus='" + id_cus + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
