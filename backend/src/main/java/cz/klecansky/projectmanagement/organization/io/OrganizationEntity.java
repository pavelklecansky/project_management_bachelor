package cz.klecansky.projectmanagement.organization.io;

import cz.klecansky.projectmanagement.user.io.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "organizations")
@Getter
@Setter
public class OrganizationEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    private String email;

    private String ico;

    private String phoneNumber;

    private String note;

    @ManyToMany(mappedBy = "organizations", cascade = {CascadeType.PERSIST}) private List<UserEntity> users = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        users.forEach(userEntity -> userEntity.getOrganizations().remove(this));
    }
}
