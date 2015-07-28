package github.lessWrongApps.lessWrongBookCreator.utilities;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Simple helper class to remove all files and sub files in a given directory
 */
public final class DirectoryPurger {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryPurger.class);
    /**
     * Removes all files and subFiles in the given directory
     * @param dir to purge
     */
    public void purgeDirectory(File dir) {
        if (dir != null && dir.isDirectory()) {
            try {
                FileUtils.cleanDirectory(dir);
            } catch (IOException ex) {
                logger.error("Unable to remove folder " + dir.getName(), ex);
            }
        }
    }
}
