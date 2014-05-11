package controller;

import dao.AcademicFormationDAO;
import dao.interfaces.IAcademicFormation;
import dao.interfaces.IFormacaoAcademica;

import java.util.List;

import model.AcademicFormation;

public class CtrFormacaoAcademica {
	
    private static IAcademicFormation dao = new AcademicFormationDAO();
   
    public static boolean salvarOuAtualizarFormacao(AcademicFormation form) {
        try {
            dao.create(form);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<AcademicFormation> getFormacoes(Integer id){
        try{
          return dao.recoverAcademicFormations(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
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
