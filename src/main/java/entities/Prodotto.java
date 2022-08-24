package entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name="prodotti",schema = "orders")
public class Prodotto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private int id;

    @Basic
    @Column(name="nome",nullable = true,length=50)
    private String nome;

    @Basic
    @Column(name="casa_editrice",nullable=true,length=100)
    private String casaEditrice;

    @Basic
    @Column(name="bar_code",nullable=true,length=60)
    private String codiceABarre;


    @Basic
    @Column(name="descrizione",nullable=true,length=500)
    private String descrizione;

    @Basic
    @Column(name="prezzo",nullable=true)
    private float prezzo;

    @Basic
    @Column(name="quantita",nullable=true)
    private int quantita;

    @Basic
    @Column(name="image",nullable=true,length=70)
    private String image;

    @Basic
    @Column(name="version",nullable=false)
    @JsonIgnore
    private long version;

    @OneToMany(targetEntity=ProdottoAcquistato.class,mappedBy="prodotti",cascade=CascadeType.MERGE)
    @JsonIgnore
    @ToString.Exclude
    private List<ProdottoAcquistato> prodottiAcquistati;
}
