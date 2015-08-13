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
