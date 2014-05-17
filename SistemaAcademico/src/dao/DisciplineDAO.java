package dao;

import java.util.List;

import model.Discipline;
import model.filter.DisciplineFilter;
import dao.interfaces.IDiscipline;

public class DisciplineDAO extends GenericDML implements IDiscipline {

	private static final long serialVersionUID = 4147298933458004503L;

	@Override
	public List<Discipline> read(DisciplineFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
