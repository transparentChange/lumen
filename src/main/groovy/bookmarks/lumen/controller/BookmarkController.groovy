package bookmarks.lumen.controller

import bookmarks.lumen.data.BookmarkRepository
import bookmarks.lumen.domain.Bookmark
import bookmarks.lumen.domain.BookmarkNode
import bookmarks.lumen.domain.InnerNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController
import org.springframework.jdbc.core.JdbcTemplate

import java.time.LocalDateTime

@CrossOrigin
@RestController
public class BookmarkController {

	@Autowired private JdbcTemplate jdbcTemplate;

	@Autowired private BookmarkRepository bkRepository;

	@GetMapping("/bookmarks")
	List<Bookmark> getBookmarks() {
		return bkRepository.findAll()
	}

	@PutMapping("/bookmarks")
	List<Bookmark> putBookmarks(@RequestBody List<Bookmark> bookmarks) {
		def newBookmarks = []
		bookmarks.each { Bookmark b ->
			Bookmark bookmarkFound = bkRepository.findById(b.getId()).get()
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
		}
		return bkRepository.saveAll(newBookmarks)
	}
	
	@PutMapping("/bookmarkTree")
	List<Bookmark> putBookmarkTree(@RequestBody InnerNode<InnerNode<BookmarkNode>> bookmarkTree) {
		InnerNode<BookmarkNode>[] folders = bookmarkTree.getChildren()

		def bookmarks = []
		for (int i = 0; i < folders.length; i++) {
			folders[i].children.each { child ->
				bookmarks.add(new Bookmark(child, folders[i].location))
			}
		}
		return bkRepository.saveAll(bookmarks)
	}
	
}
