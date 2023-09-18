package cz.klecansky.projectmanagement.user.config;

import static cz.klecansky.projectmanagement.user.EmailConstants.*;

import cz.klecansky.projectmanagement.core.config.SmtpProperties;
import java.util.Properties;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserConfig {

    @NonNull
    SmtpProperties smtpProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpProperties.server());
        mailSender.setPort(smtpProperties.port());

        mailSender.setUsername(smtpProperties.username());
        mailSender.setPassword(smtpProperties.password());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", SIMPLE_MAIL_TRANSFER_PROTOCOL);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.enable", "true");

        return mailSender;
    }
}
