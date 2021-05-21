package br.com.zupacademy.fabio.transacao;

import br.com.zupacademy.fabio.transacao.config.error.ApiErrorException;
import br.com.zupacademy.fabio.transacao.propostaApi.PropostaApi;
import br.com.zupacademy.fabio.transacao.propostaApi.ResponseToRequestApi;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/cards/{id_card}/transactions")
public class ControllerTransaction {

    private final LegacyApiTransaction transactionApi;
    private final PropostaApi propostaApi;
    private final RepositoryTransaction repositoryTransaction;
    private ResponseToRequestApi card;
    @Autowired
    public ControllerTransaction(LegacyApiTransaction transactionApi, PropostaApi propostaApi, RepositoryTransaction repositoryTransaction) {
        this.transactionApi = transactionApi;
        this.propostaApi = propostaApi;
        this.repositoryTransaction = repositoryTransaction;
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @PathVariable Long id_card,
            @RequestBody @NotBlank String email,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ){
        try {
            card = propostaApi.getCard(id_card, token);
        }catch (FeignException.Unauthorized feu){
            throw new ApiErrorException(HttpStatus.UNAUTHORIZED, "O servidor de autorização não aceitou o token");
        }catch (FeignException fe) {
            fe.printStackTrace();
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Algo saiu errado com o servidor de propostas");
        }

        LegacyApiTransaction.RequestTransaction requestTransaction = new LegacyApiTransaction.RequestTransaction(
                card.getNumber(),
                email
        );
        try{
            transactionApi.startTransactions(requestTransaction);
        }catch (FeignException fe){
            fe.printStackTrace();
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Algo saiu errado com o servidor de transações");
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> stop(
            @PathVariable Long id_card,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ){
        try {
            card = propostaApi.getCard(id_card, token);
        }catch (FeignException.Unauthorized feu){
            throw new ApiErrorException(HttpStatus.UNAUTHORIZED, "O servidor de autorização não aceitou o token");
        }catch (FeignException fe){
            fe.printStackTrace();
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Algo saiu errado com o servidor de propostas");
        }

        try{
            transactionApi.stopTransactions(card.getNumber());
        }catch (FeignException fe){
            fe.printStackTrace();
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Algo saiu errado com o servidor de transações");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> listTen(
            @PathVariable Long id_card,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ){
        try {
            card = propostaApi.getCard(id_card, token);
        }catch (FeignException.Unauthorized feu){
            throw new ApiErrorException(HttpStatus.UNAUTHORIZED, "O servidor de autorização não aceitou o token");
        }catch (FeignException fe){
            fe.printStackTrace();
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, "Algo saiu errado com o servidor de propostas");
        }

        List<Transaction> lastTransactions = repositoryTransaction.findTenByCardIdOrderByMadeInDesc(card.getNumber());
        return ResponseEntity.ok(lastTransactions);
    }
}
