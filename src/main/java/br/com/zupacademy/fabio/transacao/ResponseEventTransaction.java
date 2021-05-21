package br.com.zupacademy.fabio.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class ResponseEventTransaction {

    private String id;
    private BigDecimal valor;
    private Establishment estabelecimento;
    private Card cartao;
    private Date efetivadaEm;

    public ResponseEventTransaction() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setEstabelecimento(Establishment estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public void setCartao(Card cartao) {
        this.cartao = cartao;
    }

    public void setEfetivadaEm(Date efetivadaEm) {
        this.efetivadaEm = efetivadaEm;
    }

    public Transaction convertToTransaction() {
        System.out.println(this.toString());
        return new Transaction(
                id,
                valor,
                estabelecimento,
                cartao,
                efetivadaEm);
    }

    @Override
    public String toString() {
        return "ResponseEventTransaction{" +
                "id='" + id + '\'' +
                ", valor=" + valor +
                ", estabelecimento=" + estabelecimento.toString() +
                ", cartao=" + cartao.toString() +
                ", efetivadaEm=" + efetivadaEm +
                '}';
    }
}
