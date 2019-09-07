<%--
  Created by IntelliJ IDEA.
  User: konkon
  Date: 07/09/2019
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Customer information </h1>
<table border="1">
    <tr><td>ID: </td><td>${requestScope['product'].getId()}</td></tr>
    <tr><td>Name: </td><td>${requestScope['product'].getName()}</td></tr>
    <tr><td>Price: </td><td>${requestScope['product'].getPrice()}</td></tr>
    <tr><td>Description: </td><td>${requestScope['product'].getDescription()}</td></tr>
    <tr><td>Supplier: </td><td>${requestScope['product'].getSupplier()}</td></tr>
    <tr><td>Picture: </td><td><img src = "${product.getPicture()}" width="100px" height="100px" ></td></tr>
</table>
</body>
</html>
