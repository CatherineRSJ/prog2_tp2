package client_simple;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import server.models.Course;
import server.models.RegistrationForm;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientSimple {
    private static Socket socket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static ArrayList<Course> cours;

    private static String choisirSession(){
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
        System.out.print("1. Automne\n2. Hiver\n3. Ete\n> Choix: ");
        String session = "";
        
        while(session.equals("")){
            String choix = System.console().readLine();
            switch (choix) {
                case "1":
                    session = "Automne";
                    break;
                case "2":
                    session = "Hiver";
                    break;
                case "3":
                    session = "Ete";
                    break;
                default:
                    break;
            }
        }
        return session;
    }

    private static String choisirCours(String session) throws IOException, ClassNotFoundException{
        System.out.println("Les cours offerts pendant la session d'" + session.toLowerCase() + " sont:");
        
        cours = loadCourses(session);

        for (int i = 0; i < cours.size(); i++) {
            System.out.println("- " + cours.get(i).getCode() + "\t" + cours.get(i).getName());
        }

        System.out.println("\n Veuillez faire un choix:");
        System.out.print("1. Consulter les cours offerts pour une autre session\n2. Inscription à un cours\n> Choix: ");

        String choix = "";
        while(choix.equals("")){
            switch (System.console().readLine()) {
                case "1":
                    choix = "Sessions";
                    break;
                case "2":
                    choix = "Inscription";
                    break;
                default:
                    break;
            }
        }
        return choix;
    }

    public static void main(String[] args) {
        try {
            System.out.println("***Bienvenue au portail d'inscription au cours de l'UdeM***");

            String choix = "";
            while(!choix.equals("2")){
                choix = "";
                String session = choisirSession();
                if(choisirCours(session).equals("Inscription")){
                    remplirFormulaire();

                    System.out.println("\nVeuillez faire un choix pour continuer:");
                    System.out.print("1. Consulter la liste des sessions\n2. Quitter\n> Choix: ");
                    
                    while(choix.equals("")){
                        switch (System.console().readLine()) {
                            case "1":
                                choix = "1";
                                break;
                            case "2":
                                choix = "2";
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
    }

    /**
    */
    private static void connect() throws IOException {
        socket = new Socket("127.0.0.1", 1337);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
    */
    public static void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }

    public static ArrayList<Course> loadCourses(String session) throws IOException, ClassNotFoundException {
        connect();

        objectOutputStream.writeObject("CHARGER " + session);

        ArrayList<Course> line;
        line = (ArrayList<Course>)objectInputStream.readObject();

        disconnect();
        return line;
    }

    public static void remplirFormulaire() throws IOException{
        String prenom = "";
        String nom = "";
        String email = "";
        String matricule = "";
        Course coursTrouve = null;

        do {
            System.out.print("Veuillez saisir votre prénom: ");
            prenom = System.console().readLine().trim();
        } while (prenom.equals(""));
            
        do {
            System.out.print("Veuillez saisir votre nom: ");
            nom = System.console().readLine().trim();
        } while (nom.equals(""));
        
        do {
            System.out.print("Veuillez saisir votre email: ");
            String ligne = System.console().readLine().trim();
            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(ligne);
            if(matcher.matches()){
                email = ligne;
            }
        } while (email.equals(""));
        
        do {
            System.out.print("Veuillez saisir votre matricule: ");
            String ligne = System.console().readLine().trim();
            try{
                Integer.parseInt(ligne);

                if(ligne.length() != 8)
                    throw new NumberFormatException();
                    
                matricule = ligne;
            }catch(NumberFormatException e){
                // forcer l'utilisteur à entrer un matricule valide
            }
        } while (matricule.equals(""));

        do {
            System.out.print("Veuillez saisir le code du cours: ");
            String code = System.console().readLine().trim();

            for (int i = 0; i < cours.size(); i++) {
                if(code.equals(cours.get(i).getCode()))
                    coursTrouve = cours.get(i);
            }
        } while (coursTrouve == null);
        
        connect();

        RegistrationForm form  = new RegistrationForm(prenom, nom, email, matricule, coursTrouve);

        objectOutputStream.writeObject("INSCRIRE");
        objectOutputStream.writeObject(form);

        System.out.println(objectInputStream.readUTF());

        disconnect();
    }
}

