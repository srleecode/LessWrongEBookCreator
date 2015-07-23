package github.satoshi_nakamoto2.lessWrongBookCreator.scraper;
/**
 * Simple data class for holding information for a post
 */
public final class PostSection {
    private final String title;
    private final String url;
    private final String htmlContent;

    public PostSection(String title, String url, String htmlContent) {
        this.title = title;
        this.url = url;
        this.htmlContent = htmlContent;
    }
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PostSection other = (PostSection) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }
}
