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
<c:if test="${requestScope['message']!=null}">
    <span style="color: green">${requestScope['message']}</span>
</c:if>
<p>
    <a href="products"> Back to list</a>
</p>
<form method="post">
    <table>
        <tr><td>Price: </td><td><input type="text" id="name" name="name" value="${requestScope['product'].getName()}"></td></tr>
        <tr><td>Price: </td><td><input type="text" id="price" name="price" value="${requestScope['product'].getPrice()}"></td></tr>
        <tr><td>description: </td><td><input type="text" id="description" name="description" value="${requestScope['product'].getDescription()}"></td></tr>
        <tr><td>supplier: </td><td><input type="text" id="supplier" name="supplier" value="${requestScope['product'].getSupplier()}"></td></tr>
        <tr><td>image: </td><td><input type="text" id="picture" name="picture" value="${requestScope['product'].getPicture()}"></td></tr>
        <tr><td><input type="submit" value="Delete"></td></tr>
    </table>
</form>
</body>
</html>
