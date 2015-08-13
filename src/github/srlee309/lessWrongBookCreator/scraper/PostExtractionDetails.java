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
 * Parameter object for configuration settings to use when scraping the information from a less wrong post
 */
public final class PostExtractionDetails {
    private final String title;
    private final String sequence;
    private final String book;
    private String url; 
    private final String nextPost;
    private final boolean isCommentsIncluded;
    private final int commentThreshold; 
    private final boolean isChildPostsIncluded; 
    private final boolean isParentPostsIncluded; 
    private final boolean isThresholdAppliesToChildPosts;

    private PostExtractionDetails(Builder builder) {
        this.title = builder.title;
        this.sequence = builder.sequence;
        this.book = builder.book;
        this.url = builder.url;
        this.nextPost = builder.nextPost;
        this.isCommentsIncluded = builder.isCommentsIncluded;
        this.commentThreshold = builder.commentThreshold;
        this.isChildPostsIncluded = builder.isChildPostsIncluded;
        this.isParentPostsIncluded = builder.isParentPostsIncluded;
        this.isThresholdAppliesToChildPosts = builder.isThresholdAppliesToChildPosts;
    }
    
    public String getTitle() {
        return title;
    }

    public String getSequence() {
        return sequence;
    }

    public String getBook() {
        return book;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getNextPost() {
        return nextPost;
    }

    public boolean isCommentsIncluded() {
        return isCommentsIncluded;
    }

    public int getCommentThreshold() {
        return commentThreshold;
    }

    public boolean isChildPostsIncluded() {
        return isChildPostsIncluded;
    }

    public boolean isParentPostsIncluded() {
        return isParentPostsIncluded;
    }

    public boolean isThresholdAppliesToChildPosts() {
        return isThresholdAppliesToChildPosts;
    }
    public static class Builder {
        // All are Optional parameters - initialized to default values
        private String title = "";
        private String sequence = "";
        private String book = "";
        private String url = ""; 
        private String nextPost = "";
        private boolean isCommentsIncluded = false;
        private int commentThreshold = 0; 
        private boolean isThresholdAppliesToChildPosts = false;
        private boolean isChildPostsIncluded = false; 
        private boolean isParentPostsIncluded = false; 
        

        public Builder title (String val) {
            this.title = val;
            return this;
        }
        
        public Builder sequence(String val) {
            this.sequence = val;
            return this;
        }
        
        public Builder book(String val) {
            this.book = val;
            return this;
        }
        
        public Builder url(String val) {
            this.url = val;
            return this;
        }
        
        public Builder nextPost(String val) {
            this.nextPost = val;
            return this;
        }
        
        public Builder commentsIncluded(boolean val) {
            this.isCommentsIncluded = val;
            return this;
        }
        
        public Builder commentsThreshold(int val) {
            this.commentThreshold = val;
            return this;
        }
        
        public Builder thresholdAppliesToChildPosts(boolean val) {
            this.isThresholdAppliesToChildPosts = val;
            return this;
        }
        
        public Builder childPostsIncluded(boolean val) {
            this.isChildPostsIncluded = val;
            return this;
        }
        
        public Builder parentPostsIncluded(boolean val) {
            this.isParentPostsIncluded = val;
            return this;
        }
        
        public PostExtractionDetails build() {
            return new PostExtractionDetails(this); 
        }

    }
}
