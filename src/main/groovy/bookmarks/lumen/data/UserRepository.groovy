package bookmarks.lumen.data

import bookmarks.lumen.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
@Transactional
interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username)
}
