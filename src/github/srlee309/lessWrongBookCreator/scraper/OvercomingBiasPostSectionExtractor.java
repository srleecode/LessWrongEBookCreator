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
package github.srlee309.lessWrongBookCreator.scraper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Extracts String of scraped Overcoming bias page
 */
public class OvercomingBiasPostSectionExtractor extends PostSectionExtractor{
    @Override
    public String getPostSectionString(PostExtractionDetails postExtractionDetails, Document doc) {
        StringBuilder overcomingBiasPostContentSB = new StringBuilder(); 
        doc.select("div.gdsrcacheloader").remove();
        Elements contentElements = doc.select("div.entry-content");
        
        if (contentElements.size() > 0) {
            overcomingBiasPostContentSB.append(contentElements.get(0).outerHtml()).append(newLine);

            overcomingBiasPostContentSB.append("<hr>").append(newLine);
            overcomingBiasPostContentSB.append("<hr>").append(newLine);
            String linkTitle = postExtractionDetails.getTitle().replaceAll("\\W+", "");
            String nextPostLinkTitle = postExtractionDetails.getNextPost().replaceAll("\\W+", "");
            
            Elements commentElements = doc.select("li.comment");
            if (postExtractionDetails.isCommentsIncluded())  {
                overcomingBiasPostContentSB.append("<a id=\"").append(linkTitle).append("_comments\">").append(newLine);
                if (commentElements.size() > 0) {
                    if (!nextPostLinkTitle.isEmpty()) {
                        overcomingBiasPostContentSB.append("<a href=\"").append(nextPostLinkTitle).append(".html#").append(nextPostLinkTitle).append("\">Next Post</a><br>").append(newLine);
                    }
                    overcomingBiasPostContentSB.append(getCommentsString(commentElements));
                }
            }
        }
        return overcomingBiasPostContentSB.toString().trim();
    }
    private String getCommentsString(Elements commentElements) {
        StringBuilder commentsSB = new StringBuilder();
        Pattern digitPattern = Pattern.compile("\\d+");
        Matcher matcher = null;
        String parentId = "";
        Integer depth = 0;
        String id = "";
        for (Element commentElement : commentElements) {
            String [] lines = commentElement.outerHtml().split("\n");
            if (lines.length > 1) {
                matcher = digitPattern.matcher(lines[0]);
                if (matcher.find()) {
                    depth = Integer.parseInt(matcher.group(0));
                }
                matcher = digitPattern.matcher(lines[1]);
                if (matcher.find()) {
                    id = matcher.group(0);
                }
                //unsure how to get votes from Overcoming Bias looks like it is java script generated. Therefore setting to 99999 so they all get picked up.
                if (depth > 1) {
                    commentsSB.append("<hr>").append(newLine);
                    commentsSB.append("<span>Parent Comment: </span><a href=\"#").append(parentId)
                        .append("\">").append(parentId).append("</a>").append(newLine);
                } else {
                    parentId = id;
                }
                commentsSB.append("<hr>").append(newLine);
                commentsSB.append("<a name=\"").append(id).append("\"/><span>ID:").append(id).append("</span>").append(newLine);
                commentsSB.append(getAuthorTag(commentElement)).append(newLine);
                Elements contentElements = commentElement.select("div.dsq-comment-message");
                for (Element contentElement : contentElements) {
                    commentsSB.append(contentElement.outerHtml()).append(newLine);
                }
                
            }
        }
        return commentsSB.toString();
    }

    private String getAuthorTag(Element commentElement) {
       String authorName = "";
       for (Element element : commentElement.select("span")) {
           if (authorName.isEmpty()) {
               authorName = element.text();
           }
       }
       return "<p>" + authorName + "</p>";
    }
    
}
