package github.srlee309.lessWrongBookCreator.scraper;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
     * Saves all images in the given Element to a local newUrl and converts the src for all img tags to the local file
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
     * @param fileName to use for the saved image
     */
    protected final void saveImage(String src, String folder, String fileName) {
        if (fileName.contains("?")) {
            fileName = fileName.substring(0, fileName.lastIndexOf("?"));
        }
        
        fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_"); // replace non valid file fileName characters
        File outputFile = new File(folder +"\\" +fileName);
        try {
            URL url = new URL(src);

            HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
            connection.setRequestMethod("HEAD");
            connection.setInstanceFollowRedirects(false);
            int rspCode = connection.getResponseCode();
            if (rspCode == 301){ // redirected, so get new url
                String newUrl = connection.getHeaderField( "Location" );
                url = new URL(newUrl);
            }
            connection.disconnect();
            FileUtils.copyURLToFile(url, outputFile);
        } catch (MalformedURLException e) {
            logger.error("Malformed url exception for image src:  " + src, e);
        } catch (IOException e) {
            logger.error("IO exception for saving image src locally:  " + src, e);
        } 
    }
public boolean downloadFileFromURL(String fetchUrl, File outputFile)  
throws IOException,FileNotFoundException,IOException {  
      
    HttpURLConnection c;  
      
    //save file       
    URL url = new URL(fetchUrl);  
    c = (HttpURLConnection)url.openConnection();  
      
    //set cache and request method settings  
    c.setUseCaches(false);  
    c.setDoOutput(false);  
      
    //set other headers  
    c.setRequestProperty ("Content-Type", "image/jpeg");  
     System.out.println( c.getContentType());
    //connect  
    c.connect();  
      
    BufferedInputStream in = new BufferedInputStream(c.getInputStream());  
  
    OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));  
    byte[] buf = new byte[256];  
    int n = 0;  
    while ((n=in.read(buf))>=0) {  
       out.write(buf, 0, n);  
    }  
    out.flush();  
    out.close();  
     
    return true;          
}  

	
}
