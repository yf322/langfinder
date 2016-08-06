package cs275.langfinder.net;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LangfinderServiceCaller<ReturnType> {
	String serviceRoot;
	String jspName;
	Class<ReturnType> type;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	Map<String, String> parameters = new HashMap<String, String>();

	public void addParameter(String name, String value) {
		parameters.put(name, value);
	}

	public void addParameter(String name, Integer value) {
		if(value==null)
			parameters.put(name, null);
		else
			parameters.put(name, "" + value);
	}

	public void addParameter(String name, boolean value) {
		parameters.put(name, "" + value);
	}

	protected String getParameterString() {
		StringBuilder parameterString = new StringBuilder();
		parameters.put("timestamp", dateFormat.format(new Date()));

		for(String key : parameters.keySet()) {
			if(parameterString.length()>0)
				parameterString.append("&");

			parameterString.append(key);
			parameterString.append("=");
			if(parameters.get(key)!=null) {
				parameterString.append(URLEncoder.encode(parameters.get(key)));
			}
		}

		return parameterString.toString();
	}

	public ReturnType executeGet() {
		String serviceUri = serviceRoot + "/" + jspName + "?" + getParameterString();
		Map<String, String> headers = new HashMap<String, String>();
		HttpGetRetriever retriever = new HttpGetRetriever(serviceUri, headers, true);
		String json = retriever.retrieveString();
		Gson gson = new GsonBuilder()
		   .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
		ReturnType value = gson.fromJson(json, type);
		return value;
	}

	public ReturnType executePost() {
		String serviceUri = serviceRoot + "/" + jspName;
		Map<String, String> headers = new HashMap<String, String>();
		HttpPostRetriever retriever = new HttpPostRetriever(serviceUri, getParameterString(), headers, true);
		String json = retriever.retrieveString();
		Gson gson = new GsonBuilder()
		   .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
		ReturnType value = gson.fromJson(json, type);
		return value;
	}

	public LangfinderServiceCaller(Class<ReturnType> type, String serviceRoot, String jspName) {
		super();
		this.type = type;
		this.serviceRoot = serviceRoot;
		this.jspName = jspName;
	}
}
