package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DisplayForm", value = "/displayForm")
public class DisplayForm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String confirmationCode = request.getParameter("confirmationCode");
        try {
            if (CustomerService.isValidConfirmationCode(confirmationCode)) {
                request.getRequestDispatcher("/Project_CuaHangMuBaoHiem_war/reset_key.jsp").forward(request,response);
            } else {
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
