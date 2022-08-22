package bookmarks.lumen.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name="user")
class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username

    @Column(nullable = false)
    private String password

    private String email

    @Transient
    private Set<Role> authorities = new HashSet<>()

    @Transient
    private boolean enabled = true

    @Transient
    @Autowired
    private PasswordEncoder encoder = new BCryptPasswordEncoder()

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getUsername() {
        return username
    }

    @Override
    boolean isAccountNonExpired() {
        return enabled
    }

    @Override
    boolean isAccountNonLocked() {
        return enabled
    }

    @Override
    boolean isCredentialsNonExpired() {
        return enabled
    }

    @Override
    boolean isEnabled() {
        return enabled
    }

    void setUsername(String username) {
        this.username = username
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = encoder.encode(password)
    }

    void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }
}
