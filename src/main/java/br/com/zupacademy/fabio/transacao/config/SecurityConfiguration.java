package br.com.zupacademy.fabio.transacao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

interface Scopes{
    String transacoes = "SCOPE_transacoes";
}

@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/v1/cards/*/transactions").hasAuthority(Scopes.transacoes)
                .antMatchers(HttpMethod.POST, "/v1/cards/*/transactions").hasAuthority(Scopes.transacoes)
                .antMatchers(HttpMethod.DELETE, "/v1/cards/*/transactions").hasAuthority(Scopes.transacoes)
                .anyRequest().authenticated()
                .and().csrf().disable()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
