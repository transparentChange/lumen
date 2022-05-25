package bookmarks.lumen.domain;

import java.util.Date;

public class BookmarkNode {
	private Date dateAdded;
	private String title;
	private String url;

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date addDate) {
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

