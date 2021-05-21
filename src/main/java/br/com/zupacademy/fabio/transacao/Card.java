package br.com.zupacademy.fabio.transacao;

import javax.persistence.Embeddable;

@Embeddable
public class Card {
    private String id;
    private String email;

    public Card() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
