package github.lessWrongApps.lessWrongBookCreator.bookGeneration;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class containing common methods for manipulating files in the htmlOutput folder in the current directory
 */
public abstract class HtmlOutputFileCreator {
    private static final Logger logger = LoggerFactory.getLogger(HtmlOutputFileCreator.class);
    protected final String htmlOutputFilePath;
    public HtmlOutputFileCreator() {
        htmlOutputFilePath = new File(".").getAbsolutePath()+"//htmlOutput";
    }
    /**
     * Check that the htmlOutput folder exists in the current directory and create it if it doesn't
     */
    protected void createHtmlOutputFolderIfNotExists() {
        File htmlOutputFolder = new File(new File(".").getAbsolutePath()+"//htmlOutput");
        if (!htmlOutputFolder.exists()) {
            logger.info("htmlOutput folder not found. Creating it.");
            try{
                htmlOutputFolder.mkdir();
            } 
            catch(SecurityException se){
                logger.error("Unable to create htmlOutput folder", se);
            }     
        } 
    }
    protected void writeStringToFile(String filePath, String fileContent) {
        try {
            FileUtils.writeStringToFile(new File(filePath), fileContent);
        } catch ( IOException ex) {
            logger.error("", ex);
        }
    }
}
