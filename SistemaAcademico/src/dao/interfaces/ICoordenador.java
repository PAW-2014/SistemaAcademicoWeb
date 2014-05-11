package dao.interfaces;

import java.util.List;
import model.Discipline;
import model.Professor;

public interface ICoordenador {

    public List<Professor> filtrarProfessor(String nome);

    public List<Discipline> filtrarDisciplina(String nome);

}