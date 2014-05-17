package dao;

import java.util.List;

import model.Address;
import model.filter.AddressFilter;
import dao.interfaces.IAddress;

public class AddressDAO extends GenericDML implements IAddress{

	private static final long serialVersionUID = 8528915637600160131L;

	@Override
	public List<Address> read(AddressFilter filters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
    
}
