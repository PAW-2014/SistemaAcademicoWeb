package view.Model;

import java.util.List;
import javax.swing.AbstractListModel;
import model.Disciplina;
import model.DisciplinaPreferencial;

public class DisciplinaPreferencialListModel extends AbstractListModel {

    private List<DisciplinaPreferencial> disciplinas;

    public DisciplinaPreferencialListModel(List<DisciplinaPreferencial> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public int getSize() {
        try {
            return disciplinas.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public DisciplinaPreferencial getElementAt(int index) {
        try {
            return disciplinas.get(index);
        } catch (Exception e) {
            return null;
        } 
    }
}
