package github.srlee309.lessWrongBookCreator.scraper;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static org.junit.Assert.*;

public class OvercomingBiasPostSectionExtractorTest {
    @Test
    public void getPostSectionString_ReadStoredHtmlNoComments_OutputAsExpected() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", false, 0, false, false, false); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Overcoming Bias  Does Decadence Cause Decay.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        OvercomingBiasPostSectionExtractor instance = new OvercomingBiasPostSectionExtractor();
        String result = instance.getPostSectionString(postExtractionDetails, doc);
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Overcoming Bias  Does Decadence Cause Decay No Comments.txt").toURI()), "UTF-8");
        
        assertTrue(expResult.trim().equals(result.trim()));
    }

    @Test
    public void getPostSectionString_ReadStoredHtmlWithComments_OutputAsExpected() throws Exception{
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", true, 0, false, false, false); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Overcoming Bias  Does Decadence Cause Decay.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        OvercomingBiasPostSectionExtractor instance = new OvercomingBiasPostSectionExtractor();
        String result = instance.getPostSectionString(postExtractionDetails, doc);
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Overcoming Bias  Does Decadence Cause Decay With Comments.txt").toURI()), "UTF-8");
        
        assertTrue(expResult.trim().equals(result.trim()));
    }
    
}
