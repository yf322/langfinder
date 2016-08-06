package cs275.langfinder.svcclient;

import java.util.List;

import cs275.langfinder.data.Language;


public class App 
{

	public static void testLanguages() {
		String serviceRoot = "http://www3.twoclicksys.net/langfinder.svc";
		TypeController controller = new TypeController(serviceRoot);
		Language[] languages = controller.getLanguages();
		for(Language language: languages) {
			System.out.println("Language: " + language.getName());
		}
	}
	public static void main(String[] args) {
		testUser();
	}

	public static void testUser() {
		String serviceRoot = "http://www3.twoclicksys.net/langfinder.svc";

		UserController controller = new UserController(serviceRoot);
		
//		Integer userId = controller.login("jason@jasoncotton.com", "password");
        Integer userId = controller.login("jjc43@drexel.edu", "password");

		System.out.println("userId: " + userId);
		
		userId = controller.getCurrentUserId();

		System.out.println("Subsequent userId: " + userId);
		
		
	}
}
