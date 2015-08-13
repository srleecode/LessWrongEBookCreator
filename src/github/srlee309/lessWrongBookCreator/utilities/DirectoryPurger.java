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
package github.srlee309.lessWrongBookCreator.utilities;

import java.io.File;
import java.io.IOException;
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
     * @throws IOException if dir cannot be cleaned
     */
    public void purgeDirectory(File dir) throws IOException {
        if (dir != null && dir.isDirectory()) {
            FileUtils.cleanDirectory(dir);
        }
    }
}
