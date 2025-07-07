package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {

  @FXML
  private Button viewAllContactsButton;



  @FXML
  private Button exitButton;

  @FXML
  private Button addContactsButton;

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

}
