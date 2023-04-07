package server.models;

import java.io.Serializable;

/**
 * La classe Course représente un cours.
 * Elle implémente Serializable.
 */
public class Course implements Serializable {

    private String name;
    private String code;
    private String session;

    /**
     * [CONSTRUCTEUR] - Il initialise le cours selon les paramètres spécifiés.
     * @param name Nom du cours
     * @param code Code du cours
     * @param session Session du cours - Hiver | Ete | Automne
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne le nom du cours.
     * @return Nom du cours
     */
    public String getName() {
        return name;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui modifie le nom du cours.
     * @param name Nom du cours
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne le code du cours.
     * @return Code du cours
     */
    public String getCode() {
        return code;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui modifie le code du cours.
     * @param code Code du cours
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne la session du cours.
     * @return Session du cours
     */
    public String getSession() {
        return session;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui modifie la session du cours.
     * @param session Session du cours
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui retourne le cours en String
     * @return Cours en String
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }
}
