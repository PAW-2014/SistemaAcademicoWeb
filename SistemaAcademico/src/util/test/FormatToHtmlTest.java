package util.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import util.FormatToHtml;

public class FormatToHtmlTest extends FormatToHtml {

	@Test
	public void testHTMLTitleGoodCase() {
		
		String expected = "<center><h1>Teste</h1><hr/></center>";
		
		assertEquals(expected, HTMLTitle("Teste"));
	}
	
	@Test
	public void testHTMLTitleEmptyCase() {
		
		String expected = "<center><h1></h1><hr/></center>";
		
		assertEquals(expected, HTMLTitle(""));
	}
	
	@Test
	public void testHTMLTitleNullCase() {
		
		String expected = "<center><h1>null</h1><hr/></center>";
		
		assertEquals(expected, HTMLTitle(null));
	}
	
	@Test
	public void testHTMLSubTitleGoodCase() {
		
		String expected = "<center><h2 class='h2'>Teste</h2></center>";
		
		assertEquals(expected, HTMLSubTitle("Teste"));
	}
	
	@Test
	public void testHTMLSubTitleEmptyCase() {
		
		String expected = "<center><h2 class='h2'></h2></center>";
		
		assertEquals(expected, HTMLSubTitle(""));
	}
	
	@Test
	public void testHTMLSubTitleNullCase() {
		
		String expected = "<center><h2 class='h2'>null</h2></center>";
		
		assertEquals(expected, HTMLSubTitle(null));
	}

	@Ignore
	@Test
	public void testHTMLField() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testTagWrapperHTMLBaseTagsEnumString() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testTagWrapperHTMLBaseTagsEnumStringString() {
		fail("Not yet implemented");
	}

}
