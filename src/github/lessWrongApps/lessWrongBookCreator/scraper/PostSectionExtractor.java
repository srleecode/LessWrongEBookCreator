package github.lessWrongApps.lessWrongBookCreator.scraper;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class to define common code for extracting post sections from websites
 */
public abstract class PostSectionExtractor {
    protected static final Logger logger = LoggerFactory.getLogger(PostSectionExtractor.class);
    protected  final String newLine = System.getProperty("line.separator");
    public abstract String getPostSectionString(PostExtractionDetails postExtractionDetails, Document doc); 
    /**
     * Saves all images in the given Element to a local location and converts the src for all img tags to the local file
     * @param postContent - from which to extract the images
    */
    protected final void convertImagesToLocal(Element postContent)  {
        Elements imgs = postContent.getElementsByTag("img");
        for (Element img : imgs) {
            String src = img.absUrl("src");
            String folder = "htmlOutput";

            int indexName = src.lastIndexOf("/");
            String name = src;
            if (indexName != -1) {
                indexName = src.lastIndexOf("/") + 1;
                name = src.substring(indexName, src.length());
            }
            img.attr("src", name);
            saveImage(src, folder, name);
        }
    }
    /**
     * @param src of image to download and save
     * @param folder to which to save the image
     * @param name to use for the saved image
     */
    protected final void saveImage(String src, String folder, String name) {
        InputStream in = null;
        OutputStream out = null;
        name = name.replaceAll("[^a-zA-Z0-9.-]", "_"); // replace non valid file name characters
        try {
            URL url = new URL(src);
            in = url.openStream();
            out = new BufferedOutputStream(new FileOutputStream(folder +"\\" +name));

            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
        } catch (MalformedURLException e) {
            logger.error("Malformed url exception for image src:  " + src, e);
        } catch (IOException e) {
            logger.error("IO exception for saving image src locally:  " + src, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("IO exception when closing saved image src locally:  " + src, e);
            }
        }
    }
	
}
