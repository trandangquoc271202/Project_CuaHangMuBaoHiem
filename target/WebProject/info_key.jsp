<%@ page import="vn.edu.hcmuaf.fit.service.CustomerService" %>
<%@ page import="vn.edu.hcmuaf.fit.model.Customer" %>
<%--
  Created by IntelliJ IDEA.
  User: duynh
  Date: 12/22/2023
  Time: 11:36 AM
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
            <%--            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">--%>
            <%--                <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-info" role="tab"--%>
            <%--                   aria-controls="v-pills-info" aria-selected="true">Thông tin cá nhân</a>--%>
            <%--                <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-reset_pw" role="tab"--%>
            <%--                   aria-controls="#v-pills-reset_pw" aria-selected="false">Đổi mật khẩu</a>--%>
            <%--            </div>--%>
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <a class="nav-link" id="v-pills-home-tab" href="Profile" role="tab"
                   aria-controls="v-pills-info" aria-selected="false">Thông tin cá nhân</a>
                <a class="nav-link" id="v-pills-profile-tab" href="ChangePassword" role="tab"
                   aria-controls="#v-pills-reset_pw" aria-selected="false">Đổi mật khẩu</a>
                <a class="nav-link" id="v-pills-bill-tab" href="BillCustomer" role="tab"
                   aria-controls="#v-pills-bill" aria-selected="false">Lịch sử mua hàng</a>
                <a class="nav-link active" id="v-pills-info_key-tab" href="InfoKey" role="tab"
                   aria-controls="v-pills-info_key" aria-selected="true">Thông tin khóa</a>
            </div>
        </div>
        <div class="col-9">
            <div class="tab-content" id="v-pills-tabContent">
                <div class="tab-pane fade" id="v-pills-info" role="tabpanel"
                     aria-labelledby="v-pills-info-tab">
                    <div class="form-account">
                        <form action="">
                            <div class="title">Thông tin cá nhân</div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-6">
                                        <label class="form-label">Họ và tên *</label>
                                        <input type="text"
                                               class="form-control"
                                               placeholder="Nhập Họ và tên"
                                               name="name"
                                               value="">
                                    </div>
                                    <div class="col-6">
                                        <label class="form-label">Email *</label>
                                        <div class="form-control no_text">
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
                                                value="">
                                    </div>
                                    <div class="col-6">
                                        <label class="form-label">Địa chỉ *</label>
                                        <input
                                                type="text"
                                                class="form-control"
                                                placeholder="Nhập địa chỉ"
                                                name="address"
                                                value="">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="f-btn">
                                    <button type="submit"> Lưu</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane fade" id="v-pills-reset_pw" role="tabpanel"
                     aria-labelledby="v-pills-reset_pw-tab">
                    <div class="form-account">
                        <form action="/Project_CuaHangMuBaoHiem_war/doChangePassword" method="post">
                            <div class="title">Đổi mật khẩu</div>
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
                            </p>
                            <div class="form-group">
                                <div class="f-btn">
                                    <button type="submit" id="submit"> Lưu</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <% String error = (String) request.getAttribute("error");%>
                <% String success = (String) request.getAttribute("success");%>
                <%
                    String username = (String) session.getAttribute("tendangnhap");
                    Customer customer = CustomerService.customer(username);
                    String public_key = CustomerService.getPublicKey(customer.getId_customer());
                %>
                <div class="tab-pane fade show active" id="v-pills-info_key" role="tabpanel"
                     aria-labelledby="v-pills-info_key-tab">
                    <div class="form-account">
                        <form id="myForm" action="/Project_CuaHangMuBaoHiem_war/InfoKey" method="post">
                            <div class="title">Thông tin khóa công khai</div>
                            <div class="form-group-rp">
                                <span style="display: flex; justify-content: center; color: green; text-align: center; font-size: 18px;"><%=(success != null && success != "") ? success : ""%>
                                <div>
                                    <label class="form-label">Khóa công khai *</label>
                                    <textarea id="public_key" class="form-control no_text" style="height: 120px"
                                              readonly><%= public_key %></textarea>
                                </div>
                            </div>
                            <input type="hidden" id="publicKeyInput" name="publicKey" value="">
                            <p style="color: red; text-align: center; font-size: 18px;"><%=(error != null && error != "") ? error : ""%>
                            <div class="form-group">
                                <div class="f-btn">
                                    <button type="button" data-toggle="modal" data-target="#confirmModal"> Báo cáo lộ
                                        khóa riêng tư
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- Modal -->
                <div class="modal" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="confirmModalLabel">Xác nhận lưu</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                Bạn có chắc chắn muốn lưu không?
                            </div>
                            <div class="modal-footer">
                                <!-- Nút xác nhận -->
                                <button type="button" class="btn btn-primary" onclick="saveForm()">Xác nhận</button>
                                <!-- Nút hủy bỏ -->
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy bỏ</button>
                            </div>
                        </div>
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

<script>
    function saveForm() {
        var publicKey = document.getElementById("public_key").value;
        document.getElementById("publicKeyInput").value = publicKey;
        document.getElementById("myForm").submit();
    }
</script>
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
