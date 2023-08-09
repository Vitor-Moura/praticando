package vitormoura.apipraticando.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitormoura.apipraticando.domain.entities.Pagamento;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    List<Pagamento> findByDataHoraPagamentoNotNull();
}
