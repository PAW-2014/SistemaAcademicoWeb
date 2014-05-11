package dao.interfaces;

import java.util.List;
import model.Discipline;
import model.PreferentialDiscipline;

public interface IDisciplina {
	
	public List<Discipline> listarDisciplinas();
    
    public List<PreferentialDiscipline> getDisiciplinasPreferenciais(Integer idProfessor);
    
    public void salvarDisciplinasPreferenciais(List<PreferentialDiscipline> disciplinas, List<PreferentialDiscipline> exclu);
    
}
