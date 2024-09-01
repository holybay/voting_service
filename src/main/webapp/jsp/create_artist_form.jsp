<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create_Artist</title>
</head>
<body>
<main>
    <c:if test="${message != null}">
        ${message}
    </c:if>
    <form name="create_artist_form" method="post" action="http://localhost:8080/voting_service/artists">
        <fieldset>
            <legend>Please enter an artist name to add:</legend>
            <div>
                <label for="new_artist">Artist name:</label>
                <input id="new_artist" type="text" name="artistName" />
            </div>
        </fieldset>
        <input type="submit" value="Submit">
    </form>
</main>
</body>
</html>