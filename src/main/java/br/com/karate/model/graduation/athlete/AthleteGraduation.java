package br.com.karate.model.graduation.athlete;

import br.com.karate.enums.EnumAthleteGraduationSituation;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.belt.Belt;
import br.com.karate.model.graduation.Graduation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "athlete_graduation")
public class AthleteGraduation extends EntityAudit<UUID> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_belt")
    private Belt belt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_athlete")
    private Athlete athlete;

    @ManyToOne
    @JoinColumn(name = "id_graduation")
    private Graduation graduation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumAthleteGraduationSituation situation;

    @Min(value = 0)
    private Double grade;

}
