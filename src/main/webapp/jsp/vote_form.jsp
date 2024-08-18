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
            <legend>Choose you favourite artist:</legend>
            <div>
                <c:forEach items="${artists}" var="artist">
                    <input type="radio" name="artistName" value="<c:out value="${artist}"/>"/>${artist}<br>
                </c:forEach>
            </div>
        </fieldset>
        <fieldset>
            <legend>Choose 3-5 your favourite genres:</legend>
            <div>
                <c:forEach items="${genres}" var="genre">
                    <label><input type="checkbox" name="genre" value="<c:out value="${genre}"/>"/>${genre}</label><br>
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