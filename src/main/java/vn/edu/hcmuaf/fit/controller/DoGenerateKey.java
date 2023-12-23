package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.key.RSA;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;

@WebServlet(name = "DoGenerateKey", value = "/doGenerateKey")
public class DoGenerateKey extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RSA rsa = new RSA();
            KeyPair keyPair = rsa.generateKey();
            String publicKeyString = rsa.keyToString(keyPair.getPublic());
            String privateKeyString = rsa.keyToString(keyPair.getPrivate());
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{\"publicK\":\"" + publicKeyString + "\",\"privateK\":\"" + privateKeyString + "\"}");
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
