package server.models;

import java.io.Serializable;

/**
 * La classe RegistrationForm représente un formulaire d'inscription.
 * Elle implémente Serializable.
 */
public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    /**
     * [CONSTRUCTEUR] - Il initialise le formulaire d'inscription selon les paramètres spécifiés.
     * @param prenom Prénom de la personne qui s'inscrit
     * @param nom Nom de la personne qui s'inscrit
     * @param email Adresse courriel de la personne qui s'inscrit
     * @param matricule Matricule de la personne qui s'inscrit
     * @param course Une instance de la classe {@link Course} du cours dans lequel la personne s'inscrit
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne le prénom de la personne qui s'inscrit.
     * @return Le prénom de la personne qui s'inscrit
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui modifie le prénom de la personne qui s'inscrit.
     * @param prenom Prénom de la personne qui s'inscrit
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne le nom de la personne qui s'inscrit.
     * @return Le nom de la personne qui s'inscrit
     */
    public String getNom() {
        return nom;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui modifie le nom de la personne qui s'inscrit.
     * @param nom Nom de la personne qui s'inscrit
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne l'adresse courriel de la personne qui s'inscrit.
     * @return L'adresse courriel de la personne qui s'inscrit
     */
    public String getEmail() {
        return email;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui modifie l'adresse courriel de la personne qui s'inscrit.
     * @param email Adresse courriel de la personne qui s'inscrit
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne le matricule de la personne qui s'inscrit.
     * @return Le matricule de la personne qui s'inscrit
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui modifie le matricule de la personne qui s'inscrit.
     * @param matricule Matricule de la personne qui s'inscrit
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne une instance de la classe {@link Course} du cours dans lequel la personne s'inscrit.
     * @return Une instance de la classe {@link Course} du cours dans lequel la personne s'inscrit
     */
    public Course getCourse() {
        return course;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui modifie une instance de la classe {@link Course} du cours dans lequel la personne s'inscrit.
     * @param course Instance de la classe {@link Course} du cours dans lequel la personne s'inscrit
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne le formulaire d'inscription en String
     * @return Formulaire d'inscription en String
      */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
