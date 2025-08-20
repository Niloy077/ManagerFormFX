package sample;

import DataBase.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

public class DetailViewController {

    @FXML
    private Label addressLabel;

    @FXML
    private Button closeButtonLabel;

    @FXML
    private Label doblabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label fullNamelabel;

    @FXML
    private Label mobileLabel;

    @FXML
    private ImageView profileImageLabel;

    // This method will be called from AllRecordsController
    public void setPersonDetails(Person person) {
        fullNamelabel.setText("Name: " + person.getFirstName() + " " + person.getSurname());
        mobileLabel.setText("Phone: " + person.getMobileNumber());
        addressLabel.setText("Address: " + person.getAddress());
        emailLabel.setText("Email: " + person.getEmailAddress());
        doblabel.setText("DOB: " + (person.getDateOfBirth() != null ? person.getDateOfBirth().toString() : ""));

        String imagePath = person.getPathToProfilePhoto();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Image image = new Image(new File(imagePath).toURI().toString());
                profileImageLabel.setImage(image);
            } catch (Exception e) {
                profileImageLabel.setImage(null);
            }
        } else {
            profileImageLabel.setImage(null);
        }
    }

    @FXML
    void handleCloseDetailView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AllRecords.fxml"));
            Pane root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) closeButtonLabel.getScene().getWindow(); // reuse current stage
            stage.setScene(scene);
            stage.setTitle("All Records");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
