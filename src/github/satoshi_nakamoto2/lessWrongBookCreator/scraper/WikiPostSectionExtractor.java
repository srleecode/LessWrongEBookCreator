package github.satoshi_nakamoto2.lessWrongBookCreator.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Extracts post and comment information from http://wiki.lesswrong.com sites
 */
public final class WikiPostSectionExtractor extends PostSectionExtractor{
    @Override
    public String getPostSectionString(PostExtractionDetails postExtractionDetails, Document doc)  {
        StringBuilder wikiPostContentSB = new StringBuilder();
        Elements contentElements = doc.select("div.mw-content-ltr");
        for (Element contentElement : contentElements) {
            convertImagesToLocal(contentElement);
        }
        wikiPostContentSB.append(contentElements.outerHtml());
        return wikiPostContentSB.toString();
    }
}
