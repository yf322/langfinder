package cs275.langfinder.data;

public class UserLanguage {
	Integer id;
	Integer userId;
	Integer languageId;
	boolean wantsToLearn;
	boolean isNative;
	Integer languageLevelId;

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
	
	public Integer getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	public boolean getWantsToLearn() {
		return wantsToLearn;
	}
	public void setWantsToLearn(boolean wantsToLearn) {
		this.wantsToLearn = wantsToLearn;
	}
	public boolean getIsNative() {
		return isNative;
	}
	public void setIsNative(boolean isNative) {
		this.isNative = isNative;
	}
	public Integer getLanguageLevelId() {
		return languageLevelId;
	}
	public void setLanguageLevelId(Integer languageLevelId) {
		this.languageLevelId = languageLevelId;
	}
	public UserLanguage() {
		super();
	}
	public UserLanguage(Integer id, Integer userId, Integer languageId,
			boolean wantsToLearn, boolean isNative, Integer languageLevelId) {
		super();
		this.id = id;
		this.userId = userId;
		this.languageId = languageId;
		this.wantsToLearn = wantsToLearn;
		this.isNative = isNative;
		this.languageLevelId = languageLevelId;
	}
}
