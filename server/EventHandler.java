package server;

/**
 * L'interface EventHandler permet d'utiliser la méthode handle
 */
@FunctionalInterface
public interface EventHandler {
    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui exécute une commande.
     * @param cmd Commande à exécuter
     * @param arg Arguments pour la commande
     */
    void handle(String cmd, String arg);
}
