package vn.edu.hcmuaf.fit.service;

import org.apache.commons.codec.digest.DigestUtils;
import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.model.Customer;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class CustomerService {
    public static List<Customer> getData() throws SQLException {
        List<Customer> list = new ArrayList<>();

        return list;
    }

    public static String toMD5(String password) {
        return DigestUtils.md5Hex(password).toLowerCase();
    }

    public static String GetKey() {
        StringBuilder sb = new StringBuilder("ct_");
        sb.append(LocalDateTime.now());
        return sb.toString();
    }

    public static String GetRandom() {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        sb.append(rd.nextLong(100000, 999999));
        return sb.toString();
    }

    public static void changePassword(String username, String pass_old, String pass_new) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username, password from customer where username = ? and password = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, pass_old);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            StringBuilder sb = new StringBuilder("Xin chào " + rs.getString("username") + ", \n");
            sb.append("Mật khẩu của bạn đã được thay đổi thành công vào " + LocalDate.now() + " lúc " + LocalTime.now() + ". \n\n\n");
            sb.append("Trân trọng cảm ơn! \n");
            sb.append("Đội ngũ bảo mật HelmetShop.");
            MailService.sendMail(rs.getString("email"), "Thay đổi mật khẩu - HelmetShop", sb.toString());
            String change = "update customer set password = '" + pass_new + "' where username = '" + username + "';";
            pre.executeUpdate(change);
        }
    }

    public static void addCustomer(String username, String password, String name, String email) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
//        String sql = "insert into customer values ('" + GetKey() + "'," + "'" + name + "'," + "'" + email + "', null," + "null,'" + username + "'," + "'" + password + "',0,1," + "'" + LocalDateTime.now() + "')";
        String sql = "insert into customer values ('" + GetKey() + "','" + name + "','" + email + "', null,null,'" + username + "','" + password + "',0,1,'" + LocalDateTime.now() + "')";
        dbConnect.get().executeUpdate(sql);
    }

    public static void resetPassword(String email) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        String password = GetRandom();
        String sql = "select email, password from customer where email = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, email);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
//            String content = "Xin chào " + rs.getString("username") + ", \n" + "Chúng tôi đã nhận được thông báo cấp lại mật khẩu từ bạn. " + password + " là mật khẩu đặt lại HelmetShop của bạn.";
//            sb.append("Mật khẩu của bạn đã được thay đổi vào " + LocalDate.now() + " lúc " + LocalTime.now() +". \n");
            StringBuilder sb = new StringBuilder("Xin chào " + rs.getString("username") + ", \n");
            sb.append("Chúng tôi đã nhận được thông báo cấp lại mật khẩu từ bạn. ");
            sb.append(password + " là mật khẩu đặt lại HelmetShop của bạn. \n\n\n");
            sb.append("Trân trọng cảm ơn! \n");
            sb.append("Đội ngũ bảo mật HelmetShop.");
            MailService.sendMail(email, "Đặt lại mật khẩu - HelmetShop", sb.toString());
            String reset = "update customer set password = '" + toMD5(password) + "' where email = '" + email + "';";
            pre.executeUpdate(reset);
        }
    }

    public static Customer customer(String username) throws SQLException {
        Customer customer = null;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select * from customer where username = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            customer = new Customer(rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), Integer.parseInt(rs.getString("permission")));
        }
        return customer;
    }


    public static boolean checkLogin(String username, String password) throws SQLException {
        boolean isLogin = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username, password from customer where username = ? and password = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, password);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            isLogin = true;
        }
        return isLogin;
    }

    public static boolean checkUsername(String username) throws SQLException {
        boolean isUsername = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username from customer where username = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            isUsername = true;
        }
        return isUsername;
    }

    public static boolean checkEmail(String email) throws SQLException {
        boolean isEmail = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select email from customer where email = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, email);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            isEmail = true;
        }
        return isEmail;
    }

    public static boolean emailValidate(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

    public static boolean pwValidate(String password, String confirm_pw) {
        boolean isPassword = false;
        if (password.equals(confirm_pw)) {
            isPassword = true;
        }
        return isPassword;
    }

    public static int checkActive(String username) throws SQLException {
        int isActive = 0;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select username, active from customer where username = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            if (Integer.parseInt(rs.getString("active")) == 1) {
                isActive = 1;
            }
        }
        return isActive;
    }

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
//        System.out.println(emailValidate("@tran.duyn.han@gm.ail.com"));
//        System.out.println(pwValidate("nhandz", "nhandz"));
//        System.out.println(checkEmail("20130346@st.hcmuaf.edu.vn"));
//        addCustomer("nhandz", "123123", "iam", "123@gmail.com");
//        System.out.println(toMD5("123456"));
//        changePassword("tdn", "c4ca4238a0b923820dcc509a6f75849b", toMD5("nhandz"));
//        resetPassword("20130346@st.hcmuaf.edu.vn");
        System.out.println(checkActive("tdn"));
    }
}
