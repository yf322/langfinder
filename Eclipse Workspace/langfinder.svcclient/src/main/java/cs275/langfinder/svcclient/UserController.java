package cs275.langfinder.svcclient;

import cs275.langfinder.data.User;
import cs275.langfinder.data.UserConnectionBundle;
import cs275.langfinder.data.UserLanguageBundle;
import cs275.langfinder.data.UserProfileBundle;
import cs275.langfinder.net.LangfinderServiceCaller;

public class UserController extends ServiceController {

	public UserController(String serviceRoot) {
		super(serviceRoot);
	}

	public Integer login(String email, String passwordHash) {
		LangfinderServiceCaller<Integer> service = new LangfinderServiceCaller<Integer>(Integer.class, serviceRoot, "login.jsp");
		service.addParameter("email", email);
		service.addParameter("passwordHash", passwordHash);
		return service.executeGet();
	}

	public Integer getCurrentUserId() {
		LangfinderServiceCaller<Integer> service = new LangfinderServiceCaller<Integer>(Integer.class, serviceRoot, "getCurrentUserId.jsp");
		return service.executeGet();
	}


	public UserProfileBundle[] searchUsers(String name, Integer languageInfoId, Integer languageLevelId) {
		LangfinderServiceCaller<UserProfileBundle[]> service = new LangfinderServiceCaller<UserProfileBundle[]>(UserProfileBundle[].class, serviceRoot, "searchUsers.jsp");
		service.addParameter("name", name);
		service.addParameter("languageInfoId", languageInfoId);
		service.addParameter("languageLevelId", languageLevelId);
		return service.executeGet();
	}

	public UserProfileBundle getUserWithProfile(int userId) {
		LangfinderServiceCaller<UserProfileBundle> service = new LangfinderServiceCaller<UserProfileBundle>(UserProfileBundle.class, serviceRoot, "getUserWithProfile.jsp");
		service.addParameter("userId", userId);
		return service.executeGet();
	}

	public boolean getUsersAreFriends(int fromUserId, int otherUserId) {
		LangfinderServiceCaller<Boolean> service = new LangfinderServiceCaller<Boolean>(Boolean.class, serviceRoot, "getUsersAreFriends.jsp");
		service.addParameter("fromUserId", fromUserId);
		service.addParameter("otherUserId", otherUserId);
		return service.executeGet();
	}

	public void removeFriend(int fromUserId, int otherUserId) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "removeFriend.jsp");
		service.addParameter("fromUserId", fromUserId);
		service.addParameter("otherUserId", otherUserId);
		service.executeGet();
	}

	public UserConnectionBundle[] getUserInviteProfiles(int userId) {
		LangfinderServiceCaller<UserConnectionBundle[]> service = new LangfinderServiceCaller<UserConnectionBundle[]>(UserConnectionBundle[].class, serviceRoot, "getUserInviteProfiles.jsp");
		service.addParameter("userId", userId);
		return service.executeGet();
	}

	public UserConnectionBundle[] getUserConnectionProfiles(int userId) {
		LangfinderServiceCaller<UserConnectionBundle[]> service = new LangfinderServiceCaller<UserConnectionBundle[]>(UserConnectionBundle[].class, serviceRoot, "getUserConnectionProfiles.jsp");
		service.addParameter("userId", userId);
		return service.executeGet();
	}


	public void inviteUser(int fromUserId, int toUserId, String inviteMessage) {
		LangfinderServiceCaller<UserProfileBundle[]> service = new LangfinderServiceCaller<UserProfileBundle[]>(UserProfileBundle[].class, serviceRoot, "inviteUser.jsp");
		service.addParameter("fromUserId", fromUserId);
		service.addParameter("toUserId", toUserId);
		service.addParameter("inviteMessage", inviteMessage);
		service.executeGet();
	}

	public void updateInvite(int fromUserId, int toUserId, boolean confirmed, boolean ignored) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "updateInvite.jsp");
		service.addParameter("fromUserId", fromUserId);
		service.addParameter("toUserId", toUserId);
		service.addParameter("confirmed", confirmed);
		service.addParameter("ignored", ignored);
		service.executeGet();
	}

	public void updateProfile(int userId, String aboutText, String lookingFor) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "updateProfile.jsp");
		service.addParameter("userId", userId);
		service.addParameter("aboutText", aboutText);
		service.addParameter("lookingFor", lookingFor);
		service.executeGet();
	}

	public UserLanguageBundle[] getUserLanguages(int userId) {
		LangfinderServiceCaller<UserLanguageBundle[]> service = new LangfinderServiceCaller<UserLanguageBundle[]>(UserLanguageBundle[].class, serviceRoot, "getUserLanguages.jsp");
		service.addParameter("userId", userId);
		return service.executeGet();
	}

	public UserLanguageBundle getUserLanguage(int userId, int lanuageInfoId) {
		LangfinderServiceCaller<UserLanguageBundle> service = new LangfinderServiceCaller<UserLanguageBundle>(UserLanguageBundle.class, serviceRoot, "getUserLanguage.jsp");
		service.addParameter("userId", userId);
		service.addParameter("lanuageInfoId", lanuageInfoId);
		return service.executeGet();
	}

	public void deleteUserLanguage(int userId, int languageInfoId) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "deleteUserLanguage.jsp");
		service.addParameter("userId", userId);
		service.addParameter("languageInfoId", languageInfoId);
		service.executeGet();
	}

	public void updateUserLanguage(int userId, int languageInfoId, boolean wantsToLearn, boolean isNative, int languageLevelId) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "updateUserLanguage.jsp");
		service.addParameter("userId", userId);
		service.addParameter("languageInfoId", languageInfoId);
		service.addParameter("wantsToLearn", wantsToLearn);
		service.addParameter("isNative", isNative);
		service.addParameter("languageLevelId", languageLevelId);
		service.executeGet();
	}

	public void createUserLanguage(int userId, int languageId, boolean wantsToLearn, boolean isNative, int languageLevelId) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "createUserLanguage.jsp");
		service.addParameter("userId", userId);
		service.addParameter("languageId", languageId);
		service.addParameter("wantsToLearn", wantsToLearn);
		service.addParameter("isNative", isNative);
		service.addParameter("languageLevelId", languageLevelId);
		service.executeGet();
	}

	public User getUser(int userId) {
		LangfinderServiceCaller<User> service = new LangfinderServiceCaller<User>(User.class, serviceRoot, "getUser.jsp");
		service.addParameter("id", userId);
		return service.executeGet();
	}
	
	public Integer createUser(String email, String passwordHash, String firstName, String lastName) {
		LangfinderServiceCaller<Integer> service = new LangfinderServiceCaller<Integer>(Integer.class, serviceRoot, "createUser.jsp");
		service.addParameter("email", email);
		service.addParameter("passwordHash", passwordHash);
		service.addParameter("firstName", firstName);
		service.addParameter("lastName", lastName);
		return service.executeGet();
	}

	public static void main(String[] args) {
		UserController userController = new UserController("http://www3.twoclicksys.net/langfinder.svc");
		User user = userController.getUser(1);
		System.out.println("User Id: " + user.getId());
		System.out.println("User First Name: " + user.getFirstName());
		System.out.println("User Last Name: " + user.getLastName());
	}
}
