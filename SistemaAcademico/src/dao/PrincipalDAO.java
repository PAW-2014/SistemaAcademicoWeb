package dao;

import java.util.List;

import model.Principal;
import model.filter.PrincipalFilter;
import dao.interfaces.IPrincipal;

public class PrincipalDAO extends GenericDML implements IPrincipal {

	private static final long serialVersionUID = -1608363664597737997L;

	@Override
	public List<Principal> read(PrincipalFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

//	TODO and that old methods ? Where do i put them ?
//	  promoveProfessor
//    private void criarCoordenador(Integer idProfessor);
//
//    public void delete(Professor p);
//
//    private void excluirEndereco(Integer idEndereco);
//
//    private void excluirFormacoes(Integer id);
//
//    private void excluirDisciplinasPreferenciais(Integer id);
//
//    private void excluirExperiencias(Integer id);
}
