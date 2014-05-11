package dao.interfaces;

import model.Principal;
import model.Professor;
import model.filter.PrincipalFilter;

public interface IPrincipal extends CRUD<Principal, PrincipalFilter> {
	
	public <TypeOfProfessor extends Professor> void promove(TypeOfProfessor professor) throws Exception;
	
}
