package dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface CRUD<Element,ElementFilter> extends Serializable {

	public void create(Element... object) throws Exception;
	
	public List<Element> read(ElementFilter filters) throws Exception;
	
	public void update(Element... object) throws Exception;
	
	public void delete(Element... object) throws Exception;
	
}