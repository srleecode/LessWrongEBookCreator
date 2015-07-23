package github.satoshi_nakamoto2.lessWrongBookCreator.scraper;

import java.util.concurrent.Callable;

/**
 * Returns the result of scraping a post for a given {@link PostExtractionDetails}
 */
public final class GetPostSectionCallable implements Callable<PostSection>{
    private final PostScraper postScraper;
    private final PostExtractionDetails postExtractionDetails;
    public GetPostSectionCallable(PostScraper postScraper, PostExtractionDetails postExtractionDetails) {
        this.postScraper = postScraper;
        this.postExtractionDetails = postExtractionDetails;
    }

    @Override
    public PostSection call() {
        return postScraper.getPostSection(postExtractionDetails);
    }
    
}
