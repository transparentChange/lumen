package bookmarks.lumen.util

import bookmarks.lumen.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

import javax.persistence.EntityManager
import java.security.Principal

class UserUtil {

    @Autowired
    static EntityManager em

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication()
    }

    public static Long getCurrentUserId() {
        String currentPrincipalName = getAuthentication().getName();
        return Long.parseLong(currentPrincipalName)
    }
}
