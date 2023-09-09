package cz.klecansky.projectmanagement.project.io;

import cz.klecansky.projectmanagement.budget.io.BudgetEntity;
import cz.klecansky.projectmanagement.comment.io.CommentEntity;
import cz.klecansky.projectmanagement.group.io.entity.GroupEntity;
import cz.klecansky.projectmanagement.outcome.io.OutcomeCategoryEntity;
import cz.klecansky.projectmanagement.phase.io.PhaseEntity;
import cz.klecansky.projectmanagement.schedule.io.ScheduleEntity;
import cz.klecansky.projectmanagement.task.io.TaskEntity;
import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "projects")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@With
@Getter
@Setter
public class ProjectEntity {

    @Id
    UUID id;

    @Column(nullable = false, length = 50)
    String name;

    String description;

    Instant startDate;

    Instant endDate;

    @OneToMany(
            mappedBy = "project",
            cascade = {CascadeType.PERSIST},
            orphanRemoval = true)
    List<TaskEntity> tasks;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    List<OutcomeCategoryEntity> outcomeCategories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id")
    List<PhaseEntity> phases = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "project_user_entities",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<UserEntity> members = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "project_group_entities",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    List<GroupEntity> memberGroups = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    List<ScheduleEntity> scheduleEntity = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    List<BudgetEntity> budget = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        outcomeCategories.forEach(outcomeCategory -> outcomeCategory.setProject(null));
        outcomeCategories.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectEntity that = (ProjectEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
