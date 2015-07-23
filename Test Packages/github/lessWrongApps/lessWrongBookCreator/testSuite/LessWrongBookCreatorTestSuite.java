package github.lessWrongApps.lessWrongBookCreator.testSuite;

import github.lessWrongApps.lessWrongBookCreator.bookGeneration.PostHtmlCreatorTest;
import github.lessWrongApps.lessWrongBookCreator.bookGeneration.SummaryHtmlCreatorTest;
import github.lessWrongApps.lessWrongBookCreator.scraper.LessWrongPostSectionExtractorTest;
import github.lessWrongApps.lessWrongBookCreator.scraper.SummaryFileReaderTest;
import github.lessWrongApps.lessWrongBookCreator.scraper.WikiPostSectionExtractorTest;
import github.lessWrongApps.lessWrongBookCreator.scraper.YudowskyPostSectionExtractorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({YudowskyPostSectionExtractorTest.class, WikiPostSectionExtractorTest.class, SummaryFileReaderTest.class, 
    LessWrongPostSectionExtractorTest.class, SummaryHtmlCreatorTest.class, PostHtmlCreatorTest.class})
public class LessWrongBookCreatorTestSuite {
}
