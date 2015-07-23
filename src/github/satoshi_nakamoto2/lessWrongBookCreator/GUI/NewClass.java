/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package github.satoshi_nakamoto2.lessWrongBookCreator.GUI;

import github.satoshi_nakamoto2.lessWrongBookCreator.scraper.LessWrongCommentSection;
import github.satoshi_nakamoto2.lessWrongBookCreator.utilities.InternetConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author LeeSc
 */
public class NewClass {
    public static void main(String [] args)
    {
        try {
            InternetConfig netChecker = new InternetConfig();
            if (!netChecker.isInternetConnectionWorking()){
                netChecker.setProxySettings();
            }
            netChecker.isInternetConnectionWorking();
            Connection.Response rsp = Jsoup.connect("http://www.overcomingbias.com/2015/07/effective-altruism-complaints.html").timeout(30*1000).ignoreContentType(true).maxBodySize(100*1024*1024).ignoreHttpErrors(true).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0").referrer("http://www.google.com").followRedirects(true).execute(); 
            Document doc = rsp.parse();
            doc.select("div.gdsrcacheloader").remove();
            Elements elements = doc.select("div.entry-content");
            for (Element e : elements) {
                System.out.println(e.outerHtml());
            }
            elements = doc.select("li.comment");
            ArrayList<LessWrongCommentSection> comments = new ArrayList<LessWrongCommentSection>();
            Pattern digitPattern = Pattern.compile("\\d+");
            Matcher matcher = null;
            String parentId = "";
            Integer depth = 0;
            String id = "";
            for (Element e : elements) {
                String [] lines = e.outerHtml().split("\n");
                if (lines.length > 1) {
                    matcher = digitPattern.matcher(lines[0]);
                    if (matcher.find()) {
                        depth = Integer.parseInt(matcher.group(0));
                    }
                    matcher = digitPattern.matcher(lines[1]);
                    if (matcher.find()) {
                        id = matcher.group(0);
                    }
                    //unsure how to get votes from Overcoming Bias looks like it is java script generated. Therefore setting to 99999 so they all get picked up.
                    if (depth > 1) {
                        comments.add(new LessWrongCommentSection(id, "", "", "", 99999, e.outerHtml(), parentId));
                    } else {
                        parentId = id;
                        comments.add(new LessWrongCommentSection(id, "", "", "", 99999, e.outerHtml(), ""));
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
