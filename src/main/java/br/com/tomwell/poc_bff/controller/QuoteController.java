package br.com.tomwell.poc_bff.controller;

import br.com.tomwell.poc_bff.dto.QuoteRequest;
import br.com.tomwell.poc_bff.dto.QuoteResponse;
import br.com.tomwell.poc_bff.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping("/sync/quote")
    public Mono<ResponseEntity<QuoteResponse>> quoteSync(@RequestBody QuoteRequest quoteRequest) {
        return quoteService.requestQuote(quoteRequest)
            .map(ResponseEntity::ok);
    }
}
