package controllers;

import entities.Acquisto;
import entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.AcquistoService;
import support.ClienteNonTrovatoException;
import support.DataErrataException;
import support.QuantitaNonDisponibileException;
import support.ResponseMessage;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/acquisti")
public class AcquistoController {
    @Autowired
    private AcquistoService acquistoService;

    @PostMapping
    @ResponseStatus(code= HttpStatus.OK)
    public ResponseEntity crea(@RequestBody @Valid Acquisto acquisto){
        try{
            return new ResponseEntity<>(acquistoService.aggiungiAcquisto(acquisto),HttpStatus.OK);
        }catch(QuantitaNonDisponibileException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantità prodotto non disponibile",e);
        }
    }

    @GetMapping("/{cliente}")
    public List<Acquisto> getAcquisti(@RequestBody @Valid Cliente cliente){
        try{
            return acquistoService.getAcquistoByCliente(cliente);
        }catch(ClienteNonTrovatoException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cliente non trovato",e);
        }

    }

    @GetMapping("/{cliente}/{inizio}/{fine}")
    public ResponseEntity getAcquistiPeriodo(@Valid @PathVariable("cliente")Cliente cliente, @PathVariable("inizio")@DateTimeFormat(pattern="dd-MM-yyyy") Date inizio,@PathVariable("fine") @DateTimeFormat(pattern="dd-MM-yyyy")Date fine){
        try{
            List<Acquisto> ris = acquistoService.getAcquistoByClientePeriodo(cliente, inizio, fine);
            if ( ris.size() <= 0 ) {
                return new ResponseEntity<>(new ResponseMessage("Nessun risultato!"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ris, HttpStatus.OK);
        } catch (ClienteNonTrovatoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found XXX!", e);
        } catch (DataErrataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be previous end date XXX!", e);
        }
    }
}
