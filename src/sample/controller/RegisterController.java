package sample.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.model.User;
import sample.model.UserDAO;
import sample.utils.BCryptPassword;
import sample.utils.Validation;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField username_id;
    @FXML
    private PasswordField password_id;
    @FXML
    private PasswordField confPassword_id;
    @FXML
    private CheckBox adminCheckBox;
    @FXML
    private Label error_label;
    @FXML
    private TextField email_id;


    @FXML
    public void onRegisterButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        String username = username_id.getText();
        String password = password_id.getText();
        String confPassword = confPassword_id.getText();
        String email = email_id.getText();
        int isAdmin = 0;

        if (adminCheckBox.isSelected()) {
            isAdmin = 1;
        } else {
            isAdmin = 0;
        }


        if (!Validation.isValidUsername(username)) {
            error_label.setVisible(true);
            error_label.setText("Neteisingas vardas. Tik didziosios ir mazosios raides, skaiciai, ilgis nuo 5 iki 13 simboliu!");
        } else if (!Validation.isValidPassword(password)) {
            error_label.setVisible(true);
            error_label.setText("Neteisingas slaptazodis. Tik didžiosios ir mažosios raidės, skaičiai ir spec. ženklas !@#$%");
        } else if (!password.equals(confPassword)) {
            error_label.setVisible(true);
            error_label.setText("Ne vienodi suvesti slaptazodziai");
        } else if (!Validation.isValidEmail(email)) {
            error_label.setVisible(true);
            error_label.setText("El_Pasto adresas neteisingas");
        } else {
            String bCryptPassword = BCryptPassword.hashPassword(password);
            User user = new User(username, bCryptPassword, email, isAdmin);
            UserDAO.insert(user);
            Parent root1 = FXMLLoader.load(Main.class.getResource("view/login-view.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root1, 600, 400));
            loginStage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}