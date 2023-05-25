package cz.klecansky.projectmanagement.schedule.io;


import cz.klecansky.projectmanagement.project.io.ProjectEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@Getter
@Setter
public class ScheduleEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @OneToMany(mappedBy="schedule", cascade = CascadeType.ALL)
    private List<RowEntity> rows = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private List<TaskScheduleEntity> tasks = new ArrayList<>();


}
