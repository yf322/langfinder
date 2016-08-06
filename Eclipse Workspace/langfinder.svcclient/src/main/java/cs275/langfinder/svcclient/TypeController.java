package cs275.langfinder.svcclient;

import cs275.langfinder.data.Language;
import cs275.langfinder.data.LanguageLevel;
import cs275.langfinder.net.LangfinderServiceCaller;

public class TypeController extends ServiceController {
	public Language[] getLanguages() {
		LangfinderServiceCaller<Language[]> service = new LangfinderServiceCaller<Language[]>(Language[].class, serviceRoot, "getLanguages.jsp");
		return service.executeGet();
	}
	
	public LanguageLevel[] getLanguageLevels() {
		LangfinderServiceCaller<LanguageLevel[]> service = new LangfinderServiceCaller<LanguageLevel[]>(LanguageLevel[].class, serviceRoot, "getLanguageLevels.jsp");
		return service.executeGet();
	}

	public TypeController(String serviceRoot) {
		super(serviceRoot);
	}
}