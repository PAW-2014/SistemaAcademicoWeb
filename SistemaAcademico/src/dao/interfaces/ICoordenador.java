package dao.interfaces;

import java.util.List;
import model.Disciplina;
import model.Professor;

public interface ICoordenador {

    public List<Professor> filtrarProfessor(String nome);

    public List<Disciplina> filtrarDisciplina(String nome);

}