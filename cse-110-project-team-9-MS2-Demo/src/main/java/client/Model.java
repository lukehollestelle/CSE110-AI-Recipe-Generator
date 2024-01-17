package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.rmi.RMISecurityException;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.sampled.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.*;
import java.net.*;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;


public class Model {
    public String performRequest(String method, String name, String[] typeAndDetails, String query) {
        // Implement your HTTP request logic here and return the response

        try {
            String urlString = "http://localhost:8100/";
            if (query != null) {
                urlString += "?=" + query;
            }
            URL url = new URI(urlString).toURL(); //error here bcuz of not refactoring query - fix at recipe controller 
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(name + "!" + typeAndDetails[0] + "$" + typeAndDetails[1] + "=" + typeAndDetails[2] + "*" + typeAndDetails[3]);
                //name ! type $ user = details
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ErrorView(ex);
            ex.printStackTrace();
            return "Error";
        }
    }

    public void ErrorView(Exception e) {
        

        //Online code from StackOverflow used to convert error message into string
        //Website URL: https://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();

        int end = exceptionAsString.indexOf("\n"); 
        if (end != -1) { exceptionAsString = exceptionAsString.substring(0 , end - 1); }

        //Error message
        Label errorMsg = new Label("Error Message");
        errorMsg.setStyle("-fx-font-weight: bold;");
        Label message = new Label(exceptionAsString);

        //Error description
        Label descriptionHeader = new Label("Error Description");
        descriptionHeader.setStyle("-fx-font-weight: bold;");
        Label description = new Label("Server not started or unavailable before App Launch");

        //Suggested fix
        Label fixHeader = new Label("Suggested Fix");
        fixHeader.setStyle("-fx-font-weight: bold;");
        Label fixMsg = new Label("Launch MyServer.java or wait till server is online before logging in");

        Button close = new Button("Close");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(errorMsg, message, descriptionHeader, description, fixHeader, fixMsg, close);
        //vbox.setAlignment(Pos.CENTER);

        ScrollPane scroller = new ScrollPane(vbox);

        Stage stage = new Stage();
        stage.setWidth(400);
        stage.setHeight(235);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Error Received!");
        stage.setScene(new Scene(scroller));
        stage.show();

        close.setOnAction(e1 -> {
            stage.close();
            //Can have it so the system shuts down after receiving error message
            //System.exit(0);
        });
    }

}

class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";
    private static String result;

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

        // Set request parameters
        String prompt1 = "Create a ";
        String prompt2 = " recipe with the following ingredients: ";
        String prompt = "Create a recipe with the following ingredients: ";
        int maxTokens = 1000;

        /*
         * if (args.length > 0 && args[0] != null) {
         * maxTokens = Integer.parseInt(args[0]);
         * }
         */

        if (args.length > 0 && args[0] != null) {
            prompt += args[0];
        }

        if (args.length > 1 && args[1] != null) {
            prompt = prompt1 + args[1] + prompt2 + args[0];
        }

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        // Create the HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        // Create the request object
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(API_ENDPOINT))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Bearer %s", API_KEY))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Process the response
        String responseBody = response.body();

        JSONObject responseJson = new JSONObject(responseBody);

        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");

        result = generatedText;

        // System.out.println(generatedText);

    }

    public static String returnPrompt() {
        String title = "";
        String delimiter = "Ingredients"; // Use "\n" as the delimiter
        title = result.replace("\n", "");
        title = title.replace("-", " ");
        int delimiterindex = title.indexOf(delimiter);
        if (delimiterindex != -1) {
            title = title.substring(0, delimiterindex);
        }
        return title;
    }

    public static String getResult() {
        return result;
    }
}

class Whisper {

    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";
    private static final String FILE_PATH = "/Users/arnavkamra/Documents/PantryPal/PantryPal/you almost made me drop my croissant vine.wav";

    private static String result;

