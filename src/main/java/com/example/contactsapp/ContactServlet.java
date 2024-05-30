package com.example.contactsapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/contacts")
public class ContactServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/contactsdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
public void init() throws ServletException {
    super.init();
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        loadContactsFromDatabase();
    } catch (ClassNotFoundException e) {
        throw new ServletException("Impossible de charger le pilote JDBC MySQL", e);
    }
}

private void loadContactsFromDatabase() throws ServletException {
    List<Contact> contacts = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts")) {

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String filiere = resultSet.getString("filiere");
            String specialite = resultSet.getString("specialite");
            int age = resultSet.getInt("age");
            String skill = resultSet.getString("skill");

            contacts.add(new Contact(id, nom, prenom, filiere, specialite, age, skill));
        }

    } catch (SQLException e) {
        throw new ServletException("Impossible de récupérer les contacts depuis la base de données", e);
    }
    getServletContext().setAttribute("contacts", contacts);
}


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String filiere = resultSet.getString("filiere");
                String specialite = resultSet.getString("specialite");
                int age = resultSet.getInt("age");
                String skill = resultSet.getString("skill");

                contacts.add(new Contact(id, nom, prenom, filiere, specialite, age, skill));
            }

        } catch (SQLException e) {
            throw new ServletException("Impossible de récupérer les contacts depuis la base de données", e);
        }

        request.setAttribute("contacts", contacts);
        request.getRequestDispatcher("contacts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String filiere = request.getParameter("filiere");
        String specialite = request.getParameter("specialite");
        int age = Integer.parseInt(request.getParameter("age"));
        String skill = request.getParameter("skill");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO contacts (nom, prenom, filiere, specialite, age, skill) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, filiere);
            statement.setString(4, specialite);
            statement.setInt(5, age);
            statement.setString(6, skill);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ServletException("Impossible d'ajouter le contact à la base de données", e);
        }

        response.sendRedirect("contacts");
    }
}
