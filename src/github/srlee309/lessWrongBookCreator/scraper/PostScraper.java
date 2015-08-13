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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
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
           
            try {
                //
                new URL("jar:file://dummy.jar!/").openConnection().setDefaultUseCaches(false);
            } catch (MalformedURLException ex) {
                logger.error("",ex);
            } catch (IOException ex) {
                logger.error("",ex);
            }
            
            //timeout set to 30 seconds. Also, had to increase max body size as top 50 posts are around 1.5 MB otherwise they get cut off and you miss half the comments
            Response rsp = null; 
            int statusCode = 0;
            try {
                rsp = Jsoup.connect(postExtractionDetails.getUrl()).timeout(30*1000).ignoreContentType(true).maxBodySize(100*1024*1024).ignoreHttpErrors(true).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0").referrer("http://www.google.com").followRedirects(true).execute();
                statusCode = rsp.statusCode();
                if (statusCode != 200) {
                    rsp = Jsoup.connect(postExtractionDetails.getUrl()).timeout(120*1000).ignoreContentType(true).maxBodySize(100*1024*1024).ignoreHttpErrors(true).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0").referrer("http://www.google.com").followRedirects(true).execute();
                    statusCode = rsp.statusCode();
                }
            } catch (IOException ex) {
                logger.error("Connection TimeOut for url " + postExtractionDetails.getUrl() + newLine,ex);
            }
            
            if(statusCode == 200) {
                try{    
                    Document doc = rsp.parse();

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
                            logger.error("Site not recognised " + postExtractionDetails.getUrl() + ". Is it a less wrong site?" + newLine);
                        }

                        if (postSectionExtrator != null) {
                            postContentSB.append(postSectionExtrator.getPostSectionString(postExtractionDetails, doc));
                        }
                        postContentSB.append("<a href=\"#").append(linkTitle).append("\">Go To Start of Post</a><br>").append(newLine);
                    } else {
                        logger.error("Unable to connect to site " + postExtractionDetails.getUrl() + newLine);
                    }
                } catch (IOException e) {
                    logger.error("IOException: Unable to connect to site " + postExtractionDetails.getUrl() + newLine, e);
                } catch(IllegalArgumentException e) {
                    logger.error("IllegalArgumentException: Unable to connect to site " + postExtractionDetails.getUrl() + newLine, e);
                }
            } else {
                logger.error("Unable to connect to site due to incorrect status code " + postExtractionDetails.getUrl() + newLine);
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
