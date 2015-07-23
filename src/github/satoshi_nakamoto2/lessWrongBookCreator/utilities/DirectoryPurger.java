package github.satoshi_nakamoto2.lessWrongBookCreator.utilities;

import java.io.File;
/**
 * Simple helper class to remove all files and sub files in a given directory
 */
public final class DirectoryPurger {
    /**
     * Removes all files and subFiles in the given directory
     * @param dir to purge
     */
    public void purgeDirectory(File dir) {
        if (dir != null) {
            for (File file: dir.listFiles()) {
                if (file.isDirectory()) {
                        purgeDirectory(file);
                } 
                file.delete();
            }
        }
    }
}
