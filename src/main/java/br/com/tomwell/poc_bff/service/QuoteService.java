package br.com.tomwell.poc_bff.service;

import br.com.tomwell.poc_bff.dto.QuoteRequest;
import br.com.tomwell.poc_bff.dto.QuoteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class QuoteService {

    @Value("${api.url.request}")
    private String pocRequestApiURL;

    @Value("${api.url.sse}")
    private String pocSseURL;

    private final WebClient webClient;

    public QuoteService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<QuoteResponse> requestQuote(QuoteRequest quoteRequest) {
        Mono<QuoteResponse> sseResponse = webClient.get()
                .uri(String.format("%s/%s", this.pocSseURL, quoteRequest.clientId()))
                .retrieve()
                .bodyToFlux(QuoteResponse.class)
                .next();

        Mono<String> postResponse = webClient.post()
                .uri(this.pocRequestApiURL)
                .bodyValue(quoteRequest)
                .retrieve()
                .bodyToMono(String.class);

        return Mono.zip(sseResponse, postResponse)
                .flatMap(tuple -> Mono.just(tuple.getT1()));
    }

}
