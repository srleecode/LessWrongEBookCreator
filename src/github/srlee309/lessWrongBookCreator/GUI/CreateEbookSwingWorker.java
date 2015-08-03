package github.srlee309.lessWrongBookCreator.GUI;

import github.srlee309.lessWrongBookCreator.bookGeneration.ChapterPageCreator;
import github.srlee309.lessWrongBookCreator.bookGeneration.EBookCreator;
import github.srlee309.lessWrongBookCreator.bookGeneration.PostHtmlCreator;
import github.srlee309.lessWrongBookCreator.bookGeneration.SummaryHtmlCreator;
import github.srlee309.lessWrongBookCreator.excelReader.SummaryFileReader;
import github.srlee309.lessWrongBookCreator.scraper.BookSummarySection;
import github.srlee309.lessWrongBookCreator.scraper.ExcelExtractionException;
import github.srlee309.lessWrongBookCreator.scraper.GetPostSectionCallable;
import github.srlee309.lessWrongBookCreator.scraper.PostChapter;
import github.srlee309.lessWrongBookCreator.scraper.PostExtractionDetails;
import github.srlee309.lessWrongBookCreator.scraper.PostScraper;
import github.srlee309.lessWrongBookCreator.scraper.PostSection;
import github.srlee309.lessWrongBookCreator.scraper.PostSummarySection;
import github.srlee309.lessWrongBookCreator.scraper.SequenceSummarySection;
import github.srlee309.lessWrongBookCreator.utilities.DirectoryPurger;
import github.srlee309.lessWrongBookCreator.utilities.SubmitOrderedCompletionService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * SwingWorker class that calls the operations of getting content, building html files and creating ebook
 */
public class CreateEbookSwingWorker extends SwingWorker<Object, String>{
    private final String newLine = System.getProperty("line.separator");
    private static final Logger logger = LoggerFactory.getLogger(CreateEbookSwingWorker.class);
    private final JTextArea logTextArea;
    private final JButton createEbookButton;
    private final File inputFile;
    private final File outputFile;
    private final String coverPageFileString;
    private final int commentsThreshold;
    private final boolean isCommentsIncluded;
    private final boolean isChildPostsIncluded;
    private final boolean isParentPostsIncluded;
    private final boolean isThresholdAppliesToChildPosts;
    private double progress;
    
    public CreateEbookSwingWorker(JTextArea logTextArea, JButton createEbookButton, File inputFile, File outputFile, String coverPageFileString, int commentsThreshold, 
            boolean isCommentsIncluded, boolean isChildPostsIncluded, boolean isParentPostsIncluded, boolean isThresholdAppliesToChildPosts) {
        this.logTextArea = logTextArea;
        this.createEbookButton = createEbookButton;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.coverPageFileString = coverPageFileString;
        this.commentsThreshold = commentsThreshold;
        this.isCommentsIncluded = isCommentsIncluded;
        this.isChildPostsIncluded = isChildPostsIncluded;
        this.isParentPostsIncluded = isParentPostsIncluded;
        this.isThresholdAppliesToChildPosts = isThresholdAppliesToChildPosts;
        this.progress = 0;
    }
    
    @Override
    protected Object doInBackground() throws Exception {
        boolean cleanedHtmlOutputDir = false;
        process("Cleaning htmlOutput folder ..." + newLine);
        try {
            cleanUpHtmlOutputDir();
            cleanedHtmlOutputDir = true;
        } catch (IOException e) {
            logger.error("Unable to remove content in Html Output directory. Check permissions right on this folder", e);
        }
        if (cleanedHtmlOutputDir) {
            progress = 5;
            setProgress((int)progress);
            
            SummaryHtmlCreator summaryHtmlCreator = new SummaryHtmlCreator();
            EBookCreator ebookCreator = new EBookCreator();
            
            process("Reading summary data from: " + inputFile + newLine);
            SummaryFileReader inputFileReader = new SummaryFileReader();
            try {
                ArrayList<BookSummarySection> bookSummarySections = inputFileReader.getSummarySections(inputFile);
                summaryHtmlCreator.createSummaryPage(bookSummarySections);
                process("Summary page has been created" + newLine);
                progress += 10;
                setProgress((int)progress);
                process("Scraping posts and creating pages..." + newLine);

                ArrayList<PostChapter> postChapters = getPostChapters(bookSummarySections);
                ArrayList<PostExtractionDetails> postsExtractionDetailsList = getPostExtractionDetailsList(bookSummarySections, postChapters);

                ArrayList<PostSection> postSections = getPostSections(postsExtractionDetailsList);
                
                progress = 90;
                setProgress((int)progress);
                
                process("Creating ebook ..." + newLine);
                PostHtmlCreator postHtmlCreator = new PostHtmlCreator();
                postHtmlCreator.createPostsPages(postSections);
                
                ChapterPageCreator chapterPageCreator = new ChapterPageCreator();
                chapterPageCreator.createChapterPageFiles(postChapters);
                if (!coverPageFileString.isEmpty()) {
                    ebookCreator.createBook(postChapters, outputFile , coverPageFileString);
                } else {
                    ebookCreator.createBook(postChapters, outputFile , "");
                }
                process(outputFile.getAbsolutePath() + " has been created");
                progress = 100;
                setProgress((int)progress);
            } catch (ExcelExtractionException pe){
                setProgress (0);
                process(pe.getLocalizedMessage());
            }
        }
        return 0;
    }

