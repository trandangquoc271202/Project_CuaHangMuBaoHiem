package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "list_customer", value = "/list-customer")
public class ListCustomer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> list = ProductService.getData_Customer();
        request.setAttribute("list",list);
        request.getRequestDispatcher("customer_manager.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
