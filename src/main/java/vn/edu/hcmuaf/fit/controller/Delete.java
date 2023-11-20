package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Cart;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Delete", value = "/Delete")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("delete");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.remove(id);
        request.getSession().setAttribute("cart",cart);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ListProductInCart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
