package bookmarks.lumen.domain;

public class BookmarkNode {
	private String dateAdded;
	private String title;
	private String url;

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String addDate) {
		this.dateAdded = addDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}

