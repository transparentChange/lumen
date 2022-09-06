package bookmarks.lumen.controller

import bookmarks.lumen.data.BookmarkRepository
import bookmarks.lumen.domain.Bookmark
import bookmarks.lumen.domain.BookmarkNode
import bookmarks.lumen.domain.BookmarkStatus
import bookmarks.lumen.domain.InnerNode
import bookmarks.lumen.domain.User
import bookmarks.lumen.util.UserUtil
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

import javax.persistence.EntityManager
import java.time.LocalDateTime

@CrossOrigin
@RestController
public class BookmarkController {
	@Autowired private BookmarkRepository bkRepository;

	@Autowired
	EntityManager em

	@GetMapping("/bookmarks")
	List<Bookmark> getBookmarks() {
		return bkRepository.findAll()
	}

	@PutMapping("/bookmarks")
	List<Bookmark> putBookmarks(@RequestBody List<Bookmark> bookmarks) {
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
				newBookmarks << b
			}
		}
		return bkRepository.saveAll(bookmarks)
	}
	
	@PutMapping("/bookmarkTree")
	List<Bookmark> putBookmarkTree(@RequestBody ArrayList<BookmarkNode> bookmarkTree) {
		User user = this.em.getReference(User.class, UserUtil.getCurrentUserId())
		def bookmarks = []
		for (BookmarkNode node : bookmarkTree) {
			bookmarks.add(bkRepository.save(new Bookmark(node, user, "toolbar_____")))
		}

		return bookmarks

		//return bkRepository.saveAll(bookmarks)
	}

	@PutMapping("/setStatus/{id}")
	Bookmark setStatus(@PathVariable("id") Long id, @RequestBody BookmarkStatus status) {
		Optional<Bookmark> b = bkRepository.findById(id)
		Bookmark bookmark = bkRepository.findById(id).get()
		bookmark.setStatus(status)
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
