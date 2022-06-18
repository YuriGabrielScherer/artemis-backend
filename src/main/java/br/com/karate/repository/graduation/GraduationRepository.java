package br.com.karate.repository.graduation;

import br.com.karate.model.graduation.Graduation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GraduationRepository extends JpaRepository<Graduation, UUID> {

    public List<Graduation> findAllByCodeIn(List<Long> codeList);

    Graduation findFirstByOrderByCodeDesc();

    Optional<Graduation> findByCode(long code);

    public long countByDateAfter(LocalDate date);
}
