package controller;

import dao.ProfessorDAO;
import dao.interfaces.IProfessor;
import model.Professor;

public class CtrLogin {
	
  static IProfessor professorDAO = new ProfessorDAO();
    /**
     * Método que valida a autenticação do usuário no sistema
     * @param login
     * @param password
     * @return 
     */
    
    public static Professor authenticate(String login, String password) {
        Professor professor = null;
        
        try {
           professor = professorDAO.validateLogin(login, password);
        } catch (Exception e) {
            return null;
        }
        
        return professor;
    }
}
