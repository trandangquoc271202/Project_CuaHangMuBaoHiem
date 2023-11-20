package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DoRegister", value = "/doRegister")
public class DoRegister extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        String username = request.getParameter("username").toLowerCase().trim();
        String password = request.getParameter("password").trim();
        String confirm_pw = request.getParameter("confirm_pw").trim();
        try {
            if (name == null || name == "" || email == null || email == "" || username == null || username == "" || password == null || password == "" || confirm_pw == null || confirm_pw == "") {
                request.setAttribute("error", "Nguời dùng phải nhập đầy đủ thông tin đăng ký.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else if (CustomerService.emailValidate(email) == false) {
                request.setAttribute("error", "Người dùng nhập email không hợp lệ!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else if (CustomerService.pwValidate(password, confirm_pw) == false) {
                request.setAttribute("error", "Mật khẩu xác nhận không trùng với mật khẩu!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else if (CustomerService.checkEmail(email) == true) {
                request.setAttribute("error", "Email đã có người sử dụng!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else if (CustomerService.checkUsername(username) == true) {
                request.setAttribute("error", "Tên đăng nhập đã có người sử dụng!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                CustomerService.addCustomer(username, CustomerService.toMD5(password), name, email);
                request.setAttribute("success", "Đăng ký thành công.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
