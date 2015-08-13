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

/**
 * Class used to determine the {@link SiteType} for a url
 */
public final class SiteTypeExtractor {
    /**
     * @param url for which to determine the site type
     * @return {@link SiteType} for the given url
     */
    public SiteType getSiteTypeFromUrlString(String url) {
        if (url.startsWith("http://lesswrong")) {
            return SiteType.LESS_WRONG;
        } else if (url.contains("yudkowsky")) {
            return SiteType.YUDKOWSY;
        } else if (url.startsWith("http://wiki")) {
            return SiteType.LESS_WRONG_WIKI;
        } else if (url.contains("overcomingbias.com")){
            return SiteType.OVERCOMING_BIAS;
        } else {
            return SiteType.UNKNOWN;
        }
    }
}
