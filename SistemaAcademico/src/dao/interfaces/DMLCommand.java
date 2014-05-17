package dao.interfaces;

public interface DMLCommand<Element> {

	public void create(Element... objects) throws Exception;
	
	public void update(Element... objects) throws Exception;
	
	public void delete(Element... objects) throws Exception;
	
}
