package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Bill;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;

@WebServlet(name = "sales", value = "/sales")
public class Sales extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String m = request.getParameter("month");
        String y = request.getParameter("year");
        List<Bill> list_bill = ProductService.getListBill();
        if(m=="" || y==""){
            request.setAttribute("error","error");
            request.setAttribute("list",list_bill);
            long sales = 0;
            int count = 0;
            request.setAttribute("sales",sales);
            request.setAttribute("count",count);
            request.getRequestDispatcher("bill_manager.jsp").forward(request,response);
        }else{

            int month = Integer.parseInt(m);
            int year = Integer.parseInt(y);
            List<Bill> list = ProductService.getBillByDate(month,year);
            long sales = ProductService.totalPriceBill(month,year);
            int count = list.size();
            request.setAttribute("list",list);
            request.setAttribute("sales",sales);
            request.setAttribute("count",count);
            request.getRequestDispatcher("bill_manager.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
