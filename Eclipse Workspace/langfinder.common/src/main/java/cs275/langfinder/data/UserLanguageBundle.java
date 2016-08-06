package cs275.langfinder.data;

public class UserLanguageBundle {
	UserLanguage userLanguage;
	Language language;
	LanguageLevel languageLevel;
	public UserLanguage getUserLanguage() {
		return userLanguage;
	}
	public void setUserLanguage(UserLanguage userLanguage) {
		this.userLanguage = userLanguage;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public LanguageLevel getLanguageLevel() {
		return languageLevel;
	}
	public void setLanguageLevel(LanguageLevel languageLevel) {
		this.languageLevel = languageLevel;
	}
	public UserLanguageBundle() {
		super();
	}
	public UserLanguageBundle(UserLanguage userLanguage, Language language,
			LanguageLevel languageLevel) {
		super();
		this.userLanguage = userLanguage;
		this.language = language;
		this.languageLevel = languageLevel;
	}

}
