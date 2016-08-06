package cs275.langfinder;

import cs275.langfinder.svcclient.UserController;

public class TestService {
    public static void main(String[] args) {
        String serviceRoot = "http://localhost:8080";

        UserController controller = new UserController(serviceRoot);

        Integer userId = controller.login("jjc43@drexel.edu", "password");

        System.out.println("userId: " + userId);

        userId = controller.getCurrentUserId();

        System.out.println("Subsequent userId: " + userId);
    }
}
