package cs275.langfinder.net;

import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

// Simple get retriever to be used with GET based rest requests
public class HttpPostRetriever implements HttpRetriever {
	String uri;
	String postString;
	boolean useCookies;
	Map<String, String> headers;

	public String retrieveString() {
		try {
			// Set up headers
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);
            for(String headerName : headers.keySet()) {
                httpGet.addHeader(headerName, headers.get(headerName));
            }


			HttpPost method = new HttpPost(uri);
	        for(String headerName : headers.keySet()) {
				method.addHeader(headerName, headers.get(headerName));
	        }

	        StringEntity requestEntity = new StringEntity(
        		postString,
	        	"application/x-www-form-urlencoded",
	        	"utf-8"
	        );

	        method.setEntity(requestEntity);

	        CloseableHttpResponse response1;

            if(useCookies) {
            	response1 = httpclient.execute(method, CookieManager.getDefaultCookieManager().getContextForCookies());
            } else {
            	response1 = httpclient.execute(method);
            }

            // Get the response
            HttpEntity entity1 = response1.getEntity();

			StringBuilder result = new StringBuilder();
			InputStream inputStream = entity1.getContent();

			byte[] readBytes = new byte[1024];
			int numBytesRead;
			while((numBytesRead = inputStream.read(readBytes))!=-1) {
				
				result.append(new String(readBytes,0,numBytesRead));
			}
			
	        String responseBody = result.toString();

	        return responseBody;

		} catch(Exception ex) {
			throw new RuntimeException("Unable to retrieve data: " + ex.getMessage(), ex);
		}
	}



	public HttpPostRetriever(String uri, String postString, Map<String, String> headers, boolean useCookies) {
		this.uri = uri;
		this.postString = postString;
		this.headers = headers;
		this.useCookies = useCookies;
	}
}
