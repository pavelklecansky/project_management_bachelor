package cz.klecansky.projectmanagement.user.io.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "new_user_passcode")
@Getter
@Setter
public class NewUserPasscodeEntity {

    @Id
    private UUID id;

    @Min(1000000)
    @Max(9999999)
    @Column(nullable = false)
    private int passcode;
}
