package controller;

import dao.ProfessorDAO;
import dao.interfaces.IProfessor;
import java.util.List;
import model.Professor;

public class CtrProfessor {
    static IProfessor pd = new ProfessorDAO();
    /**
     * Método de alteração de senha do professor
     * @param pro
     * @param senha
     * @return 
     */

    public static boolean alterarSenha(Professor pro, String senha) {
        try {
            pro.setSenha(senha);
            pd.atualizarDados(pro);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método para alterar um professor
     * @param pro
     * @return 
     */

    public static boolean alterarProfessor(Professor pro) {
        try {
            pd.atualizarDados(pro);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    /**
     * Método para recuperar um Professor utilizando seu id
     * @param id
     * @return 
     */
    
    public static Professor getProfessor(Integer id){
        Professor p;
       try {
            p = pd.getProfessor(id);
           return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
    
      /**
     * Método para lista apenas os professores
     * @return 
     */
    
    public static List<Professor> listarApenasProfessores() {
        List<Professor> professores = null;
        try {
           professores = pd.listarApenasProfessores();
        } catch (Exception e) {
            return null;
        }
        return professores;
    }

    public static List<Professor> listarTodos() {
        List<Professor> professores = null;
        try {
           professores = pd.listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return professores;
    }
}
