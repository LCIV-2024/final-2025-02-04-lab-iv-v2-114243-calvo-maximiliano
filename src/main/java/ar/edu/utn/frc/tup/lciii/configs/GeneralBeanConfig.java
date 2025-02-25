package ar.edu.utn.frc.tup.lciii.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneralBeanConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
