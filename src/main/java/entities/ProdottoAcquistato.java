package entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name="prodotti_acquistati",schema="orders")
public class ProdottoAcquistato {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name="acquisto_relativo")
    @JsonIgnore
    @ToString.Exclude
    private Acquisto acquisto;

    @Basic
    @Column(name="quantita",nullable = true)
    private int quantita;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="prodotti")
    private Prodotto prodotto;
}
