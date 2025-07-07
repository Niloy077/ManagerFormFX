package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AllRecordsController {

  @FXML
  private Button deleteButton;

  @FXML
  private Button viewDetailsButton;

  @FXML
  private ListView<?> listView;

  @FXML
  private Button goBackButton;

  @FXML
  void handleDeleteButton(ActionEvent event) {

  }

  @FXML
  void handleGoBackButton(ActionEvent event) {

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InitialView.fxml"));
      Pane root = (Pane)fxmlLoader.load();

      Controller initialValueController = fxmlLoader.getController();
      Scene goBackToInitialView = new Scene(root);
      Stage primaryStage = (Stage)this.goBackButton.getScene().getWindow();
      primaryStage.setScene(goBackToInitialView);
      primaryStage.setTitle("Contacts Manager");
      primaryStage.show();
    }catch (Exception e){
      System.out.println(e.getMessage());
    }

  }

  @FXML
  void handleViewDetailsButton(ActionEvent event) {

  }

}
