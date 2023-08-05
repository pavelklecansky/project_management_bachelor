package cz.klecansky.projectmanagement.user.io.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
