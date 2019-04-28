package de.hfu.pms.conf;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
