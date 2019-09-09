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
<form method="post" enctype="multipart/form-data">
    <c:if test="${requestScope['message']!=null}">
        <span style="color: green">${requestScope['message']}</span>
    </c:if>
    <p>
        <a href="products">Back to list</a>
    </p>
    <table>
        <tr><td>ID: </td><td><input type="text" name="id" id="id"></td></tr>
        <tr><td>Name: </td><td><input type="text" name="name" id="name"></td></tr>
        <tr><td>Price: </td><td><input type="text" name="price" id="price"></td></tr>
        <tr><td>Description: </td><td><input type="text" name="description" id="description"></td></tr>
        <tr><td>Supplier: </td><td><input type="text" name="supplier" id="supplier"></td></tr>
        <tr><td>Picture: </td><td><input type="file" name="picture" id="picture"></td></tr>
        <tr><td><input type="submit" value="Create"></td></tr>
    </table>
</form>
</body>
</html>
