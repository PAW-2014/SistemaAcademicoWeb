package view.model;

import java.util.List;

import javax.swing.AbstractListModel;

import model.PreferentialDiscipline;

public class DisciplinaPreferencialListModel extends AbstractListModel {

	private static final long serialVersionUID = -227287167786551115L;
	
	private List<PreferentialDiscipline> disciplinas;

    public DisciplinaPreferencialListModel(List<PreferentialDiscipline> disciplinas) {
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
    public PreferentialDiscipline getElementAt(int index) {
        try {
            return disciplinas.get(index);
        } catch (Exception e) {
            return null;
        } 
    }
}
