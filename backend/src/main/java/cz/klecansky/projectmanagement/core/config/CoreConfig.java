package cz.klecansky.projectmanagement.core.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CoreConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}