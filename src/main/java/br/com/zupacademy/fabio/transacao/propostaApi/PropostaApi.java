package br.com.zupacademy.fabio.transacao.propostaApi;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "proposta", url = "${proposta.hostname}")
public interface PropostaApi {

    @RequestMapping(method = RequestMethod.GET, value = "${proposta.request}/{id}", headers = "{authorization}")
    ResponseToRequestApi getCard(@PathVariable(name = "id") Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
