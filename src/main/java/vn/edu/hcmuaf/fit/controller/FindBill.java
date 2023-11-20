package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Bill;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "find-bill", value = "/find-bill")
public class FindBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_bill = request.getParameter("text");
        List<Bill> list = new ArrayList<Bill>();
        try {
            list.add(ProductService.getBill(id_bill));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("list",list);
        long sales = 0;
        int count = 0;
        request.setAttribute("sales",sales);
        request.setAttribute("count",count);
        request.setAttribute("error",null);
        request.getRequestDispatcher("bill_manager.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
