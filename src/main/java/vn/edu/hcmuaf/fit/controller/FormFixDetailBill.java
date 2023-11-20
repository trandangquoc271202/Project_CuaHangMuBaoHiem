package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "form-fix_detail-bill", value = "/form-fix-detail-bill")
public class FormFixDetailBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_bill = request.getParameter("id_bill");
        try {
            request.setAttribute("bill", ProductService.getBill(id_bill));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String id_dp = request.getParameter("id_dp");
        request.getRequestDispatcher("fix_bill.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
