package cz.klecansky.projectmanagement.user;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class EmailConstants {
    public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtp";
    public static final String FROM_EMAIL = "projekty@borkovcova.com";
    public static final String EMAIL_VERIFICATION_SUBJECT = "Email verification";
    public static final String FORGOTTEN_PASSWORD_SUBJECT = "Forgotten password";
    public static final String ASSIGNED_TO_TASK_SUBJECT = "You have been assigned to a task";
    public static final int SMTP_PORT = 465;

    @Value("${smtp.username}")
    public static String SMTP_USERNAME;
    @Value("${smtp.password}")
    public static String SMTP_PASSWORD;

    @Value("${smtp.server}")
    public static String SMTP_SERVER;
}
