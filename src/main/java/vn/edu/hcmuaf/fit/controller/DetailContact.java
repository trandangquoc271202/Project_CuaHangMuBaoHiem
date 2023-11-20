package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Contact;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.service.ContactService;
import vn.edu.hcmuaf.fit.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DetailContact", value = "/DetailContact")
public class DetailContact extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("tendangnhap");
        Customer customer;
        try {
            customer = CustomerService.customer(username);
            if (customer == null || customer.getPermission() == 0) {
                request.setAttribute("error", "Đăng nhập quản trị viên để truy cập. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else if (customer.getPermission() > 1) {
                request.setAttribute("error", "Bạn không có chức vụ trong trang web này. Vui lòng đăng nhập lại!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            String id = request.getParameter("id");
            String pages = request.getParameter("pages");
            if (id != null) {
                Contact contact = ContactService.getInstance().getContact(id);
                request.setAttribute("contact", contact);
                request.setAttribute("pages", pages);
                request.getRequestDispatcher("DetailContact.jsp").forward(request, response);
            } else
                response.sendError(404, "Product not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
