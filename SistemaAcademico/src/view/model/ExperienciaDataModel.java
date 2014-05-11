package view.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ProfessionalExperience;

public class ExperienciaDataModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1822145651662502013L;
	
	private List<ProfessionalExperience> linhas;
    private String[] colunas = new String[]{
        "Empresa", "Instituição", "Data Inicial", "Data Fim"};

    public ExperienciaDataModel() {
        linhas = new ArrayList<ProfessionalExperience>();
    }

    public ExperienciaDataModel(List<ProfessionalExperience> lista) {
        linhas = new ArrayList<ProfessionalExperience>(lista);
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
        ProfessionalExperience experiencia = linhas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return experiencia.getFirm();
            case 1:
                return experiencia.getFunction();
            case 2:
                return experiencia.getStartDate();
            case 3:
                return experiencia.getEndDate();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    public ProfessionalExperience getExperiencia(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    public void addExperiencia(ProfessionalExperience exp) {
        linhas.add(exp);
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void removeExperiencia(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void addListaDeFormacao(List<ProfessionalExperience> experiencias) {
        int tamanhoAntigo = getRowCount();
        linhas.addAll(experiencias);
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