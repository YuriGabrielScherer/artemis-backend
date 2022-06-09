package br.com.karate.service.association;

import br.com.karate.model.association.Association;
import br.com.karate.model.association.AssociationInput;
import br.com.karate.service.abstracts.CrudAbstractService;

public interface AssociationService extends CrudAbstractService<Association, AssociationInput.Filter> {
}
