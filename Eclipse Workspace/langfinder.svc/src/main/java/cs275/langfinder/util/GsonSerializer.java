package cs275.langfinder.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSerializer {
	public static String serialize(Object obj) {
		Gson gson = new GsonBuilder()
		   .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
		return gson.toJson(obj);
	}
    public static <T> T unproxy(T proxy) {
        if (proxy == null) {
            return null;
        }

        if (proxy instanceof HibernateProxy) {
            Hibernate.initialize(proxy);

            HibernateProxy hibernateProxy = (HibernateProxy) proxy;
            T unproxiedObject = (T) hibernateProxy.getHibernateLazyInitializer().getImplementation();

            return unproxiedObject;
        }

        return proxy;
    }
}
