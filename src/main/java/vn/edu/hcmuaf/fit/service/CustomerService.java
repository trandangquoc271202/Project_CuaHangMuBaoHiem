package vn.edu.hcmuaf.fit.service;

import org.apache.commons.codec.digest.DigestUtils;
import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.FormData;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        String sql = "select username, email, password, name from customer where username = ? and password = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, pass_old);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            StringBuilder sb = new StringBuilder("Xin chào " + rs.getString("name") + ", \n");
            sb.append("Mật khẩu của bạn đã được thay đổi thành công vào " + LocalDate.now() + " lúc " + LocalTime.now() + ". \n\n\n");
            sb.append("Trân trọng cảm ơn! \n");
            sb.append("Đội ngũ bảo mật HelmetShop.");
            MailService.sendMail(rs.getString("email"), "Thay đổi mật khẩu - HelmetShop", sb.toString());
            String change = "update customer set password = '" + pass_new + "' where username = '" + username + "';";
            pre.executeUpdate(change);
        }
    }

    public static void addCustomer(String username, String password, String name, String email, String publicKey) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        String idCustomer = GetKey();
        String sql = "insert into customer values ('" + idCustomer + "','" + name + "','" + email + "', null,null,'" + username + "','" + password + "',0,1,'" + LocalDateTime.now() + "')";
        int idSerial = 1;
        String getSerial = "select id from public_key order by id desc limit 1";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(getSerial);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            idSerial = rs.getInt("id") + 1;
        }
        String addPublicKey = "insert into public_key values ('" + idSerial + "','" + idCustomer + "','" + publicKey + "','" + LocalDateTime.now() + "','" + LocalDateTime.of(9999, 12, 31, 23, 59, 59) + "','" + "1" + "')";
        dbConnect.get().executeUpdate(sql);
        dbConnect.get().executeUpdate(addPublicKey);
    }

    public static void resetPassword(String email) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        String password = GetRandom();
        String sql = "select username, email, password, name from customer where email = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, email);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