    // Helper method to write a parameter to the output stream in multipart form
    // data format
    private static void writeParameterToOutputStream(OutputStream outputStream, String parameterName,
            String parameterValue, String boundary) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n").getBytes());
        outputStream.write((parameterValue + "\r\n").getBytes());
    }

    // Helper method to write a file to the output stream in multipart form data
    // format
    private static void writeFileToOutputStream(OutputStream outputStream, File file, String boundary)
            throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
                ("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }

    // Helper method to handle a successful response
    private static void handleSuccessResponse(HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject responseJson = new JSONObject(response.toString());
        String generatedText = responseJson.getString("text");

        result = generatedText;
        // Save result
        // System.out.println("Transcription Result: " + generatedText);
    }

    public static String getResult() {
        return result;
    }

    // Helper method to handle an error response
    private static void handleErrorResponse(HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(connection.getErrorStream()));
        String errorLine;
        StringBuilder errorResponse = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
        }
        errorReader.close();
        String errorResult = errorResponse.toString();
        System.out.println("Error Result: " + errorResult);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        String fileName = FILE_PATH;

        if (args.length > 0 && args[0] != null) {
            fileName = args[0];
        }

        // Create file object from file path
        File file = new File(fileName);

        // Set up HTTP connection
        URL url = new URI(API_ENDPOINT).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set up request headers
        String boundary = "Boundary-" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        // Set up output stream to write request body
        OutputStream outputStream = connection.getOutputStream();

        // Write model parameter to request body
        writeParameterToOutputStream(outputStream, "model", MODEL, boundary);

        // Write file parameter to request body
        writeFileToOutputStream(outputStream, file, boundary);

        // Write closing boundary to request body
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());

        // Flush and close output stream
        outputStream.flush();
        outputStream.close();

        // Get response code
        int responseCode = connection.getResponseCode();

        // Check response code and handle response accordingly
        if (responseCode == HttpURLConnection.HTTP_OK) {
            handleSuccessResponse(connection);
        } else {
            handleErrorResponse(connection);
        }

        // Disconnect connection
        connection.disconnect();
    }

}

class MockChatGPT extends ChatGPT{
        private static String resultRecipe;
        private static String resultMealType;

        public void setResult(String mealType, String ingrediants){
            if (mealType == "breakfast"){
                resultRecipe = "A breakfast recipe with " + ingrediants;
            }
            else if (mealType == "lunch"){
                resultRecipe = "A lunch recipe with " + ingrediants;
            }
            else if (mealType == "dinner"){
                resultRecipe = "A dinner recipe with " + ingrediants;
            }
            else{
                resultRecipe = "random recipe with " + ingrediants;
            }
            resultMealType = mealType;
        }

        public String getResultRecipe() {
            return resultRecipe;
        }

        public String getMealType(){
            return resultMealType;
        }
    }

class MockWhisper extends Whisper{
    private static String result;
    File breakfast = new File("MockWhisperType1.wav");
    File lunch = new File("MockWhisperType2.wav");
    File dinner = new File("MockWhisperType3.wav");
    File breakfastIng = new File("BreakfastIng.wav");
    File lunchIng = new File("LunchtIng.wav");
    File dinnerIng = new File("DinnerIng.wav");

    public void setResult(File newRes){
        if (newRes == breakfast){result = "breakfast";}
        else if (newRes == lunch){result = "lunch";}
        else if (newRes == dinner){result = "dinner";}
        else if (newRes == dinnerIng){result = "steak, salt, pepper, potatos, carrots, wine, butter, thyme";}
        else if (newRes == breakfastIng) {result = "flour, butter, yeast, eggs, maple syrup";}
        else if (newRes == lunchIng) {result = "beef and tomatos";}
        else{result = "";}
    }

    public String getResults(){
        return result;
    }
}

class Recipe extends HBox {

    private Button deleteButton;
    private Button detailedView;
    private Label recipeLabel;
    private String recipeTotal;
    private Label recipeType;
    private String id;
    private Button share;
    private String query;
    private String recipeName;

    private DetailedView currDetailedView;
    private ShareView currShareView;
    private DetailedController detailedController;
    private RecipeController recipeController;
    private String user;
    private String imageURL;

    String defaultButtonStyle = "-fx-border-color: #000000; -fx-font: 12 arial; -fx-pref-height: 30px;";
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-height: 30px; -fx-text-fill: black;";

