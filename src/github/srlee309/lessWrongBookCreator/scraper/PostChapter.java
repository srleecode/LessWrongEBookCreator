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
 * Data class to define book and sequence each post belongs to
 */
public class PostChapter {
    private final String bookName;
    private final String sequenceName;
    private final String postName;

    public PostChapter(String bookName, String sequenceName, String postName) {
        this.bookName = bookName;
        this.sequenceName = sequenceName;
        this.postName = postName;
    }
     public String getBookName() {
        return bookName;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public String getPostName() {
        return postName;
    }
}
