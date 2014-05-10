package view.Model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Enum.SituProfessor;
import model.Professor;

public class ProfessorDataModel extends AbstractTableModel {

	private static final long serialVersionUID = -563358903351006439L;
	
	private List<Professor> linhas;
    private String[] colunas = new String[]{
        "Nome", "CPF", "Email", "Telefone", "Status" };

    public ProfessorDataModel() {
        linhas = new ArrayList<Professor>();
    }

    public ProfessorDataModel(List<Professor> lista) {
        linhas = new ArrayList<Professor>(lista);
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    ;

	@Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return SituProfessor.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Professor prof = linhas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return prof.getNome();
                case 1:
                return prof.getCpf();
            case 2:
                return prof.getEmail();
            case 3:
                return prof.getTelefone();
             case 4:
                return prof.getStatus();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    public Professor get(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void add(Professor forma) {
        linhas.add(forma);
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void remove(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addLista(List<Professor> professores) {
        int tamanhoAntigo = getRowCount();
        linhas.addAll(professores);
        fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
    }

    public void limpar() {
        linhas.clear();
        fireTableDataChanged();
    }

    public boolean isEmpty() {
        return linhas.isEmpty();
    }
}