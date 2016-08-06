package cs275.langfinder.data;

public class UserProfileBundle {
	User user;
	UserProfile userProfile;
	UserLanguage[] languages;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	public UserLanguage[] getLanguages() {
		return languages;
	}
	public void setLanguages(UserLanguage[] languages) {
		this.languages = languages;
	}
	public UserProfileBundle() {
		super();
	}
	public UserProfileBundle(User user, UserProfile userProfile,
			UserLanguage[] languages) {
		super();
		this.user = user;
		this.userProfile = userProfile;
		this.languages = languages;
	}
}
