package bookmarks.lumen.domain

import org.springframework.security.core.GrantedAuthority

class Role implements GrantedAuthority {
    public static final String USER = "USER"

    private String authority

    @Override
    String getAuthority() {
        return this.authority
    }
}
