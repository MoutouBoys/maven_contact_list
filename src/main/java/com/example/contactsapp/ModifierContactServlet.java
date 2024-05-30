package com.example.contactsapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/modifierContact")
public class ModifierContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID manquant ou invalide");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String filiere = request.getParameter("filiere");
            String specialite = request.getParameter("specialite");
            int age = Integer.parseInt(request.getParameter("age"));
            String skill = request.getParameter("skill");

            // Création d'une connexion à la base de données
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactsdb", "root", "")) {
                // Mise à jour du contact
                boolean updated = updateContact(connection, id, nom, prenom, filiere, specialite, age, skill);

                if (!updated) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Contact non trouvé");
                    return;
                }

                response.sendRedirect("contacts");
            } catch (SQLException ex) {
                Logger.getLogger(ModifierContactServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre d'âge invalide");
        }
    }

    private boolean updateContact(Connection connection, int id, String nom, String prenom, String filiere, String specialite, int age, String skill) {
        String sql = "UPDATE contacts SET nom = ?, prenom = ?, filiere = ?, specialite = ?, age = ?, skill = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, filiere);
            statement.setString(4, specialite);
            statement.setInt(5, age);
            statement.setString(6, skill);
            statement.setInt(7, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
