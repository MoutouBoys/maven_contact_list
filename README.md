# maven_contact_list
# TP : Intégration des Servlets, JSP et Maven

**Date** : Vendredi, 24/05/2024

## Description du projet

Nous allons créer une application web simple pour gérer une liste de contacts d’apprenants. Cette application permet d'ajouter, afficher et supprimer des contacts.

## Objectifs

- Comprendre les concepts de base des servlets et JSP.
- Utiliser Maven pour gérer les dépendances et le cycle de vie du projet.
- Manipuler les formulaires web pour l'ajout et la suppression de contacts.

## Prérequis

- JDK installé.
- Maven installé.
- Un IDE (Eclipse, IntelliJ IDEA, etc.).
- Apache Tomcat ou un autre serveur d'application compatible.

## Tâches

### Étape 1 : Créer le projet Maven

1. Ouvrir un terminal ou utiliser l'interface de votre IDE.
2. Créer un nouveau projet Maven en exécutant une commande ou manuellement
3. Naviguer dans le répertoire du projet :
   cd ContactList
   
Étape 2 : Configurer le projet
Ajouter les dépendances nécessaires :

Ouvrir le fichier pom.xml et ajouter les dépendances pour les servlets et JSP :
<dependencies>
        <!-- Dépendance pour Servlet API -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>

        <!-- Dépendance pour JSP API -->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.3</version>
            <scope>provided</scope>
        </dependency>

        <!-- Dépendance pour JSTL -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <!-- Dépendance pour MySQL -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.26</version>
        </dependency>
        <!-- Ajoutez d'autres dépendances ici si nécessaire -->
    </dependencies>
    
2. Configurer le fichier servlet :

Créer une classe servlet ContactServlet dans le répertoire src/main/java/com/example/servlet/ContactServlet.java.

Étape 3 : Créer le classe Java
Créer une classe Contact.java dans le répertoire src/main/java/com/example/model/Contact.java pour représenter les contacts.

Étape 4 : Créer les pages JSP
Créer index.jsp :
Placer ce fichier dans le répertoire src/main/webapp/index.jsp.

Créer contacts.jsp :
Placer ce fichier dans le répertoire src/main/webapp/contacts.jsp.

Étape 5 : Déployer et tester
1. Compiler et packager l'application avec Maven.

2. Déployer l'application :

Copier le fichier WAR généré dans le répertoire target sur le serveur d'application (par exemple, apache-tomcat/webapps).

3. Tester l'application :

Démarrer le serveur Apache Tomcat.
Accéder à l'application à l'adresse suivante : http://localhost:8080/ContactList/contacts.
Vérifier l'affichage de la page d'accueil.
Cliquer sur "Voir Contacts" pour accéder à la liste des contacts et ajouter de nouveaux contacts.
