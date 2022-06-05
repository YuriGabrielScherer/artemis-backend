package br.com.karate.repository.belt;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.belt.Belt;

public interface BeltCustomRepository {

    public Belt findNextBelt(EnumBelt belt);

}
