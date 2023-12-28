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

@WebServlet(name = "DoResetPassword", value = "/ResetPassword")
public class DoResetPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        if (username != null) {
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
        } else {
            request.getRequestDispatcher("forgot-password.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        try {
            if (CustomerService.checkEmail(email)==false){
                request.setAttribute("error", "Email chưa được sử dụng để đăng ký tài khoản.");
                request.getRequestDispatcher("forgot-password.jsp").forward(request,response);
            } else {
                request.setAttribute("success","Đặt lại mật khẩu hành công! Vui lòng kiểm tra email!");
                CustomerService.resetPassword(email);
                request.getRequestDispatcher("forgot-password.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
