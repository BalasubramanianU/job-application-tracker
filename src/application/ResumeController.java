package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ResumeController {
    @FXML
    private TextArea pasteJobTxt;

    @FXML
    private TextArea keywordsTxt;

    @FXML
    private Button generateBtn;

    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Construct the request body using Gson
            Gson gson = new Gson();
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", model);

            // Construct messages array
            List<JsonObject> messages = new ArrayList<>();
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", prompt);
            messages.add(userMessage);

            requestBody.add("messages", gson.toJsonTree(messages));

            // Write the request body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                writer.write(requestBody.toString());
                writer.flush();
            }

            // Read the response from ChatGPT
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                // Format the response for better readability
                String formattedResponse = response.toString().replace("\\n", "\n");

                // Call the method to extract the message
                return extractMessageFromJSONResponse(formattedResponse);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }

    @FXML
    private void generateKeywords() {
        String jobDescription = pasteJobTxt.getText();
        String keywordsResponse = chatGPT("Can you give important keywords from this job description for resume:" + jobDescription);
        keywordsTxt.setText(keywordsResponse);
    }

    @FXML
    private void clearKeywords() {
        keywordsTxt.clear();
    }

    @FXML
    private void clearJob() {
        pasteJobTxt.clear();
    }
}
