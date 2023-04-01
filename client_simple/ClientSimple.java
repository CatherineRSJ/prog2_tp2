package client_simple;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSimple {
    private static Socket socket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    private static String choisirSession(){
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
        System.out.print("1. Automne\n2. Hiver\n3. Ete\n> Choix: ");
        return System.console().readLine();
    }

    public static void main(String[] args) {
        try {
            socket = new Socket("127.0.0.1", 1337);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println("***Bienvenue au portail d'inscription au cours de l'UdeM***");

            String choix = "";
            while(!choix.equals("2")){
                choisirSession();
            }
        } catch (IOException e) {

        }
    }
}