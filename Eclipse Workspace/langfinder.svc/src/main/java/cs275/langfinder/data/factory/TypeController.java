package cs275.langfinder.data.factory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cs275.langfinder.data.Language;
import cs275.langfinder.data.LanguageLevel;
import cs275.langfinder.data.conn.call.DbHelper;

public class TypeController {
	public static Language[] getLanguages() {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<Language> languages = (List<Language>)session.createQuery("FROM Language").list();
			return languages.toArray(new Language[0]);
		} finally {
			session.close(); 
		}
	}
	
	public static List<LanguageLevel> getLanguageLevels() {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<LanguageLevel> languages = (List<LanguageLevel>)session.createQuery("FROM LanguageLevel").list();
			return languages;
		} finally {
			session.close();
		}
	}

	public static void main(String[] args) {
		Language[] languages = getLanguages();
		for(Language language: languages) {
			System.out.println("Language: " + language.getName());
		}
		DbHelper.getSessionFactory().close();
	}
}