package cz.klecansky.projectmanagement.outcome.io;

import cz.klecansky.projectmanagement.phase.io.PhaseEntity;
import cz.klecansky.projectmanagement.result.io.ResultEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "outcomes")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@With
public class OutcomeEntity {

    @Id
    UUID id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false)
    Instant startDate;

    @Column(nullable = false)
    Instant endDate;

    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outcome_category_id")
    OutcomeCategoryEntity outcomeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phases_id")
    PhaseEntity phase;

    @OneToMany
    @JoinColumn(name = "outcome_id")
    List<ResultEntity> results = new ArrayList<>();

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
