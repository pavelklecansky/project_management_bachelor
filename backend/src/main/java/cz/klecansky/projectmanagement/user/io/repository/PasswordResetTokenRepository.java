package cz.klecansky.projectmanagement.user.io.repository;

import cz.klecansky.projectmanagement.user.io.entity.PasswordResetTokenEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, UUID> {}
