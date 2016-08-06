package cs275.langfinder.data.conn.call;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cs275.langfinder.data.Language;
import cs275.langfinder.data.LanguageLevel;

public class DbHelper {
	static SessionFactory sessionFactory = null;

	public static synchronized SessionFactory getSessionFactory() {
		if(sessionFactory==null) {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		return sessionFactory;
	}

}
