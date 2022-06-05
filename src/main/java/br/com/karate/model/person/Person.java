package br.com.karate.model.person;

import br.com.karate.enums.EnumGender;
import br.com.karate.model.association.Association;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.professor.Professor;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "people")
public class Person extends EntityAudit<UUID> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    private long code;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private EnumGender gender;

    @Column(length = 11)
    private String document;
    private LocalDate birth;

    @JoinColumn(name = "id_association")
    @OneToOne(fetch = FetchType.LAZY)
    private Association association;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private Athlete athlete;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private Professor professor;
}

