package vitormoura.apipraticando.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitormoura.apipraticando.model.Pagamento;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    List<Pagamento> findByDataHoraPagamentoNotNull();
}
