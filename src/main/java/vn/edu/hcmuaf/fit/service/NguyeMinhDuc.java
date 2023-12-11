package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.DigitalSignature.DigitalSignature;
import vn.edu.hcmuaf.fit.model.Bill;
import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Product;

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
                    cart.put(ProductService.getDetailProduct(id_product(s)));
               }

           }catch (SQLException e){
               e.printStackTrace();
           }
        return cart;
    }

    public static boolean check_identify(String id_bill){
        boolean result = false;
        try{
            String public_key = "";
            String digital_signature = "";
            String id_customer = "";
            String username = "";
            String phonename = "";
            String address = "";
            String query = "select public_key, digital_signature, id_customer from bill where id = ?";
            PreparedStatement prs = DBConnect.getInstance().getConnection().prepareStatement(query);
            prs.setString(1,id_bill);
            ResultSet rs = prs.executeQuery();
            if (rs.next()){
                public_key += rs.getString("public_key");
                digital_signature += rs.getString("digital_signature");
                id_customer += rs.getString("id_customer");
                username = ProductService.getCustomer(id_customer).getUsername();
                phonename = ProductService.getCustomer(id_customer).getPhone();
                address = ProductService.getCustomer(id_customer).getAddress();
            }
            if(!digital_signature.equals("")){
                DigitalSignature dig = new DigitalSignature();
                String infoBill = dig.getInformationOrder(username,phonename,address,billToCart(id_bill));
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

    public static void main(String[] args) throws SQLException {
        System.out.println(check_identify("52-5-2-1-DECEMBER-2023"));
    }
}
