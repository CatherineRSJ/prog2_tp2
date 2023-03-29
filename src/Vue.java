package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Vue extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        HBox root = new HBox();
        Scene scene = new Scene(root, 400, 150);

        VBox fenetre1 = new VBox();
        root.getChildren().add(fenetre1);

        Text liste = new Text("Liste des cours");
        liste.setFont(Font.font("Verdana", 25));
        fenetre1.getChildren().add(liste);

        fenetre1.getChildren().add(new Separator());
        
        Button gauche = new Button("Gauche");
        Button centre = new Button("Centre");
        Button droite = new Button("Droite");
        
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(gauche);
        buttonGroup.getChildren().add(centre);
        buttonGroup.getChildren().add(droite);
        buttonGroup.setAlignment(Pos.CENTER);
        fenetre1.getChildren().add(buttonGroup);

        root.getChildren().add(new Separator());

        Text formulaire = new Text("Formulaire d'inscription");
        root.getChildren().add(formulaire);

        CheckBox checkBox = new CheckBox("BLABLABLA");

        root.getChildren().add(checkBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
        
    public static void main(String[] args) {
        Vue.launch(args);
    }
}
