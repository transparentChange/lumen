package bookmarks.lumen.domain.request

import groovyjarjarantlr4.v4.runtime.misc.NotNull
import javax.validation.constraints.Email


class CreateUserRequest {
    @NotNull String username;
    @NotNull String password;
    @Email String email;
}
