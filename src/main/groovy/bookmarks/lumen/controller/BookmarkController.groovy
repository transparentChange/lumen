package bookmarks.lumen.controller

import bookmarks.lumen.service.BookmarkPositionService
import bookmarks.lumen.data.BookmarkRepository
import bookmarks.lumen.data.SequenceRepository
import bookmarks.lumen.data.BookmarkPositionRepository
import bookmarks.lumen.domain.Bookmark
import bookmarks.lumen.domain.BookmarkPosition
import bookmarks.lumen.domain.BookmarkNode
import bookmarks.lumen.domain.BookmarkStatus
import bookmarks.lumen.domain.InnerNode
import bookmarks.lumen.domain.User
import bookmarks.lumen.domain.SequenceNumber
import bookmarks.lumen.util.UserUtil
import groovy.util.logging.Slf4j

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController
import org.springframework.jdbc.core.JdbcTemplate
import org.hibernate.HibernateException

import javax.persistence.EntityManager
import java.time.LocalDateTime

@Slf4j
@CrossOrigin
@RestController
public class BookmarkController {
	@Autowired private BookmarkPositionService bkService
	
	@Autowired private BookmarkRepository bkRepository
	@Autowired private SequenceRepository seqRepository
	
	@Autowired private BookmarkPositionRepository bkPosRepository

	@Autowired
	EntityManager em

	@GetMapping("/bookmarks")
	List<BookmarkPosition> getBookmarks() {
		return bkPosRepository.findAll()
	}

	@PutMapping("/bookmarks")
	List<Bookmark> putBookmarks(@RequestBody List<Bookmark> bookmarks) {
		log.info("/bookmarks PUT request: {}", bookmarks);
		def newBookmarks = []
		bookmarks.each { Bookmark b ->
			Optional<Bookmark> bookmark = bkRepository.findById(b.getId())
			if (bookmark.isPresent()) {
				Bookmark bookmarkFound = bookmark.get()
				if (!bookmarkFound.getPeriodHours()) {
					Long newHours = b.getPeriodHours()
					if (newHours) {
						bookmarkFound.setPeriodHours(newHours)
						def date = LocalDateTime.now()
						date = date.plusHours(newHours)
						bookmarkFound.setExpiryDate(date.toString())
					}
				}
				newBookmarks << bookmarkFound
			} else {
				b.setStatus(BookmarkStatus.NEW);
				newBookmarks << b
			}
		}
		log.info("Bookmarks {} saved", newBookmarks);
		return bkRepository.saveAll(newBookmarks);
	}

	@PutMapping("/bookmarkTree")
	List<Bookmark> putBookmarkTree(@RequestBody ArrayList<BookmarkNode> bookmarkTree) {
		log.info("/bookmarkTree PUT request: {}", bookmarkTree);
		User user = this.em.getReference(User.class, UserUtil.getCurrentUserId())
		def bookmarks = []
		for (BookmarkNode node : bookmarkTree) {
			try {
				SequenceNumber num = new SequenceNumber()
				seqRepository.save(num)
				Bookmark saved = bkRepository.save(new Bookmark(node, user, num, "toolbar_____"));
				bookmarks.add(saved);
			} catch (Exception e) {
				log.info("Error inserting into bookmark table ${e}")
			}
		}

		// fixes bookmarks if session is closed
		/*
		user = this.em.getReference(User.class, UserUtil.getCurrentUserId());
		for (Bookmark b : bookmarks) {
			b.setUser(user)
		}

		return bkRepository.saveAll(bookmarks)
		*/
		return bookmarks
	}

	@PutMapping("/setStatus/{id}")
	Bookmark setStatus(@PathVariable("id") Long id, @RequestBody String statusStr) {
		log.info("/setStatus/{} PUT request: {}", id, statusStr)

		BookmarkStatus status = BookmarkStatus.valueOf(statusStr)
		Bookmark bookmark = bkRepository.findById(id).get()
		bookmark.setStatus(status)
		return bkRepository.save(bookmark)
	}
	
	@PutMapping("/move/{prevIndex}/{newIndex}")
	BookmarkPosition moveInArray(@PathVariable("prevIndex") Long prevIndex, @PathVariable("newIndex") Long newIndex) {
		log.info("/move/{}/{} PUT request", prevIndex, newIndex)
		
		List<BookmarkPosition> all = bkPosRepository.findAll()
		if (prevIndex >= all.size()) {
			throw new Exception("prevIndex out of range")
		}
		if (newIndex >= all.size()) {
			throw new Exception("newIndex out of range")
		}
		if (prevIndex == newIndex) {
			return all[prevIndex]
		}
		def updated = bkService.moveInArray(all, prevIndex, newIndex)
		bkPosRepository.saveAll(updated)
		return all[prevIndex]
	}
	
	@PutMapping("/test")
	updatePosition() {
		Long n = 199
		seqRepository.save(new SequenceNumber(n))
		Bookmark bookmark = bkRepository.findById(26 as Long).get()
		bookmark.setPosition(new SequenceNumber(n))
		return bkRepository.save(bookmark)
	}

	/*
	 @PutMapping("/bookmarkTree")
	 List<Bookmark> putBookmarkTree(@RequestBody InnerNode<InnerNode<BookmarkNode>> bookmarkTree) {
	 User user = this.em.getReference(User.class, UserUtil.getCurrentUserId())
	 InnerNode<BookmarkNode>[] folders = bookmarkTree.getChildren()
	 def bookmarks = []
	 for (int i = 0; i < folders.length; i++) {
	 folders[i].children.each { child ->
	 bookmarks.add(new Bookmark(child, user, folders[i].location))
	 } 
	 }
	 return bkRepository.saveAll(bookmarks)
	 }
	 */
}
