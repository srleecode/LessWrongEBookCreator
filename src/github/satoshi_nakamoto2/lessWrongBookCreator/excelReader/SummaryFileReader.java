package github.satoshi_nakamoto2.lessWrongBookCreator.excelReader;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.BookSummarySection;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.ExcelExtractionException;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.PostSummarySection;
import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.SequenceSummarySection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads excel files and extracts list of {@link BookSummarySection}s
 */
public final class SummaryFileReader {
    private static final Logger logger = LoggerFactory.getLogger(SummaryFileReader.class);
    private final String newLine = System.getProperty("line.separator");
    /**
     * Builds list of {@link BookSummarySection}s from a given excel file
     * @param file to use to extract the BookSummarySections
     * @return bookSummarySections
     * @throws ExcelExtractionException if unable to read from the given file
     */
    public ArrayList<BookSummarySection> getSummarySections(File file) throws ExcelExtractionException {
        if (file == null) {
            throw new ExcelExtractionException("Input file was null and cannot be read");
        } else {
            Workbook wb = null;
            ArrayList<BookSummarySection> bookSummarySections = null;
            try {
                wb = WorkbookFactory.create(file);
                bookSummarySections = getBookSummarySections(wb);
            } catch (FileNotFoundException e) {  
                logger.error("",e);
                throw new ExcelExtractionException(file.getName() + " not found");
             } catch (IOException e) {   
                logger.error("",e);
                throw new ExcelExtractionException("IOException reading from " + file.getName());
             } catch (EncryptedDocumentException e) {
                logger.error("",e);
                throw new ExcelExtractionException("EncryptedDocumentException reading from " + file.getName());
            } catch (InvalidFormatException e) {
                logger.error("",e);
                throw new ExcelExtractionException("InvalidFormatException reading from " + file.getName());
            } finally {
                if (wb != null) {
                    try {
                        wb.close();
                    } catch (IOException ex) {
                       logger.error("",ex);
                        throw new ExcelExtractionException("IOException with " + file.getName() +". If the file is open, close it.");
                    } 
                }
            }

            return bookSummarySections;
        }
    }
    private ArrayList<BookSummarySection> getBookSummarySections(Workbook wb) {
        ArrayList<BookSummarySection> bookSummarySections = new ArrayList<BookSummarySection>();
        HashSet<String> bookNames = new HashSet<String>();
        HashSet<String> sequenceNames = new HashSet<String>();
        Sheet postsSheet = wb.getSheetAt(0);
        Iterator<Row> rowIterator = postsSheet.iterator();  
        String currBook = "";
        String currSequence = "";
        if (rowIterator.hasNext()) {
            rowIterator.next(); // skip first row with column headers

            while(rowIterator.hasNext()) {         
                Row row = rowIterator.next();       

                Iterator<Cell> cellIterator = row.cellIterator();   
                int column = 0;
                // increment the column we are looking for the value from if the book, sequence or title are not provided
                column += Math.abs(row.getPhysicalNumberOfCells()-row.getLastCellNum());
                PostSummarySection postSummarySection = new PostSummarySection();
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    column++;
                    if(cell.getCellType() == Cell.CELL_TYPE_STRING) {  
                        switch (column) {
                            case 1:
                                currBook = cell.getStringCellValue();
                                break;
                            case 2:
                                currSequence = cell.getStringCellValue();
                                break;
                            case 3:
                                postSummarySection.setTitle(cell.getStringCellValue());
                                break;
                            case 4:
                                postSummarySection.setUrl(cell.getStringCellValue());
                                break;
                            case 5:
                                postSummarySection.setSummary(cell.getStringCellValue());
                                break;
                        }
                    }
                }
                if (!bookNames.contains(currBook)) {
                    BookSummarySection bookSummarySection = new BookSummarySection(currBook);
                    bookSummarySections.add(bookSummarySection);
                    bookNames.add(currBook);
                }
                if (sequenceNames.contains(currSequence)) {
                    for (BookSummarySection bookSummarySection : bookSummarySections) {
                        SequenceSummarySection sequenceSummarySection = bookSummarySection.getSequenceSummarySection(currSequence);

                        if (sequenceSummarySection != null) {
                            if (!postSummarySection.getUrl().isEmpty()) {
                                sequenceSummarySection.addPostSummarySection(postSummarySection);
                            }
                        }
                    }
                } else {
                    if (!postSummarySection.getUrl().isEmpty()) {
                        SequenceSummarySection sequenceSummarySection = new SequenceSummarySection(currSequence);

                        sequenceSummarySection.addPostSummarySection(postSummarySection);
                        for (BookSummarySection bookSummarySection : bookSummarySections) {
                            if (bookSummarySection.getTitle().equals(currBook)) {
                                bookSummarySection.addSequenceSummarySection(sequenceSummarySection);
                            }
                        }
                        sequenceNames.add(currSequence);
                    }
                }
            }  
            HashMap<String, String> sequenceTitleAndSummaries = new HashMap<String, String>();
            HashMap<String, String> bookTitlesAndSummaries = new HashMap<String, String>();
            if (wb.getNumberOfSheets() == 1) {
                logger.info("There is no second sheet or third sheet found. Therefore, there are no sequence or book summaries found. Perhaps, the excel file is not in the proper format." + newLine);
            } else if (wb.getNumberOfSheets() == 2){
                logger.info("There is no third sheet found. Therefore, there are no book summaries found. Perhaps, the excel file is not in the proper format." + newLine);
                sequenceTitleAndSummaries = getTitlesAndSummaries(wb.getSheetAt(1));
            } else {
                sequenceTitleAndSummaries = getTitlesAndSummaries(wb.getSheetAt(1));
                bookTitlesAndSummaries = getTitlesAndSummaries(wb.getSheetAt(2));
            }

            for (BookSummarySection bookSummarySection : bookSummarySections) {
                String bookSummary = bookTitlesAndSummaries.get(bookSummarySection.getTitle());
                if (bookSummary != null) {
                    bookSummarySection.setSummary(bookSummary);
                }
                for (SequenceSummarySection sequenceSummarySection : bookSummarySection.getSequenceSummarySections()) {
                    String sequenceSummary = sequenceTitleAndSummaries.get(sequenceSummarySection.getTitle());
                    if (sequenceSummary != null) {
                        sequenceSummarySection.setSummary(sequenceSummary);
                    }
                }
            }
        } else {
            logger.info("There were no rows found in the first sheet. Therefore, no posts were found. Perhaps, the excel file is not in the proper format" + newLine);
        }
        return bookSummarySections;
    }
    private HashMap<String, String> getTitlesAndSummaries(Sheet sheet) {
        if (sheet == null) {
            return new HashMap<String, String>();
        }
        HashMap<String, String> contentSummaries = new HashMap<String, String>();
        Iterator<Row> rowIterator = sheet.iterator();  
        if (rowIterator.hasNext()) {
            rowIterator.next(); // skip first row with column headers

            while(rowIterator.hasNext()) {         
                Row row = rowIterator.next();       
                //For each row, iterate through each of the columns    
                Iterator<Cell> cellIterator = row.cellIterator();   

                int column = 0;
                String key = "";
                String value = "";
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    column++;
                    if(cell.getCellType() == Cell.CELL_TYPE_STRING) {  
                        switch (column) {
                            case 1:
                                key = cell.getStringCellValue();
                                break;
                            case 2:
                                value = cell.getStringCellValue();
                                break;
                        }
                    }
                }
                if (!key.isEmpty() && !value.isEmpty()) {
                    contentSummaries.put(key, value);
                }
            }   
        } else {
            
        }
        return contentSummaries;
    }	
}
