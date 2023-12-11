package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.model.Bill;
import vn.edu.hcmuaf.fit.model.Customer;

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


    public static void main(String[] args) throws SQLException {
        String status = "Đã chi";
        List<Bill> bills = NguyeMinhDuc.onePageBill(1);
        for(Bill b: bills){
            if(!NguyeMinhDuc.checkChangeBill(b.getId())){
                change_status_bill(b.getId(),status);
            }
        }
    }
}