    @Override
    protected void done() {
        try {
            super.get();
        } catch (InterruptedException ex) {
            logger.error("" ,ex);
        } catch (ExecutionException ex) {
            logger.error("" ,ex);
        }
        createEbookButton.setEnabled(true);
    }
    @Override
    protected void process(List<String> logStrings) {
        for(String logStr : logStrings) {
            logTextArea.append(logStr);
        }
    }
    private void process(String logString) {
            ArrayList<String> logStrings = new ArrayList<String>();
            logStrings.add(logString);
            process(logStrings);
    }
    /**
     * Create the folder htmlOutput if it doesn't exist otherwise remove all files in the folder
     */
    private void cleanUpHtmlOutputDir() throws IOException {
        File htmlOutputDir = new File("htmlOutput");
        
        if (!htmlOutputDir.exists()) {
            try{
                htmlOutputDir.mkdir();
            } catch(SecurityException se){
                logger.error("",se);
                cancel(true);
            }        
        } else {
            DirectoryPurger dirPurger = new DirectoryPurger();
            dirPurger.purgeDirectory(htmlOutputDir);
        }
    }
    private ArrayList<PostExtractionDetails> getPostExtractionDetailsList(ArrayList<BookSummarySection> bookSummarySections, ArrayList<PostChapter> postChapters) {
        ArrayList<PostExtractionDetails> postsExtractionDetails = new ArrayList<PostExtractionDetails>();
        int postIdx = 0;
        for (BookSummarySection bookSummarySection : bookSummarySections) {
            for(SequenceSummarySection sequenceSummarySection : bookSummarySection.getSequenceSummarySections()) {
                for (PostSummarySection postSummarySection : sequenceSummarySection.getPostSummarySections()) {
                    PostExtractionDetails postExtractionDetails = null;
                    if (postIdx + 1 < postChapters.size()) {
                        postExtractionDetails = new PostExtractionDetails(postSummarySection.getTitle(), sequenceSummarySection.getTitle(), 
                            bookSummarySection.getTitle(), postSummarySection.getUrl(), postChapters.get(postIdx + 1).getPostName(), isCommentsIncluded,
                            commentsThreshold, isChildPostsIncluded, isParentPostsIncluded, isThresholdAppliesToChildPosts);
                    } else {
                        postExtractionDetails = new PostExtractionDetails(postSummarySection.getTitle(), sequenceSummarySection.getTitle(), 
                            bookSummarySection.getTitle(), postSummarySection.getUrl(), "", isCommentsIncluded,
                            commentsThreshold, isChildPostsIncluded, isParentPostsIncluded, isThresholdAppliesToChildPosts);
                    }
                    postIdx++;
                    postsExtractionDetails.add(postExtractionDetails);
                }
            }
        }
        return postsExtractionDetails;
    }
    private ArrayList<PostSection> getPostSections(ArrayList<PostExtractionDetails> postsExtractionDetails) {
        PostScraper postScraper = new PostScraper();
        ArrayList<PostSection> postSections = new ArrayList<PostSection>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        SubmitOrderedCompletionService<PostSection> taskCompletionService = new SubmitOrderedCompletionService<PostSection>(executorService);

        try {
            for (PostExtractionDetails postExtractionDetails : postsExtractionDetails) {
                taskCompletionService.submit(new GetPostSectionCallable(postScraper, postExtractionDetails));
            }
            for (int i = 0; i < postsExtractionDetails.size(); i++) {
                Future<PostSection> result = taskCompletionService.take(); 
                PostSection postSection = result.get();
                progress += (double)75 / postsExtractionDetails.size();
                if (postSection != null) {
                    if (!postSection.getHtmlContent().isEmpty()) {
                        process("Scraped page " + postSection.getUrl() + newLine);
                        setProgress((int)progress);
                        postSections.add(postSection);
                    } else {
                        process("There was a problem scraping the page " + postSection.getUrl() + newLine);
                    }
                }
            }
        } catch (InterruptedException e) {
            logger.error("", e);
            process(e.getLocalizedMessage());
        } catch (ExecutionException e) {
            logger.error("", e);
            process(e.getLocalizedMessage());
        }
        executorService.shutdown();
        return postSections;
    }
    private ArrayList<PostChapter> getPostChapters(ArrayList<BookSummarySection> bookSummarySections){
        ArrayList<PostChapter> postChapters = new ArrayList<PostChapter>();
        for (BookSummarySection bookSection : bookSummarySections) {
            for (SequenceSummarySection sequeunceSection : bookSection.getSequenceSummarySections()) {
                for (PostSummarySection postSection : sequeunceSection.getPostSummarySections()) {
                    postChapters.add(new PostChapter(bookSection.getTitle(), sequeunceSection.getTitle(), postSection.getTitle()));
                }
            }
        }
        return postChapters;
    }
}
