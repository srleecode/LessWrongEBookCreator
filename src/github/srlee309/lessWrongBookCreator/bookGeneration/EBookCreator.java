package github.srlee309.lessWrongBookCreator.bookGeneration;

import github.srlee309.lessWrongBookCreator.scraper.PostChapter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.activation.MimetypesFileTypeMap;
import nl.siegmann.epublib.domain.Author;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
/**
 * Class creates ebooks using the html and image files found in the htmlOutput folder
 */
public class EBookCreator {
    private static final Logger logger = LoggerFactory.getLogger(EBookCreator.class);

    /**
     * @return imageFiles - in the htmlOutput folder. Non-html and non-directory files are taken to be image files
     */
    private File[] getImageFiles(String dirStr) {
        File dir = new File(dirStr);
        File[] selectedFiles = dir.listFiles(new FileFilter() {   
            @Override    
            public boolean accept(File file) {   
                MimetypesFileTypeMap mtftp = new MimetypesFileTypeMap();
                mtftp.addMimeTypes("image png tif jpg jpeg bmp");
                String mimetype = mtftp.getContentType(file);

                String type = mimetype.split("/")[0];
                return !(file.isDirectory() || file.getName().toLowerCase().endsWith(".html") || !type.equals("image"));   
            }
        });
        return selectedFiles;
    }
    /**
     * Creates an ebook with the given bookName and using postSections to get the names of the post html files
     * @param postChapters to use when creating the book 
     * @param outputFile epub output file
     * @param coverPageFilePath to use if a coverpage is required
     */
    public void createBook(ArrayList<PostChapter> postChapters, File outputFile, String coverPageFilePath) {
        try{
            Book book = new Book();
            String bookTitle = outputFile.getName();
            int pos = bookTitle.lastIndexOf(".");
            if (pos > 0) {
                bookTitle = bookTitle.substring(0, pos);
            }
            book.getMetadata().addTitle(bookTitle);
            book.getMetadata().addAuthor(new Author("Less Wrong"));
            book.getMetadata().addDate(new Date(new java.util.Date()));
            
            book.getResources().add(new Resource(EBookCreator.class.getResourceAsStream("/seperator.gif"), "seperator.gif"));
            
            if (!coverPageFilePath.isEmpty()) {
                File coverPageFile = new File(coverPageFilePath);
                File coverPageFolder = coverPageFile.getParentFile();
                if(coverPageFolder.isDirectory()){
                    for (File selectedFile : getImageFiles(coverPageFolder.getAbsolutePath())) {  
                        book.getResources().add(new Resource(new FileInputStream(selectedFile), selectedFile.getName()));
                    } 
                }
                book.setCoverPage(new Resource(new FileInputStream(coverPageFile.getAbsolutePath()), coverPageFile.getName()));
            }
            
            book.addSection("Summary", new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//summaryTOC.html"), "summaryTOC.html"));
            for (File selectedFile : getImageFiles("htmlOutput")) {  
                    book.getResources().add(new Resource(new FileInputStream(selectedFile.getAbsolutePath()), selectedFile.getName()));
            } 

            String currBook = "";
            String currSequence = "";
            for (PostChapter postChapter : postChapters) {
                if (!currBook.equals(postChapter.getBookName())) {
                    String BookFileTitle = postChapter.getBookName().replaceAll("\\W+", "");
                    book.addSection(postChapter.getBookName(), new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//" + BookFileTitle + ".html"), BookFileTitle +".html"));
                    currBook = postChapter.getBookName();
                }
                if (!currSequence.equals(postChapter.getSequenceName())) {
                    String sequenceFileTitle = postChapter.getSequenceName().replaceAll("\\W+", "");
                    book.addSection(postChapter.getSequenceName(), new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//" + sequenceFileTitle + ".html"), sequenceFileTitle +".html"));
                    currSequence = postChapter.getSequenceName();
                }
                String postFileTitle = postChapter.getPostName().replaceAll("\\W+", "");
                book.addSection(postChapter.getPostName(), new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//" + postFileTitle + ".html"), postFileTitle +".html"));
            }
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, new FileOutputStream(outputFile));
        } catch (FileNotFoundException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }
}
