package br.com.zupacademy.fabio.transacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerTransaction {

    private final RepositoryTransaction repositoryTransaction;
    private final Logger logger = LoggerFactory.getLogger(Transaction.class);

    @Autowired
    public ListenerTransaction(RepositoryTransaction repositoryTransaction) {
        this.repositoryTransaction = repositoryTransaction;
    }

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void listener(ResponseEventTransaction eventoDeTransacao) {
        System.out.println(eventoDeTransacao.toString());
        saveTransaction(eventoDeTransacao);
    }

    private void saveTransaction(ResponseEventTransaction eventTransaction){
        Transaction transaction = eventTransaction.convertToTransaction();
        repositoryTransaction.save(transaction);
        logger.info("Transaction received for card {}", offuscate(transaction.getCard().getId()));
    }

    private String offuscate(String text){
        int length = text.length();
        String firstPart = text.substring(1, 4);
        String lastPart = text.substring(length - 5, length-1);
        return firstPart + "-****-****-" + lastPart;
    }

}
