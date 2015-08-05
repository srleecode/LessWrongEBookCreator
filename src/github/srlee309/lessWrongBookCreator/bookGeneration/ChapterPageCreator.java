package github.srlee309.lessWrongBookCreator.bookGeneration;

import github.srlee309.lessWrongBookCreator.scraper.PostChapter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Creates html pages for passed in list of {@link PostChapter}s
 */
public class ChapterPageCreator extends HtmlOutputFileCreator{
    /**
     * Creates html file of title for each PostChapter
     * @param postChapters which includes chapter titles to use to create the files
     */
    public void createChapterPageFiles(ArrayList<PostChapter> postChapters) {
        HashSet<String> createdChapters = new HashSet<String>();
        createHtmlOutputFolderIfNotExists();
        for(PostChapter postChapter : postChapters) {
            if (!postChapter.getBookName().isEmpty() && !createdChapters.contains(postChapter.getBookName())) {
                createChapterPageFile(postChapter.getBookName(), ChapterType.BOOK);
                createdChapters.add(postChapter.getBookName());
            }
            if (!postChapter.getSequenceName().isEmpty() && !createdChapters.contains(postChapter.getSequenceName())) {
                createChapterPageFile(postChapter.getSequenceName(), ChapterType.SEQUENCE);
                createdChapters.add(postChapter.getSequenceName());
            }
        }
    }
    private void createChapterPageFile(String chapterTitle, ChapterType chapterType) {
        String linkTitle = chapterTitle.replaceAll("\\W+", "");
        StringBuilder chapterPageSB = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        chapterPageSB.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">").append(newLine);
        chapterPageSB.append("<html>").append(newLine);
        chapterPageSB.append("\t<head>").append(newLine);
        chapterPageSB.append("\t</head>").append(newLine);
        chapterPageSB.append("<body>").append(newLine);
        chapterPageSB.append("<div style=\"text-align: center;\">").append(newLine);
        chapterPageSB.append("<h1>").append(chapterTitle).append("</h1>").append(newLine);
        chapterPageSB.append("<img src=\"seperator.gif\">").append(newLine);
        chapterPageSB.append("</div>").append(newLine);
        chapterPageSB.append("<a href=\"summaryTOC.html#").append(linkTitle);
        if (chapterType == ChapterType.BOOK) {
           chapterPageSB .append("_book_summary\">Go To Summary</a>").append(newLine);
        } else if (chapterType == ChapterType.SEQUENCE) {
           chapterPageSB .append("_sequence_summary\">Go To Summary</a>").append(newLine);
        }
        chapterPageSB.append("</body>").append(newLine).append("</html>");
        writeStringToFile(htmlOutputFilePath + "//" + linkTitle + ".html", chapterPageSB.toString());
    }
}
