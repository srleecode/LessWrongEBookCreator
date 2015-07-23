package github.satoshi_nakamoto2.lessWrongBookCreator.bookGeneration;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.ExcelExtractionException;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.PostChapter;
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
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubWriter;
/**
 * Class creates ebooks using the html and image files found in the htmlOutput folder
 * @author Satoshi_Nakamoto
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
     * @param bookName to save the ebook as 
     * @param coverPageFilePath to use if a coverpage is required
     */
    public void createBook(ArrayList<PostChapter> postChapters, String bookName, String coverPageFilePath) {
        try{
            Book book = new Book();
            String bookTitle = new File(bookName).getName();
            int pos = bookTitle.lastIndexOf(".");
            if (pos > 0) {
                bookTitle = bookTitle.substring(0, pos);
            }
            book.getMetadata().addTitle(bookTitle);
            book.getMetadata().addAuthor(new Author("Less Wrong"));
            book.getMetadata().addDate(new Date(new java.util.Date()));
            book.getResources().add(new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//resources//seperator.gif"), "seperator.gif"));
            
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
            
            TOCReference summary = book.addSection("Summary", new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//summary.html"), "summary.html"));
            for (File selectedFile : getImageFiles("htmlOutput")) {  
                    book.getResources().add(new Resource(new FileInputStream(selectedFile.getAbsolutePath()), selectedFile.getName()));
            } 
            TOCReference currChapter = null;
            ArrayList<TOCReference> references = new ArrayList<TOCReference>();
            references.add(summary);
            String currBook = "";
            String currSequence = "";
            
            for (PostChapter postChapter : postChapters) {
                if (!currBook.equals(postChapter.getBookName())) {
                    if (currChapter != null) {
                        references.add(currChapter);
                        currChapter = null;
                    }
                    String BookFileTitle = postChapter.getBookName().replaceAll("\\W+", "");
                    currChapter = book.addSection(postChapter.getBookName(), new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//" + BookFileTitle + ".html"), BookFileTitle +".html"));
                    currBook = postChapter.getBookName();
                }
                if (!currSequence.equals(postChapter.getSequenceName())) {
                    String sequenceFileTitle = postChapter.getSequenceName().replaceAll("\\W+", "");
                    currSequence = postChapter.getSequenceName();
                    if (currChapter == null) {
                        currChapter = book.addSection(postChapter.getSequenceName(), new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//" + sequenceFileTitle + ".html"), sequenceFileTitle +".html"));
                    } else {
                        book.addSection(currChapter,postChapter.getSequenceName(), new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//" + sequenceFileTitle + ".html"), sequenceFileTitle +".html"));
                    }
                }
                String postFileTitle = postChapter.getPostName().replaceAll("\\W+", "");
                if (currChapter == null) {
                    currChapter = book.addSection(postChapter.getPostName(), new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//" + postFileTitle + ".html"), postFileTitle +".html"));
                } else {
                    book.addSection(currChapter,postChapter.getPostName(), new Resource(new FileInputStream(new File(".").getAbsolutePath()+"//htmlOutput//" + postFileTitle + ".html"), postFileTitle +".html"));
                }
            }
            if (currChapter != null) {
                references.add(currChapter);
            }
            book.getTableOfContents().setTocReferences(references);
            Spine spine = new Spine(book.getTableOfContents());
            book.setSpine(spine);
            EpubWriter epubWriter = new EpubWriter();
            epubWriter.write(book, new FileOutputStream(bookName));
        } catch (FileNotFoundException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }
}
