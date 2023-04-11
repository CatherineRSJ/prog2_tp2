package src;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;

/**
 * La classe Vue représente un client utilisant un interface graphique.
 * La classe est une extension de la classe {@link Application}.
 */
public class Vue extends Application {
    private Controlleur controller;
    
    /**
     * [MÉTHODE] - Elle instancie un {@link Controlleur controlleur}.
     * Elle génère un interface graphique permettant à l'utilisateur de charger les cours et de s'inscrire à un de ceux-ci.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controlleur(1337);

        Alert alerte = new Alert(AlertType.ERROR);

        primaryStage.setResizable(false);
        HBox root = new HBox(30);
        root.setPadding(new Insets(15,10,15,10));
        Scene scene = new Scene(root, 600, 450);
        root.setStyle("-fx-background-color: #EEEEE4;");

        VBox fenetre1 = new VBox(15);
        root.getChildren().add(fenetre1);

        Text liste = new Text("Liste des cours");
        liste.setFont(Font.font("Verdana", 18));
        fenetre1.getChildren().add(liste);

        TableView<Course> table = new TableView<Course>();
        table.setEditable(true);
        TableColumn<Course, String> code = new TableColumn<>("Code");
        code.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCode()));
        code.prefWidthProperty().bind(table.widthProperty().multiply(0.30));
        code.setResizable(false);
        TableColumn<Course, String> cours = new TableColumn<>("Cours");
        cours.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
        cours.prefWidthProperty().bind(table.widthProperty().multiply(0.70));
        cours.setResizable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(code, cours);
        fenetre1.getChildren().add(table);

        fenetre1.getChildren().add(new Separator());

        HBox select = new HBox(40);
        fenetre1.getChildren().add(select);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Automne", "Hiver", "Ete");
        comboBox.getSelectionModel().selectFirst();
        select.getChildren().add(comboBox);

        Button charger = new Button("charger");
        charger.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                try{
                    String sessionSelect = comboBox.getValue();
                    ObservableList<Course> courses = FXCollections.observableArrayList(controller.loadCourses(sessionSelect));
                    table.setItems(courses);
                }catch(IOException ex){

                }catch(ClassNotFoundException ex){

                }
            }
        });
        select.getChildren().add(charger);
        select.setAlignment(Pos.CENTER);

        VBox fenetre2 = new VBox(20);
        root.getChildren().add(fenetre2);

        Text inscription = new Text("Formulaire d'inscription");
        inscription.setFont(Font.font("Verdana", 18));
        fenetre2.getChildren().add(inscription);

        HBox formulaire = new HBox(20);
        fenetre2.getChildren().add(formulaire);
        VBox texte = new VBox(20);
        formulaire.getChildren().add(texte);
        Text pr = new Text("Prénom");
        Text no = new Text("Nom");
        Text em = new Text("Email");
        Text ma = new Text("Matricule");
        texte.getChildren().addAll(pr, no, em, ma);
        texte.setAlignment(Pos.CENTER_LEFT);

        VBox champ = new VBox(10);
        formulaire.getChildren().add(champ);
        TextField prenom = new TextField();
        TextField nom = new TextField();
        TextField email = new TextField();
        TextField matricule = new TextField();
        champ.getChildren().addAll(prenom, nom, email, matricule);
        champ.setAlignment(Pos.CENTER_LEFT);
        
        Button envoyer = new Button("envoyer");
        envoyer.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                ArrayList<String> reponse = controller.register(
                    table.getSelectionModel().getSelectedItem(), 
                    prenom.getText(), 
                    nom.getText(), 
                    email.getText(), 
                    matricule.getText()
                );
                if(reponse.get(0) == "succes"){
                    alerte.setAlertType(AlertType.INFORMATION);
                    alerte.setContentText(reponse.get(1));
                    prenom.setText("");
                    nom.setText("");
                    email.setText("");
                    matricule.setText("");
                }else{
                    alerte.setAlertType(AlertType.ERROR);
                    alerte.setContentText(String.join("\n", reponse));
                }
                alerte.show();
            }
        });
        fenetre2.getChildren().add(envoyer);

        root.setAlignment(Pos.CENTER);
        fenetre1.setAlignment(Pos.TOP_CENTER);
        fenetre2.setAlignment(Pos.TOP_CENTER);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
     /**
     * [MÉTHODE] - Il s'agit de la méthode exécutable de Vue.
     * Elle fait appel à la méthode {@link Application#launch launch} de la classe {@link Application}.
     * @param args Liste des paramètres de l'exécutable - Ne sont pas utilisés
     */
    public static void main(String[] args) {
        Vue.launch(args);
    }
}
