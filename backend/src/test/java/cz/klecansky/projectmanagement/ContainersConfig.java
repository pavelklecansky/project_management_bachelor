package cz.klecansky.projectmanagement;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
@TestPropertySource(properties = "spring.liquibase.contexts=test")
public class ContainersConfig {
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15.2-alpine");
    }

    @Bean
    public GenericContainer<?> mailhogContainer(DynamicPropertyRegistry registry) {
        GenericContainer<?> container = new GenericContainer<>("mailhog/mailhog").withExposedPorts(1025);
        registry.add("smtp.server", container::getHost);
        registry.add("smtp.port", container::getFirstMappedPort);
        registry.add("smtp.username", () -> "test@test.com");
        registry.add("smtp.password", () -> "password");
        return container;
    }
}
