package github.srlee309.lessWrongBookCreator.scraper;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static org.junit.Assert.*;

public class WikiPostSectionExtractorTest {     
    @Test
    public void getPostSectionString_ReadStoredHtml_OutputAsExpected() throws Exception {
        PostExtractionDetails postExtractionDetails = new PostExtractionDetails("", "", "", "", "", false, 0, false, false, false); 
        String htmlString =  FileUtils.readFileToString(new File(this.getClass().getResource("/Feeling Moral - Lesswrongwiki.html").toURI()));
        Document doc =  Jsoup.parse(htmlString);
        WikiPostSectionExtractor instance = new WikiPostSectionExtractor();
        String result = instance.getPostSectionString(postExtractionDetails, doc);
        String expResult = FileUtils.readFileToString(new File(this.getClass().getResource("/Feeling Moral - Lesswrongwiki.txt").toURI()));

        assertTrue(expResult.trim().equals(result.trim()));
    }
}
