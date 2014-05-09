package dao.interfaces;

import java.util.List;
import model.Professor;

public interface IProfessor {
    Professor validarLogin(String login, String senha);

    public void atualizarDados(Professor pro);

    public Professor getProfessor(Integer id);
    
    List<Professor> listarApenasProfessores();

    public List<Professor> listarTodos();
}
