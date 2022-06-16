package br.com.karate.repository.association;

import br.com.karate.model.association.Association;
import br.com.karate.model.association.AssociationInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssociationCustomRepository {

    public Page<Association> list(AssociationInput.Filter filter, Pageable pageable);

}
