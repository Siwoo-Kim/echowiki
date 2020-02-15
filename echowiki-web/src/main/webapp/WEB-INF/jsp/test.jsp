<%@ page import="org.echowiki.core.entity.EntityCategory" %>
<%@ page import="org.echowiki.core.domain.Tree" %>
<%@ page import="java.util.List" %>
<%@ page import="org.echowiki.core.domain.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="category" value="${categories.get(0)}" scope="request"/>

<%
    EntityCategory cat = (EntityCategory) request.getAttribute("category");
    List<Category> desc = cat.getDescendants(Tree.Traversal.LEVEL);
    request.setAttribute("desc", desc);
%>

<html>
<head>
    <title>TEST</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <h1>분류: ${category.title}</h1>
    <p>최근 수정 시각: ${category.eventTime.creation}</p>
</div>

<div class="container">
    상위 분류:
    <ul>
        <c:forEach items="${category.children}" var="child">
            <li>${child.title}</li>
        </c:forEach>
    </ul>
</div>

<div class="container">
    <h1>하위 분류:</h1>
    <c:forEach items="${desc}" var="descendant">
        <li>${descendant.title}</li>
    </c:forEach>
</div>

</body>
</html>
