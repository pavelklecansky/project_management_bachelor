package cz.klecansky.projectmanagement.user.io.repository;

import cz.klecansky.projectmanagement.user.io.entity.VerificationTokenEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, UUID> {}
