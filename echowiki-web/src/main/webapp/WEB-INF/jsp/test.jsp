<%@ page import="org.echowiki.core.entity.EntityCategory" %>
<%@ page import="org.echowiki.core.domain.Tree" %>
<%@ page import="java.util.List" %>
<%@ page import="org.echowiki.core.domain.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="category" value="${categories.get(0)}" scope="request"/>

<html>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<style>
    div {
        margin-top: 50px;
        font-family: -apple-system,BlinkMacSystemFont,Segoe WPC,Segoe UI,HelveticaNeue-Light,Ubuntu,Droid Sans,sans-serif;
        font-size: .9rem;
        line-height: 1.5;
        word-break: break-all;
        font-feature-settings: "liga" off,"calt" off;
    }

    textarea {
        height: 400px;
        width: 100%;
    }
</style>
<body>
<div class="container">
    <div class="row">
        <div class="col-6">
            <form action="/test" method="post">
                <textarea name="text" wrap="soft">

                </textarea>
                <button class="btn btn-primary" type="submit">Parsing</button>
            </form>
        </div>
        <div class="col-6">
            <pre>
                <c:if test="${paragraph != null}">
                    <hr>
                    ${paragraph.encodedString()}
                    <hr>
                    ${paragraph.decodedString()}
                </c:if>
            </pre>
        </div>
        <div class="col-12">
            <div class="list-group">
                <c:forEach items="${paragraph.elements}" var="e">
                    <a class="list-group-item">
                        ${e}
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

</body>
</html>
