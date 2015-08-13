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

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static org.junit.Assert.*;

public class LessWrongPostSectionExtractorTest {
    @Test
    public void getPostSectionString_NoComments_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails.Builder().build();
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong No Comments.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);

        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdZero_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails.Builder().commentsIncluded(true).build(); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection With Threshold 0.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);

        assertTrue(expResult.trim().equals(result.trim()));
    }

    @Test
    public void getPostSectionString_WithCommentsThresholdNineThousand_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails.Builder()
                                                                .commentsIncluded(true).commentsThreshold(9000)
                                                                .build(); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong No Comments.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);
        
        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdMinusNineThousand_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails.Builder()
                                                                .commentsIncluded(true).commentsThreshold(-9000)
                                                                .build();
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection Threshold Minus 9000.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);

        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdFiveAndAppliesToChildren_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails.Builder()
                                                                .commentsIncluded(true).commentsThreshold(5)
                                                                .thresholdAppliesToChildPosts(true).childPostsIncluded(true)
                                                                .build();
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection With Threshold 5 Applies To Children Includes Children.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);

        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdFiveAndAppliesToChildrenAndIncludeParent_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails.Builder()
                                                                .commentsIncluded(true).commentsThreshold(5)
                                                                .thresholdAppliesToChildPosts(true).parentPostsIncluded(true)
                                                                .build();
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection With Threshold 5 Applies To Children Includes Parent.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);
        
        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdFiveAndAppliesToChildrenAndIncludeParentAndIncludesChildren_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails.Builder()
                                                                .commentsIncluded(true).commentsThreshold(5)
                                                                .thresholdAppliesToChildPosts(true).childPostsIncluded(true)
                                                                .parentPostsIncluded(true)
                                                                .build();
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection With Threshold 5 Applies To Children Includes Parent Includes Children.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);
       
        assertTrue(expResult.trim().equals(result.trim()));
    }
 
}
