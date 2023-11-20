package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.ContactService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveContact", value = "/RemoveContact")
public class RemoveContact extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ContactService.getInstance().remove(id);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ManageContact");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
