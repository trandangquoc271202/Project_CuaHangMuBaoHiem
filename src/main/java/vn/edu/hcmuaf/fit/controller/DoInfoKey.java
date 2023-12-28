package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;
import vn.edu.hcmuaf.fit.service.MailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet(name = "DoInfoKey", value = "/InfoKey")
public class DoInfoKey extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        if (username == null) {
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Login");
        } else {
            request.getRequestDispatcher("info_key.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String publicKey = request.getParameter("publicKey");
        try {
            String email = CustomerService.getEmail(publicKey);
            String confirmationCode = UUID.randomUUID().toString();
            LocalDateTime expiration = LocalDateTime.now().plusHours(2);
            String formLink = "http://localhost:8080/Project_CuaHangMuBaoHiem_war/displayForm?confirmationCode=" + confirmationCode;
            Customer cus = CustomerService.customerByEmail(email);
            CustomerService.addFormData(cus.getId_customer(), expiration, confirmationCode);
            StringBuilder sb = new StringBuilder("Xin chào " + cus.getName() + ", \n");
            sb.append("Chúng tôi nhận được thông báo bị lộ khóa riêng tư từ bạn. Link được gửi có hiệu lực trong 2 giờ, vui lòng truy cập vào link để thay đổi thông tin: " + formLink + "\n\n\n");
            sb.append("Trân trọng cảm ơn! \n");
            sb.append("Đội ngũ bảo mật HelmetShop.");

            MailService.sendMail(email, "Thay đổi khóa công khai và khóa riêng tư - Helmet Shop", sb.toString());
            request.setAttribute("success", "Link form thay đổi thông tin khóa đã được gửi vào email của bạn.");
            request.getRequestDispatcher("info_key.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
