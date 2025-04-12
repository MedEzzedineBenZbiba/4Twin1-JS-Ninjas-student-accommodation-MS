package tn.esprit.tpfoyer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.List;

@Service
public class DeepLTranslationService {

    private final WebClient webClient;
    private final String apiKey;

    public DeepLTranslationService(
            @Value("${deepl.api.key}") String apiKey,
            @Value("${deepl.api.url}") String apiUrl) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "DeepL-Auth-Key " + apiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    public Mono<String> translate(String text, String targetLanguage) {
        if (text == null || text.isEmpty()) {
            return Mono.just(text);
        }

        String requestBody = String.format("text=%s&target_lang=%s",
                text.replace(" ", "+"),
                targetLanguage);

        return webClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, String>> translations = (List<Map<String, String>>) response.get("translations");
                    return translations.get(0).get("text");
                })
                .onErrorResume(e -> {
                    System.err.println("Translation failed: " + e.getMessage());
                    return Mono.just(text);
                });
    }
}