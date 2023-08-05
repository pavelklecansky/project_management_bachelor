package cz.klecansky.projectmanagement.schedule.io;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "schedule_rows")
@Getter
@Setter
public class RowEntity {

    @Id
    private UUID realId;

    private int id;

    private String label;

    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskScheduleEntity> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;

    @PreRemove
    private void preRemove() {
        tasks.clear();
    }
}
