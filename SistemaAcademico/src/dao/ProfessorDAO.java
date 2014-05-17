package dao;

import java.util.List;

import model.Professor;
import model.filter.ProfessorFilter;
import dao.interfaces.IProfessor;

public class ProfessorDAO extends GenericDML implements IProfessor {

	private static final long serialVersionUID = 2757953436449242428L;

	@Override
	public List<Professor> read(ProfessorFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
