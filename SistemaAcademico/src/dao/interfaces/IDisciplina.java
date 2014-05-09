package dao.interfaces;

import java.util.List;
import model.Disciplina;
import model.DisciplinaPreferencial;

public interface IDisciplina {
	
	public List<Disciplina> listarDisciplinas();
    
    public List<DisciplinaPreferencial> getDisiciplinasPreferenciais(Integer idProfessor);
    
    public void salvarDisciplinasPreferenciais(List<DisciplinaPreferencial> disciplinas, List<DisciplinaPreferencial> exclu);
    
}
