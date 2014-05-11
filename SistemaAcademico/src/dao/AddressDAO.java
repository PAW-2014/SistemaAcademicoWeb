package dao;

import java.util.List;

import model.Address;
import model.filter.AddressFilter;
import dao.connection.HibernateUtil;
import dao.interfaces.IAddress;

public class AddressDAO extends HibernateUtil implements IAddress{

	private static final long serialVersionUID = 8528915637600160131L;

	@Override
	public void create(Address... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Address> read(AddressFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Address... object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Address... object) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

    
}