    Recipe() {
        imageURL = "https://example.com/path/to/image.jpg";
        recipeName = "default";
        this.setPrefSize(600, 30); // sets size of recipe
        this.setStyle("-fx-background-color: #F0F8FF; -fx-border-width: 0;");

        deleteButton = new Button("X");

        deleteButton.setTextAlignment(TextAlignment.CENTER);
        deleteButton.setPrefHeight(Double.MAX_VALUE);
        deleteButton.setStyle(
                "-fx-background-color: #FFA9A9; -fx-border-width: 0; -fx-border-color: #8B0000; -fx-font-weight: bold");
        this.getChildren().add(deleteButton);
        detailedView = new Button("Detailed View");
        detailedView.setStyle(defaultButtonStyle);
        detailedView.setAlignment(Pos.CENTER_RIGHT);

        share = new Button("Share");
        share.setStyle(defaultButtonStyle);
        share.setAlignment(Pos.BASELINE_RIGHT);

        recipeLabel = new Label("Recipe: "); // create task name text field
        recipeLabel.setStyle(defaultLabelStyle);
        recipeLabel.setTextAlignment(TextAlignment.CENTER);

        recipeType = new Label("NONE");
        recipeType.setStyle("-fx-background-color: #BCEAD5; -fx-border-width: 1; -fx-border-color: #BCEAD5; -fx-font-weight: bold; -fx-pref-height: 30px");
        recipeType.setTextAlignment(TextAlignment.CENTER);

        this.getChildren().addAll(recipeType, recipeLabel, detailedView, share);
        currDetailedView = new DetailedView(this, this.getRecipeTotal(), this.getURL());
        currShareView = new ShareView(this, this.getLink());
        Model model = new Model();
        detailedController = new DetailedController(currDetailedView, model);
        recipeController = new RecipeController(this, model);
    }

    public String getURL() {
        return imageURL;
    }

    public void setURL(String newURL) {
        imageURL = newURL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getID(){
        return id;
    }

    public void setID(String id){
        this.id = id;
    }

    public String getLink(){
        recipeName = recipeName.replace(" ", "-");
        String link = "localhost:8100/recipe/?="+recipeName;
        return link;
    }

    public ShareView getShareView(){
        return currShareView;
    }

    public Button getShareButton(){
        return share;
    }

    public void setShareView(){
        currShareView = new ShareView(this, this.getLink());
    }
    public void setDetailedView(){
        currDetailedView = new DetailedView(this, this.getRecipeTotal(), this.getURL());
    }

    public DetailedView getDetailedView(){
        return currDetailedView;
    }

    public void setDetailedView(DetailedView view){
        currDetailedView = view;
    }

    public Button getDetailedViewButton() {
        return detailedView;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
    

    public String getRecipeLabelName() {
        return recipeLabel.getText();
    }
    // this will fix the query error for delete
    public String getQueryRecipeLabelName(){
        String formattedName;
        String base = recipeLabel.getText();

        formattedName = base.replace(' ', '-');

        return formattedName;

    }

    public String getRecipeType() {
        return recipeType.getText();
    }

    public void setRecipeType(String type) {
        //Run this line only if trying to test meal type tag without microphone working (Meal type registers anything other than breakfast, lunch, dinner)
        //recipeType.setText(type);

        if(type.toLowerCase().contains("breakfast")) {
            recipeType.setText("Breakfast");
        } else if (type.toLowerCase().contains("lunch")) {
            recipeType.setText("Lunch");
        } else if (type.toLowerCase().contains("dinner")) {
            recipeType.setText("Dinner"); 
        } else {
            return;
        }
        //recipeType.setVisible(true);
        //this.getChildren().add(1, recipeType);
    }

    public void setRecipeName(String newRecipe) { //set title of recipe
        recipeName = newRecipe;
        recipeLabel.setText(newRecipe);
    }

    public String getRecipeName() {
        return recipeLabel.getText();
    }

    public void setRecipeTotal(String therecipe) {
        recipeTotal = therecipe; // put entire recipe in string
    }

    public String getRecipeTotal() {
        return recipeTotal;
    }
    public void setRecipeText(String newText) {
        setRecipeTotal(newText);
    }
    
    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {
        deleteButton.setOnAction(eventHandler);
    }

    public void setGetButtonAction(EventHandler<ActionEvent> eventHandler) {
        detailedView.setOnAction(eventHandler);
    }
    public void setShareButtonAction(EventHandler<ActionEvent> eventHandler){
        share.setOnAction(eventHandler);
    }
}
