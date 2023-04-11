package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * La classe serveur gère la liste des cours et l'inscription.
 */
public class Server {

    /**
     * [ATTRIBUT] - Il s'agit de la constante pour la commande d'inscription.
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * [ATTRIBUT] - Il s'agit de la constante pour la commande pour recevoir la liste des cours.
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * [CONSTRUCTEUR] - Il créé un socket selon le port spécifié et initialise un {@link EventHandler} pour les commandes.
     * @param port Port de communication client - serveur
     * @throws IOException Si le constructeur n'arrive pas à créer le socket
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui permet d'ajouter un {@link EventHandler}.
     * @param h EventHandler
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui exécute le serveur.
     * Elle attend une connexion client, elle initialise les flux de communications et elle attend pour une commande.
     * Le client est déconnecté après l'excécution de la commande.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui attend une commande d'un client
     * Elle exécute la commande une fois qu'elle est reçu.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui reçoit une commande client et qui la transforme en Pair.
     * @param line String envoyée par le client qui contient une commande et des arguments
     * @return Un objet de type Pair contenant la commande et les arguments
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui ferme les flux de communication et qui déconnecte le client.
     * @throws IOException Si une erreur est lancé à la fermeture des flux ou à la déconnexion du client
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui exécute une méthode selon la commande reçue en paramètre.
     * @param cmd La commande envoyée par le client
     * @param arg Les arguments pour la commande
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui envoie la liste des cours sous le format de la classe {@link Course} au client.
     * Elle filtre les cours selon la session reçue en paramètre.
     * @param arg La session pour filtrer les cours
     */
    public void handleLoadCourses(String arg) {
        try {
            FileReader fileReader = new FileReader("server/data/cours.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<Course> filteredCourses = new ArrayList<Course>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineElements = line.split("\t");
                Course course = new Course(lineElements[1], lineElements[0], lineElements[2]);
                if (course.getSession().equals(arg)) {
                    filteredCourses.add(course);
                }
            }
            bufferedReader.close();
            objectOutputStream.writeObject(filteredCourses);

        } catch (FileNotFoundException e) {
            System.err.println("Erreur à l'ouverture du fichier");
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de la lecture ou de l'écriture : " + e.getMessage());
        }
    }

    /**
     * [MÉTHODE] - Il s'agit d'une méthode qui inscrit un utilisateur à un cours selon une instance de {@link RegistrationForm} reçue via le flux de communication.
     * Elle écrit dans le flux de communication un message en cas de succès.
     */
    public void handleRegistration() {
        try {
            RegistrationForm form = (RegistrationForm) objectInputStream.readObject();
 
            FileWriter writer = new FileWriter("server/data/inscription.txt", true);
            ArrayList<String> inscription = new ArrayList<String>();
            Collections.addAll(inscription, form.getCourse().getSession(), form.getCourse().getCode(), form.getMatricule(), form.getPrenom(), form.getNom(), form.getEmail());
            writer.write(String.join("\t", inscription) + "\n");
            writer.close();

            objectOutputStream.writeUTF("Félicitations! " + form.getPrenom() + " est inscrit.e au cours " + form.getCourse().getCode() +".");
        } catch (ClassNotFoundException e) {
            System.err.println("Une erreur s'est produite lors de la lecture ou de l'écriture : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur à l'écriture du fichier : " + e.getMessage());
        }
    }
}

