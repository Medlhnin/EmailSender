package com.example.EmailSender.Service;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionChoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OpenAIService {

    private final OpenAiService openAiService;

    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    public String generateText(String customerMessage) {
        String prompt = String.format(
                "Analyze the following client message and generate a warm, professional, and positive email confirmation: \n\n"
                        + "Client Message: \"%s\"\n\n"
                        + "Ensure the response acknowledges their message, confirms any necessary details, and encourages further communication if needed.",
                customerMessage
        );

        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt(prompt)
                .maxTokens(100)
                .temperature(0.7)
                .build();

        List<CompletionChoice> choices = openAiService.createCompletion(request).getChoices();
        return choices.isEmpty() ? "No response from OpenAI" : choices.get(0).getText().trim();
    }
}
