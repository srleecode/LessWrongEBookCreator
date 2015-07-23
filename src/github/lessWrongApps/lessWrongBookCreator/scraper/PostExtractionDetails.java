package github.lessWrongApps.lessWrongBookCreator.scraper;

/**
 * Parameter object for configuration settings to use when scraping the information from a less wrong post
 */
public final class PostExtractionDetails {
    public PostExtractionDetails(String title, String sequence, String book, String url, String nextPost, boolean isCommentsIncluded,
                    int commentThreshold, boolean isChildPostsIncluded, boolean isParentPostsIncluded, boolean isThresholdAppliesToChildPosts) {
        this.title = title;
        this.sequence = sequence;
        this.book = book;
        this.url = url;
        this.nextPost = nextPost;
        this.isCommentsIncluded = isCommentsIncluded;
        this.commentThreshold = commentThreshold;
        this.isChildPostsIncluded = isChildPostsIncluded;
        this.isParentPostsIncluded = isParentPostsIncluded;
        this.isThresholdAppliesToChildPosts = isThresholdAppliesToChildPosts;
    }
    public PostExtractionDetails(String title, String sequence, String book, String url, String nextPost) {
        this.title = title;
        this.sequence = sequence;
        this.book = book;
        this.url = url;
        this.nextPost = nextPost;
        this.isCommentsIncluded = true;
        this.commentThreshold = 10;
        this.isChildPostsIncluded = true;
        this.isParentPostsIncluded = false;
        this.isThresholdAppliesToChildPosts = false;
    }
    private final String title;
    private final String sequence;
    private final String book;
    private String url; 
    private final String nextPost;
    private final boolean isCommentsIncluded;
    private final int commentThreshold; 
    private final boolean isChildPostsIncluded; 
    private final boolean isParentPostsIncluded; 
    private final boolean isThresholdAppliesToChildPosts;

    public String getTitle() {
        return title;
    }

    public String getSequence() {
        return sequence;
    }

    public String getBook() {
        return book;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getNextPost() {
        return nextPost;
    }

    public boolean isCommentsIncluded() {
        return isCommentsIncluded;
    }

    public int getCommentThreshold() {
        return commentThreshold;
    }

    public boolean isChildPostsIncluded() {
        return isChildPostsIncluded;
    }

    public boolean isParentPostsIncluded() {
        return isParentPostsIncluded;
    }

    public boolean isThresholdAppliesToChildPosts() {
        return isThresholdAppliesToChildPosts;
    }
}
