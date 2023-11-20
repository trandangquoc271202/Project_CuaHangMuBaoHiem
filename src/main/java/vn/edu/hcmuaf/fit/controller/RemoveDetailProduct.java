package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveDetailProduct", value = "/RemoveDetailProduct")
public class RemoveDetailProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_dp= request.getParameter("id_dp");
        String id_product = request.getParameter("id");
        ProductService.removeDetailProduct(id_dp);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/DetailProduct?id=" + id_product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
