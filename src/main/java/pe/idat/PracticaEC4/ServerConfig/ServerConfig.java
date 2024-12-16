package pe.idat.PracticaEC4.ServerConfig;


import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import pe.idat.PracticaEC4.controller.CountryController;
import pe.idat.PracticaEC4.security.apiKeyAuthFilter;

@Configuration
public class ServerConfig extends ResourceConfig {
    public ServerConfig() {
        register(apiKeyAuthFilter.class);
        register(CountryController.class);
    }
}
