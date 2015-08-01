package github.srlee309.lessWrongBookCreator.scraper;

import github.srlee309.lessWrongBookCreator.excelReader.SummaryFileReader;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class SummaryFileReaderTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getSummarySections_Null_ThrowsExcelExtractionException() throws Exception {
        SummaryFileReader instance = new SummaryFileReader();
        exception.expect(ExcelExtractionException.class);
        instance.getSummarySections(null);
    }

    @Test
    public void getSummarySections_FileNotExisting_ThrowsExcelExtractionException() throws Exception {
        SummaryFileReader instance = new SummaryFileReader();
        exception.expect(ExcelExtractionException.class);
        instance.getSummarySections(new File("Non Existing"));
    }

    @Test
    public void getSummarySections_ExcelFileWithFullDetails_AllInformationExtracted() throws Exception {
        File inputFile =  new File(this.getClass().getResource("/Rationality AI to Zombies.xlsx").toURI());
        SummaryFileReader instance = new SummaryFileReader();
        
        ArrayList<PostSummarySection> expectedPostSections = new ArrayList<PostSummarySection>();
        expectedPostSections.add(new PostSummarySection("What Do We Mean By \"Rationality\"?", "http://lesswrong.com/lw/31/what_do_we_mean_by_rationality/", "When we talk about rationality, we're generally talking about either epistemic rationality (systematic methods of finding out the truth) or instrumental rationality (systematic methods of making the world more like we would like it to be). We can discuss these in the forms of probability theory and decision theory, but this doesn't fully cover the difficulty of being rational as a human. There is a lot more to rationality than just the formal theories."));
        expectedPostSections.add(new PostSummarySection("Feeling Rational", "http://lesswrong.com/lw/hp/feeling_rational/", "Strong emotions can be rational.  A rational belief that something good happened leads to rational happiness.  But your emotions ought not to change your beliefs about events that do not depend causally on your emotions."));
        ArrayList<SequenceSummarySection> expectedSequenceSections = new ArrayList<SequenceSummarySection>();
        expectedSequenceSections.add(new SequenceSummarySection("Predictably Wrong", "Sequence on cognitive bias", expectedPostSections));
        ArrayList<BookSummarySection> expectedBookSections = new ArrayList<BookSummarySection>();
        String bookSectionSummary = "<ul>\n" +
                                    "   <li>'What is rationality?</li>\n" +
                                    "   <li>'What does it really mean to be rational?'</li>\n" +
                                    "   <li>'Why is is valuable to care about the truth?'</li>\n" +
                                    "</ul>";
        expectedBookSections.add(new BookSummarySection("Map and Territory", bookSectionSummary, expectedSequenceSections));
        ArrayList<BookSummarySection> resultBookSections = instance.getSummarySections(inputFile);
        assertTrue(expectedBookSections.equals(resultBookSections));
    }

    @Test
    public void getSummarySections_EmptyExcelFile_EmptyList() throws Exception {
        File inputFile =  new File(this.getClass().getResource("/empty.xlsx").toURI());
        SummaryFileReader instance = new SummaryFileReader();
        ArrayList<BookSummarySection> resultBookSections = instance.getSummarySections(inputFile);
        assertTrue(resultBookSections.isEmpty());
    }

    @Test
    public void getSummarySections_EmptyExcelFileWithOnlyOneSheet_EmptyList() throws Exception {
        File inputFile =  new File(this.getClass().getResource("/empty one sheet.xlsx").toURI());
        SummaryFileReader instance = new SummaryFileReader();
        ArrayList<BookSummarySection> resultBookSections = instance.getSummarySections(inputFile);
        assertTrue(resultBookSections.isEmpty());
    }
}
