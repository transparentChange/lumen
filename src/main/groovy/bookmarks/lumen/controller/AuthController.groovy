package bookmarks.lumen.controller

import bookmarks.lumen.data.UserRepository
import bookmarks.lumen.domain.request.AuthRequest
import bookmarks.lumen.domain.User
import bookmarks.lumen.domain.request.CreateUserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import java.time.Instant
import java.util.stream.Collectors

@RestController
@CrossOrigin
class AuthController {
    @Autowired
    private final AuthenticationManager authManager

    @Autowired
    private final UserRepository userRepository

    @Autowired
    private final JwtEncoder jwtEncoder

    @PostMapping("register")
    User register(@RequestBody @Valid CreateUserRequest request) {
        User u = new User()
        u.setUsername(request.username)
        u.setPassword(request.password)
        u.setEmail(request.email)
        return this.userRepository.save(u)
    }

    @PostMapping("login")
    ResponseEntity<User> login(@RequestBody @Valid AuthRequest request) {
        try {
            System.out.println(request.getUsername() + " " + request.getPassword())
            Authentication authentication = authManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    )

            User user = (User) authentication.getPrincipal()

            Instant now = Instant.now()
            long expiry = 36000L;

            String scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors::joining(" "))

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("lumen.io")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiry))
                    .subject(user.getId().toString())
                    .claim("roles", scope)
                    .build()

            String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(user)

        } catch (BadCredentialsException ex) {
            System.out.println(ex)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        } catch (Exception e) {
            System.out.println("Exception " + e)
        }
    }

}
