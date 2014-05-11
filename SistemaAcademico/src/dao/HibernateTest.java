package dao;

import java.text.MessageFormat;
import java.util.List;

import model.AcademicFormation;
import model.Address;
import model.Coordinator;
import model.Discipline;
import model.PreferentialDiscipline;
import model.Principal;
import model.ProfessionalExperience;
import model.Professor;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
	
	private static String host = "localhost";
	private static String port = "3306";
	private static String schemaName = "AcademicSystem";
	
	private static String baseUrl = "jdbc:mysql://{0}:{1}/{2}";
	
	private static String url = MessageFormat.format(baseUrl, host, port, schemaName);
	private static String driverClass = "com.mysql.jdbc.Driver";
	
	private static String user = "root";
	private static String password = "toor";
	
	public void test(){
		
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Discipline.class);
		configuration.addAnnotatedClass(AcademicFormation.class);
		configuration.addAnnotatedClass(Address.class);
		configuration.addAnnotatedClass(Coordinator.class);
		configuration.addAnnotatedClass(Discipline.class);
		configuration.addAnnotatedClass(PreferentialDiscipline.class);
		configuration.addAnnotatedClass(Principal.class);
		configuration.addAnnotatedClass(ProfessionalExperience.class);
		configuration.addAnnotatedClass(Professor.class);
		configuration.setProperty("hibernate.connection.url", url);
		configuration.setProperty("hibernate.connection.username", user);
		configuration.setProperty("hibernate.connection.password", password);
		configuration.setProperty("hibernate.connection.driver_class", driverClass);
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		
		Discipline discipline = new Discipline();
		discipline.setId(1);
		discipline.setName("Testing");
		
//		ServiceRegistry serviceRegistry = null; // TODO
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		session.saveOrUpdate(discipline);
		
		transaction.commit();
		
		Criteria criteria = session.createCriteria(Discipline.class);
		for (Discipline disciplina : (List<Discipline>)criteria.list()) {
			System.out.println(disciplina);
		}
		
		session.flush();
		session.close();
		
		sessionFactory.close();
		System.out.println("END");
	}
	
}
