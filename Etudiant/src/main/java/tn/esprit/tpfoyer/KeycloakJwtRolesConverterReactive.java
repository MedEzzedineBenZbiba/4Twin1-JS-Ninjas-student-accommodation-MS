package tn.esprit.tpfoyer;



import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;

import java.util.Collection;

// Given a JWT, return a reactive stream of authorities (user roles, permissions).
public class KeycloakJwtRolesConverterReactive implements Converter<Jwt, Flux<GrantedAuthority>> {

    private final KeycloakJwtRolesConverter delegate = new KeycloakJwtRolesConverter();

    @Override
    public Flux<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = delegate.convert(jwt);
        // We return a Flux<GrantedAuthority> because we're using Spring WebFlux,
        // which is reactive (non-blocking and asynchronous). Flux represents a stream of zero or more elements.
        return Flux.fromIterable(authorities);
    }
}
