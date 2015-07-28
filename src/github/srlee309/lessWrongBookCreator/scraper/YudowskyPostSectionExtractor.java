package github.srlee309.lessWrongBookCreator.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Extracts post and comment information from http://www.yudkowsky.net sites
 */
public final class YudowskyPostSectionExtractor extends PostSectionExtractor{
    @Override
    public String getPostSectionString(PostExtractionDetails postExtractionDetails, Document doc)  {
        Elements elements = doc.select("div#content");
        doc.select("div.about").remove();
        doc.select("div#breadcrumbs").remove();
        StringBuilder yudowskyPostContentSB = new StringBuilder();
        Element contentElement = null;
        if (elements.size() > 0) {
            contentElement = elements.get(0);
            contentElement.select("div#text-size").remove();
            contentElement.select("ul.share").remove();
            contentElement.select("a.top").remove();
        }
        if (contentElement != null) {
            convertImagesToLocal(contentElement);
            yudowskyPostContentSB.append(contentElement.outerHtml());
            return yudowskyPostContentSB.toString();
        } else {
            return "";
        }
    }
}
