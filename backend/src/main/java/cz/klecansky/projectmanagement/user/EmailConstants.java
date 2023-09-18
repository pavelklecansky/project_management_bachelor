package cz.klecansky.projectmanagement.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailConstants {
    public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtp";
    public static final String FROM_EMAIL = "pavel@klecansky.cz";
    public static final String EMAIL_VERIFICATION_SUBJECT = "Email verification";
    public static final String FORGOTTEN_PASSWORD_SUBJECT = "Forgotten password";
    public static final String ASSIGNED_TO_TASK_SUBJECT = "You have been assigned to a task";
}
