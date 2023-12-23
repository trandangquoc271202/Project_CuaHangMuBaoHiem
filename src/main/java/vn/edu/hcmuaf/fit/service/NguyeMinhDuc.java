package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.DigitalSignature.DigitalSignature;
import vn.edu.hcmuaf.fit.model.Bill;
import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Product;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NguyeMinhDuc {

    public static List<Bill> onePageBill(int index){
        List<Bill> list = new ArrayList<>();
        String  query = "select id from bill  limit ?, 10";
        try{
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement(query);
            ps.setInt(1, (index-1)*10);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(getBill(rs.getString("id")));
            }
        }catch (SQLException e){
        }
        return list;
    }

    public static Bill getBill(String id) throws SQLException {
        PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement("select id_dp from detail_bill where id_bill=?");
        prs.setString(1, id);
        List<String> list_product = new ArrayList<String>();
        ResultSet rs = prs.executeQuery();
        while (rs.next()) {
            list_product.add(rs.getString("id_dp"));
        }
        PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select id_customer, date, status, address, phone from bill where id=?");
        ps.setString(1, id);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return new Bill(id, resultSet.getDate("date"), list_product, resultSet.getString("status"), resultSet.getString("id_customer"), resultSet.getString("address"), resultSet.getString("phone"));
        }
        return null;
    }

    public static int getTotalBill(){
        String query = "select count(id) from bill";
        try{
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean checkChangeBill(String id_bill) {
        boolean result = true;
        try {
            int count = 0;
            String query = "select count(id) from employees_audit where id_bill = ?";
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement(query);
            ps.setString(1, id_bill);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
            if(count == 0){
                result = true;
            }else{
                result = false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void change_status_bill(String id, String status){
        try {
            String query = "update bill set status = ? where id = ? ";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(query);
            prs.setString(1,status);
            prs.setString(2,id);
            prs.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Customer getCustomer(String idc){
        Customer c = new Customer();
        try{
            PreparedStatement ps = DBConnect.getInstance().getConnection().prepareStatement("select name,email,phone,address,username,password,permission from customer where id_customer=?");
            ps.setString(1,idc);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setId_customer(idc);
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));
                c.setUsername(rs.getString("username"));
                c.setPassword(rs.getString("password"));
                c.setPermission(Integer.valueOf(rs.getString("permission")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return c;
    }

    public static Cart billToCart(String id_bill){
        Cart cart = new Cart();
           try{
               String queryListProduct = "select id_dp from detail_bill where id_bill = ?";
               PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(queryListProduct);
               prs.setString(1,id_bill);
               ResultSet rs = prs.executeQuery();
               List<String> list = new ArrayList<String>();
               while(rs.next()){
                   list.add(rs.getString(1));
               }
               for(String s: list){
                   Product p = getProduct(id_product(s),getSize(s),getColor(s));
                    cart.putQuantity(p);
               }

           }catch (SQLException e){
               e.printStackTrace();
           }

        cart.setQuantity(getquantity(id_bill));
        cart.setTotal(getTotal(id_bill));
        return cart;
    }

    public static boolean check_identify(String id_bill){
        boolean result = false;
        String name = getName(id_bill);
        String phone = getPhone(id_bill);
        String address = getAdress(id_bill);
        try{
            String public_key = "";
            String digital_signature = "";
            String query = "select public_key, digital_signature, id_customer from bill where id = ?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(query);
            prs.setString(1,id_bill);
            ResultSet rs = prs.executeQuery();
            if (rs.next()){
                public_key += getPublicKeybyId(rs.getString("public_key"));
                digital_signature += rs.getString("digital_signature");
            }
            if(!digital_signature.equals("")){
                DigitalSignature dig = new DigitalSignature();
                String infoBill = getInformationOrder(name,phone,address,id_bill);
                String encriptBill = dig.hashString(infoBill);
                String sign_electric = dig.decryptRSA(digital_signature,dig.importKeyPublic(public_key));
                if(encriptBill.equals(sign_electric)){
                    result = true;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getColor(String id_dp){
        String color = "";
        try {
            String sql = "select color from detail_product where id_dp =?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(sql);
            prs.setString(1,id_dp);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                color = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return color;
    }

    public static String getSize(String id_dp){
        String size = "";
        try {
            String sql = "select size from detail_product where id_dp =?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(sql);
            prs.setString(1,id_dp);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                size = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return size;
    }

    public static int getquantity(String id_dp){
        int quantity = 0;
        try {
            String sql = "select quantity from bill where id =?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(sql);
            prs.setString(1,id_dp);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return quantity;
    }

    public static long getTotal(String id_dp){
        long total = 0;
        try {
            String sql = "select total from bill where id =?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(sql);
            prs.setString(1,id_dp);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                total = (long)rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return total;
    }

    public static String id_product(String id_dp){
        String id_p = "";
        try{
            String queryListProduct = "select id_product from detail_product where id_dp = ?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(queryListProduct);
            prs.setString(1,id_dp);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                id_p+=rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id_p;
    }

    public static Product getProduct(String idp,String size,String color) {
        Product p = new Product();
        try {
            p.setId(idp);
            p.setName(ProductService.getname(idp));
            p.setPrice(ProductService.getprice(idp));
            p.setBrand(ProductService.getbrand(idp));
            p.setType(ProductService.gettype(idp));
            p.setDiscount(ProductService.getdiscount(idp));
            p.setImg(ProductService.getimg(idp));
            p.setStar(ProductService.getstar(idp));
            p.setAmount(ProductService.getamount(idp));
            p.setComment(ProductService.getcomment(idp));
            p.setRelease(ProductService.getrelease(idp));
            p.setDecrispe(ProductService.getdecrispe(idp));
            p.setDetail(ProductService.getDetail(idp,size,color));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return p;
    }

    public static String getPublicKeybyId(String id){
        String publickey = "";
        try{
            String queryListProduct = "select public_key from public_key where id = ?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(queryListProduct);
            prs.setString(1,id);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                publickey+=rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return publickey;
    }

    public static String getInformationOrder(String username, String phoneNumber, String address, String id_bill) {
        Cart cart = billToCart(id_bill);
        long totalCost = 0;
        String result = "";
        result += username + "\n" + phoneNumber + "\n" + address + "\n";
        Product product = null;
        for (String key : cart.getCart().keySet()) {
            product = cart.getCart().get(key);
            result += product.getId() + " " + product.getDetail().get(0).getId() +
                    " " + product.getDetail().get(0).getSize() +
                    " " + product.getDetail().get(0).getColor() + " "
                    + product.getName() + " " + getQuanlityinDetailBill(id_bill,product.getDetail().get(0).getId()) + " " + (product.getPrice() - (product.getDiscount() * product.getPrice())) + "\n";
        }
        result += cart.getTotal();
        return result;
    }

    public static int getQuanlityinDetailBill(String idBill, String id_dp){
        int quantity = 0;
        try {
            String query = "select quantity from detail_bill where id_bill =? and id_dp =?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(query);
            prs.setString(1,idBill);
            prs.setString(2,id_dp);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return quantity;
    }
    public static String getName(String id_bill){
        String name = "";
        try {
            String sql = "select name from bill where id =?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(sql);
            prs.setString(1,id_bill);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                name = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return name;
    }

    public static String getPhone(String id_bill){
        String phone = "";
        try {
            String sql = "select phone from bill where id =?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(sql);
            prs.setString(1,id_bill);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                phone = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return phone;
    }
    public static String getAdress(String id_bill){
        String address = "";
        try {
            String sql = "select address from bill where id =?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(sql);
            prs.setString(1,id_bill);
            ResultSet rs = prs.executeQuery();
            if(rs.next()){
                address = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return address;
    }


    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        String id = "47-56-16-23-DECEMBER-2023";
        System.out.println(check_identify(id));

    }
}
