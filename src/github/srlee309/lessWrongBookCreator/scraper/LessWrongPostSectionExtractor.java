package github.srlee309.lessWrongBookCreator.scraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Extracts post and comment information from http://lesswrong.com sites
 */
public final class LessWrongPostSectionExtractor extends PostSectionExtractor{
    @Override
    public String getPostSectionString(PostExtractionDetails postExtractionDetails, Document doc)  {
        if (postExtractionDetails == null || doc == null) {
            return "";
        }
        StringBuilder lessWrongPostContentSB = new StringBuilder(); 
        Elements authorSpans = doc.select("span.author");
        if (authorSpans.size() > 0) {
            lessWrongPostContentSB.append(authorSpans.get(0).outerHtml());
        }
        Elements postDateSpans = doc.select("span.date");
        if (postDateSpans.size() > 0) {
            lessWrongPostContentSB.append(postDateSpans.get(0).outerHtml());
        }
        Elements mdContent = doc.select("div.md");
        Element postContent = null;
        if (mdContent.size() > 0) {
            if (mdContent.get(0).text().startsWith("Less Wrong is a community blog")) {
                if (mdContent.size() > 1) {
                    postContent = mdContent.get(1);
                }
            } else {
                postContent = mdContent.get(0);
            }
        }
        if (postContent != null) {
            convertImagesToLocal(postContent);
            postContent.select("p[style*=text-align:right]").remove();
            lessWrongPostContentSB.append(postContent.outerHtml()).append(newLine);
        }
        lessWrongPostContentSB.append("<hr>").append(newLine);
        lessWrongPostContentSB.append("<hr>").append(newLine);
        String linkTitle = postExtractionDetails.getTitle().replaceAll("\\W+", "");
        String nextPostLinkTitle = postExtractionDetails.getNextPost().replaceAll("\\W+", "");
        lessWrongPostContentSB.append("<a id=\"").append(linkTitle).append("_comments\">").append(newLine);
        if (postExtractionDetails.isCommentsIncluded()) {
            ArrayList<LessWrongCommentSection> comments = getAllComments(doc);
            int numComments = 0;
            for (LessWrongCommentSection comment : comments) {
                if (comment.getVotes() >= postExtractionDetails.getCommentThreshold()) {
                    numComments++;
                }
            }
            if (numComments > 0) {
                if (!nextPostLinkTitle.isEmpty()) {
                    lessWrongPostContentSB.append("<a href=\"").append(nextPostLinkTitle).append(".html#").append(nextPostLinkTitle).append("\">Next Post</a><br>").append(newLine);
                }
                lessWrongPostContentSB.append(getStringOfIncludedComments(postExtractionDetails, comments));
            }
        }
        return lessWrongPostContentSB.toString().trim();
    }
    private String getStringOfIncludedComments(PostExtractionDetails postExtractionDetails, ArrayList<LessWrongCommentSection> comments) {
        StringBuilder commentsString = new StringBuilder();
        HashMap<String, String> commentIdAuthorSet = new HashMap<String, String>();
        HashSet<String> includedCommentIds = new HashSet<String>();
        HashSet<String> explicitlyIncludedCommentIds = new HashSet<String>();
        for (int i = 0; i < comments.size(); ++i) {
            if (postExtractionDetails.isChildPostsIncluded() && explicitlyIncludedCommentIds.contains(comments.get(i).getParentCommentId()) || 
                (comments.get(i).getVotes() >= postExtractionDetails.getCommentThreshold() && 
                (comments.get(i).getParentCommentId().isEmpty() || postExtractionDetails.isThresholdAppliesToChildPosts()))) {

                if (postExtractionDetails.isParentPostsIncluded() && !comments.get(i).getParentCommentId().isEmpty()) {
                    LessWrongCommentSection currLessWrongCommentSection = comments.get(i);
                    String prevParentCommentId = ""; 
                    while(!currLessWrongCommentSection.getParentCommentId().isEmpty()) {
                        prevParentCommentId = currLessWrongCommentSection.getParentCommentId();
                        for(LessWrongCommentSection commentSection : comments) {
                            if (commentSection.getId().equals(currLessWrongCommentSection.getParentCommentId())) {
                                currLessWrongCommentSection = commentSection;
                                includedCommentIds.add(commentSection.getId());
                                commentIdAuthorSet.put(commentSection.getId(), commentSection.getAuthorHtml());
                                break;
                            }
                        }
                        if (prevParentCommentId.equals(currLessWrongCommentSection.getParentCommentId())) {
                            break;
                        }
                    }
                }
                includedCommentIds.add(comments.get(i).getId());
                explicitlyIncludedCommentIds.add(comments.get(i).getId());
                commentIdAuthorSet.put(comments.get(i).getId(), comments.get(i).getAuthorHtml());
            }
        }
        for (LessWrongCommentSection commentSection : comments) {
            if (includedCommentIds.contains(commentSection.getId())) {
                if (!commentSection.getParentCommentId().isEmpty()) {
                    commentsString.append("<hr>").append(newLine);
                    if (commentIdAuthorSet.containsKey(commentSection.getParentCommentId())) {
                        commentsString.append("<span>Parent Comment: </span><a href=\"#").append(commentSection.getParentCommentId())
                            .append("\">").append(commentSection.getParentCommentId()).append("</a>")
                            .append("<span> by </span>").append(commentIdAuthorSet.get(commentSection.getParentCommentId())).append(newLine);
                    } else {
                        commentsString.append("<p>Parent comment not included</p>").append(newLine);
                    }

                    commentsString.append("<hr>").append(newLine);
                }
                commentsString.append(commentSection.getAsHtml());
                commentsString.append("<hr>").append(newLine);
            }
        }
        return commentsString.toString();
    }
    /**
     * Get all comments including those that will later be filtered out
     * @param doc - to get comment elements from
     * @return commentSectionList containing all comments
     */
    private ArrayList<LessWrongCommentSection> getAllComments(final Document doc){
        ArrayList<LessWrongCommentSection> commentSections = new ArrayList<LessWrongCommentSection>();
        Elements entries = doc.select("div.comment");
        for (Element e : entries) {
            String commentId = e.id().replace("thingrow_t1_", "");
            String authorHtml = "";
            String commentDate = "";
            String votesHtml = "";
            int votes = 0;
            String parentId = "";
            Elements authorElements = e.select("span.author");
            if (authorElements.size() > 0) {
                authorHtml = authorElements.get(0).outerHtml();
            }
            Elements commentDateElements = e.select("span.comment-date");
            if (commentDateElements.size() > 0) {
                commentDate = commentDateElements.get(0).outerHtml();
            }

            Elements votesElements = e.select("span.votes");
            if (votesElements.size() > 0) {
                votesHtml = votesElements.get(0).outerHtml();
                String votesStr = votesElements.get(0).text();

                if(votesStr.contains(" ")){
                    votesStr = votesStr.substring(0, votesStr.indexOf(" ")); 
                    try {
                        votes = Integer.parseInt(votesStr);
                    } catch (NumberFormatException ne) {
                        logger.info("Unable to convert " + votesStr + " to a number. The votes for comment " + commentId + " have been set to 0");
                    }
                }
            }
            Elements parent = e.select("a.bylink");
            for (Element p : parent) {
                if (p.id().replace("parent_t1_", "").equals(commentId)) {
                    parentId = p.attr("href").substring(1);
                }
            }
           
            Element commentContentId = e.getElementById("body_t1_" + commentId);
            if (commentContentId != null) {
                String contentHtml = commentContentId.outerHtml();
            
                // only include comments which have content, i.e. don't include deleted ones
                if (!commentContentId.select("div.md").isEmpty()) {
                    commentSections.add(new LessWrongCommentSection(commentId, authorHtml, commentDate, votesHtml, votes, contentHtml, parentId));
                } 
            }
        }
        return commentSections;
    }
}
