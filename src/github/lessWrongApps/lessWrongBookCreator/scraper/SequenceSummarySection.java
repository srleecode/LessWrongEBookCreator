package github.lessWrongApps.lessWrongBookCreator.scraper;
import java.util.ArrayList;

/**
 * Class contains title and summary text for a book along with a list of {@link PostSummarySection}
 */
public final class SequenceSummarySection{
    private final String title;
    private String summary;
    private ArrayList<PostSummarySection> postSummarySections;

    public SequenceSummarySection(String title) {
        this(title, "", new ArrayList<PostSummarySection>());
    }
    public SequenceSummarySection(String title, ArrayList<PostSummarySection> postSummarySections) {
        this(title, "", postSummarySections);
    }
    public SequenceSummarySection(String title, String summary, ArrayList<PostSummarySection> postSummarySections) {
        this.title = title;
        this.summary = summary;
        this.postSummarySections = postSummarySections;
        
    }
    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }
    public ArrayList<PostSummarySection> getPostSummarySections() {
        return postSummarySections;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public void addPostSummarySection(PostSummarySection postSummarySection) {
        postSummarySections.add(postSummarySection);
    }
    /**
     * @return html - Sequence summary along with the output of {@link PostSummarySection#getAsHtml()} for each post in this sequence
     */
    public String getAsHtml() {
        StringBuilder sb = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        sb.append("<hr>").append(newLine);
        sb.append("<a id=\"").append(title.replaceAll("\\W+", "")).append("_sequence_summary\"></a>").append(newLine);
        sb.append("<h3>").append(title).append("</h3>").append(newLine);
        sb.append("<p>").append(summary).append("</p>").append(newLine);
        sb.append("<hr>").append(newLine);
        for (PostSummarySection postSummary : postSummarySections) {
            sb.append(postSummary.getAsHtml());
        }
        return sb.toString();
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 89 * hash + (this.summary != null ? this.summary.hashCode() : 0);
        hash = 89 * hash + (this.postSummarySections != null ? this.postSummarySections.hashCode() : 0);
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
        final SequenceSummarySection other = (SequenceSummarySection) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.summary == null) ? (other.summary != null) : !this.summary.equals(other.summary)) {
            return false;
        }
        return !(this.postSummarySections != other.postSummarySections && (this.postSummarySections == null || !this.postSummarySections.equals(other.postSummarySections)));
    }
}
