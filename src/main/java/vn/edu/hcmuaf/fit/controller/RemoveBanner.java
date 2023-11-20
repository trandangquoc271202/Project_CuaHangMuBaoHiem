package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.service.SlideShowService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveBanner", value = "/RemoveBanner")
public class RemoveBanner extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        SlideShowService.getInstance().removeBanner(id);
        response.sendRedirect("/Project_CuaHangMuBaoHiem_war/ManageHome");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
