package controller;

import dao.FormacaoAcademicaDAO;
import dao.interfaces.IFormacaoAcademica;
import java.util.List;
import model.FormacaoAcademica;

public class CtrFormacaoAcademica {
    private static IFormacaoAcademica dao = new FormacaoAcademicaDAO();
   
    /**
     * Método para atualizar as formações acadêmicas do professor
     * @param pro
     * @param form
     * @return 
     */
    
    public static boolean salvarOuAtualizarFormacao(FormacaoAcademica form) {
        try {
            dao.salvarOuAtualizarFormacao(form);
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
    
     public static boolean verificarFormacao(FormacaoAcademica form) {
        try {
            return dao.verificarFormacao(form);
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
    public static List<FormacaoAcademica> getFormacoes(Integer id){
        try{
          return dao.getFormcaoesAcademicas(id);
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
     public static boolean excluir(FormacaoAcademica form) {
        try {
            dao.excluirFormacao(form);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
