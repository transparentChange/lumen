package bookmarks.lumen.data

import bookmarks.lumen.domain.Bookmark
import org.hibernate.annotations.SQLInsert
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
@Transactional
@SQLInsert(sql = "INSERT IGNORE INTO bookmark (date_added, location, title, url) VALUES (?, ?, ?, ?)")
interface BookmarkRepository extends CrudRepository<Bookmark, Long> {

}
