package cz.klecansky.projectmanagement.outcome.io;

import cz.klecansky.projectmanagement.phase.io.PhaseEntity;
import cz.klecansky.projectmanagement.result.io.ResultEntity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "outcomes")
@Getter
@Setter
public class OutcomeEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = false)
    private Instant endDate;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outcome_category_id")
    private OutcomeCategoryEntity outcomeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phases_id")
    private PhaseEntity phase;

    @OneToMany
    @JoinColumn(name = "outcome_id")
    private List<ResultEntity> results = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OutcomeEntity that = (OutcomeEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
