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
    private final String currentDirPath;

    public EBookCreator() {
        this.currentDirPath = new File(".").getAbsolutePath();
    }
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
                        addResource(book, selectedFile, selectedFile.getName());
                    } 
                }
                setCoverPage(book,coverPageFile, coverPageFile.getName());
            }
            File summaryHtmlFile = new File(currentDirPath + "//htmlOutput//summaryTOC.html");
            addSection(book, summaryHtmlFile, "summaryTOC.html", "Summary");
          
            for (File selectedFile : getImageFiles("htmlOutput")) {  
                    addResource(book, selectedFile, selectedFile.getName());
            } 

            String currBook = "";
            String currSequence = "";
            for (PostChapter postChapter : postChapters) {
                if (!currBook.equals(postChapter.getBookName())) {
                    String BookFileTitle = postChapter.getBookName().replaceAll("\\W+", "");
                    File bookFile = new File(currentDirPath+"//htmlOutput//" + BookFileTitle + ".html");
                    addSection(book, bookFile, BookFileTitle +".html", postChapter.getBookName());
                    currBook = postChapter.getBookName();
                }
                if (!currSequence.equals(postChapter.getSequenceName())) {
                    String sequenceFileTitle = postChapter.getSequenceName().replaceAll("\\W+", "");
                    File sequenceFile = new File(currentDirPath+"//htmlOutput//" + sequenceFileTitle + ".html");
                    addSection(book, sequenceFile, sequenceFileTitle +".html", postChapter.getSequenceName());
                    currSequence = postChapter.getSequenceName();
                }
                String postFileTitle = postChapter.getPostName().replaceAll("\\W+", "");
                File postFile = new File(currentDirPath+"//htmlOutput//" + postFileTitle + ".html");
                addSection(book, postFile, postFileTitle +".html", postChapter.getPostName());
            }
            EpubWriter epubWriter = new EpubWriter();
            FileOutputStream out = new FileOutputStream(outputFile);
            epubWriter.write(book, out);
            out.close();
        } catch (FileNotFoundException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }
    private void setCoverPage(Book book, File file, String resourceTitle) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            book.setCoverPage(new Resource(in, resourceTitle));
        } catch (FileNotFoundException ex) {
            logger.error("", ex);
        } catch (IOException ex) {
            logger.error("", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
               logger.error("", ex);
            }
        }
    }
    
    private void addResource(Book book, File file, String resourceTitle) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            book.getResources().add(new Resource(in, resourceTitle));
        } catch (FileNotFoundException ex) {
            logger.error("", ex);
        } catch (IOException ex) {
            logger.error("", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
               logger.error("", ex);
            }
        }
    }
    private void addSection (Book book, File file, String resourceTitle, String sectionTitle) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            book.addSection(sectionTitle, new Resource(in, resourceTitle));
        } catch (FileNotFoundException ex) {
            logger.error("", ex);
        } catch (IOException ex) {
            logger.error("", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
               logger.error("", ex);
            }
        }
    }
}
