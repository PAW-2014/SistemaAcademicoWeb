package dao;

import java.util.List;

import model.PreferentialDiscipline;
import model.filter.PreferentialDisciplineFilter;
import dao.connection.HibernateUtil;
import dao.interfaces.IPreferentialDiscipline;

public class PreferentialDisciplineDAO extends HibernateUtil implements IPreferentialDiscipline {

	private static final long serialVersionUID = 3041037158948904279L;

	@Override
	public void create(PreferentialDiscipline... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PreferentialDiscipline> read(
			PreferentialDisciplineFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(PreferentialDiscipline... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(PreferentialDiscipline... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
