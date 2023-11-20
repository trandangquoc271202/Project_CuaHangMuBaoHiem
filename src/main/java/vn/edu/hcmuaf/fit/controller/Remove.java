package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Remove", value = "/Remove")
public class Remove extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
//        ProductService.removeProduct(id);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ManageProduct");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
