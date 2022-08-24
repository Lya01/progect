package entities;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="acquisti",schema="orders")
public class Acquisto {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id",nullable = false)
private int id;

@Column(name="quantita")
private int quantita;

@Basic
@CreationTimestamp
@Temporal(TemporalType.TIMESTAMP)
@Column(name="data_acquisto")
private Date dataDiAcquisto;

@ManyToOne
@JoinColumn(name="acquirente")
private Cliente acquirente;

@Column(name="fine")
    private boolean fine;



}
