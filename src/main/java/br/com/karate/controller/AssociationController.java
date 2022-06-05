package br.com.karate.controller;

import br.com.karate.controller.abstracts.CrudAbstractController;
import br.com.karate.model.association.Association;
import br.com.karate.model.association.AssociationInput;
import br.com.karate.model.association.AssociationOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/association")
public class AssociationController extends CrudAbstractController<Association, AssociationInput.Save, AssociationOutput.Dto,  AssociationInput.Save> {

}
