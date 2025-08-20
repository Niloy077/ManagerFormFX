package sample;

import DataBase.DatabaseHelper;
import DataBase.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class AllRecordsController {

  @FXML
  private Button deleteButton;

  @FXML
  private Button viewDetailsButton;

  @FXML
  private ListView<Person> listView;

  @FXML
  private Button goBackButton;

  private ObservableList<Person> observablePersonList;

  private DatabaseHelper db;

  @FXML
  void initialize() {
    db = new DatabaseHelper();

    // Fetch all contacts from DB
    List<Person> allContacts = db.getAllPersons();
    observablePersonList = FXCollections.observableArrayList(allContacts);

    // Set items in ListView
    listView.setItems(observablePersonList);
  }

  @FXML
  void handleDeleteButton(ActionEvent event) {
    Person selectedPerson = listView.getSelectionModel().getSelectedItem();

    if (selectedPerson != null) {
      // Remove from DB
      db.deletePerson(selectedPerson.getId().toString());

      // Remove from ListView
      observablePersonList.remove(selectedPerson);
    } else {
      Stage primaryStage = (Stage) deleteButton.getScene().getWindow();
      viewUtilities.showErrorMessageDialogueBox("Select a contact to delete.", primaryStage);
    }
  }

  @FXML
  void handleGoBackButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InitialView.fxml"));
      Pane root = (Pane) fxmlLoader.load();

      Controller initialValueController = fxmlLoader.getController();
      Scene scene = new Scene(root);
      Stage primaryStage = (Stage) this.goBackButton.getScene().getWindow();
      primaryStage.setScene(scene);
      primaryStage.setTitle("Contacts Manager");
      primaryStage.show();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @FXML
  void handleViewDetailsButton(ActionEvent event) {
    Person selectedPerson = listView.getSelectionModel().getSelectedItem();
    if (selectedPerson == null) {
      Stage primaryStage = (Stage) viewDetailsButton.getScene().getWindow();
      viewUtilities.showErrorMessageDialogueBox("Select a contact to view details.", primaryStage);
      return;
    }

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailView.fxml"));
      Pane root = loader.load();

      DetailViewController controller = loader.getController();
      controller.setPersonDetails(selectedPerson); // pass the Person object to detail view

      Scene scene = new Scene(root);
      Stage stage = (Stage) viewDetailsButton.getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Contact Details");
      stage.show();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
