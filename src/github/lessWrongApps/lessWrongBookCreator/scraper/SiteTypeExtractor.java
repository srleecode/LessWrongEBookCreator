package github.lessWrongApps.lessWrongBookCreator.scraper;

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
