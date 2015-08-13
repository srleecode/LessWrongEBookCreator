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
package github.srlee309.lessWrongBookCreator.bookGeneration;

import github.srlee309.lessWrongBookCreator.scraper.PostSection;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PostHtmlCreatorTest {
    @Test
    public void createPostsPages_PostSection_OutputHtmlAsExpected() throws Exception{
        String expectedHtmlString = FileUtils.readFileToString(new File(this.getClass().getResource("/FeelingRational.html").toURI()));
        String title = "Feeling Rational";
        String url = "http://lesswrong.com/lw/hp/feeling_rational/?all=true";
        String postContent = FileUtils.readFileToString(new File(this.getClass().getResource("/Feeling Rational PostSection content.txt").toURI()));
        PostSection postSection = new PostSection(title, url, postContent);
        ArrayList<PostSection> postSections = new ArrayList<PostSection>();
        postSections.add(postSection);
        PostHtmlCreator instance = new PostHtmlCreator();
        instance.createPostsPages(postSections);
        String resultHtmlString =  FileUtils.readFileToString(new File("htmlOutput/FeelingRational.html"));
    
        assertTrue(resultHtmlString.equals(expectedHtmlString));
    }
    
}
