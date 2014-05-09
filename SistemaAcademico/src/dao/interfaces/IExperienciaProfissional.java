package dao.interfaces;

import java.util.List;
import model.ProfessionalExperience;

public interface IExperienciaProfissional {
	
	public List<ProfessionalExperience> recoverProfessionalExperiences(Integer idProfessor);
	
	public void save(ProfessionalExperience professionalExperience);
	
	public boolean verify(ProfessionalExperience professionalExperience);

    public void delete(ProfessionalExperience professionalExperience);
    
}
