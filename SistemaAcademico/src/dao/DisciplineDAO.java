package dao;

import java.util.List;

import model.Discipline;
import model.filter.DisciplineFilter;
import dao.connection.HibernateUtil;
import dao.interfaces.IDiscipline;

public class DisciplineDAO extends HibernateUtil implements IDiscipline {

	private static final long serialVersionUID = 4147298933458004503L;

	@Override
	public void create(Discipline... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Discipline> read(DisciplineFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Discipline... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Discipline... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
