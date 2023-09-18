package cz.klecansky.projectmanagement.core.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("smtp")
public record SmtpProperties(
        @NotEmpty String username, @NotEmpty String password, @NotEmpty String server, @NotNull Integer port) {
    @ConstructorBinding
    public SmtpProperties {}
}
