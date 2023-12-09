package vn.edu.hcmuaf.fit.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

@WebServlet(name = "DownloadBill", value = "/DownloadBill")
public class DownloadBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bill = request.getParameter("bill");
        String decodedText = URLDecoder.decode(bill, "UTF-8");
        String fileName = "bill.txt";
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try (PrintWriter writer = response.getWriter()) {
            writer.print(decodedText);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
