package dao;

import java.util.List;

import model.Coordinator;
import model.filter.CoordinatorFilter;
import dao.connection.HibernateUtil;
import dao.interfaces.ICoordinator;

public class CoordinatorDAO extends HibernateUtil implements ICoordinator {

	private static final long serialVersionUID = -4179272312453677733L;

	@Override
	public void create(Coordinator... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Coordinator> read(CoordinatorFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Coordinator... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Coordinator... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
