package cz.klecansky.projectmanagement.phase.io;

import cz.klecansky.projectmanagement.outcome.io.OutcomeEntity;
import cz.klecansky.projectmanagement.task.io.TaskEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phases")
@Getter
@Setter
public class PhaseEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = false)
    private Instant endDate;

    private String note;

    @OneToMany
    @JoinColumn(name = "phases_id")
    private List<TaskEntity> taskEntities = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "phases_id")
    private List<OutcomeEntity> outcomes = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        taskEntities.forEach(taskEntity -> taskEntity.setPhase(null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhaseEntity that = (PhaseEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
