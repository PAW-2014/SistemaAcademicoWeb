package dao.connection;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateUtil {

	protected Session session;
	protected Transaction transaction;
	
	protected void beginSession(){
		session = HibernateSingleton.sessionFactory.openSession();
	}
	
	protected void beginTransaction(){
		transaction = session.beginTransaction();
	}
	
	protected void commitTransaction(){
		transaction.commit();
	}
	
	protected void endSession(){
		session.flush();
		session.close();
	}
	
}
