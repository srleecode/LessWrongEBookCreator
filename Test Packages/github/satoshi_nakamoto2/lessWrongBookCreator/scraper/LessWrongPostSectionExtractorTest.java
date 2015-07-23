package github.satoshi_nakamoto2.lessWrongBookCreator.scraper;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static org.junit.Assert.*;

public class LessWrongPostSectionExtractorTest {
    @Test
    public void getPostSectionString_NoComments_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", false, 0, false, false, false); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong No Comments.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);

        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdZero_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", true, 0, false, false, false); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection With Threshold 0.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);

        assertTrue(expResult.trim().equals(result.trim()));
    }

    @Test
    public void getPostSectionString_WithCommentsThresholdNineThousand_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", true, 9000, false, false, false); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong No Comments.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);
        
        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdMinusNineThousand_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", true, -9000, false, false, false); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection Threshold Minus 9000.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);

        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdFiveAndAppliesToChildren_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", true, 5, true, false, true); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection With Threshold 5 Applies To Children Includes Children.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);

        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdFiveAndAppliesToChildrenAndIncludeParent_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", true, 5, false, true, true); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection With Threshold 5 Applies To Children Includes Parent.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);
        
        assertTrue(expResult.trim().equals(result.trim()));
    }
    
    @Test
    public void getPostSectionString_WithCommentsThresholdFiveAndAppliesToChildrenAndIncludeParentAndIncludesChildren_StringCorrect() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", true, 5, true, true, true); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        LessWrongPostSectionExtractor instance = new LessWrongPostSectionExtractor();
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Sorting Out Sticky Brains - Less Wrong PostSection With Threshold 5 Applies To Children Includes Parent Includes Children.txt").toURI()));
        String result = instance.getPostSectionString(postExtractionDetails, doc);
       
        assertTrue(expResult.trim().equals(result.trim()));
    }
 
}
