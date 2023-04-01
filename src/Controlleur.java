package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.models.Course;
import server.models.RegistrationForm;

public class Controlleur {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    /**
    */
    public Controlleur(int port) {
        // try{
        //     socket = new Socket("127.0.0.1", port);
        //     objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        //     objectInputStream = new ObjectInputStream(socket.getInputStream());
        // }catch(IOException error){
        //     System.out.println(error.getMessage());
        // }
    }

    private void connect() throws IOException {
        socket = new Socket("127.0.0.1", 1337);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
    */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }

    public ArrayList<Course> loadCourses(String session) throws IOException, ClassNotFoundException {
        this.connect();
        

        objectOutputStream.writeObject("CHARGER " + session);

        ArrayList<Course> line;
        line = (ArrayList<Course>)objectInputStream.readObject();

        this.disconnect();
        return line;
    }

    public ArrayList<String> register(Course cours, String prenom, String nom, String email, String matricule) throws IOException{
        ArrayList<String> reponse = new ArrayList<String>();

        if(cours == null)
            reponse.add("Veuillez sélectionner un cours.");
        if(prenom.trim().length() == 0)
            reponse.add("Veuillez entrer un prénom.");
        if(nom.trim().length() == 0)
            reponse.add("Veuillez entrer un nom");

        if(email.trim().length() == 0){
            reponse.add("Veuillez entrer un email.");
        }else{
            // regex pris en ligne: https://www.javatpoint.com/java-email-validation
            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if(!matcher.matches()){
                reponse.add("Le email entré est invalide.");
            }
        }

        if(matricule.trim().length() == 0){
            reponse.add("Veuillez entrer un matricule.");
        }else{
            // on s'assure que matricule est un nombre
            try{
                Integer.parseInt(matricule);

                if(matricule.length() != 8)
                    throw new NumberFormatException();
            }catch(NumberFormatException e){
                reponse.add("Le matricule entré est invalide.");
            }
        }

        if(reponse.size() == 0){
            this.connect();

            RegistrationForm form  = new RegistrationForm(prenom, nom, email, matricule, cours);

            objectOutputStream.writeObject("INSCRIRE");
            objectOutputStream.writeObject(form);

            reponse.add("succes"); // pour afficher une alerte succès
            reponse.add(objectInputStream.readUTF());

            this.disconnect();
        }else{
            reponse.add(0, "Le formulaire est invalide.");
        }

        return reponse;
    }
}
