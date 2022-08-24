package repositories;

import entities.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.List;

@Repository
public interface ProdottoAcquistatoRepository extends JpaRepository<ProdottoAcquistato,Integer> {
    ProdottoAcquistato findByAcquirenteAndProdotto(Cliente cliente, Prodotto p);
    List<ProdottoAcquistato> findByProdotto(Prodotto p);
}
