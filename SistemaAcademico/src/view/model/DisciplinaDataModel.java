package view.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Discipline;

public class DisciplinaDataModel extends AbstractTableModel {

	private static final long serialVersionUID = 8931893645918565315L;
	
	private List<Discipline> linhas;
    private String[] colunas = new String[]{
        "Nome Disciplina"};

    public DisciplinaDataModel() {
        linhas = new ArrayList<Discipline>();
    }

    public DisciplinaDataModel(List<Discipline> lista) {
        linhas = new ArrayList<Discipline>(lista);
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
                return Date.class;
            case 3:
                return Date.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Discipline disciplina = linhas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return disciplina.getName();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    public Discipline getFormacao(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void addFormacao(Discipline forma) {
        linhas.add(forma);
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeFormacao(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addListaDeFormacao(List<Discipline> disciplinas) {
        int tamanhoAntigo = getRowCount();
        linhas.addAll(disciplinas);
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