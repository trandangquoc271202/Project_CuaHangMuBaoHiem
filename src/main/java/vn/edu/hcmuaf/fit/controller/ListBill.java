package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "list-bill", value = "/list-bill")
public class ListBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("list", ProductService.getListBill());
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
