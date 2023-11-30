package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.DigitalSignature.DigitalSignature;
import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "CheckSignature", value = "/CheckSignature")
public class CheckSignature extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DigitalSignature digitalSignature = new DigitalSignature();
        PrintWriter out = response.getWriter();
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        String result = digitalSignature.getInformationOrder("Trần Đặng Quốc", "0355432702", "Thủ Đức", cart);
        out.write(result);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
