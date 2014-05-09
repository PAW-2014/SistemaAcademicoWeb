package dao.interfaces;

import java.util.List;
import model.ExperienciaProfissional;

public interface IExperienciaProfissional {
void excluirExperiencia(ExperienciaProfissional ep);
List<ExperienciaProfissional> getExperienciasProfissionais(Integer idProfessor);
void salvarOuAtualizarExperiencia(ExperienciaProfissional ep);

    public boolean verificarExperiencia(ExperienciaProfissional ex);
}
