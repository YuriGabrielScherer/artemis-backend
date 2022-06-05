package br.com.karate.repository.athlete;

import br.com.karate.model.athlete.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, UUID> {
    public Athlete findByPersonCode(long code);
}
