package br.com.karate.repository.belt;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.belt.Belt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeltRepository extends JpaRepository<Belt, UUID> {

    public Belt findByBelt(EnumBelt belt);

}
