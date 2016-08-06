package cs275.langfinder.data;

public class UserProfile {
	Integer id;
	Integer userId;
	String aboutText;
	String lookingForText;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAboutText() {
		return aboutText;
	}
	public void setAboutText(String aboutText) {
		this.aboutText = aboutText;
	}
	public String getLookingForText() {
		return lookingForText;
	}
	public void setLookingForText(String lookingForText) {
		this.lookingForText = lookingForText;
	}
	public UserProfile() {
		super();
	}
	public UserProfile(Integer id, Integer userId, String aboutText,
			String lookingForText) {
		super();
		this.id = id;
		this.userId = userId;
		this.aboutText = aboutText;
		this.lookingForText = lookingForText;
	}
}
