package cz.klecansky.projectmanagement.schedule.io;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "schedule_tasks")
@Getter
@Setter
public class TaskScheduleEntity {

    @Id
    private UUID realId;

    private int id;

    private String label;

    private int resourceId;

    private Instant fromDate;

    private Instant toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "row_id")
    private RowEntity row;
}
