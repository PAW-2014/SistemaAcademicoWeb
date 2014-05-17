package dao;

import dao.connection.HibernateUtil;
import dao.interfaces.DMLCommand;

public abstract class GenericDML extends HibernateUtil implements DMLCommand<Object>{

	@Override
	public void create(Object... objects) throws Exception {
		beginSession();
		
		for (Object object : objects) {
			try{
				beginTransaction();
				session.save(object);
				commitTransaction();
			}catch(Exception exception){
				throw exception;
			}
		}
		
		endSession();
	}

	@Override
	public void update(Object... objects) throws Exception {
		beginSession();
		
		for (Object object : objects) {
			try{
				beginTransaction();
				session.update(object);
				commitTransaction();
			}catch(Exception exception){
				throw exception;
			}
		}
		
		endSession();
	}

	@Override
	public void delete(Object... objects) throws Exception {
		beginSession();
		
		for (Object object : objects) {
			try{
				beginTransaction();
				session.delete(object);
				commitTransaction();
			}catch(Exception exception){
				throw exception;
			}
		}
		
		endSession();
	}
}
