package sample;

import DataBase.DatabaseHelper;
import DataBase.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
public class addContactController {

  @FXML
  private TextField firstnameTextField;

  @FXML
  private TextField surnameTextField;

  @FXML
  private ComboBox<Integer> daySelectionComboBox;

  @FXML
  private ComboBox<Month> monthSelectionComboBox;

  @FXML
  private ComboBox<Integer> yearSelectionComboBox;

  @FXML
  private Button savebutton;

  @FXML
  private Button deleteButton;

  @FXML
  private TextField moblileNumberTextField;

  @FXML
  private TextField emailtextfield;

  @FXML
  private TextField addressTextField;

  @FXML
  private Button chooseProfileButton;

  @FXML
  private Button goBackButton1;
  @FXML
  private Button viewSlectedItem;

  @FXML
  private Button clearFormButton;

  @FXML
  private ImageView chosenProfileImage;

  @FXML
  private ListView<Person> listView1;

  private ObservableList<Person> personsObservableList;

  private ArrayList<Person> personDataBase;


  //Observable list is an interface and it defines a certain methods
  private ObservableList<Integer> dayObservableList;
  private ObservableList<Month> monthObservableList;
  private ObservableList<Integer> yearObservableList;

  //keep track of choosen profile pic
  private String profilePhotoPath = null;
  @FXML
  void initialize(){
    //System.out.println("initial");

    //we can initialize combo boxes with data
    ArrayList<Integer> days = new ArrayList<>();
    for (int day = 1;day<=30;day++){
      days.add(day);
    }

    ArrayList<Month> months = new ArrayList<>(
      Arrays.asList(Month.values())
    );

    ArrayList<Integer> years = new ArrayList<>();
    for (int year = 1930;year<=2050;year++){
      years.add(year);
    }

    this.dayObservableList = FXCollections.observableArrayList(days);
    this.monthObservableList = FXCollections.observableArrayList(months);
    this.yearObservableList = FXCollections.observableArrayList(years);

    this.daySelectionComboBox.setItems(this.dayObservableList);
    this.monthSelectionComboBox.setItems(this.monthObservableList);
    this.yearSelectionComboBox.setItems(this.yearObservableList);

    this.monthSelectionComboBox
            .getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
              if (newValue == null) {
                // maybe clear the day combo box too
                daySelectionComboBox.getItems().clear();
                return; // exit early
              }

              ArrayList<Integer> _days = new ArrayList<>();
              switch (newValue) {
                case JANUARY:
                case MARCH:
                case MAY:
                  for (int day = 1; day <= 31; day++) { // also fixed to start at 1
                    _days.add(day);
                  }
                  break;
                case FEBRUARY:
                  for (int day = 1; day <= 29; day++) {
                    _days.add(day);
                  }
                  break;
                default:
                  for (int day = 1; day <= 30; day++) {
                    _days.add(day);
                  }
                  break;
              }
              this.dayObservableList = FXCollections.observableArrayList(_days);
              this.daySelectionComboBox.setItems(this.dayObservableList);
            });

    // Set prompt text for TextFields
    firstnameTextField.setPromptText("First Name");
    surnameTextField.setPromptText("Surname");
    moblileNumberTextField.setPromptText("Mobile Number");
    emailtextfield.setPromptText("Email Address");
    addressTextField.setPromptText("Address");

    //you can read and write files here

