package view.Model;

import java.util.List;
import javax.swing.AbstractListModel;
import model.Discipline;

public class DisciplinaListModel extends AbstractListModel {

	private static final long serialVersionUID = 3161642911906826704L;
	
	private List<Discipline> disciplinas;

    public DisciplinaListModel(List<Discipline> disciplinas) {
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
    public Discipline getElementAt(int index) {
        try {
            return disciplinas.get(index);
        } catch (Exception e) {
            return null;
        } 
    }
}
