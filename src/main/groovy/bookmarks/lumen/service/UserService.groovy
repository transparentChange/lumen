package bookmarks.lumen.service

import bookmarks.lumen.data.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService { //implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository

    private final PasswordEncoder passwordEncoder

    /*
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userRepo
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                format("User: %s not found", username)
                        )
                )
    }
     */
}
