package dao;

import java.util.List;

import model.AcademicFormation;
import model.filter.AcademicFormationFilter;
import dao.interfaces.IAcademicFormation;

public class AcademicFormationDAO extends GenericDML implements IAcademicFormation{
	
	private static final long serialVersionUID = 8495174858714715641L;

	@Override
	public List<AcademicFormation> read(AcademicFormationFilter filters)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
    
}
