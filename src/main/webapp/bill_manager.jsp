<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.model.Product" %>
<%@ page import="vn.edu.hcmuaf.fit.service.ProductService" %>
<%@ page import="vn.edu.hcmuaf.fit.model.NumberFormat" %>
<%@ page import="vn.edu.hcmuaf.fit.model.Bill" %>
<%@ page import="vn.edu.hcmuaf.fit.service.NguyeMinhDuc" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html :class="{ 'theme-dark': dark }" x-data="data()" lang="en" xmlns:x-transition="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Quản lý hóa đơn</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
  <link
          href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap"
          rel="stylesheet"
  />
  <link rel="stylesheet" href="admin/assets/css/tailwind.output.css" />
  <link rel="stylesheet" href="admin/assets/css/filter.css" />
  <script
          src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js"
          defer
  ></script>
  <script src="admin/assets/js/init-alpine.js"></script>
  <style>
    .error{
      color: red;
      font-size: 14px;
    }
  </style>
</head>
<body>
<div
        class="flex h-screen bg-gray-50 dark:bg-gray-900"
        :class="{ 'overflow-hidden': isSideMenuOpen}"
>
  <!-- Desktop sidebar -->
  <aside
          class="z-20 flex-shrink-0 hidden w-64 overflow-y-auto bg-white dark:bg-gray-800 md:block"
  >
    <div class="py-4 text-gray-500 dark:text-gray-400">
      <a
              class="ml-6 text-lg font-bold text-gray-800 dark:text-gray-200"
              href="/Project_CuaHangMuBaoHiem_war/ManageProduct"
      >
        Admin
      </a>
      <ul class="mt-6">
        <li class="relative px-6 py-3">
          <a
                  class="inline-flex items-center w-full text-sm font-semibold transition-colors duration-150 hover:text-gray-800 dark:hover:text-gray-200"
                  href="/Project_CuaHangMuBaoHiem_war/Home"
          >
            <svg
                    class="w-5 h-5"
                    aria-hidden="true"
                    fill="none"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
            >
              <path
                      d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"
              ></path>
            </svg>
            <span class="ml-4">Về trang bán hàng</span>
          </a>
        </li>
      </ul>
      <ul>
        <li class="relative px-6 py-3">

          <a
                  class="inline-flex items-center w-full text-sm font-semibold text-gray-800 transition-colors duration-150 hover:text-gray-800 dark:hover:text-gray-200 dark:text-gray-100"
                  href="/Project_CuaHangMuBaoHiem_war/ManageProduct"
          >
            <svg
                    class="w-5 h-5"
                    aria-hidden="true"
                    fill="none"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
            >
              <path d="M4 6h16M4 10h16M4 14h16M4 18h16"></path>
            </svg>
            <span class="ml-4">Quản lí sản phẩm</span>
          </a>
        </li>
        <li class="relative px-6 py-3">
                <span
                        class="absolute inset-y-0 left-0 w-1 bg-purple-600 rounded-tr-lg rounded-br-lg"
                        aria-hidden="true"
                ></span>
          <a
                  class="inline-flex items-center w-full text-sm font-semibold text-gray-800 transition-colors duration-150 hover:text-gray-800 dark:hover:text-gray-200 dark:text-gray-100"
                  href="/Project_CuaHangMuBaoHiem_war/list-bill"
          >
            <svg
                    class="w-5 h-5"
                    aria-hidden="true"
                    fill="none"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
            >
              <path d="M4 6h16M4 10h16M4 14h16M4 18h16"></path>
            </svg>
            <span class="ml-4">Quản lí hóa đơn</span>
          </a>
        </li>
        <li class="relative px-6 py-3">
          <a class="inline-flex items-center w-full text-sm font-semibold text-gray-800 transition-colors duration-150 hover:text-gray-800 dark:hover:text-gray-200 dark:text-gray-100"
             href="/Project_CuaHangMuBaoHiem_war/list-customer">
            <svg
                    class="w-5 h-5"
                    aria-hidden="true"
                    fill="none"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
            >
              <path d="M4 6h16M4 10h16M4 14h16M4 18h16"></path>
            </svg>
            <span class="ml-4">Quản lý khách hàng</span>
          </a>
        </li>
        <li class="relative px-6 py-3">
          <a
                  class="inline-flex items-center w-full text-sm font-semibold text-gray-800 transition-colors duration-150 hover:text-gray-800 dark:hover:text-gray-200 dark:text-gray-100"
                  href="/Project_CuaHangMuBaoHiem_war/ManageHome"
          >
            <svg
                    class="w-5 h-5"
                    aria-hidden="true"
                    fill="none"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    viewBox="0 0 24 24"
                    stroke="currentColor">
              <path d="M4 6h16M4 10h16M4 14h16M4 18h16"></path>
            </svg>
            <span class="ml-4">Quản lý trang chủ</span>
          </a>
        </li>
      </ul>

    </div>
  </aside>
  <!-- Mobile sidebar -->
  <!-- Backdrop -->
  <div class="flex flex-col flex-1 w-full">
    <header class="z-10 py-4 bg-white shadow-md dark:bg-gray-800">
      <div
              class="container flex items-center justify-between h-full px-6 mx-auto text-purple-600 dark:text-purple-300"
      >
        <!-- Mobile hamburger -->
        <button
                class="p-1 mr-5 -ml-1 rounded-md md:hidden focus:outline-none focus:shadow-outline-purple"
                @click="toggleSideMenu"
                aria-label="Menu"
        >
          <svg
                  class="w-6 h-6"
                  aria-hidden="true"
                  fill="currentColor"
                  viewBox="0 0 20 20"
          >
            <path
                    fill-rule="evenodd"
                    d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z"
                    clip-rule="evenodd"
            ></path>
          </svg>
        </button>
        <!-- Search input -->
        <div class="flex justify-center flex-1 lg:mr-32">
          <div
                  class="relative w-full max-w-xl mr-6 focus-within:text-purple-500"
          >
            <div class="absolute inset-y-0 flex items-center pl-2">

            </div>
            <form action="/Project_CuaHangMuBaoHiem_war/find-bill" method="get">
              <input
                      class="pl-8 pr-2 text-sm text-gray-700 placeholder-gray-600 bg-gray-100 border-0 rounded-md dark:placeholder-gray-500 dark:focus:shadow-outline-gray dark:focus:placeholder-gray-600 dark:bg-gray-700 dark:text-gray-200 focus:placeholder-gray-500 focus:bg-white focus:border-purple-300 focus:outline-none focus:shadow-outline-purple form-input"
                      style="width: 450px"
                      type="text" name="text"
                      placeholder="Id hóa đơn"
              />
              <input type="submit" value="Tìm kiếm" style="width:80px;height: 36px;color: black;border-radius: 5px"></form>
          </div>
        </div>
        <ul class="flex items-center flex-shrink-0 space-x-6">
          <!-- Theme toggler -->
          <li class="flex">
            <button
                    class="rounded-md focus:outline-none focus:shadow-outline-purple"
                    @click="toggleTheme"
                    aria-label="Toggle color mode"
            >
              <template x-if="!dark">
                <svg
                        class="w-5 h-5"
                        aria-hidden="true"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                >
                  <path
                          d="M17.293 13.293A8 8 0 016.707 2.707a8.001 8.001 0 1010.586 10.586z"
                  ></path>
                </svg>
              </template>
              <template x-if="dark">
                <svg
                        class="w-5 h-5"
                        aria-hidden="true"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                >
                  <path
                          fill-rule="evenodd"
                          d="M10 2a1 1 0 011 1v1a1 1 0 11-2 0V3a1 1 0 011-1zm4 8a4 4 0 11-8 0 4 4 0 018 0zm-.464 4.95l.707.707a1 1 0 001.414-1.414l-.707-.707a1 1 0 00-1.414 1.414zm2.12-10.607a1 1 0 010 1.414l-.706.707a1 1 0 11-1.414-1.414l.707-.707a1 1 0 011.414 0zM17 11a1 1 0 100-2h-1a1 1 0 100 2h1zm-7 4a1 1 0 011 1v1a1 1 0 11-2 0v-1a1 1 0 011-1zM5.05 6.464A1 1 0 106.465 5.05l-.708-.707a1 1 0 00-1.414 1.414l.707.707zm1.414 8.486l-.707.707a1 1 0 01-1.414-1.414l.707-.707a1 1 0 011.414 1.414zM4 11a1 1 0 100-2H3a1 1 0 000 2h1z"
                          clip-rule="evenodd"
                  ></path>
                </svg>
              </template>
            </button>
          </li>
          <!-- Notifications menu -->
          <li class="relative">

            <a href="/Project_CuaHangMuBaoHiem_war/ManageContact">
              <button  class="relative align-middle rounded-md focus:outline-none focus:shadow-outline-purple" @click="toggleNotificationsMenu" @keydown.escape="closeNotificationsMenu" aria-label="Notifications" aria-haspopup="true">
                <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M10 2a6 6 0 00-6 6v3.586l-.707.707A1 1 0 004 14h12a1 1 0 00.707-1.707L16 11.586V8a6 6 0 00-6-6zM10 18a3 3 0 01-3-3h6a3 3 0 01-3 3z"></path>
                </svg>
              </button>
            </a>
          </li>
          <!-- Profile menu -->
        </ul>
      </div>
    </header>
    <main class="h-full pb-16 overflow-y-auto">
      <div class="container grid px-6 mx-auto">
        <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
          Kiểm tra doanh thu
        </h2>
        <div style="display: flex">
          <%
            List<Bill> list = (List<Bill>) request.getAttribute("list");
            String error = (String)request.getAttribute("error");
            if(error == "error") error = "* Vui lòng nhập ngày và tháng";
            if(error == null) error ="";

            String month = request.getParameter("month");
            String year = request.getParameter("year");
            month = month==null?"":month;
            year=year==null?"":year;
            long sales = (long)request.getAttribute("sales");
            int count = (int) request.getAttribute("count");

            int index = (int) request.getAttribute("index");
            int endPage = (int) request.getAttribute("endP");
            int pre = (int) request.getAttribute("pre");
            int next = (int) request.getAttribute("next");

          %>

          <form action="/Project_CuaHangMuBaoHiem_war/sales" method="get" style="margin-left: 176px">
            <div class="px-4 py-3 mb-8 bg-white rounded-lg shadow-md dark:bg-gray-800">
              <label class="block text-sm">
                <span class="text-gray-700 dark:text-gray-400">Tháng</span>
                <input class="block w-full mt-1 text-sm dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:text-gray-300 dark:focus:shadow-outline-gray form-input"
                       type="number"   name="month"   value="<%=month%>"  placeholder="Nhập tháng"   />
                <span class="error"></span>
              </label>
              <br>
              <label class="block text-sm">
                <span class="text-gray-700 dark:text-gray-400">Năm</span>
                <input class="block w-full mt-1 text-sm dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:text-gray-300 dark:focus:shadow-outline-gray form-input"
                       type="number"   name="year"     value="<%=year%>"  placeholder="Nhập năm"   />
              </label>
              <span id="error" class="error" style="padding: 10px"><%=error%></span>
              <div><input type="submit" value="Tìm kiếm" class="button" style="background: #007bff;width: 100px;border-radius: 4px;margin-top: 7px"></div>
            </div>
          </form>
          <div style="margin-left: 200px" class="px-4 py-3 mb-8 bg-white rounded-lg shadow-md dark:bg-gray-800">
            <label class="block text-sm">
              <span class="text-gray-700 dark:text-gray-400">Doanh thu</span>
              <input class="block w-full mt-1 text-sm dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:text-gray-300 dark:focus:shadow-outline-gray form-input"
                     name=""   value="<%=sales%>"  placeholder="0"  disabled />
            </label>
            <br>
            <label class="block text-sm">
              <span class="text-gray-700 dark:text-gray-400">Doanh số</span>
              <input class="block w-full mt-1 text-sm dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:text-gray-300 dark:focus:shadow-outline-gray form-input"
                     name=""     value="<%=count%>"  placeholder="0"  disabled />
            </label>
          </div>
        </div>
      </div>
      <div class="container grid px-6 mx-auto">
        <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
          Danh sách hóa đơn
        </h2>

        <!-- With actions -->
        <h4
                class="mb-4 text-lg font-semibold text-gray-600 dark:text-gray-300"
        >

        </h4>
        <div class="w-full overflow-hidden rounded-lg shadow-xs">
          <div class="w-full overflow-x-auto">
            <table class="w-full whitespace-no-wrap">
              <thead>
              <tr
                      class="text-xs font-semibold tracking-wide text-left text-gray-500 uppercase border-b dark:border-gray-700 bg-gray-50 dark:text-gray-400 dark:bg-gray-800"
              >
                <th class="px-4 py-3">Id hóa đơn</th>
                <th class="px-4 py-3">Tên khách hàng</th>
                <th class="px-4 py-3">Đơn giá</th>
                <th class="px-4 py-3">Địa chỉ</th>
                <th class="px-4 py-3">Số điện thoại</th>
                <th class="px-4 py-3">Ngày lập đơn</th>
                <th class="px-4 py-3">Trạng thái</th>
                <th class="px-4 py-3">Xác minh đơn hàng</th>
                <th class="px-4 py-3">Kiểm tra đơn hàng</th>
                <th class="px-4 py-3">Chỉnh sửa</th>
              </tr>
              </thead>
              <tbody
                      class="bg-white divide-y dark:divide-gray-700 dark:bg-gray-800"
              >
              <%;
                for(Bill b:list){
              %>

              <tr class="text-gray-700 dark:text-gray-400">

                <td class="px-4 py-3">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                    <div class="flex items-center text-sm">
                      <!-- Avatar with inset shadow -->

                      <div>
                        <%=b.getId()%>
                      </div>
                    </div>
                  </a>
                </td>
                <td class="px-4 py-3 text-sm">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                  <p class="font-semibold"><%=ProductService.getCustomer(b.getId_cus()).getName()%></p>
                  <p class="text-xs text-gray-600 dark:text-gray-400">
                    <%=ProductService.getCustomer(b.getId_cus()).getId_customer()%>
                  </p>
                  </a>
                </td>
                <td class="px-4 py-3 text-sm">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                  <% long price = 0;
                    for(String id_dp:b.getProductList()){
                      Product p = ProductService.getProduct(ProductService.getIdProduct(id_dp));
                      price += p.getPrice()-p.getPrice()*p.getDiscount();
                    }
                    NumberFormat nf = new NumberFormat();%>
                  <%=nf.numberFormat(price)%>đ
                  </a>
                </td>
                <td class="px-4 py-3 text-sm">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                    <%=b.getAddress()%>
                  </a>
                </td>
                <td class="px-4 py-3 text-sm">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                    <%=b.getPhone()%>
                  </a>
                </td>
                <td class="px-4 py-3 text-sm">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                  <%=b.getDate()%>
                  </a>
                </td>
                <td class="px-4 py-3 text-sm">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                  <%=b.getStatus()%>
                  </a>
                </td>
                <td class="px-4 py-3 text-sm">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                    <%if(NguyeMinhDuc.check_identify(b.getId())){%>
                    <%="Đã xác minh"%>
                    <%}else{%>
                    <%="Chưa được xác minh"%>
                    <%}%>
                  </a>
                </td>
                <td class="px-4 py-3 text-sm">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                    <%if(NguyeMinhDuc.checkChangeBill(b.getId())){%>
                      <%="Bình thường"%>
                    <%}else{%>
                    <%="Bị thay đổi thông tin"%>
                    <%}%>
                  </a>
                </td>
                <td class="px-4 py-3">
                  <a href="/Project_CuaHangMuBaoHiem_war/detail-bill?id=<%=b.getId()%>">
                  <div class="flex items-center space-x-4 text-sm">
                    <a href="/Project_CuaHangMuBaoHiem_war/form-fix-bill?id=<%=b.getId()%>"><button
                            class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray"
                            aria-label="Edit"
                    >
                      <svg
                              class="w-5 h-5"
                              aria-hidden="true"
                              fill="currentColor"
                              viewBox="0 0 20 20"
                      >
                        <path
                                d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z"
                        ></path>
                      </svg>
                    </button></a>
                    <a href="/Project_CuaHangMuBaoHiem_war/delete-bill?id=<%=b.getId()%>"><button
                            class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray"
                            aria-label="Delete"
                    >
                      <svg
                              class="w-5 h-5"
                              aria-hidden="true"
                              fill="currentColor"
                              viewBox="0 0 20 20"
                      >
                        <path
                                fill-rule="evenodd"
                                d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z"
                                clip-rule="evenodd"
                        ></path>
                      </svg>
                    </button></a>
                  </div>
                    </a>
                </td>
              </tr>
              <%}%>
              </tbody>
            </table>
          </div>
          <div
                  class="grid px-4 py-3 text-xs font-semibold tracking-wide text-gray-500 uppercase border-t dark:border-gray-700 bg-gray-50 sm:grid-cols-9 dark:text-gray-400 dark:bg-gray-800"
          >
            <%
              int size=0;
              if((month==""||year=="")){
                size = ProductService.getListBill().size();
              }else {
                size = ProductService.getBillByDate(Integer.parseInt(month),Integer.parseInt(year)).size();
              }
              int start = Math.min(size,(index-1)*10+1);
              int end = Math.min(size,(index-1)*10+10);
              if(list.size()==1){
                start=end=size=1;
                endPage=1;
              }
              int max = 0;
              if(ProductService.getListBill().size()%10==0){
                max = ProductService.getListBill().size()/10;
              }else{
                max = ProductService.getListBill().size()/10 + 1;
              }
            %>
            <span class="flex items-center col-span-3">
                  Hiển thị <%=start%>-<%=end%> của <%=size%>
                </span>
            <span class="col-span-2"></span>
            <!-- Pagination -->
            <span class="flex col-span-4 mt-2 sm:mt-auto sm:justify-end">
                  <nav aria-label="Table navigation">
                    <ul class="inline-flex items-center">
                      <%if(endPage<=max){%>
                        <li class="page-item <%=index==1? "disabled":""%>">
                        <%if(pre != 0){%>
                          <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + pre%>"><button class="px-3 py-1 rounded-md rounded-l-lg focus:outline-none focus:shadow-outline-purple" aria-label="Previous">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                          <%}%>
                      </li>
                      <%for(int i = 1; i <= endPage; i++){%>
                        <li>
                        <a  href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + i%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==i? "text-white bg-purple-600":""%>">
                          <%=i%>
                        </button></a>
                        </li>
                      <%}%>
                      <li class="page-item <%=index==endPage? "disabled":""%>">
                        <%if(next<=max){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + next%>"><button class="px-3 py-1 rounded-md rounded-r-lg focus:outline-none focus:shadow-outline-purple" aria-label="Next">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                        <%}%>
                      </li>
                        <%}else{
                          if(index==1||index==endPage){%>
                        <li class="page-item <%=index==1? "disabled":""%>">
                          <%if(pre != 0){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + pre%>"><button class="px-3 py-1 rounded-md rounded-l-lg focus:outline-none focus:shadow-outline-purple" aria-label="Previous">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                          <%}%>
                      </li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + 1%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==1? "text-white bg-purple-600":""%>">
                          1
                        </button></a></li>
                      <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + 2%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==2? "text-white bg-purple-600":""%>">
                          2
                        </button></a></li>
                        <li>...</li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + (endPage-1)%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==(endPage-1)? "text-white bg-purple-600":""%>">
                          <%=endPage-1%>
                        </button></a></li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + endPage%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==endPage? "text-white bg-purple-600":""%>">
                          <%=endPage%>
                        </button></a></li>
                      <li class="page-item <%=index==endPage? "disabled":""%>">
                        <%if(next<=max){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + next%>"><button class="px-3 py-1 rounded-md rounded-r-lg focus:outline-none focus:shadow-outline-purple" aria-label="Next">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                        <%}%>
                      </li>
                        <%}else if(index==2){%>
                        <li class="page-item <%=index==1? "disabled":""%>">
                          <%if(pre != 0){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + pre%>"><button class="px-3 py-1 rounded-md rounded-l-lg focus:outline-none focus:shadow-outline-purple" aria-label="Previous">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                          <%}%>
                      </li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + 1%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==1? "text-white bg-purple-600":""%>">
                          1
                        </button></a></li>
                      <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + 2%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==2? "text-white bg-purple-600":""%>">
                          2
                        </button></a></li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + 3%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==3? "text-white bg-purple-600":""%>">
                          3
                        </button></a></li>
                        <li>...</li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + (endPage-1)%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==(endPage-1)? "text-white bg-purple-600":""%>">
                          <%=endPage-1%>
                        </button></a></li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + endPage%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==endPage? "text-white bg-purple-600":""%>">
                          <%=endPage%>
                        </button></a></li>
                      <li class="page-item <%=index==endPage? "disabled":""%>">
                        <%if(next<=max){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + next%>"><button class="px-3 py-1 rounded-md rounded-r-lg focus:outline-none focus:shadow-outline-purple" aria-label="Next">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                        <%}%>
                      </li>
                        <%}else if(index==(endPage-1)){%>
                        <li class="page-item <%=index==1? "disabled":""%>">
                          <%if(pre != 0){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + pre%>"><button class="px-3 py-1 rounded-md rounded-l-lg focus:outline-none focus:shadow-outline-purple" aria-label="Previous">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                          <%}%>
                      </li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + 1%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==1? "text-white bg-purple-600":""%>">
                          1
                        </button></a></li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + 2%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==2? "text-white bg-purple-600":""%>">
                          2
                        </button></a></li>
                        <li>...</li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + (endPage-2)%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==(endPage-2)? "text-white bg-purple-600":""%>">
                          <%=endPage-2%>
                        </button></a></li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + (endPage-1)%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==(endPage-1)? "text-white bg-purple-600":""%>">
                          <%=endPage-1%>
                        </button></a></li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + endPage%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==endPage? "text-white bg-purple-600":""%>">
                          <%=endPage%>
                        </button></a></li>
                      <li class="page-item <%=index==endPage? "disabled":""%>">
                        <%if(next<=max){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + next%>"><button class="px-3 py-1 rounded-md rounded-r-lg focus:outline-none focus:shadow-outline-purple" aria-label="Next">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                        <%}%>
                      </li>
                        <%}else{%>
                        <li class="page-item <%=index==1? "disabled":""%>">
                          <%if(pre != 0){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + pre%>"><button class="px-3 py-1 rounded-md rounded-l-lg focus:outline-none focus:shadow-outline-purple" aria-label="Previous">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                          <%}%>
                      </li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + 1%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==1? "text-white bg-purple-600":""%>">
                          1
                        </button></a></li>
                        <li>...</li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + (index-1)%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==(index-1)? "text-white bg-purple-600":""%>">
                          <%=index-1%>
                        </button></a></li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + (index)%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==(index)? "text-white bg-purple-600":""%>">
                          <%=index%>
                        </button></a></li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + (index+1)%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==(index+1)? "text-white bg-purple-600":""%>">
                          <%=index+1%>
                        </button></a></li>
                        <li>...</li>
                        <li><a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + endPage%>"><button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple <%=index==endPage? "text-white bg-purple-600":""%>">
                          <%=endPage%>
                        </button></a></li>
                      <li class="page-item <%=index==endPage? "disabled":""%>">
                        <%if(next<=max){%>
                        <a href="<%="/Project_CuaHangMuBaoHiem_war/list-bill?index=" + next%>"><button class="px-3 py-1 rounded-md rounded-r-lg focus:outline-none focus:shadow-outline-purple" aria-label="Next">
                          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
                            <path d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" fill-rule="evenodd">
                            </path>
                          </svg>
                        </button></a>
                        <%}%>
                      </li>
                        <%}%>
                        <%}%>
                    </ul>
                  </nav>
                </span>
          </div>
        </div>
      </div>
    </main>
  </div>
</div>
</body>
</html>
