package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "fix-bill", value = "/fix-bill")
public class FixBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String status = request.getParameter("status");
        ProductService.updateBill(id,address,phone,status);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/list-bill");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
