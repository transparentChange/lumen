package bookmarks.lumen.domain

import org.hibernate.annotations.SQLInsert

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

enum BookmarkStatus {
    VISITED,
    COMPLETED
}

@Entity
@Table(name="bookmark")
@SQLInsert(sql = """INSERT OR IGNORE INTO bookmark
    (date_added, expiry_date, location, period_hours, status, title, url, user_id)
 VALUES (?, ?, ?, ?, ?, ?, ?, ?)""")
class Bookmark {
    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_added", nullable = false)
    private String dateAdded;

    private String location;

    @Column(nullable = false)
    private String title;

    @Column(name = "url", unique = true, nullable = false)
    private String url;

    @Column(name = "period_hours")
    private Long periodHours;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Enumerated(EnumType.STRING)
    private BookmarkStatus status;

    Bookmark() {
    }

    Bookmark(BookmarkNode node, User user, String location) {
        this.dateAdded = node.dateAdded;
        this.title = node.title;
        this.url = node.url;
        this.user = user;
        this.location = location;
    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    User getUser() {
        return user
    }

    void setUser(User user) {
        this.user = user
    }

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

    String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }

    Long getPeriodHours() {
        return periodHours
    }

    void setPeriodHours(Long periodHours) {
        this.periodHours = periodHours
    }

    String getExpiryDate() {
        return expiryDate
    }

    void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate
    }

    BookmarkStatus getStatus() {
        return status
    }

    void setStatus(BookmarkStatus status) {
        this.status = status
    }
}
