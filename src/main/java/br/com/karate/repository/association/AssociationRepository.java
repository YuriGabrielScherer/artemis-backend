package br.com.karate.repository.association;

import br.com.karate.model.association.Association;
import br.com.karate.repository.AbstractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssociationRepository extends JpaRepository<Association, UUID> {

    public Optional<Association> findByCode(long code);

    public Association findFirstByOrderByCodeDesc();


}
