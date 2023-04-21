package fr.simplon.sondageservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**

 Cette classe est utilisée pour configurer Swagger, un outil de documentation et de conception d'API.

 Elle permet de personnaliser les informations affichées dans la documentation Swagger de l'API de gestion des sondages.
 */
@Configuration
public class SwaggerConfig {

    /**

     Méthode qui permet de créer un objet OpenAPI personnalisé pour la documentation Swagger.
     @return un objet OpenAPI contenant les informations personnalisées pour la documentation Swagger de l'API de gestion des sondages.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de gestion des sondages").version("1.0.0"));
    }
}