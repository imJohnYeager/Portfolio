package com.jyeager.urlshortener.redirect.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Serviço responsável por buscar a URL original associada a um código curto.
 * Utiliza o RestTemplate para se comunicar com o serviço de encurtamento (Shortener Service).
 */
@Service
public class RedirectService {

    // Injeção de dependência do RestTemplate via construtor.
    public RedirectService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final RestTemplate restTemplate;

    /**
     * Metdo para buscar a URL original no Shortener Service, com base no código curto fornecido.
     *
     * @param shortCode Código curto associado à URL original.
     * @return A URL original se encontrada, ou null caso contrário.
     */
    public String getOriginalUrl(String shortCode) {
        // Define a URL do endpoint do Shortener Service para recuperar a URL original.
        String shortenerServiceUrl = "http://localhost:8080/shortener/" + shortCode;

        // Log da URL do serviço sendo chamada.
        System.out.println("URL do Shortener Service: " + shortenerServiceUrl);

        // Faz uma requisição GET ao Shortener Service e retorna a URL original como String.
        return restTemplate.getForObject(shortenerServiceUrl, String.class);
    }

    /**
     * @Service: Marca esta classe como um componente de serviço do Spring, responsável pela lógica de negócios.
     * Injeção de dependência do RestTemplate:
     * O RestTemplate é injetado no construtor, seguindo as boas práticas de injeção de dependências no Spring.
     * Ele é usado para realizar requisições HTTP a outros serviços.
     * Metdo getOriginalUrl:
     * Definição da URL do Shortener Service:
     * A URL do endpoint é construída dinamicamente, adicionando o código curto à base do serviço
     * (http://localhost:8080/shortener/).
     * Log da URL chamada:
     * O metdo imprime no console a URL do serviço sendo acessado, útil para depuração.
     * Requisição HTTP:
     * O restTemplate.getForObject faz uma chamada GET para o endpoint e espera como resposta uma String
     * (a URL original).
     */
}
