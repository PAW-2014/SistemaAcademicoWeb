package dao.interfaces;

import model.Professor;

public interface IDiretor {

    public void promove(Integer idProfessor);

    public void delete(Professor professor);

}
