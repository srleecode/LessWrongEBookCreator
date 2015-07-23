package github.lessWrongApps.lessWrongBookCreator.bookGeneration;

import github.lessWrongApps.lessWrongBookCreator.scraper.BookSummarySection;
import java.util.ArrayList;

/**
 * Class creates html file for the information in a given list of {@link BookSummarySection} classes
 */
public final class SummaryHtmlCreator extends HtmlOutputFileCreator{
    /**
     * Creates a summary page html file in the htmlOuput folder containing html bookSummarySections
     * @param bookSummarySections for which to retrieve the html content
     */
    public void createSummaryPage(ArrayList<BookSummarySection> bookSummarySections) {
        StringBuilder summaryPageSB = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        summaryPageSB.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">").append(newLine);
        summaryPageSB.append("<html>").append(newLine);
        summaryPageSB.append("\t<head>").append(newLine);
        summaryPageSB.append("\t</head>").append(newLine);
        summaryPageSB.append("<body>").append(newLine);
        for (BookSummarySection bookSummarySection : bookSummarySections) {
            summaryPageSB.append(bookSummarySection.getAsHtml());
        }
        summaryPageSB.append("</body>").append(newLine).append("</html>");
        createHtmlOutputFolderIfNotExists();
        writeStringToFile(htmlOutputFilePath + "//summary.html", summaryPageSB.toString());
    }
}
