package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DoResetKey", value = "/doResetKey")
public class DoResetKey extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String public_key = request.getParameter("public_key").trim();
        String private_key = request.getParameter("private_key").trim();
        if (public_key == null || public_key == "" || private_key == null || private_key =="") {
            request.setAttribute("error", "Nguời dùng phải nhập đầy đủ thông tin đăng ký.");
            request.getRequestDispatcher("reset_key.jsp").forward(request, response);
        } else {
            String s = (String) request.getSession().getAttribute("tendangnhap");
            Customer customer = null;
            String public_key_old = "";
            String idCus ="";
            try {
                customer = CustomerService.customer(s);
                idCus = customer.getId_customer();
                public_key_old = CustomerService.getPublicKey(idCus);
                CustomerService.resetKey(idCus, public_key_old, public_key, private_key);
                request.setAttribute("success", "Thay đổi khóa thành công.");
                request.getRequestDispatcher("info_key.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
