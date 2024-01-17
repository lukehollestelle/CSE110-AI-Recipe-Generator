package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class ShareView {
    private Label linkLabel;
    private Button close;
    private Stage stage;
    private TextField link;

    public ShareView(Recipe recipe, String sharableUrl) {
        
        linkLabel = new Label("Share Link");
        String defaultLabelStyle = "-fx-pref-height: 30px; -fx-text-fill: black;";
        linkLabel.setStyle(defaultLabelStyle);


        link = new TextField(sharableUrl);
        close = new Button("Close");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(linkLabel, link, close);
        vbox.setAlignment(Pos.CENTER);

        stage = new Stage();
        stage.setWidth(500);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Share Recipe");
        stage.setScene(new Scene(vbox));

        close.setOnAction(e -> {
            stage.close();
        });
    }
    public Stage getStage() {
        return stage;
    }
}