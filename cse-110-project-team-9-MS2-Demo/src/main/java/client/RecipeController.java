package client;

import static com.mongodb.client.model.Filters.jsonSchema;

import org.bson.json.JsonObject;

import client.View.RecipeList;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import org.json.JSONObject;


public class RecipeController {
    private static Model model;
    private Recipe recipe;
    private static RecipeList recipeList;
    private static View view;

    public RecipeController(Recipe recipe, Model model) {
        this.recipe = recipe;
        this.model = model;
        
        this.recipe.setGetButtonAction(this::handleGetButton);
        this.recipe.setDeleteButtonAction(this::handleDeleteButton);
        this.recipe.setShareButtonAction(this::handleShareButton);
    }

    public void handleDeleteButton(ActionEvent event) {

        Recipe curr = this.recipe;

        String response = model.performRequest("DELETE", null, null, recipe.getQueryRecipeLabelName());
        curr.getID();

        //recipeList.deleteRecipe(curr);
        System.out.println("delete: " + response);
    }

    private void handleGetButton(ActionEvent event) {
        Recipe curr = this.recipe;
        
        String response = model.performRequest("GET", null, null, recipe.getQueryRecipeLabelName());
        curr.getID();
        //System.out.print("GET RESPONSE: " + response);
 
       
        final String name;
        final String type;
        final String details;
        final String user;
        final String imageURL;

        
        JSONObject jsonObject = new JSONObject(response);
        String id = jsonObject.getString("_id");
        name = jsonObject.getString("name");
        type = jsonObject.getString("type");
        details = jsonObject.getString("details");
        user = jsonObject.getString("user");
        if (jsonObject.has("imageURL")){
            imageURL = jsonObject.getString("imageURL");
        }
        else imageURL = "https://media.wired.com/photos/5b8999943667562d3024c321/master/w_1600,c_limit/trash2-01.jpg";
        /*
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Details: " + details);
        */

        Platform.runLater(() -> {
            recipe.setRecipeName(name);
            recipe.setRecipeTotal(details);
            recipe.setRecipeText(details);
            recipe.setRecipeType(type);
            recipe.setUser(user);
            recipe.setID(id);
            recipe.setURL(imageURL);
            recipe.setDetailedView();
            recipe.getDetailedView().getStage().show();
            DetailedController detailedController = new DetailedController(recipe.getDetailedView(), model);
        });
    }
    public void handleShareButton(ActionEvent event){
        final String name;
        Recipe curr = this.recipe;
        String response = model.performRequest("GET", null, null, recipe.getQueryRecipeLabelName());

        JSONObject jsonObject = new JSONObject(response);
            String id = jsonObject.getString("_id");
            name = jsonObject.getString("name");

            System.out.println(name);
            
            Platform.runLater(() -> {
                recipe.setRecipeName(name);
                recipe.setShareView();
                recipe.getShareView().getStage().show();
        });
        }
}
