package view.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.AcademicFormation;

public class FormacaoDataModel extends AbstractTableModel {

	private static final long serialVersionUID = 9151447301559780074L;
	
	private List<AcademicFormation> linhas;
    private String[] colunas = new String[]{
        "Nome Curso", "Instituição", "Data Inicial", "Data Fim"};

    public FormacaoDataModel() {
        linhas = new ArrayList<AcademicFormation>();
    }

    public FormacaoDataModel(List<AcademicFormation> lista) {
        linhas = new ArrayList<AcademicFormation>(lista);
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
        AcademicFormation formacao = linhas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return formacao.getNomeCurso();
            case 1:
                return formacao.getInstituicao();
            case 2:
                return formacao.getDataInicio();
            case 3:
                return formacao.getDataFim();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    public AcademicFormation getFormacao(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void addFormacao(AcademicFormation forma) {
        linhas.add(forma);
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeFormacao(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addListaDeFormacao(List<AcademicFormation> formacoes) {
        int tamanhoAntigo = getRowCount();
        linhas.addAll(formacoes);
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