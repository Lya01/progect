package services;

import entities.*;
import repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import support.QuantitaNonDisponibileException;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import support.ClienteNonTrovatoException;
import support.DataErrataException;
@Service
public class AcquistoService {
    @Autowired
    private AcquistoRepository acquistoRepository;

    public List<Acquisto> getAll() {
        return acquistoRepository.findAll();
    }

    public void setAcquistoDone(Integer id, Boolean fine) {
        Acquisto p = acquistoRepository.findByIdacquisto(id);
        p.setFine(fine);
        acquistoRepository.flush();
        return;
    }

}