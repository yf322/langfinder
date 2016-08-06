package cs275.langfinder.net;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class CookieManager {
	static CookieManager defaultCookieManager = new CookieManager();

	CookieStore cookieStore = new BasicCookieStore();

	public HttpContext getContextForCookies() {
	    HttpContext localContext = new BasicHttpContext();
	    // Bind custom cookie store to the local context
	    localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	    return localContext;
	}

	public static CookieManager getDefaultCookieManager() {
		return defaultCookieManager;
	}

}
