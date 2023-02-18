package com.criteriaqueryapi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.criteriaqueryapi.entities.Employee;
import com.criteriaqueryapi.utility.HibernateUtil;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		// get the SessionFactory Obj
		SessionFactory factory = HibernateUtil.getSessionFactory();
		// get Session Obj
		Session ses = HibernateUtil.getSession();
		try (factory; ses) {
			// create the Objects
			Employee e1 = new Employee("sam", 5000.0, "QA");
			Employee e2 = new Employee("Jhon", 6000.0, "BA");
			Employee e3 = new Employee("Randy", 7000.0, "DEV");
			Employee e4 = new Employee("Brock", 5000.0, "QA");
			Employee e5 = new Employee("Angela", 6000.0, "BA");
			Employee e6 = new Employee("Carla", 7000.0, "DEV");
			Employee e7 = new Employee("Raja", 5000.0, "QA");
			Employee e8 = new Employee("Rani", 6000.0, "BA");
			Employee e9 = new Employee("Ram", 7000.0, "DEV");
			Employee e10 = new Employee("Sandy", 5000.0, "QA");

			// begin the tx
			ses.beginTransaction();

			ses.save(e1);
			ses.save(e2);
			ses.save(e3);
			ses.save(e4);
			ses.save(e5);
			ses.save(e6);
			ses.save(e7);
			ses.save(e8);
			ses.save(e9);
			ses.save(e10);

			// commit the tx
			ses.getTransaction().commit();
			System.out.println("Hello World");

		}
	}
}
