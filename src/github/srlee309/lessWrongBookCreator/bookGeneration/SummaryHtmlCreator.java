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
        writeStringToFile(htmlOutputFilePath + "//summaryTOC.html", summaryPageSB.toString());
    }
}
