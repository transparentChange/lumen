package bookmarks.lumen.domain

class InnerNode<T> {
    private Date dateAdded;
    private String title;
    private String location;
    private T[] children;

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

    String getLocation() {
        return location
    }

    void setLocation(String location) {
        this.location = location
    }

    T[] getChildren() {
        return children
    }

    void setChildren(T[] children) {
        this.children = children
    }
}
