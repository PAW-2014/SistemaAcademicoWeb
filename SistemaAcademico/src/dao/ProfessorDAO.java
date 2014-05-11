package dao;

import java.util.List;

import model.Professor;
import model.filter.ProfessorFilter;
import dao.connection.HibernateUtil;
import dao.interfaces.IProfessor;

public class ProfessorDAO extends HibernateUtil implements IProfessor {

	private static final long serialVersionUID = 2757953436449242428L;

	@Override
	public void create(Professor... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Professor> read(ProfessorFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Professor... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Professor... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
