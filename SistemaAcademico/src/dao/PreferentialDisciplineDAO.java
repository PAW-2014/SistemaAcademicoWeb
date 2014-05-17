package dao;

import java.util.List;

import model.PreferentialDiscipline;
import model.filter.PreferentialDisciplineFilter;
import dao.interfaces.IPreferentialDiscipline;

public class PreferentialDisciplineDAO extends GenericDML implements IPreferentialDiscipline {

	private static final long serialVersionUID = 3041037158948904279L;

	@Override
	public List<PreferentialDiscipline> read(
			PreferentialDisciplineFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
