package sample;

import DataBase.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
        ArrayList<Integer> _days = new ArrayList<>();
        switch (newValue) {
          case JANUARY:
          case MARCH:
          case MAY:
            for (int day = 0; day <= 31; day++) {
              _days.add(day);
            }
            this.dayObservableList = FXCollections.observableArrayList(_days);
            this.daySelectionComboBox.setItems(this.dayObservableList);
            break;

          case FEBRUARY:
            for (int day = 0; day <= 29; day++) {
              _days.add(day);
            }
            this.dayObservableList = FXCollections.observableArrayList(_days);
            this.daySelectionComboBox.setItems(this.dayObservableList);
            break;

          default:
            break;
        }
      });

    //you can read and write files here

//    try {
//      this.personDataBase = FileOperations.readFromFile("file/path");
//    }catch (Exception e){
//      System.out.println("File not found.");
//    }

    this.personDataBase = new ArrayList<>();

    //you can initialize list view and table views
    this.personsObservableList = FXCollections.observableArrayList(this.personDataBase);
    this.listView1.setItems(this.personsObservableList);

  }

  @FXML
  void handleViewSelectedItem(ActionEvent event) {

  }

  @FXML
  void handleDeleteButton(ActionEvent event) {

    //first we need to know which button was selected
      int selectedItemIndex = this.listView1.getSelectionModel().getSelectedIndex();
      System.out.println(selectedItemIndex);

      if (selectedItemIndex != -1) {
        this.personDataBase.remove(selectedItemIndex);
        System.out.println(this.personDataBase);
        this.refreshListView();
        //FileOperations.writeToFile("Path",this.personDataBase);
      }

      if(selectedItemIndex==-1){
        Stage primaryStage = (Stage)this.savebutton.getScene().getWindow();
        viewUtilities.showErrorMessageDialogueBox("Select a field from list to delete.",primaryStage);
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
//    System.out.println("pfrofile.");
    FileChooser fileChooser = new FileChooser();
    Stage primaryStage = (Stage)this.chooseProfileButton.getScene().getWindow();
    File selectedfile = fileChooser.showOpenDialog(primaryStage);

    if(selectedfile != null){
      String selectedFilePath = selectedfile.toURI().getPath();
      this.profilePhotoPath = selectedFilePath;
      System.out.println(selectedFilePath);
    }

  }

}
