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

import javax.swing.JTextArea;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
/**
 * Log4j appender that writes its output to a JTextArea
 */
public final class LogTextAreaAppender extends AppenderSkeleton  {
    private JTextArea logTextArea = null;

    public LogTextAreaAppender(final JTextArea inputTextArea) {
        logTextArea = inputTextArea;
    }
    @Override
    protected void append(LoggingEvent event) 
    {
        if(logTextArea != null){
            logTextArea.append(event.getMessage().toString());
        }
    }
    @Override
    public void close() 
    {
    }
    @Override
    public boolean requiresLayout() 
    {
        return false;
    }
}