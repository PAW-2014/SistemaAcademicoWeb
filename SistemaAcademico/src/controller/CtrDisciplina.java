package controller;

import dao.DisciplinaDAO;
import dao.interfaces.IDisciplina;
import java.util.List;
import model.Disciplina;
import model.DisciplinaPreferencial;

public class CtrDisciplina {
    private static IDisciplina dao = new DisciplinaDAO();
     /**
     * Método que recupera todas as disciplinas cadastradas no sistema
     * @return 
     */
    public static List<Disciplina> listarDisciplinas(){
        try{
          return dao.listarDisciplinas();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
     /**
     * Método que recupera todas as disciplinas cadastradas no sistema
     * @return 
     */
    public static List<DisciplinaPreferencial> getDisciplinas(Integer idProfessor){
        try{
          return dao.getDisiciplinasPreferenciais(idProfessor);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean salvarDisciplinasPreferenciais(List<DisciplinaPreferencial> list, List<DisciplinaPreferencial> exclu){
        try{
           dao.salvarDisciplinasPreferenciais(list,exclu);
          return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
