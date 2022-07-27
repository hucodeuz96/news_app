package hucode.news_app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author "Husniddin Ulachov"
 * @created 3:47 PM on 7/25/2022
 * @project news_app
 */
@Configuration
public class BeanConfig {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}