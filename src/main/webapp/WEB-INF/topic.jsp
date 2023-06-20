<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="topics" scope="request" type="java.util.List<flashcards.project.model.Topic>" />

<html>
<head>
    <meta charset="UTF-8">
    <title>Flashcards</title>
    <link href="css/topic.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</head>
<body class="body">
<section class="topic-section">
    <div class="container">
        <div class="topic-form">
            <form action="<c:url value="/topicPage"/>" method="POST" enctype="application/x-www-form-urlencoded">
                <input type="text" name="newTopic" placeholder="Add topic">
                <button type="submit" class="button  add-button">Add topic</button>
            </form>
        </div>
        <div class="topic-cards">
            <div class="row row-cols-3">
                <c:if test="${topics.isEmpty()}">
                    <p>Folders don't exist</p>
                </c:if>
                <c:if test="${!topics.isEmpty()}">
                    <c:forEach var="topic" items="${topics}">
                        <div class="col">
                            <a href="<c:url value="/topicPage/subtopicPage?topicId=${topic.id}"/>" class="col-item"><c:out value="${topic.title}" /></a>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</section>
</body>
</html>
