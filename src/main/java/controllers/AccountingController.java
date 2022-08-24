package controllers;


import entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.AccountingService;
import support.MailClienteNonEsisteException;
import support.ResponseMessage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clienti")
public class AccountingController {
    @Autowired
    private AccountingService accountingService;

    @PostMapping
    public ResponseEntity crea(@RequestBody @Valid Cliente cliente){
        try{
            Cliente aggiunto=accountingService.registraCliente(cliente);
            return new ResponseEntity(aggiunto, HttpStatus.OK);
        } catch (MailClienteNonEsisteException e) {
            return new ResponseEntity<>(new ResponseMessage("L'email non esiste"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Cliente> getAll(){return accountingService.getTuttiClienti();}
}
