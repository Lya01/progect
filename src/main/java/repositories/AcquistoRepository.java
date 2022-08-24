package repositories;
import entities.*;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto,Integer>{

    List<Acquisto> findByAcquirente(Cliente acquirente);
    List<Acquisto> findByDataDiAcquisto(Date data);
    List<Acquisto> findByFine(boolean fine);
    Acquisto findByIdacquisto(int id);

    @Query("select p from Acquisto p where p.dataDiAcquisto > ?1 and p.dataDiAcquisto < ?2 and p.acquirente = ?3")
    List<Acquisto> findByAcquirenteInPeriodo(Date inizio,Date fine, Cliente acquirente);


}
