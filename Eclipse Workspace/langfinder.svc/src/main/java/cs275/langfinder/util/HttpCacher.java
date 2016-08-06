package cs275.langfinder.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpCacher {
	protected static class HttpContext {
		HttpSession session;
		HttpServletRequest request;
		HttpServletResponse response;
		public HttpSession getSession() {
			return session;
		}
		public HttpServletRequest getRequest() {
			return request;
		}
		public HttpServletResponse getResponse() {
			return response;
		}
		public HttpContext(HttpSession session, HttpServletRequest request,
				HttpServletResponse response) {
			super();
			this.session = session;
			this.request = request;
			this.response = response;
		}
	}
	static Map<Thread, HttpContext> contexts = new HashMap<Thread, HttpCacher.HttpContext>();

	protected static synchronized HttpContext getContext() {
		return contexts.get(Thread.currentThread());
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz");

	public synchronized static void setup(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add( Calendar.DATE, -1 );
        
		HttpContext context = new HttpContext(session, request, response);
		contexts.put(Thread.currentThread(), context);
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Expires", sdf.format(c.getTime()));
		response.addHeader("Pragma", "no-cache");

	}

	public static HttpServletRequest getRequest() {
		return getContext().getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		return getContext().getResponse();
	}

	public static HttpSession getSession() {
		return getContext().getSession();
	}
}
