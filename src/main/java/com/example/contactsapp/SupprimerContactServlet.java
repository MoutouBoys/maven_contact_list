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

@WebServlet("/supprimerContact")
public class SupprimerContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID manquant ou invalide");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            // Création d'une connexion à la base de données
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactsdb", "root", "")) {
                // Suppression du contact
                boolean removed = deleteContact(connection, id);

                if (!removed) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Contact non trouvé");
                    return;
                }

                response.sendRedirect("contacts");
            } catch (SQLException ex) {
                Logger.getLogger(SupprimerContactServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalide");
        }
    }

    private boolean deleteContact(Connection connection, int id) {
        try {
            String sql = "DELETE FROM contacts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
