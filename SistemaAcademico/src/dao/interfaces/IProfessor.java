package dao.interfaces;

import java.util.List;
import model.Professor;

public interface IProfessor {
	
    public Professor validateLogin(String login, String senha);

    public void updateData(Professor pro);

    public Professor recoverProfessor(Integer id);
    
    public List<Professor> listProfessors();

    public List<Professor> listAll();
    
}
