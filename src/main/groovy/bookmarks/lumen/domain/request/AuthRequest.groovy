package bookmarks.lumen.domain.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class AuthRequest {
    @NotNull String username
    @NotNull String password
}
