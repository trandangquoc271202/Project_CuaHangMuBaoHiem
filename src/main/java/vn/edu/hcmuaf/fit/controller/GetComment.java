package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.ProductService;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet(name = "getComment", value = "/get-comment")
public class GetComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idPro = request.getParameter("id_Pro");
        String mess = request.getParameter("mess");
        int star = Integer.parseInt(request.getParameter("star"));
        Customer customer = (Customer) request.getSession().getAttribute("tendangnhap");
        ProductService.addComment(customer.getId_customer(),idPro,mess,star);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/detail?id="+idPro);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
