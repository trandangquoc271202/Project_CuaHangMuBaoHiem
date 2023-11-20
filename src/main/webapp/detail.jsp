<%@ page import="vn.edu.hcmuaf.fit.model.Product" %>
<%@ page import="vn.edu.hcmuaf.fit.model.NumberFormat" %>

<%@ page import="java.util.Map" %>

<%@ page import="java.util.List" %>

<%@ page import="vn.edu.hcmuaf.fit.service.ProductService" %><%--
Created by IntelliJ IDEA.
User: ACER
Date: 11/6/2022
Time: 9:13 PM
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

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
    <link rel='stylesheet prefetch' href='https://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css'>

    <style>

        input.star { display: none; }

        label.star {
            float: right;
            padding: 10px;
            font-size: 20px;
            color: #444;
            transition: all .2s;
        }

        input.star:checked ~ label.star:before {
            content: '\f005';
            color: #FD4;
            transition: all .25s;
        }

        label.star:hover { cursor: pointer }

        label.star:before {
            content: '\f006';
            font-family: FontAwesome;
        }
    </style>
</head>

<body>

<!-- Header Start -->
<%@ include file="header.jsp"%>
<!-- Header End -->


<!-- Breadcrumb Start -->
<div class="container-fluid">
    <div class="row px-xl-5">
        <div class="col-12">
            <nav class="breadcrumb bg-light mb-30">
                <a class="breadcrumb-item text-dark" href="#">Trang chủ</a>
                <a class="breadcrumb-item text-dark" href="#">Sản phẩm</a>
                <span class="breadcrumb-item active">Chi tiết sản phẩm</span>
            </nav>
        </div>
    </div>
</div>
<!-- Breadcrumb End -->

<% NumberFormat nf = new NumberFormat();
    Product p= (Product) request.getAttribute("product"); %>
