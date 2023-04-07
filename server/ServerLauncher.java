package server;

/**
 * La classe ServerLauncher représente un exécutable pour le serveur.
 */
public class ServerLauncher {
    /**
     * [ATTRIBUT] - Il s'agit d'un attribut pour le numéro de PORT.
     */
    public final static int PORT = 1337;

    /**
     * [MÉTHODE] - Il s'agit de la méthode exécutable du serveur.
     * Elle initialise un Server et exécute la méthode run() de celui-ci.
     * @param args Liste des paramètres de l'exécutable - Ne sont pas utilisés
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}