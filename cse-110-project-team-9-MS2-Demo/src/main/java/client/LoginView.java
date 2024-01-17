package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class LoginView {
    private TextField usernameField, passwordField;
    private Button loginButton, createAccountButton;
    private GridPane grid;

    public LoginView() {
        grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);
        
        Label userNameLabel = new Label("Username:");
        usernameField = new TextField();
        grid.add(userNameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        passwordField = new TextField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);


        createAccountButton = new Button("Create Account");
        loginButton = new Button("Login");

        grid.add(createAccountButton, 0, 3);
        grid.add(loginButton, 1, 3);

    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public GridPane getGrid() {
        return grid;
    }

    public void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler) {
        createAccountButton.setOnAction(eventHandler);
    }

    public void setLoginButtonAction(EventHandler<ActionEvent> eventHandler) {
        loginButton.setOnAction(eventHandler);
    }

    public void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}