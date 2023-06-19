<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="subtopics" scope="request" type="java.util.List<flashcards.project.model.Subtopic>"/>
<jsp:useBean id="topicId" scope="request" type="java.lang.Integer"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flashcards</title>
    <link rel="stylesheet" href="<c:url value="/css/subtopic.css"/>" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/0d84691a96.js" crossorigin="anonymous"></script>
</head>
<body class="body">

<section class="subtopic-section">
    <div class="container">

        <div class="subtopic-form">
            <form action="<c:url value="/topicPage/subtopicPage/add-subtopic" />" method="POST" enctype="application/x-www-form-urlencoded">
                <input type="text" name="newSubtopicName" placeholder="Add subtopic">
                <button type="submit" name="topicId" value="${topicId}" class="button add-button">Add subtopic</button>
            </form>
        </div>

        <div class="subtopic-del-form">
                <form action="<c:url value="/topicPage/subtopicPage" />" method="POST" enctype="application/x-www-form-urlencoded">
                    <button type="submit" name="topicId" value="${topicId}" class="button delete-button">Delete topic</button>
                </form>
        </div>

        <div class="subtopic-cards">
            <div class="row row-cols-3">
                <c:if test="${subtopics.isEmpty()}">
                    <p>Subtopics don't exist</p>
                </c:if>

                <c:if test="${!subtopics.isEmpty()}">
                    <c:forEach var="subtopic" items="${subtopics}">
                        <div class="col">
                            <a href="<c:url value="/topicPage/subtopicPage/trainingFirstPage?topicId=${subtopic.topicId}&subtopicId=${subtopic.id}&previousCard=0"/>"
                               class="col-item"><c:out value="${subtopic.title}" /><span><c:out value="${subtopic.learnedCardsCount} / ${subtopic.totalCardsCount}" /></span></a>
                            <a href="<c:url value="/topicPage/subtopicPage/cardEditPage?topicId=${subtopic.topicId}&subtopicId=${subtopic.id}" />" class="col-item-edit"><i class="fa-solid fa-pen-to-square"></i></a>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</section>
</body>
</html>