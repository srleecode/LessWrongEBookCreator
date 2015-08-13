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
 * Extracts post and comment information from http://wiki.lesswrong.com sites
 */
public final class WikiPostSectionExtractor extends PostSectionExtractor{
    @Override
    public String getPostSectionString(PostExtractionDetails postExtractionDetails, Document doc)  {
        StringBuilder wikiPostContentSB = new StringBuilder();
        Elements contentElements = doc.select("div.mw-content-ltr");
        contentElements.select("div.noprint").remove();
        contentElements.select("div#stub").remove();
        for (Element contentElement : contentElements) {
            convertImagesToLocal(contentElement);
        }
        wikiPostContentSB.append(contentElements.outerHtml());
        return wikiPostContentSB.toString();
    }
}
