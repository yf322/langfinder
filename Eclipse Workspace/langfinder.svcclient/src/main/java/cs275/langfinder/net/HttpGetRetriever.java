package cs275.langfinder.net;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

// Simple get retriever to be used with GET based rest requests
public class HttpGetRetriever implements HttpRetriever {
    String uri;
    Map<String, String> headers = new HashMap<String, String>();
    boolean useCookies;

    // Headers may be set via this map
    public Map<String, String> getHeaders() {
        return headers;
    }

    public String retrieveString() {
        try {
            // Set up headers
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);
            for(String headerName : headers.keySet()) {
                httpGet.addHeader(headerName, headers.get(headerName));
            }
            
            // Execute the call
            CloseableHttpResponse response1;

            if(useCookies) {
            	response1 = httpclient.execute(httpGet, CookieManager.getDefaultCookieManager().getContextForCookies());
            } else {
            	response1 = httpclient.execute(httpGet);
            }

            // Get the response
            HttpEntity entity1 = response1.getEntity();

            StringBuilder result = new StringBuilder();
            InputStream inputStream = entity1.getContent();

            // Read from stream
            byte[] readBytes = new byte[1024];
            int numBytesRead;
            while((numBytesRead = inputStream.read(readBytes))!=-1) {

                result.append(new String(readBytes,0,numBytesRead));
            }

            // Convert to string
            String responseBody = result.toString();

            // return it
            return responseBody;

        } catch(Exception ex) {
            throw new RuntimeException("Unable to retrieve data: " + ex.getMessage(), ex);
        }
    }

    public HttpGetRetriever(String uri) {
        this.uri = uri;
    }

    public HttpGetRetriever(String uri, Map<String, String> headers, boolean useCookies) {
        this(uri);
        this.headers = headers;
        this.useCookies = useCookies;
    }

}
