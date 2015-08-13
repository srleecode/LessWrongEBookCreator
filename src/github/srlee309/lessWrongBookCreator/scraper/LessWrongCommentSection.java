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
 * Class contains information required to create the html for a comment
 */
public final class LessWrongCommentSection {
    private final String id;
    private final String authorHtml;
    private final String timeSubmittedHtml;
    private final String votesHtml;
    private final int votes;
    private final String contentHtml;
    private final String parentCommentId;

    public LessWrongCommentSection(String id, String authorHtml, String timeSubmittedHtml, String votesHtml, int votes, String contentHtml, String parentCommentId) {
        this.id = id;
        this.authorHtml = authorHtml;
        this.timeSubmittedHtml = timeSubmittedHtml;
        this.votesHtml = votesHtml;
        this.votes = votes;
        this.contentHtml = contentHtml;
        this.parentCommentId = parentCommentId;
    }
    public String getId() {
        return id;
    }

    public String getAuthorHtml() {
        return authorHtml;
    }

    public String getTimeSubmittedHtml() {
        return timeSubmittedHtml;
    }

    public String getVotesHtml() {
        return votesHtml;
    }

    public int getVotes() {
        return votes;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public String getAsHtml() {
        StringBuilder commentHtmlBuilder = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        commentHtmlBuilder.append("<a name=\"").append(id).append("\"/><span>ID: ").append(id).append("</span>").append(newLine);
        commentHtmlBuilder.append(authorHtml).append(newLine);
        commentHtmlBuilder.append(timeSubmittedHtml).append(newLine);
        commentHtmlBuilder.append(votesHtml).append(newLine);
        commentHtmlBuilder.append(contentHtml).append(newLine);
        return commentHtmlBuilder.toString();
    }
}
