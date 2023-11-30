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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

@WebServlet(name = "CheckSignature", value = "/CheckSignature")
public class CheckSignature extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DigitalSignature digitalSignature = new DigitalSignature();
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String privateKey = request.getParameter("privateKey");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        String result = digitalSignature.getInformationOrder(name, phone, address, cart);
        String message = digitalSignature.hashString(result);
        try {
            digitalSignature.importPrivateKey(privateKey);
        } catch (Exception e) {
            request.setAttribute("error","error");
            request.getRequestDispatcher("checkout.jsp").forward(request,response);
        }
//        String signature = digitalSignature.encryptRSA(digitalSignature.importPrivateKey(privateKey););
        out.write(result);
//        out.write(signature);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
