package cz.klecansky.projectmanagement.user.io.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
public class VerificationTokenEntity {

    @Id
    @Column(nullable = false)
    private UUID token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private Instant createdDate;

    @Column(nullable = false)
    private Instant expiryDate;
}
