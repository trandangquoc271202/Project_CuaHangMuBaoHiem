package vn.edu.hcmuaf.fit.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DoBillCustomer", value = "/BillCustomer")
public class DoBillCustomer  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        if (username == null) {
            response.sendRedirect("/Project_CuaHangMuBaoHiem_war/Login");
        } else {
            request.getRequestDispatcher("bill_customer.jsp").forward(request,response);
        }
    }
}
