package src;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Vue extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        HBox root = new HBox(30);
        Scene scene = new Scene(root, 600, 400);
        root.setStyle("-fx-background-color: #f5f7d2;");

        VBox fenetre1 = new VBox(20);
        root.getChildren().add(fenetre1);

        Text liste = new Text("Liste des cours");
        liste.setFont(Font.font("Verdana", 18));
        fenetre1.getChildren().add(liste);

        HBox boite = new HBox(10);
        fenetre1.getChildren().add(boite);
        Label code = new Label("Code");
        Label cours = new Label("Cours");
        boite.getChildren().addAll(code, cours);

        fenetre1.getChildren().add(new Separator());

        HBox select = new HBox(30);
        fenetre1.getChildren().add(select);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Option 1", "Option 2", "Option 3");
        select.getChildren().add(comboBox);

        Button charger = new Button("charger");
        select.getChildren().add(charger);

        root.getChildren().add(new Separator());

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
        fenetre2.getChildren().add(envoyer);

        root.setAlignment(Pos.CENTER);
        fenetre1.setAlignment(Pos.TOP_CENTER);
        fenetre2.setAlignment(Pos.TOP_CENTER);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
        
    public static void main(String[] args) {
        Vue.launch(args);
    }
}
