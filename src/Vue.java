package src;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;

public class Vue extends Application {
    private TableView<Course> table;
    private Controlleur controller;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controlleur(1337);

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
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Course, String> cours = new TableColumn<>("Cours");
        code.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(code, cours);
        this.table = table;
        fenetre1.getChildren().add(table);

        fenetre1.getChildren().add(new Separator());

        HBox select = new HBox(40);
        fenetre1.getChildren().add(select);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Automne", "Hiver", "Été");
        comboBox.getSelectionModel().selectFirst();
        select.getChildren().add(comboBox);

        Button charger = new Button("charger");
        charger.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                try{
                    ObservableList<Course> courses = controller.loadCourses("Hiver");
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
        fenetre2.getChildren().add(envoyer);

        root.setAlignment(Pos.CENTER);
        fenetre1.setAlignment(Pos.TOP_CENTER);
        fenetre2.setAlignment(Pos.TOP_CENTER);
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // @Override
    // public void stop(){
    //     try{
    //         controller.disconnect();
    //     }catch(IOException e){
            
    //     }
    // }

    // public void remplirTableau(Course[] cours){
    //     table.getItems().addAll(cours);
    // }
        
    public static void main(String[] args) {
        Vue.launch(args);
    }
}
