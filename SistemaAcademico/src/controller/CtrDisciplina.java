package controller;

import dao.DisciplineDAO;
import dao.interfaces.IDisciplina;
import java.util.List;
import model.Discipline;
import model.PreferentialDiscipline;

public class CtrDisciplina {
    private static IDisciplina dao = new DisciplineDAO();
     /**
     * Método que recupera todas as disciplinas cadastradas no sistema
     * @return 
     */
    public static List<Discipline> listarDisciplinas(){
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
    public static List<PreferentialDiscipline> getDisciplinas(Integer idProfessor){
        try{
          return dao.getDisiciplinasPreferenciais(idProfessor);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean salvarDisciplinasPreferenciais(List<PreferentialDiscipline> list, List<PreferentialDiscipline> exclu){
        try{
           dao.salvarDisciplinasPreferenciais(list,exclu);
          return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
