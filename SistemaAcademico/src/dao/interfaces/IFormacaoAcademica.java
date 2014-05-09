package dao.interfaces;

import java.util.List;
import model.FormacaoAcademica;

public interface IFormacaoAcademica {
  List<FormacaoAcademica> getFormcaoesAcademicas(Integer idProfessor);
  void salvarOuAtualizarFormacao(FormacaoAcademica f);
  boolean verificarFormacao(FormacaoAcademica form);
  void excluirFormacao(FormacaoAcademica f);
}
