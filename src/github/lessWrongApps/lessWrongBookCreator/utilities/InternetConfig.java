package github.lessWrongApps.lessWrongBookCreator.utilities;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.List;

import com.btr.proxy.search.ProxySearch;
import com.btr.proxy.search.ProxySearch.Strategy;

/**
 * Class used for checking internet connection works and configuring the default proxy to use
 */
public class InternetConfig {
    /**
     * Pings http://google.com to check if internet connection works
     * @return true if response is 200 otherwise return false
     */
    public boolean isInternetConnectionWorking() {
        String strUrl = "http://google.com";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();

            if (HttpURLConnection.HTTP_OK != urlConn.getResponseCode()) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    /**
     * Sets the system to use the default proxy settings
     */
    public void setProxySettings() {
        ProxySearch proxySearch = new ProxySearch();
        proxySearch.addStrategy(Strategy.OS_DEFAULT); 
        proxySearch.addStrategy(Strategy.JAVA); 
        proxySearch.addStrategy(Strategy.BROWSER); 
        ProxySelector proxySelector = proxySearch.getProxySelector(); 

        ProxySelector.setDefault(proxySelector); 
        URI home = URI.create("http://www.google.com"); 

        List<Proxy> proxyList = proxySelector.select(home); 
        if (proxyList != null && !proxyList.isEmpty()) { 
            for (Proxy proxy : proxyList) { 
                SocketAddress address = proxy.address(); 
                if (address instanceof InetSocketAddress) { 
                    String host = ((InetSocketAddress) address).getHostName(); 
                    String port = Integer.toString(((InetSocketAddress) address).getPort()); 
                    System.setProperty("http.proxyHost", host); 
                    System.setProperty("http.proxyPort", port); 
                } 
            } 
        }
    }
}
