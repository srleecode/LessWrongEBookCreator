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
