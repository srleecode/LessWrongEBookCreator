package github.lessWrongApps.lessWrongBookCreator.scraper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            overcomingBiasPostContentSB.append("<a id=\"").append(linkTitle).append("_comments\">").append(newLine);
            Elements commentElements = doc.select("li.comment");
            if (postExtractionDetails.isCommentsIncluded() && commentElements.size() > 0) {
                overcomingBiasPostContentSB.append("<a href=\"").append(nextPostLinkTitle).append(".html#").append(nextPostLinkTitle).append("\">Skip Comments</a><br>").append(newLine);
                overcomingBiasPostContentSB.append(getCommentsString(commentElements));
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
