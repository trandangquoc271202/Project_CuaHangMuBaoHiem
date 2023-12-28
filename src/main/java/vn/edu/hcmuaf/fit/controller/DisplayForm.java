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

@WebServlet(name = "DisplayForm", value = "/displayForm")
public class DisplayForm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String confirmationCode = request.getParameter("confirmationCode");
        HttpSession session = request.getSession();
        session.setAttribute("confirmationCode", confirmationCode);
        try {
            if (CustomerService.isValidConfirmationCode(confirmationCode)) {
                String idCus = CustomerService.getIdCustomerByCf_Code(confirmationCode);
                Customer customer = CustomerService.customerById(idCus);
                String username = customer.getUsername();
                String name = customer.getName();
                int permission = customer.getPermission();
                session.setAttribute("id_customer", idCus);
                session.setAttribute("tendangnhap", username);
                session.setAttribute("tennguoidung", name);
                session.setAttribute("permission", permission);
                request.getRequestDispatcher("reset_key.jsp").forward(request,response);
            } else {
                session.setAttribute("confirmationCode", null);
                response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Home");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
