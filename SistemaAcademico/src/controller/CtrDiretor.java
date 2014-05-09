package controller;

import dao.DiretorDAO;
import dao.interfaces.IDiretor;
import model.Professor;

public class CtrDiretor {
private static IDiretor dao = new DiretorDAO();
    /**
     * Método para excluir um professor do sistema
     * @param pro
     * @return 
     */
    
    public static boolean excluirProfessor(Professor pro) {
        try {
           dao.excluirProfessor(pro);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método para promover professor
     * @param pro
     * @return 
     */
    
    public static boolean promoverProfessor(Professor pro) {
        try {
            dao.promoverProfessor(pro.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método para inativar um professor do sistema
     * @param pro
     * @return 
     */

    public static boolean inativarProfessor(Professor pro) {
//        List<Professor> list = Professor.getProfessoresList();
//        try {
//            list.remove(pro);
//            Professor.salvarProfessores(list);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
        return false;
    }
    
    /**
     * Método para importar dados do rh
     * @return 
     */
    
    public static boolean importarDados(){
        boolean b = false;
//         List<Professor> list = Professor.pegarDados();
//         List<Professor> lista = Professor.getProfessoresList();
//         try{
//             b = lista.containsAll(list);
//             if(b)
//                 return false;
//             b = lista.addAll(list);
//         }catch(Exception e){
//             e.printStackTrace();
//         }
         return b;
    }
}
