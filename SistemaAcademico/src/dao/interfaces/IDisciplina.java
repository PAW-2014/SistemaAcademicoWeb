package dao.interfaces;

import java.util.List;
import model.Disciplina;
import model.DisciplinaPreferencial;

public interface IDisciplina {
    List<Disciplina> listarDisciplinas();
    public List<DisciplinaPreferencial> getDisiciplinasPreferenciais(Integer idProfessor);
    void salvarDisciplinasPreferenciais(List<DisciplinaPreferencial> disciplinas, List<DisciplinaPreferencial> exclu);
}
