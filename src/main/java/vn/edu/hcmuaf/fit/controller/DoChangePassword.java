package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DoChangePassword", value = "/ChangePassword")
public class DoChangePassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        if (username == null) {
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Login");
        } else {
            request.getRequestDispatcher("change-password.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        String pass_old = request.getParameter("pass_old").trim();
        String password = request.getParameter("password").trim();
        String confirm_pw = request.getParameter("confirm_pw").trim();

        if (username == null || username == "") {
            request.setAttribute("error ", "Vui lòng đăng nhập để sử dụng chức năng này!");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        } else if (pass_old == null || pass_old == "" || password == null || password == "" || confirm_pw == null || confirm_pw == "") {
            request.setAttribute("error", "Người dùng chưa điền đầy đủ thông tin.");
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
        } else if (CustomerService.pwValidate(password, confirm_pw) == false) {
            request.setAttribute("error", "Mật khẩu xác nhận không trùng với mật khẩu mới.");
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
        } else {
            try {
                if (CustomerService.checkLogin(username, CustomerService.toMD5(pass_old)) == false) {
                    request.setAttribute("error", "Mật khẩu hiện tại không đúng.");
                    request.getRequestDispatcher("change-password.jsp").forward(request, response);
                } else {
                    CustomerService.changePassword(username, CustomerService.toMD5(pass_old), CustomerService.toMD5(password));
                    request.setAttribute("success", "Đổi mật khẩu thành công.");
                    request.getRequestDispatcher("change-password.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
