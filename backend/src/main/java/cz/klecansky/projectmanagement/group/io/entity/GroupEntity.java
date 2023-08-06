package cz.klecansky.projectmanagement.group.io.entity;

import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.task.io.TaskEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "groups")
@Getter
@Setter
public class GroupEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private List<GroupMemberEntity> members;

    @ManyToMany(
            mappedBy = "memberGroups",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProjectEntity> projects = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "groups_id")
    private List<TaskEntity> taskAssignedTo = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        taskAssignedTo.forEach(taskEntity -> taskEntity.setAssignedForGroup(null));
        projects.forEach(projectEntity -> projectEntity.getMemberGroups().remove(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupEntity that = (GroupEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
