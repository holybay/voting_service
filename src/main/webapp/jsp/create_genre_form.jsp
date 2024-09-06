<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create_Genre</title>
</head>
<body>
<main>
    <c:if test="${message != null}">
        ${message}
    </c:if>
    <form name="create_genre_form" method="post" action="http://localhost:8080/voting_service/genres">
        <fieldset>
            <legend>Please enter a genre to add:</legend>
            <div>
                <label for="new_genre">Genre name:</label>
                <input id="new_genre" type="text" name="genreName" />
            </div>
        </fieldset>
        <input type="submit" value="Submit">
    </form>
</main>
</body>
</html>