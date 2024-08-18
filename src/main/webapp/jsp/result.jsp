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
    <fieldset>
        <legend>Artist vote results:</legend>
            <div>
                <c:forEach items="${artists}" var="artist">
                    <p>${artist.key} : ${artist.value}</p>
                </c:forEach>
            </div>
    </fieldset>
    <fieldset>
        <legend>Favourite genres vote results:</legend>
            <div>
                <c:forEach items="${genres}" var="genre">
                    <p>${genre.key} : ${genre.value}</p>
                </c:forEach>
            </div>
    </fieldset>
    <fieldset>
        <legend>All comments left:</legend>
            <div>
                <c:forEach items="${comments}" var="comment">
                    <p>${comment.dateVoted} - ${comment.textComment}</p>
                </c:forEach>
            </div>
    </fieldset>
</main>
</body>
</html>