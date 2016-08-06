package cs275.langfinder.data;

public class UserConnectionBundle {
	UserConnection userConnection;
	UserProfile user2Profile;
	User user;

	public UserConnection getUserConnection() {
		return userConnection;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setUserConnection(UserConnection userConnection) {
		this.userConnection = userConnection;
	}
	public UserProfile getUser2Profile() {
		return user2Profile;
	}
	public void setUser2Profile(UserProfile user2Profile) {
		this.user2Profile = user2Profile;
	}
	public UserConnectionBundle() {
		super();
	}
	public UserConnectionBundle(UserConnection userConnection,
			UserProfile user2Profile, User user) {
		super();
		this.userConnection = userConnection;
		this.user2Profile = user2Profile;
		this.user = user;
	}
}
