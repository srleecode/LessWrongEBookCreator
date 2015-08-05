package github.srlee309.lessWrongBookCreator.scraper;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static org.junit.Assert.*;

public class YudowskyPostSectionExtractorTest {
    @Test
    public void getPostSectionString_ReadStoredHtml_OutputAsExpected() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", false, 0, false, false, false); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Yudkowsky - The Simple Truth.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        YudowskyPostSectionExtractor instance = new YudowskyPostSectionExtractor();
        String result = instance.getPostSectionString(postExtractionDetails, doc);
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Yudkowsky - The Simple Truth Post Section.txt").toURI()));
        
        assertTrue(expResult.trim().equals(result.trim()));
    }
}
