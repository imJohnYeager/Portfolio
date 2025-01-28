package com.jyeager.urlshortener.shortener.repository;

import com.jyeager.urlshortener.shortener.entity.ShortenerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório JPA para a entidade ShortenerEntity.
 * Fornece métodos para acessar o banco de dados e buscar URLs encurtadas.
 */
public interface ShortenerRepository extends JpaRepository<ShortenerEntity, Long> {

    /**
     * Busca uma entidade pelo código encurtado.
     *
     * @param shortCode O código encurtado associado a uma URL original.
     * @return Um Optional contendo a entidade encontrada, ou vazio se não existir.
     */
    Optional<ShortenerEntity> findByShortCode(String shortCode);

    /**
     * ShortenerRepository estende JpaRepository<ShortenerEntity, Long>,
     * o que significa que ele já possui métodos prontos para operações básicas de banco de dados,
     * como salvar, deletar e buscar por ID.
     * O metdo findByShortCode(String shortCode) permite buscar uma URL original a partir de seu código encurtado.
     * O retorno é um Optional<ShortenerEntity>, o que ajuda a evitar NullPointerException ao trabalhar
     * com valores que podem não existir no banco.
     */
}
