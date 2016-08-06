package cs275.langfinder.test;

import cs275.langfinder.data.User;
import cs275.langfinder.data.factory.UserController;

public class Tester {
	
	public static void main() {
		UserController userController = new UserController();
		User user = userController.getUser(1);
		System.out.println("User.name: " + user.getFirstName());
	}
}
