package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveImage", value = "/RemoveImage")
public class RemoveImage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_img= request.getParameter("id_img");
        String id_product = request.getParameter("id");
        ProductService.removeImage(id_img);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/DetailProduct?id=" + id_product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
