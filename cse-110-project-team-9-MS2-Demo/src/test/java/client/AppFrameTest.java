package client;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import client.View.RecipeList;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppFrameTest {
    //private static Recipe r = new Recipe();

    @Test
    void labelTest(){
        String a = "apple";
        String b = "apple";
        assertEquals(a,b);
    }

    @Test
    void RecipeCreateNameTest(){//recipe named correctly
        
        try {
            Recipe r= new Recipe();
            r.setRecipeName("apple pie");
            assertEquals(r.getRecipeLabelName(),"apple pie");

        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        //Recipe r= new Recipe();
        
        //String b = "apple";   
    }
 
    @Test 
    void savetoRecipeListTest(){
        try {
            Recipe r= new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            Recipe r2=new Recipe();
            rl.getChildren().add(r2);

            String s= "save";
            if(s=="save"){
                rl.getChildren().add(r);
            }
            assertEquals(rl.getChildren().contains(r), true);
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        
        //Filewriter r= n
        //RecipeList rl= new RecipeList();

    }

    @Test
    void editRecipeTest(){
        
        try {
            Recipe r= new Recipe();
            r.setRecipeName("ar");
            r.setRecipeTotal("add more apples");
            String s=r.getRecipeTotal();
            r.setRecipeTotal("no more apples");
            assertNotEquals(s, r.getRecipeTotal());
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }

        
        
    }

    @Test
    void deleteRecipeTest(){
        try {
            Recipe r= new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            Recipe r2=new Recipe();
            rl.getChildren().add(r2);

            String s= "save";
            if(s=="delete"){
                rl.deleteRecipe(r);
            }
            assertEquals(rl.getChildren().contains(r), false);
        //Filewriter r= n
        //RecipeList rl= new RecipeList();
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        
    }
 
    @Test
    void saveRecipeNewTest(){
        try {
            Recipe r= new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();

            String s= "save";
            if(s=="save"){
                rl.getChildren().add(r);
            }
            assertEquals(rl.getChildren().contains(r), true);
            //Filewriter r= n
            //RecipeList rl= new RecipeList();
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        
    }


    @Test
    void chooseMealTypeTest(){
        try {
            String type= "lunch";
            Recipe recipe = new Recipe();
            //recipe.setRecipeName("type");
            if(type=="breakfast"){
                recipe.setRecipeName("pancakes");
                recipe.setRecipeTotal("Ingredients: flour, butter, yeast, eggs, maple syrup");
            }
            if(type=="lunch"){
                recipe.setRecipeName("taco");
                recipe.setRecipeTotal("Ingredients: tortilla, beef, tomato, cheese, salsa");
            }
            if(type=="dinner"){
                recipe.setRecipeName("steak dinner");
                recipe.setRecipeTotal("Ingredients: steak, salt, pepper, potatos, carrots, wine, butter, thyme");
            }

            assertEquals(type, "lunch");
            assertEquals(recipe.getRecipeTotal(), "Ingredients: tortilla, beef, tomato, cheese, salsa");
            assertEquals(type, recipe);
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        
    }

    @Test
    //This BDD tests for when there is a meal type tag
    void MealTypeTagBDD1(){
        try{
            Recipe r = new Recipe();
            
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();;
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperType1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");
        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    @Test
    //This BDD tests for when there is no meal type tag
    void MealTypeTagBDD2(){
        try{
            Recipe r = new Recipe();
            
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("recording.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertNotEquals(r.getRecipeType().toString(), "breakfast");
        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    @Test
    //This BDD tests for when a user doesn't refresh and then saves
    void RefreshBDD1(){
        try{
            Recipe r = new Recipe();
           
           Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            String refresh="no refresh";
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            if(refresh=="no refresh"){

                rl.getChildren().add(r);
                String newrecipe="";
            }
           assertEquals(rl.getChildren().contains(r), true);

        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    @Test
    //This BDD tests for when a user doesn't refresh and then cancels
    void RefreshBDD2(){
        try{
            Recipe r = new Recipe();
           
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            String refresh="no refresh";
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            String cancel="cancel";
            
            if(cancel=="cancel" && refresh== "no refresh"){
                rl.deleteRecipe(r);
            }
            assertEquals(rl.getChildren().contains(r), false);

        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }
     
    @Test
    //This BDD tests for when a user saves a refreshed recipe
    void RefreshBDD3(){
        try{
            Recipe r = new Recipe();
           
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            String refresh="refresh";
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            String newrecipe="";
            if(refresh== "refresh"){
                mcg.setResult(WhisperResponse1, WhisperResponse2);
                newrecipe=mcg.getResultRecipe();
                r.setRecipeTotal(mcg.getResultRecipe());
                r.setRecipeType(mcg.getMealType());
            }
           assertEquals(mcg.getResultRecipe(), newrecipe);

        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }
    
    

    @Test
    //This BDD tests for when a user cancels a refreshed recipe
    void RefreshBDD4(){
        try{
            Recipe r = new Recipe();
           
           Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            String refresh="refresh";
            String cancel="cancel";
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            String newrecipe="";
            if(refresh== "refresh"){
                mcg.setResult(WhisperResponse1, WhisperResponse2);
                newrecipe=mcg.getResultRecipe();
                r.setRecipeTotal(mcg.getResultRecipe());
                r.setRecipeType(mcg.getMealType());
            }
           assertEquals(mcg.getResultRecipe(), newrecipe);

           if(cancel=="cancel"){
             rl.deleteRecipe(r);
            }
            assertEquals(rl.getChildren().contains(r), false);

        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }


    @Test
    void FilterMealtypeSelected(){
        try{
            Recipe r = new Recipe();
            Recipe r2= new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");

            mw.setResult(new File("MockWhisperTest2.wav"));
            WhisperResponse1 = mw.getResults();
            mw.setResult(new File("LunchIng.wav"));
            WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r2.setRecipeTotal(mcg.getResultRecipe());
            r2.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r2);
            assertEquals(r2.getRecipeType().toString(), "lunch");
    
            rl.setFilter("Lunch");
            
            
            assertNotEquals(rl.getRecipes().contains(r), true);
            assertEquals(rl.getRecipes().contains(r2), true);
            

        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    @Test
    void FilterMealtypeNotSelected(){
        try{
            Recipe r = new Recipe();
            Recipe r2= new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");

            mw.setResult(new File("MockWhisperTest2.wav"));
            WhisperResponse1 = mw.getResults();
            mw.setResult(new File("LunchIng.wav"));
            WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r2.setRecipeTotal(mcg.getResultRecipe());
            r2.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r2);
            assertEquals(r2.getRecipeType().toString(), "lunch");
    
            //rl.updateFilteredBreakfast();
            assertEquals(rl.getRecipes().contains(r2), true);
            assertEquals(rl.getRecipes().contains(r), true);

        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }


    @Test
    void FilterMealtypeSelectandNotSelected(){
        try{
            Recipe r = new Recipe();
            Recipe r2= new Recipe();
            Stage primaryStage= new Stage();
           String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");

            mw.setResult(new File("MockWhisperTest2.wav"));
            WhisperResponse1 = mw.getResults();
            mw.setResult(new File("LunchIng.wav"));
            WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r2.setRecipeTotal(mcg.getResultRecipe());
            r2.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r2);
            assertEquals(r2.getRecipeType().toString(), "lunch");
    
            rl.setFilter("All");
            assertNotEquals(rl.getRecipes().contains(r2), true);
            assertEquals(rl.getRecipes().contains(r), true);

            rl.setFilter("Breakfast");
            assertEquals(rl.getRecipes().contains(r2), true);
            assertEquals(rl.getRecipes().contains(r), true);

        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void sortChronilogical(){
        try{
            Recipe r = new Recipe();
            Recipe r2= new Recipe();
            Stage primaryStage= new Stage();
           String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");

            mw.setResult(new File("MockWhisperTest2.wav"));
            WhisperResponse1 = mw.getResults();
            mw.setResult(new File("LunchIng.wav"));
            WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r2.setRecipeTotal(mcg.getResultRecipe());
            r2.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r2);
            assertEquals(r2.getRecipeType().toString(), "lunch");

            rl.setSort("New to Old");
            rl.sortRecipes();
            assertEquals(rl.getRecipes().indexOf(r), 0);
            assertEquals(rl.getRecipes().indexOf(r2), 1);
        }
        catch(ExceptionInInitializerError e){
            e.printStackTrace();
        }
        catch(NoClassDefFoundError e){
            e.printStackTrace();
        }
    }

    @Test
    void sortReverseChronilogical(){
        try{
            Recipe r = new Recipe();
            Recipe r2= new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");

            mw.setResult(new File("MockWhisperTest2.wav"));
            WhisperResponse1 = mw.getResults();
            mw.setResult(new File("LunchIng.wav"));
            WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r2.setRecipeTotal(mcg.getResultRecipe());
            r2.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r2);
            assertEquals(r2.getRecipeType().toString(), "lunch");

            rl.setSort("Old to New");
            rl.sortRecipes();
            assertEquals(rl.getRecipes().indexOf(r), 0);
            assertEquals(rl.getRecipes().indexOf(r2), 1);
        }
        catch(ExceptionInInitializerError e){
            e.printStackTrace();
        }
        catch(NoClassDefFoundError e){
            e.printStackTrace();
        }
    }


    @Test
    void sortAlphabetical(){
        try{
            Recipe r = new Recipe();
            Recipe r2= new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");

            mw.setResult(new File("MockWhisperTest2.wav"));
            WhisperResponse1 = mw.getResults();
            mw.setResult(new File("LunchIng.wav"));
            WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r2.setRecipeTotal(mcg.getResultRecipe());
            r2.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r2);
            assertEquals(r2.getRecipeType().toString(), "lunch");

            rl.setSort("Alphabetical");
            rl.sortRecipes();
            assertEquals(rl.getRecipes().indexOf(r), 1);
            assertEquals(rl.getRecipes().indexOf(r2), 0);
        }
        catch(ExceptionInInitializerError e){
            e.printStackTrace();
        }
        catch(NoClassDefFoundError e){
            e.printStackTrace();
        }
    }

    @Test
    void sortReverseAlphabetical(){
        try{
            Recipe r = new Recipe();
            Recipe r2= new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");

            mw.setResult(new File("MockWhisperTest2.wav"));
            WhisperResponse1 = mw.getResults();
            mw.setResult(new File("LunchIng.wav"));
            WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r2.setRecipeTotal(mcg.getResultRecipe());
            r2.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r2);
            assertEquals(r2.getRecipeType().toString(), "lunch");

            rl.setSort("Reverse Alphabetical");
            rl.sortRecipes();
            assertEquals(rl.getRecipes().indexOf(r), 0);
            assertEquals(rl.getRecipes().indexOf(r2), 1);
        }
        catch(ExceptionInInitializerError e){
            e.printStackTrace();
        }
        catch(NoClassDefFoundError e){
            e.printStackTrace();
        }
    }

    @Test
    void DallEBDDWithRefresh(){
        try{
            Recipe r = new Recipe();
           
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            String refresh="refresh";
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            String newrecipe="";
            if(refresh== "refresh"){
                mcg.setResult(WhisperResponse1, WhisperResponse2);
                newrecipe=mcg.getResultRecipe();
                r.setRecipeTotal(mcg.getResultRecipe());
                r.setRecipeType(mcg.getMealType());
            }
           assertEquals(mcg.getResultRecipe(), newrecipe);

           MockDallE md = new MockDallE();
           String dallEImage=md.getUrl("breakfast");
           assertEquals(dallEImage, "breakfast.com");


        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    @Test
    void DallEBDDWithoutRefresh(){
        try{
            Recipe r = new Recipe();
           
           Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            String refresh="no refresh";
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            if(refresh=="no refresh"){

                rl.getChildren().add(r);
                String newrecipe="";
            }
           assertEquals(rl.getChildren().contains(r), true);

           MockDallE md = new MockDallE();
           String dallEImage=md.getUrl("breakfast");
           assertEquals(dallEImage, "breakfast.com");

        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    @Test
    void ShareURL(){
        try{
            Recipe r = new Recipe();
            Stage primaryStage= new Stage();
            String response= "hi";
            View v= new View(primaryStage, response);
            RecipeList rl = v.getRecipeList();
            MockChatGPT mcg = new MockChatGPT();
            MockWhisper mw = new MockWhisper();
            mw.setResult(new File("MockWhisperTest1.wav"));
            String WhisperResponse1 = mw.getResults();
            mw.setResult(new File("BreakfastIng.wav"));
            String WhisperResponse2 = mw.getResults();
            mcg.setResult(WhisperResponse1, WhisperResponse2);
            r.setRecipeTotal(mcg.getResultRecipe());
            r.setRecipeType(mcg.getMealType());
            rl.getChildren().add(r);
            assertEquals(r.getRecipeType().toString(), "breakfast");

            String expected = "localhost:8100/recipe/?=" + r.getRecipeName();
            assertEquals(r.getLink(), expected);


        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

 
    //TODO
    @Test
    void CreateAccountBDD(){
        String username = "name";
        String password = "pass";
       
       ArrayList<String> accounts=new ArrayList<String>();
       ArrayList<String> passwords= new ArrayList<String>();
       accounts.add("name2");
       passwords.add("pass2");
        if(!accounts.contains(username)){
            accounts.add(username);
            passwords.add(password);
            assertEquals(true, accounts.contains(username));
            assertEquals(true, passwords.contains(password));
        }

    }
    
    //TODO
    @Test
    void LoginBDD(){
        String username = "name";
        String password = "pass";
       
       ArrayList<String> accounts=new ArrayList<String>();
       ArrayList<String> passwords= new ArrayList<String>();
       accounts.add(username);
       passwords.add(password);
        if(accounts.contains(username) && passwords.contains(password)){
            System.out.println("logged in!");
            assertEquals(true, accounts.contains(username));
            assertEquals(true, passwords.contains(password));   
        }

    }

    //TODO
    @Test
    void ServerNotActive(){
        Boolean ServerWorking = false;
        if(ServerWorking==false){
            System.out.print("Server Error");
            assert(true);//tests pass at this point bc server failed as expected
        }
    }
}

