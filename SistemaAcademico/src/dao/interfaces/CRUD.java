package dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface CRUD<Element,ElementFilter> extends DMLCommand<Object>,Serializable {

	public List<Element> read(ElementFilter filters) throws Exception;
	
}