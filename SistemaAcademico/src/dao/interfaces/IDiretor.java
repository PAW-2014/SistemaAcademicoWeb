package dao.interfaces;

import model.Professor;

public interface IDiretor {

    public void promoverProfessor(Integer idProfessor);

    public void excluirProfessor(Professor p);

}
