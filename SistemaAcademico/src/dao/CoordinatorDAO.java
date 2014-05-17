package dao;

import java.util.List;

import model.Coordinator;
import model.filter.CoordinatorFilter;
import dao.interfaces.ICoordinator;

public class CoordinatorDAO extends GenericDML implements ICoordinator {

	private static final long serialVersionUID = -4179272312453677733L;

	@Override
	public List<Coordinator> read(CoordinatorFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
