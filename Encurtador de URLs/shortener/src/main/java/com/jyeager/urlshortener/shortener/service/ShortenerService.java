package com.jyeager.urlshortener.shortener.service;

import com.jyeager.urlshortener.shortener.entity.ShortenerEntity;
import com.jyeager.urlshortener.shortener.repository.ShortenerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Serviço responsável pelas operações de encurtamento de URL e busca de URLs encurtadas.
 */
@Service
public class ShortenerService {

    /**
     * Repositório para acesso aos dados armazenados no banco.
     */
    private final ShortenerRepository repository;

    /**
     * Construtor que injeta o repositório.
     *
     * @param repository Instância do repositório para operações de banco de dados.
     */
    public ShortenerService(ShortenerRepository repository) {
        this.repository = repository;
    }

    /**
     * Encurta uma URL gerando um código único de 6 caracteres.
     *
     * @param originalUrl A URL original que será encurtada.
     * @return O código gerado que representa a URL encurtada.
     */
    public String shortenUrl(String originalUrl) {
        // Gera um código único aleatório com 6 caracteres.
        String shortCode = UUID.randomUUID().toString().substring(0, 6);

        // Cria uma nova entidade para armazenar a URL original e seu código.
        ShortenerEntity shortenerEntity = new ShortenerEntity();
        shortenerEntity.setOriginalUrl(originalUrl);
        shortenerEntity.setShortCode(shortCode);

        // Salva a entidade no banco de dados.
        repository.save(shortenerEntity);

        // Retorna o código gerado.
        return shortCode;
    }

    /**
     * Busca uma entidade pelo código encurtado.
     *
     * @param shortCode O código da URL encurtada.
     * @return Um Optional contendo a entidade encontrada, ou vazio se não existir.
     */
    public Optional<ShortenerEntity> findByShortCode(String shortCode) {
        // Usa o repositório para buscar a entidade com base no código fornecido.
        return repository.findByShortCode(shortCode);
    }

    /**
     * Classe ShortenerService: Responsável por toda a lógica de negócios para encurtar URLs e buscar URLs originais.
     * Injeção do repositório: O repositório é passado pelo construtor para que o serviço possa acessar o
     * banco de dados.
     * Metdo shortenUrl:
     * Gera um código único de 6 caracteres usando UUID.
     * Cria uma nova entidade para armazenar a URL original e o código gerado.
     * Salva a entidade no banco de dados.
     * Retorna o código gerado.
     * Metdo findByShortCode:
     * Usa o repositório para buscar a entidade correspondente ao código fornecido.
     * Retorna um Optional, que pode conter a entidade ou estar vazio caso o código não seja encontrado.
     */
}
