package com.jyeager.urlshortener.redirect.controller;

import com.jyeager.urlshortener.redirect.service.RedirectService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controlador responsável por gerenciar redirecionamentos baseados em códigos curtos.
 * Define endpoints relacionados à funcionalidade de redirecionamento.
 */
@RestController
@RequestMapping("/redirect")
public class RedirectController {

    // Injeção do serviço de redirecionamento via construtor.
    public RedirectController(RedirectService service) {
        this.service = service;
    }

    private final RedirectService service;

    /**
     * Endpoint para redirecionar o cliente para a URL original associada a um código curto.
     *
     * @param shortCode Código curto fornecido na URL para identificar a URL original.
     * @param response  Objeto HttpServletResponse usado para manipular a resposta HTTP.
     * @throws IOException Se ocorrer um erro ao tentar redirecionar.
     */
    @GetMapping("/{shortCode}")
    public void redirectToOriginal(@PathVariable String shortCode, HttpServletResponse response)
            throws IOException {
        // Obtém a URL original associada ao código curto
        String originalUrl = service.getOriginalUrl(shortCode);

        // Log da URL original recuperada
        System.out.println("URL original: " + originalUrl);

        // Verifica se a URL original existe
        if (originalUrl != null) {
            // Log do redirecionamento
            System.out.println("Redirecionando para: " + originalUrl);

            // Redireciona o cliente para a URL original
            response.sendRedirect(originalUrl);
            return;
        } else {
            // Retorna um erro 404 (NOT FOUND) se a URL não for encontrada
            response.sendError(HttpStatus.NOT_FOUND.value(), "URL não encontrada.");
        }
    }

    /**
     * @RestController: Indica que esta classe é um controlador Spring, cujos métodos retornam dados
     * diretamente no corpo da resposta ou manipulam a resposta HTTP, como no caso de redirecionamento.
     * Injeção de dependência via construtor:
     * O RedirectService é injetado para gerenciar a lógica de redirecionamento.
     * Método redirectToOriginal:
     * @GetMapping("/{shortCode}"): Mapeia requisições GET no caminho /redirect/{shortCode}. O {shortCode}
     * é extraído da URL e passado como argumento ao metdo.
     * HttpServletResponse: Usado para configurar manualmente a resposta HTTP, como redirecionamentos e
     * códigos de erro.
     * Redirecionamento com sendRedirect:
     * Se a URL original é encontrada, o cliente é redirecionado usando o metdoresponse.sendRedirect(originalUrl).
     * Erro 404:
     * Se a URL original não é encontrada, o metdo response.sendError retorna um código de erro HTTP 404
     * e uma mensagem personalizada.
     */
}
