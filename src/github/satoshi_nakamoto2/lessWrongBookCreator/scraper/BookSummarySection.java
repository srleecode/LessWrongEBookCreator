package github.satoshi_nakamoto2.lessWrongBookCreator.scraper;
import java.util.ArrayList;
/**
 * Class contains title and summary text for a book along with a list of {@link SequenceSummarySection}
 */
public final class BookSummarySection{
    private final String title;
    private String summary;
    private ArrayList<SequenceSummarySection> sequenceSummarySections;

    public BookSummarySection(String title) {
        this(title, new ArrayList<SequenceSummarySection>());
    }
    public BookSummarySection(String title, ArrayList<SequenceSummarySection> sequenceSummarySections) {
        this(title, "", sequenceSummarySections);
    }
    public BookSummarySection(String title, String summary, ArrayList<SequenceSummarySection> sequenceSummarySections) {
        this.title = title;
        this.summary = summary;
        this.sequenceSummarySections = sequenceSummarySections;
    }
    public String getTitle() {
        return title;
    }
    /**
     * @param title - title of the sequence to retrieve 
     * @return SequenceSummarySection with given title or null if none is found
     */
    public SequenceSummarySection getSequenceSummarySection(String title) {
        for (int i = 0; i < sequenceSummarySections.size(); ++i) {
            if (sequenceSummarySections.get(i).getTitle().equals(title)) {
                return sequenceSummarySections.get(i);
            }
        }
        return null;
    }
    public ArrayList<SequenceSummarySection> getSequenceSummarySections() {
        return sequenceSummarySections;
    }
    public void addSequenceSummarySection(SequenceSummarySection sequenceSummarySection) {
        sequenceSummarySections.add(sequenceSummarySection);
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    /**
     * @return html - book summary along with output of {@link SequenceSummarySection#getAsHtml()} for each sequence 
     * and {@link PostSummarySection#getAsHtml()} for each post in the sequence
     */
    public String getAsHtml() {
        StringBuilder sb = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        sb.append("<a id=\"").append(title.replaceAll("\\W+", "")).append("_book_summary\"></a>").append(newLine);
        sb.append("<h1>").append(title).append("</h1>").append(newLine);
        sb.append("<p>").append(summary).append("</p>").append(newLine);
        for (SequenceSummarySection sequenceSummary : sequenceSummarySections) {
                sb.append(sequenceSummary.getAsHtml());
        }
        return sb.toString();
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 41 * hash + (this.summary != null ? this.summary.hashCode() : 0);
        hash = 41 * hash + (this.sequenceSummarySections != null ? this.sequenceSummarySections.hashCode() : 0);
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
        final BookSummarySection other = (BookSummarySection) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if ((this.summary == null) ? (other.summary != null) : !this.summary.equals(other.summary)) {
            return false;
        }
        return !(this.sequenceSummarySections != other.sequenceSummarySections && (this.sequenceSummarySections == null || !this.sequenceSummarySections.equals(other.sequenceSummarySections)));
    }
}
