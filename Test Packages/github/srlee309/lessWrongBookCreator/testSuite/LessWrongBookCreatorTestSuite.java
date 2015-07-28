package github.srlee309.lessWrongBookCreator.testSuite;

import github.srlee309.lessWrongBookCreator.bookGeneration.PostHtmlCreatorTest;
import github.srlee309.lessWrongBookCreator.bookGeneration.SummaryHtmlCreatorTest;
import github.srlee309.lessWrongBookCreator.scraper.LessWrongPostSectionExtractorTest;
import github.srlee309.lessWrongBookCreator.scraper.SummaryFileReaderTest;
import github.srlee309.lessWrongBookCreator.scraper.WikiPostSectionExtractorTest;
import github.srlee309.lessWrongBookCreator.scraper.YudowskyPostSectionExtractorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({YudowskyPostSectionExtractorTest.class, WikiPostSectionExtractorTest.class, SummaryFileReaderTest.class, 
    LessWrongPostSectionExtractorTest.class, SummaryHtmlCreatorTest.class, PostHtmlCreatorTest.class})
public class LessWrongBookCreatorTestSuite {
}
