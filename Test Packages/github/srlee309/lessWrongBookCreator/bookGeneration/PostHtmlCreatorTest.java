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
