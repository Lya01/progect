package repositories;

import entities.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

    List<Cliente> findByNome(String nome);
    List<Cliente> findByCognome(String cognome);
    List<Cliente> findByNomeAndCognome(String nome,String cognome);
    List<Cliente> findByEmail(String email);
    List<Cliente> findByCodice(String codice);
    boolean existsByEmail(String email);


}