//    try {
//      this.personDataBase = FileOperations.readFromFile("file/path");
//    }catch (Exception e){
//      System.out.println("File not found.");
//    }
    DatabaseHelper db = new DatabaseHelper();
    this.personDataBase = new ArrayList<>(db.getAllPersons());

    //you can initialize list view and table views
    this.personsObservableList = FXCollections.observableArrayList(this.personDataBase);
    this.listView1.setItems(this.personsObservableList);

  }

  @FXML
  void handleViewSelectedItem(ActionEvent event) {
    Person selectedPerson = listView1.getSelectionModel().getSelectedItem();

    if (selectedPerson == null) {
      // Optional: Show alert if nothing is selected
      Alert alert = new Alert(Alert.AlertType.WARNING, "No record selected!", ButtonType.OK);
      alert.showAndWait();
      return;
    }

    firstnameTextField.setText(selectedPerson.getFirstName());
    surnameTextField.setText(selectedPerson.getSurname());
    emailtextfield.setText(selectedPerson.getEmailAddress());
    addressTextField.setText(selectedPerson.getAddress());
    moblileNumberTextField.setText(selectedPerson.getMobileNumber());

    // Fill date combo boxes
    LocalDate dob = selectedPerson.getDateOfBirth();
    if (dob != null) {
      daySelectionComboBox.setValue(dob.getDayOfMonth());
      monthSelectionComboBox.setValue(dob.getMonth());
      yearSelectionComboBox.setValue(dob.getYear());
    } else {
      daySelectionComboBox.setValue(null);
      monthSelectionComboBox.setValue(null);
      yearSelectionComboBox.setValue(null);
    }

    // Load and set profile image
    String imagePath = selectedPerson.getPathToProfilePhoto(); // Assuming your Person class stores it
    if (imagePath != null && !imagePath.isEmpty()) {
      try {
        Image image = new Image(new File(imagePath).toURI().toString());
        chosenProfileImage.setImage(image);
        this.profilePhotoPath = imagePath; // <-- store in controller
      } catch (Exception e) {
        System.err.println("Could not load image: " + e.getMessage());
        chosenProfileImage.setImage(null);
        this.profilePhotoPath = null; // clear it if invalid
      }
    } else {
      chosenProfileImage.setImage(null);
      this.profilePhotoPath = null;
    }


    }


  @FXML
  void handleDeleteButton(ActionEvent event) {
    // get selected person
    Person selectedPerson = this.listView1.getSelectionModel().getSelectedItem();
    int selectedItemIndex = this.listView1.getSelectionModel().getSelectedIndex();

    if (selectedItemIndex != -1 && selectedPerson != null) {
      // remove from in-memory list
      this.personDataBase.remove(selectedItemIndex);
      this.refreshListView();

      // remove from database
      DatabaseHelper db = new DatabaseHelper();
      db.deletePerson(selectedPerson.getId().toString());

      System.out.println("Deleted: " + selectedPerson.getFirstName());
    } else {
      Stage primaryStage = (Stage)this.savebutton.getScene().getWindow();
      viewUtilities.showErrorMessageDialogueBox("Select a field from list to delete.", primaryStage);
    }
  }


  @FXML
  void handleGoBackButtonClick1(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InitialView.fxml"));
      Pane root = (Pane)fxmlLoader.load();

      Controller initialValueController = fxmlLoader.getController();
      Scene goBackToInitialView = new Scene(root);
      Stage primaryStage = (Stage)this.goBackButton1.getScene().getWindow();
      primaryStage.setScene(goBackToInitialView);
      primaryStage.setTitle("Contacts Manager");
      primaryStage.show();
    }catch (Exception e){
      System.out.println(e.getMessage());
    }


  }

  @FXML
  void handleSaveButtonClick(ActionEvent event) {
    //System.out.println("Save button clicked.");
    String firstName = this.firstnameTextField.getText();
    String surname = this.surnameTextField.getText();
    String mobileNumber = this.moblileNumberTextField.getText();
    String address = this.addressTextField.getText();
    String emailAddress = this.emailtextfield.getText();
    String pathToProfilePhoto = this.profilePhotoPath;

    Integer day = this.daySelectionComboBox.getSelectionModel().getSelectedItem();
    Month month = this.monthSelectionComboBox.getSelectionModel().getSelectedItem();
    Integer year = this.yearSelectionComboBox.getSelectionModel().getSelectedItem();


    try {
      Person newPerson = new Person(firstName,surname,mobileNumber,address,day,month,year,emailAddress,pathToProfilePhoto);
      System.out.println(newPerson);
      this.personDataBase.add(newPerson);
      //FileOperations.writeToFile("C:\\Users\\Administrator\\IdeaProjects\\TestjavaFx\\src\\database.txt",this.personDataBase);

      this.refreshListView();
      //this.resetUI();

      //save to database
      DatabaseHelper db = new DatabaseHelper();
      db.insertPerson(newPerson);
    }catch (Exception exception){
      Stage primaryStage = (Stage)this.savebutton.getScene().getWindow();
      viewUtilities.showErrorMessageDialogueBox(exception.getMessage(),primaryStage);
    }

  }

  public void refreshListView(){

    this.personsObservableList = FXCollections.observableArrayList(this.personDataBase);
    this.listView1.setItems(this.personsObservableList);
    this.listView1.refresh();
  }

  public void resetUI(){
    this.firstnameTextField.setText("");
    this.surnameTextField.setText("");
    this.moblileNumberTextField.setText("");
    this.addressTextField.setText("");
    this.daySelectionComboBox.setValue(null);
    this.monthSelectionComboBox.setValue(null);
    this.yearSelectionComboBox.setValue(null);
    this.emailtextfield.setText("");
  }

  @FXML
  void handlechooseProfileButton(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    Stage primaryStage = (Stage) this.chooseProfileButton.getScene().getWindow();
    File selectedFile = fileChooser.showOpenDialog(primaryStage);

    if (selectedFile != null) {
      this.profilePhotoPath = selectedFile.getAbsolutePath(); // ✅ safer
      System.out.println(this.profilePhotoPath);

      Image image = new Image(new File(this.profilePhotoPath).toURI().toString());
      this.chosenProfileImage.setImage(image);
    }
  }

  @FXML
  void handleClearForm(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Clear Form");
    alert.setHeaderText("Are you sure you want to clear the form?");
    alert.setContentText("This will erase all entered information.");

    ButtonType yesButton = new ButtonType("Yes");
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(yesButton, noButton);

    alert.showAndWait().ifPresent(type -> {
      if (type == yesButton) {
        resetUI(); // ✅ Your existing method to clear fields
        chosenProfileImage.setImage(null); // Clear profile image too
        profilePhotoPath = null;           // Reset photo path
      }
    });
  }


}
