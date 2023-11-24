package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Bill;
import vn.edu.hcmuaf.fit.service.MailService;
import vn.edu.hcmuaf.fit.service.NguyeMinhDuc;
import vn.edu.hcmuaf.fit.service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "list-bill", value = "/list-bill")
public class ListBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String indexPage = request.getParameter("index");
        if((indexPage==null)){
            indexPage="1";
        }
        int index = Integer.parseInt(indexPage);
        int pre = 0;
        int next = 0;
        if(NguyeMinhDuc.onePageBill(index).size()!=0){
            pre = index - 1;
            next = index + 1;
        }

        int n = NguyeMinhDuc.getTotalBill();
        int endPage = n/10;
        if(n % 10 != 0){
            endPage++;
        }

        request.setAttribute("index", index);
        request.setAttribute("pre", pre);
        request.setAttribute("next", next);
        request.setAttribute("endP", endPage);

        List<Bill> bills = NguyeMinhDuc.onePageBill(index);
        for(Bill b: bills){
            if(!NguyeMinhDuc.checkChangeBill(b.getId())){
                String status = "Đã hủy";
                NguyeMinhDuc.change_status_bill(b.getId(),status);
                MailService.sendMail("","Hủy đơn hàng","Lý do: Đơn hàng đã bị chỉnh sửa");
            }
        }

        request.setAttribute("list", NguyeMinhDuc.onePageBill(index));
        long sales = 0;
        int count = 0;
        request.setAttribute("sales",sales);
        request.setAttribute("count",count);
        request.setAttribute("error",null);
        request.getRequestDispatcher("bill_manager.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
