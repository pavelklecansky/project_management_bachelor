package cz.klecansky.projectmanagement.user.service;

import cz.klecansky.projectmanagement.group.shared.GroupCommand;
import cz.klecansky.projectmanagement.group.shared.GroupMemberCommand;
import cz.klecansky.projectmanagement.task.shared.TaskCommand;
import cz.klecansky.projectmanagement.user.exception.SmtpErrorException;
import cz.klecansky.projectmanagement.user.io.entity.PasswordResetTokenEntity;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import cz.klecansky.projectmanagement.user.io.entity.VerificationTokenEntity;
import cz.klecansky.projectmanagement.user.shared.UserCommand;
import cz.klecansky.projectmanagement.user.shared.UserMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static cz.klecansky.projectmanagement.user.EmailConstants.*;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class EmailService {

    static final String VERIFICATION_EMAIL_BODY = "<h1>Confirm your email</h1>"
            + "<p>Thank you for registering in our application. To complete the registration,"
            + " click on this link: "
            + "<a href='$address'>"
            + "The last step to complete the registration </a><br/><br/>";

    static final String FORGOTTEN_PASSWORD_BODY = "<h1>Password reset request</h1>"
            + "<p>Someone has asked for your password reset. If it wasn't you, please ignore."
            + " otherwise, open the link below in the browser window and set a new password:"
            + "<a href='$address'>"
            + "Click this link to reset your password</a><br/><br/>";

    static final String ASSIGN_TO_TASK_BODY = "<h1>You have been assigned to a task</h1>"
            + "<p>You have been assigned to a task: $taskName. "
            + " you can open task on this url: "
            + "<a href='$address'>$taskName</a><br/><br/>";

    @Value("${server.url}")
    @NonFinal
    String serverUrl;

    @NonNull JavaMailSender javaMailSender;
    @NonNull VerificationTokenService verificationTokenService;
    @NonNull PasswordResetTokenService passwordResetTokenService;
    @NonNull UserMapper userMapper;

    @SneakyThrows
    public void sendVerificationEmail(UserCommand userCommand) {
        VerificationTokenEntity verificationTokenEntity = createVerificationToken(userCommand);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(FROM_EMAIL);
        helper.setTo(userCommand.getEmail());
        helper.setSubject(EMAIL_VERIFICATION_SUBJECT);
        helper.setText(getVerificationEmailHTMLText(verificationTokenEntity), true);
        verificationTokenService.saveVerificationToken(verificationTokenEntity);
        javaMailSender.send(mimeMessage);
    }

    public void sendForgottenPasswordEmail(UserCommand userCommand) {
        PasswordResetTokenEntity passwordResetToken = createPasswordResetToken(userCommand);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(FROM_EMAIL);
            helper.setTo(userCommand.getEmail());
            helper.setSubject(FORGOTTEN_PASSWORD_SUBJECT);
            helper.setText(getForgottenPasswordEmailHTMLText(passwordResetToken), true);
            passwordResetTokenService.savePasswordResetToken(passwordResetToken);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SmtpErrorException("Application cannot send email.");
        }
    }

    public void sendAssignToTaskEmail(TaskCommand taskCommand) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(FROM_EMAIL);
            helper.setTo(taskCommand.getAssigned().getEmail());
            helper.setSubject(ASSIGNED_TO_TASK_SUBJECT);
            helper.setText(getAssignToTaskEmailHTMLText(taskCommand), true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SmtpErrorException("Application cannot send email.");
        }
    }

    public void sendAssignToGroupTaskEmail(TaskCommand taskCommand) {
        GroupCommand assignedForGroup = taskCommand.getAssignedForGroup();
        for (GroupMemberCommand member : assignedForGroup.getMembers()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setFrom(FROM_EMAIL);
                helper.setTo(member.getUser().getEmail());
                helper.setSubject(ASSIGNED_TO_TASK_SUBJECT);
                helper.setText(getAssignToTaskEmailHTMLText(taskCommand), true);
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                throw new SmtpErrorException("Application cannot send email.");
            }
        }
    }


    private String getVerificationEmailHTMLText(VerificationTokenEntity verificationTokenEntity) {
        return VERIFICATION_EMAIL_BODY.replace("$address", "http://" + serverUrl + "/authentication/email-verification/" + verificationTokenEntity.getToken());
    }

    private String getForgottenPasswordEmailHTMLText(PasswordResetTokenEntity passwordResetTokenEntity) {
        return FORGOTTEN_PASSWORD_BODY.replace("$address", "http://" + serverUrl + "/authentication/new-password/" + passwordResetTokenEntity.getToken());
    }

    private String getAssignToTaskEmailHTMLText(TaskCommand taskCommand) {
        return ASSIGN_TO_TASK_BODY.replace("$address", "http://" + serverUrl + "/task/" + taskCommand.getId()).replace("$taskName", taskCommand.getName());
    }

    private VerificationTokenEntity createVerificationToken(UserCommand userCommand) {
        UserEntity userEntity = userMapper.userCommandToUserEntity(userCommand);
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity();
        verificationTokenEntity.setToken(UUID.randomUUID());
        verificationTokenEntity.setUser(userEntity);
        verificationTokenEntity.setCreatedDate(Instant.now());
        verificationTokenEntity.setExpiryDate(Instant.now().plus(15, ChronoUnit.MINUTES));
        return verificationTokenEntity;
    }

    private PasswordResetTokenEntity createPasswordResetToken(UserCommand userCommand) {
        UserEntity userEntity = userMapper.userCommandToUserEntity(userCommand);
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(UUID.randomUUID());
        passwordResetTokenEntity.setUser(userEntity);
        passwordResetTokenEntity.setExpiryDate(Instant.now().plus(15, ChronoUnit.MINUTES));
        return passwordResetTokenEntity;
    }

}
