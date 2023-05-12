package bookmarks.lumen.data

import javax.transaction.Transactional

import org.springframework.data.repository.CrudRepository

import bookmarks.lumen.domain.Bookmark
import bookmarks.lumen.domain.SequenceNumber

@Transactional
interface SequenceRepository extends CrudRepository<SequenceNumber, Long> {
}