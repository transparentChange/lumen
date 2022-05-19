package bookmarks.lumen;

import java.util.Date;

public class Bookmark<T> {
	//private Date dateAdded = new Date(1647652563438L);
	private String title;
	
	/*
	private String url;
	private T[] children;
	
	Bookmark(Date addDate) {
		//this.addDate = addDate;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date addDate) {
		this.dateAdded = addDate;
	}
	*/

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	*/
}

