package view.Model;

import java.util.List;
import javax.swing.AbstractListModel;
import model.Disciplina;

public class DisciplinaListModel extends AbstractListModel {

	private static final long serialVersionUID = 3161642911906826704L;
	
	private List<Disciplina> disciplinas;

    public DisciplinaListModel(List<Disciplina> disciplinas) {
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
    public Disciplina getElementAt(int index) {
        try {
            return disciplinas.get(index);
        } catch (Exception e) {
            return null;
        } 
    }
}
