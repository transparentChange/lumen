package bookmarks.lumen.data

import bookmarks.lumen.domain.Bookmark
import bookmarks.lumen.domain.User
import org.hibernate.annotations.SQLInsert
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

@Repository
@Transactional
//@SQLInsert(sql = """INSERT IGNORE INTO bookmark
//    (date_added, expiry_date, location, period_hours, status, title, url)
//    VALUES (?, ?, ?, ?, ?, ?, ?)""")
interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    Optional<Bookmark> findByUrl(String url)
}
