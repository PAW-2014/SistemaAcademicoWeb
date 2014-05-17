package dao.test;

import static org.junit.Assert.*;
import model.Discipline;

import org.junit.Ignore;
import org.junit.Test;

import dao.DisciplineDAO;

public class DisciplineDAOTest {

	DisciplineDAO disciplineDAO = new DisciplineDAO();
	
	@Test
	@Ignore
	public void testRead() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		
		Discipline discipline = new Discipline();
		discipline.setId(2);
		discipline.setName("T");
		
		try {
			disciplineDAO.create(discipline);
		} catch (Exception e) {
//			fail("Exception");
		}
		
	}
	
	@Test
	@Ignore
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testDelete() {
		fail("Not yet implemented");
	}

}
