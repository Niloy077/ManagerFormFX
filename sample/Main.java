/*
  CSE215L, Section 7
  Group Theta : Project (Contacts Manager)
  Niloy Biswas,2031429
 */
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("InitialView.fxml"));
        primaryStage.setTitle("Contacts Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.setMinHeight(768);
        primaryStage.setMinWidth(1024);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
