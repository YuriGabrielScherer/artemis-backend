package br.com.karate.model.belt;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "belts")
public class Belt extends EntityAudit<UUID> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumBelt belt;

    @NotNull
    @Column(name = "min_months")
    private int minMonths;

    @NotNull
    private int sequence;

    @OneToMany(mappedBy = "belt")
    private Set<AthleteGraduation> graduations;
}
