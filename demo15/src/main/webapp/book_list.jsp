<%--
  Created by IntelliJ IDEA.
  User: ZZZCNY
  Date: 2021/11/5
  Time: 上午 8:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>图书列表</title>
    <link rel="stylesheet" href="styles/a.css">
    <link rel="stylesheet" href="styles/div.css">
</head>
<body>
<table>
    <tr>
        <th>图书编号</th>
        <th>图书名称</th>
        <th>图书作者</th>
        <th>出版时间</th>
        <th>出版社</th>
        <th>图书价格</th>
        <th>剩余数量</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${pageBean.books}" var="book">
        <tr>
            <td>${book.bookId}</td>
            <td>${book.bookName}</td>
            <td>${book.bookAuthor}</td>
            <td>${book.bookPublishTime}</td>
            <td>${book.bookPublish}</td>
            <td>${book.bookPrice}</td>
            <td>${book.bookNum}</td>
            <td>
                <a href="${pageContext.request.contextPath}/ReserveServlet?bookId=${book.bookId}&currentPage=${pageBean.currentPage}">
                    <button>预定</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="page">
    <a href="PageServlet?currentPage=1">
        <button>首页</button>
    </a>
    <a href="PageServlet?currentPage=${pageBean.currentPage==1?1:pageBean.currentPage-1}">
        <button>上一页</button>
    </a>
    <span>当前第${pageBean.currentPage}页</span>
    <span>共${pageBean.totalPage}页</span>
    <a href="PageServlet?currentPage=${pageBean.currentPage==pageBean.totalPage?pageBean.totalPage:pageBean.currentPage+1}">
        <button>下一页</button>
    </a>
    <a href="PageServlet?currentPage=${pageBean.totalPage}">
        <button>尾页</button>
    </a>
</div>
</body>
</html>
