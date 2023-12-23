<%@ page import="vn.edu.hcmuaf.fit.model.Customer" %>
<%@ page import="vn.edu.hcmuaf.fit.service.CustomerService" %><%--
  Created by IntelliJ IDEA.
  User: duynh
  Date: 12/22/2023
  Time: 10:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>HelmetsShop</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/account.css" rel="stylesheet">
</head>

<body>
<!-- Header Start -->
<%@include file="header.jsp" %>
<!-- Header End -->

<!-- Login Start -->
<section class="nav-vertical">
    <div class="row">
        <div class="col-3">
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <a class="nav-link active" id="v-pills-info-tab" data-toggle="pill" href="#v-pills-info" role="tab"
                   aria-controls="v-pills-info" aria-selected="true">Thông tin cá nhân</a>
                <a class="nav-link" id="v-pills-reset_pw-tab" data-toggle="pill" href="#v-pills-reset_pw" role="tab"
                   aria-controls="v-pills-reset_pw" aria-selected="false">Đổi mật khẩu</a>
                <a class="nav-link" id="v-pills-bill-tab" data-toggle="pill" href="#v-pills-bill" role="tab"
                   aria-controls="v-pills-bill" aria-selected="false">Lịch sử mua hàng</a>
                <a class="nav-link" id="v-pills-info_key-tab" data-toggle="pill" href="#v-pills-info_key" role="tab"
                   aria-controls="v-pills-info_key" aria-selected="false">Thông tin khóa</a>
            </div>
        </div>
        <% String error = (String) request.getAttribute("error");%>
        <% String success = (String) request.getAttribute("success");%>
        <% String username = (String) session.getAttribute("tendangnhap");
            Customer cus = CustomerService.customer(username);
        %>
        <% String public_key = CustomerService.getPublicKey(cus.getId_customer());%>
        <div class="col-9">
            <div class="tab-content" id="v-pills-tabContent">
                <div class="tab-pane fade" id="v-pills-info" role="tabpanel" aria-labelledby="v-pills-info-tab">
                    <!-- Nội dung Tab 1 -->
                    <div class="form-account">
                        <form action="/Project_CuaHangMuBaoHiem_war/DoProfile" method="post">
                            <div class="title">Thông tin cá nhân</div>
                            <span style="color: green; font-size: 18px; text-align: center;"><%=(success != null && success != "") ? success : ""%></span>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-6">
                                        <label class="form-label">Họ và tên *</label>
                                        <input type="text"
                                               class="form-control"
                                               placeholder="Nhập Họ và tên"
                                               name="name"
                                               value="<%= cus.getName()!=null? cus.getName():"" %>">
                                    </div>
                                    <div class="col-6">
                                        <label class="form-label">Email *</label>
                                        <div class="form-control no_text"><%= cus.getEmail() %>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <label class="form-label">Số điện thoại *</label>
                                        <input
                                                type="text"
                                                class="form-control"
                                                placeholder="Nhập Số điện thoại"
                                                name="phone"
                                                value="<%= cus.getPhone()!=null? cus.getPhone():"" %>">
                                    </div>
                                    <div class="col-6">
                                        <label class="form-label">Địa chỉ *</label>
                                        <input
                                                type="text"
                                                class="form-control"
                                                placeholder="Nhập địa chỉ"
                                                name="address"
                                                value="<%= cus.getAddress()!=null? cus.getAddress():"" %>">
                                    </div>
                                </div>
                            </div>
                            <span style="color: red; font-size: 18px; text-align: center;"><%=(error != null && error != "") ? error : ""%></span>
                            <div class="form-group">
                                <div class="f-btn">
                                    <button type="submit"> Lưu</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane fade" id="v-pills-reset_pw" role="tabpanel" aria-labelledby="v-pills-reset_pw-tab">
                    <!-- Nội dung Tab 2 -->
                    <div class="form-account">
                        <form action="/Project_CuaHangMuBaoHiem_war/doChangePassword" method="post">
                            <div class="title">Đổi mật khẩu</div>
                            <span style="display: flex; justify-content: center; color: green; text-align: center; font-size: 18px;"><%=(success != null && success != "") ? success : ""%>
                            </span>
                            <div class="form-group-rp">
                                <input type="password" class="form-control" placeholder="Mật khẩu hiện tại"
                                       name="pass_old">
                                <input type="password" id="password" class="form-control" placeholder="Mật khẩu mới"
                                       name="password">
                                <p style="color: red">Mật khẩu phải chứa tối thiểu 6 ký tự.</p>
                                <input type="password" id="confirm_pw" class="form-control"
                                       placeholder="Nhập lại mật khẩu"
                                       name="confirm_pw">
                                <p style="color: red">Mật khẩu xác nhận phải trùng với mật khẩu mới.</p>
                            </div>
                            <p style="color: red; text-align: center; font-size: 18px;"><%=(error != null && error != "") ? error : ""%>
                            </p>
                            <div class="form-group">
                                <div class="f-btn">
                                    <button type="submit" id="submit"> Lưu</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane fade" id="v-pills-bill" role="tabpanel" aria-labelledby="v-pills-bill-tab">
                    <!-- Nội dung Tab 3 -->
                </div>
                <div class="tab-pane fade" id="v-pills-info_key" role="tabpanel" aria-labelledby="v-pills-info_key-tab">
                    <!-- Nội dung Tab 4 -->
                    <div class="form-account">
                        <form action="/Project_CuaHangMuBaoHiem_war/doChangePassword" method="post">
                            <div class="title">Thông tin khóa công khai</div>
                            <span style="display: flex; justify-content: center; color: green; text-align: center; font-size: 18px;"><%=(success != null && success != "") ? success : ""%>
                            </span>
                            <div class="col-6">
                                <label class="form-label">Khóa công khai *</label>
                                <div class="form-control no_text"><%= public_key %>
                                </div>
                            </div>
                            <p style="color: red; text-align: center; font-size: 18px;"><%=(error != null && error != "") ? error : ""%>
                            </p>
                            <div class="form-group">
                                <div class="f-btn">
                                    <button type="submit"> Lưu</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Login End-->

<!-- Footer Start -->
<%@include file="footer.jsp" %>
<!-- Footer End -->


<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>


<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
<script src="lib/easing/easing.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>

<!-- Contact Javascript File -->
<script src="mail/jqBootstrapValidation.min.js"></script>
<script src="mail/contact.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>
<script src="js/valid.js" charset="utf-8"></script>
</body>
</html>
