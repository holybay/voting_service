<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vote.Me</title>
</head>
<body>
<main>
    <form name="vote_page" method="post" action="votes">
        <fieldset>
            <legend>Choose your favourite artist:</legend>
            <c:forEach items="${artists}" var="artist">
                <div>
                    <input type="radio" name="artistId" value="<c:out value="${artist.id}"/>"/>${artist.name}<br>
                </div>
            </c:forEach>
        </fieldset>
        <fieldset>
            <legend>Choose 3-5 your favourite genres:</legend>
            <div>
                <c:forEach items="${genres}" var="genre">
                    <label><input type="checkbox" name="genreId" value="<c:out value="${genre.id}"/>"/>${genre.name}</label><br>
                </c:forEach>
            </div>
        </fieldset>
        <fieldset>
            <legend>Leave some notes about you:</legend>
            <div>
                <label for="about_me">Comment:</label>
                <input id="about_me" name="comment" type="text" placeholder="Please leave a comment here" required/>
            </div>
        </fieldset>
        <input type="submit" value="Submit">
    </form>
</main>
</body>
</html>