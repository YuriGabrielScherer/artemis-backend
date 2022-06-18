package br.com.karate.controller;

import br.com.karate.controller.abstracts.CrudAbstractController;
import br.com.karate.converter.AssociationConverter;
import br.com.karate.model.association.Association;
import br.com.karate.model.association.AssociationInput;
import br.com.karate.model.association.AssociationOutput;
import br.com.karate.model.association.AssociationOutput.TopAssociations;
import br.com.karate.service.association.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/association")
public class AssociationController extends CrudAbstractController<Association, AssociationInput.Save, AssociationOutput.Dto,  AssociationInput.Filter> {

    @Autowired
    private AssociationService service;
    @Autowired
    private AssociationConverter converter;

    @GetMapping("/listTopAssociationsByAthletes")
    @Transactional(readOnly = true)
    public ResponseEntity<List<TopAssociations>> listTopAssociationsByAthletes() {
        final List<Association> associations = service.listTopAssociationsByAthletes();
        return ResponseEntity.ok(converter.toTopAthleteAssociations(associations));
    }

}
