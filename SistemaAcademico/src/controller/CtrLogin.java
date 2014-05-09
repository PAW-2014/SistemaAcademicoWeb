package controller;

import dao.ProfessorDAO;
import dao.interfaces.IProfessor;
import model.Professor;

public class CtrLogin {
  static IProfessor ip = new ProfessorDAO();
    /**
     * Método que valida a autenticação do usuário no sistema
     * @param login
     * @param senha
     * @return 
     */
    
    public static Professor autenticar(String login, String senha) {
        Professor p = null;
        try {
           p = ip.validarLogin(login, senha);
        } catch (Exception e) {
            return null;
        }
        return p;
    }
}
