package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class DallE {

    private static final String API_ENDPOINT = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = "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";
    private static final String MODEL = "dall-e-2";
    private static String generatedImageURL;

    public static void main(String[] args) throws IOException, InterruptedException {

        String prompt = "Create an image of the following recipe:  ";


        if (args.length > 0 && args[0] != null) {
            prompt += args[0];
        }
        int n = 1;

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("n", n);
        requestBody.put("size", "256x256");

        // Create the HTTP client
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

        JSONArray URL = responseJson.getJSONArray("data");
        JSONObject URLObject = URL.getJSONObject(0);
        generatedImageURL = URLObject.getString("url");
        
        
        System.out.println("DALL-E Response:");
        System.out.println(generatedImageURL);
         
        /* 
        // Download the Generated Image to Current Directory
        try (InputStream in = new URI(generatedImageURL).toURL().openStream()) {
            Files.copy(in, Paths.get("image.jpg"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        */
    }
    // example url https://media.wired.com/photos/5b8999943667562d3024c321/master/w_1600,c_limit/trash2-01.jpg
    public static String getURL() {
        return generatedImageURL;
    }
}

class MockDallE extends DallE{
    String url;
    String breakfastURL = "breakfast.com";
    String lunchURL = "lunch.com";
    String dinnerURL = "dinner.com";
    
    public String getUrl(String input){
        if(input == "breakfastMeal"){return breakfastURL;}
        else if(input == "lunchMeal"){return lunchURL;}
        else return dinnerURL;
    }
}