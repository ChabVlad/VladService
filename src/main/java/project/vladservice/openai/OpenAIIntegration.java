package project.vladservice.openai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenAIIntegration implements AIIntegration{

    private static String apiKey;
    private static String apiModel;

    @Value("${openai.api.key}")
    private String injectedApiKey;

    @Value("${openai.api.model}")
    private String injectedApiModel;

    @PostConstruct
    private void init() {
        apiKey = injectedApiKey;
        apiModel = injectedApiModel;
    }

    public String getResponse(String question) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", apiModel);
            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messages.put(userMessage);
            requestBody.put("messages", messages);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject responseJson = new JSONObject(response.body());
                String answer = responseJson.getJSONArray("choices").getJSONObject(0)
                        .getJSONObject("message").getString("content");
                return answer;
            } else {
                return "Sorry, I can't find the answer to your question right now.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "There was an error while processing your request.";
        }
    }
}
