package br.com.azship.gestaofretesapi.modulos.frete.repository;

import br.com.azship.gestaofretesapi.modulos.frete.model.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FreteRepository extends JpaRepository<Frete, Integer>, QuerydslPredicateExecutor<Frete> {

    boolean existsByNfe(String nfe);
}
