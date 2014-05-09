package controller;

import dao.ExperienciaProfissionalDAO;
import dao.interfaces.IExperienciaProfissional;
import java.util.List;
import model.ExperienciaProfissional;

public class CtrExperienciaProfissional {
    private static IExperienciaProfissional dao = new ExperienciaProfissionalDAO();
    /**
     * Método para atualizar as experiências profissionais do professor
     * @param exp
     * @return 
     */
    
    public static boolean salvarOuAtualizarExperiencia(ExperienciaProfissional ex) {
        try {
            dao.salvarOuAtualizarExperiencia(ex);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     /**
     * Método que recupera todas as experiências profissionais de um determinado professor
     * @param id professor
     * @return 
     */
    public static List<ExperienciaProfissional> getExperiencias(Integer id){
        try{
          return dao.getExperienciasProfissionais(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Método que verifica se a experiencia profissional já foi cadastrada
     * @param form
     * @return 
     */
    
     public static boolean verificarExperiencia(ExperienciaProfissional ex) {
        try {
            return dao.verificarExperiencia(ex);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método para remover uma experiência profissional
     * @param exp
     * @return 
     */
     public static boolean excluir(ExperienciaProfissional exp) {
        try {
            dao.excluirExperiencia(exp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

