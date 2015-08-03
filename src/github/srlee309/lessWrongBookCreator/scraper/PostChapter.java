package github.srlee309.lessWrongBookCreator.scraper;
/**
 * Data class to define book and sequence each post belongs to
 */
public class PostChapter {
    private final String bookName;
    private final String sequenceName;
    private final String postName;

    public PostChapter(String bookName, String sequenceName, String postName) {
        this.bookName = bookName;
        this.sequenceName = sequenceName;
        this.postName = postName;
    }
     public String getBookName() {
        return bookName;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public String getPostName() {
        return postName;
    }
}
