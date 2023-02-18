package com.criteriaqueryapi;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.criteriaqueryapi.entities.Employee;
import com.criteriaqueryapi.utility.HibernateUtil;

public class Test {

	public static void main(String[] args) {

		SessionFactory factory = HibernateUtil.getSessionFactory();

		Session ses = HibernateUtil.getSession();

		try (factory; ses) {
			// Case-1 getting records whose id are greater than 5 and less than 10
			// create cb
			CriteriaBuilder cb = ses.getCriteriaBuilder();
			// create CQ Object
			CriteriaQuery cq = cb.createQuery(Employee.class);
			// create root Object
			Root<Employee> root = cq.from(Employee.class);
			// write the cluase conditions
			cq.select(root).where(cb.and(cb.ge(root.get("id"), 5), cb.le(root.get("id"), 10)));
			// create query object
			Query query = ses.createQuery(cq);
			// execute the query
			List<Employee> list = query.getResultList();
			// print
			list.forEach(System.out::println);

			System.out.println("------------------------------------");

			// Case-2 selecting all the employees

			CriteriaBuilder cb1 = ses.getCriteriaBuilder();
			CriteriaQuery cq1 = cb1.createQuery(Employee.class);
			Root<Employee> root1 = cq1.from(Employee.class);// select * from Emp;
			Query query1 = ses.createQuery(cq1);
			List<Employee> list1 = query1.getResultList();
			list1.forEach(System.out::println);

			System.out.println("-----------------------------------");

			// Case-3 using in operator of sql

			CriteriaBuilder cb2 = ses.getCriteriaBuilder();
			CriteriaQuery cq2 = cb2.createQuery(Employee.class);
			Root<Employee> root2 = cq2.from(Employee.class);
			cq2.select(root).where(root.get("name").in("sam", "Jhon", "carla")).orderBy(cb2.asc(root.get("sal")));
			Query query2 = ses.createQuery(cq2);
			List<Employee> list2 = query2.getResultList();
			list2.forEach(System.out::println);

			System.out.println("----------------------------------");

			// Case-4 using like operator pattern

			CriteriaBuilder cb3 = ses.getCriteriaBuilder();
			CriteriaQuery cq3 = cb3.createQuery(Employee.class);
			Root<Employee> root3 = cq3.from(Employee.class);
			// clause condition
			cq3.select(root).where(cb3.like(root.get("name"), "s%"));
			Query query3 = ses.createQuery(cq3);
			List<Employee> list3 = query3.getResultList();
			list3.forEach(System.out::println);

			System.out.println("-----------------------------------");

			// Case-5 select specific column
			// create CB Object
			CriteriaBuilder cb4 = ses.getCriteriaBuilder();
			// create CQ Object
			CriteriaQuery cq4 = cb4.createQuery(Object[].class);
			// create root
			Root<Employee> root4 = cq4.from(Employee.class);// select * from Emp;
			// clause conditon
			cq4.multiselect(root4.get("id"), root4.get("name"))
					.where(cb4.and(cb4.ge(root4.get("id"), 5), (cb4.le(root4.get("id"), 10))))
					.orderBy(cb4.desc(root4.get("id")));
			// create query Object
			Query query4 = ses.createQuery(cq4);
			// execute the query
			List<Object[]> list4 = query4.getResultList();
			// print
			list4.forEach(roe -> {
				for (Object ob : roe) {
					System.out.print(ob + " ");
				}
				System.out.println();
			});

			System.out.println("-----------------------------------");

			// select ename from emp where ename like 's%'
			// create CB Object
			CriteriaBuilder cb5 = ses.getCriteriaBuilder();
			// create CQ Object
			CriteriaQuery cq5 = cb5.createQuery(String.class);
			// create root
			Root<Employee> root5 = cq5.from(Employee.class);// select * from Emp;
			// clause conditon
			cq5.multiselect(root5.get("name")).where(cb5.like(root5.get("name"), "R%"));
			// create query Object
			Query query5 = ses.createQuery(cq5);
			// execute
			List<String> list5 = query5.getResultList();
			// print
			list5.forEach(System.out::println);

			System.out.println("------------------------------------------");

			// select count(empid) from emp
			// create CB Object
			CriteriaBuilder cb6 = ses.getCriteriaBuilder();
			// create CQ Object
			CriteriaQuery cq6 = cb6.createQuery(Long.class);
			// create root
			Root<Employee> root6 = cq6.from(Employee.class);// select * from Emp;
			// clause conditon
			cq6.multiselect(cb6.count(root6.get("id")));
			// create query obj
			Query query6 = ses.createQuery(cq6);
			// execute
			Long count6 = (Long) query6.getSingleResult();
			// print
			System.out.println("Number of emps " + count6);

			System.out.println("------------------------------------------");

			// select count(eid),sum(sal),max(sal),min(sal),avg(sal) from emp
			// create CB Object
			CriteriaBuilder cb7 = ses.getCriteriaBuilder();
			// create CQ Object
			CriteriaQuery cq7 = cb7.createQuery(Object[].class);
			// create root
			Root<Employee> root7 = cq7.from(Employee.class);// select * from Emp;
			// specific col
			cq7.multiselect(cb7.count(root7.get("id")), cb7.max(root.get("sal")), cb7.min(root7.get("sal")),
					cb7.avg(root7.get("sal")), cb7.sum(root7.get("sal")));
			// create query obj
			Query query7 = ses.createQuery(cq7);
			// excute
			Object[] obj = (Object[]) query7.getSingleResult();
			// print
			System.out.println("Count of emo " + obj[0]);
			System.out.println("max sal " + obj[1]);
			System.out.println("min of sal " + obj[2]);
			System.out.println("avg of sal " + obj[3]);
			System.out.println("sum of sal " + obj[4]);

			System.out.println("-------------------------------------");

			// update the QA dept emp sal to 8000
			// create CB Object
			CriteriaBuilder cb8 = ses.getCriteriaBuilder();
			// create CU object
			CriteriaUpdate cu8 = cb8.createCriteriaUpdate(Employee.class);
			// create root
			Root<Employee> root8 = cu8.from(Employee.class);// select * from Emp;
			// clause
			cu8.set(root8.get("sal"), 8000.0).where(cb8.equal(root8.get("dept"), "QA"));
			// create Query Obj
			Query query8 = ses.createQuery(cu8);
			// execute
			ses.beginTransaction();
			int count8 = query8.executeUpdate();
			// print the number of records updated
			System.out.println("Number of records Updated " + count8);
			// commit tx
			ses.getTransaction().commit();

			System.out.println("--------------------------------------");

			// delete the emp whose name start with r
		 	// create CB Object
			CriteriaBuilder cb9 = ses.getCriteriaBuilder();
			// create cd obj
			CriteriaDelete cd9 = cb9.createCriteriaDelete(Employee.class);
			// create root
			Root<Employee> root9 = cd9.from(Employee.class);// select * from Emp;
			// clause condition
			cd9.where(cb9.like(root9.get("name"), "r%"));
			// create Query Object
			Query query9 = ses.createQuery(cd9);
			// execute
			ses.beginTransaction();
			int count9 = query9.executeUpdate();
			System.out.println("Number of emp deleted " + count9);
			// commit the tx
			ses.getTransaction().commit();

			// System.out.println("source code as same as upper one : ");
			/*
			 * //get the SessionFactory Obj SessionFactory factory =
			 * HibernateUtil.getSessionFactory(); //get Session Obj Session ses =
			 * HibernateUtil.getSession(); try(factory;ses){ //create cb CriteriaBuilder cb
			 * = ses.getCriteriaBuilder(); //create CQ Object CriteriaQuery cq =
			 * cb.createQuery(Employee.class); //create root Object Root<Employee> root =
			 * cq.from(Employee.class); //write the cluase conditions
			 * cq.select(root).where(cb.and(cb.ge(root.get("id"),5),cb.le(root.get("id"),10)
			 * )); //create query object Query query = ses.createQuery(cq); //execute the
			 * query List<Employee> list = query.getResultList(); //print
			 * list.forEach(System.out::println);
			 * 
			 * //select all the emp //create CB Obj CriteriaBuilder cb =
			 * ses.getCriteriaBuilder(); //create CQ Object CriteriaQuery cq =
			 * cb.createQuery(Employee.class); //create root Object Root<Employee> root =
			 * cq.from(Employee.class);//select * from Emp; //create query Object Query
			 * query = ses.createQuery(cq); //execute the query List<Employee> list =
			 * query.getResultList(); //print the info list.forEach(System.out::println);
			 * 
			 * //create CB Object CriteriaBuilder cb = ses.getCriteriaBuilder(); //create CQ
			 * Object CriteriaQuery cq = cb.createQuery(Employee.class); //create root
			 * Root<Employee> root = cq.from(Employee.class);//select * from Emp; //clause
			 * condtion
			 * cq.select(root).where(root.get("name").in("sam","Jhon","carla")).orderBy(cb.
			 * asc(root.get("sal"))); //create query Object Query query =
			 * ses.createQuery(cq); //execute the query List<Employee> list =
			 * query.getResultList(); //print the info list.forEach(System.out::println);
			 * 
			 * //create CB Object CriteriaBuilder cb = ses.getCriteriaBuilder(); //create CQ
			 * Object CriteriaQuery cq = cb.createQuery(Employee.class); //create root
			 * Root<Employee> root = cq.from(Employee.class);//select * from Emp; //clause
			 * condition cq.select(root).where(cb.like(root.get("name"), "s%")); //create
			 * query Object Query query = ses.createQuery(cq); //execute the query
			 * List<Employee> list = query.getResultList(); //print the info
			 * list.forEach(System.out::println);
			 * 
			 * //select specific multiple cols //create CB Object CriteriaBuilder cb =
			 * ses.getCriteriaBuilder(); //create CQ Object CriteriaQuery cq =
			 * cb.createQuery(Object[].class); //create root Root<Employee> root =
			 * cq.from(Employee.class);//select * from Emp; //clause conditon
			 * cq.multiselect(root.get("id"),root.get("name")).where(cb.and(cb.ge(root.get(
			 * "id"),5),(cb.le(root.get("id"),10)))).orderBy(cb.desc(root.get("id")));
			 * //create query Object Query query = ses.createQuery(cq); //execute the query
			 * List<Object[]> list = query.getResultList(); //print list.forEach(roe->{
			 * for(Object ob : roe) { System.out.print(ob + " "); } System.out.println();
			 * });
			 * 
			 * //select ename from emp where ename like 's%' //create CB Object
			 * CriteriaBuilder cb = ses.getCriteriaBuilder(); //create CQ Object
			 * CriteriaQuery cq = cb.createQuery(String.class); //create root Root<Employee>
			 * root = cq.from(Employee.class);//select * from Emp; //clause conditon
			 * cq.multiselect(root.get("name")).where(cb.like(root.get("name"), "R%"));
			 * //create query Object Query query = ses.createQuery(cq); //execute
			 * List<String> list = query.getResultList(); //print
			 * list.forEach(System.out::println);
			 * 
			 * //select count(empid) from emp //create CB Object CriteriaBuilder cb =
			 * ses.getCriteriaBuilder(); //create CQ Object CriteriaQuery cq =
			 * cb.createQuery(Long.class); //create root Root<Employee> root =
			 * cq.from(Employee.class);//select * from Emp; //clause conditon
			 * cq.multiselect(cb.count(root.get("id"))); //create query obj Query query =
			 * ses.createQuery(cq); //execute Long count = (Long)query.getSingleResult();
			 * //print System.out.println("Number of emps "+count);
			 * 
			 * //select count(eid),sum(sal),max(sal),min(sal),avg(sal) from emp //create CB
			 * Object CriteriaBuilder cb = ses.getCriteriaBuilder(); //create CQ Object
			 * CriteriaQuery cq = cb.createQuery(Object[].class); //create root
			 * Root<Employee> root = cq.from(Employee.class);//select * from Emp; //specific
			 * col cq.multiselect(cb.count(root.get("id")), cb.max(root.get("sal")),
			 * cb.min(root.get("sal")), cb.avg(root.get("sal")), cb.sum(root.get("sal")) );
			 * //create query obj Query query = ses.createQuery(cq); //excute Object[] obj =
			 * (Object[])query.getSingleResult(); //print
			 * System.out.println("Count of emo "+obj[0]);
			 * System.out.println("max sal "+obj[1]);
			 * System.out.println("min of sal "+obj[2]);
			 * System.out.println("avg of sal "+obj[3]);
			 * System.out.println("sum of sal "+obj[4]);
			 * 
			 * //update the QA dept emp sal to 8000 //create CB Object CriteriaBuilder cb =
			 * ses.getCriteriaBuilder(); //create CU object CriteriaUpdate cu =
			 * cb.createCriteriaUpdate(Employee.class); //create root Root<Employee> root =
			 * cu.from(Employee.class);//select * from Emp; //clause cu.set(root.get("sal"),
			 * 8000.0).where(cb.equal(root.get("dept"), "QA")); //create Query Obj Query
			 * query = ses.createQuery(cu); //execute ses.beginTransaction(); int count =
			 * query.executeUpdate(); //print the number of records updated
			 * System.out.println("Number of records Updated "+count); //commit tx
			 * ses.getTransaction().commit();
			 * 
			 * //delete the emp whose name start with r //create CB Object CriteriaBuilder
			 * cb = ses.getCriteriaBuilder(); //create cd obj CriteriaDelete cd =
			 * cb.createCriteriaDelete(Employee.class); //create root Root<Employee> root =
			 * cd.from(Employee.class);//select * from Emp; //clause condition
			 * cd.where(cb.like(root.get("name"), "r%")); //create Query Object Query query
			 * = ses.createQuery(cd); //execute ses.beginTransaction(); int count =
			 * query.executeUpdate(); System.out.println("Number of emp deleted "+count);
			 * //commit the tx ses.getTransaction().commit();
			 */

		}
	}
}
