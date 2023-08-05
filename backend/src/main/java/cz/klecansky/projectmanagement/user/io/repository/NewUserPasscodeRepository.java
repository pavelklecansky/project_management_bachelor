package cz.klecansky.projectmanagement.user.io.repository;

import cz.klecansky.projectmanagement.user.io.entity.NewUserPasscodeEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewUserPasscodeRepository extends JpaRepository<NewUserPasscodeEntity, UUID> {
    Optional<NewUserPasscodeEntity> findNewUserPasscodeEntityByPasscode(int passcode);
}
