package cz.klecansky.projectmanagement.group.io;

import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {}
