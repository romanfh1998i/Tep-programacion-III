package edu.pucmm.jdbc.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import javax.transaction.Transactional;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {

        try {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("Enitial SessionFactory creation failed" + th);
        }
    }

    @Transactional
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
