package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.service.MailService;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(name = "add_bill", value = "/add_bill")
public class Add_Bill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        List<String> id_dp = new ArrayList<String>();
        for(Product p: cart.getListProduct()){
            id_dp.add(p.getDetail().get(0).getId());
        }
        if(name==""||email==""||phone==""||address==""){
            request.setAttribute("error","error");
            request.getRequestDispatcher("checkout.jsp").forward(request,response);
        }else{
            LocalDateTime date = LocalDateTime.now();
            String id_bill = ""+date.getSecond()+date.getMinute()+date.getHour()+date.getDayOfMonth()+date.getMonthValue()+date.getYear();
            String username = (String) request.getSession().getAttribute("tendangnhap");
            String id_cus = "";
            try {
                id_cus += ProductService.getCustomer(ProductService.getIdCusByUserName(username)).getId_customer();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ProductService.addBill(id_bill,id_cus,"Đang gửi",id_dp,address,phone);
            cart.getCart().clear();
            cart.setTotal(0);
            cart.setQuantity(0);
            request.setAttribute("name",name);
            request.setAttribute("email",email);
            request.setAttribute("phone",phone);
            request.setAttribute("address",address);
            request.setAttribute("id_bill",id_bill);
            request.setAttribute("list",id_dp);
            String conttent_recive = "";
            conttent_recive+=id_bill+"\n"+name+"\n"+email+"\n"+phone+"\n"+address+"\n"+"Tuy cập vào đây nếu giao hàng thành công"+"\n"+"http://localhost:8080/Project_CuaHangMuBaoHiem_war/ReciveProduct?id_bill="+id_bill;
            MailService.sendMail("20130233@st.hcmuaf.edu.vn","Giao hàng",conttent_recive);
            String conttent_cancel = "";
            conttent_cancel+=id_bill+"\n"+name+"\n"+email+"\n"+phone+"\n"+address+"\n"+"Tuy cập vào đây nếu muốn hủy hàng"+"\n"+"http://localhost:8080/Project_CuaHangMuBaoHiem_war/CancelProduct?id_bill="+id_bill;
            MailService.sendMail(email,"Helmetsshop",conttent_cancel);
            MailService.sendMail("20130233@st.hcmuaf.edu.vn","Giao hàng",conttent_recive);
            request.getRequestDispatcher("detail_bill.jsp").forward(request,response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
