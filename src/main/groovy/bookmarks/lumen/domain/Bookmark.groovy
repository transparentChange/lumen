package bookmarks.lumen.domain

import org.hibernate.annotations.SQLInsert

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="bookmark")
@SQLInsert(sql = "INSERT OR IGNORE INTO bookmark (date_added, location, title, url, bookmark_id) VALUES (?, ?, ?, ?, ?)")
class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookmark_id;

    @Column(name = "date_added", nullable = false)
    private Date dateAdded;

    @Column(nullable = false)
    private String title;

    @Column(name = "url", unique = true, nullable = false)
    private String url;

    private String location;

    Bookmark() {
    }

    Bookmark(BookmarkNode node, String location) {
        this.dateAdded = node.dateAdded;
        this.title = node.title;
        this.url = node.url;
        this.location = location;
    }

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

    String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }
}
