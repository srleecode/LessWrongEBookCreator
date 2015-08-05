package github.srlee309.lessWrongBookCreator.scraper;
/**
 * Simple data class for holding information for a post summary
 */
public final class PostSummarySection {
    private String title;
    private String url;
    private String summary;
    public PostSummarySection() {
        this("", "", "");
    }
    public PostSummarySection(String title, String url, String summary) {
        this.title = title;
        this.url = url;
        this.summary = summary;
    }
    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }
    public String getSummary() {
        return summary;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getAsHtml() {
        StringBuilder sb = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String linkTitle = title.replaceAll("\\W+", "");
        sb.append("<a id=\"").append(linkTitle).append("_post_summary\"></a>").append(newLine);
        sb.append("<h4>").append(title).append("</h4>").append(newLine);
        sb.append("<p>").append(summary).append("</p>").append(newLine);
        sb.append("<a href=\"").append(linkTitle).append(".html#").append(linkTitle).append("\">Read post</a> ");
        SiteType siteType = new SiteTypeExtractor().getSiteTypeFromUrlString(url);
        if (siteType == SiteType.LESS_WRONG || siteType == SiteType.OVERCOMING_BIAS) {
            sb.append("<a href=\"").append(linkTitle).append(".html#").append(linkTitle).append("_comments\">Read comments</a>").append(newLine);
        }
        sb.append("<hr>").append(newLine);
        return sb.toString();
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 41 * hash + (this.url != null ? this.url.hashCode() : 0);
        hash = 41 * hash + (this.summary != null ? this.summary.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PostSummarySection other = (PostSummarySection) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        return !((this.summary == null) ? (other.summary != null) : !this.summary.equals(other.summary));
    }
}
