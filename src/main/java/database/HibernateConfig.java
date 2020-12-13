package database;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    private static final SessionFactory SESSION_FACTORY;

    static {

        try {
            Configuration configuration = new Configuration();
            configuration.configure("/hibernate.cfg.xml");

            SESSION_FACTORY = configuration.buildSessionFactory();
        } catch (HibernateException hibernateException) {
            System.err.println("Błąd inicjalizacji");
            hibernateException.printStackTrace();
            throw hibernateException;
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
