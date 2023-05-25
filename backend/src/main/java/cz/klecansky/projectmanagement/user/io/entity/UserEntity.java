package cz.klecansky.projectmanagement.user.io.entity;

import cz.klecansky.projectmanagement.group.io.entity.GroupMemberEntity;
import cz.klecansky.projectmanagement.organization.io.OrganizationEntity;
import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import cz.klecansky.projectmanagement.task.io.TaskEntity;
import cz.klecansky.projectmanagement.user.shared.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
/*
    @Serial private static final long serialVersionUID = 5313493413859894403L;
*/

    @Id
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    private String phoneNumber;

    private String note;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(nullable = false)
    private boolean accountNotLocked = true;

    @Column(nullable = false)
    private boolean accountNotExpired = true;

    @Column(nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(nullable = false)
    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) private List<PasswordResetTokenEntity> passwordResetTokenEntities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) private List<VerificationTokenEntity> verificationTokenEntities = new ArrayList<>();

    @OneToMany(mappedBy = "assigned", cascade = {CascadeType.PERSIST, CascadeType.MERGE}) private List<TaskEntity> tasks = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_organization_entities",
            joinColumns = @JoinColumn(name = "user_entity"),
            inverseJoinColumns = @JoinColumn(name = "organization_entities"))
    private List<OrganizationEntity> organizations = new ArrayList<>();

    @ManyToMany(mappedBy = "members", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProjectEntity> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<GroupMemberEntity> groupMemberEntities = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        tasks.forEach(taskEntity -> taskEntity.setAssigned(null));
        projects.forEach(projectEntity -> projectEntity.getMembers().remove(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity that = (UserEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
