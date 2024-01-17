package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class MyRequestHandler implements HttpHandler {

    private final Map<String, String[]> data;
    MongoCollection<Document> recipesCollection;
    MongoCollection<Document> usersCollection;
    MongoDatabase recipe_db;
    MongoDatabase users_db;
    String uri;
    MongoClient mongoClient;

    public MyRequestHandler(Map<String, String[]> data) {
        this.data = data;
        uri = "mongodb+srv://admin:123@cluster0.cp02bnz.mongodb.net/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        recipe_db = mongoClient.getDatabase("recipe_db");
        users_db = mongoClient.getDatabase("users_db");
        recipesCollection = recipe_db.getCollection("recipes");
        usersCollection = users_db.getCollection("users");

    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }
        // Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }
    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "default";
       // InputStream inStream = httpExchange.getRequestBody();
       // Scanner scanner = new Scanner(inStream);
       // String postData = scanner.nextLine();
       // String name = postData;
       // System.out.println(result);

        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            value.replace("-", " ");
            System.out.print("value: " + value);
            if (value.substring(0, 5).equals("login")) {
                Document rec = usersCollection.find(eq("username", value.substring(5))).first();
                if (rec == null) {
                    return "Incorrect username or password";
                }
                response = rec.toJson();
                return response;
            }
            
            value = value.replace("-", " ");
            // Bson filter = eq("name", value);
            Document rec = recipesCollection.find(eq("name", value)).first();
            // DeleteResult result = recipesCollection.deleteOne(filter);
            String view = rec.get("details").toString();
            String imgURL = "";
            if (rec.containsKey("imageURL")){
                imgURL = rec.get("imageURL").toString();
            }
            else imgURL = "https://media.wired.com/photos/5b8999943667562d3024c321/master/w_1600,c_limit/trash2-01.jpg";
            response = rec.toJson();
            System.out.println(rec);
            // System.out.println(result);
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder
                    .append("<html>")
                    .append("\n")
                    .append("<body>")
                    .append("\n")
                    .append("    <h1>")
                    .append(value)
                    .append("\n")
                    .append("\n")
                    .append("</h1>\n")
                    .append("<img src='").append(imgURL).append("' alt='Image'>")
                    .append("\n")
                    .append(view.replace("\n", "<br>"))
                    .append("\n")
                    .append("\n")
                    .append("</body>")
                    .append("\n")
                    .append("</html>");
            response = htmlBuilder.toString();
            return response;
        }
        return response;
    }
}