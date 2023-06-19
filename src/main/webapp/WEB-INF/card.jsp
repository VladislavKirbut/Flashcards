<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="cards" scope="request" type="java.util.List<flashcards.project.model.Card>"/>
<jsp:useBean id="subtopicId" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="topicId" scope="request" type="java.lang.Integer"/>

<html>
<head>
  <meta charset="UTF-8">
  <title>Flashcards</title>
  <link href="<c:url value="/css/card.css"/>" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
  <script src="https://kit.fontawesome.com/0d84691a96.js" crossorigin="anonymous"></script>
</head>
<body class="body">
  <section class="subtopic-section">
    <div class="container">
      <div class="subtopic-form">
        <form action="<c:url value="/topicPage/subtopicPage/cardEditPage/add-card"/>" method="POST" enctype="application/x-www-form-urlencoded">
          <input type="text" name="newCardQuestion" placeholder="Question" />
          <input type="text" name="newCardAnswer" placeholder="Answer" />
          <input type="hidden" name="topicId" value="${topicId}" />
          <button type="submit" name="subtopicId" value="${subtopicId}" class="button  add-button">Add card</button>
        </form>
      </div>
        <div class="subtopic-del-form">
          <form action="<c:url value="/topicPage/subtopicPage/cardEditPage"/>" method="POST" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="topicId" value="${topicId}" />
            <button type="submit" name="subtopicId" value="${subtopicId}" class="button  delete-button">Delete subtopic</button>
          </form>
        </div>
        <div class="subtopic-cards">
          <div class="row row-cols-3">
            <c:if test="${cards.isEmpty()}">
              <p>Cards don't exist</p>
            </c:if>
            <c:if test="${!cards.isEmpty()}">
              <c:forEach var="card" items="${cards}">
                <div class="col">
                  <div class="col-item"><c:out value="${card.question}|${card.answer}"/>
                    <form action="<c:url value="/topicPage/subtopicPage/cardEditPage/remove-card" />" method="POST" enctype="application/x-www-form-urlencoded" class="del-form">
                      <input type="hidden" name="cardId" value="${card.id}">
                      <input type="hidden" name="subtopicId" value="${card.subtopicId}">
                      <button type="submit" class="del-card" name="topicId" value="${topicId}"><i class="fa fa-times" aria-hidden="true"></i></button>
                    </form>
                  </div>
                </div>
              </c:forEach>
            </c:if>
          </div>
        </div>
    </div>
  </section>
</body>
</html>
