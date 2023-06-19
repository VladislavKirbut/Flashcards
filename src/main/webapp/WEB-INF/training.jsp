<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="card" scope="request" type="flashcards.project.model.Card" />
<jsp:useBean id="topicId" scope="request" type="java.lang.Integer"/>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Flashcards</title>
  <link href="<c:url value="/css/training.css"/>" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</head>
<body class="body">

<section class="training-section">
  <div class="container">
    <div class="card">
      <div class="card-content">
        <c:out value="${card.question}" />
      </div>
    </div>

    <div class="rotate-card">
      <div class="card-rotate-content">
        <c:out value="${card.answer}" />
      </div>
    </div>
    <div class="training-form">
      <form action="<c:url value="/topicPage/subtopicPage/trainingPage/update-card" />" method="POST" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="cardId" value="${card.id}" />
        <input type="hidden" name="subtopicId" value="${card.subtopicId}" />
        <button type="submit" name="topicId" value="${topicId}" class="button know-button">Знаю</button>
      </form>
      <form action="<c:url value="/topicPage/subtopicPage/trainingPage" />" method="POST" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="cardId" value="${card.id}" />
        <input type="hidden" name="subtopicId" value="${card.subtopicId}" />
        <button type="submit" name="topicId" value="${topicId}" class="button unknown-button">Не знаю</button>
      </form>
      <button class="button rotate-button" onclick="onClick()">Повернуть карточку</button>
    </div>
  </div>
</section>

<script src="<c:url value="/js/training.js"/>"></script>
</body>
</html>
