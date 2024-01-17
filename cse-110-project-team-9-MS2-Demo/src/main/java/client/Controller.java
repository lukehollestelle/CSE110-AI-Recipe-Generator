package client;

import client.View.RecipeList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
    private static View view;
    private static Model model;
    //private Stage primaryStage;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        //this.primaryStage = primaryStage;
        
        this.view.setSaveButtonAction(this::handlePostButton);
        this.view.setDeleteButtonAction(this::handleDeleteButton);
        this.view.setLogOutAction(this::handleLogoutButton);
    }

    private void handlePostButton(ActionEvent event) {

        Recipe curr = view.getRecipe();
        curr.setRecipeName(view.getRecipeName());
        curr.setRecipeType(view.getRecipeType());
        
        String[] typeAndRecipe = new String[]{curr.getRecipeType(), view.getUser(), curr.getURL(), curr.getRecipeTotal()};
        String response = model.performRequest("POST", curr.getQueryRecipeLabelName(), typeAndRecipe, null);
        curr.setID(response);
        
        System.out.print("POST RESPONSE: " + response);
        
        new Thread(() -> {
            view.getRecipeList().fetchRecipesFromMongoDB();
            Platform.runLater(() -> {
                view.getRecipeList().updateRecipeListView();
            });
        }).start();
        
    }
    // this code is not being used, using recipe controller for delete
    public void handleDeleteButton(ActionEvent event) {

        Recipe curr = view.getRecipe();

        String response = model.performRequest("DELETE", null, null, curr.getQueryRecipeLabelName());
        curr.getID();
        
        System.out.println("delete: " + response);

        new Thread(() -> {
            view.getRecipeList().fetchRecipesFromMongoDB();
            Platform.runLater(() -> {
                view.getRecipeList().updateRecipeListView();
            });
        }).start();

    }
    public void handleLogoutButton(ActionEvent event){
        String[] userAndPassword = new String[]{null, view.getUser(), null, null};
        System.out.println(view.getUser());
        String response = model.performRequest("POST", "logout", userAndPassword, null);
        System.out.println(response);

        LoginView login = new LoginView();
            Model model1 = new Model();
            LoginController controllerLogin = new LoginController(login, model1, view.getAppFrame());
            Scene scene = new Scene(login.getGrid(), 400, 150);
            view.getAppFrame().setScene(scene);
            view.getAppFrame().setTitle("MyServerUI");
            view.getAppFrame().show();
        }
}