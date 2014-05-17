package dao;

import java.util.List;

import model.ProfessionalExperience;
import model.filter.ProfessionalExperienceFilter;
import dao.interfaces.IProfessionalExperience;

public class ProfessionalExperienceDAO extends GenericDML implements IProfessionalExperience {

	private static final long serialVersionUID = -4147420342312051507L;

	@Override
	public List<ProfessionalExperience> read(
			ProfessionalExperienceFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
    
}
