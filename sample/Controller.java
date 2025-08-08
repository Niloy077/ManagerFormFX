package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Controller {

  @FXML
  private Button viewAllContactsButton;

  @FXML
  private Button exitButton;

  @FXML
  private Button addContactsButton;

  @FXML
  private Button chooseProfileButton;

  @FXML
  void handleAddContactsButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddContacts.fxml"));
      Pane root = (Pane)fxmlLoader.load();

      addContactController addContactController = fxmlLoader.getController();
      Scene addContactsScene = new Scene(root);
      Stage primaryStage = (Stage)this.addContactsButton.getScene().getWindow();
      primaryStage.setScene(addContactsScene);
      primaryStage.setTitle("Add Contact");
      primaryStage.show();
    }catch (Exception e){
      System.out.println(e.getMessage());
    }


  }

  @FXML
  void handleExitButton(ActionEvent event) {
    Stage currentStage =(Stage) this.exitButton.getScene().getWindow();
    currentStage.close();

  }


  @FXML
  void handleViewAllContactsButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllRecords.fxml"));
      Pane root = (Pane)fxmlLoader.load();

      AllRecordsController allRecordsController = fxmlLoader.getController();
      Scene addContactsScene = new Scene(root);
      Stage primaryStage = (Stage)this.viewAllContactsButton.getScene().getWindow();
      primaryStage.setScene(addContactsScene);
      primaryStage.setTitle("All Records");
      primaryStage.show();
    }catch (Exception e){
      System.out.println(e.getMessage());
    }

  }

  @FXML
  void handlechooseProfileButton(ActionEvent event){
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose Profile Picture");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
    );

    File selectedFile = fileChooser.showOpenDialog(chooseProfileButton.getScene().getWindow());
    if(selectedFile != null){
      Path dest = Paths.get("profile_pics",selectedFile.getName());
      try{
        Files.copy(selectedFile.toPath(),dest, StandardCopyOption.REPLACE_EXISTING);
      }catch (IOException e){
        e.printStackTrace();
      }
    }
  }

}
