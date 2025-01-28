package com.jyeager.urlshortener.shortener.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um encurtador de URL no banco de dados.
 */
@Entity(name = "short_codes") // Define que esta classe é uma entidade JPA e será mapeada para a tabela "short_codes".
public class ShortenerEntity {

    @Id // Define este campo como a chave primária da tabela.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que o ID será gerado automaticamente pelo banco de dados.
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false, unique = true) // Garante que este campo não pode ser nulo e que cada valor seja único.
    private String originalUrl; // Armazena a URL original antes do encurtamento.

    @Setter
    @Getter
    @Column(nullable = false, unique = true) // Também não pode ser nulo e deve ser único para evitar duplicatas.
    private String shortCode; // Armazena o código encurtado correspondente à URL original.

    /**
     * Essa classe representa uma entidade JPA (Java Persistence API) que será armazenada no banco de dados.
     * O nome da tabela é "short_codes", conforme definido na anotação @Entity(name = "short_codes").
     * O campo id é a chave primária e é gerado automaticamente pelo banco.
     * Os campos originalUrl e shortCode são únicos e obrigatórios, pois cada código encurtado deve corresponder a uma única URL.
     * Lombok (@Getter e @Setter) é usado para gerar automaticamente os métodos get e set, evitando código repetitivo.
     */
}
