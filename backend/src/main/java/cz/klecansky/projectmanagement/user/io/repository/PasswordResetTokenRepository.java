package cz.klecansky.projectmanagement.user.io.repository;

import cz.klecansky.projectmanagement.user.io.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, UUID> {
}
