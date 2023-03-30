package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.collections.ObservableList;
import server.models.Course;

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

    /**
    */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }

    public ObservableList<Course> loadCourses(String session) throws IOException, ClassNotFoundException {
        socket = new Socket("127.0.0.1", 1337);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        objectOutputStream.writeObject("CHARGER " + session);

        ObservableList<Course> line;
        line = (ObservableList<Course>)objectInputStream.readObject();
        // return line;
        // if ((line = objectInputStream.readObject()) != null) {
        //     // à supprimer
        //     System.out.println(line);

        //     // Pair<String, String> parts = processCommandLine(line);
        //     // String cmd = parts.getKey();
        //     // String arg = parts.getValue();
        //     // this.alertHandlers(cmd, arg);
        // }

        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
        return line;
    }
}
