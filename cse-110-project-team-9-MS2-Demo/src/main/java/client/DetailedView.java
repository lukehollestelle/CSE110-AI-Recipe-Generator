package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.rmi.RMISecurityException;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.sampled.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DetailedView {
    private Stage stage;
    private TextArea recipeAsText;
    private Recipe recipe;
    private Button closeButton;
    private String recipeText;
    private Image image;
    private ImageView imageView;


    public DetailedView(Recipe inputRecipe, String recipeString, String imageURL) {
        stage = new Stage();
        recipe = inputRecipe;
        stage.setTitle("Detailed View");
        recipeText = recipe.getRecipeTotal();  // Initialize recipeText with the recipe's total
        BorderPane mainLayout = createMainLayout(recipe, recipeString, imageURL);
        Scene detailedScene = new Scene(mainLayout, 600, 600);
        stage.setScene(detailedScene);
    }

    public void setRecipeTotal(String total){
        recipeText = total;
    }

    public TextArea getRecipeAsText(){
        return recipeAsText;
    }

    public Stage getStage(){
        return stage;
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public String getRecipeName(){
        return recipe.getRecipeLabelName();
    }

    public String getRecipeType(){
        return recipe.getRecipeType();
    }

    public String getRecipeTotal(){
        return recipe.getRecipeTotal();
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private BorderPane createMainLayout(Recipe recipe, String totalRecipe, String imageURL) {

        BorderPane root = new BorderPane();
        DVHeader header = new DVHeader();
        DVFooter footer = new DVFooter(recipe);

        recipeAsText = new TextArea(totalRecipe);// create editable text area
        recipeAsText.setEditable(false);
        ScrollPane scroll = new ScrollPane(recipeAsText);

        imageView = new ImageView();
        image = new Image(imageURL);
        imageView.setImage(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);


        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        root.setTop(header);
        root.setCenter(scroll);
        root.setBottom(footer);
        root.setRight(imageView);
        return root;
    }

    public String saveNewRecipe() {
        return recipeAsText.getText();
    }



    class DVHeader extends BorderPane {
        DVHeader() {
            setPrefSize(500, 60);
            setStyle("-fx-background-color: #e8e113;");

            Text titleText = new Text("Detailed View");
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-background-color: Orange");
            setCenter(titleText);
        }
    }
    
    public void setPutButtonAction(EventHandler<ActionEvent> eventHandler) {
        closeButton.setOnAction(eventHandler);
        }

    class DVFooter extends HBox {


        // private Button ediButton;
        DVFooter(Recipe recipe) {
            setPrefSize(500, 60);
            setStyle("-fx-background-color: #F0F8FF;");
            setSpacing(15);
            /*
             * Button saveButton = new Button("Save Recipe");//is back button save?
             * saveButton.setOnAction(e -> {
             * 
             * close();
             * });
             */
            // saveButton.setStyle("-fx-font-weight: bold; -fx-font-size: 20;
            // -fx-background-color: Green");

            Button editButton = new Button("Edit Recipe");

            editButton.setOnAction(e -> {
                recipeAsText.setEditable(true);
                editButton.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-background-color: Green");
            });
            // editButton.setOnAction(e->
            // recipeText=editText()
            // );
            editButton.setStyle("-fx-font-weight: bold; -fx-font-size: 20");
            /*
             * Button saveButton = new Button("Save Changes");
             * saveButton.setOnAction(e -> {
             * recipeAsText.setEditable(false);
             * });
             */
            closeButton = new Button("Save and Close Detailed View");
             
            closeButton.setOnAction(e -> {
                recipeAsText.setEditable(false);
                recipe.setRecipeTotal(recipeAsText.getText());
                stage.close();
            });// add method to delete the recipe as a whole
            

            closeButton.setStyle("-fx-font-weight: bold; -fx-font-size: 20");

            // getChildren().addAll(saveButton);
            getChildren().addAll(editButton);
            getChildren().addAll(closeButton);
            setAlignment(Pos.CENTER);
        }

    }
}
