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

@WebServlet(name = "DoResetKey", value = "/ResetKey")
public class DoResetKey extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String confirmationCode = (String) session.getAttribute("confirmationCode");
        if (confirmationCode == null) {
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
        } else {
            request.getRequestDispatcher("reset_key.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String public_key = request.getParameter("public_key").trim();
        String private_key = request.getParameter("private_key").trim();
        HttpSession session = request.getSession();
        String confirmationCode = (String) session.getAttribute("confirmationCode");
        String id_customer = (String) session.getAttribute("id_customer");
        if (public_key == null || public_key == "" || private_key == null || private_key =="") {
            request.setAttribute("error", "Nguời dùng phải nhập đầy đủ thông tin đăng ký.");
            request.getRequestDispatcher("reset_key.jsp").forward(request, response);
        } else {
            try {
                String public_key_old = CustomerService.getPublicKey(id_customer);
                CustomerService.resetFormData(confirmationCode);
                CustomerService.resetKey(id_customer, public_key_old, public_key);
                session.setAttribute("confirmationCode", null);
                request.setAttribute("success", "Thay đổi khóa thành công.");
                request.getRequestDispatcher("info_key.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
