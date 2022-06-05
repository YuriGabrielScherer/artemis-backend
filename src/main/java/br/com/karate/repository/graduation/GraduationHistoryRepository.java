package br.com.karate.repository.graduation;

import br.com.karate.model.graduation.history.GraduationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GraduationHistoryRepository extends JpaRepository<GraduationHistory, UUID> {
}
