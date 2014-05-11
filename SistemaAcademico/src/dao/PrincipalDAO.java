package dao;

import java.util.List;

import model.Principal;
import model.Professor;
import model.filter.PrincipalFilter;
import dao.connection.HibernateUtil;
import dao.interfaces.IPrincipal;

public class PrincipalDAO extends HibernateUtil implements IPrincipal {

	private static final long serialVersionUID = -1608363664597737997L;

	@Override
	public void create(Principal... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Principal> read(PrincipalFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Principal... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Principal... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <TypeOfProfessor extends Professor> void promove(TypeOfProfessor professor) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
//	TODO and that old methods ? Where do i put them ?
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
