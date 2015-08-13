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

import github.srlee309.lessWrongBookCreator.scraper.PostSection;
import java.util.ArrayList;

/**
 * Class creates html files for the information in a given list of {@link PostSection} classes
 */
public final class PostHtmlCreator extends HtmlOutputFileCreator{
    /**
     * Creates a post page html file in the htmlOuput folder for each {@link PostSection} in the provided postSections
     * @param postSections for which to get the html content to output to a file
     */
    public void createPostsPages(ArrayList<PostSection> postSections) {
        for (PostSection postSection : postSections) {
            createPostPage(postSection);
        }
    }
    private void createPostPage(PostSection postSection) {
        StringBuilder postPageSB = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        postPageSB.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">").append(newLine);
        postPageSB.append("<html>").append(newLine);
        postPageSB.append("\t<head>").append(newLine);
        postPageSB.append("\t</head>").append(newLine);
        postPageSB.append("<body>").append(newLine);
        postPageSB.append(postSection.getHtmlContent()).append(newLine);
        postPageSB.append("</body>").append(newLine).append("</html>");
        String fileTitle = postSection.getTitle().replaceAll("\\W+", "");
        String filePath = htmlOutputFilePath + "//" + fileTitle + ".html";
        createHtmlOutputFolderIfNotExists();
        writeStringToFile(filePath, postPageSB.toString());
    }
}
