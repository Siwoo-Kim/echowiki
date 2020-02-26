<%@ page import="org.echowiki.core.entity.EntityCategory" %>
<%@ page import="org.echowiki.core.domain.Tree" %>
<%@ page import="java.util.List" %>
<%@ page import="org.echowiki.core.domain.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    div.result {
        margin-top: 0px;
    }

    textarea {
        overflow: scroll;
        height: 600px;
        scroll-behavior: smooth;
        width: 100%;
    }
</style>
<body>
<div class="container">
    문서 이름:
    <input type="text" class="text-input" name="document_id">
    문서 위치:

</div>
<div class="container">
    <div class="row">
        <div class="col-6">
            <form action="/test" method="post">
                <textarea name="text" wrap="soft">

                </textarea>
                <button class="btn btn-primary" type="submit">Parsing</button>
            </form>
        </div>
        <div class="col-6 result">
            <c:forEach items="${paragraph}" var="p">
            <div class="card">
                <pre>
                    <c:if test="${p != null}">
                        <hr>
                        ${p.encodedString()}
                        <hr>
                        ${p.rawString()}
                    </c:if>
                </pre>
            </div>
            </c:forEach>
        </div>
        <div class="col-12">
            <div class="list-group">
                <c:forEach items="${paragraph}" var="p">
                    <c:forEach items="${p.elements}" var="e">
                        <a class="list-group-item">
                                ${e}
                        </a>
                    </c:forEach>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

</body>
</html>
