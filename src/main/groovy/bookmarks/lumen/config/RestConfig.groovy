package bookmarks.lumen.config

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.web.SecurityFilterChain

import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

/*
class RestConfig {
    @Value('${jwt_pub}')
    RSAPublicKey pub

    @Value('${jwt_priv}')
    RSAPrivateKey priv

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.ignoreAntMatchers("/login"))
            .httpBasic(Customizer.withDefaults())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
        return http.build()

    }

    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build()
    }

    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build()
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk))
        return new NimbusJwtEncoder(jwks)
    }
}

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.interfaces.RSAPrivateKey;

@Component
@ConfigurationPropertiesBinding
public class MyPrivateKeyConverter implements Converter<String, RSAPrivateKey> {
    @Override
    public RSAPrivateKey convert(String from) {
        return RsaKeyConverters.pkcs8().convert(new ByteArrayInputStream(from.getBytes()));
    }
}

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.interfaces.RSAPublicKey;

@Component
@ConfigurationPropertiesBinding
public class MyPublicKeyConverter implements Converter<String, RSAPublicKey> {
    @Override
    public RSAPublicKey convert(String from) {
        return RsaKeyConverters.x509().convert(new ByteArrayInputStream(from.getBytes()));
    }
}
 */
