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
package github.srlee309.lessWrongBookCreator.bookGeneration;
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
            FileUtils.writeStringToFile(new File(filePath), fileContent, "UTF-8");
        } catch ( IOException ex) {
            logger.error("", ex);
        }
    }
}
