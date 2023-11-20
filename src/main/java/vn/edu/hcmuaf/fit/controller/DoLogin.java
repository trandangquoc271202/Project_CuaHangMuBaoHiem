package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DoLogin", value = "/doLogin")
public class DoLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username").toLowerCase().trim();
        String password = request.getParameter("password").trim();
        HttpSession session = request.getSession();
        try {
            if (username == null || username == "" || password == null || password == "") {
                request.setAttribute("error", "Người dùng không được để trống Tên đăng nhập hoặc Mật khẩu.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else if(CustomerService.checkActive(username) == 0) {
                request.setAttribute("error", "Tài khoản đã bị khóa.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else if (CustomerService.checkLogin(username, CustomerService.toMD5(password)) == true) {
                session.setAttribute("tendangnhap", username);
                Customer customer = CustomerService.customer(username);
                if (customer.getPermission() == 0){
                    response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
                }else {

                    response.sendRedirect("ManageProduct");
                }
            } else {
                request.setAttribute("error", "Người dùng nhập không đúng Tên đăng nhập hoặc Mật khẩu.");
                request.getRequestDispatcher("login.jsp").forward(request, response);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
