package de.borisskert.springwiremockexample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("wiremock")
class GreetingResourceTest {

    @Autowired
    private WebTestClient client;

    @Value("${example.remote.url}")
    String greetingUrl;

    @Test
    void shouldResponseWithGreeting() throws Exception {
        client.get()
                .uri("/greeting")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo("Hello World!");
    }

    @Test
    void shouldResponseWithGreetingFromRemote() throws Exception {
        client.get()
                .uri("/greeting/remote")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo("Greeting response: Hello World! from remote");
    }

    @Test
    void shouldResponseWithGreetingFromRemoteDirectly() throws Exception {
        client.get()
                .uri(greetingUrl)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo("Hello World! from remote");
    }
}
