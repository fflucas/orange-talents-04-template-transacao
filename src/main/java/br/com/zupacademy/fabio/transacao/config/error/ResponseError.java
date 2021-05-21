package br.com.zupacademy.fabio.transacao.config.error;

import java.util.Collection;

public class ResponseError {
    private Collection<String> messages;

    public ResponseError(Collection<String> messages) {
        this.messages = messages;
    }

    public Collection<String> getMessages() {
        return messages;
    }
}
