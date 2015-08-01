package github.srlee309.lessWrongBookCreator.scraper;
import java.io.IOException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses JSoup to pull post content and comments out of a less wrong or other sites
 */
public final class PostScraper {
    private static final Logger logger = LoggerFactory.getLogger(PostScraper.class);
    private final String newLine = System.getProperty("line.separator");
        
    public PostSection getPostSection(PostExtractionDetails postExtractionDetails)  {
        if(!postExtractionDetails.getUrl().isEmpty()){
            SiteType siteType = new SiteTypeExtractor().getSiteTypeFromUrlString(postExtractionDetails.getUrl());
            if (siteType == SiteType.LESS_WRONG) {
                String url = postExtractionDetails.getUrl();
                if (!url.endsWith("/?all=true")) {
                    if (!url.endsWith("/")) {
                        url += "/";
                    }
                    url  += "?all=true";
                    postExtractionDetails.setUrl(url);
                }
            }
            StringBuilder postContentSB = new StringBuilder();
            String linkTitle = postExtractionDetails.getTitle().replaceAll("\\W+", "");
            String nextPostLinkTitle = postExtractionDetails.getNextPost().replaceAll("\\W+", "");
            try{
                //timeout set to 30 seconds. Also, had to increase max body size as top 50 posts are around 1.5 MB otherwise they get cut off and you miss half the comments
                Response rsp = Jsoup.connect(postExtractionDetails.getUrl()).timeout(30*1000).ignoreContentType(true).maxBodySize(100*1024*1024).ignoreHttpErrors(true).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0").referrer("http://www.google.com").followRedirects(true).execute(); 
                Document doc = rsp.parse();
                if (doc == null) {
                    rsp = Jsoup.connect(postExtractionDetails.getUrl()).timeout(30*1000).ignoreContentType(true).maxBodySize(100*1024*1024).ignoreHttpErrors(true).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0").referrer("http://www.google.com").followRedirects(true).execute(); 
                    doc = rsp.parse();
                }
                if (doc != null) {
                    removeComments(doc);
                    postContentSB.append("<a id=\"").append(linkTitle).append("\"/>").append(newLine);
                    postContentSB.append("<h2><a href=\"").append(postExtractionDetails.getUrl()).append("\">").append(postExtractionDetails.getTitle()).append("</a></h2>");
                    postContentSB.append("<a href=\"summaryTOC.html#").append(linkTitle).append("_post_summary\">Summary</a>" );
                    if (!postExtractionDetails.getNextPost().isEmpty()) {
                        postContentSB.append(" - <a href=\"").append(nextPostLinkTitle).append(".html#").append(nextPostLinkTitle).append("\">Next Post</a>");
                    }
                    
                    PostSectionExtractor postSectionExtrator = null;
                    
                    if (siteType == SiteType.LESS_WRONG || siteType == SiteType.OVERCOMING_BIAS) {
                        if (postExtractionDetails.isCommentsIncluded()) {
                            postContentSB.append(" - <a href=\"#").append(linkTitle).append("_comments\">Comments</a><br><br>").append(newLine);
                        } else {
                            postContentSB.append("<br><br>").append(newLine);
                        }
                    }
                    if (siteType == SiteType.LESS_WRONG) {
                        postSectionExtrator = new LessWrongPostSectionExtractor();
                    } else if (siteType == SiteType.YUDKOWSY) {
                        postSectionExtrator = new YudowskyPostSectionExtractor();
                    } else if (siteType == SiteType.LESS_WRONG_WIKI) {
                        postSectionExtrator = new WikiPostSectionExtractor();
                    } else if (siteType == SiteType.OVERCOMING_BIAS) {
                        postSectionExtrator = new OvercomingBiasPostSectionExtractor();
                    } else {
                        logger.error("Site not recognised " + postExtractionDetails.getUrl() + ". Is it a less wrong site?");
                    }
                    
                    if (postSectionExtrator != null) {
                        postContentSB.append(postSectionExtrator.getPostSectionString(postExtractionDetails, doc));
                    }
                    postContentSB.append("<a href=\"#").append(linkTitle).append("\">Go To Start of Post</a><br>").append(newLine);
                } else {
                    logger.error("Unable to connect to site " + postExtractionDetails.getUrl());
                }
            } catch (IOException e) {
                logger.error("Unable to connect to site " + postExtractionDetails.getUrl(), e);
            } catch(IllegalArgumentException e) {
                logger.error("Unable to connect to site " + postExtractionDetails.getUrl(), e);
            }
            return new PostSection(postExtractionDetails.getTitle(), postExtractionDetails.getUrl(), postContentSB.toString());
        } else {
            return null;
        }
    }
     private void removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }
}