//            String content = "Xin chào " + rs.getString("username") + ", \n" + "Chúng tôi đã nhận được thông báo cấp lại mật khẩu từ bạn. " + password + " là mật khẩu đặt lại HelmetShop của bạn.";
//            sb.append("Mật khẩu của bạn đã được thay đổi vào " + LocalDate.now() + " lúc " + LocalTime.now() +". \n");
            StringBuilder sb = new StringBuilder("Xin chào " + rs.getString("name") + ", \n");
            sb.append("Chúng tôi đã nhận được thông báo cấp lại mật khẩu từ bạn. ");
            sb.append("<b>" + password + "</b> là mật khẩu đặt lại HelmetShop của bạn. \n\n\n");
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
            customer = new Customer(rs.getString("id_customer"), rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), Integer.parseInt(rs.getString("permission")));
        }
        return customer;
    }

    public static Customer customerById(String id) throws SQLException {
        Customer customer = null;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select * from customer where id_customer = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, id);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            customer = new Customer(rs.getString("id_customer"), rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getString("username"), Integer.parseInt(rs.getString("permission")));
        }
        return customer;
    }

    public static Customer customerByEmail(String email) throws SQLException {
        Customer customer = null;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select * from customer where email = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, email);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            customer = new Customer(rs.getString("id_customer"), rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), Integer.parseInt(rs.getString("permission")));
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

    public static boolean checkPublicKey(String public_key) throws SQLException {
        boolean isPublicKey = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select public_key from public_key where public_key = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, public_key);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            isPublicKey = true;
        }
        return isPublicKey;
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

    public static String getIdCustomer(String username) throws SQLException {
        String id = "";
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select id_customer from customer where username = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, username);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            id = rs.getString("id_customer");
        }
        return id;
    }

    public static String getIdCustomerByCf_Code(String cf_code) throws SQLException {
        String id = "";
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select id_kh from formdata where cf_code = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, cf_code);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            id = rs.getString("id_kh");
        }
        return id;
    }

    public static String getPublicKey(String idCustomer) throws SQLException {
        String public_key = "";
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select * from public_key where id_kh = ? and status = 1";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, idCustomer);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            public_key += rs.getString("public_key");
        }
        return public_key;
    }

    public static String getEmail(String publicKey) throws SQLException {
        String email = "";
        String idCus = "";
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select id_kh from public_key where public_key = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, publicKey);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            idCus = rs.getString("id_kh");
        }
        String sqlE = "select email from customer where id_customer = ?";
        PreparedStatement p = dbConnect.getConnection().prepareStatement(sqlE);
        p.setString(1, idCus);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            email += r.getString("email");
        }
        return email;
    }

    public static void addFormData(String id_kh, LocalDateTime date, String cf_code) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        int idSerial = 1;
        String getSerial = "select Max(id) as maxId from formdata";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(getSerial);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            idSerial = rs.getInt("maxId") + 1;
        }
        String sql = "insert into formdata values ('" + idSerial + "','" + id_kh + "','" + date + "','" + cf_code + "')";
        dbConnect.get().executeUpdate(sql);
    }

    public static void resetFormData(String confirmationCode) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select * from formdata where cf_code = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, confirmationCode);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            String resetEx_time = "update formdata set ex_time = '" + LocalDateTime.now() + "' where cf_code = '" + confirmationCode + "'";
            pre.executeUpdate(resetEx_time);
        }
    }

    public static boolean isValidConfirmationCode(String confirmationCode) throws SQLException {
        boolean isValid = false;
        DBConnect dbConnect = DBConnect.getInstance();
        String sql = "select ex_time from formdata where cf_code = ?";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
        pre.setString(1, confirmationCode);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            Timestamp timestamp = rs.getTimestamp("ex_time");
            LocalDateTime ex_time = timestamp.toLocalDateTime();
            LocalDateTime current = LocalDateTime.now();
            if (ex_time.isAfter(current)) {
                isValid = true;
            }
        }
        return isValid;
    }

    public static void addPublic_key(String idCus, String public_key) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        int idSerial = 1;
        String getSerial = "select Max(id) as maxId from public_key";
        PreparedStatement pre = dbConnect.getConnection().prepareStatement(getSerial);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            idSerial = rs.getInt("maxId") + 1;
        }
        String sql = "insert into public_key values ('" + idSerial + "','" + idCus + "','" + public_key + "','" + LocalDateTime.now() + "','" + LocalDateTime.of(9999, 12, 31, 23, 59, 59) + "','" + "1" + "')";
        PreparedStatement pre2 = dbConnect.getConnection().prepareStatement(sql);
        pre2.executeUpdate(sql);
    }

    public static void resetKey(String idCus, String publicKey_old, String publicKey) throws SQLException {
        if (publicKey_old == "" || publicKey_old == null) {
            CustomerService.addPublic_key(idCus, publicKey);
        } else {
            DBConnect dbConnect = DBConnect.getInstance();
            String sql = "select * from public_key where public_key = ?";
            PreparedStatement pre = dbConnect.getConnection().prepareStatement(sql);
            pre.setString(1, publicKey_old);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                String resetEx_time = "update public_key set ee_date = '" + LocalDateTime.now() + "' where public_key = '" + publicKey_old + "'";
                String resetStatus = "update public_key set status = '" + 0 + "' where public_key = '" + publicKey_old + "'";
                pre.executeUpdate(resetEx_time);
                pre.executeUpdate(resetStatus);
                CustomerService.addPublic_key(idCus, publicKey);
            }
        }
    }

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
//        System.out.println(emailValidate("@tran.duyn.han@gm.ail.com"));
//        System.out.println(pwValidate("nhandz", "nhandz"));
//        System.out.println(checkEmail("20130346@st.hcmuaf.edu.vn"));
//        addCustomer("nhandz", "123123", "iam", "123@gmail.com");
//        System.out.println(toMD5("123456"));
//        changePassword("tdn", "c4ca4238a0b923820dcc509a6f75849b", toMD5("nhandz"));
//        resetPassword("20130346@st.hcmuaf.edu.vn");
//        System.out.println(checkActive("tdn"));
//        System.out.println(checkEmail("@gmail.com"));
//        System.out.println(getPublicKey("ad_nhan"));
        Customer customer = CustomerService.customer("qadmin");
        String public_key = CustomerService.getPublicKey(customer.getId_customer());
        System.out.println(public_key);
    }


}