<!-- Shop Detail Start -->
<%Map<String,String> listComment = ProductService.getListComment(p.getId());%>
<div class="container-fluid pb-5">
    <div class="row px-xl-5">
        <div class="col-lg-5 mb-30">
            <div id="product-carousel" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner bg-light">
                    <div class="carousel-item active">
                        <img class="w-100 h-100" <%if(p.getImg().size()<=0){%>src="img/noimage.jpg"<%}else{%>src="<%= p.getImg().get(0).getImg()%>"<%}%> alt="Image">
                    </div>
                    <% for(int i = 1;i<p.getImg().size();i++){%>
                    <div class="carousel-item">
                        <img class="w-100 h-100" src="<%=p.getImg().get(i).getImg()%>" alt="Image">
                    </div>

                    <%}%>
                </div>
                <a class="carousel-control-prev" href="#product-carousel" data-slide="prev">
                    <i class="fa fa-2x fa-angle-left text-dark"></i>
                </a>
                <a class="carousel-control-next" href="#product-carousel" data-slide="next">
                    <i class="fa fa-2x fa-angle-right text-dark"></i>
                </a>
            </div>
        </div>

        <div class="col-lg-7 h-auto mb-30">
            <div class="h-100 bg-light p-30">
                <h3><%=p.getName()%></h3>
                <div class="d-flex mb-3">
                    <div class="text-primary mr-2">
                        <%for (int j=1;j<=p.getStar();j++){%>
                        <small class="fa fa-star text-primary mr-1"></small>
                        <%}
                            if ((p.getStar()*10)%10!=0){
                        %>
                        <small class="fa fa-star-half-alt text-primary mr-1"></small>
                        <%}%>
                    </div>
                    <small class="pt-1">(<%= p.getAmount() %> đánh giá)</small>
                </div>
                <h3 class="font-weight-semi-bold mb-4"><%= nf.numberFormat((long) (p.getPrice()-p.getPrice()*p.getDiscount())) %>đ</h3>
                <div class="d-flex mb-3">
                    <strong class="text-dark mr-3">Kích thước:</strong>
                    <form id="size" action="/Project_CuaHangMuBaoHiem_war/AddDetail">
                        <input type="hidden" name="id" value="<%= p.getId() %>">
                        <% int i=1; for(String size : p.getListSize()){ i++;%>
                        <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio" required="required" class="custom-control-input" id="size-<%=i%>" name="size"  value="<%= size %>">
                            <label class="custom-control-label" for="size-<%=i%>"><%= size %></label>
                        </div>

                        <%}%>
                </div>

                <div class="d-flex mb-4">
                    <strong class="text-dark mr-3">Màu sắc:</strong>
                        <% int j=0; for(String color : p.getListColor()){ j++;%>
                        <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio"  required="required" class="custom-control-input" id="color-<%=j%>" name="color" value="<%= color%>">
                            <label class="custom-control-label" for="color-<%=j%>"><%= color %></label>
                        </div>
                        <%}%>

                </div>
                <div>

                </div>
                <p id="color1234" class="help-block text-danger"></p>

                <div class="d-flex align-items-center mb-4 pt-2">
                    <div class="input-group quantity mr-3" style="width: 130px;">
                        <div class="input-group-btn">
                            <button class="btn btn-primary btn-minus" type="button">
                                <i class="fa fa-minus"></i>
                            </button>
                        </div>

                        <input type="text" class="form-control bg-secondary border-0 text-center" name="quantity" style="height: 30px" value="1">

                        <div class="input-group-btn">
                            <button class="btn btn-primary btn-plus" type="button">
                                <i class="fa fa-plus"></i>
                            </button>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary px-3" onclick="check()"><i class="fa fa-shopping-cart mr-1"></i> Thêm vào
                        giỏ hàng</button>
                </div>
                </form>
                <div class="d-flex pt-2">
                    <strong class="text-dark mr-2">Chia sẻ:</strong>
                    <div class="d-inline-flex">
                        <a class="text-dark px-2" href="http://facebook.com" target="_blank">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a class="text-dark px-2" href="http://twitter.com" target="_blank">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a class="text-dark px-2" href="http://linkedin.com" target="_blank">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                        <a class="text-dark px-2" href="http://pinterest.com" target="_blank">
                            <i class="fab fa-pinterest"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row px-xl-5">
        <div class="col">
            <div class="bg-light p-30">
                <div class="nav nav-tabs mb-4">
                    <a class="nav-item nav-link text-dark active" data-toggle="tab" href="#tab-pane-1">Mô tả sản phẩm</a>

                    <a class="nav-item nav-link text-dark" data-toggle="tab" href="#tab-pane-3">Bình luận (<%=listComment.size()%>)</a>
                </div>
                <div class="tab-content">
                    <div class="tab-pane fade show active" id="tab-pane-1">
                        <h4 class="mb-3">Mô tả sản phẩm</h4>
                        <p><%=p.getDecrispe()%></p>
                    </div>

                    <div class="tab-pane fade" id="tab-pane-3">
                        <div class="row" style="overflow: auto">
                            <div style="float:left;width:680px; padding-right:0px;">
                                <%for(String key:listComment.keySet()){%>
                                <div class="col-md-6">
                                    <div class="media mb-4" style="width: 600px;">
                                        <div class="media-body" >
                                            <h6><%=ProductService.getCustomer(key).getName()%><small> - <i><%=ProductService.getDateComment(key,p.getId(),listComment.get(key))%></i></small></h6>
                                            <div class="text-primary mb-2">
                                                <%int star = ProductService.getStarComment(key,p.getId(),listComment.get(key));
                                                    for(int a=0;a<star;a++){%>
                                                <i class="fas fa-star"></i>
                                                <%}%>
                                            </div>
                                            <p><%=listComment.get(key)%></p>
                                        </div>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                            <div class="col-md-6" style="float: right; width: 500px;">

                                <h4 class="mb-4">Viết đánh giá</h4>
                                <form action="/Project_CuaHangMuBaoHiem_war/get-comment" method="get">
                                    <div class="d-flex my-3">
                                        <p class="mb-0 mr-2">Đánh giá * :</p>
                                        <div class="text-primary">
                                            <input class="star star-5" id="star-5" value="5" type="radio" name="star">
                                            <label class="star star-5" for="star-5"></label>
                                            <input class="star star-4" id="star-4" value="4" type="radio" name="star">
                                            <label class="star star-4" for="star-4"></label>
                                            <input class="star star-3" id="star-3" value="3" type="radio" name="star">
                                            <label class="star star-3" for="star-3"></label>
                                            <input class="star star-2" id="star-2" value="2" type="radio" name="star">
                                            <label class="star star-2" for="star-2"></label>
                                            <input class="star star-1" id="star-1" value="1" type="radio" checked="checked" name="star">
                                            <label class="star star-1" for="star-1"></label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="message">Bình luận *</label>
                                        <textarea id="message" name="mess" cols="30" rows="5" class="form-control"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <input type="hidden" name="id_Pro" value="1" class="form-control" id="email">
                                    </div>
                                    <div class="form-group mb-0">
                                        <input type="submit" value="Gửi" class="btn btn-primary px-3">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Shop Detail End -->


<!-- Products Start -->
<div class="container-fluid py-5">
    <h2 class="section-title position-relative text-uppercase mx-xl-5 mb-4"><span class="bg-secondary pr-3">Có thể bạn cũng thích</span></h2>
    <div class="row px-xl-5">
        <div class="col">
            <div class="owl-carousel related-carousel">
                <% List<Product> list = ProductService.listType(p.getType(),p.getId());
                for(Product pd : list){
                %>

                <div class="product-item bg-light">
                    <div class="product-img position-relative overflow-hidden">
                        <img class="img-fluid w-100" <%if(pd.getImg().size()<=0){%>src="img/noimage.jpg"<%}else{%> src="<%=pd.getImg().get(0).getImg()%>" <%}%> alt="">
                        <div class="product-action">
                            <a class="btn btn-outline-dark btn-square" href="<%= "/Project_CuaHangMuBaoHiem_war/detail?id="+pd.getId() %>"><i class="fa fa-search"></i></a>
                        </div>
                    </div>
                    <div class="text-center py-4">
                        <a class="h6 text-decoration-none text-truncate" href="<%= "/Project_CuaHangMuBaoHiem_war/detail?id="+pd.getId() %>"><%= pd.getName() %></a>
                        <div class="d-flex align-items-center justify-content-center mt-2">
                            <h5><%=nf.numberFormat((long)(pd.getPrice()-pd.getPrice()*pd.getDiscount()))%>đ</h5><h6 class="text-muted ml-2"><del><%=nf.numberFormat(pd.getPrice())%>đ</del></h6>
                        </div>
                        <% if(p.sumQuantity()<=0) {%>Hết hàng<%}else{%> Còn: <%=p.sumQuantity()%><%}%>
                        <div class="d-flex align-items-center justify-content-center mb-1">
                            <%for (int x=1;x<=pd.getStar();x++){%>
                            <small class="fa fa-star text-primary mr-1"></small>
                            <%}
                                if ((pd.getStar()*10)%10!=0){
                            %>
                            <small class="fa fa-star-half-alt text-primary mr-1"></small>
                            <%}%>
                            <small>(<%=pd.getAmount()%>)</small>
                        </div>
                    </div>
                </div>

                <%}%>
            </div>
        </div>
    </div>
</div>
<!-- Products End -->


<!-- Footer Start -->
<%@include file="footer.jsp"%>
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
<script src="js/addDetail.js"></script>
</body>

</html>