package services;




import entities.Acquisto;
import entities.Cliente;
import entities.ProdottoAcquistato;
import repositories.AcquistoRepository;
import repositories.ClienteRepository;
import repositories.ProdottoAcquistatoRepository;
import repositories.ProdottoRepository;
import entities.Prodotto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import support.*;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AcquistoRepository acquistoRepository;

    @Autowired
    private ProdottoAcquistatoRepository prodottoAcquistatoRepository;


    @Transactional(readOnly = false)
    public void aggiungiProdotto(Prodotto prodotto) throws CodiceABarreExistException{
        /*if(prodotto.getCodiceABarre() !=null && prodottoRepository.existsByCodiceABarre(prodotto.getCodiceABarre())){
            throw new CodiceABarreExistException();
        }*/
        prodottoRepository.save(prodotto);
        System.out.println("sono nel service");
    }

    //Ho un prodotto esistente ma ne voglio caricare un altro uguale con dati differenti
    @Transactional(readOnly = false)
    public Prodotto modificaProdotto(Prodotto prodotto){
        boolean esiste=prodottoRepository.existsById(prodotto.getId());
        if(esiste){return prodottoRepository.save(prodotto);}
        return null;
    }


    @Transactional(readOnly = false)
    public void rimuoviProdotto(int id) throws ProdottoNonTrovatoException{
        if(!prodottoRepository.existsById(id)){
            throw new ProdottoNonTrovatoException();
        }
        prodottoRepository.remove(id);
    }//aggiunto da me


    @Transactional(readOnly = true)
    public List<Prodotto> mostraTutti(){
        return prodottoRepository.findAll();}

    @Transactional(readOnly = true)
    public List<Prodotto> mostraListaProdotti(int numeroPagina,int dimensionePagina, String sortBy) {
        Pageable paging = PageRequest.of(numeroPagina, dimensionePagina, Sort.by(sortBy));
        Page<Prodotto> pagedResult = prodottoRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

        @Transactional(readOnly = true)
        public List<Prodotto> mostraProdottiByNome(String nome){
            return prodottoRepository.findByNomeContaining(nome);
        }//mostraListaProdotti


    @Transactional()
    public void compraProdotto(Cliente cliente, String nomeProd, int quantita) throws RuntimeException, ProdottoNonTrovatoException, QuantitaNonDisponibileException {
        //int quantita=Integer.parseInt(qta);
        Prodotto p=prodottoRepository.findByNome(nomeProd);
        if(p==null)
            throw new ProdottoNonTrovatoException();
        int newQta=p.getQuantita()-quantita;
        if(newQta<0)
            throw new QuantitaNonDisponibileException();

        Acquisto acquisto=new Acquisto(null, quantita,null ,cliente,false);
        acquistoRepository.save(acquisto);
        p.setQuantita(quantita);
        ProdottoAcquistato temp=prodottoAcquistatoRepository.findByAcquirenteAndProdotto(cliente,p);
        prodottoAcquistatoRepository.delete(temp);
        prodottoRepository.flush();
    }

    @Transactional()
    public void acquistaCart(@RequestParam String email) throws QuantitaNonDisponibileException, ProdottoNonTrovatoException, CarrelloVuotoException, ClienteNonTrovatoException {
        System.out.println("AcquistaCart");
        Cliente cliente= (Cliente) clienteRepository.findByEmail(email);
        if(cliente==null)
            throw new ClienteNonTrovatoException();
        if(cliente.getCarrello().size()==0)
            throw new CarrelloVuotoException();
        for(ProdottoAcquistato p: cliente.getCarrello()){
            compraProdotto(cliente,p.getProdotto().getNome(),p.getQuantita());
        }
    }






    public List<Prodotto> mostraProdottiByCodiceABarre(String codiceABarre){return prodottoRepository.findByCodiceABarre(codiceABarre);}

}
