package github.satoshi_nakamoto2.lessWrongBookCreator.scraper;

/**
 * Exception for when Input excel files cannot be read correctly either due to formatting issues or other exceptions
 */
public final class ExcelExtractionException extends Exception {
    public ExcelExtractionException(String message) {
        super(message);
    }
}
