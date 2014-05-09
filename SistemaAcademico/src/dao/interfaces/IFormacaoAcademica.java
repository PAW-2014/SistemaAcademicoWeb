package dao.interfaces;

import java.util.List;
import model.AcademicFormation;

public interface IFormacaoAcademica {
	
  public List<AcademicFormation> recoverAcademicFormations(Integer idProfessor);
  
  public void save(AcademicFormation academicFormation);
  
  public boolean verify(AcademicFormation academicFormation);
  
  public void delete(AcademicFormation academicFormation);
  
}
