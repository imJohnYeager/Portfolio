package com.jyeager.urlshortener.redirect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Classe de configuração para a aplicação.
 * Define beans e outras configurações necessárias para o contexto do Spring.
 */
@Configuration
public class AppConfig {

    /**
     * Define um bean do tipo RestTemplate.
     * O RestTemplate é usado para fazer requisições HTTP a outros serviços.
     *
     * @return Uma instância de RestTemplate pronta para uso.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * @Configuration: Indica que esta classe contém definições de configuração para o contexto do Spring.
     * Bean RestTemplate:
     * O metdo restTemplate() cria e registra uma instância de RestTemplate no contexto do Spring.
     * Essa instância pode ser injetada em outros componentes da aplicação para realizar requisições HTTP
     * a APIs externas.
     * Por exemplo, ao chamar um serviço de encurtamento de URLs, o RestTemplate poderia ser usado para
     * enviar dados ou recuperar informações.
     */
}
