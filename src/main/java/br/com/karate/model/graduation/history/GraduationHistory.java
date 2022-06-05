package br.com.karate.model.graduation.history;

import br.com.karate.enums.EnumGraduationSituation;
import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.graduation.Graduation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "graduation_history")
public class GraduationHistory extends EntityAudit<UUID> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnumGraduationSituation situation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_graduation")
    private Graduation graduation;
}
