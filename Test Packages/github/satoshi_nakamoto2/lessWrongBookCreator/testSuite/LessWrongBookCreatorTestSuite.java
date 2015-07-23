package github.satoshi_nakamoto2.lessWrongBookCreator.testSuite;

import github.satoshi_nakamoto2.lessWrongBookCreator.bookGeneration.PostHtmlCreatorTest;
import github.satoshi_nakamoto2.lessWrongBookCreator.bookGeneration.SummaryHtmlCreatorTest;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.LessWrongPostSectionExtractorTest;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.SummaryFileReaderTest;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.WikiPostSectionExtractorTest;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.YudowskyPostSectionExtractorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({YudowskyPostSectionExtractorTest.class, WikiPostSectionExtractorTest.class, SummaryFileReaderTest.class, 
    LessWrongPostSectionExtractorTest.class, SummaryHtmlCreatorTest.class, PostHtmlCreatorTest.class})
public class LessWrongBookCreatorTestSuite {
}
