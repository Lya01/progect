package controllers;


import entities.Prodotto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.ProdottoService;
import support.CodiceABarreExistException;
import support.ResponseMessage;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    @Autowired
    private ProdottoService prodottoService;

    @PostMapping
    public ResponseEntity crea(@RequestBody @Valid Prodotto prodotto){
        try{
            prodottoService.aggiungiProdotto(prodotto);
            System.out.println(prodotto);
        }catch(CodiceABarreExistException e){
            return new ResponseEntity<>(new ResponseMessage("Il codice a barre non esiste!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Aggiunto con successo"),HttpStatus.OK);
    }

    @GetMapping
    public List<Prodotto> getAll(){return prodottoService.mostraTutti();}

    @GetMapping("/paged")//o paged...
    public ResponseEntity getAll(@RequestParam(value="numeroPagina", defaultValue = "0") int numeroPagina,@RequestParam(value="lunghezzaPagina",defaultValue="10") int lunghezzaPagina, @RequestParam(value="sortBy",defaultValue = "id") String sortBy){
        List<Prodotto> ris=prodottoService.mostraListaProdotti(numeroPagina,lunghezzaPagina,sortBy);
        if(ris.size()<=0){
            return new ResponseEntity<>(new ResponseMessage("Non ci sono risultati"),HttpStatus.OK);
        }
        return new ResponseEntity<>(ris,HttpStatus.OK);
    }

    public ResponseEntity getByName(@RequestParam(required=false) String nome){
        List<Prodotto> ris=prodottoService.mostraProdottiByNome(nome);
        if(ris.size()<=0){
            return new ResponseEntity<>(new ResponseMessage("Non ci sono risultati"),HttpStatus.OK);
        }
        return new ResponseEntity<>(ris,HttpStatus.OK);
    }

}
