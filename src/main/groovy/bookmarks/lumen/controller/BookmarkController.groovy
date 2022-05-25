package bookmarks.lumen.controller

import bookmarks.lumen.data.BookmarkRepository
import bookmarks.lumen.domain.Bookmark
import bookmarks.lumen.domain.BookmarkNode
import bookmarks.lumen.domain.InnerNode
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController

import java.sql.Connection
import java.sql.DriverManager;
import org.springframework.jdbc.core.JdbcTemplate

import java.util.stream.Collectors;

@RestController
public class BookmarkController {

	@Autowired private JdbcTemplate jdbcTemplate;

	@Autowired private BookmarkRepository bkRepository;

	@GetMapping("/bookmarks")
	List<BookmarkNode> getBookmarks() {
		// read json file
		def library = [new BookmarkNode()];
		return library;
	}
	
	@PostMapping("/bookmarks")
	Iterable<Bookmark> postBookmarks(@RequestBody InnerNode<InnerNode<BookmarkNode>> bookmarkTree) {
		InnerNode<BookmarkNode>[] folders = bookmarkTree.getChildren();

		def bookmarks = [];
		for (int i = 0; i < folders.length; i++) {
			folders[i].children.stream()
					.forEach(child -> bookmarks.add(new Bookmark(child, folders[i].location)))
		}
		return bkRepository.saveAll(bookmarks);
	}
	
}
