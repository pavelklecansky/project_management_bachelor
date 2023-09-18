package cz.klecansky.projectmanagement.task.io;

import cz.klecansky.projectmanagement.comment.io.CommentEntity;
import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
import cz.klecansky.projectmanagement.phase.io.PhaseEntity;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.task.shared.Priority;
import cz.klecansky.projectmanagement.task.shared.Status;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tasks")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@With
@Getter
@Setter
public class TaskEntity {

    @Id
    UUID id;

    @Column(nullable = false, length = 50)
    String name;

    String description;

    Instant startDate;

    Instant endDate;

    @Column(nullable = false)
    Priority priority;

    Status status;

    @Column(nullable = false, columnDefinition = "integer default 0")
    Integer progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    UserEntity assigned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groups_id")
    GroupEntity assignedForGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phases_id")
    PhaseEntity phase;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "projects_id", nullable = false)
    ProjectEntity project;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    List<CommentEntity> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskEntity that = (TaskEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
