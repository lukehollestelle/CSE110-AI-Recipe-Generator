package client;

import org.json.JSONObject;

import client.View.RecipeList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LoginController {
    private LoginView view;
    private Model model;
    private Stage primaryStage;

    public LoginController(LoginView view, Model model, Stage primaryStage) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;

        
        this.view.setCreateAccountButtonAction(this::handleCreateAccountButton);
        this.view.setLoginButtonAction(this::handleLoginButton);
    }

    public void handleCreateAccountButton(ActionEvent event){
        System.out.print("Creating Account");
        String[] userAndPassword = new String[]{view.getUsername(), view.getPassword(), null, null};
        String response = model.performRequest("POST", "login", userAndPassword, null);
        System.out.print("Create Account response: " + response);
        if(response.equals("Username already taken")){
            view.showAlert(response);
            return;
        }
        successfulLogin(view.getUsername());
    }

    public void handleLoginButton(ActionEvent event){
        System.out.print("Logging in");
        String response = model.performRequest("GET", "login", null, "login" + view.getUsername());
        System.out.print("Login response: " + response);
        if(response.equals("Incorrect username or password")){
            view.showAlert(response);
            return;
        }
        JSONObject jsonObject = new JSONObject(response);
        String password = jsonObject.getString("password");
        if(password.equals(view.getPassword())){
            successfulLogin(view.getUsername());
        }else{
            view.showAlert("Incorrect username or password");
        }
    }

    public void successfulLogin(String username){
        View view = new View(primaryStage, username);
        Controller controller = new Controller(view, model);
        view.getAppFrame().show();
        new Thread(() -> {
            while (true) {
                try {
                    view.getRecipeList().fetchRecipesFromMongoDB();//EDIT THIS TO BE ONLY THE USERS RECIPES
    
                    Platform.runLater(() -> {
                        view.getRecipeList().updateRecipeListView();
                    });
                    Thread.sleep(2500); //sleep time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
