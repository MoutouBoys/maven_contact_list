<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contacts</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0 0 30px 0;
            background: linear-gradient(to right, #ff7e5f, #feb47b, #ff6f91);
        }

        form {
            margin-bottom: 20px;
            background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
            padding: 20px;
            width: 500px;
            border-radius: 20px;
            color: white;
            text-align: center;
        }

        label {
            display: inline-block;
            width: 100px;
            margin-top: 15px;
        }

        input[type="text"], input[type="number"] {
            width: 200px;
            border-radius: 20px;
            margin-bottom: 10px;
            outline: none;
            padding-left: 10px;
            border: none;
        }
        .ajout_btn:hover, .ajout_btn:focus{
            background-color: rosybrown;
        }
        input[type="submit"] {
            background: red;
            color: white;
            border: none;
            border-radius: 20px;
            padding: 10px 30px;
            cursor: pointer;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: red;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .back_btn {
            background-color: red;
            color: white;
            padding: 10px 20px;
            border-radius: 10px;
            text-decoration: none;
        }
        .back_btn:hover, .back_btn:focus{
            background-color:rgba(0, 0, 0, 0.5);
        }

        /* Styles pour la fenêtre modale */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.4);
            padding-top: 60px;
        }

        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }
        button{
            border: none;
            border-radius: 10px;
            padding: 10px 20px 10px 20px;
        }
        button:hover,button:focus{
            background-color: red;
            color: white;
        } 
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

    </style>
</head>
<body>
    <div>
        <h1>Liste des Contacts</h1>
        <form action="contacts" method="post">
            <label for="nom">Nom:</label>
            <input type="text" id="nom" name="nom" required><br>
            <label for="prenom">Prénom:</label>
            <input type="text" id="prenom" name="prenom" required><br>
            <label for="filiere">Filière:</label>
            <input type="text" id="filiere" name="filiere" required><br>
            <label for="specialite">Spécialité:</label>
            <input type="text" id="specialite" name="specialite" required><br>
            <label for="age">Âge:</label>
            <input type="number" id="age" name="age" required><br>
            <label for="skill">Compétence:</label>
            <input type="text" id="skill" name="skill" required><br><br>
            <input type="submit" value="Ajouter" class="ajout_btn">
        </form>
        <h2>Contacts existants:</h2>
        <table>
            <thead>
                <tr>
                    <th>id</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Filière</th>
                    <th>Spécialité</th>
                    <th>Âge</th>
                    <th>Compétence</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="contact" items="${contacts}">
                    <tr>
                        <td>${contact.id}</td>
                        <td>${contact.nom}</td>
                        <td>${contact.prenom}</td>
                        <td>${contact.filiere}</td>
                        <td>${contact.specialite}</td>
                        <td>${contact.age}</td>
                        <td>${contact.skill}</td>
                        <td>
                            <button type="button" onclick="showEditForm('${contact.id}', '${contact.nom}', '${contact.prenom}', '${contact.filiere}', '${contact.specialite}', '${contact.age}', '${contact.skill}')">Modifier</button>
                            <form action="supprimerContact" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${contact.id}">
                                <button type="submit">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <a href="index.jsp" class="back_btn">Retour à l'accueil</a>
    </div>

    <!-- Modal for Editing -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeEditForm()">&times;</span>
            <form id="editForm" action="modifierContact" method="post">
                <input type="hidden" name="id" id="editId">
                <label for="editNom">Nom:</label>
                <input type="text" id="editNom" name="nom" required><br>
                <label for="editPrenom">Prénom:</label>
                <input type="text" id="editPrenom" name="prenom" required><br>
                <label for="editFiliere">Filière:</label>
                <input type="text" id="editFiliere" name="filiere" required><br>
                <label for="editSpecialite">Spécialité:</label>
                <input type="text" id="editSpecialite" name="specialite" required><br>
                <label for="editAge">Âge:</label>
                <input type="number" id="editAge" name="age" required><br>
                <label for="editSkill">Compétence:</label>
                <input type="text" id="editSkill" name="skill" required><br><br>
                <input type="submit" value="Modifier">
            </form>
        </div>
    </div>

    <script>
        function showEditForm(id, nom, prenom, filiere, specialite, age, skill) {
            document.getElementById('editId').value = id;
            document.getElementById('editNom').value = nom;
            document.getElementById('editPrenom').value = prenom;
            document.getElementById('editFiliere').value = filiere;
            document.getElementById('editSpecialite').value = specialite;
            document.getElementById('editAge').value = age;
            document.getElementById('editSkill').value = skill;
            document.getElementById('editModal').style.display = 'block';
        }

        function closeEditForm() {
            document.getElementById('editModal').style.display = 'none';
        }
    </script>
</body>
</html>
