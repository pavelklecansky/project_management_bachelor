package cz.klecansky.projectmanagement.user.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
public class PasswordResetTokenEntity {

    @Id
    private UUID token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private Instant expiryDate;
}
