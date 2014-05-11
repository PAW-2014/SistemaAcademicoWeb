package dao;

import java.util.List;

import model.ProfessionalExperience;
import model.filter.ProfessionalExperienceFilter;
import dao.connection.HibernateUtil;
import dao.interfaces.IProfessionalExperience;

public class ProfessionalExperienceDAO extends HibernateUtil implements IProfessionalExperience {

	private static final long serialVersionUID = -4147420342312051507L;

	@Override
	public void create(ProfessionalExperience... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProfessionalExperience> read(
			ProfessionalExperienceFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ProfessionalExperience... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ProfessionalExperience... object) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
    
}
