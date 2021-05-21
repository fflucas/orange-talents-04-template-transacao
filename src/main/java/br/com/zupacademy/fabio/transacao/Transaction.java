package br.com.zupacademy.fabio.transacao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    private String id;
    private BigDecimal value;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nome", column = @Column(name = "store_name")),
            @AttributeOverride(name = "cidade", column = @Column(name = "store_city")),
            @AttributeOverride(name = "endereco", column = @Column(name = "store_address"))
    })
    private Establishment establishment;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "card_number")),
            @AttributeOverride(name = "email", column = @Column(name = "card_email")),
    })
    private Card card;
    @Temporal(TemporalType.TIMESTAMP)
    private Date madeIn;

    @Deprecated
    public Transaction() {
    }

    public Transaction(String id, BigDecimal value, Establishment establishment, Card card, Date madeIn) {
        this.id = id;
        this.value = value;
        this.establishment = establishment;
        this.card = card;
        this.madeIn = madeIn;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public Date getMadeIn() {
        return madeIn;
    }

    public Card getCard() {
        return card;
    }
}
