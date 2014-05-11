package dao;

import java.util.List;

import model.AcademicFormation;
import model.filter.AcademicFormationFilter;
import dao.connection.HibernateUtil;
import dao.interfaces.IAcademicFormation;

public class AcademicFormationDAO extends HibernateUtil implements IAcademicFormation{
	
	private static final long serialVersionUID = 8495174858714715641L;

	@Override
	public void create(AcademicFormation... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AcademicFormation> read(AcademicFormationFilter filters)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(AcademicFormation... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(AcademicFormation... object) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
}
