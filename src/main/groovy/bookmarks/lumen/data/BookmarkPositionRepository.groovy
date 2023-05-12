package bookmarks.lumen.data

import javax.transaction.Transactional
import org.springframework.data.repository.CrudRepository

import bookmarks.lumen.domain.BookmarkPosition

@Transactional
interface BookmarkPositionRepository extends CrudRepository<BookmarkPosition, Long> {
	static final Long INCREMENT_BY = 200
}