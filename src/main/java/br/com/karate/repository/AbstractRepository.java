package br.com.karate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;
@NoRepositoryBean
public interface AbstractRepository<T> extends JpaRepository<T, UUID> {
    public Optional<T> findByCode(long code);
}
