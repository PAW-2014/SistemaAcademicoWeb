package controller;

import dao.FormacaoAcademicaDAO;
import dao.interfaces.IFormacaoAcademica;
import java.util.List;
import model.AcademicFormation;

public class CtrFormacaoAcademica {
    private static IFormacaoAcademica dao = new FormacaoAcademicaDAO();
   
    /**
     * Método para atualizar as formações acadêmicas do professor
     * @param pro
     * @param form
     * @return 
     */
    
    public static boolean salvarOuAtualizarFormacao(AcademicFormation form) {
        try {
            dao.save(form);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método que verifica se a formacaoAcademica já foi cadastrada
     * @param form
     * @return 
     */
    
     public static boolean verificarFormacao(AcademicFormation form) {
        try {
            return dao.verify(form);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     /**
     * Método que recupera todas s informações acadêmicas de um determinado professor
     * @param id professor
     * @return 
     */
    public static List<AcademicFormation> getFormacoes(Integer id){
        try{
          return dao.recoverAcademicFormations(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Método para remover uma formação acadêmica
     * @param form
     * @return 
     */
     public static boolean excluir(AcademicFormation form) {
        try {
            dao.delete(form);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
