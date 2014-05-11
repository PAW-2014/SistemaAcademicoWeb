package controller;

import dao.ProfessionalExperienceDAO;
import dao.interfaces.IExperienciaProfissional;
import java.util.List;
import model.ProfessionalExperience;

public class CtrExperienciaProfissional {
    private static IExperienciaProfissional dao = new ProfessionalExperienceDAO();
    /**
     * Método para atualizar as experiências profissionais do professor
     * @param exp
     * @return 
     */
    
    public static boolean salvarOuAtualizarExperiencia(ProfessionalExperience ex) {
        try {
            dao.save(ex);
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
    public static List<ProfessionalExperience> getExperiencias(Integer id){
        try{
          return dao.recoverProfessionalExperiences(id);
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
    
     public static boolean verificarExperiencia(ProfessionalExperience ex) {
        try {
            return dao.verify(ex);
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
     public static boolean excluir(ProfessionalExperience exp) {
        try {
            dao.delete(exp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

