package com.criteriaqueryapi.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.criteriaqueryapi.entities.Employee;

public class HibernateUtil {

	/*private static Session session;
	public static Session getSession()
	{
		//creating a cfg object of Configuration class
		Configuration cfg=new Configuration();
		cfg.configure();
		
		//calling a method of buildSessionFactoru() of return type factory
		SessionFactory factory=cfg.buildSessionFactory();
		Session session=factory.openSession();
		//returning session
		return session;
	}
	*/
	static SessionFactory factory = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Employee.class);
        factory = cfg.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return factory;
    }
    public static Session getSession() {
        return factory.openSession();
    }

}
