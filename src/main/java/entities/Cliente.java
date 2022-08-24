package entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name="clienti",schema="orders")



public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id",nullable = false)

    private int id;


    @Basic
    @Column(name="codice",nullable = true,length=70)
    private String codice;

    @Basic /*significa che si avrà una mappatura, l'attributo deve essere persistente*/
    @Column(name="nome",nullable=true,length=50)//gli dico dove lo deve andare a prendere
    private String nome;

    @Basic
    @Column(name="cognome",nullable = true,length=50)
    private String cognome;

    @Basic
    @Column(name="telefono",nullable=true,length=20)
    private String numeroDiTelefono;

    @Basic
    @Column(name="indirizzo",nullable=true,length=200)
    private String indirizzo;

    @Basic
    @Column(name="email",nullable = true,length=80)
    private String email;

    //ogni cliente ha una lista di acquisti,
    @ToString.Exclude
    @OneToMany(mappedBy="acquirente",cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<ProdottoAcquistato> carrello=new ArrayList<>();
}
