package br.com.tomwell.poc_bff.dto;

public record QuoteRequest(
    String clientId, String cpf, int qtdVidas
) {
}
