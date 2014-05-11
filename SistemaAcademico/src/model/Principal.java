package model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="principal")
@Inheritance(strategy=InheritanceType.JOINED)
public class Principal extends Coordinator {
	
	private static final long serialVersionUID = -1844880944850399632L;

}
