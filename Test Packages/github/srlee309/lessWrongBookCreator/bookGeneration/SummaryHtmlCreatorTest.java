/* 
 * Copyright (C) 2015 Scott Lee
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package github.srlee309.lessWrongBookCreator.bookGeneration;

import github.srlee309.lessWrongBookCreator.scraper.BookSummarySection;
import github.srlee309.lessWrongBookCreator.scraper.PostSummarySection;
import github.srlee309.lessWrongBookCreator.scraper.SequenceSummarySection;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class SummaryHtmlCreatorTest {
    @Test
    public void createSummaryPage_BookSummarySection_OutputHtmlAsExpected() throws Exception{
        String expectedHtmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/summaryTOC.html").toURI()));
        SummaryHtmlCreator summaryHtmlCreator = new SummaryHtmlCreator();
        ArrayList<PostSummarySection> expectedPostSections = new ArrayList<PostSummarySection>();
        expectedPostSections.add(new PostSummarySection("What Do We Mean By \"Rationality\"?", "http://lesswrong.com/lw/31/what_do_we_mean_by_rationality/", "When we talk about rationality, we're generally talking about either epistemic rationality (systematic methods of finding out the truth) or instrumental rationality (systematic methods of making the world more like we would like it to be). We can discuss these in the forms of probability theory and decision theory, but this doesn't fully cover the difficulty of being rational as a human. There is a lot more to rationality than just the formal theories."));
        expectedPostSections.add(new PostSummarySection("Feeling Rational", "http://lesswrong.com/lw/hp/feeling_rational/", "Strong emotions can be rational.  A rational belief that something good happened leads to rational happiness.  But your emotions ought not to change your beliefs about events that do not depend causally on your emotions."));
        ArrayList<SequenceSummarySection> expectedSequenceSections = new ArrayList<SequenceSummarySection>();
        expectedSequenceSections.add(new SequenceSummarySection("Predictably Wrong", "Sequence on cognitive bias", expectedPostSections));
        ArrayList<BookSummarySection> bookSections = new ArrayList<BookSummarySection>();
        String bookSectionSummary = "<ul>\n" +
                                    "   <li>'What is rationality?</li>\n" +
                                    "   <li>'What does it really mean to be rational?'</li>\n" +
                                    "   <li>'Why is is valuable to care about the truth?'</li>\n" +
                                    "</ul>";
        bookSections.add(new BookSummarySection("Map and Territory", bookSectionSummary, expectedSequenceSections));
        summaryHtmlCreator.createSummaryPage(bookSections);
        String resultHtmlString = FileUtils.readFileToString(new File("htmlOutput/summaryTOC.html"));
        assertTrue(resultHtmlString.equals(expectedHtmlString));
    }
    
}
