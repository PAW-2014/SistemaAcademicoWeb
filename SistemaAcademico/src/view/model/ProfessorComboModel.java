package view.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import model.Professor;

public class ProfessorComboModel extends AbstractListModel implements ComboBoxModel{
	
	private static final long serialVersionUID = -4451970250369298647L;
	
	List<Professor> lista;
    Professor selecionado;
    
    public ProfessorComboModel(List<Professor> lista) {
        this.lista = lista;
        
        if(lista == null)
             this.lista = new ArrayList<Professor>();
    }
    
    @Override
    public int getSize() {
        try {
            return lista.size();
        } catch (Exception e) {
            return 0;
        }
    }
    @Override
    public Professor getElementAt(int index) {
        try{
            return lista.get(index);
        } catch(Exception e){
            return null;
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        try{
            selecionado = (Professor) anItem;
        } catch(Exception e){
            selecionado = null;
        }
    }

    @Override
    public Object getSelectedItem() {
        try{
            return (Professor) selecionado;
        } catch(Exception e){
            return null;
        }
    }
}
