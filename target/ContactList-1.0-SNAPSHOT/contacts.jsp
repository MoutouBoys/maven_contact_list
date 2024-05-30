<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contacts</title>
</head>
<body>
    <h1>Liste des Contacts</h1>
    <form action="contacts" method="post">
        Nom: <input type="text" name="name" required><br>
        Compétence: <input type="text" name="skill" required><br>
        <input type="submit" value="Ajouter">
    </form>
    <h2>Contacts existants:</h2>
    <ul>
        <c:forEach var="contact" items="${contacts}">
            <li>${contact.name} - ${contact.skill}</li>
        </c:forEach>
    </ul>
    <a href="index.jsp">Retour à l'accueil</a>
</body>
</html>
