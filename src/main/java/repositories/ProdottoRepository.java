package repositories;

import entities.Prodotto;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto,Integer>{


    List<Prodotto> findByNomeContaining(String nome);

    Prodotto findByNome(String nome);
    List<Prodotto> findByCodiceABarre(String codiceABarre);
    boolean existsByCodiceABarre(String codiceABarre);
    boolean existsById(int id);

    Prodotto findProdottoById(int id);//l'ho aggiunto io

    void remove(int id);

}
