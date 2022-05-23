package de.borisskert.springwiremockexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/greeting")
public class GreetingResource {

    private final WebClient client;

    private final String greetingUrl;

    @Autowired
    public GreetingResource(
            WebClient client,
            @Value("${example.remote.url}") String greetingUrl
    ) {
        this.client = client;
        this.greetingUrl = greetingUrl;
    }

    @GetMapping
    public String get() {
        return "Hello World!";
    }

    @GetMapping("/remote")
    public Mono<String> getFromRemote() {
        return client.get()
                .uri(greetingUrl)
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> "Greeting response: " + body);
    }
}
