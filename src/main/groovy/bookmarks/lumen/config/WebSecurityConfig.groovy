package bookmarks.lumen.config

import bookmarks.lumen.data.UserRepository
import bookmarks.lumen.domain.Role
import bookmarks.lumen.domain.User
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

import javax.servlet.http.HttpServletResponse
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value('${jwt_pub}')
    RSAPublicKey pub

    @Value('${jwt_priv}')
    RSAPrivateKey priv

    @Autowired
    UserRepository userRepository

    WebSecurityConfig() {
        //this.userRepo = userRepo
    }

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            /*
            User u = new User()
            u.setId(1)
            u.setUsername("matthew.sekirin@gmail.com")
            u.setPassword("sk")
             */
            User u
            try {
                u = this.userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        format("User: %s not found", username)
                                ))
                Role r = new Role()
                r.authority = Role.USER
                Set<Role> authorities = [r]
                u.setAuthorities(authorities)

            } catch (Exception e) {
                System.out.println(e)
            }
            return u
        }
        )
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

        http = http
            .exceptionHandling()
            .authenticationEntryPoint(
                    (request, response, ex) -> {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())
                    }
            )
            .and()

        http.authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer()
                .jwt()
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.pub).build()
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.pub).privateKey(this.priv).build()
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk))
        return new NimbusJwtEncoder(jwks)
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean()
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(["http://localhost:4200", "moz-extension://349df8b5-63f3-45e6-be5f-3661405ddc4e", "moz-extension://5bb5d3fb-2a72-47b2-93d4-01f9a0712297", "moz-extension://ebcee376-2abd-475c-abf6-cf01e244d8fc"])
        configuration.setAllowedMethods(["HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"])
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(["*"]);
        configuration.setExposedHeaders(["X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"]);
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        return source;
    }
}
