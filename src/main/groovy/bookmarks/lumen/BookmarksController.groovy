package bookmarks.lumen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class BookmarksController {
	@GetMapping("/bookmarks")
	List<Bookmark> getBookmarks() {
		// read json file
		def library = [new Bookmark()];
		return library;
	}
	
	@PostMapping("/bookmarks")
	Bookmark postBookmarks(@RequestBody Bookmark bookmark) {
		return bookmark;
	}
	
}
