package github.satoshi_nakamoto2.lessWrongBookCreator.utilities;

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